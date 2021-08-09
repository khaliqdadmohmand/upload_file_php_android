package com.example.imageproject;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface HttpService {

    @Multipart
    @POST("upload_file/upload_api.php")
    Call<FileModel> callUploadApi(@Part MultipartBody.Part image);
}
