package com.example.doorbellcamera.Notification;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAA8EoDXc4:APA91bFBYxDspROJxRIdgvdIZljTwZHQX-q8H0Gb4-0fxYttGUrkICSBa2qHs5_31gy6DQrUeccGTDr_hTX6dfF5m-Sw_PHPlNULagK7ystJtmwzUdPSxtv0xgWObLBJtW39nwIWbXva"
    })

    @POST("fcm/send")
    Call<Response> sendNotification(@Body Sender body);
}
