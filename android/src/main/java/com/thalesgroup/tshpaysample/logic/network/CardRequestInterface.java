package com.thalesgroup.tshpaysample.logic.network;

import com.thalesgroup.tshpaysample.logic.network.model.RemoteCardDetail;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface  CardRequestInterface {
    @GET("ISWCardTokenization/api/internal/accountCardList/{accountNumber}")
    Call<RemoteCardDetail> getAllCard(@Path("accountNumber") String accountNumber);
}
