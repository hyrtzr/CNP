package com.hyrt.cnp.base.account.model;

import com.hyrt.cnp.base.account.utils.FaceUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Zoe on 2014-04-01.
 */
public class SchoolSearch implements Serializable {
    private int nursery_id;  //幼儿园ID
    private String nName;       //幼儿园名称
    private int property;       //性质(1=公办，2=民办)
    private String nCreate;     //建园时间（日期格式2011-04-13）
    private String district;    //县区
    private String address;     //地址
    private int staffNum;       //职工总数
    private String tel;         //电话
    private String nStatus;     //幼儿园状态('Y'=可用，'N'=禁用)
    private String province;    //省
    private String city;        //城市

    /**
     * 获取学校图片
     */
    public String getSchoolImagePath(){
        return FaceUtils.getSchoolImage(nursery_id);
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

    public int getProperty() {
        return property;
    }

    public void setProperty(int property) {
        this.property = property;
    }

    public String getnCreate() {
        return nCreate;
    }

    public void setnCreate(String nCreate) {
        this.nCreate = nCreate;
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

    public int getStaffNum() {
        return staffNum;
    }

    public void setStaffNum(int staffNum) {
        this.staffNum = staffNum;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getnStatus() {
        return nStatus;
    }

    public void setnStatus(String nStatus) {
        this.nStatus = nStatus;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) return false;

        SchoolSearch schoolSearch = (SchoolSearch) o;
        if (nursery_id != schoolSearch.nursery_id) return false;
        if (nName != null ? !nName.equals(schoolSearch.nName) : schoolSearch.nName != null) return false;
        if (property != schoolSearch.property) return false;
        if (nCreate != null ? !nCreate.equals(schoolSearch.nCreate) : schoolSearch.nCreate != null) return false;
        if (district != null ? !district.equals(schoolSearch.district) : schoolSearch.district != null) return false;
        if (address != null ? !address.equals(schoolSearch.address) : schoolSearch.address != null) return false;
        if (staffNum != schoolSearch.staffNum) return false;
        if (tel != null ? !tel.equals(schoolSearch.tel) : schoolSearch.tel != null) return false;
        if (nStatus != null ? !nStatus.equals(schoolSearch.nStatus) : schoolSearch.nStatus != null) return false;
        if (province != null ? !province.equals(schoolSearch.province) : schoolSearch.province != null) return false;
        if (city != null ? !city.equals(schoolSearch.city) : schoolSearch.city != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nursery_id;
        result = 31 * result + (nName != null ? nName.hashCode() : 0);
        result = 31 * result + property;
        result = 31 * result + (nCreate != null ? nCreate.hashCode() : 0);
        result = 31 * result + (district != null ? district.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + staffNum;
        result = 31 * result + (tel != null ? tel.hashCode() : 0);
        result = 31 * result + (nStatus != null ? nStatus.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }

    public static class Model extends Base {
        private static final int serialVersionUID = -1;

        private ArrayList<SchoolSearch> data;

        @Override
        public boolean equals(Object o) {
            if (this == o) {return true;}
            if (o == null || getClass() != o.getClass()) {return false;}
            if (!super.equals(o)) {return false;}

            Model model = (Model) o;
            if (data != null ? !data.equals(model.data) : model.data != null) {return false;}

            return true;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (data != null ? data.hashCode() : 0);
            return result;
        }

        public ArrayList<SchoolSearch> getData() { return data;}
        public void setData(ArrayList<SchoolSearch> data) { this.data = data;}
    }
}
