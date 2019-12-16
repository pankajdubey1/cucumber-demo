package com.cucumber;

public class ResponseHandler {

    private int statuscode;
    private String jsonrequest;

    public int getStatusCode() {
        return statuscode;
    }

    public void setStatusCode(int newStatusCode) {
        this.statuscode = newStatusCode;
    }

    public void setJsonRequest(String newJsonRequest) {
        this.jsonrequest = newJsonRequest;
    }

    public String getJsonRequest() {
        return jsonrequest;
    }
}
