package update.gautamsolar.creda;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gautamsolar.creda.R;

public class Owner_Portion_Activity extends AppCompatActivity {
    RelativeLayout mis_pen_found,mis_pen_insta,mislenious;
    Dialog Localdialog;
    SharedPreferences sharedPreferences;
    TextView textViewname, textViewrole, textViewproject, textViewdist, textViewcontact;
    @Override
    protected void onCreate(Bundle SavedInstantstate){
        super.onCreate(SavedInstantstate);
        setContentView(R.layout.owner_portion);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mis_pen_found=findViewById(R.id.mis_pen_found);
        mis_pen_insta=findViewById(R.id.mis_pen_insta);
        mislenious=findViewById(R.id.mislenious);

        mis_pen_found.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Owner_Portion_Activity.this,Misc_Pend_Inatallation.class);
                i.putExtra("api","getallPending_founpaymentlist.php");
                i.putExtra("type","FOUNDATION");
                startActivity(i);
            }
        });
        mis_pen_insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Owner_Portion_Activity.this,Misc_Pend_Inatallation.class);
                i.putExtra("api","getallInstallation_pending_paymentlist.php");
                i.putExtra("type","INSTALLATION");
                startActivity(i);
            }
        });
        mislenious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Owner_Portion_Activity.this,Misc_Pend_Inatallation.class);
                i.putExtra("api","getmiscellaneous_paymentlist.php");
                i.putExtra("type","PAYMENT");
                startActivity(i);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.logoutpage, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.profile:

                Localdialog = new Dialog(Owner_Portion_Activity.this);
                String username = sharedPreferences.getString("username", "");
                String contact = sharedPreferences.getString("contact", "");
                String district = sharedPreferences.getString("district", "");
                String project = sharedPreferences.getString("project", "");
                String role = sharedPreferences.getString("role", "");
                Localdialog.setContentView(R.layout.profile);
                Localdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Localdialog.setCancelable(false);
                textViewname = Localdialog.findViewById(R.id.nametextdialog);
                textViewcontact = Localdialog.findViewById(R.id.textdialogcontact);
                textViewrole = Localdialog.findViewById(R.id.textdialogdesg);
                textViewproject = Localdialog.findViewById(R.id.textdialogproject);
                textViewdist = Localdialog.findViewById(R.id.textdialogdist);


                textViewname.setText(username);
                textViewcontact.setText(contact);
                textViewrole.setText(role);
                textViewproject.setText(project);
                textViewdist.setText(district);

                Localdialog.findViewById(R.id.clickok).setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    public void onClick(View v) {


                        Localdialog.dismiss();
                    }
                });

                Localdialog.show();
                break;
            case R.id.credalogut:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("EXIT");
                builder1.setIcon(R.mipmap.gsplcredaicon);
                builder1.setMessage("Are you sure? ");

                builder1.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                builder1.setNegativeButton("Log out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("login_status", "0");
                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();


                    }
                });
                builder1.show();


                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
