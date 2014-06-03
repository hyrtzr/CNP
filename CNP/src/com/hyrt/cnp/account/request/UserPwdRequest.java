package com.hyrt.cnp.account.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.UserService;

/**
 * Created by yepeng on 14-1-3.
 */
public class UserPwdRequest extends BaseRequest {

    @Inject
    private UserService userService;
    private String password;
    private String newPwd;
    private String repeatPwd;

    public UserPwdRequest(Context context,String password,String newPwd,String repeatPwd) {
        super(BaseTest.class,context);
        this.password = password;
        this.newPwd = newPwd;
        this.repeatPwd = repeatPwd;
    }

    @Override
    public BaseTest run() {
        return userService.modifyUserPassword(password,newPwd,repeatPwd);
    }

    /**
     * This method generates a unique cache key for this request. In this case
     * our cache key depends just on the keyword.
     * @return
     */
    public String createCacheKey() {
        return "user.pwd";
    }

	@Override
	public int compareTo(Object another) {
		// TODO Auto-generated method stub
		return 0;
	}
}
