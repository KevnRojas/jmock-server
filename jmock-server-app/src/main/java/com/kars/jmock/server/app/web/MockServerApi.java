package com.kars.jmock.server.app.web;

import com.kars.jmock.server.app.dto.ResponseMockDto;
import com.kars.jmock.server.app.dto.ValidateRequestDto;
import com.kars.jmock.server.app.service.MockServerService;
import com.kars.jmock.server.repository.model.*;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

@Hidden
@RestController
@RequestMapping("jmock-server")
public class MockServerApi {

    private final MockServerService mockServerService;

    public MockServerApi(MockServerService mockServerService) {
        this.mockServerService = mockServerService;
    }

    @GetMapping(value = "/**")
    public ResponseEntity<String> get(HttpServletRequest request) {

        ValidateRequestDto validateRequestDto = new ValidateRequestDto(
                getPath(request),
                HttpMethod.GET,
                getHeaders(request),
                getQueryParams(request),
                null
        );

        return validateRequest(validateRequestDto);
    }

    @PostMapping(value = "/**")
    public ResponseEntity<String> post(HttpServletRequest request, @RequestBody String body) {

        ValidateRequestDto validateRequestDto = new ValidateRequestDto(
                getPath(request),
                HttpMethod.POST,
                getHeaders(request),
                getQueryParams(request),
                body
        );

        return validateRequest(validateRequestDto);
    }

    @PutMapping(value = "/**")
    public ResponseEntity<String> put(HttpServletRequest request, @RequestBody String body) {
        ValidateRequestDto validateRequestDto = new ValidateRequestDto(
                getPath(request),
                HttpMethod.PUT,
                getHeaders(request),
                getQueryParams(request),
                body
        );

        return validateRequest(validateRequestDto);
    }

    @PatchMapping(value = "/**")
    public ResponseEntity<String> patch(HttpServletRequest request, @RequestBody String body) {
        ValidateRequestDto validateRequestDto = new ValidateRequestDto(
                getPath(request),
                HttpMethod.PATCH,
                getHeaders(request),
                getQueryParams(request),
                body
        );

        return validateRequest(validateRequestDto);
    }

    @DeleteMapping(value = "/**")
    public ResponseEntity<String> delete(HttpServletRequest request, @RequestBody String body) {
        ValidateRequestDto validateRequestDto = new ValidateRequestDto(
                getPath(request),
                HttpMethod.DELETE,
                getHeaders(request),
                getQueryParams(request),
                body
        );

        return validateRequest(validateRequestDto);
    }

    private static String getPath(HttpServletRequest request) {
        return request.getRequestURI().replaceFirst("/jmock-server", "");
    }

    private HttpHeaders getHeaders(List<HttpHeader> headers, MockBody body) {
        HttpHeaders httpHeaders = new HttpHeaders();

        Optional.ofNullable(headers)
                .orElse(List.of())
                .forEach(httpHeader -> httpHeaders.add(httpHeader.getName(), httpHeader.getValue()));

        if (body != null && MockBodyType.JSON.equals(body.getType())) {
            httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        }

        return httpHeaders;
    }

    private List<QueryParam> getQueryParams(HttpServletRequest request) {
        final String queryParamsString = request.getQueryString();
        List<QueryParam> queryParams = new ArrayList<>();

        if (queryParamsString == null) {
            return queryParams;
        }

        final String[] params = queryParamsString.split("&");

        for (String param : params) {
            final String[] paramValue = param.split("=");
            queryParams.add(new QueryParam(paramValue[0], paramValue[1]));
        }

        return queryParams;
    }

    private List<HttpHeader> getHeaders(HttpServletRequest request) {
        List<HttpHeader> headers = new ArrayList<>();
        Enumeration<String> headerNames = request.getHeaderNames();

        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                final String headerName = headerNames.nextElement();
                headers.add(new HttpHeader(headerName, request.getHeader(headerName)));
            }
        }

        return headers;
    }

    private ResponseEntity<String> validateRequest(ValidateRequestDto validateRequestDto) {
        ResponseMockDto responseMock = this.mockServerService.validate(validateRequestDto);

        delay(responseMock.delay());

        return new ResponseEntity<>(
                responseMock.body() == null ? null : responseMock.body().getValue(),
                getHeaders(responseMock.headers(), responseMock.body()),
                responseMock.httpStatus()
        );
    }

    private void delay(Long delay) {
        if (delay != null && delay > 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
