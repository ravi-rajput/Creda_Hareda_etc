package update.gautamsolar.creda.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import update.gautamsolar.creda.NewInstalationActivity
import update.gautamsolar.creda.R
import update.gautamsolar.creda.RegisterChalanActivity
import update.gautamsolar.creda.models.ChalanModel

class ChalanAdapter(var context: Context, private val chalanList: List<ChalanModel>) :
    RecyclerView.Adapter<ChalanAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define views from your item layout here
        val name: TextView = itemView.findViewById(R.id.name)
        val saralNo: TextView = itemView.findViewById(R.id.saralNo)
        val regNo: TextView = itemView.findViewById(R.id.reg_no)
        val mobile: TextView = itemView.findViewById(R.id.mobile)
        val invoice_image_date: TextView = itemView.findViewById(R.id.invoice_image_date)
        val image: ImageView = itemView.findViewById(R.id.image)
        val main_linear: LinearLayout = itemView.findViewById(R.id.main_linear)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_chalan_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chalans = chalanList[position]
        // Bind question data to views in your item layout here
       holder.name.text = "Name: "+chalans.fname
       holder.saralNo.text = "Saral No: "+chalans.chalanSaralNo
       holder.regNo.text = "Reg No: "+chalans.reg_no_new.toString()
       holder.mobile.text =  "Contact: "+chalans.contact_no
       holder.invoice_image_date.text = "Date: "+chalans.chalanRegisterDate
        Glide.with(context)
            .load(chalans.saralChalanImage)
            .into(holder.image)

        holder.main_linear.setOnClickListener {

            val i = Intent(context, RegisterChalanActivity::class.java)
            i.putExtra("regnnumber", chalans.reg_no_new.toString())
            i.putExtra("benifname", chalans.fname)
            i.putExtra("fathername", chalans.fathername)
            i.putExtra("contact", chalans.contact_no.toString())
            i.putExtra("village", chalans.village)
            i.putExtra("block", chalans.block)
            i.putExtra("saralid", chalans.chalanSaralNo)
            i.putExtra("fondimg1", chalans.saralChalanImage)
            context.startActivity(i)

        }
    }

    override fun getItemCount(): Int {
        return chalanList.size
    }

}
