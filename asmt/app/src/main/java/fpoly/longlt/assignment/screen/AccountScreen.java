package fpoly.longlt.assignment.screen;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fpoly.longlt.assignment.R;

public class AccountScreen extends AppCompatActivity {
    ImageView img_back;
    EditText edfirstname, edlastname, edngaysinh, edphone;
    Button btncreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        anhxa();
        img_back.setOnClickListener(v -> finish());
    }

    public void anhxa(){
        img_back = findViewById(R.id.img_back);
        edfirstname = findViewById(R.id.ed_fisrt_name);
        edlastname = findViewById(R.id.ed_last_name);
        edngaysinh = findViewById(R.id.ed_date_born);
        edphone = findViewById(R.id.ed_phone);
        btncreate = findViewById(R.id.btn_crate_profile);
    }
}