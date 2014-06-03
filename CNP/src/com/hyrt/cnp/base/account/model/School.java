package com.hyrt.cnp.base.account.model;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by GYH on 14-1-4.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class School {
    private int nursery_id;
    private String nName;
    private String property;
    private String nType;
    private String nCreate;
    private String province;
    private String city;
    private String district;
    private String address;
    private String logo;
    private String staffNum;
    private String tel;
    private String issignup;
    private String scale;
    private String area;
    private String dispensary;
    private String intro;
    private String message;
    private String leadId;
    private String nStatus;
    private String posttime;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Model extends Base{
        private static final long serialVersionUID = -1;
        private ArrayList<School> data;

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

        public ArrayList<School> getData() {
            return data;
        }

        public void setData(ArrayList<School> data) {
            this.data = data;
        }
    }

    public static class Model2 extends Base{
        private static final long serialVersionUID = -1;
        private School data;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            Model2 model = (Model2) o;

            if (data != null ? !data.equals(model.data) : model.data != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (data != null ? data.hashCode() : 0);
            return result;
        }

        public School getData() {
            return data;
        }

        public void setData(School data) {
            this.data = data;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        School school = (School) o;

        if (nursery_id != school.nursery_id) return false;
        if (address != null ? !address.equals(school.address) : school.address != null)
            return false;
        if (area != null ? !area.equals(school.area) : school.area != null) return false;
        if (city != null ? !city.equals(school.city) : school.city != null) return false;
        if (dispensary != null ? !dispensary.equals(school.dispensary) : school.dispensary != null)
            return false;
        if (district != null ? !district.equals(school.district) : school.district != null)
            return false;
        if (intro != null ? !intro.equals(school.intro) : school.intro != null) return false;
        if (issignup != null ? !issignup.equals(school.issignup) : school.issignup != null)
            return false;
        if (leadId != null ? !leadId.equals(school.leadId) : school.leadId != null) return false;
        if (logo != null ? !logo.equals(school.logo) : school.logo != null) return false;
        if (message != null ? !message.equals(school.message) : school.message != null)
            return false;
        if (nCreate != null ? !nCreate.equals(school.nCreate) : school.nCreate != null)
            return false;
        if (nName != null ? !nName.equals(school.nName) : school.nName != null) return false;
        if (nStatus != null ? !nStatus.equals(school.nStatus) : school.nStatus != null)
            return false;
        if (nType != null ? !nType.equals(school.nType) : school.nType != null) return false;
        if (posttime != null ? !posttime.equals(school.posttime) : school.posttime != null)
            return false;
        if (property != null ? !property.equals(school.property) : school.property != null)
            return false;
        if (province != null ? !province.equals(school.province) : school.province != null)
            return false;
        if (scale != null ? !scale.equals(school.scale) : school.scale != null) return false;
        if (staffNum != null ? !staffNum.equals(school.staffNum) : school.staffNum != null)
            return false;
        if (tel != null ? !tel.equals(school.tel) : school.tel != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nursery_id;
        result = 31 * result + (nName != null ? nName.hashCode() : 0);
        result = 31 * result + (property != null ? property.hashCode() : 0);
        result = 31 * result + (nType != null ? nType.hashCode() : 0);
        result = 31 * result + (nCreate != null ? nCreate.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (district != null ? district.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (logo != null ? logo.hashCode() : 0);
        result = 31 * result + (staffNum != null ? staffNum.hashCode() : 0);
        result = 31 * result + (tel != null ? tel.hashCode() : 0);
        result = 31 * result + (issignup != null ? issignup.hashCode() : 0);
        result = 31 * result + (scale != null ? scale.hashCode() : 0);
        result = 31 * result + (area != null ? area.hashCode() : 0);
        result = 31 * result + (dispensary != null ? dispensary.hashCode() : 0);
        result = 31 * result + (intro != null ? intro.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (leadId != null ? leadId.hashCode() : 0);
        result = 31 * result + (nStatus != null ? nStatus.hashCode() : 0);
        result = 31 * result + (posttime != null ? posttime.hashCode() : 0);
        return result;
    }

    public int getNursery_id() {
        return nursery_id;
    }

    public void setNursery_id(int nursery_id) {
        this.nursery_id = nursery_id;
    }

    public String getnName() {
        return nName;
    }

    public void setnName(String nName) {
        this.nName = nName;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getnType() {
        return nType;
    }

    public void setnType(String nType) {
        this.nType = nType;
    }

    public String getnCreate() {
        return nCreate;
    }

    public void setnCreate(String nCreate) {
        this.nCreate = nCreate;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getStaffNum() {
        return staffNum;
    }

    public void setStaffNum(String staffNum) {
        this.staffNum = staffNum;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIssignup() {
        return issignup;
    }

    public void setIssignup(String issignup) {
        this.issignup = issignup;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDispensary() {
        return dispensary;
    }

    public void setDispensary(String dispensary) {
        this.dispensary = dispensary;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLeadId() {
        return leadId;
    }

    public void setLeadId(String leadId) {
        this.leadId = leadId;
    }

    public String getnStatus() {
        return nStatus;
    }

    public void setnStatus(String nStatus) {
        this.nStatus = nStatus;
    }

    public String getPosttime() {
        return posttime;
    }

    public void setPosttime(String posttime) {
        this.posttime = posttime;
    }
}
