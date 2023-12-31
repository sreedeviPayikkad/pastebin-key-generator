package io.pastebin.keygenerationservices.controller;

import io.pastebin.keygenerationservices.services.KeyInfoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class KeyGenController {
    @Autowired
    KeyInfoService keyInfoService;

    @GetMapping()
    public String test() {
        log.info("app test successful");
        return "Application up and running";
    }

    @PostMapping("/generate")
    public ResponseEntity<Object> generateKey(HttpServletRequest request) {
        String clientIP = request.getRemoteAddr();
        Map<String, String> responseData = new HashMap<>();
        String generatedKey = keyInfoService.generateKey(clientIP);
        if (generatedKey != null) {
            keyInfoService.saveGeneratedKey(generatedKey);
            responseData.put("key", generatedKey);
            return ResponseEntity.status(200).body(responseData);
        }
        responseData.put("key", null);
        return ResponseEntity.status(500).body(responseData);
    }
}
