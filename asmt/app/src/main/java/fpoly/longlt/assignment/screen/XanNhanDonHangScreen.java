package fpoly.longlt.assignment.screen;

import static android.app.PendingIntent.getActivity;
import static fpoly.longlt.assignment.screen.LoginScreen.id;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

import fpoly.longlt.assignment.R;
import fpoly.longlt.assignment.adapter.XacNhanDonHangAdapter;
import fpoly.longlt.assignment.model.Fruits;
import fpoly.longlt.assignment.model.Item;
import fpoly.longlt.assignment.model.Order;
import fpoly.longlt.assignment.model.ResponData;
import fpoly.longlt.assignment.service.HTTPRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class XanNhanDonHangScreen extends AppCompatActivity {
    ImageButton imgBtnEdit;
    Button btnPlaceOrder;
    Context context = this;
    RadioButton rd_offline, rd_online;
    TextView tvNameUserKL, tvPhoneKL, tv_payment_kiemdon, tvAddressKL, tvTotal, tvDiscount, tvShip, tvTotalPayment;
    RadioGroup rgPaymentMethod;
    RecyclerView rc_kiem_lai;
    ArrayList<Item> lst = new ArrayList<>();
    Fruits fruits = new Fruits();
    Fruits fruits1 = new Fruits();
    HTTPRequest httpRequest;
    String namefruit;
    int price;
    int total_price = 0;
    Order order;
    Item item;
    XacNhanDonHangAdapter adapter;
    ArrayList<Fruits> lstFruits = new ArrayList<>();
    ArrayList<Order> lstOrder = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_xan_nhan_don_hang_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        anhxa();
        Bundle bundle = getIntent().getExtras();
        httpRequest = new HTTPRequest();
        if (bundle != null) {
            item = new Item();
            item = (Item) bundle.getSerializable("fruits");
            Log.d("kg", "kg: "+item.getQuantity());
        }
            httpRequest.getApiservice()
                    .getFruitDetail(item.getFruitId())
                    .enqueue(getFruitDetail);


        Log.d("TAG", "onCreate: "+price);
        Log.d("TAG", "onCreate: "+namefruit);



//            tv_payment_kiemdon.setText(total_price+20000+" VND");
            btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    order = new Order();
                    item = (Item) bundle.getSerializable("fruits");
                    lst = new ArrayList<>();
                    lst.add(item);
                    order.setUserId(id);
//                    order.setStatus("pending");
                    order.setItems(lst);
                    Log.d("TAG", "oder: "+order.toString());
                    httpRequest = new HTTPRequest();
                    httpRequest.getApiservice()
                            .createOrder(order)
                            .enqueue(createOrder);
                    finish();
                }
            });
    }

        Callback<ResponData<Fruits>> getFruitDetail = new Callback<ResponData<Fruits>>() {
        @Override
        public void onResponse(Call<ResponData<Fruits>> call, Response<ResponData<Fruits>> response) {
            if (response.isSuccessful()){
                fruits = response.body().getData();

                namefruit = fruits.getNamefruit();
                price = fruits.getPrice();

                Log.d("TAG", "onResponse: "+fruits.getNamefruit());
                Log.d("TAG", "onResponse: "+fruits.getPrice());
//                fruits1 = new Fruits();
//                fruits1.setPrice(fruits.getPrice());
//                fruits1.setNamefruit(fruits.getNamefruit());
//                lstFruits.add(fruits1);
//                fruits = new Fruits();
                fruits.setKg(item.getQuantity());


                lstFruits.add(fruits);
                adapter = new XacNhanDonHangAdapter(lstFruits, XanNhanDonHangScreen.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                rc_kiem_lai.setLayoutManager(linearLayoutManager);
                rc_kiem_lai.setAdapter(adapter);
                total_price += item.getQuantity() * price;
                tvTotal.setText(total_price+" VND");
                tvTotalPayment.setText((total_price+20000)+" VND");
                tvShip.setText(20000+" VND");
            }
        }

        @Override
        public void onFailure(Call<ResponData<Fruits>> call, Throwable t) {

        }
    };
    Callback<ResponData<Order>> createOrder = new Callback<ResponData<Order>>() {
        @Override
        public void onResponse(Call<ResponData<Order>> call, Response<ResponData<Order>> response) {
            if (response.isSuccessful()){
                order = response.body().getData();
                Toast.makeText(XanNhanDonHangScreen.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "gettotal: "+order.getTotalPrice());
            }
        }

        @Override
        public void onFailure(Call<ResponData<Order>> call, Throwable t) {
            Log.d("TAG", "onFailure: " + t.getMessage());
        }
    };
    public void anhxa(){
        rd_offline = findViewById(R.id.rbCashOnDelivery);
        rd_online = findViewById(R.id.rbEWallet);
        rc_kiem_lai = findViewById(R.id.recyclerViewProducts);
        tvNameUserKL = findViewById(R.id.tvUserNameKL);
        tv_payment_kiemdon = findViewById(R.id.tv_payment_kiemdon);
        tvPhoneKL = findViewById(R.id.tvUserPhoneKL);
        tvAddressKL = findViewById(R.id.tvUserAddressKL);
        imgBtnEdit = findViewById(R.id.btnEditAddress);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        rgPaymentMethod = findViewById(R.id.radioGroupPayment);
        tvTotal = findViewById(R.id.tvSubtotal);
        tvTotalPayment = findViewById(R.id.tvTotalPayment);
        tvShip = findViewById(R.id.tvShippingFee);
    }
    public String getDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH); // Tháng bắt đầu từ 0 (January = 0)
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String toDay = year + "-" + (month + 1) + "-" + day;
        return toDay;
    }
}