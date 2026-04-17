package com.IzabelaTarasin.spacebooking.error;

public class FieldError {
    private String field;
    private String reason;
    public FieldError(String field, String reason) {
        this.field = field;
        this.reason = reason;
    }
    public String getField() { return field; }
    public String getReason() { return reason; }
}