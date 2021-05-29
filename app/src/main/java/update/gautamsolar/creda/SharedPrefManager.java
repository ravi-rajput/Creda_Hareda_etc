package update.gautamsolar.creda;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;


public class SharedPrefManager {


    private static SharedPrefManager mInstance1;
    private RequestQueue requestQueue1;
    private static Context mCtx1;
    private static final String SHARED_PREF_NAME="mysharedpref12";
    private static final String KEY_USERNAME="username";
    private static final String KEY_USER_EMAIL="email";
    private static final String KEY_USER_CONTACT="contact";
    //private static final String KEY_DEPT="deptname";
    private static final String KEY_ROLE="role";
    //private static final String KEY_WAREHOUSE="warehouse";


    private SharedPrefManager(Context context1){

        mCtx1=context1;


    }

    public  static synchronized SharedPrefManager getInstance(Context context1){

        if (mInstance1==null){

            mInstance1= new SharedPrefManager(context1);

        }

        return mInstance1;


    }

    public  boolean userLogin(String username, String email, String contact, String role){

        SharedPreferences sharedPreferences = mCtx1.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_USER_EMAIL,email);
        editor.putString(KEY_USERNAME,username);
        editor.putString(KEY_USER_CONTACT,contact);
       // editor.putString(KEY_DEPT,deptname);
        editor.putString(KEY_ROLE,role);
        //editor.putString(KEY_WAREHOUSE,warehouse);
        editor.apply();
        return true;

    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx1.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_USERNAME,null)!=null){
            return true;
        }
        return false;
    }


    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx1.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getUsername(){

        SharedPreferences sharedPreferences = mCtx1.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME,null);

    }

    public String getUserEmail(){

        SharedPreferences sharedPreferences = mCtx1.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL,null);
    }



    public String getUserContact(){

        SharedPreferences sharedPreferences = mCtx1.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_CONTACT,null);
    }


    public String getUserRole(){

        SharedPreferences sharedPreferences = mCtx1.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ROLE,null);
    }





}

