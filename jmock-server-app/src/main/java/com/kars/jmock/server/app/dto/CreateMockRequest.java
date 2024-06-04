package com.kars.jmock.server.app.dto;

import com.kars.jmock.server.repository.model.HttpHeader;
import com.kars.jmock.server.repository.model.HttpMethod;
import com.kars.jmock.server.repository.model.QueryParam;
import com.kars.jmock.server.repository.model.MockBody;

import java.util.List;

public record CreateMockRequest(
        String name,
        String path,
        HttpMethod method,
        CreateResponseMockDto response,
        List<HttpHeader> headers,
        List<QueryParam> queryParams,
        MockBody body
) {

}
