package com.kirill.pimenov.domain;

import lombok.Data;

@Data
public class GetUrl {
    private String preview;
    private String original;
    private String error = null;
    public GetUrl(String error) {
        this.error = error;
    }
    public GetUrl(){
    }
}
