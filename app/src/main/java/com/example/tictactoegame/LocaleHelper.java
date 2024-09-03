package com.example.tictactoegame;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import java.util.Locale;

public class LocaleHelper {

    public static void setLocale(Context context, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale);
            context.getApplicationContext().createConfigurationContext(config);
        } else {
            config.locale = locale;
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            context.getResources().updateConfiguration(config, metrics);
        }
    }
}