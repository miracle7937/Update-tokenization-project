package com.thalesgroup.tshpaysample.logic;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.flutter_tokenization_plugin.R;

public class CustomButton extends RelativeLayout {
    TypedArray attributes;
    TextView textView;
    ImageView imageView;
    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.custom_button, this);
        attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomButton,0,0);
        textView = findViewById(R.id.custom_btn_text);
        imageView = findViewById(R.id.custom_btn_image);
        imageView.setImageResource(attributes.getResourceId(R.styleable.CustomButton_Image, R.color.black));
        setText(attributes.getString(R.styleable.CustomButton_Text));
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public String getText(){
        return  attributes.getString(R.styleable.CustomButton_Text);
    }
    public void setText(String text){
         textView.setText(text);
    }


}
