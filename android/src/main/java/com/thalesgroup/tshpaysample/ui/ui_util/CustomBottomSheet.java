package com.thalesgroup.tshpaysample.ui.ui_util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.flutter_tokenization_plugin.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class CustomBottomSheet {


   static    public  void  error(Activity activity, String message){
         BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                activity, R.style.CustomBottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(activity)
                .inflate(R.layout.modal_bottom_sheet,
                        (LinearLayout) activity.findViewById(R.id.modalBottomSheetContainer));


        bottomSheetDialog.setContentView(bottomSheetView);
       TextView title = bottomSheetDialog.findViewById(R.id.bottom_sheet_title);
       TextView content = bottomSheetDialog.findViewById(R.id.bottom_sheet_content);
       ImageView imageView = bottomSheetDialog.findViewById(R.id.imageView_title);
       Button button = bottomSheetDialog.findViewById(R.id.button_tap);


       assert button != null;
       button.setOnClickListener(view -> {
           bottomSheetDialog.dismiss();
       });

       assert title != null;
       title.setText(R.string.error);

       assert content != null;
       content.setText(message);

       assert imageView != null;
       imageView.setImageResource(R.drawable.error_icon);
        bottomSheetDialog.show();
    }


    static    public  void  success(Activity activity, String message){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                activity, R.style.CustomBottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(activity)
                .inflate(R.layout.modal_bottom_sheet,
                        (LinearLayout) activity.findViewById(R.id.modalBottomSheetContainer));


        bottomSheetDialog.setContentView(bottomSheetView);
        TextView title = bottomSheetDialog.findViewById(R.id.bottom_sheet_title);
        TextView content = bottomSheetDialog.findViewById(R.id.bottom_sheet_content);
        ImageView imageView = bottomSheetDialog.findViewById(R.id.imageView_title);
        Button button = bottomSheetDialog.findViewById(R.id.button_tap);


        assert button != null;
        button.setOnClickListener(view -> {
            bottomSheetDialog.dismiss();
        });

        assert title != null;
        title.setText(R.string.success);

        assert content != null;
        content.setText(message);

        assert imageView != null;
        imageView.setImageResource(R.drawable.success);
        bottomSheetDialog.show();
    }

}
