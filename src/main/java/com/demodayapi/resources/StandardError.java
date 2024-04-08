package com.demodayapi.resources;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;

public class StandardError implements Serializable {

    private static final long serialVersionUID = 1L;

    private Instant timestamp;
    private Integer status;
    private Map<String, String> errors;
    private String message;
    private String path;

    public StandardError(){
        
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
