package com.hyrt.cnp.base.account.service;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.CNPClient;
import com.hyrt.cnp.base.account.model.Recipe;
import com.hyrt.cnp.base.account.model.RecipeInfo;

import net.oschina.app.AppContext;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by GYH on 14-1-15.
 */
public class RecipeInfoService {

    private CNPClient cnpClient;

    public RecipeInfoService(CNPClient cnpClient){
        this.cnpClient = cnpClient;
    }

    //TODO after modify
    public RecipeInfo.Model2 getRecipeWeekData(RestTemplate restTemplate,Recipe recipe){
//        cnpClient.configureRequest();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("year",recipe.getYears());
        params.put("week",recipe.getWeekd());
        params.put("sid", recipe.getNurseryID());
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"school/week_recipe?"+
                "sid={sid}&year={year}&week={week}",
                RecipeInfo.Model2.class, params);
    }

    /**
     * 获取每天食谱信息
     * */
    //TODO after modify
    public RecipeInfo.Model getRecipeDayData(RestTemplate restTemplate,String time){
        cnpClient.configureRequest();
        HashMap<String, String> params = cnpClient.getParamsforGet();
        params.put("time",time);
        android.util.Log.i("tag", "time:"+time+" sid:"+params.get("sid"));
        return  restTemplate.getForObject(AppContext.getInstance().getString(R.string.path_api)+"school/recipe_info/?" +
                "token={token}&uuid={uuid}&sid={sid}&time={time}",
                RecipeInfo.Model.class, params);
    }
 }
