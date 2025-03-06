package fpoly.longlt.assignment.screen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import fpoly.longlt.assignment.R;
import fpoly.longlt.assignment.model.ResponData;
import fpoly.longlt.assignment.model.User;
import fpoly.longlt.assignment.service.HTTPRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreen extends AppCompatActivity {
    Button btnLogin;
    TextView tvforgotpass, tvcreateaccount;
    EditText edemail, edpassword;
    CheckBox chkremember;
    ArrayList<User> lst = new ArrayList<>();
    HTTPRequest httpRequest;
    public  static String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        anhxa();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpRequest = new HTTPRequest();
                httpRequest.getApiservice()
                        .getAllUser()
                        .enqueue(getCallback);
            }
        });
        tvcreateaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, RegisterScreen.class));
            }
        });
    }
    public void anhxa(){
        btnLogin = findViewById(R.id.btn_login);
        tvforgotpass = findViewById(R.id.tv_forgot_pass);
        tvcreateaccount = findViewById(R.id.tv_create_account);
        edemail = findViewById(R.id.ed_email_login);
        edpassword = findViewById(R.id.ed_pass_login);
        chkremember = findViewById(R.id.chk_rememberme);
    }

    Callback<ResponData<List<User>>> getCallback = new Callback<ResponData<List<User>>>() {
        @Override
        public void onResponse(Call<ResponData<List<User>>> call, Response<ResponData<List<User>>> response) {
                if (response.isSuccessful()){
                    lst = (ArrayList<User>) response.body().getData();
                    checkLogInApi(lst);
                }
        }

        @Override
        public void onFailure(Call<ResponData<List<User>>> call, Throwable t) {

        }
    };

    private void checkLogInApi(ArrayList<User> users) {
        String email = edemail.getText().toString().trim();
        String pass = edpassword.getText().toString().trim();
        if (email.isEmpty() || pass.isEmpty()){
            Toast.makeText(this, "Nhap trong...", Toast.LENGTH_SHORT).show();
        }
        boolean isUser = false;
        boolean isAdmin = false;
        for (int i = 0; i < users.size(); i++){
            User userAPI = users.get(i);
            if (email.equals(userAPI.getEmail()) && pass.equals(userAPI.getPassword()) && userAPI.getRole()==0){
                isAdmin = true;
                break;
            }
            if (email.equals(userAPI.getEmail()) && pass.equals(userAPI.getPassword()) && userAPI.getRole()==1){
                isUser = true;
                break;
            }
        }

        if (isUser == true){
            httpRequest = new HTTPRequest();
            httpRequest.getApiservice().getIdUserByEmailAndPass(email, pass).enqueue(new Callback<ResponData<String>>() {
                @Override
                public void onResponse(Call<ResponData<String>> call, Response<ResponData<String>> response) {
                    if (response.isSuccessful()){
                        String user = response.body().getData();
                        if (user!=null){
                            id = user;
                            Log.d("TAG", "userID: "+id);
                        }
//                        httpRequest = new HTTPRequest();
//                        httpRequest.getApiservice().getAllUser().enqueue(getCallback);
                    }
                }

                @Override
                public void onFailure(Call<ResponData<String>> call, Throwable t) {
                    Log.d("TAG", "Userid: "+t.getMessage());
                }
            });
            Bundle bundle = new Bundle();
            Intent intent = new Intent();
            bundle.putString("userid", id);
            intent.putExtras(bundle);
            intent.setClass(LoginScreen.this, HomeScreen.class);
            startActivity(intent);
            finish();
        }
        if (isAdmin == true){
            startActivity(new Intent(LoginScreen.this,AdminScreen.class));
            finish();
        }
    }
}