package com.example.phemmelliot.concrypt.model;

/**
 * Created by phemmelliot on 11/3/17.
 */

public class Currency {
    private int name;
    private String code;
    private int image;
    public Currency(int name, String code, int image) {
        this.name = name;
        this.code = code;
        this.image = image;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
