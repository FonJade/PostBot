package models;

import kong.unirest.JsonNode;

public class JsonResponse {
    private int statusCode;
    private JsonNode body;

    public JsonNode getBody() {
        return body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public JsonResponse(int statusCode, JsonNode body) {
        this.statusCode = statusCode;
        this.body = body;
    }
}