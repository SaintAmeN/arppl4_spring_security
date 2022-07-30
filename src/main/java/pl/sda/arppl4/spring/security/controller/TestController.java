package pl.sda.arppl4.spring.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Paweł Recław, AmeN
 * @project arppl4_spring_security
 * @created 30.07.2022
 */
@Slf4j
@RestController
@RequestMapping("/api/test")
public class TestController {

    // dostępne dla każdego:        /api/test
    @GetMapping("")
    public String test() {
        return "test";
    }

    // dostępne dla zalogowanego :  /api/test/authorized
    @GetMapping("/authorized")
    public String testAuthorized() {
        return "testAuthorized";
    }
}
