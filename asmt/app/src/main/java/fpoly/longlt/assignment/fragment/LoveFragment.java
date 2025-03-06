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

import fpoly.longlt.assignment.R;
import fpoly.longlt.assignment.adapter.LoveAdapter;
import fpoly.longlt.assignment.model.Fruits;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoveFragment extends Fragment {
    ArrayList<Fruits> lst = new ArrayList<>();
    ListView lv;
    LoveAdapter adapter;
    public LoveFragment() {
        // Required empty public constructor
    }

    public static LoveFragment newInstance() {
        LoveFragment fragment = new LoveFragment();
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
        return inflater.inflate(R.layout.fragment_love, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = view.findViewById(R.id.lv_love);

        adapter = new LoveAdapter(getContext(), lst);
        lv.setAdapter(adapter);
    }
}