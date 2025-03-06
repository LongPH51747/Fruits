package fpoly.longlt.assignment.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

import fpoly.longlt.assignment.R;
import fpoly.longlt.assignment.model.Fruits;

public class XacNhanDonHangAdapter extends RecyclerView.Adapter<XacNhanDonHangAdapter.XacNhanDonHang> {
    ArrayList<Fruits> lst = new ArrayList<>();
    Context context;
    String nameString;
    public static int priceAmount, priceOneProduct;
    public XacNhanDonHangAdapter(ArrayList<Fruits> lst, Context context) {
        this.lst = lst;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return lst!=null?lst.size():0;
    }

    @Override
    public void onBindViewHolder(@NonNull XacNhanDonHang holder, int position) {
        Fruits fruits = lst.get(position);
        holder.name.setText("Name Product: "+ fruits.getNamefruit());
        holder.kg.setText(fruits.getKg()+" kg");
        holder.price.setText("Price: " + fruits.getPrice() +"đ");
        try {
            String imgPath = fruits.getImg();  // Lấy đường dẫn tệp từ SQLite
            File imgFile = new File(imgPath);  // Tạo đối tượng File từ đường dẫn
            if (imgFile.exists()) {
                Uri uri = Uri.fromFile(imgFile);  // Chuyển đường dẫn thành URI
                holder.imgPrepare.setImageURI(uri);  // Đặt URI vào ImageView
            }
            else {
                holder.imgPrepare.setImageResource(R.drawable.ic_launcher_background);
            }
        } catch (Exception e) {
            holder.imgPrepare.setImageResource(R.drawable.ic_launcher_background);
        }
    }


    @NonNull
    @Override
    public XacNhanDonHang onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new XacNhanDonHang(View.inflate(context, R.layout.item_prepare_order,null));
    }
    public static class XacNhanDonHang extends RecyclerView.ViewHolder {
        TextView name, kg, price;
        ImageView imgPrepare;
        public XacNhanDonHang(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.tvProductName);
            kg = view.findViewById(R.id.tv_kg);
            price = view.findViewById(R.id.tvProductPrice);
            imgPrepare = view.findViewById(R.id.ivProductImage);
        }
    }
}
