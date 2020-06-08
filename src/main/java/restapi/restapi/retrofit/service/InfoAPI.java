package restapi.restapi.retrofit.service;

import restapi.restapi.retrofit.model.ResponseDto;
import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.*;

public interface InfoAPI {


    @FormUrlEncoded
    @POST("/cotest/index.php/events/scratch/get_user_point")
    Call<ResponseDto> getInfoList(@Field("id") String id);

    @GET("/")
    Call<ResponseDto> getInfo(String name);
}
