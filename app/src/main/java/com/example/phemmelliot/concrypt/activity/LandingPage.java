package com.example.phemmelliot.concrypt.activity;

import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.graphics.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.phemmelliot.concrypt.R;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.phemmelliot.concrypt.adapter.ConcryptAdapter;
import com.example.phemmelliot.concrypt.model.Conversion;
import com.example.phemmelliot.concrypt.model.Currency;
import com.example.phemmelliot.concrypt.rest.ApiClient;
import com.example.phemmelliot.concrypt.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandingPage extends AppCompatActivity implements ConcryptAdapter.onItemClickListener{
    private ArrayList<Currency> currencies = new ArrayList<>();
    private StringBuilder input = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Concrypt");
        setSupportActionBar(toolbar);
        initializeCollapsingToolbar();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        loadData();
        ConcryptAdapter adapter = new ConcryptAdapter(currencies, this, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        try {
            Glide.with(this).load(R.drawable.bitcoin2).centerCrop().into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int i = 0; i<currencies.size(); i++)
            input.append(currencies.get(i).getCode() + ",");

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadData(){
        currencies.add(new Currency(R.string.australian, "AUD", R.drawable.australiadollar));
        currencies.add(new Currency(R.string.brazil, "BRL", R.drawable.brazil));
        currencies.add(new Currency(R.string.canada, "CAD", R.drawable.canadiandollar));
        currencies.add(new Currency(R.string.chile, "CLP", R.drawable.chile));
        currencies.add(new Currency(R.string.china, "CNY", R.drawable.china));
        currencies.add(new Currency(R.string.britain, "GBP", R.drawable.englandpound));
        currencies.add(new Currency(R.string.euro, "EUR", R.drawable.euro));
        currencies.add(new Currency(R.string.india, "INR", R.drawable.india));
        currencies.add(new Currency(R.string.indonesia, "IDR", R.drawable.indonesia));
        currencies.add(new Currency(R.string.isreal, "ILS", R.drawable.isreal));
        currencies.add(new Currency(R.string.japan, "JPY", R.drawable.japaneseyen));
        currencies.add(new Currency(R.string.malaysia, "MYR", R.drawable.malaysia));
        currencies.add(new Currency(R.string.newZealand, "NZD", R.drawable.newzealand));
        currencies.add(new Currency(R.string.nigeria, "NGN", R.drawable.nigeriannaira));
        currencies.add(new Currency(R.string.singapore, "SGD", R.drawable.singaporedollar));
        currencies.add(new Currency(R.string.southAfrica, "ZAR", R.drawable.southafrica));
        currencies.add(new Currency(R.string.korea, "KRW", R.drawable.southkorea));
        currencies.add(new Currency(R.string.switzerland, "CHF", R.drawable.swissfranc));
        currencies.add(new Currency(R.string.us, "USD", R.drawable.usdollar));
        currencies.add(new Currency(R.string.russia, "RUR", R.drawable.russianrubble));


    }

    private void initializeCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                findViewById(R.id.toolbarLayout);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    @Override
    public void onItemClick(int itemIndex) {
        //Toast.makeText(this, "It entered onItemClick", Toast.LENGTH_LONG).show();
        String code = currencies.get(itemIndex).getCode();
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setView(R.layout.dialog);
        final AlertDialog alertDialog = dialog.create();
        alertDialog.show();
        Map<String, String> data = new HashMap<>();
        data.put("fsym", code);
        data.put("tsyms", input.toString());

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        final Call<Conversion> call = apiService.getPrice(data);

        View view = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        final TextView text1 = view.findViewById(R.id.currency);
        Button convert = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button leave = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);

        convert.setTextColor(getResources().getColor(R.color.button));
        convert.setBackgroundResource(R.color.colorPrimaryDark);

        leave.setTextColor(getResources().getColor(R.color.button));
        leave.setBackgroundResource(R.color.colorPrimaryDark);


        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LandingPage.this, "It entered leave button", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call.enqueue(new Callback<Conversion>() {
                    @Override
                    public void onResponse(Call<Conversion> call, Response<Conversion> response) {
                        int statusCode = response.code();
                        text1.setText(response.body().getResult());

                    }

                    @Override
                    public void onFailure(Call<Conversion> call, Throwable t) {
                        // Log error here since request failed
                        //Log.e(TAG, t.toString());
                    }
                });
            }
        });




    }
}
