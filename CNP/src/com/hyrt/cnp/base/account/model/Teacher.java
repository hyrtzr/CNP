package com.hyrt.cnp.base.account.model;


import com.hyrt.cnp.base.account.utils.FaceUtils;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by GYH on 14-1-4.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Teacher implements Serializable {

    private static final long serialVersionUID = -1;

    private int user_id;
    private String username;
    private String renname;
    private int groupID;
    private int nursery_id;
    private String nurseryName;
    private int classroom;
    private String roomName;
    private String email;
    private String province;
    private String city;
    private String district;
    private String address;
    private String sex;
    private String birthday;
    private String mobile;
    private String jobdate;
    private String hometown;
    private String ethnic;
    private String qq;
    private String telephone;
    private String elderName;
    private String nexus;
    private String political;
    private String career;
    private String intro;
    private String skills;
    private String specialty;
    private String certificateNo;
    private String jobTitle;
    private String formation;
    private String nationality;
    private String bloodType;
    private String degree;
    private String workAdd;
    private String favorites;
    private String funk;
    private String likeFood;
    private String allergy;
    private String timagePath;

    public String getImagepath(){
        return  FaceUtils.getAvatar(user_id, FaceUtils.FACE_BIG);
    }

    public static class Model extends Base{
        private static final long serialVersionUID = -1;

        private ArrayList<Teacher> data;

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

        public ArrayList<Teacher> getData() {
            return data;
        }

        public void setData(ArrayList<Teacher> data) {
            this.data = data;
        }
    }


    public static class Model2 extends Base{
        private static final long serialVersionUID = -1;

        private Teacher data;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Model2)) return false;

            Model2 model2 = (Model2) o;

            if (data != null ? !data.equals(model2.data) : model2.data != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return data != null ? data.hashCode() : 0;
        }

        public Teacher getData() {
            return data;
        }

        public void setData(Teacher data) {
            this.data = data;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teacher teacher = (Teacher) o;

        if (classroom != teacher.classroom) return false;
        if (groupID != teacher.groupID) return false;
        if (nursery_id != teacher.nursery_id) return false;
        if (user_id != teacher.user_id) return false;
        if (address != null ? !address.equals(teacher.address) : teacher.address != null)
            return false;
        if (allergy != null ? !allergy.equals(teacher.allergy) : teacher.allergy != null)
            return false;
        if (birthday != null ? !birthday.equals(teacher.birthday) : teacher.birthday != null)
            return false;
        if (bloodType != null ? !bloodType.equals(teacher.bloodType) : teacher.bloodType != null)
            return false;
        if (career != null ? !career.equals(teacher.career) : teacher.career != null) return false;
        if (certificateNo != null ? !certificateNo.equals(teacher.certificateNo) : teacher.certificateNo != null)
            return false;
        if (city != null ? !city.equals(teacher.city) : teacher.city != null) return false;
        if (degree != null ? !degree.equals(teacher.degree) : teacher.degree != null) return false;
        if (district != null ? !district.equals(teacher.district) : teacher.district != null)
            return false;
        if (elderName != null ? !elderName.equals(teacher.elderName) : teacher.elderName != null)
            return false;
        if (email != null ? !email.equals(teacher.email) : teacher.email != null) return false;
        if (ethnic != null ? !ethnic.equals(teacher.ethnic) : teacher.ethnic != null) return false;
        if (favorites != null ? !favorites.equals(teacher.favorites) : teacher.favorites != null)
            return false;
        if (formation != null ? !formation.equals(teacher.formation) : teacher.formation != null)
            return false;
        if (funk != null ? !funk.equals(teacher.funk) : teacher.funk != null) return false;
        if (hometown != null ? !hometown.equals(teacher.hometown) : teacher.hometown != null)
            return false;
        if (intro != null ? !intro.equals(teacher.intro) : teacher.intro != null) return false;
        if (jobTitle != null ? !jobTitle.equals(teacher.jobTitle) : teacher.jobTitle != null)
            return false;
        if (jobdate != null ? !jobdate.equals(teacher.jobdate) : teacher.jobdate != null)
            return false;
        if (likeFood != null ? !likeFood.equals(teacher.likeFood) : teacher.likeFood != null)
            return false;
        if (mobile != null ? !mobile.equals(teacher.mobile) : teacher.mobile != null) return false;
        if (nationality != null ? !nationality.equals(teacher.nationality) : teacher.nationality != null)
            return false;
        if (nexus != null ? !nexus.equals(teacher.nexus) : teacher.nexus != null) return false;
        if (nurseryName != null ? !nurseryName.equals(teacher.nurseryName) : teacher.nurseryName != null)
            return false;
        if (political != null ? !political.equals(teacher.political) : teacher.political != null)
            return false;
        if (province != null ? !province.equals(teacher.province) : teacher.province != null)
            return false;
        if (qq != null ? !qq.equals(teacher.qq) : teacher.qq != null) return false;
        if (renname != null ? !renname.equals(teacher.renname) : teacher.renname != null)
            return false;
        if (roomName != null ? !roomName.equals(teacher.roomName) : teacher.roomName != null)
            return false;
        if (sex != null ? !sex.equals(teacher.sex) : teacher.sex != null) return false;
        if (skills != null ? !skills.equals(teacher.skills) : teacher.skills != null) return false;
        if (specialty != null ? !specialty.equals(teacher.specialty) : teacher.specialty != null)
            return false;
        if (telephone != null ? !telephone.equals(teacher.telephone) : teacher.telephone != null)
            return false;
        if (username != null ? !username.equals(teacher.username) : teacher.username != null)
            return false;
        if (workAdd != null ? !workAdd.equals(teacher.workAdd) : teacher.workAdd != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = user_id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (renname != null ? renname.hashCode() : 0);
        result = 31 * result + groupID;
        result = 31 * result + nursery_id;
        result = 31 * result + (nurseryName != null ? nurseryName.hashCode() : 0);
        result = 31 * result + classroom;
        result = 31 * result + (roomName != null ? roomName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (district != null ? district.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (jobdate != null ? jobdate.hashCode() : 0);
        result = 31 * result + (hometown != null ? hometown.hashCode() : 0);
        result = 31 * result + (ethnic != null ? ethnic.hashCode() : 0);
        result = 31 * result + (qq != null ? qq.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (elderName != null ? elderName.hashCode() : 0);
        result = 31 * result + (nexus != null ? nexus.hashCode() : 0);
        result = 31 * result + (political != null ? political.hashCode() : 0);
        result = 31 * result + (career != null ? career.hashCode() : 0);
        result = 31 * result + (intro != null ? intro.hashCode() : 0);
        result = 31 * result + (skills != null ? skills.hashCode() : 0);
        result = 31 * result + (specialty != null ? specialty.hashCode() : 0);
        result = 31 * result + (certificateNo != null ? certificateNo.hashCode() : 0);
        result = 31 * result + (jobTitle != null ? jobTitle.hashCode() : 0);
        result = 31 * result + (formation != null ? formation.hashCode() : 0);
        result = 31 * result + (nationality != null ? nationality.hashCode() : 0);
        result = 31 * result + (bloodType != null ? bloodType.hashCode() : 0);
        result = 31 * result + (degree != null ? degree.hashCode() : 0);
        result = 31 * result + (workAdd != null ? workAdd.hashCode() : 0);
        result = 31 * result + (favorites != null ? favorites.hashCode() : 0);
        result = 31 * result + (funk != null ? funk.hashCode() : 0);
        result = 31 * result + (likeFood != null ? likeFood.hashCode() : 0);
        result = 31 * result + (allergy != null ? allergy.hashCode() : 0);
        return result;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRenname() {
        return renname;
    }

    public void setRenname(String renname) {
        this.renname = renname;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getJobdate() {
        return jobdate;
    }

    public void setJobdate(String jobdate) {
        this.jobdate = jobdate;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getEthnic() {
        return ethnic;
    }

    public void setEthnic(String ethnic) {
        this.ethnic = ethnic;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getElderName() {
        return elderName;
    }

    public void setElderName(String elderName) {
        this.elderName = elderName;
    }

    public String getNexus() {
        return nexus;
    }

    public void setNexus(String nexus) {
        this.nexus = nexus;
    }

    public String getPolitical() {
        return political;
    }

    public void setPolitical(String political) {
        this.political = political;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getWorkAdd() {
        return workAdd;
    }

    public void setWorkAdd(String workAdd) {
        this.workAdd = workAdd;
    }

    public String getFavorites() {
        return favorites;
    }

    public void setFavorites(String favorites) {
        this.favorites = favorites;
    }

    public String getFunk() {
        return funk;
    }

    public void setFunk(String funk) {
        this.funk = funk;
    }

    public String getLikeFood() {
        return likeFood;
    }

    public void setLikeFood(String likeFood) {
        this.likeFood = likeFood;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }
}
