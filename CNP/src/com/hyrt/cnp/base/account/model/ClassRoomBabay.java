package com.hyrt.cnp.base.account.model;


import com.hyrt.cnp.base.account.utils.FaceUtils;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by GYH on 14-1-6.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClassRoomBabay implements Serializable {
    private int user_id;
    private String renname;
    private String logo;
    private String loginnum;

    public String getLogopath(){
        return FaceUtils.getAvatar(user_id,FaceUtils.FACE_BIG);
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getRenname() {
        return renname;
    }

    public void setRenname(String renname) {
        this.renname = renname;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLoginnum() {
        return loginnum;
    }

    public void setLoginnum(String loginnum) {
        this.loginnum = loginnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassRoomBabay that = (ClassRoomBabay) o;

        if (user_id != that.user_id) return false;
        if (loginnum != null ? !loginnum.equals(that.loginnum) : that.loginnum != null)
            return false;
        if (logo != null ? !logo.equals(that.logo) : that.logo != null) return false;
        if (renname != null ? !renname.equals(that.renname) : that.renname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = user_id;
        result = 31 * result + (renname != null ? renname.hashCode() : 0);
        result = 31 * result + (logo != null ? logo.hashCode() : 0);
        result = 31 * result + (loginnum != null ? loginnum.hashCode() : 0);
        return result;
    }

    public static class Model extends Base{
        private static final long serialVersionUID = -1;

        private ArrayList<ClassRoomBabay> data;
//        private String more;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Model model = (Model) o;

            if (data != null ? !data.equals(model.data) : model.data != null) return false;
//            if (more != null ? !more.equals(model.more) : model.more != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = data != null ? data.hashCode() : 0;
//            result = 31 * result + (more != null ? more.hashCode() : 0);
            return result;
        }

//        public String getMore() {
//            return more;
//        }
//
//        public void setMore(String more) {
//            this.more = more;
//        }

        public ArrayList<ClassRoomBabay> getData() {
            return data;
        }

        public void setData(ArrayList<ClassRoomBabay> data) {
            this.data = data;
        }
    }
}
