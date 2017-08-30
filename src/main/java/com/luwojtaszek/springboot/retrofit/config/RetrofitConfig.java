package com.luwojtaszek.springboot.retrofit.config;

import com.luwojtaszek.springboot.retrofit.core.service.GitHubService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by lukasz on 30.08.2017.
 * With IntelliJ IDEA 15
 */
@Configuration
public class RetrofitConfig {

    @Value("${custom.gitHub.url}")
    private String gitHubUrl;

    private static Retrofit buildRetrofit(String baseUrl, OkHttpClient client) {
        return new Retrofit.Builder()
                .addConverterFactory(defaultConverter())
                .addCallAdapterFactory(defaultCallAdapter())
                .client(client)
                .baseUrl(baseUrl)
                .build();
    }

    private static retrofit2.Converter.Factory defaultConverter() {
        return JacksonConverterFactory.create();
    }

    private static retrofit2.CallAdapter.Factory defaultCallAdapter() {
        return RxJava2CallAdapterFactory.create();
    }

    @Bean
    public GitHubService gitHubService() {
        final Logger logger = LoggerFactory.getLogger(GitHubService.class);
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    logger.info("Sending request to url: {}", request.url());
                    Response response = chain.proceed(request);
                    logger.info("Received response for call: {}", request.url());
                    return response;
                })
                .build();
        final Retrofit retrofit = buildRetrofit(gitHubUrl, okHttpClient);

        return retrofit.create(GitHubService.class);
    }
}
