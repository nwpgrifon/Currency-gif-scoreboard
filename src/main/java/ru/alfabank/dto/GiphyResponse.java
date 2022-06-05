package ru.alfabank.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class GiphyResponse {

    private List<Gif> data;

    public void setResponse(List<Gif> data) {
        this.data = data;
    }

    public List<Gif> getData() {
        return data;
    }
}
