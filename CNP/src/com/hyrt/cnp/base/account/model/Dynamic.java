package com.hyrt.cnp.base.account.model;


import android.text.SpannableString;

import com.hyrt.cnp.base.account.utils.FaceUtils;
import com.hyrt.cnp.base.account.utils.StringUtils;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by GYH on 14-1-21.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dynamic implements Serializable {
    private String tranId;
    private int tranType;
    private String dContent;
    private int tUserId;
    private String tUserName;
    private String tContent;
    private String diaryTitle;
    private int picGroupId;
    private String photoname;
    private String _id;
    private int dataType;
    private ArrayList<String> infoId;
    private int userId;
    private int groupId;
    private String userName;
    private int nueseryId;
    private int classRoomId;
    private String title;
    private String content;
    private int review;
    private int transmit;
    private int isTran;
    private String posttime;
    private String ip;
    private String statusType;
    private String nurseryName;
    private String className;
    private ArrayList<String> sPicAry;
    private ArrayList<String> bPicAry;

    public String getType(){
        int type = dataType;
        if(isTran == 2){
            type = tranType;
        }else{
            type = dataType;
        }
        String typeStr = "";
        switch (type){
            case 1: typeStr = "来自教案";
                break;
            case 2: typeStr = "活动剪影";
                break;
            case 3: typeStr = "观察日记";
                break;
            case 4: typeStr = "宝宝作品";
                break;
            case 5: typeStr = "宝宝相册";
                break;
            case 6: typeStr = "教育随笔";
                break;
            case 11: typeStr = "家长日记";
                break;
            case 12: typeStr = "观察日记";
                break;
            case 15: typeStr = "班级相册";
                break;
            case 16: typeStr = "工作计划";
                break;
            case 17: typeStr = "班级视频";
                break;
            case 50: typeStr = "我的动态";
                break;
            case 51: typeStr = "动感相册";
                break;
            default:
                typeStr = "我的动态";
        }
        return "来自"+typeStr;
    }

    public String getUserphoto(){
        return FaceUtils.getAvatar(userId,FaceUtils.FACE_BIG);
    }

    public String getsPicAry1(){
        if(sPicAry.size()>1){
            return "http://img.chinaxueqian.com/"+sPicAry.get(1);
        }else{
            return null;
        }
    }
    public String getsPicAry2(){
        if(sPicAry.size()>2){
            return "http://img.chinaxueqian.com/"+sPicAry.get(2);
        }else{
            return null;
        }
    }
    public String getsPicAry0(){
        if(sPicAry.size()>0){
            return "http://img.chinaxueqian.com/"+sPicAry.get(0);
        }else{
            return null;
        }
    }

    public static class Model3 extends Base{
        private static final long serialVersionUID = -1;
        private String data;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            Model3 model3 = (Model3) o;

            if (data != null ? !data.equals(model3.data) : model3.data != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (data != null ? data.hashCode() : 0);
            return result;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    public static class Model extends Base{
        private static final long serialVersionUID = -1;

        private ArrayList<Dynamic> data;
        private String more;
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

        public ArrayList<Dynamic> getData() {
            return data;
        }

        public void setData(ArrayList<Dynamic> data) {
            this.data = data;
        }
        public String getMore() {
            return more;
        }

        public void setMore(String more) {
            this.more = more;
        }
    }

    public void setInfoId(ArrayList<String> infoId) {
        this.infoId = infoId;
    }

    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    public int getTranType() {
        return tranType;
    }

    public void setTranType(int tranType) {
        this.tranType = tranType;
    }

    public String getdContent() {
        return dContent;
    }

    public void setdContent(String dContent) {
        this.dContent = dContent;
    }

    public int gettUserId() {
        return tUserId;
    }

    public void settUserId(int tUserId) {
        this.tUserId = tUserId;
    }

    public String gettUserName() {
        return tUserName;
    }

    public void settUserName(String tUserName) {
        this.tUserName = tUserName;
    }

    public String gettContent() {
        String result = tContent;
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

    public void settContent(String tContent) {
        this.tContent = tContent;
    }

    public String getDiaryTitle() {
        return diaryTitle;
    }

    public void setDiaryTitle(String diaryTitle) {
        this.diaryTitle = diaryTitle;
    }

    public int getPicGroupId() {
        return picGroupId;
    }

    public void setPicGroupId(int picGroupId) {
        this.picGroupId = picGroupId;
    }

    public String getPhotoname() {
        return photoname;
    }

    public void setPhotoname(String photoname) {
        this.photoname = photoname;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getNueseryId() {
        return nueseryId;
    }

    public void setNueseryId(int nueseryId) {
        this.nueseryId = nueseryId;
    }

    public int getClassRoomId() {
        return classRoomId;
    }

    public void setClassRoomId(int classRoomId) {
        this.classRoomId = classRoomId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        String result = content;
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
    public String getContent2() {
        String result = "";
        if(isTran==1){
            result = content;
        }else{
            result = dContent;
        }
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

    public void setContent(String content) {
        this.content = content;
    }

    public int getReview() {
        return review;
    }
    public String getReview2() {
        return "("+review+")";
    }

    public void setReview(int review) {
        this.review = review;
    }

    public int getTransmit() {
        return transmit;
    }
    public String getTransmit2() {
        return "("+transmit+")";
    }

    public void setTransmit(int transmit) {
        this.transmit = transmit;
    }

    public int getIsTran() {
        return isTran;
    }

    public void setIsTran(int isTran) {
        this.isTran = isTran;
    }

    public String getPosttime2() {
        try {
            return StringUtils.millTimeToNormalTime2(posttime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public String getPosttime3() {
        try {
            return StringUtils.friendly_time(StringUtils.millTimeToNormalTime(posttime));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getPosttime() {
        return posttime;
    }

    public void setPosttime(String posttime) {
        this.posttime = posttime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public String getNurseryName() {
        return nurseryName;
    }

    public void setNurseryName(String nurseryName) {
        this.nurseryName = nurseryName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ArrayList<String> getsPicAry() {
        return sPicAry;
    }

    public void setsPicAry(ArrayList<String> sPicAry) {
        this.sPicAry = sPicAry;
    }

    public ArrayList<String> getbPicAry() {
        return bPicAry;
    }

    public ArrayList<String> getbPicAry2() {
        ArrayList<String> result = new ArrayList<String>();
        for(int i=0,j=bPicAry.size(); i<j; i++){
            result.add("http://img.chinaxueqian.com/"+bPicAry.get(i));
        }
        return result;
    }

    public void setbPicAry(ArrayList<String> bPicAry) {
        this.bPicAry = bPicAry;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dynamic daynamic = (Dynamic) o;

        if (classRoomId != daynamic.classRoomId) return false;
        if (dataType != daynamic.dataType) return false;
        if (groupId != daynamic.groupId) return false;
        if (isTran != daynamic.isTran) return false;
        if (nueseryId != daynamic.nueseryId) return false;
        if (picGroupId != daynamic.picGroupId) return false;
        if (review != daynamic.review) return false;
        if (tUserId != daynamic.tUserId) return false;
        if (tranType != daynamic.tranType) return false;
        if (transmit != daynamic.transmit) return false;
        if (userId != daynamic.userId) return false;
        if (_id != null ? !_id.equals(daynamic._id) : daynamic._id != null) return false;
        if (bPicAry != null ? !bPicAry.equals(daynamic.bPicAry) : daynamic.bPicAry != null)
            return false;
        if (className != null ? !className.equals(daynamic.className) : daynamic.className != null)
            return false;
        if (content != null ? !content.equals(daynamic.content) : daynamic.content != null)
            return false;
        if (dContent != null ? !dContent.equals(daynamic.dContent) : daynamic.dContent != null)
            return false;
        if (diaryTitle != null ? !diaryTitle.equals(daynamic.diaryTitle) : daynamic.diaryTitle != null)
            return false;
        if (infoId != null ? !infoId.equals(daynamic.infoId) : daynamic.infoId != null)
            return false;
        if (ip != null ? !ip.equals(daynamic.ip) : daynamic.ip != null) return false;
        if (nurseryName != null ? !nurseryName.equals(daynamic.nurseryName) : daynamic.nurseryName != null)
            return false;
        if (photoname != null ? !photoname.equals(daynamic.photoname) : daynamic.photoname != null)
            return false;
        if (posttime != null ? !posttime.equals(daynamic.posttime) : daynamic.posttime != null)
            return false;
        if (sPicAry != null ? !sPicAry.equals(daynamic.sPicAry) : daynamic.sPicAry != null)
            return false;
        if (statusType != null ? !statusType.equals(daynamic.statusType) : daynamic.statusType != null)
            return false;
        if (tContent != null ? !tContent.equals(daynamic.tContent) : daynamic.tContent != null)
            return false;
        if (tUserName != null ? !tUserName.equals(daynamic.tUserName) : daynamic.tUserName != null)
            return false;
        if (title != null ? !title.equals(daynamic.title) : daynamic.title != null) return false;
        if (tranId != null ? !tranId.equals(daynamic.tranId) : daynamic.tranId != null)
            return false;
        if (userName != null ? !userName.equals(daynamic.userName) : daynamic.userName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tranId != null ? tranId.hashCode() : 0;
        result = 31 * result + tranType;
        result = 31 * result + (dContent != null ? dContent.hashCode() : 0);
        result = 31 * result + tUserId;
        result = 31 * result + (tUserName != null ? tUserName.hashCode() : 0);
        result = 31 * result + (tContent != null ? tContent.hashCode() : 0);
        result = 31 * result + (diaryTitle != null ? diaryTitle.hashCode() : 0);
        result = 31 * result + picGroupId;
        result = 31 * result + (photoname != null ? photoname.hashCode() : 0);
        result = 31 * result + (_id != null ? _id.hashCode() : 0);
        result = 31 * result + dataType;
        result = 31 * result + (infoId != null ? infoId.hashCode() : 0);
        result = 31 * result + userId;
        result = 31 * result + groupId;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + nueseryId;
        result = 31 * result + classRoomId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + review;
        result = 31 * result + transmit;
        result = 31 * result + isTran;
        result = 31 * result + (posttime != null ? posttime.hashCode() : 0);
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (statusType != null ? statusType.hashCode() : 0);
        result = 31 * result + (nurseryName != null ? nurseryName.hashCode() : 0);
        result = 31 * result + (className != null ? className.hashCode() : 0);
        result = 31 * result + (sPicAry != null ? sPicAry.hashCode() : 0);
        result = 31 * result + (bPicAry != null ? bPicAry.hashCode() : 0);
        return result;
    }
}
