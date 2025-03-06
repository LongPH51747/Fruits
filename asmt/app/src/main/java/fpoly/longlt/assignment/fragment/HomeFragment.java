package fpoly.longlt.assignment.fragment;


import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fpoly.longlt.assignment.R;
import fpoly.longlt.assignment.adapter.BannerAdapter;
import fpoly.longlt.assignment.adapter.SPAdapter;
import fpoly.longlt.assignment.model.Fruits;
import fpoly.longlt.assignment.model.ResponData;
import fpoly.longlt.assignment.screen.AccountScreen;
import fpoly.longlt.assignment.service.HTTPRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    RecyclerView rc_sp;
    ImageView img_person, img_search;
    SPAdapter adapter;
    private Handler sliderHandler = new Handler();
    private int currentPage = 0;
    private static final int SLIDE_DELAY = 3000; // Thời gian chờ giữa các slide, tính bằng ms
    private ViewPager2 viewPager2;
    Fruits fruits;
    HTTPRequest httpRequest;
    ArrayList<Fruits> lst = new ArrayList<>();
    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        img_person = view.findViewById(R.id.img_person);
        img_search = view.findViewById(R.id.img_search);
        rc_sp = view.findViewById(R.id.rc_sp);
        viewPager2 = view.findViewById(R.id.viewPagerBanner);

        // Danh sách ảnh banner
        List<Integer> bannerImages = Arrays.asList(
                R.drawable.banner1,
                R.drawable.banner2,
                R.drawable.banner3,
                R.drawable.banner4
        );
        BannerAdapter bannerAdapter = new BannerAdapter(getContext(), bannerImages);
        viewPager2.setAdapter(bannerAdapter);

        // Thiết lập vòng lặp tự động
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPage = position;
                sliderHandler.removeCallbacks(slideRunnable);
                sliderHandler.postDelayed(slideRunnable, SLIDE_DELAY);
            }
        });
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                // Set margins
                outRect.left = 10;  // Khoảng cách trái
                outRect.right = 10; // Khoảng cách phải
                outRect.top = 10;   // Khoảng cách trên
                outRect.bottom = 10; // Khoảng cách dưới
            }
        };
        rc_sp.addItemDecoration(itemDecoration);

        img_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), AccountScreen.class));
            }
        });

    }


    public void loadData(){
        httpRequest = new HTTPRequest();
        httpRequest.getApiservice()
                .getAllFruit()
                .enqueue(getsp);
    }

    Callback<ResponData<List<Fruits>>> getsp = new Callback<ResponData<List<Fruits>>>(){
        @Override
        public void onResponse(Call<ResponData<List<Fruits>>> call, Response<ResponData<List<Fruits>>> response) {
            if (response.isSuccessful()){
                if (response.body().getStatus() == 200){
                    lst = (ArrayList<Fruits>) response.body().getData();
                    Log.e("Distributoradc",""+lst.size());
                    adapter = new SPAdapter(lst,getActivity());
                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL); // 2 cột
                    rc_sp.setLayoutManager(layoutManager);
                    rc_sp.setAdapter(adapter);
                }
            }
        }

        @Override
        public void onFailure(Call<ResponData<List<Fruits>>> call, Throwable t) {

        }
    };

    private final Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager2.getAdapter() != null) {
                int itemCount = viewPager2.getAdapter().getItemCount();
                currentPage = (currentPage + 1) % itemCount;
                viewPager2.setCurrentItem(currentPage, true);
                sliderHandler.postDelayed(this, SLIDE_DELAY);
            }
        }
    };
    @Override
    public void onResume() {
        super.onResume();
        loadData();
        sliderHandler.postDelayed(slideRunnable, SLIDE_DELAY);
    }

    @Override
    public void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(slideRunnable);
    }
}
