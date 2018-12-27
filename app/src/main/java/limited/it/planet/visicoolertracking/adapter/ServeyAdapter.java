package limited.it.planet.visicoolertracking.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import limited.it.planet.visicoolertracking.R;
import limited.it.planet.visicoolertracking.util.ServeyModel;

/**
 * Created by User on 1/31/2018.
 */

public class ServeyAdapter extends RecyclerView.Adapter<ServeyAdapter.MyViewHolder> {
    private Context mContext;
    private List<ServeyModel> serveyModelList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtOutletId,txvEntryDate,txvSyncStaus ;


        public MyViewHolder(View view) {
            super(view);
            txtOutletId = (TextView) view.findViewById(R.id.txv_outlet_id);
            txvEntryDate = (TextView) view.findViewById(R.id.txv_entry_date);
            txvSyncStaus = (TextView) view.findViewById(R.id.txv_sync_status);



        }
    }


    public ServeyAdapter(Context context, List<ServeyModel> searchList) {
        this.mContext = context;
        this.serveyModelList = searchList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_failed_servey_data_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ServeyModel searchModel = serveyModelList.get(position);
        holder.txtOutletId.setText(String.valueOf(searchModel.getOutletId()));
        holder.txvEntryDate.setText(searchModel.getEntryDate());
        holder.txvSyncStaus.setText(searchModel.getSyncStatus());


    }
    @Override
    public int getItemCount() {
        return serveyModelList.size();
    }
}
