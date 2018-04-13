package demo.dada.com.apnacomplexexercise.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import demo.dada.com.apnacomplexexercise.MainActivity;
import demo.dada.com.apnacomplexexercise.data.MainData;
import demo.dada.com.apnacomplexexercise.R;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private MainActivity activity;
    private ArrayList<MainData> arrayList;

    public MainAdapter(MainActivity activity, ArrayList<MainData> arrayList)
    {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_main_data_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        int image = arrayList.get(holder.getAdapterPosition()).getImage();
        String name = arrayList.get(holder.getAdapterPosition()).getName();

        holder.imgView_setBgImg.setBackgroundResource(image);
        holder.textView_setNameBottom.setText(name);

        if(holder.getAdapterPosition() == 0)
            holder.imgView_setBgImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.moveToAnotherActivity();
                }
            });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgView_setBgImg;
        TextView textView_setNameBottom;

        ViewHolder(View itemView) {
            super(itemView);

            imgView_setBgImg = itemView.findViewById(R.id.imgView_setBgImg);
            textView_setNameBottom = itemView.findViewById(R.id.textView_setNameBottom);
        }
    }
}
