package com.hyrt.cnp.base.account.utils;

import com.hyrt.cnp.R;

import net.oschina.app.AppContext;

/**
 * 计算头像的路径
 * Created by yepeng on 14-1-14.
 */
public class FaceUtils {

    //图片类型
    public static final String FACE_SMALL = "small";
    public static final String FACE_BIG = "big";
    public static final String FACE_MIDDLE = "middle";
    public static final String FACE_BG = "bg";

    /**
     *
     * @param uid 用户id
     * @param type 图片类型
     * @return
     */
    public static String getAvatar(int uid,String type){
        StringBuilder basePath = new StringBuilder(AppContext.getInstance().getString(R.string.path_user_face));
        Float part1 = (float)uid/(float)10000;
        Float part2 = (float)uid%(float)10000/(float)1000;
        Float uppart1 = Float.valueOf((part1.intValue() + 1));
        Float uppart2 = Float.valueOf((part1.intValue() + 1));
        return basePath.append(Float.compare(part1+Float.valueOf(1),uppart1) == 0 ? part1.intValue() : part1.intValue()+1).append("/")
                .append(Float.compare(part2+Float.valueOf(1),uppart2) == 0 ? part2.intValue() : part2.intValue()+1)
                .append("/").append(uid).append("_").append(type).append(".jpg").toString();

    }

    /**
     *
     * @param classroomid 班级id
     * @param type 图片类型
     * @return
     */
    public static String getClassRoomImage(int classroomid,String type){
        StringBuilder basePath = new StringBuilder(AppContext.getInstance().getString(R.string.path_classroom_banner));
        Float part1 = (float)classroomid/(float)10000;
        Float uppart1 = Float.valueOf((part1.intValue() + 1));
        return basePath.append(Float.compare(part1+Float.valueOf(1),uppart1) == 0 ? part1.intValue() : part1.intValue()+1).append("/")
                .append("/").append(classroomid).append("_").append(type).append(".jpg").toString();

    }

    /**
     *
     * @param nid 学校id
     * @param type 图片类型
     * @return
     */
    public static String getSchoolImage(int nid,String type){
        StringBuilder basePath = new StringBuilder(AppContext.getInstance().getString(R.string.path_school_banner));
        Float part1 = (float)nid/(float)1000;
        Float uppart1 = Float.valueOf((part1.intValue() + 1));
        return basePath.append(Float.compare(part1+Float.valueOf(1),uppart1) == 0 ?part1.intValue() : part1.intValue()+1).append("/")
                .append("/").append(nid).append("_").append(type).append(".jpg").toString();

    }

    /**
     *
     * @param nid 学校id
     * @return
     */
    public static String getSchoolImage(int nid){
        StringBuilder basePath = new StringBuilder(AppContext.getInstance().getString(R.string.path_school_banner));
        Float part1 = (float)nid/(float)1000;
        Float uppart1 = Float.valueOf((part1.intValue() + 1));
        return basePath.append(Float.compare(part1 + Float.valueOf(1), uppart1) == 0 ? part1.intValue() : part1.intValue() + 1).append("/")
                .append("/").append(nid).append(".jpg").toString();

    }

    /**
     *
     * @param photoid 相册id
     * @param type 图片类型
     * @return
     */
    public static String getClassroomAblumImage(int photoid,String type){
        StringBuilder basePath = new StringBuilder(AppContext.getInstance().getString(R.string.path_album_img));
        Float part1 = (float)photoid/(float)10000;
        Float uppart1 = Float.valueOf((part1.intValue() + 1));
        String imagepath=basePath.append("/").append(Float.compare(part1+Float.valueOf(1),uppart1) == 0 ? part1.intValue() : part1.intValue()+1).append("/")
                .append("/").append(photoid).append(".jpg").toString();
        return imagepath;

    }

    public static String getClassroomBanner(int classroomid){
        return AppContext.getInstance().getString(R.string.path_classroom_banner)+"1/"+classroomid+"_banner.jpg";
    }

    public static String getImage(String photoUrl){
        return "http://img.chinaxueqian.com/"+photoUrl;
    }
}
