package ts.trainticket.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


public class ClearEditTextTools {
    public static void addclearListener(final EditText e1, final ImageView m1){
        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                 String s1 = s + "";
                if(s.length() >0 ){
                    m1.setVisibility(View.VISIBLE);
                }else{
                    m1.setVisibility(View.INVISIBLE);
                }
            }
        });
        m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e1.setText("");
            }
        });
    }
}
