package com.elitetechnocrats.dev.youtube;

import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by dev on 3/24/2017.
 */

public class Application extends android.app.Application {
    private static Application me;

    public static Application getInstance() {
        return me;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        me = this;
    }
}
