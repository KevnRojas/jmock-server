package com.kars.jmock.server.repository.model;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

public class ResponseMockEntity {

    private String id;
    private MockBody body;
    private List<HttpHeader> headers;
    private Short httpStatus;
    private Duration delay;
    private OffsetDateTime createAtDate;
    private OffsetDateTime updateAtDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MockBody getBody() {
        return body;
    }

    public void setBody(MockBody body) {
        this.body = body;
    }

    public List<HttpHeader> getHeaders() {
        return headers;
    }

    public void setHeaders(List<HttpHeader> headers) {
        this.headers = headers;
    }

    public Short getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Short httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Duration getDelay() {
        return delay;
    }

    public void setDelay(Duration delay) {
        this.delay = delay;
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
