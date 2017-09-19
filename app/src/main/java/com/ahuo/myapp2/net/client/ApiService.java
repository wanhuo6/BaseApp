package com.ahuo.myapp2.net.client;


import com.ahuo.myapp2.entity.response.GetUserResponse;
import com.ahuo.myapp2.entity.response.LoginResponse;
import com.ahuo.myapp2.entity.response.RegisterResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created on 17-8-1
 *
 * @author liuhuijie
 */

public interface ApiService {

    @FormUrlEncoded
    @POST(NetUrls.REGISTER)
    Observable<RegisterResponse> register(@Field("name") String name, @Field("account") String account, @Field("password") String password);

    @GET(NetUrls.LOGIN)
    Observable<LoginResponse> getLogin(@Query("account") String account, @Query("password") String password);

    @GET(NetUrls.GET_USERS)
    Observable<GetUserResponse> getUsers();

}
