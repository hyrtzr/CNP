package com.hyrt.cnp.base.account.model;

import java.io.Serializable;

/**
 * Created by yepeng on 13-12-11.
 */
public class User implements Serializable{

    private static final long serialVersionUID = -1;

    //用户令牌
    private String token;
    //用户uid
    private String uuid;
    //用户id
    private String user_id;
    //用户登陆名
    private String username;
    //用户姓名
    private String renname;
    //身份组 (2=园长、4=教师、7=家长)
    private String groupID;
    //所在幼儿园id
    private String nursery_id;
    //所在幼儿园名称
    private String nurseryName;
    //班级ID
    private String classroom;
    //班级名称
    private String roomName;
    //出生日期
    private String birthday;
    //是否有站内信 (0=否、1=是)
    private String message;
    //最后登录时间
    private String lastdate;
    //登录次数
    private String loginnum;
    //用户状态(Y=可用,N=禁用,O=退学,G=毕业,D=删除)
    private String nstatus;
    //用户登陆密码
    private String password;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRenname() {
        return renname;
    }

    public void setRenname(String renname) {
        this.renname = renname;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getNursery_id() {
        return nursery_id;
    }

    public void setNursery_id(String nursery_id) {
        this.nursery_id = nursery_id;
    }

    public String getNurseryName() {
        return nurseryName;
    }

    public void setNurseryName(String nurseryName) {
        this.nurseryName = nurseryName;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLastdate() {
        return lastdate;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }

    public String getLoginnum() {
        return loginnum;
    }

    public void setLoginnum(String loginnum) {
        this.loginnum = loginnum;
    }

    public String getNstatus() {
        return nstatus;
    }

    public void setNstatus(String nstatus) {
        this.nstatus = nstatus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (birthday != null ? !birthday.equals(user.birthday) : user.birthday != null)
            return false;
        if (classroom != null ? !classroom.equals(user.classroom) : user.classroom != null)
            return false;
        if (groupID != null ? !groupID.equals(user.groupID) : user.groupID != null) return false;
        if (lastdate != null ? !lastdate.equals(user.lastdate) : user.lastdate != null)
            return false;
        if (loginnum != null ? !loginnum.equals(user.loginnum) : user.loginnum != null)
            return false;
        if (message != null ? !message.equals(user.message) : user.message != null) return false;
        if (nstatus != null ? !nstatus.equals(user.nstatus) : user.nstatus != null) return false;
        if (nurseryName != null ? !nurseryName.equals(user.nurseryName) : user.nurseryName != null)
            return false;
        if (nursery_id != null ? !nursery_id.equals(user.nursery_id) : user.nursery_id != null)
            return false;
        if (password != null ? !password.equals(user.password) : user.password != null)
            return false;
        if (renname != null ? !renname.equals(user.renname) : user.renname != null) return false;
        if (roomName != null ? !roomName.equals(user.roomName) : user.roomName != null)
            return false;
        if (token != null ? !token.equals(user.token) : user.token != null) return false;
        if (user_id != null ? !user_id.equals(user.user_id) : user.user_id != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null)
            return false;
        if (uuid != null ? !uuid.equals(user.uuid) : user.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = token != null ? token.hashCode() : 0;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (user_id != null ? user_id.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (renname != null ? renname.hashCode() : 0);
        result = 31 * result + (groupID != null ? groupID.hashCode() : 0);
        result = 31 * result + (nursery_id != null ? nursery_id.hashCode() : 0);
        result = 31 * result + (nurseryName != null ? nurseryName.hashCode() : 0);
        result = 31 * result + (classroom != null ? classroom.hashCode() : 0);
        result = 31 * result + (roomName != null ? roomName.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (lastdate != null ? lastdate.hashCode() : 0);
        result = 31 * result + (loginnum != null ? loginnum.hashCode() : 0);
        result = 31 * result + (nstatus != null ? nstatus.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    public static class UserModel extends Base{

        private static final long serialVersionUID = -1;

        private User data;

        public User getData() {
            return data;
        }

        public void setData(User data) {
            this.data = data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UserModel)) return false;

            UserModel userModel = (UserModel) o;

            if (!data.equals(userModel.data)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return data.hashCode();
        }
    }

}
