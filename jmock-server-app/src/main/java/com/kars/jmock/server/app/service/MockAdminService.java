package com.kars.jmock.server.app.service;

import com.kars.jmock.server.app.dto.*;

import java.util.List;

public interface MockAdminService {

    CreateMockResponse create(CreateMockRequest createMockRequest);

    List<GetMockResponse> listAll();

    GetMockResponse findById(String id);

    UpdateMockResponse update(String id, UpdateMockRequest request);

    void delete(String id);

}
