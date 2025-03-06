package fpoly.longlt.assignment.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.longlt.assignment.R;
import fpoly.longlt.assignment.model.Order;

public class OrderAdminAdapter extends BaseAdapter {
    ImageView img_anh;
    TextView tv_ma_dh, tv_ten_kh, tv_ngay_dat, tv_tong_tien, tv_status, tv_soluong;
    Button btn_xacnhan, btn_huy, btn_dagiaohang;
    ArrayList<Order> lst = new ArrayList<>();
    Context context;

    public OrderAdminAdapter(ArrayList<Order> lst, Context context) {
        this.lst = lst;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lst!=null?lst.size():0;
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
        convertView = View.inflate(context, R.layout.list_item_quanlidonhang_admin, null);
        img_anh = convertView.findViewById(R.id.img_sp_donhang_admin);
        tv_ma_dh = convertView.findViewById(R.id.tv_ma_don_hang_admin);
        tv_ten_kh = convertView.findViewById(R.id.tv_ten_khach_hang_admin);
        tv_ngay_dat = convertView.findViewById(R.id.tv_ngay_dat_hang_admin);
        tv_tong_tien = convertView.findViewById(R.id.tv_tong_gia_tri_admin);
        tv_status = convertView.findViewById(R.id.tv_status_donhang_admin);
        tv_soluong = convertView.findViewById(R.id.tv_soluong_don_hang_admin);
        btn_xacnhan = convertView.findViewById(R.id.btn_xac_nhan_donhang_adin);
        btn_dagiaohang = convertView.findViewById(R.id.btn_giaohang_thanhcong);
        btn_huy = convertView.findViewById(R.id.btn_huy_donhang_admin);

        Order order = lst.get(position);
        tv_ma_dh.setText(order.getId());
        tv_ten_kh.setText(order.getUserId());
        tv_ngay_dat.setText(order.getCreatedAt());
        tv_tong_tien.setText(order.getTotalPrice()+" VND");
        tv_soluong.setText(order.getQuantity()+" kg");
        if (order.getStatus().equals("pending")){
            tv_status.setText("Chờ xác nhận");
        }else if (order.getStatus().equals("accepted")){
            tv_status.setText("Đã xác nhận");
        }
        else if (order.getStatus().equals("delivered")){
            tv_status.setText("Đã giao hàng");
        }

        return convertView;
    }
}
