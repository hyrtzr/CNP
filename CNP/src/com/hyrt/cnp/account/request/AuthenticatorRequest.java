package com.hyrt.cnp.account.request;

import net.oschina.app.AppContext;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.model.User;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

/**
 * Created by yepeng on 13-12-11.
 */
public class AuthenticatorRequest extends SpringAndroidSpiceRequest {

    private User user;

    public AuthenticatorRequest(User user) {
        super(User.UserModel.class);
        this.user = user;
    }

    /**
     * This method generates a unique cache key for this request. In this case
     * our cache key depends just on the keyword.
     *
     * @return
     */
    public String createCacheKey() {
        return "user."+user.getUsername();
    }

    @Override
    public User.UserModel loadDataFromNetwork() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.set("username", user.getUsername());
        params.set("password", user.getPassword());
        return getCustomRestTemplate().postForObject(
                AppContext.getInstance().getString(R.string.path_api)+"account/login",
                params, User.UserModel.class);
    }

    public RestTemplate getCustomRestTemplate() {
        return new RestTemplate(true, new HttpComponentsClientHttpRequestFactory());
    }
}
