package fpoly.longlt.assignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.longlt.assignment.R;
import fpoly.longlt.assignment.model.Fruits;

public class LoveAdapter extends BaseAdapter {
    Context context;
    ArrayList<Fruits> lst = new ArrayList<>();
    ImageView img_sp_love, img_like;
    TextView tv_ten_sp, tv_gia_sp, tv_kg_cart;

    public LoveAdapter(Context context, ArrayList<Fruits> lst) {
        this.context = context;
        this.lst = lst;
    }

    @Override
    public int getCount() {
        if (lst != null){
            return lst.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_love, parent,false);
        img_sp_love = view.findViewById(R.id.img_sp_love);
        img_like = view.findViewById(R.id.img_like);
        tv_ten_sp = view.findViewById(R.id.tv_ten_love);
        tv_gia_sp = view.findViewById(R.id.tv_price_love);

//        img_sp_love.setImageResource(lst.get(position).getImg());
        tv_ten_sp.setText("Tên: " + lst.get(position).getNamefruit());
        tv_gia_sp.setText("Giá: " + lst.get(position).getPrice());

        return view;
    }
}
