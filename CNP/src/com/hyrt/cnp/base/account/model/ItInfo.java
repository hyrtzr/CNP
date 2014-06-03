package com.hyrt.cnp.base.account.model;

import com.hyrt.cnp.base.account.utils.FaceUtils;
import com.hyrt.cnp.base.account.utils.StringUtils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

/**
 * Created by GYH on 2014/4/6.
 */
public class ItInfo implements Serializable {
    private int fromUid;
    private String fromName;
    private int toUid;
    private String toName;
    private int msgType;
    private String msgTitle;
    private String msgData;
    private String msgUrl;
    private int status;
    private String ptime;
    private String dContent;
    private int tUserId;
    private String tUserName;
    private String tContent;
    private ArrayList<String> sPicAry;
    private ArrayList<String> bPicAry;
    private String key;

    public static class Model extends Base{
        private static final long serialVersionUID = -1;
        private ArrayList<ItInfo> data;
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

        public ArrayList<ItInfo> getData() {
            return data;
        }

        public void setData(ArrayList<ItInfo> data) {
            this.data = data;
        }

        public String getMore() {
            return more;
        }

        public void setMore(String more) {
            this.more = more;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItInfo itInfo = (ItInfo) o;

        if (fromUid != itInfo.fromUid) return false;
        if (msgType != itInfo.msgType) return false;
        if (status != itInfo.status) return false;
        if (tUserId != itInfo.tUserId) return false;
        if (toUid != itInfo.toUid) return false;
        if (bPicAry != null ? !bPicAry.equals(itInfo.bPicAry) : itInfo.bPicAry != null)
            return false;
        if (dContent != null ? !dContent.equals(itInfo.dContent) : itInfo.dContent != null)
            return false;
        if (fromName != null ? !fromName.equals(itInfo.fromName) : itInfo.fromName != null)
            return false;
        if (key != null ? !key.equals(itInfo.key) : itInfo.key != null) return false;
        if (msgData != null ? !msgData.equals(itInfo.msgData) : itInfo.msgData != null)
            return false;
        if (msgTitle != null ? !msgTitle.equals(itInfo.msgTitle) : itInfo.msgTitle != null)
            return false;
        if (msgUrl != null ? !msgUrl.equals(itInfo.msgUrl) : itInfo.msgUrl != null) return false;
        if (ptime != null ? !ptime.equals(itInfo.ptime) : itInfo.ptime != null) return false;
        if (sPicAry != null ? !sPicAry.equals(itInfo.sPicAry) : itInfo.sPicAry != null)
            return false;
        if (tContent != null ? !tContent.equals(itInfo.tContent) : itInfo.tContent != null)
            return false;
        if (tUserName != null ? !tUserName.equals(itInfo.tUserName) : itInfo.tUserName != null)
            return false;
        if (toName != null ? !toName.equals(itInfo.toName) : itInfo.toName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fromUid;
        result = 31 * result + (fromName != null ? fromName.hashCode() : 0);
        result = 31 * result + toUid;
        result = 31 * result + (toName != null ? toName.hashCode() : 0);
        result = 31 * result + msgType;
        result = 31 * result + (msgTitle != null ? msgTitle.hashCode() : 0);
        result = 31 * result + (msgData != null ? msgData.hashCode() : 0);
        result = 31 * result + (msgUrl != null ? msgUrl.hashCode() : 0);
        result = 31 * result + status;
        result = 31 * result + (ptime != null ? ptime.hashCode() : 0);
        result = 31 * result + (dContent != null ? dContent.hashCode() : 0);
        result = 31 * result + tUserId;
        result = 31 * result + (tUserName != null ? tUserName.hashCode() : 0);
        result = 31 * result + (tContent != null ? tContent.hashCode() : 0);
        result = 31 * result + (sPicAry != null ? sPicAry.hashCode() : 0);
        result = 31 * result + (bPicAry != null ? bPicAry.hashCode() : 0);
        result = 31 * result + (key != null ? key.hashCode() : 0);
        return result;
    }

    public String getUserphoto(){
        return FaceUtils.getAvatar(fromUid, FaceUtils.FACE_BIG);
    }

    public String getPosttime3() {
        try {
            return StringUtils.friendly_time(StringUtils.millTimeToNormalTime(ptime));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getPosttime2() {
        try {
            return StringUtils.millTimeToNormalTime2(ptime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public int getFromUid() {
        return fromUid;
    }

    public void setFromUid(int fromUid) {
        this.fromUid = fromUid;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public int getToUid() {
        return toUid;
    }

    public void setToUid(int toUid) {
        this.toUid = toUid;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgData() {
        String result = msgData;
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

    public void setMsgData(String msgData) {
        this.msgData = msgData;
    }

    public String getMsgUrl() {
        return msgUrl;
    }

    public void setMsgUrl(String msgUrl) {
        this.msgUrl = msgUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getdContent() {
        String result = dContent;
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

    public ArrayList<String> getsPicAry() {
        return sPicAry;
    }

    public void setsPicAry(ArrayList<String> sPicAry) {
        this.sPicAry = sPicAry;
    }

    public ArrayList<String> getbPicAry() {
        return bPicAry;
    }

    public void setbPicAry(ArrayList<String> bPicAry) {
        this.bPicAry = bPicAry;
    }
}
