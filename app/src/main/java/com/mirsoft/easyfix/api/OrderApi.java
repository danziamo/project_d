package com.mirsoft.easyfix.api;

import com.mirsoft.easyfix.models.Order;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface OrderApi {

    @GET("/users/{id}/orders")
    void getByUserId(@Path("id") int id, Callback<ArrayList<Order>> callback);

    @GET("/users/{id}/orders/?status={statuses}")
    void getByUserIdAndStatuses(@Path("id") int id, @Path("statuses") String statuses, Callback<ArrayList<Order> > callback);

    @GET("/orders/{id}")
    void getById(@Path("id") int id, Callback<Order> callback);

    @POST("/users/{userId}/orders/{orderId}/post_contractor_request")
    void postRequest(@Body Object object, @Path("userId") int userId, @Path("orderId") int orderId, Callback<Object> callback);
}