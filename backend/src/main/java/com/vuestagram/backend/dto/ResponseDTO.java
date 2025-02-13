package com.vuestagram.backend.dto;

import lombok.Getter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vuestagram.backend.config.ApiStatus;

@Getter
public class ResponseDTO<T> {

    @JsonProperty(required = true)
    private final int statusCd;

    @JsonProperty(required = true)
    private final String statusMsg;

    @JsonProperty(value = "success", required = true)
    private final boolean success;

    @JsonProperty(value = "body")
    private final T body;

    public ResponseDTO(ApiStatus status, boolean success, T body) {
    	this.statusCd = status.getStatus().value();
        this.statusMsg = status.getMessage();
        this.success = success;
        this.body = body;
    }
}

