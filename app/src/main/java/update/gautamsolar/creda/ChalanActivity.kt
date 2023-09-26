package update.gautamsolar.creda

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import update.gautamsolar.creda.Adapters.ChalanAdapter
import update.gautamsolar.creda.Interfaces.APIService
import update.gautamsolar.creda.models.ChalanModel
import java.io.File


class ChalanActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var relativeLayout: RelativeLayout
    var sharedPreferences: SharedPreferences? = null
    lateinit var adapter: ChalanAdapter
    lateinit var button: Button
    var pb: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chalan)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        pb = ProgressDialog(this)
        pb!!.setMessage("Loading..")
        recyclerView = findViewById(R.id.recyclerView)
        button = findViewById(R.id.register)

        button.setOnClickListener {
            val intent = Intent(this@ChalanActivity, RegisterChalanActivity::class.java)
            startActivity(intent)
        }

        listload1()
    }

    fun listload1() {
        pb?.show()
        val body1: RequestBody = RequestBody.create(MediaType.parse("text/plain"), sharedPreferences?.getString("eng_id", ""))

        val mAPIService = RetrofitClient.getClient().create(APIService::class.java)
        val responseCall = mAPIService.getChalans(body1)
        responseCall.enqueue(object : Callback<List<ChalanModel>> {
            override fun onResponse(
                call: Call<List<ChalanModel>>,
                response: Response<List<ChalanModel>>
            ) {
                if (response.isSuccessful && response.body()!!.isNotEmpty()) {
                    adapter = ChalanAdapter(this@ChalanActivity, response.body()!!)
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this@ChalanActivity, "NO Data Found", Toast.LENGTH_SHORT)
                        .show()
                }
                pb?.dismiss()
            }

            override fun onFailure(call: Call<List<ChalanModel>>, t: Throwable) {
                Toast.makeText(this@ChalanActivity, t.message, Toast.LENGTH_SHORT).show()
                pb?.dismiss()
            }
        })
    }
    override fun onBackPressed() {
        if(intent.getStringExtra("go_back")!=null && intent.getStringExtra("go_back").equals("true")) {
            super.onBackPressed()
        }
        else{
            val builder1 = AlertDialog.Builder(this)
            builder1.setTitle("EXIT")
            builder1.setIcon(R.mipmap.gsplcredaicon)
            builder1.setMessage("Are you sure? ")
            builder1.setPositiveButton(
                "Cancel"
            ) { dialog, which -> }
            builder1.setNegativeButton(
                "Log out"
            ) { dialog, which ->
                val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                editor.putString("login_status", "0")
                editor.commit()
                clearApplicationData()
                val intent = Intent(applicationContext, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            builder1.show()
        }
    }
    fun clearApplicationData() {
        val cacheDirectory = cacheDir
        val applicationDirectory = File(cacheDirectory.parent)
        if (applicationDirectory.exists()) {
            val fileNames = applicationDirectory.list()
            for (fileName in fileNames) {
                if (fileName != "lib") {
                    BenificiaryListitem.deleteFile(File(applicationDirectory, fileName))
                }
            }
        }
    }
}
