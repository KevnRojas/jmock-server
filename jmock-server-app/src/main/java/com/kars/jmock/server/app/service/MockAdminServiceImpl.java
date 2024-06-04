package com.kars.jmock.server.app.service;

import com.kars.jmock.server.app.dto.*;
import com.kars.jmock.server.app.mapper.MockAdminMapper;
import com.kars.jmock.server.repository.MockAdminRepository;
import com.kars.jmock.server.repository.model.RequestMockEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MockAdminServiceImpl implements MockAdminService {

    private final MockAdminRepository mockAdminRepository;

    public MockAdminServiceImpl(MockAdminRepository mockAdminRepository) {
        this.mockAdminRepository = mockAdminRepository;
    }

    @Override
    public CreateMockResponse create(CreateMockRequest createMockRequest) {

        RequestMockEntity response = this.mockAdminRepository.create(MockAdminMapper.toRequestMockEntity(createMockRequest));
        return new CreateMockResponse(
                response.getId(),
                response.getName(),
                response.getPath(),
                response.getMethod(),
                new ResponseMockDto(
                        response.getResponse().getId(),
                        response.getResponse().getBody(),
                        response.getResponse().getHeaders(),
                        response.getResponse().getHttpStatus(),
                        response.getResponse().getDelay() == null ? null : response.getResponse().getDelay().toMillis()
                ),
                response.getHeaders(),
                response.getBody()
        );
    }



    @Override
    public List<GetMockResponse> listAll() {
        return this.mockAdminRepository.listAll().stream()
                .map(MockAdminMapper::toResponse)
                .toList();
    }

    @Override
    public GetMockResponse findById(String id) {
        RequestMockEntity savedMock = this.mockAdminRepository.findById(id);

        return MockAdminMapper.toResponse(savedMock);
    }

    @Override
    public UpdateMockResponse update(String id, UpdateMockRequest request) {
        RequestMockEntity update = this.mockAdminRepository.update(MockAdminMapper.toRequestMockEntity(id, request));

        return MockAdminMapper.toUpdateMockResponse(update);
    }

    @Override
    public void delete(String id) {
        this.mockAdminRepository.delete(id);
    }
}
