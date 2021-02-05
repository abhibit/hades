package com.oyo.hades.api.paisa;

import com.oyo.hades.api.RestApiManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class BasePaisaApiManager extends RestApiManager {

    @Autowired
    @Qualifier("paisaRestTemplate")
    private RestTemplate restTemplate;

    @Value("${api.paisa.base.url}")
    private String basePaisaUrl;



    public <T> T get(String url, String query, Class<T> responseClassType, String logTag) {
        return super.get(basePaisaUrl, url, query, getRequestHeaders(), responseClassType, logTag, restTemplate);
    }

    public <T> T post(String url, String query, Object body, Class<T> responseClassType, String logTag) {
        return super.post(basePaisaUrl, url, query, body, getRequestHeaders(), responseClassType, logTag, restTemplate);
    }


    public <T> T postfile(String url, String query, Object body, Class<T> responseClassType, String logTag, String userId) {
        return super.postfile(basePaisaUrl, url, query, body, postFileRequestHeaders(userId), responseClassType, logTag, restTemplate);
    }


    private HttpHeaders getRequestHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(new MediaType("application", "json"));
        return requestHeaders;
    }

    private HttpHeaders postFileRequestHeaders(String userId) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

        return requestHeaders;
    }
}