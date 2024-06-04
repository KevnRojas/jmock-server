package com.kars.jmock.server.app.service;

import com.kars.jmock.server.app.dto.ResponseMockDto;
import com.kars.jmock.server.app.dto.ValidateRequestDto;

public interface MockServerService {

    ResponseMockDto validate(ValidateRequestDto validateRequestDto);
}
