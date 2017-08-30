package com.luwojtaszek.springboot.retrofit.core.dto.github.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by lukasz on 30.08.2017.
 * With IntelliJ IDEA 15
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubGistDTO {
    private String id;
    private String url;
    @JsonProperty(value = "html_url")
    private String htmlUrl;
}
