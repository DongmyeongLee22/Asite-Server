package me.asite.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class WebRestController {

    private final Environment environment;

    @GetMapping("/profile")
    public String getProfile(){
        return Arrays.stream(environment.getActiveProfiles())
                .findFirst()
                .orElse("");
    }
}
