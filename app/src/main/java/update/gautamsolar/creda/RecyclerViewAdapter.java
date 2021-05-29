package update.gautamsolar.creda;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gautamsolar.creda.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    Context context;

    List<GetDataAdapter> getDataAdapter;
    //private ItemClickListener itemClickListener;

    public RecyclerViewAdapter(List<GetDataAdapter> getDataAdapter, Context context) {

        super();

        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final GetDataAdapter getDataAdapter1 = getDataAdapter.get(position);

        holder.NameTextView.setText(getDataAdapter1.getName());

        holder.IdTextView.setText(String.valueOf(getDataAdapter1.getId()));

        holder.PhoneNumberTextView.setText(getDataAdapter1.getPhone_number());
        holder.PumpTextView.setText(getDataAdapter1.getPump_type());
        holder.DispatchTextView.setText(getDataAdapter1.getdispatch_status());
        holder.StructuretextView.setText(getDataAdapter1.getStructure());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    Toast.makeText(context, "Long Click:" + getDataAdapter1.Id, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "" + getDataAdapter1.Id, Toast.LENGTH_LONG).show();
                    String fname = getDataAdapter1.name;
                    String reg_no = String.valueOf(getDataAdapter1.Id);
                    String dispatch_status = getDataAdapter1.dispatch_status;

                    String pump_type = getDataAdapter1.pump_type;
                    String structure = getDataAdapter1.structure;


                    Intent intent = new Intent(context, UploadAll.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("fname", fname);

                    bundle.putString("reg_no", reg_no);

                    bundle.putString("pump_type", pump_type);
                    bundle.putString("pump_capacity", structure);

                    bundle.putString("dispatch_status", dispatch_status);


                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            }
        });


    }

    @Override
    public int getItemCount() {

        return getDataAdapter.size();
    }

    /*@Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }*/

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TextView IdTextView;
        public TextView NameTextView;
        public TextView PhoneNumberTextView;
        public TextView DispatchTextView;
        public TextView PumpTextView;
        public TextView StructuretextView;

        private ItemClickListener itemClickListener;


        public ViewHolder(View itemView) {

            super(itemView);

            IdTextView = (TextView) itemView.findViewById(R.id.textView2);
            NameTextView = (TextView) itemView.findViewById(R.id.textView4);
            PhoneNumberTextView = (TextView) itemView.findViewById(R.id.textView6);
            PumpTextView = (TextView) itemView.findViewById(R.id.textView8);
            DispatchTextView = (TextView) itemView.findViewById(R.id.textView10);
            StructuretextView = (TextView) itemView.findViewById(R.id.textView12);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);


        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;

        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);

        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return true;
        }
    }


}
