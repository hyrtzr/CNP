package com.hyrt.cnp.account.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.UserDetail;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.UserService;

/**
 * Created by yepeng on 14-1-3.
 */
public class UserDetailRequest extends BaseRequest {

    @Inject
    private UserService userService;

    public UserDetailRequest(Context context) {
        super(UserDetail.UserDetailModel.class,context);
    }

    @Override
    public UserDetail.UserDetailModel run() {
        return userService.getUser();
    }

    /**
     * This method generates a unique cache key for this request. In this case
     * our cache key depends just on the keyword.
     * @return
     */
    public String createCacheKey() {
        return "user.detail"+System.currentTimeMillis();
    }

	@Override
	public int compareTo(Object another) {
		// TODO Auto-generated method stub
		return 0;
	}
}
