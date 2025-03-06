package fpoly.longlt.assignment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fpoly.longlt.assignment.R;
import fpoly.longlt.assignment.model.Fruits;
import fpoly.longlt.assignment.model.ResponData;
import fpoly.longlt.assignment.service.HTTPRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SP_Admin_Adapter extends RecyclerView.Adapter<SP_Admin_Adapter.SP_Admin_Holder> {
    ArrayList<Fruits> lst = new ArrayList<>();
    Context context;
    HTTPRequest httpRequest;
    Fruits fruits;

    public SP_Admin_Adapter(ArrayList<Fruits> lst, Context context) {
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
    public void onBindViewHolder(@NonNull SP_Admin_Holder holder, @SuppressLint("RecyclerView") int position) {
        Fruits fruit = lst.get(position);
        holder.tv_name_sp_admin.setText(fruit.getNamefruit());
        holder.tv_price_sp_admin.setText(fruit.getPrice()+"");
        Log.d("adapter", "ten: "+fruit.getNamefruit());
        Log.d("adapter", "gia: "+fruit.getPrice());
        if (fruit.isStatus()){
            holder.chk_status_sp_admin.setChecked(true);
            holder.tv_name_sp_admin.setPaintFlags(holder.tv_name_sp_admin.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.tv_price_sp_admin.setPaintFlags(holder.tv_price_sp_admin.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        else {
            holder.chk_status_sp_admin.setChecked(false);
            holder.tv_name_sp_admin.setPaintFlags(holder.tv_name_sp_admin.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tv_price_sp_admin.setPaintFlags(holder.tv_price_sp_admin.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (lst.size() > 0) {
            try {
                String imgPath = fruit.getImg();  // Lấy đường dẫn tệp từ SQLite
                File imgFile = new  File(imgPath);  // Tạo đối tượng File từ đường dẫn
                if(imgFile.exists()) {
                    Uri uri = Uri.fromFile(imgFile);  // Chuyển đường dẫn thành URI
                    holder.img_sp_admin.setImageURI(uri);  // Đặt URI vào ImageView
                }
            } catch (Exception e){
                holder.img_sp_admin.setImageResource(R.drawable.ic_launcher_background);
            }
        }
        holder.chk_status_sp_admin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                httpRequest = new HTTPRequest();
                fruits = new Fruits();
                fruits.setStatus(isChecked);
                httpRequest.getApiservice().updateStatus(lst.get(position).get_id(), fruits).enqueue(updateStatus);
                httpRequest.getApiservice().getAllFruit().enqueue(getFruit);
            }
        });
    }

    Callback<ResponData<Fruits>> updateStatus = new Callback<ResponData<Fruits>>() {
        @Override
        public void onResponse(Call<ResponData<Fruits>> call, Response<ResponData<Fruits>> response) {
            if (response.isSuccessful()){
                fruits = response.body().getData();
            }
        }

        @Override
        public void onFailure(Call<ResponData<Fruits>> call, Throwable t) {

        }
    };
    Callback<ResponData<List<Fruits>>> getFruit = new Callback<ResponData<List<Fruits>>>() {
        @Override
        public void onResponse(Call<ResponData<List<Fruits>>> call, Response<ResponData<List<Fruits>>> response) {
            if (response.isSuccessful()){
                lst = (ArrayList<Fruits>) response.body().getData();
                notifyDataSetChanged();
            }
        }

        @Override
        public void onFailure(Call<ResponData<List<Fruits>>> call, Throwable t) {

        }
    };
    @NonNull
    @Override
    public SP_Admin_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SP_Admin_Holder(View.inflate(context, R.layout.item_sp_admin, null));
    }

    public static final class SP_Admin_Holder extends RecyclerView.ViewHolder {
        ImageView img_sp_admin;
        TextView tv_name_sp_admin, tv_price_sp_admin;
        CheckBox chk_status_sp_admin;
        public SP_Admin_Holder(@NonNull View itemView) {
            super(itemView);
            img_sp_admin = itemView.findViewById(R.id.img_sp_admin);
            tv_name_sp_admin = itemView.findViewById(R.id.tv_name_sp_admin);
            tv_price_sp_admin = itemView.findViewById(R.id.tv_price_sp_admin);
            chk_status_sp_admin = itemView.findViewById(R.id.chk_hidden_sp);
        }
    }
}
