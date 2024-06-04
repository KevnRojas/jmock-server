package com.kars.jmock.server.repository;

import com.kars.jmock.server.repository.exception.MockNotFoundException;
import com.kars.jmock.server.repository.model.RequestMockEntity;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class MockAdminOnMemoryRepository implements MockAdminRepository {

    private final List<RequestMockEntity> mockServer;

    public MockAdminOnMemoryRepository() {
        this.mockServer = new ArrayList<>();
    }

    @Override
    public RequestMockEntity create(RequestMockEntity requestMockEntity) {
        requestMockEntity.setId(UUID.randomUUID().toString());
        requestMockEntity.setCreateAtDate(OffsetDateTime.now());
        requestMockEntity.setUpdateAtDate(OffsetDateTime.now());
        requestMockEntity.getResponse().setId(UUID.randomUUID().toString());
        requestMockEntity.getResponse().setCreateAtDate(OffsetDateTime.now());
        requestMockEntity.getResponse().setUpdateAtDate(OffsetDateTime.now());
        this.mockServer.add(requestMockEntity);

        return requestMockEntity;
    }

    @Override
    public List<RequestMockEntity> listAll() {
        return new ArrayList<>(this.mockServer);
    }

    @Override
    public RequestMockEntity findById(String id) {
        return this.mockServer.stream()
                .parallel()
                .filter(requestMockEntity -> requestMockEntity.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new MockNotFoundException("Not found mock with id " + id));
    }

    @Override
    public RequestMockEntity update(RequestMockEntity requestMockEntity) {
        RequestMockEntity savedRequest = findById(requestMockEntity.getId());
        savedRequest.setBody(requestMockEntity.getBody());
        savedRequest.setHeaders(requestMockEntity.getHeaders());
        savedRequest.setMethod(requestMockEntity.getMethod());
        savedRequest.setName(requestMockEntity.getName());
        savedRequest.setResponse(requestMockEntity.getResponse());
        savedRequest.setPath(requestMockEntity.getPath());
        savedRequest.setUpdateAtDate(OffsetDateTime.now());

        return savedRequest;
    }

    @Override
    public void delete(String id) {
        this.mockServer.removeIf(requestMockEntity -> requestMockEntity.getId().equals(id));
    }

}
