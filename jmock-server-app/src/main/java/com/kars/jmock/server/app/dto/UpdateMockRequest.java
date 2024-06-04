package com.kars.jmock.server.app.dto;

import com.kars.jmock.server.repository.model.HttpHeader;
import com.kars.jmock.server.repository.model.HttpMethod;
import com.kars.jmock.server.repository.model.QueryParam;
import com.kars.jmock.server.repository.model.MockBody;

import java.util.List;

public record UpdateMockRequest(
        String name,
        String path,
        HttpMethod method,
        ResponseMockDto response,
        List<HttpHeader> headers,
        List<QueryParam> queryParams,
        MockBody body
) {
}
