package demo.dada.com.apnacomplexexercise;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import demo.dada.com.apnacomplexexercise.adapter.DocumentListAdapter;
import demo.dada.com.apnacomplexexercise.data.DocumentListData;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;

public class DocumentListActivity extends AppCompatActivity implements View.OnClickListener {


    private OkHttpClient okHttpClient;
    private RecyclerView recView_docList;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DocumentListData> arrayList;
    private String cat_id = "";
    private TextView textView_noDataFound, textView_setTitle;
    private ImageView imgView_hmbgIcon, imgView_backIcon, imgView_searchIcon, imgView_menuIcon;
    private static final String TAG = "DocumentListActivity";
    private static final String TITLE = "My Documents";
    private String files = "Files";
    private String name = "Name";
    private TextView textView_files, textView_name;
    private Typeface robotoFonts_Regular;
    private CoordinatorLayout coordinatorLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_list);
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

        robotoFonts_Regular = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");

        textView_setTitle = findViewById(R.id.textView_setTitle);
        textView_setTitle.setText(TITLE);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        imgView_hmbgIcon = findViewById(R.id.imgView_hmbgIcon);
        imgView_hmbgIcon.setVisibility(View.INVISIBLE);
        imgView_backIcon = findViewById(R.id.imgView_backIcon);
        imgView_backIcon.setOnClickListener(this);
        imgView_backIcon.setVisibility(View.VISIBLE);
        imgView_searchIcon = findViewById(R.id.imgView_searchIcon);
        imgView_searchIcon.setVisibility(View.VISIBLE);
        imgView_menuIcon = findViewById(R.id.imgView_menuIcon);
        imgView_menuIcon.setVisibility(View.VISIBLE);

        textView_files = findViewById(R.id.textView_files);
        textView_files.setText(files);
        textView_files.setTypeface(robotoFonts_Regular, Typeface.BOLD);

        textView_name = findViewById(R.id.textView_name);
        textView_name.setText(name);
        textView_name.setTypeface(robotoFonts_Regular, Typeface.BOLD);

        findViewById(R.id.imgView_searchIcon).setOnClickListener(this);
        findViewById(R.id.imgView_menuIcon).setOnClickListener(this);

        textView_noDataFound = findViewById(R.id.textView_noDataFound);
        recView_docList = findViewById(R.id.recView_docList);
        layoutManager = new LinearLayoutManager(this);
        recView_docList.setLayoutManager(layoutManager);
        recView_docList.setHasFixedSize(true);

        okHttpClient = new OkHttpClient();
        arrayList = new ArrayList<>();

        cat_id = this.getIntent().getStringExtra("Send CAT_ID");

        sendFetchData();
    }

    private void sendFetchData()
    {
        String sendUrl = "https://s3.ap-south-1.amazonaws.com/mobileassignment/repository/docs_list/" + cat_id;


        Request request = new Request.Builder()
                .url(sendUrl)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if(response != null)
                {
                    try
                    {
                        String resp = response.body().string();
                        Log.d(TAG, resp);

                        JSONObject jsonObject = new JSONObject(resp);
                        JSONArray jsonArray = jsonObject.getJSONArray("documents");

                        arrayList.clear();

                        for(int i = 0 ; i < jsonArray.length() ; i++)
                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            arrayList.add(new DocumentListData(
                                    jsonObject1.getString("doc_name"),
                                    jsonObject1.getString("doc_size"),
                                    jsonObject1.getString("doc_type"),
                                    jsonObject1.getString("doc_url")
                            ));
                        }

                        //File downLoadedFile = new File(getCacheDir(),)

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setAdapter();
                            }
                        });

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    private void setAdapter()
    {
        if(arrayList.size() > 0)
        {
            adapter = new DocumentListAdapter(DocumentListActivity.this, arrayList);
            recView_docList.setAdapter(adapter);

            recView_docList.setVisibility(View.VISIBLE);
            textView_noDataFound.setVisibility(View.GONE);
        }
        else
        {
            recView_docList.setVisibility(View.GONE);
            textView_noDataFound.setVisibility(View.VISIBLE);
        }
    }

    public void downloadFile(final String fileName, String url)
    {

        Request request = new Request.Builder()
                            .url(url)
                            .build();


        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if(response != null)
                {
                    final File file = new File(Environment.getExternalStorageDirectory() + fileName);

                    Log.d(TAG, file.getAbsolutePath());

                    BufferedSink sink = Okio.buffer(Okio.sink(file));
                    sink.writeAll(response.body().source());
                    sink.close();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewFile(file);
                        }
                    });
                }

            }
        });

    }

    public void snackbarAction()
    {
        Snackbar.make(coordinatorLayout, "Currently there is no function for this button", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void viewFile(File file)
    {
        try {

            Uri path = FileProvider.getUriForFile(DocumentListActivity.this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    file);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(path, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.imgView_menuIcon:
                snackbarAction();
                break;

            case R.id.imgView_searchIcon:
                snackbarAction();
                break;

            case R.id.imgView_backIcon:
                onBackPressed();
                break;
        }


    }
}
