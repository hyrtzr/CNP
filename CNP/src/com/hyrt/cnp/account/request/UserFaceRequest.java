package com.hyrt.cnp.account.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.UserService;

import java.io.File;

/**
 * Created by yepeng on 14-1-3.
 */
public class UserFaceRequest extends BaseRequest {

    @Inject
    private UserService userService;

    private File faceFile;

    public UserFaceRequest(Context context,File faceFile) {
        super(BaseTest.class,context);
        this.faceFile = faceFile;
    }

    @Override
    public BaseTest run() {
        return userService.modifyUserFace(faceFile);
    }

    /**
     * This method generates a unique cache key for this request. In this case
     * our cache key depends just on the keyword.
     * @return
     */
    public String createCacheKey() {
        return "user.face";
    }

	@Override
	public int compareTo(Object another) {
		// TODO Auto-generated method stub
		return 0;
	}
}
