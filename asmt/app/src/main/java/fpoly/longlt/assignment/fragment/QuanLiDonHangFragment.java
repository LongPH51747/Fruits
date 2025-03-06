package fpoly.longlt.assignment.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fpoly.longlt.assignment.R;
import fpoly.longlt.assignment.adapter.OrderAdminAdapter;
import fpoly.longlt.assignment.model.Order;
import fpoly.longlt.assignment.model.ResponData;
import fpoly.longlt.assignment.service.HTTPRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuanLiDonHangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuanLiDonHangFragment extends Fragment {
    ArrayList<Order> lst = new ArrayList<>();
    ListView lv_donhang;
    OrderAdminAdapter adapter;
    HTTPRequest httpRequest;
    public QuanLiDonHangFragment() {
        // Required empty public constructor
    }

    public static QuanLiDonHangFragment newInstance() {
        QuanLiDonHangFragment fragment = new QuanLiDonHangFragment();
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
        return inflater.inflate(R.layout.fragment_quan_li_don_hang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv_donhang = view.findViewById(R.id.lv_donhangmanagment);
        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    public void loadData(){
        httpRequest = new HTTPRequest();
        httpRequest.getApiservice().getAllOrder().enqueue(getAllOrder);
    }

    Callback<ResponData<List<Order>>> getAllOrder = new Callback<ResponData<List<Order>>>() {
        @Override
        public void onResponse(Call<ResponData<List<Order>>> call, Response<ResponData<List<Order>>> response) {
            if (response.isSuccessful()){
                lst = (ArrayList<Order>) response.body().getData();
                adapter = new OrderAdminAdapter(lst,getActivity());
                lv_donhang.setAdapter(adapter);
            }
        }

        @Override
        public void onFailure(Call<ResponData<List<Order>>> call, Throwable t) {

        }
    };
}