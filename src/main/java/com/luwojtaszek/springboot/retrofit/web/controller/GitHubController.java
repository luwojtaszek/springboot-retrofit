package com.luwojtaszek.springboot.retrofit.web.controller;

import com.luwojtaszek.springboot.retrofit.core.service.GitHubService;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executors;

/**
 * Created by lukasz on 30.08.2017.
 * With IntelliJ IDEA 15
 */
@Slf4j
@RestController
@RequestMapping("/github")
@RequiredArgsConstructor
public class GitHubController {
    private final GitHubService gitHubService;

    @GetMapping("/statistics")
    public Single<String> getStatisticsForUser(@RequestParam("user") String user) {
        log.info("Getting GitHub statistics for user: {}", user);
        Scheduler scheduler = Schedulers.from(Executors.newFixedThreadPool(2));

        return Single.zip(
                gitHubService.getGists(user).subscribeOn(scheduler),
                gitHubService.getRepos(user).subscribeOn(scheduler),
                (gists, repos) -> "Gists: " + gists.size() + ", Repos: " + repos.size()
        );
    }
}
