package com.hyrt.cnp.base.account.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by GYH on 14-1-3.
 */
public class Photo implements Serializable {

    private int photoID;
    private int paId;
    private String pics;
    private String thpath;
    private String title;
    private String introduce;
    private int isFocus;
    private int pkind;
    //用户id
    private int userID;
    //幼儿园id
    private int nurseryID;
    //班级id
    private int classroomID;
    private int commentNum;

    /**
     * 获取缩略图
     * @return
     */
    public String getImagethpath(){
        String str= "http://img.chinaxueqian.com/"+thpath;
        return str;
    }

    /**
     * 获取大图
     * @return
     */
    public String getImagepics(){
        String str= "http://img.chinaxueqian.com/"+pics;
        return str;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getNurseryID() {
        return nurseryID;
    }

    public void setNurseryID(int nurseryID) {
        this.nurseryID = nurseryID;
    }

    public int getClassroomID() {
        return classroomID;
    }

    public void setClassroomID(int classroomID) {
        this.classroomID = classroomID;
    }

    public int getPhotoID() {
        return photoID;
    }

    public void setPhotoID(int photoID) {
        this.photoID = photoID;
    }

    public int getPaId() {
        return paId;
    }

    public void setPaId(int paId) {
        this.paId = paId;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getThpath() {
        return thpath;
    }

    public void setThpath(String thpath) {
        this.thpath = thpath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getIsFocus() {
        return isFocus;
    }

    public void setIsFocus(int isFocus) {
        this.isFocus = isFocus;
    }

    public int getPkind() {
        return pkind;
    }

    public void setPkind(int pkind) {
        this.pkind = pkind;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public static class Model extends Base {

        private ArrayList<Photo> data;
        private String more;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Model model = (Model) o;

            if (data != null ? !data.equals(model.data) : model.data != null) return false;
            if (more != null ? !more.equals(model.more) : model.more != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = data != null ? data.hashCode() : 0;
            result = 31 * result + (more != null ? more.hashCode() : 0);
            return result;
        }

        public ArrayList<Photo> getData() {
            return data;
        }

        public void setData(ArrayList<Photo> data) {
            this.data = data;
        }

        public String getMore() {
            return more;
        }

        public void setMore(String more) {
            this.more = more;
        }
    }
}
