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

package com.thalesgroup.tshpaysample.ui.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.gemalto.mfs.mwsdk.dcm.DigitalizedCardStatus;
import com.gemalto.mfs.mwsdk.mobilegateway.enrollment.PendingCardActivation;
import com.example.flutter_tokenization_plugin.R;
import com.thalesgroup.tshpaysample.logic.CustomButton;
import com.thalesgroup.tshpaysample.sdk.SdkHelper;
import com.thalesgroup.tshpaysample.sdk.helpers.AsyncHelperCardState;
import com.thalesgroup.tshpaysample.ui.CardListActivity;
import com.thalesgroup.tshpaysample.sdk.helpers.CardWrapper;
import com.thalesgroup.tshpaysample.ui.ui_util.CustomBottomSheet;
import com.thalesgroup.tshpaysample.ui.ui_util.DisplayMessage;
import com.thalesgroup.tshpaysample.ui.views.ViewCardFront;
import com.thalesgroup.tshpaysample.utlis.AppLoggerHelper;

import java.util.Objects;

public class FragmentCardPage extends Fragment {

    //region Defines
    public static final String ARGUMENT_CARD_ID = "ArgumentCardId";

    private static final String TAG = FragmentCardPage.class.getSimpleName();

    private CustomButton mButtonActivate, mButtonSuspend, mButtonSetDefault, mButtonEnroll, mButtonPayment;
    private TextView mTextIsDefault, mTextStatus;

    private ViewCardFront mCardVisual;
    private CardWrapper mCardWrapper;

    private final CardWrapper.CardActionDelegate mCardOperationDelegate = (value, message) -> {
        new Handler(Looper.getMainLooper()).post(() -> {
            // Display some common notification based on state.
            CharSequence toDisplay;
            if (value) {
                toDisplay = getText(R.string.card_deletion);
                CustomBottomSheet.success(getActivity(),  (String) toDisplay);
            } else {
                toDisplay = message == null ? getText(R.string.fragment_card_page_action_failed) : message;
                CustomBottomSheet.error(getActivity(),  (String) toDisplay);

            }


//            DisplayMessage.show(Objects.requireNonNull(getMainActivity()), (String) toDisplay);

        });
    };

    //endregion

    //region Life Cycle

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View retValue = inflater.inflate(R.layout.fragment_card_page, container, false);

        // Top card visual with basic information
        mCardVisual = retValue.findViewById(R.id.fragment_card_page_card_visual);

        // Additional card information
        mTextIsDefault = retValue.findViewById(R.id.fragment_card_page_is_default);
        mTextStatus = retValue.findViewById(R.id.fragment_card_page_text_status);

        // Card actions
        mButtonActivate = initButton(retValue, R.id.fragment_card_page_button_resume, this::onButtonPressedResume);
//        mButtonSuspend = initButton(retValue, R.id.fragment_card_page_button_suspend, this::onButtonPressedSuspend);
        mButtonSetDefault = initButton(retValue, R.id.fragment_card_page_button_set_default, this::onButtonPressedSetDefault);
        mButtonEnroll = initButton(retValue, R.id.fragment_card_page_button_activate, this::onButtonPressedActivate);
        mButtonPayment = initButton(retValue, R.id.fragment_card_page_button_payment, this::onButtonPressedPayment);
        initButton(retValue, R.id.fragment_card_page_button_delete, this::onButtonPressedDelete);

        //Activity setting title
        requireActivity().setTitle(R.string.tokenize_card);

