package com.mng.hermes.util.http;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestInvoker implements InvocationHandler {

    private static Logger LOGGER = LoggerFactory.getLogger(RequestInvoker.class);
    private HttpRequestBuilder http = new HttpRequestBuilder();
    private Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    @SuppressWarnings("unchecked")
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // TODO: 2018. 07. 05. Checking args to prevent errors

        String name = method.getName();
        String path = args[0].toString();
        Class returnType = method.getReturnType();

        LOGGER.info("\nInvoked method: {}", name);

        Matcher keyMatcher = Pattern.compile("^(get|post|put|delete)").matcher(name);
        if (!keyMatcher.find()) {
            throw new InvalidMethodSignature("Method needs to start with: get, post, put, or delete");
        }
        String requestMethod = keyMatcher.group(1);
        LOGGER.info("REQUEST METHOD: " + requestMethod);

        boolean convertFromJson = name.contains("Json");
        LOGGER.info("Json flag: " + convertFromJson);

        keyMatcher = Pattern.compile("With([a-zA-z]{6,})$").matcher(name);
        if (keyMatcher.find()) {
            String optionsRaw = keyMatcher.group(1);
            List<String> optionNames = Arrays.asList(optionsRaw.split("And"));
            LOGGER.info(optionNames.toString());

            Map<String, String> queries = null;
            Map<String, String> headers = null;
            String fragment = null;
            String token = null;

            for (int i = 0; i < optionNames.size(); i++) {
                int paramIndex = i + 1;
                switch (optionNames.get(i)) {
                    case "Headers":
                        headers = (Map<String, String>)args[paramIndex];
                        break;
                    case "Query":
                        queries = (Map<String, String>)args[paramIndex];
                        break;
                    case "Bearer":
                        token = args[paramIndex].toString();
                        break;
                    case "Fragment":
                        fragment = args[paramIndex].toString();
                        break;
                }
            }

            String url;
            if (queries != null && fragment != null) {
                url = http.assembleUrl(path, queries, fragment);
            } else if (queries != null) {
                url = http.assembleUrl(path, queries);
            } else {
                url = path;
            }
            HttpURLConnection connection = http.initConnection(url, requestMethod);
            LOGGER.info("Connection opened on path " + url + "\n" + connection);

            if (token != null) {
                http.addToken(connection, token);
            }
            if (headers != null) {
                http.setHeaders(connection, headers);
            }

            String response = http.sendRequest(connection);
            return response != null && convertFromJson ? gson.fromJson(response, returnType) : response;
        }

        return null;
    }
}
