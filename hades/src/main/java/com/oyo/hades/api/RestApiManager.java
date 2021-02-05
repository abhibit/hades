package com.oyo.hades.api;



import com.google.gson.Gson;
import com.oyo.hades.util.TransformUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;


@Component
public class RestApiManager {

    @Autowired
    Gson gson;

    private static final Logger log = LoggerFactory.getLogger(com.oyo.hades.api.RestApiManager.class);

    public <T> T get(String baseUrl, String url, String query, HttpHeaders requestHeaders, Class<T> responseClassType, String logTag, RestTemplate restTemplate) {
        ResponseEntity<T> responseEntity = null;
        try {
            String completeUrl = getFullUrl(baseUrl, url, query);
            log.info("RestApiManager: GET params -> {}, {}, {}", completeUrl, query, requestHeaders);
            HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestHeaders);
            responseEntity = restTemplate.exchange(completeUrl, HttpMethod.GET, requestEntity, responseClassType);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            } else {
                throw new Exception(String.format("Response other than 200 OK for params -> {}, {}, {}", completeUrl, query, requestHeaders));
            }
        } catch (Exception e) {
            log.error("{} Error in RestApiManager:get : {} ; Exception : {}", logTag, responseEntity, e.getMessage(), e);
        } finally {
        }
        return null;
    }

    public <T> T post(String baseUrl, String url, String query, Object body, HttpHeaders requestHeaders, Class<T> responseClassType, String logTag, RestTemplate restTemplate) {
        ResponseEntity<T> responseEntity = null;
        try {
            String completeUrl = getFullUrl(baseUrl, url, query);
            log.info("RestApiManager: POST params -> {}, {}", completeUrl, requestHeaders);
            String bodyJson = null;
            if (body != null) {
                bodyJson = TransformUtil.toJson(body);
            }
            HttpEntity<Object> requestEntity = new HttpEntity<Object>(bodyJson, requestHeaders);
            responseEntity = restTemplate.exchange(completeUrl, HttpMethod.POST, requestEntity, responseClassType);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            } else {
                throw new Exception(String.format("Response other than 200 OK for params -> {}, {}", completeUrl, requestHeaders));
            }
        } catch (HttpStatusCodeException httpStatusCodeException){
            if(httpStatusCodeException.getStatusCode().is4xxClientError()){
                String responseBodyOfError = httpStatusCodeException.getResponseBodyAsString();
                return gson.fromJson(responseBodyOfError, responseClassType);
            }
        }
        catch (Exception e) {
            log.error("{} Error in RestApiManager:post : {} ; Exception : {}", logTag, responseEntity, e.getMessage(), e);
        } finally {
        }
        return null;
    }

    public <T> T put(String baseUrl, String url, String query, Object body, HttpHeaders requestHeaders, Class<T> responseClassType, String logTag, RestTemplate restTemplate) {
        ResponseEntity<T> responseEntity = null;
        try {
            String completeUrl = getFullUrl(baseUrl, url, query);
            log.info("RestApiManager: PUT params -> {}, {}", completeUrl, requestHeaders);
            String bodyJson = null;
            if (body != null) {
                bodyJson = TransformUtil.toJson(body);
            }
            HttpEntity<Object> requestEntity = new HttpEntity<Object>(bodyJson, requestHeaders);
            responseEntity = restTemplate.exchange(completeUrl, HttpMethod.PUT, requestEntity, responseClassType);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            } else {
                throw new Exception(String.format("Response other than 200 OK for params -> {}, {}", completeUrl, requestHeaders));
            }
        } catch (Exception e) {
            log.error("{} Error in RestApiManager:put : {} ; Exception : {}", logTag, responseEntity, e.getMessage(), e);
        } finally {
        }
        return null;
    }

    public <T> T postfile(String baseUrl, String url, String query, Object body, HttpHeaders requestHeaders, Class<T> responseClassType, String logTag, RestTemplate restTemplate) {
        ResponseEntity<T> responseEntity = null;
        try {
            String completeUrl = getFullUrl(baseUrl, url, query);
            log.info("RestApiManager: POST params -> {}, {}", completeUrl, requestHeaders);
            HttpEntity<Object> requestEntity = new HttpEntity<Object>(body, requestHeaders);
            responseEntity = restTemplate.exchange(completeUrl, HttpMethod.POST, requestEntity, responseClassType);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            } else {
                throw new Exception(String.format("Response other than 200 OK for params -> {}, {}", completeUrl, requestHeaders));
            }
        } catch (Exception e) {
            log.error("{} Error in RestApiManager:post : {} ; Exception : {}", logTag, responseEntity, e.getMessage(), e);
        } finally {
        }
        return null;
    }

    private String getFullUrl(String baseUrl, String url, String query) {
        StringBuilder fullUrl = new StringBuilder();
        fullUrl.append(baseUrl);
        if (url != null) {
            fullUrl.append(url);
        }
        if (query != null && query.startsWith("?")) {
            query = query.substring(1);
        }
        query = StringUtils.trimToNull(query);
        if (query != null) {
            fullUrl.append("?");
            fullUrl.append(query);
        }
        return fullUrl.toString();
    }



}

