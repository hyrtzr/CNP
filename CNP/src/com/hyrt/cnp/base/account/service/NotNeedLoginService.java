package com.hyrt.cnp.base.account.service;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.model.SchoolSearch;
import com.hyrt.cnp.base.account.model.UtilVar;
import com.hyrt.cnp.base.account.utils.LogHelper;

import net.oschina.app.AppContext;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by HY on 2014-05-05.
 */
public class NotNeedLoginService {

    public UtilVar getUtilvar(String name){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name",name);
        return  getRestTemplate().getForObject(AppContext.getInstance().getString(R.string.path_api)+"var/get_var?name={name}",
                UtilVar.class, params);
    }

    public UtilVar getUtilvar(String name, String provinceName){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name",name);
        params.put("provinceName", provinceName);
        android.util.Log.i("tag", "name:"+name+" provinceName:"+provinceName);
        return  getRestTemplate().getForObject(AppContext.getInstance().getString(R.string.path_api)+"var/get_var?" +
                        "name={name}&provinceName={provinceName}",
                UtilVar.class, params);
    }

    public UtilVar getUtilvar(String name, int province){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name",name);
        params.put("province", province+"");
        android.util.Log.i("tag", "name:"+name+" province:"+province);
        return  getRestTemplate().getForObject(AppContext.getInstance().getString(R.string.path_api)+"var/get_var?" +
                        "name={name}&province={province}",
                UtilVar.class, params);
    }

    public SchoolSearch.Model getSchoolSearchData(
            RestTemplate restTemplate, String keytName,
            String keytDistrict, String keytProperty, String keytScale,
            Double lng, Double lat, String province, String more){
        android.util.Log.i("tag", "keytName:"+keytName+" keytDistrict:"+keytDistrict
                +" keytProperty："+keytProperty+" keytScale:"+keytScale+" lng:"+lng+" lat:"+lat
                +" province："+province);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", keytName);
        StringBuffer path = new StringBuffer(AppContext.getInstance().getString(R.string.path_api)+"school/search/?"
                + "name={name}");
        if (keytDistrict.length() > 0) {
            path.append("&city="+keytDistrict);
        }
        if (keytProperty.length() > 0) {
            path.append("&property="+keytProperty);
        }
        if (keytScale.length() > 0) {
            path.append("&scale="+keytScale);
        }
        if(lng != 0 && lat != 0){
            path.append("&jingwei="+lng+","+lat);
        }
        if (province != null && province.length() > 0) {
            path.append("&province="+province);
        }
        if(more != null && !"1".equals(more)){
        	path.append("&isMore=old,"+more);
        }

        LogHelper.i("tag", "path:"+path);
        
        return restTemplate.getForObject(path.toString(),
                SchoolSearch.Model.class, params);
    }

    protected RestTemplate getRestTemplate() {
        return new RestTemplate(true, new HttpComponentsClientHttpRequestFactory());
    }
}
