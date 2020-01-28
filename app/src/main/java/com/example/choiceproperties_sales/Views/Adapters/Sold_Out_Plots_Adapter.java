package com.example.choiceproperties_sales.Views.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.choiceproperties_sales.Constant.Constant;
import com.example.choiceproperties_sales.Models.Plots;
import com.example.choiceproperties_sales.Models.Requests;
import com.example.choiceproperties_sales.R;
import com.example.choiceproperties_sales.Views.Activities.Update_Sold_Out_Plots_Activity;
import com.example.choiceproperties_sales.Views.dialog.ProgressDialogClass;
import com.example.choiceproperties_sales.repository.LeedRepository;

import java.util.List;

public class Sold_Out_Plots_Adapter extends RecyclerView.Adapter<Sold_Out_Plots_Adapter.ViewHolder> {

    private static List<Plots> searchArrayList;
    private Context context;
    private boolean isFromRequest;
    ProgressDialogClass progressDialogClass;
    LeedRepository leedRepository;

    public Sold_Out_Plots_Adapter(Context context, List<Plots> userArrayList, boolean isFromRequest) {
        this.context = context;
        this.searchArrayList = userArrayList;
        this.isFromRequest = isFromRequest;
    }

    @Override
    public Sold_Out_Plots_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sold_out_plots_adapter_layout, parent, false);
        Sold_Out_Plots_Adapter.ViewHolder viewHolder = new ViewHolder(v);
        //  context = parent.getContext();
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final Sold_Out_Plots_Adapter.ViewHolder holder, int position) {
        final Plots plots = searchArrayList.get(position);

        if (plots.getPlotnumber() != null) {
            holder.txtCustomerName.setText(": " + searchArrayList.get(position).getPlotnumber());
        } else {
            holder.txtCustomerName.setText("Null");
        }
        if (plots.getCustomerNmae() != null) {
            holder.txtAddress.setText(": " + searchArrayList.get(position).getCustomerNmae());
        }else {
            holder.txtAddress.setText("Null");
        }
        if (plots.getPayedAmount() != null) {
            holder.txtNumber.setText(": " + searchArrayList.get(position).getPayedAmount());
        }else {
            holder.txtNumber.setText("Null");
        }
        if (plots.getRemainingAmount() != null) {
            holder.txtStatus.setText(": " + searchArrayList.get(position).getRemainingAmount());
        }else {
            holder.txtStatus.setText("Null");
        }

//        holder.txtbilldate.setText(": "+ Utility.convertMilliSecondsToFormatedDate(searchArrayList.get(position).getCreatedDateTimeLong(), GLOBAL_DATE_FORMATE));

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.card_view.getContext(), Update_Sold_Out_Plots_Activity.class);
                intent.putExtra(Constant.PLOTS
                        , plots);
                holder.card_view.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return searchArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtCustomerName, txtAddress, txtNumber, txtStatus;
        CardView card_view;
        LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);


            txtCustomerName = (TextView) itemView.findViewById(R.id.txt_txt_customer_name_value);
            txtAddress = (TextView) itemView.findViewById(R.id.txt_address_value);
            txtNumber = (TextView) itemView.findViewById(R.id.txt_number_value);
            txtStatus = (TextView) itemView.findViewById(R.id.txt_status_value);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
//            layout = (LinearLayout) itemView.findViewById(R.id.layoutdetails);

        }
    }

}