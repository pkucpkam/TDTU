package com.example.bai1.api;

import com.example.bai1.model.Student;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();

    ApiService apiService = new Retrofit.Builder().baseUrl("http://10.0.2.2/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("get-students.php")
    Call<ApiResponse<List<Student>>> getAllStudent();

    @FormUrlEncoded
    @POST("add-student.php")
    Call<ApiResponse<Integer>> createStudent(@Field("name") String name, @Field("email") String email, @Field("phone") String phone);

    @FormUrlEncoded
    @POST("update-student.php")
    Call<ApiResponse<Student>> updateStudent(@Field("id") Integer id, @Field("name") String name, @Field("email") String email, @Field("phone") String phone);

    @FormUrlEncoded
    @POST("delete-student.php")
    Call<ApiResponse<Student>> deleteStudent(@Field("id") Integer id);
}
