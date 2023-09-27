package com.vention.proxy.web;

import static java.lang.String.format;

import java.io.IOException;
import java.util.Map;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author v.snitko
 * @since 2023.09.04
 */
@RestController
public class ProxyController {

    @GetMapping("/hello")
    public ResponseEntity<?> getHello() {
        return ResponseEntity.ok("hello");
    }

    @RequestMapping("/proxy/**")
    public String handleProxy(@RequestHeader Map<String, String> headers) throws IOException {
        final String url = format("%s%s", headers.get("host"), headers.get("x-original-uri"));
        return Request.get(url)
            .execute()
            .handleResponse(new BasicHttpClientResponseHandler());
    }
}
