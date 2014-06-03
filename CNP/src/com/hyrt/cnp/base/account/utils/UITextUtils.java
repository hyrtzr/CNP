package  com.hyrt.cnp.base.account.utils;

import android.widget.EditText;
import android.widget.TextView;

/**
 * textview、edittext工具类
 */
public class UITextUtils
{

    public UITextUtils()
    {
    }

    public static void setTextWithSelection(EditText edittext, String s)
    {
        edittext.setText(s);
        edittext.setSelection(s.length());
    }


    public static void setTextWithSelection(TextView textView, String s)
    {
        if(s != null)
            textView.setText(s);
    }
}
