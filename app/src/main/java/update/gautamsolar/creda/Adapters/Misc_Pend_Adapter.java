package update.gautamsolar.creda.Adapters;


import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gautamsolar.creda.R;

import java.util.ArrayList;
import java.util.List;

import update.gautamsolar.creda.Misc_Pend_Model;
import update.gautamsolar.creda.Payment_Approve_Activity;

public class Misc_Pend_Adapter extends RecyclerView.Adapter<Misc_Pend_Adapter.ViewHolder> implements Filterable {

List<Misc_Pend_Model> listItems, filterList;
Context mContext;

   public Misc_Pend_Adapter(List<Misc_Pend_Model> listItems, Context context){

       this.listItems=listItems;
       mContext=context;
       this.filterList=new ArrayList<>();
       this.filterList.addAll(this.listItems);

   }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.misc_pay_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Misc_Pend_Model misc_pend_model = listItems.get(position);

if(misc_pend_model.getType().equals("PAYMENT")){
    holder.eng_lay.setVisibility(View.GONE);
    holder.for_payment_lay.setVisibility(View.VISIBLE);
    holder.name_layout.setVisibility(View.VISIBLE);

    holder.for_payment.setText(misc_pend_model.getFor_payment());
    holder.pay_name.setText(misc_pend_model.getPay_name());

}
        //binding the data with the viewholder views
        holder.Benifname.setText(misc_pend_model.getBenefitiary_name());
        holder.Regnnumber.setText(misc_pend_model.getReg_no());
        holder.eng_name.setText(misc_pend_model.getEng_name());
        holder.dist.setText(misc_pend_model.getDistrict());
        holder.cmnt.setText(misc_pend_model.getMisc_comment());
        holder.real_amnt.setText(misc_pend_model.getMisc_ammount());
        holder.schme.setText(misc_pend_model.getScheme());
        holder.status.setText(misc_pend_model.getStatus());
        holder.block.setText(misc_pend_model.getBlock());
holder.next.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(mContext, Payment_Approve_Activity.class);
        i.putExtra("benif_name",misc_pend_model.getBenefitiary_name());
        i.putExtra("reg_no",misc_pend_model.getReg_no());
        i.putExtra("eng_name",misc_pend_model.getEng_name());
        i.putExtra("dist",misc_pend_model.getDistrict());
        i.putExtra("cmnt",misc_pend_model.getMisc_comment());
        i.putExtra("real_amnt",misc_pend_model.getMisc_ammount());
        i.putExtra("schme",misc_pend_model.getScheme());
        i.putExtra("status",misc_pend_model.getStatus());
        i.putExtra("block",misc_pend_model.getBlock());
        i.putExtra("amount_send",misc_pend_model.getIns_miss_amount_send());
        i.putExtra("type",misc_pend_model.getType());
        i.putExtra("for_payment",misc_pend_model.getFor_payment());
        i.putExtra("pay_name",misc_pend_model.getPay_name());
        i.putExtra("id",misc_pend_model.getId());

        mContext.startActivity(i);
    }
});

    }

    @Override
    public int getItemCount() {
        return (null != filterList ? filterList.size() : 0);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filterList = listItems;
                } else {
                    List<Misc_Pend_Model> filteredList = new ArrayList<>();
                    for (Misc_Pend_Model row : listItems) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getBenefitiary_name().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getStatus().toLowerCase().contains(charString.toLowerCase())
                                || row.getReg_no().toLowerCase().contains(charString.toLowerCase())
                                || row.getDistrict().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filterList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterList = (ArrayList<Misc_Pend_Model>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView eng_name,dist,cmnt,real_amnt,block,schme, Benifname, Regnnumber,status,for_payment,pay_name;
        RelativeLayout next;
        LinearLayout for_payment_lay,name_layout,eng_lay;

       public ViewHolder(View itemview){
           super(itemview);

           Benifname = itemView.findViewById(R.id.benifnameid);
           eng_name = itemView.findViewById(R.id.eng_name);
           Regnnumber = itemView.findViewById(R.id.numberid);
           dist = itemView.findViewById(R.id.dist);
           cmnt = itemView.findViewById(R.id.cmnt);
           real_amnt = itemView.findViewById(R.id.real_amnt);
           schme = itemView.findViewById(R.id.schme);
           block = itemView.findViewById(R.id.block);
           next = itemView.findViewById(R.id.item);
           for_payment_lay=itemView.findViewById(R.id.for_payment_lay);
           name_layout=itemView.findViewById(R.id.name_layout);
           for_payment=itemView.findViewById(R.id.for_payment);
           pay_name=itemView.findViewById(R.id.pay_name);
           eng_lay=itemview.findViewById(R.id.eng_lay);

           status=itemview.findViewById(R.id.status);

       }
    }
}
