package fpoly.longlt.assignment.screen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import fpoly.longlt.assignment.R;
import fpoly.longlt.assignment.model.ResponData;
import fpoly.longlt.assignment.model.User;
import fpoly.longlt.assignment.service.HTTPRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterScreen extends AppCompatActivity {
    Button btncreateaccount;
    TextView tvlogin;
    EditText edemail, edpass;
    ArrayList<User> lst = new ArrayList<>();
    HTTPRequest httpRequest;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        anhxa();
        tvlogin.setOnClickListener(v -> {
             finish();
        });
        btncreateaccount.setOnClickListener(v -> {
            String email = edemail.getText().toString();
            String pass = edpass.getText().toString();
            String parent = "[a-zA-Z0-9]+@gmail.com";
            if (email.isEmpty()||pass.isEmpty()){
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
            else {
                if (email.matches(parent)) {
                    user = new User();
                    user.setEmail(email);
                    user.setPassword(pass);
                    user.setRole(1);
                    user.setAvailable(true);
                    user.setName("");
                    user.setAvatar("");
                    user.setPhonenumber("");
                    user.setDate("");
                    httpRequest = new HTTPRequest();
                    httpRequest.getApiservice()
                            .createUser(user)
                            .enqueue(createUser);
                }
                else {
                    Toast.makeText(this, "Email không đúng định dạng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    Callback<ResponData<List<User>>> getUser = new Callback<ResponData<List<User>>>() {
        @Override
        public void onResponse(Call<ResponData<List<User>>> call, retrofit2.Response<ResponData<List<User>>> response) {
            if (response.isSuccessful()){
                ResponData<List<User>> responData = response.body();
                lst = (ArrayList<User>) responData.getData();
            }
        }
        @Override
        public void onFailure(Call<ResponData<List<User>>> call, Throwable t) {

        }
    };

    Callback<ResponData<User>> createUser = new Callback<ResponData<User>>() {
        @Override
        public void onResponse(Call<ResponData<User>> call, Response<ResponData<User>> response) {
            if (response.isSuccessful()){
                httpRequest = new HTTPRequest();
                httpRequest.getApiservice()
                        .getAllUser()
                        .enqueue(getUser);
                Toast.makeText(RegisterScreen.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
            else {
                Toast.makeText(RegisterScreen.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<ResponData<User>> call, Throwable t) {
            Log.d("API Error", "onFailure: "+t.getMessage());
            Toast.makeText(RegisterScreen.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
        }
    };

    public void anhxa() {
        btncreateaccount = (Button) findViewById(R.id.btn_create_account);
        tvlogin = (TextView) findViewById(R.id.tv_login_account);
        edemail = (EditText) findViewById(R.id.ed_email_register);
        edpass = (EditText) findViewById(R.id.ed_pass_register);    

    }
}