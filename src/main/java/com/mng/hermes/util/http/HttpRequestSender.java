package com.mng.hermes.util.http;

import com.mng.hermes.model.User;

import java.util.Map;

@HttpRequestAssembler
public interface HttpRequestSender {

    User getJsonWithBearer(String url, String token);
    void getWithHeadersAndQuery(String url, Map<String, String> headers, Map<String, String> query);

}
