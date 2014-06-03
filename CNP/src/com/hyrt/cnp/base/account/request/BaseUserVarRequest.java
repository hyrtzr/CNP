package com.hyrt.cnp.base.account.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.UserDetail;
import com.hyrt.cnp.base.account.model.UtilVar;
import com.hyrt.cnp.base.account.service.NotNeedLoginService;
import com.hyrt.cnp.base.account.service.UserService;

/**
 * Created by yepeng on 14-1-3.
 */
public class BaseUserVarRequest extends NotNeedLoginBaseRequest {

//    @Inject
//    private UserService userService;
    @Inject
    private NotNeedLoginService notNeedLoginService;

    private String name;
    private int province;
    private String provinceName;
    private int type = 0;
    public BaseUserVarRequest(Context context, String name) {
        super(UserDetail.UserDetailModel.class,context);
        this.name=name;
        type = 0;
    }

    public BaseUserVarRequest(Context context, String name, String provinceName) {
        super(UserDetail.UserDetailModel.class,context);
        this.name=name;
        this.provinceName = provinceName;
        type = 1;
    }

    public BaseUserVarRequest(Context context, String name, int province) {
        super(UserDetail.UserDetailModel.class,context);
        this.name=name;
        this.province = province;
        type = 2;
    }

    @Override
    public UtilVar run() {
        if(type == 1){
            return notNeedLoginService.getUtilvar(name, provinceName);
        }else if(type == 2){
            return notNeedLoginService.getUtilvar(name, province);
        }else{
            return notNeedLoginService.getUtilvar(name);
        }

    }

    /**
     * This method generates a unique cache key for this request. In this case
     * our cache key depends just on the keyword.
     * @return
     */
    public String createCacheKey() {
        return "uservar"+name;
    }

	@Override
	public int compareTo(Object another) {
		// TODO Auto-generated method stub
		return 0;
	}
}
