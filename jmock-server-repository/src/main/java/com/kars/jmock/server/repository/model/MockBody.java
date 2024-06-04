package com.kars.jmock.server.repository.model;

public class MockBody {
    private MockBodyType type;
    private String value;

    public MockBodyType getType() {
        return type;
    }

    public void setType(MockBodyType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
