package com.luwojtaszek.springboot.retrofit.core.service;

import com.luwojtaszek.springboot.retrofit.core.dto.github.common.GitHubGistDTO;
import com.luwojtaszek.springboot.retrofit.core.dto.github.common.GitHubRepositoryDTO;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

/**
 * Created by lukasz on 30.08.2017.
 * With IntelliJ IDEA 15
 */
public interface GitHubService {
    @GET("users/{user}/gists")
    Single<List<GitHubGistDTO>> getGists(@Path("user") String user);

    @GET("users/{user}/repos")
    Single<List<GitHubRepositoryDTO>> getRepos(@Path("user") String user);
}
