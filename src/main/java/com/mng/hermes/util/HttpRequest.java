package com.mng.hermes.util;

import com.mng.hermes.model.User;
import com.mng.hermes.util.http.HttpRequestAssembler;
import com.mng.hermes.util.http.HttpRequestSender;
import com.mng.hermes.util.http.HttpUtil;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

@Component
public class HttpRequest {

    private static String USERINFO_ENDPOINT = "https://danielcs88.eu.auth0.com/userinfo";
    private static String TESTER = "https://webhook.site/3ec85d6d-32ca-4bbd-b72c-bf8600ce399b";

    @HttpRequestAssembler
    private HttpRequestSender http;

    @HttpRequestAssembler
    private HttpUtil httpUtil;

    public User fetchUserData(String token) {
        //testHttpMagic();
        return http.getJsonWithBearer(USERINFO_ENDPOINT, token);
    }

    private void testHttpMagic() {
        Map<String, String> headers = new HashMap<>();
        headers.put("x-test", "first header there");
        headers.put("x-custom", "another one");
        Map<String, String> query = new HashMap<>();
        query.put("key", "value");
        query.put("java hashmap init", "need a rehaul");
        http.getWithHeadersAndQuery(TESTER, headers, query);
        httpUtil.putWithBearerAndFragmentAndQuery(TESTER, "secrettokken", "fragmenthere", query);
    }

}
