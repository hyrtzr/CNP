/*
 * Copyright 2012 GitHub Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hyrt.cnp.account;

import static android.accounts.AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE;
import static android.accounts.AccountManager.KEY_ACCOUNT_NAME;
import static android.accounts.AccountManager.KEY_ACCOUNT_TYPE;
import static android.accounts.AccountManager.KEY_AUTHTOKEN;
import static android.accounts.AccountManager.KEY_BOOLEAN_RESULT;
import static android.accounts.AccountManager.KEY_INTENT;
import static com.hyrt.cnp.account.LoginActivity.PARAM_AUTHTOKEN_TYPE;
import static com.hyrt.cnp.account.LoginActivity.PARAM_USERNAME;
import static com.hyrt.cnp.base.account.AccountConstants.ACCOUNT_TYPE;

import java.io.IOException;

import net.oschina.app.AppContext;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.model.User;
import com.hyrt.cnp.base.account.utils.LogHelper;

class AccountAuthenticator extends AbstractAccountAuthenticator {

    private static final String TAG = "CNPAccountAuthenticator";

    private Context context;

    public AccountAuthenticator(final Context context) {
        super(context);

        this.context = context;
    }

    /**
     * The user has requested to add a new account to the system. We return an
     * intent that will launch our login screen if the user has not logged in
     * yet, otherwise our activity will just pass the user's credentials on to
     * the account manager.
     */
    @Override
    public Bundle addAccount(final AccountAuthenticatorResponse response,
                             final String accountType, final String authTokenType,
                             final String[] requiredFeatures, final Bundle options)
            throws NetworkErrorException {
        LogHelper.i(TAG, "addAccount");
        final Intent intent = new Intent(context, LoginActivity.class);
//        final Intent intent = new Intent(context, WelcomeActivity.class);
        intent.putExtra(PARAM_AUTHTOKEN_TYPE, authTokenType);
        intent.putExtra(KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle confirmCredentials(
            final AccountAuthenticatorResponse response, final Account account,
            final Bundle options) {
        return null;
    }

    @Override
    public Bundle editProperties(final AccountAuthenticatorResponse response,
                                 final String accountType) {
        return null;
    }

    private Intent createLoginIntent(final AccountAuthenticatorResponse response) {
        LogHelper.i(TAG, "createLoginIntent");
        final Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(PARAM_AUTHTOKEN_TYPE, ACCOUNT_TYPE);
        intent.putExtra(KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        return intent;
    }

    @Override
    public Bundle getAuthToken(final AccountAuthenticatorResponse response,
                               final Account account, final String authTokenType,
                               final Bundle options) throws NetworkErrorException {
        LogHelper.d(TAG, "Retrieving OAuth2 token");

        final Bundle bundle = new Bundle();

        if (!ACCOUNT_TYPE.equals(authTokenType))
            return bundle;

        AccountManager am = AccountManager.get(context);
        String password = am.getPassword(account);
        if (TextUtils.isEmpty(password)) {
            bundle.putParcelable(KEY_INTENT, createLoginIntent(response));
            return bundle;
        }
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.set("username", account.name);
        params.set("password", password);
        User.UserModel userModel = null;
        try{
            userModel = getCustomRestTemplate().postForObject(
                    AppContext.getInstance().getString(R.string.path_api)+"account/login",
                    params, User.UserModel.class);
        }catch(Exception e){
            e.printStackTrace();
            throw new NetworkErrorException(new IOException("No authentication challenges found"));
        }
        if (userModel == null || userModel.getData() == null || TextUtils.isEmpty(userModel.getData().getToken())){
            bundle.putParcelable(KEY_INTENT, createLoginIntent(response));
        }else {
            bundle.putString(KEY_ACCOUNT_NAME, account.name);
            bundle.putString(KEY_ACCOUNT_TYPE, ACCOUNT_TYPE);
            bundle.putString(KEY_AUTHTOKEN, userModel.getData().getToken() + "&uuid=" + userModel.getData().getUuid() +
                    "&sid=" + userModel.getData().getNursery_id() +
                    "&cid=" + userModel.getData().getClassroom());
            am.clearPassword(account);
        }
        return bundle;
    }

    public RestTemplate getCustomRestTemplate() {
        return new RestTemplate(true, new HttpComponentsClientHttpRequestFactory());
    }

    @Override
    public String getAuthTokenLabel(final String authTokenType) {
        if (ACCOUNT_TYPE.equals(authTokenType))
            return authTokenType;
        else
            return null;
    }

    @Override
    public Bundle hasFeatures(final AccountAuthenticatorResponse response,
                              final Account account, final String[] features)
            throws NetworkErrorException {
        final Bundle result = new Bundle();
        result.putBoolean(KEY_BOOLEAN_RESULT, false);
        return result;
    }

    @Override
    public Bundle updateCredentials(
            final AccountAuthenticatorResponse response, final Account account,
            final String authTokenType, final Bundle options) {
        LogHelper.i(TAG, "updateCredentials");
        final Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(PARAM_AUTHTOKEN_TYPE, authTokenType);
        intent.putExtra(KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        if (!TextUtils.isEmpty(account.name))
            intent.putExtra(PARAM_USERNAME, account.name);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_INTENT, intent);
        return bundle;
    }
}
