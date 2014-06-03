package com.hyrt.cnp.base.account;


import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;

import static org.eclipse.egit.github.core.client.IGitHubConstants.AUTH_TOKEN;

/**
 * Created by yepeng on 13-12-11.
 */
public class CNPClient {

    MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
    HashMap<String, String> paramsforGet = new HashMap<String, String>();

    public HashMap<String, String> getParamsforGet() {
        return paramsforGet;
    }

    public MultiValueMap<String, Object> getParams() {
        return params;
    }

    /**
     * Set credentials
     *
     * @param user
     * @param password
     * @return this client
     */
    public CNPClient setCredentials(final String user, final String password) {
        paramsforGet.clear();
        this.params.clear();
        if (user != null && user.length() > 0 && password != null
                && password.length() > 0) {
            params.set("username", user);
            params.set("password", password);
        }
        return this;
    }

    /**
     * Set OAuth2 token
     *
     * @param token
     * @return this client
     */
    public CNPClient setOAuth2Token(String token) {
        if (token != null && token.length() > 0) {
            paramsforGet.clear();
            this.params.clear();
            if (token != null && token.length() > 0) {
                String credentials = AUTH_TOKEN + '=' + token;
                String[] params = credentials.split("&");
                for (int i = 0; i < params.length; i++) {
                    String[] param = params[i].split("=");
                    this.params.set(param[0], param[1]);
                    this.paramsforGet.put(param[0], param[1]);
                }
            }

        }
        return this;
    }

    /**
     * configure http request
     *
     * @return this client
     */
    public void configureRequest() {
    }


}
