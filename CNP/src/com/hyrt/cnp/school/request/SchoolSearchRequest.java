package com.hyrt.cnp.school.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.request.NotNeedLoginBaseRequest;
import com.hyrt.cnp.base.account.service.NotNeedLoginService;
import com.hyrt.cnp.base.account.service.SchoolSearchService;
import com.hyrt.cnp.base.account.utils.Log;

/**
 * Created by Zoe on 2014-04-01.
 */
public class SchoolSearchRequest extends NotNeedLoginBaseRequest{

    private String keytName, keytDistrict, keytProperty, keytScale;
    private Double lng, lat;
    private String province;

//    @Inject
//    private SchoolSearchService mSchoolSearchService;

    @Inject
    private NotNeedLoginService notNeedLoginService;

    public SchoolSearchRequest(Class clazz, Context context,
                               String keytName, String keytDistrict,
                               String keytProperty, String keytScale, double lng, double lat, String province) {
        super(clazz, context);
        this.keytName = keytName;
        this.keytDistrict = keytDistrict;
        this.keytProperty = keytProperty;
        this.keytScale = keytScale;
        this.lng = lng;
        this.lat = lat;
        this.province = province;
    }

    @Override
    public Base run() {
        return notNeedLoginService.getSchoolSearchData(
                getRestTemplate(), keytName,
                keytDistrict, keytProperty, keytScale, lng, lat, province);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "SchoolSearch"+keytName+keytDistrict+keytProperty+keytScale+lng+lat+province;
    }
}
