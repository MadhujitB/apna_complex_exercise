package demo.dada.com.apnacomplexexercise;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import demo.dada.com.apnacomplexexercise.adapter.MainAdapter;
import demo.dada.com.apnacomplexexercise.data.MainData;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recView_Grid;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<MainData> arrayList;
    private ImageView imgView_hmbgIcon, imgView_backIcon, imgView_searchIcon, imgView_menuIcon;
    private TextView textView_setTitle;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recView_Grid = findViewById(R.id.recView_Grid);
        layoutManager = new GridLayoutManager(this, 2);
        recView_Grid.setLayoutManager(layoutManager);
        recView_Grid.setHasFixedSize(true);

        arrayList = new ArrayList<>();

        String title = getString(R.string.app_name);

        textView_setTitle = findViewById(R.id.textView_setTitle);
        textView_setTitle.setText(title);

        imgView_hmbgIcon = findViewById(R.id.imgView_hmbgIcon);
        imgView_hmbgIcon.setVisibility(View.VISIBLE);
        imgView_hmbgIcon.setOnClickListener(this);

        imgView_backIcon = findViewById(R.id.imgView_backIcon);
        imgView_backIcon.setVisibility(View.INVISIBLE);
        imgView_searchIcon = findViewById(R.id.imgView_searchIcon);
        imgView_searchIcon.setVisibility(View.INVISIBLE);
        imgView_menuIcon = findViewById(R.id.imgView_menuIcon);
        imgView_menuIcon.setVisibility(View.INVISIBLE);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        addData();

    }

    private void addData()
    {
        int[] image = {
                        R.drawable.repository_bg,
                        R.drawable.dummy1_bg,
                        R.drawable.dummy2_bg,
                        R.drawable.dummy_3bg,
                        R.drawable.dummy4_bg
                      };

        String[] name = {"Repository", "LOREUM IPSUM", "LOREUM IPSUM", "LOREUM IPSUM", "LOREUM IPSUM"};

        arrayList.clear();

        for(int i = 0; i < name.length; i++)
        {
            arrayList.add(new MainData(image[i], name[i]));
        }

        setAdapter();
    }

    private void setAdapter()
    {
        adapter = new MainAdapter(MainActivity.this, arrayList);
        recView_Grid.setAdapter(adapter);
    }

    public void moveToAnotherActivity()
    {
        Intent gotoDocumentCategoriesActivity = new Intent(this, DocumentCategoriesActivity.class);
        startActivity(gotoDocumentCategoriesActivity);
    }

    @Override
    public void onClick(View v) {
        snackbarAction();
    }

    public void snackbarAction()
    {
        Snackbar.make(coordinatorLayout, "Currently there is no function for this button", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
