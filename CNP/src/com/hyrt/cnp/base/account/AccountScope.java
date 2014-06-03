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
package com.hyrt.cnp.base.account;

import android.accounts.Account;
import android.accounts.AccountManager;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.OutOfScopeException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Custom scope that makes an authenticated GitHub account available by
 * enforcing that the user is logged in before proceeding.
 */
public class AccountScope extends ScopeBase {

    private static final Key<CNPAccount> GITHUB_ACCOUNT_KEY = Key
            .get(CNPAccount.class);

    /**
     * Create new module
     *
     * @return module
     */
    public static Module module() {
        return new AbstractModule() {
            public void configure() {
                AccountScope scope = new AccountScope();

                bind(AccountScope.class).toInstance(scope);

                bind(GITHUB_ACCOUNT_KEY).toProvider(
                        AccountScope.<CNPAccount> seededKeyProvider()).in(
                        scope);
            }
        };
    }

    private final ThreadLocal<CNPAccount> currentAccount = new ThreadLocal<CNPAccount>();

    private final Map<CNPAccount, Map<Key<?>, Object>> repoScopeMaps = new ConcurrentHashMap<CNPAccount, Map<Key<?>, Object>>();

    /**
     * Enters scope using a GitHubAccount derived from the supplied account
     *
     * @param account
     * @param accountManager
     */
    public void enterWith(final Account account,
            final AccountManager accountManager) {
        enterWith(new CNPAccount(account, accountManager));
    }

    /**
     * Enter scope with account
     *
     * @param account
     */
    public void enterWith(final CNPAccount account) {
        if (currentAccount.get() != null)
            throw new IllegalStateException(
                    "A scoping block is already in progress");

        currentAccount.set(account);
    }

    /**
     * Exit scope
     */
    public void exit() {
        if (currentAccount.get() == null)
            throw new IllegalStateException("No scoping block in progress");

        currentAccount.remove();
    }

    @Override
    protected <T> Map<Key<?>, Object> getScopedObjectMap(final Key<T> key) {
        CNPAccount account = currentAccount.get();
        if (account == null)
            throw new OutOfScopeException("Cannot access " + key
                    + " outside of a scoping block");

        Map<Key<?>, Object> scopeMap = repoScopeMaps.get(account);
        if (scopeMap == null) {
            scopeMap = new ConcurrentHashMap<Key<?>, Object>();
            scopeMap.put(GITHUB_ACCOUNT_KEY, account);
            repoScopeMaps.put(account, scopeMap);
        }
        return scopeMap;
    }
}
