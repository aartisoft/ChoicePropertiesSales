package com.example.choiceproperties_sales.Views.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.choiceproperties_sales.Constant.Constant;
import com.example.choiceproperties_sales.Models.Plots;
import com.example.choiceproperties_sales.R;
import com.example.choiceproperties_sales.Views.Activities.Update_Sold_Out_Plots_Activity;
import com.example.choiceproperties_sales.Views.dialog.ProgressDialogClass;
import com.example.choiceproperties_sales.repository.LeedRepository;
import com.example.choiceproperties_sales.utilities.Utility;

import java.util.ArrayList;
import java.util.List;

import static com.example.choiceproperties_sales.Constant.Constant.GLOBAL_DATE_FORMATE;

public class Reports_Adapter extends RecyclerView.Adapter<Reports_Adapter.ViewHolder> {

    private static List<Plots> searchArrayList;
    private Context context;
    private boolean isFromRequest;
    ProgressDialogClass progressDialogClass;
    LeedRepository leedRepository;

    public Reports_Adapter(Context context, List<Plots> userArrayList) {
        this.context = context;
        this.searchArrayList = userArrayList;
        this.isFromRequest = isFromRequest;
    }


    @Override
    public Reports_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reports_adapter_layout, parent, false);
        Reports_Adapter.ViewHolder viewHolder = new ViewHolder(v);
        //  context = parent.getContext();
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final Reports_Adapter.ViewHolder holder, final int position) {
        final Plots plots = searchArrayList.get(position);

        if (plots.getPlotnumber() != null) {
            holder.txtCustomerName.setText(": " + searchArrayList.get(position).getPlotnumber());
        } else {
            holder.txtCustomerName.setText("Null");
        }
        if (plots.getCustomerNmae() != null) {
            holder.txtAddress.setText(": " + searchArrayList.get(position).getCustomerNmae());
        } else {
            holder.txtAddress.setText("Null");
        }
        if (plots.getPlotPrice() != null) {
            holder.txtNumber.setText(": " + searchArrayList.get(position).getPlotPrice());
        } else {
            holder.txtNumber.setText("Null");
        }
        if (plots.getAgentName() != null) {
            holder.txtStatus.setText(": " + searchArrayList.get(position).getAgentName());
        } else {
            holder.txtStatus.setText("Null");
        }


        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(holder.card_view.getContext());
                dialog.setContentView(R.layout.dialogeditnotice);

                Button btnYes = (Button) dialog.findViewById(R.id.dialogButtoncancle);

                TextView txtCustomerName = (TextView) dialog.findViewById(R.id.txt_customer_name_value);
                TextView txtTotalAmount = (TextView) dialog.findViewById(R.id.txt_total_amount_value);
                TextView txtSoldDate = (TextView) dialog.findViewById(R.id.txt_sold_date_value);
                TextView txtPaidAmount = (TextView) dialog.findViewById(R.id.txt_paid_amount_value);
                TextView txtPendingAmount = (TextView) dialog.findViewById(R.id.txt_panding_amount_value);
                TextView txtAgentName = (TextView) dialog.findViewById(R.id.txt_agent_name_value);
                TextView txtPlotNumber = (TextView) dialog.findViewById(R.id.txt_plot_number_value);

                txtPlotNumber.setText(": "+ plots.getPlotnumber());
                txtCustomerName.setText(": "+ plots.getCustomerNmae());
                txtTotalAmount.setText(": "+ plots.getPlotPrice());
                txtPaidAmount.setText(": "+ plots.getPayedAmount());
                txtPendingAmount.setText(": "+ plots.getRemainingAmount());
                txtAgentName.setText(": "+ plots.getAgentName());
                txtSoldDate.setText(": "+ Utility.convertMilliSecondsToFormatedDate(searchArrayList.get(position).getCreatedDateTimeLong(), GLOBAL_DATE_FORMATE));


                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
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

    public void reload(ArrayList<Plots> leedsModelArrayList) {
        searchArrayList.clear();
        searchArrayList.addAll(leedsModelArrayList);
        notifyDataSetChanged();
    }

}