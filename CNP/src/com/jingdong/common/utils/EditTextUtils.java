package com.jingdong.common.utils;

import android.widget.EditText;

public class EditTextUtils
{

    public EditTextUtils()
    {
    }

    public static void setTextWithSelection(EditText edittext, String s)
    {
        edittext.setText(s);
        edittext.setSelection(s.length());
    }
}
