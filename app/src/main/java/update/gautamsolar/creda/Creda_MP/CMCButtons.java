package update.gautamsolar.creda.Creda_MP;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;

import update.gautamsolar.creda.R;

public class CMCButtons extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String regnnumber, benifname, fathername, contact, block, village, Cmc_one, Cmc_two, Cmc_three, Cmc_four, Cmc_five, Cmc_Six, Cmc_seven, Cmc_Eight, Cmc_nine, Cmc_ten;
    CardView Cmc_one_button, Cmc_two_button, CMCthreebutton, Cmc_four_button, Cmc_five_button, Cmc_Six_button, Cmc_seven_buton, Cmc_Eight_button, Cmc_nine_button, Cmc_ten_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmcbuttons);
        //Bundle bundlefe = getIntent().getExtras();
//
//        benifname = bundlefe.getString("benifname");
//        regnnumber = bundlefe.getString("regnnumber");
//        fathername = bundlefe.getString("fathername");
//        contact = bundlefe.getString("contact");
//        block = bundlefe.getString("block");
//        village = bundlefe.getString("village");

        Bundle bundlef = getIntent().getExtras();

        fathername = bundlef.getString("fathername");
        regnnumber = bundlef.getString("regnnumber");
        benifname = bundlef.getString("benifname");
        village = bundlef.getString("village");
        block = bundlef.getString("block");
        contact = bundlef.getString("contact");
        intialize();
        ClickListners();



    }

    public void ClickListners() {
        // Toast.makeText(this, String.valueOf(Cmc_one.length())+String.valueOf(Cmc_three.length()), Toast.LENGTH_SHORT).show();
        if (Cmc_one.equals("PENDING")) {
            Cmc_one_button.setVisibility(View.VISIBLE);

            Cmc_one_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), CMCActivity.class);
                    Bundle bundlecmca = new Bundle();
                    bundlecmca.putString("fathername", fathername);
                    bundlecmca.putString("regnnumber", regnnumber);
                    bundlecmca.putString("benifname", benifname);
                    bundlecmca.putString("village", village);
                    bundlecmca.putString("block", block);
                    bundlecmca.putString("contact", contact);
                    intent.putExtras(bundlecmca);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("button_id", "1");
                    editor.commit();
                    startActivity(intent);

                }
            });

        }
        if (Cmc_two.equals("PENDING")) {
            Cmc_two_button.setVisibility(View.VISIBLE);

            Cmc_two_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("button_id", "2");
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), CMCActivity.class);
                    Bundle bundlecmca = new Bundle();
                    bundlecmca.putString("fathername", fathername);
                    bundlecmca.putString("regnnumber", regnnumber);
                    bundlecmca.putString("benifname", benifname);
                    bundlecmca.putString("village", village);
                    bundlecmca.putString("block", block);
                    bundlecmca.putString("contact", contact);
                    intent.putExtras(bundlecmca);
                    startActivity(intent);

                }
            });


        }
        if (Cmc_three.equals("PENDING")) {
            CMCthreebutton.setVisibility(View.VISIBLE);

            CMCthreebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), CMCActivity.class);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("button_id", "3");
                    editor.commit();
                    Bundle bundlecmca = new Bundle();
                    bundlecmca.putString("fathername", fathername);
                    bundlecmca.putString("regnnumber", regnnumber);
                    bundlecmca.putString("benifname", benifname);
                    bundlecmca.putString("village", village);
                    bundlecmca.putString("block", block);
                    bundlecmca.putString("contact", contact);
                    intent.putExtras(bundlecmca);
                    startActivity(intent);
                }
            });
        }
        if (Cmc_four.equals("PENDING")) {
            Cmc_four_button.setVisibility(View.VISIBLE);

            Cmc_four_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("button_id", "4");
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), CMCActivity.class);
                    Bundle bundlecmca = new Bundle();
                    bundlecmca.putString("fathername", fathername);
                    bundlecmca.putString("regnnumber", regnnumber);
                    bundlecmca.putString("benifname", benifname);
                    bundlecmca.putString("village", village);
                    bundlecmca.putString("block", block);
                    bundlecmca.putString("contact", contact);
                    intent.putExtras(bundlecmca);
                    startActivity(intent);

                }
            });

        }
        if (Cmc_five.equals("PENDING")) {
            Cmc_five_button.setVisibility(View.VISIBLE);

            Cmc_five_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("button_id", "5");
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), CMCActivity.class);
                    Bundle bundlecmca = new Bundle();
                    bundlecmca.putString("fathername", fathername);
                    bundlecmca.putString("regnnumber", regnnumber);
                    bundlecmca.putString("benifname", benifname);
                    bundlecmca.putString("village", village);
                    bundlecmca.putString("block", block);
                    bundlecmca.putString("contact", contact);
                    intent.putExtras(bundlecmca);
                    startActivity(intent);

                }
            });
        }
        if (Cmc_Six.equals("PENDING")) {
            Cmc_Six_button.setVisibility(View.VISIBLE);

            Cmc_Six_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("button_id", "6");
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), CMCActivity.class);
                    Bundle bundlecmca = new Bundle();
                    bundlecmca.putString("fathername", fathername);
                    bundlecmca.putString("regnnumber", regnnumber);
                    bundlecmca.putString("benifname", benifname);
                    bundlecmca.putString("village", village);
                    bundlecmca.putString("block", block);
                    bundlecmca.putString("contact", contact);
                    intent.putExtras(bundlecmca);
                    startActivity(intent);

                }
            });

        }
        if (Cmc_seven.equals("PENDING")) {
            Cmc_seven_buton.setVisibility(View.VISIBLE);

            Cmc_seven_buton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("button_id", "7");
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), CMCActivity.class);
                    Bundle bundlecmca = new Bundle();
                    bundlecmca.putString("fathername", fathername);
                    bundlecmca.putString("regnnumber", regnnumber);
                    bundlecmca.putString("benifname", benifname);
                    bundlecmca.putString("village", village);
                    bundlecmca.putString("block", block);
                    bundlecmca.putString("contact", contact);
                    intent.putExtras(bundlecmca);
                    startActivity(intent);

                }
            });
        }
        if (Cmc_Eight.equals("PENDING")) {
            Cmc_Eight_button.setVisibility(View.VISIBLE);

            Cmc_Eight_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("button_id", "8");
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), CMCActivity.class);
                    Bundle bundlecmca = new Bundle();
                    bundlecmca.putString("fathername", fathername);
                    bundlecmca.putString("regnnumber", regnnumber);
                    bundlecmca.putString("benifname", benifname);
                    bundlecmca.putString("village", village);
                    bundlecmca.putString("block", block);
                    bundlecmca.putString("contact", contact);
                    intent.putExtras(bundlecmca);
                    startActivity(intent);

                }
            });

        }
        if (Cmc_nine.equals("PENDING")) {
            Cmc_nine_button.setVisibility(View.VISIBLE);

            Cmc_nine_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("button_id", "9");
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), CMCActivity.class);
                    Bundle bundlecmca = new Bundle();
                    bundlecmca.putString("fathername", fathername);
                    bundlecmca.putString("regnnumber", regnnumber);
                    bundlecmca.putString("benifname", benifname);
                    bundlecmca.putString("village", village);
                    bundlecmca.putString("block", block);
                    bundlecmca.putString("contact", contact);
                    intent.putExtras(bundlecmca);
                    startActivity(intent);

                }
            });

        }
        if (Cmc_ten.equals("PENDING")) {
            Cmc_ten_button.setVisibility(View.VISIBLE);

            Cmc_ten_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("button_id", "10");
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), CMCActivity.class);
                    Bundle bundlecmca = new Bundle();
                    bundlecmca.putString("fathername", fathername);
                    bundlecmca.putString("regnnumber", regnnumber);
                    bundlecmca.putString("benifname", benifname);
                    bundlecmca.putString("village", village);
                    bundlecmca.putString("block", block);
                    bundlecmca.putString("contact", contact);
                    intent.putExtras(bundlecmca);
                    startActivity(intent);

                }
            });

        }



    }


    public void intialize() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        CMCthreebutton = findViewById(R.id.threecmcbutton);
        Cmc_one_button = findViewById(R.id.cmc_buttone_one);
        Cmc_two_button = findViewById(R.id.cmc_button_two);
        Cmc_four_button = findViewById(R.id.cmc_button_four);
        Cmc_five_button = findViewById(R.id.cmc_button_five);
        Cmc_Six_button = findViewById(R.id.cmc_button_six);
        Cmc_seven_buton = findViewById(R.id.cmc_button_seven);
        Cmc_Eight_button = findViewById(R.id.cmc_button_eight);
        Cmc_nine_button = findViewById(R.id.cmc_button_nine);
        Cmc_ten_button = findViewById(R.id.cmc_button_ten);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Cmc_one = sharedPreferences.getString("CMC_1", "");
        Cmc_two = sharedPreferences.getString("CMC_2", "");
        Cmc_three = sharedPreferences.getString("CMC_3", "");
        Cmc_four = sharedPreferences.getString("CMC_4", "");
        Cmc_five = sharedPreferences.getString("CMC_5", "");
        Cmc_Six = sharedPreferences.getString("CMC_6", "");
        Cmc_seven = sharedPreferences.getString("CMC_7", "");
        Cmc_Eight = sharedPreferences.getString("CMC_8", "");
        Cmc_nine = sharedPreferences.getString("CMC_9", "");
        Cmc_ten = sharedPreferences.getString("CMC_10", "");
        Cmc_one_button.setVisibility(View.GONE);
        Cmc_two_button.setVisibility(View.GONE);
        CMCthreebutton.setVisibility(View.GONE);
        Cmc_four_button.setVisibility(View.GONE);
        Cmc_five_button.setVisibility(View.GONE);
        Cmc_Six_button.setVisibility(View.GONE);
        Cmc_seven_buton.setVisibility(View.GONE);
        Cmc_Eight_button.setVisibility(View.GONE);
        Cmc_nine_button.setVisibility(View.GONE);
        Cmc_ten_button.setVisibility(View.GONE);
    }
}
