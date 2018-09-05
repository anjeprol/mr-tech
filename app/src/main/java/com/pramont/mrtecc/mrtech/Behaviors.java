package com.pramont.mrtecc.mrtech;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

/**
 * Created by antoniopradoo on 9/15/16.
 */
public abstract class Behaviors {

    private final static String ACTION = "Action";

    //Method to hide the keyboard from all the activities
    public static void hideKeyboard(View view, Context context) {
        if (view != null)
        {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    //Method to hide the keyBoard
    public static void showKeyboard(Context context) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).
                toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    //Method to show a custom snackbar

    /**
     * Generate a custom snack bar
     *
     * @param view     view object to add the snack bar.
     * @param activity activity to associate the custom color.
     * @param message  String message to show into the snack bar.
     * @param duration     int value to indicate duration.
     * @param color    int value to add the color.
     */

    public static void showSnackBar(View view, Activity activity, String message, int duration, int color) {
        Snackbar snack = Snackbar.make(view, message, duration).setAction(ACTION, null);
        ViewGroup viewGroup = (ViewGroup) snack.getView();
        TextView textView = viewGroup.findViewById(android.support.design.R.id.snackbar_text);
        viewGroup.setBackgroundColor(ContextCompat.getColor(activity, color));
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        snack.show();
    }
}
