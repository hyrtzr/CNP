package com.hyrt.cnp.base.account.model;


import com.hyrt.cnp.base.account.utils.FaceUtils;
import com.hyrt.cnp.base.account.utils.StringUtils;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

/**
 * Created by GYH on 14-1-5.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Album implements Serializable {
    private int paId;
    private String albumName;
    private int classroomId;
    private String classroomName;
    private String aType;
    private String pkind;
    private String albumDesc;
    private String posttime;
    private int photo_ID;
    private String photoNums;

    private int nurseryId;
    private String nurseryName;
    private int user_id;
    private String authShell;
    private String thpath;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;

        if (classroomId != album.classroomId) return false;
        if (nurseryId != album.nurseryId) return false;
        if (paId != album.paId) return false;
        if (photo_ID != album.photo_ID) return false;
        if (user_id != album.user_id) return false;
        if (aType != null ? !aType.equals(album.aType) : album.aType != null) return false;
        if (albumDesc != null ? !albumDesc.equals(album.albumDesc) : album.albumDesc != null)
            return false;
        if (albumName != null ? !albumName.equals(album.albumName) : album.albumName != null)
            return false;
        if (authShell != null ? !authShell.equals(album.authShell) : album.authShell != null)
            return false;
        if (classroomName != null ? !classroomName.equals(album.classroomName) : album.classroomName != null)
            return false;
        if (nurseryName != null ? !nurseryName.equals(album.nurseryName) : album.nurseryName != null)
            return false;
        if (photoNums != null ? !photoNums.equals(album.photoNums) : album.photoNums != null)
            return false;
        if (pkind != null ? !pkind.equals(album.pkind) : album.pkind != null) return false;
        if (posttime != null ? !posttime.equals(album.posttime) : album.posttime != null)
            return false;
        if (thpath != null ? !thpath.equals(album.thpath) : album.thpath != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = paId;
        result = 31 * result + (albumName != null ? albumName.hashCode() : 0);
        result = 31 * result + classroomId;
        result = 31 * result + (classroomName != null ? classroomName.hashCode() : 0);
        result = 31 * result + (aType != null ? aType.hashCode() : 0);
        result = 31 * result + (pkind != null ? pkind.hashCode() : 0);
        result = 31 * result + (albumDesc != null ? albumDesc.hashCode() : 0);
        result = 31 * result + (posttime != null ? posttime.hashCode() : 0);
        result = 31 * result + photo_ID;
        result = 31 * result + (photoNums != null ? photoNums.hashCode() : 0);
        result = 31 * result + nurseryId;
        result = 31 * result + (nurseryName != null ? nurseryName.hashCode() : 0);
        result = 31 * result + user_id;
        result = 31 * result + (authShell != null ? authShell.hashCode() : 0);
        result = 31 * result + (thpath != null ? thpath.hashCode() : 0);
        return result;
    }

    public int getNurseryId() {
        return nurseryId;
    }

    public void setNurseryId(int nurseryId) {
        this.nurseryId = nurseryId;
    }

    public String getNurseryName() {
        return nurseryName;
    }

    public void setNurseryName(String nurseryName) {
        this.nurseryName = nurseryName;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAuthShell() {
        return authShell;
    }

    public void setAuthShell(String authShell) {
        this.authShell = authShell;
    }

    public String getThpath() {
        return thpath;
    }

    public void setThpath(String thpath) {
        this.thpath = thpath;
    }

    public String getImagepath(){
       return FaceUtils.getClassroomAblumImage(paId,FaceUtils.FACE_BIG);
    }
    
    public String getImagepath2(){
       return "http://img.chinaxueqian.com/"+thpath;
   }

    public static class Model extends Base{
        private static final long serialVersionUID = -1;

        private ArrayList<Album> data;
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

        public ArrayList<Album> getData() {
            return data;
        }

        public void setData(ArrayList<Album> data) {
            this.data = data;
        }

        public String getMore() {
            return more;
        }

        public void setMore(String more) {
            this.more = more;
        }
    }

    public int getPaId() {
        return paId;
    }

    public void setPaId(int paId) {
        this.paId = paId;
    }

    public String getAlbumName() {
        String result = albumName;
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

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getaType() {
        return aType;
    }

    public void setaType(String aType) {
        this.aType = aType;
    }

    public String getPkind() {
        return pkind;
    }

    public void setPkind(String pkind) {
        this.pkind = pkind;
    }

    public String getAlbumDesc() {
        return "照片描述："+albumDesc;
    }

    public String getSimpleAlbumDesc(){
        return albumDesc;
    }

    public void setAlbumDesc(String albumDesc) {
        this.albumDesc = albumDesc;
    }

    public String getPosttime() {
        return posttime;
    }

    public String getPosttime2(){
        try {
            return "时间："+StringUtils.millTimeToNormalTime2(posttime + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void setPosttime(String posttime) {
        this.posttime = posttime;
    }

    public int getPhoto_ID() {
        return photo_ID;
    }

    public void setPhoto_ID(int photo_ID) {
        this.photo_ID = photo_ID;
    }

    public String getPhotoNums() {
        return photoNums;
    }

    public void setPhotoNums(String photoNums) {
        this.photoNums = photoNums;
    }
}
