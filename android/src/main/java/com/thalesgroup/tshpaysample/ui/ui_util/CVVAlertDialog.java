package com.thalesgroup.tshpaysample.ui.ui_util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Html;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;

import com.example.flutter_tokenization_plugin.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.thalesgroup.tshpaysample.logic.OnCvvCompleted;

public class CVVAlertDialog {


 static public void   show(Activity context, OnCvvCompleted cvvCompleted){
     LayoutInflater factory = LayoutInflater.from(context);
     final View view = factory.inflate(R.layout.cvv_collection_widget, null);
     AlertDialog.Builder builder = new AlertDialog.Builder(
             new ContextThemeWrapper(context, R.style.MyAlertDialog));
     TextInputEditText cvv_Edit_Text  =  view.findViewById(R.id.cvv_edit_text);
     TextInputLayout cvv_edit_Layout  =  view.findViewById(R.id.cvv_edit_Layout);
     cvv_edit_Layout.setCounterMaxLength(3);
     cvv_edit_Layout.setCounterEnabled(true);
     builder.setView(view);
     builder.setTitle("Enter Card CVV");
      builder.setPositiveButton(Html.fromHtml("<font color='#000000'>Confirm</font>"), new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {

              if(cvv_Edit_Text.getText().length() ==3){
                  cvvCompleted.get(cvv_Edit_Text.getText().toString());
              }

          }
      });
      builder.setNegativeButton(Html.fromHtml("<font color='#000000'>Cancel</font>"), new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
              dialog.dismiss();
          }
      });
      AlertDialog dialog = builder.create();
     dialog.show();
 }
}

