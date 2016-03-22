package ielite.app.eventme;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Suraj on 22-03-2016.
 */
public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
