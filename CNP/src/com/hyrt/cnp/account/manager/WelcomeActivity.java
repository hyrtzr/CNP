package com.hyrt.cnp.account.manager;

import static android.accounts.AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE;
import static com.hyrt.cnp.account.LoginActivity.PARAM_AUTHTOKEN_TYPE;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hyrt.cnp.R;
import com.hyrt.cnp.account.LoginActivity;
import com.hyrt.cnp.school.ui.SchoolSearchResultActivity;
import com.jingdong.common.frame.BaseActivity;

/**
 * Created by Zoe on 2014-04-23.
 */
public class WelcomeActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        findView();
    }

    private void findView() {
        Button btnSearch = (Button) findViewById(R.id.btn_search);
        Button btnLogin = (Button) findViewById(R.id.btn_login);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(WelcomeActivity.this, SchoolSearchResultActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Intent data = getIntent();
                intent.putExtra(PARAM_AUTHTOKEN_TYPE, data.getStringExtra("PARAM_AUTHTOKEN_TYPE"));
                intent.putExtra(KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, data.getParcelableExtra("KEY_ACCOUNT_AUTHENTICATOR_RESPONSE"));
                intent.setClass(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void initTitleview() {
        actionBar.hide();
    }
}
