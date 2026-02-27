package com.onlinehotel.hotelservice;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    @Operation(
            description = "Get test by test-id",
            summary = "Get test summary"
    )
    public String test(){
        return "test";
    }
}
