package com.example.mob2041_pnlib_libmana_project;

import java.text.DecimalFormat;

public class FormatPrice {


    public int formatNumber(int price){
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        decimalFormat.format(String.valueOf(price));
        return price;
    }


}
