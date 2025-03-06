package fpoly.longlt.assignment.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fpoly.longlt.assignment.R;
import fpoly.longlt.assignment.adapter.CartAdapter;
import fpoly.longlt.assignment.model.Cart;
import fpoly.longlt.assignment.model.Fruits;
import fpoly.longlt.assignment.model.ResponData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {
    ArrayList<Cart> lst = new ArrayList<>();
    ListView lv;
    CartAdapter adapter;
    Button btn_order;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
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
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = view.findViewById(R.id.lv_cart);
        btn_order = view.findViewById(R.id.btn_order);
    }

    Callback<ResponData<List<Cart>>> getCart = new Callback<ResponData<List<Cart>>>() {
        @Override
        public void onResponse(Call<ResponData<List<Cart>>> call, Response<ResponData<List<Cart>>> response) {
            if (response.isSuccessful()){
                lst = (ArrayList<Cart>) response.body().getData();
                adapter = new CartAdapter(getContext(), lst);
                lv.setAdapter(adapter);
            }
        }

        @Override
        public void onFailure(Call<ResponData<List<Cart>>> call, Throwable t) {

        }
    };
}