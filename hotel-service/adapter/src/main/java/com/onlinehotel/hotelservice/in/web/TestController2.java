package com.onlinehotel.hotelservice.in.web;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController2 {
    @GetMapping("/test2")
    @Operation(
            description = "Get test by test-id",
            summary = "Get test summary"
    )
    public String test(){
        return "test2";
    }
}
