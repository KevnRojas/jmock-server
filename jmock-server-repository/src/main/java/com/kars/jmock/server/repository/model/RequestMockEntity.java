package com.kars.jmock.server.repository.model;

import java.time.OffsetDateTime;
import java.util.List;

public class RequestMockEntity {

    private String id;
    private String name;
    private String path;
    private HttpMethod method;
    private ResponseMockEntity response;
    private List<HttpHeader> headers;
    private List<QueryParam> queryParams;
    private MockBody body;
    private OffsetDateTime createAtDate;
    private OffsetDateTime updateAtDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public ResponseMockEntity getResponse() {
        return response;
    }

    public void setResponse(ResponseMockEntity response) {
        this.response = response;
    }

    public List<HttpHeader> getHeaders() {
        return headers;
    }

    public void setHeaders(List<HttpHeader> headers) {
        this.headers = headers;
    }

    public List<QueryParam> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(List<QueryParam> queryParams) {
        this.queryParams = queryParams;
    }

    public MockBody getBody() {
        return body;
    }

    public void setBody(MockBody body) {
        this.body = body;
    }

    public OffsetDateTime getCreateAtDate() {
        return createAtDate;
    }

    public void setCreateAtDate(OffsetDateTime createAtDate) {
        this.createAtDate = createAtDate;
    }

    public OffsetDateTime getUpdateAtDate() {
        return updateAtDate;
    }

    public void setUpdateAtDate(OffsetDateTime updateAtDate) {
        this.updateAtDate = updateAtDate;
    }
}
