package fpoly.longlt.assignment.screen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import fpoly.longlt.assignment.R;
import fpoly.longlt.assignment.model.ResponData;
import fpoly.longlt.assignment.model.Fruits;
import fpoly.longlt.assignment.service.HTTPRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFruits extends AppCompatActivity {
    EditText edt_name,edt_price,ed_description;
    Button btnthem;
    ImageView img;
    Uri imageUri;
    private ActivityResultLauncher<Intent> galleryLauncher;
    static final int PICK_IMAGE_REQUEST_CODE = 1;
    HTTPRequest httpRequest;
    ArrayList<Fruits> lst = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_fruits);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imageUri = result.getData().getData();
                        if (imageUri != null) {
                            img.setImageURI(imageUri);
                        }
                    }
                }
        );

        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fruits fruits = new Fruits();
                String name = edt_name.getText().toString();
                String price = edt_price.getText().toString();
                String description = ed_description.getText().toString();
                if (name.isEmpty()||price.isEmpty()||description.isEmpty()||imageUri==null){
                    Toast.makeText(AddFruits.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    fruits.setNamefruit(edt_name.getText().toString());
                    fruits.setPrice(Integer.parseInt(edt_price.getText().toString()));
                    fruits.setDescription(ed_description.getText().toString());
                    fruits.setImg(saveImageToInternalStorage(imageUri));
                    fruits.setLike(false);
                    fruits.setStatus(true);
                    httpRequest = new HTTPRequest();
                    httpRequest.getApiservice()
                            .addFruit(fruits)
                            .enqueue(addsp);
                    finish();
                }
            }
        });
    }

    Callback<ResponData<List<Fruits>>> getsp = new Callback<ResponData<List<Fruits>>>(){
        @Override
        public void onResponse(Call<ResponData<List<Fruits>>> call, Response<ResponData<List<Fruits>>> response) {
            if (response.isSuccessful()){
                if (response.body().getStatus() == 200){
                    lst = (ArrayList<Fruits>) response.body().getData();
                    Log.e("Distributoradc",""+lst.size());
                    Log.e("API Response", new Gson().toJson(response.body()));
                }
            }
        }

        @Override
        public void onFailure(Call<ResponData<List<Fruits>>> call, Throwable t) {
            Toast.makeText(AddFruits.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
        }
    };

    Callback<ResponData<Fruits>> addsp = new Callback<ResponData<Fruits>>(){
        @Override
        public void onResponse(Call<ResponData<Fruits>> call, Response<ResponData<Fruits>> response) {
            if (response.isSuccessful()){
                if (response.body().getStatus()==200){
                    httpRequest.getApiservice()
                            .getAllFruit()
                            .enqueue(getsp);
                    Toast.makeText(AddFruits.this, response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                    Log.e("API Response", new Gson().toJson(response.body()));
                }
            }else {
                // Nếu không thành công (mã lỗi HTTP)
                Log.e("API Error", "Response failed: " + response.code());
            }
        }

        @Override
        public void onFailure(Call<ResponData<Fruits>> call, Throwable t) {
            Log.e("API Error", "Failure: " + t.getMessage());
            t.printStackTrace(); // In chi tiết lỗi ra logcat
        }
    };

    public void initView(){
        img = findViewById(R.id.img_sp);
        edt_name=findViewById(R.id.ed_name);
        edt_price=findViewById(R.id.ed_price);
        ed_description=findViewById(R.id.ed_description);
        btnthem=findViewById(R.id.btnthem);
    }
    private String saveImageToInternalStorage(Uri imageUri) {
        try {
            // Mở InputStream từ URI
            InputStream inputStream = getContentResolver().openInputStream(imageUri);

            // Tạo tệp trong bộ nhớ trong
            File directory = getFilesDir();  // Lấy thư mục trong bộ nhớ trong của ứng dụng
            File file = new File(directory, "image_" + System.currentTimeMillis() + ".jpg");  // Đặt tên cho ảnh

            // Viết ảnh vào tệp
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();

            // Trả về đường dẫn của tệp
            return file.getAbsolutePath();  // Đường dẫn đến tệp đã lưu trong bộ nhớ trong
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PICK_IMAGE_REQUEST_CODE
            );
        } else {
            openGallery();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PICK_IMAGE_REQUEST_CODE && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }
}