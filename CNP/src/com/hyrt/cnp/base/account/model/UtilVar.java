package com.hyrt.cnp.base.account.model;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.Map;

/**
 * Created by GYH on 14-2-13.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UtilVar extends Base{
    private static final long serialVersionUID = -1;

    private Map<String,String> data ;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UtilVar utilVar = (UtilVar) o;

        if (data != null ? !data.equals(utilVar.data) : utilVar.data != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
