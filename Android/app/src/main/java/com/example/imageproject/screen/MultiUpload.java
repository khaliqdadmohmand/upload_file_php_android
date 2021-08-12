package com.example.imageproject.screen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.imageproject.FileModel;
import com.example.imageproject.FileUtils;
import com.example.imageproject.HttpService;
import com.example.imageproject.R;
import com.example.imageproject.RetrofitBuilder;
import com.example.imageproject.adapter.ImageListAdapter;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MultiUpload extends AppCompatActivity {

    private Button btnAdd, btnSubmit;
    private ListView imageList;
    private List<Uri> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_upload);

        btnAdd = findViewById(R.id.btn_add);
        btnSubmit = findViewById(R.id.btn_upload);
        imageList = findViewById(R.id.image_list);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*"); //allow any image file type.
                gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(gallery, 1);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadToServer();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode !=RESULT_CANCELED){

            switch (requestCode){
                case 1:
                    if(resultCode == RESULT_OK && data !=null){

                        int count = data.getClipData().getItemCount();
                        for(int i=0; i<count; i++){
                            Uri image = data.getClipData().getItemAt(i).getUri();
                            String imagePath = FileUtils.getPath(MultiUpload.this,image);
                            images.add(Uri.parse(imagePath));
                        }

                        ImageListAdapter adapter = new ImageListAdapter(MultiUpload.this,images);
                        imageList.setAdapter(adapter);
                    }
            }

        }
    }

    //method to call api to upload files to server
    public void uploadToServer(){

       List<MultipartBody.Part> list = new ArrayList<>();
       for(Uri uri: images){
           list.add(prepairFiles("file[]", uri));
       }

        HttpService service = RetrofitBuilder.getClient().create(HttpService.class);
        Call<FileModel> call = service.callMultipleUploadApi(list);
        call.enqueue(new Callback<FileModel>() {
            @Override
            public void onResponse(Call<FileModel> call, Response<FileModel> response) {
                FileModel model = response.body();
                Toast.makeText(MultiUpload.this, model.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<FileModel> call, Throwable t) {
                Toast.makeText(MultiUpload.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @NonNull
    private MultipartBody.Part prepairFiles(String partName, Uri fileUri){
        File file = new File( fileUri.getPath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);

        return  MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }
}