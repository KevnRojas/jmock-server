package com.kars.jmock.server.repository;

import com.kars.jmock.server.repository.model.RequestMockEntity;

import java.util.List;

public interface MockAdminRepository {

    RequestMockEntity create(RequestMockEntity requestMockEntity);

    List<RequestMockEntity> listAll();

    RequestMockEntity findById(String id);

    RequestMockEntity update(RequestMockEntity requestMockEntity);

    void delete(String id);
}
