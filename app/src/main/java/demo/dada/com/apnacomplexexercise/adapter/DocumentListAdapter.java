package demo.dada.com.apnacomplexexercise.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import demo.dada.com.apnacomplexexercise.DocumentListActivity;
import demo.dada.com.apnacomplexexercise.data.DocumentListData;
import demo.dada.com.apnacomplexexercise.R;

public class DocumentListAdapter extends RecyclerView.Adapter<DocumentListAdapter.ViewHolder> {

    private DocumentListActivity activity;
    private ArrayList<DocumentListData> arrayList;
    private Typeface robotoFonts_Regular, robotoFonts_Light;

    public DocumentListAdapter(DocumentListActivity activity, ArrayList<DocumentListData> arrayList)
    {
        this.activity = activity;
        this.arrayList = arrayList;
        robotoFonts_Regular = Typeface.createFromAsset(activity.getAssets(), "Roboto-Regular.ttf");
        robotoFonts_Light = Typeface.createFromAsset(activity.getAssets(), "Roboto-Light.ttf");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_document_list_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final String imgUrl = arrayList.get(holder.getAdapterPosition()).getDoc_url();
        String doc_name = arrayList.get(holder.getAdapterPosition()).getDoc_name();
        String doc_size = arrayList.get(holder.getAdapterPosition()).getDoc_size();

        Picasso.get()
                .load(imgUrl)
                .placeholder(R.drawable.ic_no_image)
                .error(R.drawable.ic_no_image)
                .into(holder.imgView_setImg);

        holder.textView_imgName.setText(doc_name);
        holder.textView_imgName.setTypeface(robotoFonts_Regular, Typeface.BOLD);

        holder.textView_imgSize.setText(doc_size);
        holder.textView_imgSize.setTypeface(robotoFonts_Light);

        if(holder.getAdapterPosition() == arrayList.size() - 1)
            holder.setView.setVisibility(View.INVISIBLE);
        else
            holder.setView.setVisibility(View.VISIBLE);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(imgUrl);
                String fileName = file.getName();
                Log.d("Document List Adapter", fileName);

                activity.downloadFile(fileName, imgUrl);
            }
        });

        holder.imgView_setDots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.snackbarAction();
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        RelativeLayout relativeLayout;
        ImageView imgView_setImg;
        TextView textView_imgName;
        TextView textView_imgSize;
        View setView;
        ImageView imgView_setDots;

        ViewHolder(View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.relativeLayout);
            imgView_setImg = itemView.findViewById(R.id.imgView_setImg);
            textView_imgName = itemView.findViewById(R.id.textView_imgName);
            textView_imgSize = itemView.findViewById(R.id.textView_imgSize);
            setView = itemView.findViewById(R.id.setView);
            imgView_setDots = itemView.findViewById(R.id.imgView_setDots);
        }
    }
}
