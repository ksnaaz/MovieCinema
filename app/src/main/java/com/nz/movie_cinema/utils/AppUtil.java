package com.nz.movie_cinema.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hp on 4/26/2020.
 */

public class AppUtil {
    public static String movieImagePathBuilder(String imagePath) {
        return "https://image.tmdb.org/t/p/" +
                "w500" +
                imagePath;
    }

    public static long getRandomNumber() {
        long x = (long) ((Math.random() * ((100000 - 0) + 1)) + 0);
        return x;
    }

    public static String changeDateFormat(String oldDate) {
        String formatedDate = "";
        try {
            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
            Date dateValue = input.parse(oldDate);
            SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
            formatedDate = output.format(dateValue);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatedDate;
    }

    public static void showKeyboard(Context context, EditText editText){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideKeyboard(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}
