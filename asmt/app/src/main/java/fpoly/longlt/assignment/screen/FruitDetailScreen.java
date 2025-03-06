package fpoly.longlt.assignment.screen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import fpoly.longlt.assignment.R;
import fpoly.longlt.assignment.model.Cart;
import fpoly.longlt.assignment.model.Fruits;
import fpoly.longlt.assignment.model.Item;
import fpoly.longlt.assignment.model.ResponData;
import fpoly.longlt.assignment.service.HTTPRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FruitDetailScreen extends AppCompatActivity {
    ImageView img_fruit, img_back;
    EditText ed_kg;
    TextView tv_price, tv_name, tv_mota;
    CardView btn_cong, btn_tru;
    Fruits fruits;
    HTTPRequest httpRequest;
    Button btn_add_cart, btn_buy_now;
    String userid = HomeScreen.userid;
    int kg = 0;
    ArrayList<Cart> lst = new ArrayList<>();
    Cart cart = new Cart();
    Item item = new Item();
    List<Item> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fruit_detail_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        anhxa();
        ed_kg.setText(String.valueOf(kg));
        ed_kg.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                kg = ed_kg.getText().toString().isEmpty() ? 0 : Integer.parseInt(ed_kg.getText().toString());
                return true;
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            fruits = (Fruits) bundle.getSerializable("fruits");
            Log.d("id", "idFruit: "+fruits.get_id());
            httpRequest = new HTTPRequest();
            httpRequest.getApiservice()
                    .getFruitDetail(fruits.get_id())
                    .enqueue(getFruitDetail);
        }
        kg = Integer.parseInt(ed_kg.getText().toString());
        btn_cong.setOnClickListener(v -> {
            kg += 1;
            ed_kg.setText(kg+" kg");
        });
        btn_tru.setOnClickListener(v -> {
            kg -= 1;
            if (kg<0) {
                kg = 0;
            }
            ed_kg.setText(kg+" kg");
        });
        img_back.setOnClickListener(v -> {
           finish();
        });
//        userid =
        btn_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                item.setFruitId(id);
//                item.setQuantity(kg);
//                items.add(item);
                cart.setUserId(userid);
                cart.setFruitId(fruits.get_id());
                cart.setQuantity(kg);
                cart.setFruitPrice(fruits.getPrice());
                cart.setFruitName(fruits.getNamefruit());
                httpRequest = new HTTPRequest();
                httpRequest.getApiservice()
                        .addToCart(cart)
                        .enqueue(addCart);
            }
        });
        btn_buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kg<=0){
                    Toast.makeText(FruitDetailScreen.this, "Xin mới nhập số kg bạn muốn mua", Toast.LENGTH_SHORT).show();
                }
                else {
                        fruits = (Fruits) bundle.getSerializable("fruits");
                        Item item = new Item();
                        Intent intent = new Intent(FruitDetailScreen.this, XanNhanDonHangScreen.class);
                        Bundle bundle1 = new Bundle();
                        item.setFruitId(fruits.get_id());
                        item.setQuantity(kg);
                        bundle1.putSerializable("fruits", item);
                        intent.putExtras(bundle1);
                        startActivity(intent);
                }
            }
        });
    }
    Callback<ResponData<Fruits>> getFruitDetail = new Callback<ResponData<Fruits>>() {
        @Override
        public void onResponse(Call<ResponData<Fruits>> call, Response<ResponData<Fruits>> response) {
            if (response.isSuccessful()){
                fruits = response.body().getData();
                img_fruit.setImageURI(Uri.parse(fruits.getImg()));
                tv_name.setText(fruits.getNamefruit());
                tv_price.setText("đ "+fruits.getPrice());
                tv_mota.setText(fruits.getDescription());
            }
        }

        @Override
        public void onFailure(Call<ResponData<Fruits>> call, Throwable t) {

        }
    };

    Callback<ResponData<Cart>> addCart = new Callback<ResponData<Cart>>() {
        @Override
        public void onResponse(Call<ResponData<Cart>> call, Response<ResponData<Cart>> response) {
            if (response.isSuccessful()){
                httpRequest = new HTTPRequest();
                httpRequest.getApiservice().getCart(userid).enqueue(new Callback<ResponData<List<Cart>>>() {
                    @Override
                    public void onResponse(Call<ResponData<List<Cart>>> call, Response<ResponData<List<Cart>>> response) {
                        if (response.isSuccessful()){
                            lst = (ArrayList<Cart>) response.body().getData();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponData<List<Cart>>> call, Throwable t) {

                    }
                });
            }
        }

        @Override
        public void onFailure(Call<ResponData<Cart>> call, Throwable t) {

        }
    };


    public void anhxa(){
        img_fruit = findViewById(R.id.img_sp_detail);
        img_back = findViewById(R.id.img_back_detail);
        ed_kg = findViewById(R.id.ed_kg_detail);
        btn_cong = findViewById(R.id.btn_cong);
        btn_tru = findViewById(R.id.btn_tru);
        tv_price = findViewById(R.id.tv_price_sp_detail);
        tv_name = findViewById(R.id.tv_name_sp_detail);
        tv_mota = findViewById(R.id.tv_mota_detail);
        btn_add_cart = findViewById(R.id.btn_add_cart);
        btn_buy_now = findViewById(R.id.btn_buy_now);
    }
}