package com.mng.hermes.util.http;

import java.util.Map;

@HttpRequestAssembler
public interface HttpUtil {
    void putWithBearerAndFragmentAndQuery(String url, String token, String fragment, Map<String, String> params);
}
