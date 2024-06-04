package com.kars.jmock.server.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kars.jmock.server.app.dto.ResponseMockDto;
import com.kars.jmock.server.app.dto.ValidateRequestDto;
import com.kars.jmock.server.app.exception.MockNotFoundFromRequestException;
import com.kars.jmock.server.app.mapper.MockAdminMapper;
import com.kars.jmock.server.repository.MockAdminRepository;
import com.kars.jmock.server.repository.model.HttpHeader;
import com.kars.jmock.server.repository.model.QueryParam;
import com.kars.jmock.server.repository.model.MockBodyType;
import com.kars.jmock.server.repository.model.RequestMockEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class MockServerServiceImpl implements MockServerService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(MockServerServiceImpl.class);

    private final MockAdminRepository mockAdminRepository;

    public MockServerServiceImpl(MockAdminRepository mockAdminRepository) {
        this.mockAdminRepository = mockAdminRepository;
    }

    @Override
    public ResponseMockDto validate(ValidateRequestDto validateRequestDto) {
        return this.mockAdminRepository.listAll().stream()
                .filter(requestMockEntity -> isMatchUrl(requestMockEntity, validateRequestDto))
                .filter(requestMockEntity -> isMatchMethod(requestMockEntity, validateRequestDto))
                .filter(requestMockEntity -> isMatchQueryParams(requestMockEntity, validateRequestDto))
                .filter(requestMockEntity -> isMatchHeaders(requestMockEntity, validateRequestDto))
                .filter(requestMockEntity -> isMatchBody(requestMockEntity, validateRequestDto))
                .findFirst()
                .map(requestMockEntity -> MockAdminMapper.toResponse(requestMockEntity.getResponse()))
                .orElseThrow(() -> new MockNotFoundFromRequestException("No se encontró coincidencia con los Mocks registrados"));
    }

    private boolean isMatchBody(RequestMockEntity requestMockEntity, ValidateRequestDto validateRequestDto) {

        if (requestMockEntity.getBody() == null) {
            return true;
        }

        if (requestMockEntity.getBody().getType() == MockBodyType.TEXT
                && requestMockEntity.getBody().getValue().equals(validateRequestDto.body())) {
            return true;
        }

        return switch (requestMockEntity.getBody().getType()) {
            case TEXT -> requestMockEntity.getBody().getValue().equals(validateRequestDto.body());
            case JSON -> compareJson(requestMockEntity.getBody().getValue(), validateRequestDto.body());
        };

    }

    private boolean compareJson(String mockBody, String requestBody) {
        try {
            return OBJECT_MAPPER.readTree(mockBody).equals(OBJECT_MAPPER.readTree(requestBody));
        } catch (JsonProcessingException ex) {
            log.warn("Error al procesar JSON", ex);

            return false;
        }
    }

    private boolean isMatchMethod(RequestMockEntity requestMockEntity, ValidateRequestDto validateRequestDto) {
        return requestMockEntity.getMethod().equals(validateRequestDto.method());
    }

    private boolean isMatchHeaders(RequestMockEntity requestMockEntity, ValidateRequestDto validateRequestDto) {
        if (requestMockEntity.getHeaders() == null || requestMockEntity.getHeaders().isEmpty()) {
            return true;
        }

        for (HttpHeader header : requestMockEntity.getHeaders()) {
            if (!isMathHeader(header, validateRequestDto.headers())) {
                return false;
            }
        }

        return true;
    }

    private boolean isMathHeader(HttpHeader header, List<HttpHeader> headersFromRequest) {
        return headersFromRequest.stream()
                .anyMatch(headerFromRequest ->
                        header.getName().equalsIgnoreCase(headerFromRequest.getName())
                                && header.getValue().equals(headerFromRequest.getValue()));
    }

    private boolean isMatchQueryParams(RequestMockEntity requestMockEntity, ValidateRequestDto validateRequestDto) {

        if (requestMockEntity.getQueryParams() == null || requestMockEntity.getQueryParams().isEmpty()) {
            return true;
        }

        for (QueryParam queryParam : requestMockEntity.getQueryParams()) {
            if (!isMathQueryParam(queryParam, validateRequestDto.queryParams())) {
                return false;
            }
        }

        return true;
    }

    private boolean isMathQueryParam(QueryParam queryParam, List<QueryParam> queryParamsFromRequest) {
        return queryParamsFromRequest.stream()
                .anyMatch(queryParamFromRequest ->
                        queryParam.getName().equals(queryParamFromRequest.getName())
                                && queryParam.getValue().equals(queryParamFromRequest.getValue()));
    }

    private boolean isMatchUrl(RequestMockEntity mock, ValidateRequestDto request) {
        return Pattern.compile(mock.getPath()).matcher(request.path()).find();
    }
}
