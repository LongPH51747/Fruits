package fpoly.longlt.assignment.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fpoly.longlt.assignment.R;
import fpoly.longlt.assignment.adapter.SP_Admin_Adapter;
import fpoly.longlt.assignment.model.ResponData;
import fpoly.longlt.assignment.model.Fruits;
import fpoly.longlt.assignment.screen.AddFruits;
import fpoly.longlt.assignment.service.HTTPRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAddFruit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddFruit extends Fragment {
    TextView btn_add;
    RecyclerView rc_sp;
    ArrayList<Fruits> lst = new ArrayList<>();
    SP_Admin_Adapter adapter;
    HTTPRequest httpRequest = new HTTPRequest();
    Uri imageUri;
    Fruits fruits;
    private ActivityResultLauncher<Intent> galleryLauncher;
    static final int PICK_IMAGE_REQUEST_CODE = 1;
    public FragmentAddFruit() {
        // Required empty public constructor
    }

    public static FragmentAddFruit newInstance() {
        FragmentAddFruit fragment = new FragmentAddFruit();
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
        return inflater.inflate(R.layout.fragment_quan_li_fruit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_add = view.findViewById(R.id.btn_add_sp);
        rc_sp = view.findViewById(R.id.rc_quanlisp);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddFruits.class);
                startActivity(intent);
            }
        });
        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    public void loadData(){
        HTTPRequest httpRequest = new HTTPRequest();
        httpRequest.getApiservice().getAllFruit().enqueue(getsp);
    }
    Callback<ResponData<List<Fruits>>> getsp = new Callback<ResponData<List<Fruits>>>(){
        @Override
        public void onResponse(Call<ResponData<List<Fruits>>> call, Response<ResponData<List<Fruits>>> response) {
            if (response.isSuccessful()){
                if (response.body().getStatus() == 200){
                    lst = (ArrayList<Fruits>) response.body().getData();
                    Log.e("Distributoradc",""+lst.size());
                    adapter = new SP_Admin_Adapter(lst,getActivity());
                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                    rc_sp.setLayoutManager(layoutManager);
                    rc_sp.setAdapter(adapter);
                }
            }
        }

        @Override
        public void onFailure(Call<ResponData<List<Fruits>>> call, Throwable t) {

        }
    };
//    Callback<ResponData<List<SanPham>>> addsp = new Callback<ResponData<List<SanPham>>>(){
//        @Override
//        public void onResponse(Call<ResponData<List<SanPham>>> call, Response<ResponData<List<SanPham>>> response) {
//            if (response.isSuccessful()){
//                if (response.body().getStatus()==200){
//                    httpRequest.getApiservice()
//                            .getAllFruit()
//                            .enqueue(getsp);
//                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//
//        @Override
//        public void onFailure(Call<ResponData<List<SanPham>>> call, Throwable t) {
//
//        }
//    };

}