package com.hyrt.cnp.account;

import static android.accounts.AccountManager.KEY_ACCOUNT_NAME;
import static android.accounts.AccountManager.KEY_ACCOUNT_TYPE;
import static android.accounts.AccountManager.KEY_AUTHTOKEN;
import static com.hyrt.cnp.base.account.AccountConstants.ACCOUNT_TYPE;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hyrt.cnp.R;
import com.hyrt.cnp.account.request.AuthenticatorRequest;
import com.hyrt.cnp.account.requestListener.LoginRequestListener;
import com.hyrt.cnp.base.account.model.User;
import com.hyrt.cnp.base.account.utils.AlertUtils;
import com.hyrt.cnp.school.ui.SchoolSearchResultActivity;
import com.octo.android.robospice.JacksonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;

public class LoginActivity extends AccountAuthenticatorActivity {

    /**
     * Auth token type parameter
     */
    public static final String PARAM_AUTHTOKEN_TYPE = "authtokenType";

    /**
     * Initial user name
     */
    public static final String PARAM_USERNAME = "username";

    /**
     * Was the original caller asking for an entirely new account?
     */
    protected boolean requestNewAccount = true;
    private String username = "";//slerman@163.com
    private String password = "";//123456
    private String authTokenType;

    private AccountManager accountManager;

    private EditText usernameEt;
    private EditText passwordEt;
    private Button loginBtn;

    private boolean isLoginPage = false;

    public static final int RESULT_FROM_SEARCH_ACTIVITY = 101;

    public SpiceManager spiceManager = new SpiceManager(
            JacksonSpringAndroidSpiceService.class);

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isRequestNewAccount() {
        return requestNewAccount;
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.user_login);
        loadWelcome();
        Intent intent = getIntent();
        //username = intent.getStringExtra(PARAM_USERNAME);
        authTokenType = intent.getStringExtra(PARAM_AUTHTOKEN_TYPE);
        int type = intent.getIntExtra("type", 0);
    }

    private void loadWelcome(){
        isLoginPage = false;
        setContentView(R.layout.activity_welcome);
        Button btnSearch = (Button) findViewById(R.id.btn_search);
        Button btnLogin = (Button) findViewById(R.id.btn_login);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, SchoolSearchResultActivity.class);
                intent.putExtra("isLogin", false);
                startActivityForResult(intent, RESULT_FROM_SEARCH_ACTIVITY);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadLogin();
            }
        });
    }

    private void loadLogin(){
        isLoginPage = true;
        setContentView(R.layout.user_login);
        accountManager = AccountManager.get(this);
        usernameEt = (EditText)findViewById(R.id.login_name_et);
        passwordEt = (EditText)findViewById(R.id.login_password_et);
        TextView tv_none_account = (TextView) findViewById(R.id.tv_none_account);
        tv_none_account.setText(Html.fromHtml("<u>没有登录账号?</u>"));
        loginBtn = (Button)findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(usernameEt.getText().toString().length() <= 0){
                        AlertUtils.getInstance().showCenterToast(LoginActivity.this, "用户名不能为空！");
                    }else if(passwordEt.getText().toString().length() <= 0){
                        AlertUtils.getInstance().showCenterToast(LoginActivity.this, "密码不能为空！");
                    }else{
                        //                if(!usernameEt.getText().toString().equals("")){
                        username = usernameEt.getText().toString();
                        password = passwordEt.getText().toString();
//                }
                        handlerLogin();
                    }
            }
        });

        //requestNewAccount = username == null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_FROM_SEARCH_ACTIVITY){
            loadLogin();
        }
    }

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    private void handlerLogin() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        AuthenticatorRequest request = new AuthenticatorRequest(user);
        String lastRequestCacheKey = request.createCacheKey();
        LoginRequestListener loginRequestListener = new LoginRequestListener(this);
        spiceManager.execute(request, lastRequestCacheKey,DurationInMillis.ONE_MINUTE,loginRequestListener.start());
    }


    public void finishLogin(final String username, final String password) {
        final Intent intent = new Intent();
        intent.putExtra(KEY_ACCOUNT_NAME, username);
        intent.putExtra(KEY_ACCOUNT_TYPE, ACCOUNT_TYPE);
        if (ACCOUNT_TYPE.equals(authTokenType))
            intent.putExtra(KEY_AUTHTOKEN, password);
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(isLoginPage){
                loadWelcome();
                return true;
            }
            if(notSupportKeyCodeBack()){
               // exitApp(); // 退出应用处理
            } else {
                // 返回桌面,经测试,有一些手机不支持,查看 notSupportKeyCodeBack 方法
                Intent i= new Intent(Intent.ACTION_MAIN);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                i.addCategory(Intent.CATEGORY_HOME);
                startActivity(i);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 测试手机是否支持返回到桌面
     * */
    private boolean notSupportKeyCodeBack(){
        if("3GW100".equals(Build.MODEL)|| "3GW101".equals(Build.MODEL) || "3GC101".equals (Build.MODEL)) {
            return true;
        }
        return false;
    }
}
