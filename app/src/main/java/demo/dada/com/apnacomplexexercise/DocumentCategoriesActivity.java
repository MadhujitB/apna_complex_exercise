package demo.dada.com.apnacomplexexercise;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import demo.dada.com.apnacomplexexercise.adapter.DocumentCategoriesAdapter;
import demo.dada.com.apnacomplexexercise.data.DocumentCategoriesData;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DocumentCategoriesActivity extends AppCompatActivity implements View.OnClickListener {

    private OkHttpClient okHttpClient;
    private ArrayList<DocumentCategoriesData> arrayList;
    private RecyclerView recView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView textView_sorryNoData, textView_setTitle;
    private ImageView imgView_hmbgIcon, imgView_backIcon, imgView_searchIcon, imgView_menuIcon;
    private static final String TAG = "DocumentCatActivity";
    private static final String TITLE = "Repository";
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Currently there is no function for this button", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        textView_setTitle = findViewById(R.id.textView_setTitle);
        textView_setTitle.setText(TITLE);

        imgView_hmbgIcon = findViewById(R.id.imgView_hmbgIcon);
        imgView_hmbgIcon.setVisibility(View.INVISIBLE);
        imgView_backIcon = findViewById(R.id.imgView_backIcon);
        imgView_backIcon.setOnClickListener(this);
        imgView_backIcon.setVisibility(View.VISIBLE);
        imgView_searchIcon = findViewById(R.id.imgView_searchIcon);
        imgView_searchIcon.setVisibility(View.INVISIBLE);
        imgView_menuIcon = findViewById(R.id.imgView_menuIcon);
        imgView_menuIcon.setVisibility(View.INVISIBLE);

        recView = findViewById(R.id.recView);
        textView_sorryNoData = findViewById(R.id.textView_sorryNoData);
        okHttpClient = new OkHttpClient();
        arrayList = new ArrayList<>();

        layoutManager = new LinearLayoutManager(this);
        recView.setLayoutManager(layoutManager);
        recView.setHasFixedSize(true);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        sendAndFetchRequest();
    }


    private void sendAndFetchRequest()
    {
        Request request = new Request.Builder()
                              .url("https://s3.ap-south-1.amazonaws.com/mobileassignment/repository/doc_categories")
                              .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if(response != null) {
                    String resp = response.body().string();
                    Log.d(TAG, resp);
                    try {
                        JSONObject jsonObject = new JSONObject(resp);
                        JSONArray jsonArray = jsonObject.getJSONArray("DocumentCategories");

                        Log.d(TAG, "Array Length: " + String.valueOf(jsonArray.length()));

                        for(int i = 0 ; i < jsonArray.length() ; i++)
                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            arrayList.add(new DocumentCategoriesData(
                                    jsonObject1.getString("cat_id"),
                                    jsonObject1.getString("cat_name"),
                                    jsonObject1.getString("cat_icon"),
                                    jsonObject1.getString("cat_background_img"),
                                    jsonObject1.getString("num_docs")
                            ));
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setAdapter();
                            }
                        });



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });


    }

    private void setAdapter()
    {

        if(arrayList.size() > 0) {

            recView.setVisibility(View.VISIBLE);
            textView_sorryNoData.setVisibility(View.GONE);
            adapter = new DocumentCategoriesAdapter(DocumentCategoriesActivity.this, arrayList);
            recView.setAdapter(adapter);
        }
        else
        {
            recView.setVisibility(View.GONE);
            textView_sorryNoData.setVisibility(View.VISIBLE);
        }

    }

    public void anotherActivity(int position)
    {
        String cat_id = arrayList.get(position).getCat_id();
        Intent gotoDocumentCategoriesActivity = new Intent(this, DocumentListActivity.class);
        gotoDocumentCategoriesActivity.putExtra("Send CAT_ID", cat_id);
        startActivity(gotoDocumentCategoriesActivity);
    }



    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}
