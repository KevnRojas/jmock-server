package com.kars.jmock.server.app.dto;

import com.kars.jmock.server.repository.model.HttpHeader;
import com.kars.jmock.server.repository.model.MockBody;

import java.util.List;

public record ResponseMockDto(
        String id,
        MockBody body,
        List<HttpHeader> headers,
        Short httpStatus,
        Long delay
) {
}
