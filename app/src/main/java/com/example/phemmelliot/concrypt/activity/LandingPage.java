package com.example.phemmelliot.concrypt.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.phemmelliot.concrypt.R;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
    private String TAG = getClass().getSimpleName();
    private LinearLayout layout;
    private ProgressBar progressBar;
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
            Glide.with(this).load(R.drawable.bitcoin3).centerCrop().into((ImageView) findViewById(R.id.backdrop));
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
        final HashMap<String, String> entries = new HashMap<>();
        entries.put("BitCoin", "BTC");
        entries.put("LiteCoin", "LTC");
        entries.put("NameCoin", "NMC");
        entries.put("Ethereum", "ETH");
        entries.put("SwiftCoin", "STC");
        entries.put("PeerCoin", "PPC");
        entries.put("DogeCoin", "DOGE");

        final String code = currencies.get(itemIndex).getCode();

        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View view = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        dialog.setView(view);

        final TextView text1 = view.findViewById(R.id.currency);
        layout = view.findViewById(R.id.layout);
        progressBar = view.findViewById(R.id.progress);
        final EditText userInput = view.findViewById(R.id.edit);
        String hint1 = getString(currencies.get(itemIndex).getName());
        String hint = hint1.substring(0, hint1.length() - 10);
        userInput.setHint(hint);
        Button leave = view.findViewById(R.id.ok);
        Button convert = view.findViewById(R.id.convert);
        final Spinner spinner = view.findViewById(R.id.spinner);


        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);






        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                String convertString = entries.get(spinner.getSelectedItem().toString());
                Map<String, String> data = new HashMap<>();
                data.put("fsym", code);
                data.put("tsyms", convertString);
                final Call<Conversion> call = apiService.getPrice(data);
                if(networkAvailable() && !TextUtils.isEmpty(userInput.getText().toString()))
                    fetch(call, text1, userInput, code, convertString);
                else if(!networkAvailable() && TextUtils.isEmpty(userInput.getText().toString()))
                    Toast.makeText(LandingPage.this, "Check your internet and input something", Toast.LENGTH_LONG).show();
                else if(TextUtils.isEmpty(userInput.getText().toString()))
                    Toast.makeText(LandingPage.this, "No input", Toast.LENGTH_LONG).show();
                else if(!networkAvailable())
                    Toast.makeText(LandingPage.this, "Check your internet connection", Toast.LENGTH_LONG).show();

            }
        });


        dialog.show();

    }

    public void fetch(Call<Conversion> call, final TextView text1, final EditText userInput, final String code, final String convertString)
    {
        showProgress();
        call.clone().enqueue(new Callback<Conversion>() {
            @Override
            public void onResponse(@NonNull Call<Conversion> call, @NonNull Response<Conversion> response) {
                if(response.body() == null)
                    Toast.makeText(LandingPage.this, "response is null", Toast.LENGTH_LONG).show();
                hideProgress();
                Double apiResult = response.body().getResult();
                double inputAsDouble = Double.parseDouble(userInput.getText().toString());
                double result = apiResult * inputAsDouble;
                DecimalFormat REAL_FORMATTER = new DecimalFormat("0.##############");
                String showText = inputAsDouble + code + " converts to " + REAL_FORMATTER.format(result) + convertString;
                text1.setText(showText);

            }

            @Override
            public void onFailure(@NonNull Call<Conversion> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                hideProgress();
                Toast.makeText(LandingPage.this, "Try again", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showProgress()
    {
        layout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }
    public void hideProgress()
    {
        layout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public boolean networkAvailable()
    {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if(manager.getActiveNetworkInfo() != null)
          activeNetwork = manager.getActiveNetworkInfo();

        return activeNetwork != null;


    }
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}









