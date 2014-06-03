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
package com.hyrt.cnp.module;

import android.content.Context;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.hyrt.cnp.account.AccountClient;
import com.hyrt.cnp.base.account.AccountScope;
import com.hyrt.cnp.base.account.CNPAccount;
import com.hyrt.cnp.base.account.CNPClient;

import java.io.File;

/**
 * Main module provide services and clients
 */
public class CNPModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new ServicesModule());
        install(new ActivityModule());
        install(AccountScope.module());
    }

    @Provides
    CNPClient client(Provider<CNPAccount> accountProvider) {
        return new AccountClient(accountProvider);
    }

    @Provides
    @Named("cacheDir")
    File cacheDir(Context context) {
        return new File(context.getFilesDir(), "cache");
    }

}
