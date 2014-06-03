package com.hyrt.cnp.school.ui;

import android.os.Bundle;
import android.widget.Toast;

import com.hyrt.cnp.base.account.model.School;
import com.hyrt.cnp.R;
import com.hyrt.cnp.school.request.SchoolListRequest;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * Created by GYH on 14-1-3.
 */
public class SchoolListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolindex);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadhomedynamic();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void loadhomedynamic() {
        setProgressBarIndeterminateVisibility(true);
        spiceManager.execute(new SchoolListRequest(School.Model.class, this), "github", DurationInMillis.ONE_SECOND * 10,
                new HomedynamicRequestListener());
    }

    public class HomedynamicRequestListener implements RequestListener<School.Model> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            setProgressBarIndeterminateVisibility(false);
            Toast.makeText(SchoolListActivity.this, "失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess(School.Model result) {
            setProgressBarIndeterminateVisibility(false);
            if (result == null) {
                Toast.makeText(SchoolListActivity.this, "is null", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SchoolListActivity.this, "city===" + result.getData().get(0).getCity(), Toast.LENGTH_SHORT).show();
            }

        }
    }
}
