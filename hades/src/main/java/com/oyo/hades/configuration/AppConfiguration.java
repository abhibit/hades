package com.oyo.hades.configuration;


import com.google.gson.Gson;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.apache.http.client.HttpClient;
import java.util.List;

@Configuration
public class AppConfiguration {

    @Value("${httpClient.connection.pool.size:100}")
    private String poolMaxTotal;

    @Value("${httpClientFactory.connection.timeout:100000}")
    private String connectionTimeOut;

    @Value("${httpClientFactory.read.timeout:60000}")
    private String readTimeOut;

    @Value("${httpClientFactory.read.paisa.timeout:6000}")
    private String paisaTimeOut;



    @Bean
    public RestTemplate restTemplate() {
        return restTemplate(Integer.parseInt(connectionTimeOut), Integer.parseInt(readTimeOut), Integer.parseInt(poolMaxTotal));
    }

    public RestTemplate restTemplate(int connectionTimeout, int readTimeout, int maxConnections) {
        RestTemplate template = new RestTemplate(httpRequestFactory(connectionTimeout, readTimeout, maxConnections));
        List<HttpMessageConverter<?>> messageConverters = template.getMessageConverters();
        messageConverters.add(new FormHttpMessageConverter());
        template.setMessageConverters(messageConverters);
        return template;
    }

    public ClientHttpRequestFactory httpRequestFactory(int connectionTimeout, int readTimeout, int maxConnections) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient(maxConnections));
        factory.setConnectTimeout(connectionTimeout);
        factory.setReadTimeout(readTimeout);
        return factory;
    }

    public HttpClient httpClient(int noOfConnections) {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(noOfConnections);
        return HttpClientBuilder.create().setConnectionManager(connectionManager).build();
    }

    @Bean("paisaRestTemplate")
    public RestTemplate paisaRestTemplate() {
        return restTemplate(Integer.parseInt(connectionTimeOut), Integer.parseInt(paisaTimeOut), Integer.parseInt(poolMaxTotal));

    }

    @Bean
    public Gson gson(){
        return new Gson();
    }


}
