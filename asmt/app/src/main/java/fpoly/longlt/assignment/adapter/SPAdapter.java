package fpoly.longlt.assignment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

import fpoly.longlt.assignment.R;
import fpoly.longlt.assignment.model.Fruits;
import fpoly.longlt.assignment.screen.FruitDetailScreen;

public class SPAdapter extends RecyclerView.Adapter<SPAdapter.SPHolder> {
    ArrayList<Fruits> lst = new ArrayList<>();
    Context context;

    public SPAdapter(ArrayList<Fruits> lst, Context context) {
        this.lst = lst;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        if (lst != null){
            return lst.size();
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull SPHolder holder, @SuppressLint("RecyclerView") int position) {
        if (lst.size()>0){
//            holder.img_sp.setImageResource(lst.get(position).getImg());
            Fruits fruits = lst.get(position);
            holder.tvNameSP.setText(fruits.getNamefruit());
            holder.tvPriceSP.setText("đ "+ fruits.getPrice());
            holder.tv_hethang.setVisibility(View.GONE);
            holder.overlay.setVisibility(View.GONE);
            if (fruits.isStatus()==false){
                holder.tv_hethang.setVisibility(View.VISIBLE);
            }
            try {
                String imgPath = fruits.getImg();  // Lấy đường dẫn tệp từ SQLite
                File imgFile = new  File(imgPath);  // Tạo đối tượng File từ đường dẫn
                if(imgFile.exists()) {
                    Uri uri = Uri.fromFile(imgFile);  // Chuyển đường dẫn thành URI
                    holder.imgSP.setImageURI(uri);  // Đặt URI vào ImageView
                }
            } catch (Exception e){
                holder.imgSP.setImageResource(R.drawable.ic_launcher_background);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lst.get(position).isStatus()==false){
                    holder.itemView.setFocusable(false);
                }
                else {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent();
                    bundle.putSerializable("fruits", lst.get(position));
                    intent.putExtras(bundle);
                    intent.setClass(context, FruitDetailScreen.class);
                    context.startActivity(intent);
                }
            }
        });
    }

    @NonNull
    @Override
    public SPHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_sp_kh, parent, false);
        return new SPHolder(view);
    }

    public static final class SPHolder extends RecyclerView.ViewHolder {
        ImageView imgSP, imgLove;
        TextView tvNameSP, tvPriceSP;
        View overlay;
        CardView tv_hethang;

        public SPHolder(@NonNull View itemView) {
            super(itemView);
            imgLove = itemView.findViewById(R.id.img_like_sp);
            imgSP = itemView.findViewById(R.id.img_sp_kh);
            tvNameSP = itemView.findViewById(R.id.tv_name_sp_kh);
            tvPriceSP = itemView.findViewById(R.id.tv_price_sp_kh);
            overlay = itemView.findViewById(R.id.overlay);
            tv_hethang = itemView.findViewById(R.id.tv_hethang);

        }
    }
}