        return retValue;
    }

    @Override
    public void onViewCreated(@NonNull final View view,
                              @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(ARGUMENT_CARD_ID)) {
            mCardWrapper = new CardWrapper(arguments.getString(ARGUMENT_CARD_ID));
            updateState();
        }
    }

    //endregion

    //region Public API

    public void updateState() {
        // For some reason we do not have proper data layer. Prevent app crash.
        if (mCardWrapper == null) {
            return;
        }

        // Load card graphics and basic information like PAN, EXP etc...
        mCardVisual.loadCardDetails(mCardWrapper);

        // Load additional card info.
        mCardWrapper.isDefault((value, message) -> {
            mTextIsDefault.setText(value ? R.string.common_word_yes : R.string.common_word_no);
        });


       mButtonActivate.setVisibility(View.GONE);
        mButtonSetDefault.setVisibility(View.GONE);
        mButtonEnroll.setVisibility(View.GONE);
        mButtonPayment.setVisibility(View.GONE);

        // Get current card state so we can enable proper actions.
        mCardWrapper.getDigitalizedCardState(new AsyncHelperCardState.Delegate() {
            @Override
            public void onSuccess(final DigitalizedCardStatus value) {
                mTextStatus.setText(value.getState().name());

                switch (value.getState()) {
                    case ACTIVE:
                        mButtonPayment.setVisibility(View.VISIBLE);
                        mCardWrapper.isDefault((isDefault, message) -> {
                            mCardVisual.setCardVisibilityIcon(isDefault); // set default icon visibility
                            if(!isDefault){
                                mButtonSetDefault.setVisibility(View.VISIBLE);
                            }else{
                                mButtonSetDefault.setVisibility(View.GONE);
                            }


                        });
                        break;
                    case SUSPENDED:
                        final PendingCardActivation pendingActiovation = mCardWrapper.getPendingActivation();
                        if (pendingActiovation != null) {
                            mButtonEnroll.setVisibility(View.VISIBLE);
                        } else {
                            mButtonActivate.setVisibility(View.VISIBLE);
                        }
                        break;
                    case RETIRED:
                        // TODO: Do we want to handle this in sample?
                        break;
                    case UNKNOWN:
                        CustomBottomSheet.error(getActivity(),  (String) getText(R.string.fragment_card_page_unknown_card_state));
                        AppLoggerHelper.error(TAG, "Card is in unknown state.");
                        break;
                    default:
                        CustomBottomSheet.error(getActivity(),  (String) getText(R.string.fragment_card_page_unhandled_card_state));

                        AppLoggerHelper.error(TAG, "Card is in unhandled state.");
                        break;
                }
            }

            @Override
            public void onError(final String error) {
                if (getContext() != null) {
                    CustomBottomSheet.error(getActivity(), error);
//                    Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //endregion

    //region Private Helpers

    private CustomButton initButton(final View parent,
                              final @IdRes int buttonId,
                              final View.OnClickListener handler) {
        final CustomButton retValue = parent.findViewById(buttonId);
        retValue.setOnClickListener(handler);

        return retValue;
    }


    //endregion

    //region User Interface

    private void onButtonPressedResume(final View sender) {
        mCardWrapper.resumeCard(mCardOperationDelegate);
    }

    private void onButtonPressedSuspend(final View sender) {
        mCardWrapper.suspendCard(mCardOperationDelegate);
    }

    private void onButtonPressedSetDefault(final View sender) {
        mCardWrapper.setDefault((value, message) -> {
            final CardListActivity cardListActivity = getMainActivity();
            if (value && cardListActivity != null) {
                cardListActivity.reloadFragmentData();
            } else if (cardListActivity != null) {
                mCardOperationDelegate.onFinished(value, message);
            }
        });
    }

    private void onButtonPressedDelete(final View sender) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyDialogTheme);
        builder.setNegativeButton(android.R.string.no, null);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("Delete Card")
                .setMessage("Are you sure you want to delete this card?")
                .setCancelable(true)
                .setNegativeButton(Html.fromHtml("<font color='#000000'>NO</font>"), null)
                .setPositiveButton(Html.fromHtml("<font color='#000000'>Yes</font>"), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                 mCardWrapper.deleteCard(mCardOperationDelegate);
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

    }

    private void onButtonPressedActivate(final View sender) {
        final PendingCardActivation pendingActivation = mCardWrapper.getPendingActivation();
        final CardListActivity cardListActivity = getMainActivity();
        if (pendingActivation != null && cardListActivity != null) {
            SdkHelper.getInstance().getTshEnrollment().invokePendingActivation(pendingActivation, (CardListActivity) getActivity());
        } else if (cardListActivity != null){
            Toast.makeText(cardListActivity, getString(R.string.sdk_no_pending_activation), Toast.LENGTH_LONG).show();
            AppLoggerHelper.error(TAG, "No pending card activation.");
            updateState();
        }
    }


    private void onButtonPressedPayment(final View sender) {
      mCardWrapper.invokeManualPayment(getContext());
    }

    private CardListActivity getMainActivity() {
        if (getActivity() instanceof CardListActivity) {
            return (CardListActivity) getActivity();
        } else {
            return null;
        }
    }

    //endregion

}
