package com.hyrt.cnp.base.account.model;


import com.hyrt.cnp.base.account.utils.FaceUtils;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by GYH on 14-1-4.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StarBabay {
    private int user_id;
    private String renname;
    private int nursery_id;
    public String getImagepath(){
        return  FaceUtils.getAvatar(user_id, FaceUtils.FACE_BIG);
    }
    public static class Model extends Base{
        private static final long serialVersionUID = -1;
        private ArrayList<StarBabay> data;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            Model model = (Model) o;

            if (data != null ? !data.equals(model.data) : model.data != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (data != null ? data.hashCode() : 0);
            return result;
        }

        public ArrayList<StarBabay> getData() {
            return data;
        }

        public void setData(ArrayList<StarBabay> data) {
            this.data = data;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StarBabay babay = (StarBabay) o;

        if (nursery_id != babay.nursery_id) return false;
        if (user_id != babay.user_id) return false;
        if (renname != null ? !renname.equals(babay.renname) : babay.renname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = user_id;
        result = 31 * result + (renname != null ? renname.hashCode() : 0);
        result = 31 * result + nursery_id;
        return result;
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

    public int getNursery_id() {
        return nursery_id;
    }

    public void setNursery_id(int nursery_id) {
        this.nursery_id = nursery_id;
    }
}
