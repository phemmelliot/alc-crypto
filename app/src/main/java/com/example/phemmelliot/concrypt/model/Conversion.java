package com.example.phemmelliot.concrypt.model;

import com.google.gson.annotations.SerializedName;
/**
 * Created by phemmelliot on 11/5/17.
 */

public class Conversion {
    @SerializedName(value = "BTC", alternate = {"LTC","NMC","ETH","STC","PPC","DOGE"})
    private double result;

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
}
