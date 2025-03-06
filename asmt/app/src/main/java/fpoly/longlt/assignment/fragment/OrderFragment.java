package fpoly.longlt.assignment.fragment;

import static fpoly.longlt.assignment.screen.LoginScreen.id;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fpoly.longlt.assignment.R;
import fpoly.longlt.assignment.adapter.OrderAdapter;
import fpoly.longlt.assignment.model.Order;
import fpoly.longlt.assignment.model.ResponData;
import fpoly.longlt.assignment.model.User;
import fpoly.longlt.assignment.service.HTTPRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {
    TextView txtNameUserBills;
    ListView rc_content_bills;
    OrderAdapter adapter;
    ArrayList<Order> donHangArrayList = new ArrayList<>();
    HTTPRequest httpRequest;
    public OrderFragment() {
        // Required empty public constructor
    }

    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtNameUserBills = view.findViewById(R.id.txtNameUserBills);
        rc_content_bills = view.findViewById(R.id.rc_content_bills);
        httpRequest = new HTTPRequest();
        httpRequest.getApiservice().getInfomationUser(id).enqueue(getUser);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    public void loadData() {
        httpRequest = new HTTPRequest();
        httpRequest.getApiservice().getAllOrderByUser(id).enqueue(getAllOrderByUser);
    }

    Callback<ResponData<User>> getUser = new Callback<ResponData<User>>() {
        @Override
        public void onResponse(Call<ResponData<User>> call, Response<ResponData<User>> response) {
            if (response.isSuccessful()){
                User user = response.body().getData();
                txtNameUserBills.setText(user.getName());
            }
        }

        @Override
        public void onFailure(Call<ResponData<User>> call, Throwable t) {

        }
    };
    Callback<ResponData<List<Order>>> getAllOrderByUser = new Callback<ResponData<List<Order>>>() {
        @Override
        public void onResponse(Call<ResponData<List<Order>>> call, Response<ResponData<List<Order>>> response) {
            if (response.isSuccessful()){
                donHangArrayList = (ArrayList<Order>) response.body().getData();
                adapter = new OrderAdapter(getContext(),donHangArrayList);
                rc_content_bills.setAdapter(adapter);
            }
        }

        @Override
        public void onFailure(Call<ResponData<List<Order>>> call, Throwable t) {

        }
    };
}