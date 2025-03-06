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
import fpoly.longlt.assignment.model.Cart;
import fpoly.longlt.assignment.model.Fruits;

public class CartAdapter extends BaseAdapter {
    Context context;
    ArrayList<Cart> lst = new ArrayList<>();
    ImageView img_sp_cart, img_delete;
    TextView tv_ten_sp, tv_gia_sp, tv_kg_cart;

    public CartAdapter(Context context, ArrayList<Cart> lst) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_cart, parent,false);
        img_sp_cart = view.findViewById(R.id.img_sp_cart);
        img_delete = view.findViewById(R.id.img_delete);
        tv_ten_sp = view.findViewById(R.id.tv_ten_sp_cart);
        tv_gia_sp = view.findViewById(R.id.tv_price_sp_cart);
        tv_kg_cart = view.findViewById(R.id.tv_kg_sp_cart);

//        img_sp_cart.setImageResource(lst.get(position).getImg());
        tv_ten_sp.setText("Tên: " + lst.get(position).getFruitName());
        tv_gia_sp.setText("Giá: " + lst.get(position).getFruitPrice());
        tv_kg_cart.setText("kg: "+lst.get(position).getQuantity());

        return view;
    }
}
