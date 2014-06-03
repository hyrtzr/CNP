package com.hyrt.cnp.base.account.model;


import com.hyrt.cnp.base.account.utils.StringUtils;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by GYH on 14-1-4.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe implements Serializable {
    private String total;
    private String posttime;
    private String nurseryID;
    private String weekd;
    private String years;
    private String starttime;
    private String endtime;



    public static class Model extends Base{
        private static final long serialVersionUID = -1;

        private ArrayList<Recipe> data;

        public ArrayList<Recipe> getData() {
            return data;
        }

        public void setData(ArrayList<Recipe> data) {
            this.data = data;
        }

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
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        if (endtime != null ? !endtime.equals(recipe.endtime) : recipe.endtime != null)
            return false;
        if (nurseryID != null ? !nurseryID.equals(recipe.nurseryID) : recipe.nurseryID != null)
            return false;
        if (posttime != null ? !posttime.equals(recipe.posttime) : recipe.posttime != null)
            return false;
        if (starttime != null ? !starttime.equals(recipe.starttime) : recipe.starttime != null)
            return false;
        if (total != null ? !total.equals(recipe.total) : recipe.total != null) return false;
        if (weekd != null ? !weekd.equals(recipe.weekd) : recipe.weekd != null) return false;
        if (years != null ? !years.equals(recipe.years) : recipe.years != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = total != null ? total.hashCode() : 0;
        result = 31 * result + (posttime != null ? posttime.hashCode() : 0);
        result = 31 * result + (nurseryID != null ? nurseryID.hashCode() : 0);
        result = 31 * result + (weekd != null ? weekd.hashCode() : 0);
        result = 31 * result + (years != null ? years.hashCode() : 0);
        result = 31 * result + (starttime != null ? starttime.hashCode() : 0);
        result = 31 * result + (endtime != null ? endtime.hashCode() : 0);
        return result;
    }

    public String getPosttime2(){
        try {
            return StringUtils.millTimeToNormalTime(posttime+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPosttime() {
        return posttime;
    }

    public void setPosttime(String posttime) {
        this.posttime = posttime;
    }

    public String getNurseryID() {
        return nurseryID;
    }

    public void setNurseryID(String nurseryID) {
        this.nurseryID = nurseryID;
    }

    public String getWeekd() {
        return weekd;
    }

    public void setWeekd(String weekd) {
        this.weekd = weekd;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
}
