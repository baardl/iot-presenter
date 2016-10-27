/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.baardl.presenter.application;

import com.github.kevinsawicki.http.HttpRequest;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import io.baardl.presenter.util.StringConv;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.security.oauth2.common.OAuth2AccessToken.ACCESS_TOKEN;
import static org.springframework.security.oauth2.provider.token.AccessTokenConverter.GRANT_TYPE;

class CommandGetServiceAccountAccess extends HystrixCommand<Optional<String>> {

    private static final Logger log = getLogger(CommandGetServiceAccountAccess.class);
    private static final String CLIENT_CREDENTIALS = "client_credentials";
    private String tokenUrl;
    private String clientId;
    private String clientSecret;


    CommandGetServiceAccountAccess(String tokenUrl, String clientId, String clientSecret) {
        super(HystrixCommandGroupKey.Factory.asKey("GetServiceAccountAccess"));
        this.tokenUrl = tokenUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public Optional<String> run() {
        HttpRequest request = HttpRequest.post(tokenUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED.getType())
                .basic(clientId, clientSecret)
                .form(GRANT_TYPE, CLIENT_CREDENTIALS);

        byte[] responseBody = request.bytes();
        int statusCode = request.code();
        String responseAsText = StringConv.UTF8(responseBody);

        if (statusCode != 200) {
            log.warn("Service account login failed. Bad status: " + statusCode + " response: " + responseAsText);
            return Optional.empty();
        } else {
            JSONObject jsonObject = new JSONObject(responseAsText);
            if (jsonObject.has(ACCESS_TOKEN)) {
                return Optional.of(jsonObject.getString(ACCESS_TOKEN));
            } else {
                log.warn("Found no access token in response: {}", responseAsText);
                return Optional.empty();
            }
        }
    }

    @Override
    protected Optional<String> getFallback() {
        log.warn("Fallback - serviceUri={}", tokenUrl);
        return Optional.empty();
    }

}
