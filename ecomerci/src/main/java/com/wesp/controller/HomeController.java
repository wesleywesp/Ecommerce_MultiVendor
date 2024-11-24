package com.wesp.controller;

import com.wesp.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public ResponseEntity<ApiResponse> homeControllerHandler(){
        return ResponseEntity.ok(new ApiResponse("Welcome to Ecomerci"));
    }
}
