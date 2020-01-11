package com.kirill.pimenov.domain;

import lombok.Data;

@Data
public class GetUri {
    private String preview;
    private String original;
    private String error = null;
    public GetUri(String error) {
        this.error = error;
    }
    public GetUri(){
    }
}
