package com.hyrt.cnp.school.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyrt.cnp.R;
import com.jingdong.common.frame.BaseActivity;

/**
 * Created by Zoe on 2014-03-31.
 */
public class SchoolSearchActivity extends BaseActivity{
    private EditText etSearchKeyt;
    private Button btnSearchSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_search);
        findView();
        btnSearchSubmit.setOnClickListener(btnOnClickListener);
    }

    View.OnClickListener btnOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(SchoolSearchActivity.this, SchoolSearchResultActivity.class);
            intent.putExtra("name", etSearchKeyt.getText().toString());
            startActivity(intent);
        }
    };

    private void findView() {
        etSearchKeyt = (EditText) findViewById(R.id.et_search_keyt);
        btnSearchSubmit = (Button) findViewById(R.id.btn_search_submit);
    }
}
