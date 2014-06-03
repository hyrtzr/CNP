package com.hyrt.cnp.base.account.model;


import com.hyrt.cnp.base.account.utils.FaceUtils;
import com.hyrt.cnp.base.account.utils.StringUtils;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by GYH on 14-1-7.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment implements Serializable{
    private String _id;
    private String infoID;
    private String infoTitle;
    private String infoUserId;
    private String infoNurseryId;
    private String infoClassroomId;
    private String siteid;
    private String url;
    private int userid;
    private String username;
    private String nursery_id;
    private String nursery_name;
    private String userGroup;
    private String creatdate;
    private String ip;
    private String lstatus;
    private String content;
    private String reply;
    private String recontent;
    private String reuserId;
    private String reusername;
    private String redate;
    private String infoid2;

    public void setInfoID(String infoID) {
        this.infoID = infoID;
    }

    public String getInfoID() {
        return infoID;
    }

    public String getInfoUserId() {
        return infoUserId;
    }

    public void setInfoUserId(String infoUserId) {
        this.infoUserId = infoUserId;
    }

    public String getInfoNurseryId() {
        return infoNurseryId;
    }

    public void setInfoNurseryId(String infoNurseryId) {
        this.infoNurseryId = infoNurseryId;
    }

    public String getInfoClassroomId() {
        return infoClassroomId;
    }

    public void setInfoClassroomId(String infoClassroomId) {
        this.infoClassroomId = infoClassroomId;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getNursery_id() {
        return nursery_id;
    }

    public void setNursery_id(String nursery_id) {
        this.nursery_id = nursery_id;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (userid != comment.userid) return false;
        if (_id != null ? !_id.equals(comment._id) : comment._id != null) return false;
        if (content != null ? !content.equals(comment.content) : comment.content != null)
            return false;
        if (creatdate != null ? !creatdate.equals(comment.creatdate) : comment.creatdate != null)
            return false;
        if (infoClassroomId != null ? !infoClassroomId.equals(comment.infoClassroomId) : comment.infoClassroomId != null)
            return false;
        if (infoID != null ? !infoID.equals(comment.infoID) : comment.infoID != null) return false;
        if (infoNurseryId != null ? !infoNurseryId.equals(comment.infoNurseryId) : comment.infoNurseryId != null)
            return false;
        if (infoTitle != null ? !infoTitle.equals(comment.infoTitle) : comment.infoTitle != null)
            return false;
        if (infoUserId != null ? !infoUserId.equals(comment.infoUserId) : comment.infoUserId != null)
            return false;
        if (infoid2 != null ? !infoid2.equals(comment.infoid2) : comment.infoid2 != null)
            return false;
        if (ip != null ? !ip.equals(comment.ip) : comment.ip != null) return false;
        if (lstatus != null ? !lstatus.equals(comment.lstatus) : comment.lstatus != null)
            return false;
        if (nursery_id != null ? !nursery_id.equals(comment.nursery_id) : comment.nursery_id != null)
            return false;
        if (nursery_name != null ? !nursery_name.equals(comment.nursery_name) : comment.nursery_name != null)
            return false;
        if (recontent != null ? !recontent.equals(comment.recontent) : comment.recontent != null)
            return false;
        if (redate != null ? !redate.equals(comment.redate) : comment.redate != null) return false;
        if (reply != null ? !reply.equals(comment.reply) : comment.reply != null) return false;
        if (reuserId != null ? !reuserId.equals(comment.reuserId) : comment.reuserId != null)
            return false;
        if (reusername != null ? !reusername.equals(comment.reusername) : comment.reusername != null)
            return false;
        if (siteid != null ? !siteid.equals(comment.siteid) : comment.siteid != null) return false;
        if (url != null ? !url.equals(comment.url) : comment.url != null) return false;
        if (userGroup != null ? !userGroup.equals(comment.userGroup) : comment.userGroup != null)
            return false;
        if (username != null ? !username.equals(comment.username) : comment.username != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (infoID != null ? infoID.hashCode() : 0);
        result = 31 * result + (infoTitle != null ? infoTitle.hashCode() : 0);
        result = 31 * result + (infoUserId != null ? infoUserId.hashCode() : 0);
        result = 31 * result + (infoNurseryId != null ? infoNurseryId.hashCode() : 0);
        result = 31 * result + (infoClassroomId != null ? infoClassroomId.hashCode() : 0);
        result = 31 * result + (siteid != null ? siteid.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + userid;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (nursery_id != null ? nursery_id.hashCode() : 0);
        result = 31 * result + (nursery_name != null ? nursery_name.hashCode() : 0);
        result = 31 * result + (userGroup != null ? userGroup.hashCode() : 0);
        result = 31 * result + (creatdate != null ? creatdate.hashCode() : 0);
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (lstatus != null ? lstatus.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (reply != null ? reply.hashCode() : 0);
        result = 31 * result + (recontent != null ? recontent.hashCode() : 0);
        result = 31 * result + (reuserId != null ? reuserId.hashCode() : 0);
        result = 31 * result + (reusername != null ? reusername.hashCode() : 0);
        result = 31 * result + (redate != null ? redate.hashCode() : 0);
        result = 31 * result + (infoid2 != null ? infoid2.hashCode() : 0);
        return result;
    }

    public String getInfoid2() {
        return infoid2;
    }

    public void setInfoid2(String infoid2) {
        this.infoid2 = infoid2;
    }

    public String getphotoImage(){
        return  FaceUtils.getAvatar(userid, FaceUtils.FACE_BIG);
    }

    public static class Model extends Base{
        private static final long serialVersionUID = -1;
        private ArrayList<Comment> data;
        private String more;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            Model model = (Model) o;

            if (data != null ? !data.equals(model.data) : model.data != null) return false;
            if (more != null ? !more.equals(model.more) : model.more != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (data != null ? data.hashCode() : 0);
            result = 31 * result + (more != null ? more.hashCode() : 0);
            return result;
        }

        public String getMore() {

            return more;
        }

        public void setMore(String more) {
            this.more = more;
        }

        public ArrayList<Comment> getData() {
            return data;
        }

        public void setData(ArrayList<Comment> data) {
            this.data = data;
        }
    }

    public static class Model2 extends Base{
        private static final long serialVersionUID = -1;
        private Comment data;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            Model2 model2 = (Model2) o;

            if (data != null ? !data.equals(model2.data) : model2.data != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (data != null ? data.hashCode() : 0);
            return result;
        }

        public Comment getData() {
            return data;
        }

        public void setData(Comment data) {
            this.data = data;
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


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }



    public String getInfoTitle() {
        return infoTitle;
    }

    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle;
    }



    public String getSiteid() {
        return siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public String getNursery_name() {
        return nursery_name;
    }

    public void setNursery_name(String nursery_name) {
        this.nursery_name = nursery_name;
    }



    public String getCreatdate() {
        return creatdate;
    }
    public String getCreatdate2() {
        try {
            return StringUtils.friendly_time(StringUtils.millTimeToNormalTime(creatdate+""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setCreatdate(String creatdate) {
        this.creatdate = creatdate;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLstatus() {
        return lstatus;
    }

    public void setLstatus(String lstatus) {
        this.lstatus = lstatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getRecontent() {
        return recontent;
    }

    public void setRecontent(String recontent) {
        this.recontent = recontent;
    }

    public String getReuserId() {
        return reuserId;
    }

    public void setReuserId(String reuserId) {
        this.reuserId = reuserId;
    }

    public String getReusername() {
        return reusername;
    }

    public void setReusername(String reusername) {
        this.reusername = reusername;
    }

    public String getRedate() {
        return redate;
    }

    public String getRedate2(){
        try {
            return StringUtils.friendly_time(StringUtils.millTimeToNormalTime(redate+""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void setRedate(String redate) {
        this.redate = redate;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "_id='" + _id + '\'' +
                ", infoID='" + infoID + '\'' +
                ", infoTitle='" + infoTitle + '\'' +
                ", infoUserId='" + infoUserId + '\'' +
                ", infoNurseryId='" + infoNurseryId + '\'' +
                ", infoClassroomId='" + infoClassroomId + '\'' +
                ", siteid='" + siteid + '\'' +
                ", url='" + url + '\'' +
                ", userid=" + userid +
                ", username='" + username + '\'' +
                ", nursery_id='" + nursery_id + '\'' +
                ", nursery_name='" + nursery_name + '\'' +
                ", userGroup='" + userGroup + '\'' +
                ", creatdate='" + creatdate + '\'' +
                ", ip='" + ip + '\'' +
                ", lstatus='" + lstatus + '\'' +
                ", content='" + content + '\'' +
                ", reply='" + reply + '\'' +
                ", recontent='" + recontent + '\'' +
                ", reuserId='" + reuserId + '\'' +
                ", reusername='" + reusername + '\'' +
                ", redate='" + redate + '\'' +
                ", infoid2='" + infoid2 + '\'' +
                '}';
    }
}
