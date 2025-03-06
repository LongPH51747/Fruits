package fpoly.longlt.assignment.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.io.File;
import java.util.ArrayList;

import fpoly.longlt.assignment.R;
import fpoly.longlt.assignment.model.Order;

public class OrderAdapter extends BaseAdapter {
    Context context;
    ArrayList<Order> list;
    CardView card_bill_od;
    ImageView img_sp_bills;
    TextView txt_ma_bills, txt_date_bills, txt_show_more, tv_tongtien;

    public OrderAdapter(Context context, ArrayList<Order> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list!=null?list.size():0;
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
        convertView = View.inflate(context, R.layout.list_item_quanli_donhang, null);
        tv_tongtien = convertView.findViewById(R.id.tv_total_price_order);
        img_sp_bills = convertView.findViewById(R.id.img_sp_donhang);
        txt_ma_bills = convertView.findViewById(R.id.tv_ma_donhang);
        txt_date_bills = convertView.findViewById(R.id.txt_date_bill);
        txt_show_more = convertView.findViewById(R.id.txtShowMoreBills);
        card_bill_od = convertView.findViewById(R.id.card_bills_od);

        Order order = list.get(position);
        tv_tongtien.setText(order.getTotalPrice()+" VND");
        txt_ma_bills.setText(order.getId()+"");
        txt_date_bills.setText(order.getCreatedAt());
        try {
            String imgPath = order.getImg();  // Lấy đường dẫn tệp từ SQLite
            File imgFile = new  File(imgPath);  // Tạo đối tượng File từ đường dẫn
            if(imgFile.exists()) {
                Uri uri = Uri.fromFile(imgFile);  // Chuyển đường dẫn thành URI
                img_sp_bills.setImageURI(uri);  // Đặt URI vào ImageView
            }
        } catch (Exception e){
            img_sp_bills.setImageResource(R.drawable.ic_launcher_background);
        }
        txt_show_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }
}
