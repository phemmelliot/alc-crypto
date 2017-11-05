package com.example.phemmelliot.concrypt.model;

import com.google.gson.annotations.SerializedName;
/**
 * Created by phemmelliot on 11/5/17.
 */

public class Conversion {
    @SerializedName(value = "AUD", alternate = {"BRL","CAD","CLD","CNY","GBP","EUR","INR","IDR","ILS","JPY",
            "MYR","NZD","NGN","SGD","ZAR","KRW","CHF","USD"})
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
