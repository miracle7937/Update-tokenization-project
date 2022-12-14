/*
 * MIT License
 *
 * Copyright (c) 2021 Thales DIS
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.thalesgroup.tshpaysample.ui;

import android.graphics.Color;

import android.graphics.drawable.ColorDrawable;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.gemalto.mfs.mwsdk.mobilegateway.enrollment.IDVMethodSelector;
import com.gemalto.mfs.mwsdk.mobilegateway.enrollment.PendingCardActivation;
import com.example.flutter_tokenization_plugin.R;
import com.thalesgroup.tshpaysample.sdk.SdkHelper;
import com.thalesgroup.tshpaysample.sdk.enrollment.TshEnrollment;
import com.thalesgroup.tshpaysample.sdk.enrollment.TshEnrollmentDelegate;
import com.thalesgroup.tshpaysample.sdk.enrollment.TshEnrollmentState;
import com.thalesgroup.tshpaysample.ui.fragments.FragmentSplash;
import com.thalesgroup.tshpaysample.ui.fragments.FragmentTermsAndConditions;
import com.thalesgroup.tshpaysample.ui.ui_util.CustomBottomSheet;
import com.thalesgroup.tshpaysample.utlis.AppLoggerHelper;

import java.util.Objects;

public class CardListActivity extends BaseAppActivity implements TshEnrollmentDelegate {

    //region DefinesmProgressViewContainer

    private static final String TAG = CardListActivity.class.getSimpleName();

    private TshEnrollmentState mLastProcessedState;

    //endregion

    //region Life Cycle

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        super.onViewCreated();

        //set zenith color on appbar
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CC0000")));
        // By default load splash screen.
        showFragment(new FragmentSplash(), false);

        // Make sure, that our application is default one used for NFC payments, but only if NFC hardware is present
        if (getPackageManager().hasSystemFeature("android.hardware.nfc")) {
            SdkHelper.getInstance().getInit().checkTapAndPay();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        final TshEnrollment tshEnrollment = SdkHelper.getInstance().getTshEnrollment();
        if (tshEnrollment.getEnrollmentState() != mLastProcessedState) {
            onStateChange(tshEnrollment.getEnrollmentState(), tshEnrollment.getEnrollmentError());
        }
        reloadFragmentData();
    }

    //endregion

    //region TshEnrollmentDelegate

    public void onStateChange(final TshEnrollmentState state, final String error) {
        if (state == TshEnrollmentState.ENROLLING_FINISHED_WAITING_FOR_SERVER) {
            progressHide();
            CustomBottomSheet.success(this, getString(state.getActionDescription()) );
//            displayMessageToast(state.getActionDescription());
        } else if (state == TshEnrollmentState.ELIGIBILITY_TERMS_AND_CONDITIONS) {
            progressHide();
            showFragment(new FragmentTermsAndConditions(), true);
        } else if (state == TshEnrollmentState.DIGITIZATION_FINISHED) {
            reloadFragmentData();
        } else if (state.isProgressState()) {
            // Any ongoing enrollment type.
            progressShow(state.getActionDescription());
        } else {
            // Enrollment not started or failed with error.
            progressHide();
            displayMessageToast(error);
        }

        mLastProcessedState = state;
    }

    @Override
    public void onSelectIDVMethod(final IDVMethodSelector idvMethodSelector) {
        if (idvMethodSelector.getIdvMethodList().length == 0) {
            AppLoggerHelper.error(TAG, "IDVMethodSelector is empty");
            return;
        }

        // Prepare list of possible options.
        final String[] methods = new String[idvMethodSelector.getIdvMethodList().length];
        for (int loopIndex = 0; loopIndex < idvMethodSelector.getIdvMethodList().length; loopIndex++) {
            methods[loopIndex] = idvMethodSelector.getIdvMethodList()[loopIndex].getType();
        }

        // Display selection dialog.
        final AlertDialog.Builder builder = new AlertDialog.Builder(CardListActivity.this);
        builder.setTitle(getString(R.string.sdk_idv_selection_dialog));
        builder.setSingleChoiceItems(methods, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                idvMethodSelector.select(idvMethodSelector.getIdvMethodList()[which].getId());
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(getString(R.string.common_word_cancel), null);
        builder.create().show();
    }

    public void onActivationRequired(final PendingCardActivation pendingCardActivation) {
        switch (pendingCardActivation.getState()) {
            case IDV_METHOD_NOT_SELECTED:
                // Not relevant for this method.
                break;
            case OTP_NEEDED:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.sdk_otp_entry_dialog);
                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);
                builder.setPositiveButton(R.string.common_word_ok, (dialog, which) -> {
                    final String enteredValue = input.getText().toString();
                    if (!enteredValue.isEmpty()) {
                        pendingCardActivation.activate(enteredValue.getBytes(), SdkHelper.getInstance().getTshEnrollment());
                    } else {
                        // Invalid entry. Display message and re-try.
                        CustomBottomSheet.error(this,  (String) getText(R.string.sdk_otp_entry_empty_string));

//                        displayMessageToast(R.string.sdk_otp_entry_empty_string);
                        onActivationRequired(pendingCardActivation);
                    }
                });
                builder.setNegativeButton(getString(R.string.common_word_cancel), null);
                builder.show();

                break;
            case WEB_3DS_NEEDED:
            case APP2APP_NEEDED:
                // Not in the scope of sample app.
                displayMessageToast(R.string.sdk_not_in_scope);
                break;
            default:
                CustomBottomSheet.error(this,  (String) getText(R.string.sdk_otp_entry_empty_string));
//                displayMessageToast(R.string.sdk_idv_method_not_handled);
                break;
        }

    }

    //endregion

}