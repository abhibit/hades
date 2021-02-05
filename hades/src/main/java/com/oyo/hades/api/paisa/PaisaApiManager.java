package com.oyo.hades.api.paisa;

import com.fasterxml.jackson.core.type.TypeReference;

import com.oyo.hades.api.paisa.request.SampleRequest;
import com.oyo.hades.api.paisa.response.SampleResponse;
import com.oyo.hades.exception.InternalServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Component
public class PaisaApiManager extends BasePaisaApiManager {

    private static final Logger log = LoggerFactory.getLogger(com.oyo.hades.api.paisa.PaisaApiManager.class);


    private final static String CONSUMER_PRICE_FETCH="/consumer-price/fetch";






    public SampleResponse getConsumerPriceFromPaisa(SampleRequest request){

        SampleResponse response;
        try {
            response = super.post(CONSUMER_PRICE_FETCH, null, request, SampleResponse.class, "");
        }catch (Exception e){
            throw new InternalServerException("Paisa did not return any data");
        }
        return response;
    }



}
