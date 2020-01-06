package com.example.basicautentication.api;

import android.util.Base64;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceBA {
    private static final String BASE_URL_BA="http://181.64.218.36:8045/";
    private static WebServiceBA instance;
    private Retrofit retrofit;
    private HttpLoggingInterceptor httpLoggingInterceptor;
    private OkHttpClient.Builder okhttpClientBuilder;

    private static final String AUTH_ADMIN = "Basic "+ Base64.encodeToString(("alberto:alberto").getBytes(),Base64.NO_WRAP);
    private static final String AUTH_USER = "Basic "+ Base64.encodeToString(("user:b14361404c078ffd549c03db443c3fede2f3e534d73f78f77301ed97d4a436a9fd9db05ee8b325c0ad36438b43fec8510c204fc1c1edb21d0941c00e9e2c1ce2").getBytes(),Base64.NO_WRAP);

    private WebServiceBA(){
          httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
          okhttpClientBuilder = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor);
          /*
          okhttpClientBuilder.addInterceptor(new Interceptor() {
              @Override
              public Response intercept(Chain chain) throws IOException {
                   Request original =chain.request();
                   Request.Builder requestBuilder = original.newBuilder().addHeader("Authorization",AUTH_USER)
                                    .method(original.method(),original.body());

                   Request request = requestBuilder.build();
                   return chain.proceed(request);
              }
          });    */
          retrofit = new Retrofit.Builder().baseUrl(BASE_URL_BA).client(okhttpClientBuilder.build()).addConverterFactory(GsonConverterFactory.create()).build();

    }

    public static synchronized WebServiceBA  getInstance(){
        if(instance==null){
            instance= new WebServiceBA();
        }
        return instance;
    }


    public <C> C createService(Class<C> service){
        return retrofit.create(service);
    }
}
