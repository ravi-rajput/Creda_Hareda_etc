package update.gautamsolar.creda;

import android.app.Application;
import com.activeandroid.ActiveAndroid;


public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Initializing Active Android
        ActiveAndroid.initialize(this);
    }
}
