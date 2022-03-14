package update.gautamsolar.creda;

import android.app.Application;
import com.activeandroid.ActiveAndroid;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MyApplication extends Application {
    private static MyApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();

        //Initializing Active Android
        ActiveAndroid.initialize(this);
        mInstance = this;
    }
    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

}
