package com.thalesgroup.tshpaysample.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.flutter_tokenization_plugin.R;
import com.thalesgroup.tshpaysample.logic.CardItemAdapter;
import com.thalesgroup.tshpaysample.logic.CardModel;
import com.thalesgroup.tshpaysample.logic.OnItemClickListener;
import com.thalesgroup.tshpaysample.logic.UserAccount;
import com.thalesgroup.tshpaysample.logic.network.CardRequestInterface;
import com.thalesgroup.tshpaysample.logic.network.model.CardInfo;
import com.thalesgroup.tshpaysample.logic.network.model.RemoteCardDetail;
import com.thalesgroup.tshpaysample.logic.network.repository.RequestRepository;
import com.thalesgroup.tshpaysample.sdk.SdkHelper;
import com.thalesgroup.tshpaysample.ui.CardListActivity;
import com.thalesgroup.tshpaysample.ui.ui_util.CVVAlertDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecycleViewFragment extends AbstractFragment implements OnItemClickListener {
    private CardItemAdapter cardItemAdapter;
    private List<CardModel> cardModelList = new ArrayList<CardModel>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initalization card
   requestCardInitialization();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =     inflater.inflate(R.layout.fragment_recycle_view, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
         cardItemAdapter=  new CardItemAdapter(cardModelList,getContext());

        recyclerView.setAdapter(cardItemAdapter);
        cardItemAdapter.setClickListener(this);
        //Activity setting title
        requireActivity().setTitle(R.string.select_tokenize_card);
        return view;
    }


    private  void requestCardInitialization(){
        CardRequestInterface cardRequestInterface =  RequestRepository.retrofit.create(CardRequestInterface.class);
     Call<RemoteCardDetail> call = cardRequestInterface.getAllCard(UserAccount.getInstance().getAccount());
        getMainActivity().progressShow("fetching card detail");
        new Thread(new Runnable() {
            @Override
            public void run() {
                call.enqueue(new Callback<RemoteCardDetail>() {
                    @Override
                    public void onResponse(Call<RemoteCardDetail> call, Response<RemoteCardDetail> response) {
                        getMainActivity().runOnUiThread(new Runnable() {
                            @SuppressLint("NotifyDataSetChanged")
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void run() {
                                if(response.body() != null){
                                    for (CardInfo value: response.body().getData()) {
                                        cardModelList.add(new CardModel(
                                                value.getNameOnCard(),
                                                value.getCardType(),
                                                value.getCardTypeDescription(),
                                                value.getMaskedFPan(),
                                                value.getExpiryDate(),
                                                value.getCipheredCardInfo()
                                        ));
                                    }
                                }

                                cardItemAdapter.notifyDataSetChanged();
                                getMainActivity().progressHide();
                                if(cardModelList.isEmpty()){
                                    //navigate back if empty
                                    getMainActivity().hideCurrentFragment();
                                }
                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<RemoteCardDetail> call, Throwable t) {
                        getMainActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getMainActivity().progressHide();

                            }
                        });
                    }
                });
            }

        }).start();



    }


//    private List<CardModel> initData() {
//        List itemList =new ArrayList<CardModel>();
//        itemList.add(new CardModel("4147049980036816", "123","1225"));
//        itemList.add(new CardModel("4147049980036808", "123","1225"));
//        itemList.add(new CardModel(" ", "123","1225"));
//        return itemList;
//    }

    @Override
    public int getFragmentCaption() {
        return R.string.fragment_card_enrollment_caption;
    }

    @Override
    public void onClick(View view, int position) {
        onButtonPressEnroll(cardModelList.get(position));
    }


    private void onButtonPressEnroll(CardModel cardModel) {

        CVVAlertDialog.show(getActivity(), cvv -> {
            // Main activity is also acting as delegate for enrollment.
            final CardListActivity cardListActivity = getMainActivity();
            // Trigger enrollment, display new progress view and wait for sdk notifications.
            cardListActivity.progressShow(R.string.enrollment_state_inactive);

           SdkHelper.getInstance().getTshEnrollment().enrollCard("4147049980036808", "1225","123", cardListActivity);
//    SdkHelper.getInstance().getTshEnrollment().enrollCardWithToken(cardModel.getCipheredCardInfo(),cvv, cardListActivity);
            // Hide enrollemnt fragment.
            getMainActivity().hideCurrentFragment();
        });
    }

}

