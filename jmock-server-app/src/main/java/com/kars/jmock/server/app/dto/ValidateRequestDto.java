package com.kars.jmock.server.app.dto;

import com.kars.jmock.server.repository.model.HttpHeader;
import com.kars.jmock.server.repository.model.HttpMethod;
import com.kars.jmock.server.repository.model.QueryParam;

import java.util.List;

public record ValidateRequestDto(
        String path,
        HttpMethod method,
        List<HttpHeader> headers,
        List<QueryParam> queryParams,
        String body
) {
}
