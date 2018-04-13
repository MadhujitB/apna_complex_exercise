package demo.dada.com.apnacomplexexercise.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import demo.dada.com.apnacomplexexercise.DocumentCategoriesActivity;
import demo.dada.com.apnacomplexexercise.data.DocumentCategoriesData;
import demo.dada.com.apnacomplexexercise.R;

public class DocumentCategoriesAdapter extends RecyclerView.Adapter<DocumentCategoriesAdapter.ViewHolder> {

    private DocumentCategoriesActivity activity;
    private ArrayList<DocumentCategoriesData> arrayList;

    public DocumentCategoriesAdapter(DocumentCategoriesActivity activity,
                                     ArrayList<DocumentCategoriesData> arrayList)
    {
        this.activity = activity;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_document_categories_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        String cat_id = arrayList.get(holder.getAdapterPosition()).getCat_id();
        String cat_name = arrayList.get(holder.getAdapterPosition()).getCat_name();
        String num = arrayList.get(holder.getAdapterPosition()).getNum_docs();
        String num_docs = "(" + num + ")" ;

        switch (cat_id)
        {
            case "form" :
                holder.linearLayout.setBackgroundResource(R.drawable.form_bg);
                holder.imgView_setForms.setBackgroundResource(R.drawable.forms_ic);

                holder.textView_setFormName.setText(cat_name);
                holder.textView_contentView.setText(num_docs);

                break;

            case "image" :
                holder.linearLayout.setBackgroundResource(R.drawable.images_bg);
                holder.imgView_setForms.setBackgroundResource(R.drawable.images_ic);

                holder.textView_setFormName.setText(cat_name);
                holder.textView_contentView.setText(num_docs);

                break;

            case "my_docs" :
                holder.linearLayout.setBackgroundResource(R.drawable.mydocuments_bg);
                holder.imgView_setForms.setBackgroundResource(R.drawable.mydocuments_ic);

                holder.textView_setFormName.setText(cat_name);
                holder.textView_contentView.setText(num_docs);

                break;

            case "bank" :
                holder.linearLayout.setBackgroundResource(R.drawable.bankstatement_bg);
                holder.imgView_setForms.setBackgroundResource(R.drawable.bankstatement_ic);

                holder.textView_setFormName.setText(cat_name);
                holder.textView_contentView.setText(num_docs);

                break;

            case "other" :
                holder.linearLayout.setBackgroundResource(R.drawable.otherdocuments_bg);
                holder.imgView_setForms.setBackgroundResource(R.drawable.otherdocuments_ic);

                holder.textView_setFormName.setText(cat_name);
                holder.textView_contentView.setText(num_docs);
                break;

        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.anotherActivity(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        ImageView imgView_setForms;
        TextView textView_setFormName;
        TextView textView_contentView;

        ViewHolder(View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.linearLayout);
            imgView_setForms = itemView.findViewById(R.id.imgView_setForms);
            textView_setFormName = itemView.findViewById(R.id.textView_setFormName);
            textView_contentView = itemView.findViewById(R.id.textView_contentView);

        }
    }
}
