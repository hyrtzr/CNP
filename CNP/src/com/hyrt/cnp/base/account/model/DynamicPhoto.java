package com.hyrt.cnp.base.account.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

/**
 * Created by Zoe on 2014-04-12.
 */
public class DynamicPhoto implements Serializable{
    private int photoID;
    private int userID;
    private int groupID;
    private String username;
    private int nurseryID;
    private int classroomID;
    private int paId;
    private int pkind;
    private String pics;
    private String thpath;
    private String title;
    private String introduce;
    private int ptype;
    private String posttime;
    private int isFocus;
    private String baby_id;
    private String bName;
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

    public int getPhotoID() {
        return photoID;
    }

    public void setPhotoID(int photoID) {
        this.photoID = photoID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getPaId() {
        return paId;
    }

    public void setPaId(int paId) {
        this.paId = paId;
    }

    public int getPkind() {
        return pkind;
    }

    public void setPkind(int pkind) {
        this.pkind = pkind;
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
        String result = introduce;
        if(result == null){
            return "";
        }
        for(int i=0; i<4; i++){
            try{
                result = URLDecoder.decode(result, "UTF-8");
            }catch (UnsupportedEncodingException e){

            }
        }
        return result;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getPtype() {
        return ptype;
    }

    public void setPtype(int ptype) {
        this.ptype = ptype;
    }

    public String getPosttime() {
        return posttime;
    }

    public void setPosttime(String posttime) {
        this.posttime = posttime;
    }

    public int getIsFocus() {
        return isFocus;
    }

    public void setIsFocus(int isFocus) {
        this.isFocus = isFocus;
    }

    public String getBaby_id() {
        return baby_id;
    }

    public void setBaby_id(String baby_id) {
        this.baby_id = baby_id;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DynamicPhoto that = (DynamicPhoto) o;

        if (classroomID != that.classroomID) return false;
        if (commentNum != that.commentNum) return false;
        if (groupID != that.groupID) return false;
        if (isFocus != that.isFocus) return false;
        if (nurseryID != that.nurseryID) return false;
        if (paId != that.paId) return false;
        if (photoID != that.photoID) return false;
        if (pkind != that.pkind) return false;
        if (ptype != that.ptype) return false;
        if (userID != that.userID) return false;
        if (bName != null ? !bName.equals(that.bName) : that.bName != null) return false;
        if (baby_id != null ? !baby_id.equals(that.baby_id) : that.baby_id != null) return false;
        if (introduce != null ? !introduce.equals(that.introduce) : that.introduce != null)
            return false;
        if (pics != null ? !pics.equals(that.pics) : that.pics != null) return false;
        if (posttime != null ? !posttime.equals(that.posttime) : that.posttime != null)
            return false;
        if (thpath != null ? !thpath.equals(that.thpath) : that.thpath != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = photoID;
        result = 31 * result + userID;
        result = 31 * result + groupID;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + nurseryID;
        result = 31 * result + classroomID;
        result = 31 * result + paId;
        result = 31 * result + pkind;
        result = 31 * result + (pics != null ? pics.hashCode() : 0);
        result = 31 * result + (thpath != null ? thpath.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (introduce != null ? introduce.hashCode() : 0);
        result = 31 * result + ptype;
        result = 31 * result + (posttime != null ? posttime.hashCode() : 0);
        result = 31 * result + isFocus;
        result = 31 * result + (baby_id != null ? baby_id.hashCode() : 0);
        result = 31 * result + (bName != null ? bName.hashCode() : 0);
        result = 31 * result + commentNum;
        return result;
    }

    public static class Model extends Base {

        private ArrayList<DynamicPhoto> data;
        private String more;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Model)) return false;

            Model model = (Model) o;

            if (data != null ? !data.equals(model.data) : model.data != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return data != null ? data.hashCode() : 0;
        }

        public ArrayList<DynamicPhoto> getData() {
            return data;
        }

        public void setData(ArrayList<DynamicPhoto> data) {
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
