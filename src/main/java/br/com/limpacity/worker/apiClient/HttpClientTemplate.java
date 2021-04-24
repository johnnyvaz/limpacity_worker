package br.com.limpacity.worker.apiClient;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Objects;

public class HttpClientTemplate {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientTemplate.class);
    private final RestTemplate restTemplate;

    public HttpClientTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> callAPI(String url, HttpMethod method, String token) throws IOException {
        return this.callAPI(url, method, this.headers(token));
    }

    public <T> ResponseEntity<String> callAPI(String url, HttpMethod method, String token, T body) throws IOException {
        return this.callAPI(url, method, this.headersAndBody(body, token));
    }

    private <T> ResponseEntity<String> callAPI(String url, HttpMethod method, HttpEntity<T> headers) throws IOException {

        long startCall = System.currentTimeMillis();
        MDC.put("http.method", method.name());
        MDC.put("http.url", url);

        try {
            ResponseEntity<String> response = this.restTemplate.exchange(url, method, headers, String.class);
            this.logResponse(startCall, response.getStatusCode().value(), (String)response.getBody(), response.getHeaders().toString());
            return response;
        } catch (HttpStatusCodeException var8) {
            this.logResponse(startCall, var8);
            return new ResponseEntity(var8.getResponseBodyAsString(), var8.getStatusCode());
        } catch (Exception var9) {
            this.logResponse(startCall, var9);
            throw var9;
        }
    }

    private void clearLog() {
        MDC.put("http.method", (Object)null);
        MDC.put("http.url", (Object)null);
        MDC.put("http.status_code", (Object)null);
        MDC.put("http.response_body", (Object)null);
        MDC.put("http.response_header", (Object)null);
        MDC.put("http.error", (Object)null);
    }

    private void logResponse(long startCall, Exception e) {
        MDC.put("http.status_code", "");
        MDC.put("http.response_body", "");
        MDC.put("http.response_header", "");
        MDC.put("http.error", e.getMessage());
        MDC.put("http.time", System.currentTimeMillis() - startCall);
        logger.error("client rest finish with errors.");
        this.clearLog();
    }

    private void logResponse(long startCall, HttpStatusCodeException e) {
        MDC.put("http.status_code", e.getStatusCode().value());
        MDC.put("http.response_body", e.getResponseBodyAsString());
        MDC.put("http.response_header", (Objects.requireNonNull(e.getResponseHeaders())).toString());
        MDC.put("http.error", e.getMessage());
        MDC.put("http.time", System.currentTimeMillis() - startCall);
        logger.warn("client rest finish with warning.");
        this.clearLog();
    }

    private void logResponse(long startCall, int statusCode, String responseBody, String responseHeaders) {
        MDC.put("http.status_code", statusCode);
        MDC.put("http.response_body", responseBody);
        MDC.put("http.response_header", responseHeaders);
        MDC.put("http.error", "");
        MDC.put("http.time", System.currentTimeMillis() - startCall);
        logger.debug("cliente rest finish");
        this.clearLog();
    }

    private <T> HttpEntity<T> headersAndBody(T body, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (token != null && !token.isBlank()) {
            headers.set("Authorization", "Bearer " + token);
        }

        HttpEntity<T> entity = new HttpEntity(body, headers);
        String request = body.toString();
        MDC.put("http.request", request);
        return entity;
    }

    private HttpEntity<Object> headers(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (token != null && !token.isBlank()) {
            headers.set("Authorization", "Bearer " + token);
        }

        HttpEntity<Object> entity = new HttpEntity(headers);
        return entity;
    }

}
