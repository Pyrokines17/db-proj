package ru.nsu.db_proj;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Message {
    private String type;
    private ObjectNode payload;

    public Message() {
        this.payload = new ObjectMapper().createObjectNode();
    }

    public Message(String type) {
        this.type = type;
        this.payload = new ObjectMapper().createObjectNode();
    }

    public Message(String queryResult, ArrayNode results) {
        this.type = queryResult;
        this.payload = new ObjectMapper().createObjectNode();
        this.payload.set("results", results);
    }

    public Message(String queryResult, ObjectNode payload) {
        this.type = queryResult;
        this.payload = payload;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ObjectNode getContent() {
        return payload;
    }

    public void setContent(ObjectNode payload) {
        this.payload = payload;
    }

    public static Message fromJson(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Message.class);
    }

    public String toJson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
