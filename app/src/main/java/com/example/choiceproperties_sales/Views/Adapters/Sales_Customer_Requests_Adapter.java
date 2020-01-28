package com.example.choiceproperties_sales.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.choiceproperties_sales.CallBack.CallBack;
import com.example.choiceproperties_sales.Constant.Constant;
import com.example.choiceproperties_sales.Models.Requests;
import com.example.choiceproperties_sales.Models.User;
import com.example.choiceproperties_sales.R;
import com.example.choiceproperties_sales.Views.dialog.ProgressDialogClass;
import com.example.choiceproperties_sales.repository.LeedRepository;
import com.example.choiceproperties_sales.repository.impl.LeedRepositoryImpl;
import com.example.choiceproperties_sales.utilities.Utility;

import java.util.List;
import java.util.Map;

public class Sales_Customer_Requests_Adapter extends RecyclerView.Adapter<Sales_Customer_Requests_Adapter.ViewHolder> {

    private static List<Requests> searchArrayList;
    private Context context;
    private boolean isFromRequest;
    ProgressDialogClass progressDialogClass;
    LeedRepository leedRepository;

    public Sales_Customer_Requests_Adapter(Context context, List<Requests> userArrayList, boolean isFromRequest) {
        this.context = context;
        this.searchArrayList = userArrayList;
        this.isFromRequest = isFromRequest;
    }

    @Override
    public Sales_Customer_Requests_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sales_generated_requests_adapter_layout, parent, false);
        Sales_Customer_Requests_Adapter.ViewHolder viewHolder = new ViewHolder(v);
        //  context = parent.getContext();
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final Sales_Customer_Requests_Adapter.ViewHolder holder, int position) {
        final Requests request = searchArrayList.get(position);
        leedRepository = new LeedRepositoryImpl();

        if (request.getName() != null) {
            holder.txtCustomerName.setText(": " + searchArrayList.get(position).getName());
        } else {
            holder.txtCustomerName.setText(": Null");
        }
        if (request.getAddress() != null) {
            holder.txtAddress.setText(": " + searchArrayList.get(position).getAddress());
        } else {
            holder.txtAddress.setText(": Null");
        }
        if (request.getMobile() != null) {
            holder.txtNumber.setText(": " + searchArrayList.get(position).getMobile());
        } else {
            holder.txtNumber.setText(": Null");
        }
        if (request.getStatus() != null) {
            holder.txtStatus.setText(": " + searchArrayList.get(position).getStatus());
        } else {
            holder.txtStatus.setText(": Null");
        }

        holder.card_view_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLeedStatus(request);
            }

            private void setLeedStatus(Requests requests) {
                requests.setStatus(Constant.STATUS_REQUEST_VERIFIED);
                updateLeed(requests.getRequestId(), requests.getLeedStatusMap1());
            }
            private void updateLeed(String leedId, Map leedsMap) {

                leedRepository.updateRequest(leedId, leedsMap, new CallBack() {
                    @Override
                    public void onSuccess(Object object) {
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Object object) {
                        Context context1 = context;
                        Utility.showLongMessage(context1, context1.getString(R.string.server_error));

                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return searchArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtCustomerName, txtAddress, txtNumber, txtStatus;
        CardView card_view, card_view_status;
        LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);


            txtCustomerName = (TextView) itemView.findViewById(R.id.txt_txt_customer_name_value);
            txtAddress = (TextView) itemView.findViewById(R.id.txt_address_value);
            txtNumber = (TextView) itemView.findViewById(R.id.txt_number_value);
            txtStatus = (TextView) itemView.findViewById(R.id.txt_status_value);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
            card_view_status = (CardView) itemView.findViewById(R.id.card_view_status);
//            layout = (LinearLayout) itemView.findViewById(R.id.layoutdetails);

        }
    }

}