package com.hyrt.cnp.base.account.model;


import com.hyrt.cnp.base.account.utils.StringUtils;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by GYH on 14-1-4.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeInfo {
    private String recipe_id;
    private String user_id;
    private String recipeDate;
    private String fooder;
    private String breakfast;
    private String b_ingredients;
    private String addfood;
    private String a_ingredients;
    private String lunch;
    private String l_ingredients;
    private String lunchsnacks;
    private String dinner;
    private String d_ingredients;
    private String level;
    private String nurseryID;
    private int posttime;


    public static class Model2 extends Base{
        private static final long serialVersionUID = -1;
        private ArrayList<RecipeInfo> data;

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

        public ArrayList<RecipeInfo> getData() {
            return data;
        }

        public void setData(ArrayList<RecipeInfo> data) {
            this.data = data;
        }
    }

    public static class Model extends Base{
        private static final long serialVersionUID = -1;

        private RecipeInfo data;

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

        public RecipeInfo getData() {
            return data;
        }

        public void setData(RecipeInfo data) {
            this.data = data;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecipeInfo that = (RecipeInfo) o;

        if (posttime != that.posttime) return false;
        if (a_ingredients != null ? !a_ingredients.equals(that.a_ingredients) : that.a_ingredients != null)
            return false;
        if (addfood != null ? !addfood.equals(that.addfood) : that.addfood != null) return false;
        if (b_ingredients != null ? !b_ingredients.equals(that.b_ingredients) : that.b_ingredients != null)
            return false;
        if (breakfast != null ? !breakfast.equals(that.breakfast) : that.breakfast != null)
            return false;
        if (d_ingredients != null ? !d_ingredients.equals(that.d_ingredients) : that.d_ingredients != null)
            return false;
        if (dinner != null ? !dinner.equals(that.dinner) : that.dinner != null) return false;
        if (fooder != null ? !fooder.equals(that.fooder) : that.fooder != null) return false;
        if (l_ingredients != null ? !l_ingredients.equals(that.l_ingredients) : that.l_ingredients != null)
            return false;
        if (level != null ? !level.equals(that.level) : that.level != null) return false;
        if (lunch != null ? !lunch.equals(that.lunch) : that.lunch != null) return false;
        if (lunchsnacks != null ? !lunchsnacks.equals(that.lunchsnacks) : that.lunchsnacks != null)
            return false;
        if (nurseryID != null ? !nurseryID.equals(that.nurseryID) : that.nurseryID != null)
            return false;
        if (recipeDate != null ? !recipeDate.equals(that.recipeDate) : that.recipeDate != null)
            return false;
        if (recipe_id != null ? !recipe_id.equals(that.recipe_id) : that.recipe_id != null)
            return false;
        if (user_id != null ? !user_id.equals(that.user_id) : that.user_id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = recipe_id != null ? recipe_id.hashCode() : 0;
        result = 31 * result + (user_id != null ? user_id.hashCode() : 0);
        result = 31 * result + (recipeDate != null ? recipeDate.hashCode() : 0);
        result = 31 * result + (fooder != null ? fooder.hashCode() : 0);
        result = 31 * result + (breakfast != null ? breakfast.hashCode() : 0);
        result = 31 * result + (b_ingredients != null ? b_ingredients.hashCode() : 0);
        result = 31 * result + (addfood != null ? addfood.hashCode() : 0);
        result = 31 * result + (a_ingredients != null ? a_ingredients.hashCode() : 0);
        result = 31 * result + (lunch != null ? lunch.hashCode() : 0);
        result = 31 * result + (l_ingredients != null ? l_ingredients.hashCode() : 0);
        result = 31 * result + (lunchsnacks != null ? lunchsnacks.hashCode() : 0);
        result = 31 * result + (dinner != null ? dinner.hashCode() : 0);
        result = 31 * result + (d_ingredients != null ? d_ingredients.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (nurseryID != null ? nurseryID.hashCode() : 0);
        result = 31 * result + posttime;
        return result;
    }

    public String getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(String recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRecipeDate() {
        return recipeDate;
    }
    public String getRecipeDate2() {
        try {
            return StringUtils.millTimeToNormalTime2(recipeDate+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void setRecipeDate(String recipeDate) {
        this.recipeDate = recipeDate;
    }

    public String getFooder() {
        return fooder;
    }

    public void setFooder(String fooder) {
        this.fooder = fooder;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getB_ingredients() {
        return b_ingredients;
    }

    public void setB_ingredients(String b_ingredients) {
        this.b_ingredients = b_ingredients;
    }

    public String getAddfood() {
        return addfood;
    }

    public void setAddfood(String addfood) {
        this.addfood = addfood;
    }

    public String getA_ingredients() {
        return a_ingredients;
    }

    public void setA_ingredients(String a_ingredients) {
        this.a_ingredients = a_ingredients;
    }

    public String getLunch() {
        return lunch;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }

    public String getL_ingredients() {
        return l_ingredients;
    }

    public void setL_ingredients(String l_ingredients) {
        this.l_ingredients = l_ingredients;
    }

    public String getLunchsnacks() {
        return lunchsnacks;
    }

    public void setLunchsnacks(String lunchsnacks) {
        this.lunchsnacks = lunchsnacks;
    }

    public String getDinner() {
        return dinner;
    }

    public void setDinner(String dinner) {
        this.dinner = dinner;
    }

    public String getD_ingredients() {
        return d_ingredients;
    }

    public void setD_ingredients(String d_ingredients) {
        this.d_ingredients = d_ingredients;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNurseryID() {
        return nurseryID;
    }

    public void setNurseryID(String nurseryID) {
        this.nurseryID = nurseryID;
    }

    public int getPosttime() {
        return posttime;
    }

    public void setPosttime(int posttime) {
        this.posttime = posttime;
    }
}
