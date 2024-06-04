package com.kars.jmock.server.app.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kars.jmock.server.app.dto.*;
import com.kars.jmock.server.app.exception.MockAdminBadRequestException;
import com.kars.jmock.server.repository.model.*;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public final class MockAdminMapper {

    private static final String EMPTY = "";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private MockAdminMapper() {
    }

    public static String getPath(String path) {
        if (path == null || EMPTY.equals(path.trim())) {
            throw new MockAdminBadRequestException("Path is required");
        }

        return path;
    }

    public static HttpMethod getMethod(HttpMethod method) {
        if (method == null) {
            throw new MockAdminBadRequestException("Method is required");
        }

        return method;
    }

    public static MockBody getBody(MockBody body) {

        if (body != null) {
            if (body.getType() == null) {
                throw new MockAdminBadRequestException("Body type is required");
            } else if (body.getType() == MockBodyType.JSON && !isJson(body.getValue())) {
                throw new MockAdminBadRequestException("Body value not is JSON");
            }
        }

        return body;
    }

    private static boolean isJson(String value) {
        try {
            MAPPER.readTree(value);
        } catch (JsonProcessingException e) {
            return false;
        }

        return true;
    }

    public static GetMockResponse toResponse(RequestMockEntity requestMockEntity) {
        return new GetMockResponse(
                requestMockEntity.getId(),
                requestMockEntity.getName(),
                requestMockEntity.getPath(),
                requestMockEntity.getMethod(),
                new ResponseMockDto(
                        requestMockEntity.getResponse().getId(),
                        requestMockEntity.getResponse().getBody(),
                        requestMockEntity.getResponse().getHeaders(),
                        requestMockEntity.getResponse().getHttpStatus(),
                        requestMockEntity.getResponse().getDelay() == null ? null : requestMockEntity.getResponse().getDelay().toMillis()
                ),
                requestMockEntity.getHeaders(),
                requestMockEntity.getQueryParams(),
                requestMockEntity.getBody()
        );
    }

    public static RequestMockEntity toRequestMockEntity(CreateMockRequest request) {
        RequestMockEntity requestMockEntity = new RequestMockEntity();
        requestMockEntity.setPath(MockAdminMapper.getPath(request.path()));
        requestMockEntity.setName(request.name());
        requestMockEntity.setMethod(MockAdminMapper.getMethod(request.method()));
        requestMockEntity.setBody(MockAdminMapper.getBody(request.body()));
        requestMockEntity.setHeaders(Optional.ofNullable(request.headers()).orElse(List.of()));
        requestMockEntity.setQueryParams(Optional.ofNullable(request.queryParams()).orElse(List.of()));

        ResponseMockEntity responseMockEntity = new ResponseMockEntity();
        responseMockEntity.setBody(getBody(request.response().body()));
        responseMockEntity.setHeaders(request.response().headers());

        setDelay(request.response().delay(), responseMockEntity);

        responseMockEntity.setHttpStatus(request.response().httpStatus());
        requestMockEntity.setResponse(responseMockEntity);

        return requestMockEntity;
    }

    public static RequestMockEntity toRequestMockEntity(String id, UpdateMockRequest request) {
        RequestMockEntity requestMockEntity = new RequestMockEntity();
        requestMockEntity.setId(id);
        requestMockEntity.setPath(MockAdminMapper.getPath(request.path()));
        requestMockEntity.setName(request.name());
        requestMockEntity.setMethod(MockAdminMapper.getMethod(request.method()));
        requestMockEntity.setBody(MockAdminMapper.getBody(request.body()));
        requestMockEntity.setHeaders(Optional.ofNullable(request.headers()).orElse(List.of()));
        requestMockEntity.setQueryParams(Optional.ofNullable(request.queryParams()).orElse(List.of()));

        ResponseMockEntity responseMockEntity = new ResponseMockEntity();
        responseMockEntity.setBody(getBody(request.response().body()));
        responseMockEntity.setHeaders(request.response().headers());

        setDelay(request.response().delay(), responseMockEntity);

        responseMockEntity.setHttpStatus(request.response().httpStatus());
        requestMockEntity.setResponse(responseMockEntity);

        return requestMockEntity;
    }

    private static void setDelay(Long delay, ResponseMockEntity responseMockEntity) {
        if (delay != null && delay > 0) {
            responseMockEntity.setDelay(Duration.of(delay, ChronoUnit.MILLIS));
        } else {
            responseMockEntity.setDelay(null);
        }
    }

    public static UpdateMockResponse toUpdateMockResponse(RequestMockEntity requestMockEntity) {
        return new UpdateMockResponse(
                requestMockEntity.getId(),
                requestMockEntity.getName(),
                requestMockEntity.getPath(),
                requestMockEntity.getMethod(),
                new ResponseMockDto(
                        requestMockEntity.getResponse().getId(),
                        requestMockEntity.getResponse().getBody(),
                        requestMockEntity.getResponse().getHeaders(),
                        requestMockEntity.getResponse().getHttpStatus(),
                        requestMockEntity.getResponse().getDelay() == null ? null : requestMockEntity.getResponse().getDelay().toMillis()
                ),
                requestMockEntity.getHeaders(),
                requestMockEntity.getQueryParams(),
                requestMockEntity.getBody()
        );
    }

    public static ResponseMockDto toResponse(ResponseMockEntity response) {
        return new ResponseMockDto(
                response.getId(),
                response.getBody(),
                response.getHeaders(),
                response.getHttpStatus(),
                response.getDelay() == null ? null : response.getDelay().toMillis()
        );
    }
}