package com.example.mob2041_pnlib_libmana_project;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class ShowSnackBar {

    public void defineSnackBar(View view, String message){
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
