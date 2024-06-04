package com.kars.jmock.server.app.web;

import com.kars.jmock.server.app.dto.*;
import com.kars.jmock.server.app.service.MockAdminService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/mocks")
public class MockAdminApi {

    private final MockAdminService mockAdminService;

    public MockAdminApi(MockAdminService mockAdminService) {
        this.mockAdminService = mockAdminService;
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateMockResponse create(@RequestBody CreateMockRequest request) {
        return this.mockAdminService.create(request);
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GetMockResponse> listAll() {
        return this.mockAdminService.listAll();
    }

    @GetMapping(value = "{mockId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetMockResponse findById(@PathVariable("mockId") String id) {

        return this.mockAdminService.findById(id);
    }

    @PutMapping(value = "{mockId}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UpdateMockResponse update(@PathVariable("mockId") String id,
                                     @RequestBody UpdateMockRequest request) {

        return this.mockAdminService.update(id, request);
    }

    @DeleteMapping(value = "{mockId}")
    public void delete(@PathVariable("mockId") String id) {
        this.mockAdminService.delete(id);
    }
}
