package com.webhooks.cdc;

import com.gigya.socialize.*;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WebHooksCdcController {

    @PostMapping("/")
    public ResponseEntity Webhookv2(@RequestBody Map<String, Object>json, @RequestHeader Map<String, Object> header) throws GSKeyNotFoundException {
        String token = (String) header.get("x-gigya-sig-jwt");
        WebhookSignature signature= new WebhookSignature(token);
        if(signature.validateSignature()) {
           return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }
}
