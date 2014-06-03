package com.hyrt.cnp.base.account.model;

import java.io.Serializable;

/**
 * Created by GYH on 14-1-24.
 * 班级成员
 */
public class BabyInfo implements Serializable {

    private int user_id;
    private String renname;
    private int nursery_id;
    private String nurseryName;
    private int classroom;
    private String roomName;
    private String sex;
    private String birthday;
    private String ethnic;
    private String jobdate;
    private String bloodType;
    private String nationality;
    private String timeText;// 问候语
    private String intro;// 个性签名

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BabyInfo babyInfo = (BabyInfo) o;

        if (classroom != babyInfo.classroom) return false;
        if (nursery_id != babyInfo.nursery_id) return false;
        if (user_id != babyInfo.user_id) return false;
        if (birthday != null ? !birthday.equals(babyInfo.birthday) : babyInfo.birthday != null)
            return false;
        if (bloodType != null ? !bloodType.equals(babyInfo.bloodType) : babyInfo.bloodType != null)
            return false;
        if (ethnic != null ? !ethnic.equals(babyInfo.ethnic) : babyInfo.ethnic != null)
            return false;
        if (intro != null ? !intro.equals(babyInfo.intro) : babyInfo.intro != null) return false;
        if (jobdate != null ? !jobdate.equals(babyInfo.jobdate) : babyInfo.jobdate != null)
            return false;
        if (nationality != null ? !nationality.equals(babyInfo.nationality) : babyInfo.nationality != null)
            return false;
        if (nurseryName != null ? !nurseryName.equals(babyInfo.nurseryName) : babyInfo.nurseryName != null)
            return false;
        if (renname != null ? !renname.equals(babyInfo.renname) : babyInfo.renname != null)
            return false;
        if (roomName != null ? !roomName.equals(babyInfo.roomName) : babyInfo.roomName != null)
            return false;
        if (sex != null ? !sex.equals(babyInfo.sex) : babyInfo.sex != null) return false;
        if (timeText != null ? !timeText.equals(babyInfo.timeText) : babyInfo.timeText != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = user_id;
        result = 31 * result + (renname != null ? renname.hashCode() : 0);
        result = 31 * result + nursery_id;
        result = 31 * result + (nurseryName != null ? nurseryName.hashCode() : 0);
        result = 31 * result + classroom;
        result = 31 * result + (roomName != null ? roomName.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (ethnic != null ? ethnic.hashCode() : 0);
        result = 31 * result + (jobdate != null ? jobdate.hashCode() : 0);
        result = 31 * result + (bloodType != null ? bloodType.hashCode() : 0);
        result = 31 * result + (nationality != null ? nationality.hashCode() : 0);
        result = 31 * result + (timeText != null ? timeText.hashCode() : 0);
        result = 31 * result + (intro != null ? intro.hashCode() : 0);
        return result;
    }

    public static class Model extends Base{
        private static final long serialVersionUID = -1;

        private BabyInfo data;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Model model = (Model) o;

            if (data != null ? !data.equals(model.data) : model.data != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return data != null ? data.hashCode() : 0;
        }

        public BabyInfo getData() {
            return data;
        }

        public void setData(BabyInfo data) {
            this.data = data;
        }
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getTimeText() {
        return timeText;
    }

    public void setTimeText(String timeText) {
        this.timeText = timeText;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getJobdate() {
        return jobdate;
    }

    public void setJobdate(String jobdate) {
        this.jobdate = jobdate;
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

    public String getNurseryName() {
        return nurseryName;
    }

    public void setNurseryName(String nurseryName) {
        this.nurseryName = nurseryName;
    }

    public int getClassroom() {
        return classroom;
    }

    public void setClassroom(int classroom) {
        this.classroom = classroom;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEthnic() {
        return ethnic;
    }

    public void setEthnic(String ethnic) {
        this.ethnic = ethnic;
    }
}
