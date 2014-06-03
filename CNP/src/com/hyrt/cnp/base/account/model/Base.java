package com.hyrt.cnp.base.account.model;

import java.io.Serializable;

/**
 * Created by yepeng on 13-12-11.
 */
public class Base implements Serializable{

    private static final long serialVersionUID = -1;

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
