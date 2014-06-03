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

import android.text.TextUtils;
import android.util.Log;

import com.google.inject.Provider;
import com.hyrt.cnp.base.account.CNPAccount;
import com.hyrt.cnp.base.account.CNPClient;
import com.hyrt.cnp.base.account.utils.LogHelper;

import static android.util.Log.DEBUG;

/**
 * {@link AccountClient} extensions that integrates with the Android account
 * manager to provide request credentials
 */
public class AccountClient extends CNPClient {

    private static final String TAG = "AccountClient";

    private final Provider<CNPAccount> accountProvider;


    /**
     * Create account-aware client
     *
     * @param accountProvider
     */
    public AccountClient(final Provider<CNPAccount> accountProvider) {
        super();
        this.accountProvider = accountProvider;
    }

    @Override
    public void configureRequest() {
        CNPAccount account = accountProvider.get();

//        if (Log.isLoggable(TAG, DEBUG))
            LogHelper.d(TAG, "Authenticating using " + account);
        String token = account.getAuthToken();
        if (!TextUtils.isEmpty(token))
            setOAuth2Token(token);
        else
            setCredentials(account.getUsername(), account.getPassword());
        super.configureRequest();
    }

}
