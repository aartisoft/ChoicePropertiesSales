package com.example.choiceproperties_sales.Views.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.choiceproperties_sales.CallBack.CallBack;
import com.example.choiceproperties_sales.Constant.Constant;
import com.example.choiceproperties_sales.Models.Customer;
import com.example.choiceproperties_sales.Models.Requests;
import com.example.choiceproperties_sales.R;
import com.example.choiceproperties_sales.Views.dialog.ProgressDialogClass;
import com.example.choiceproperties_sales.repository.LeedRepository;
import com.example.choiceproperties_sales.repository.UserRepository;
import com.example.choiceproperties_sales.repository.impl.LeedRepositoryImpl;
import com.example.choiceproperties_sales.repository.impl.UserRepositoryImpl;

import java.util.List;

public class Sales_Customer_Verified_Requests_Adapter extends RecyclerView.Adapter<Sales_Customer_Verified_Requests_Adapter.ViewHolder> {

    private static List<Requests> searchArrayList;
    private Context context;
    private boolean isFromRequest;
    ProgressDialogClass progressDialogClass;
    UserRepository userRepository;

    public Sales_Customer_Verified_Requests_Adapter(Context context, List<Requests> userArrayList, boolean isFromRequest) {
        this.context = context;
        this.searchArrayList = userArrayList;
        this.isFromRequest = isFromRequest;
    }

    @Override
    public Sales_Customer_Verified_Requests_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sales_verified_requests_adapter_layout, parent, false);
        Sales_Customer_Verified_Requests_Adapter.ViewHolder viewHolder = new ViewHolder(v);
        //  context = parent.getContext();
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final Sales_Customer_Verified_Requests_Adapter.ViewHolder holder, int position) {
        final Requests request = searchArrayList.get(position);

        userRepository = new UserRepositoryImpl();
        if (request.getName() != null) {
            holder.txtCustomerName.setText(": " + searchArrayList.get(position).getName());
        } else {
            holder.txtCustomerName.setText(": Null");
        }
        if (request.getAddress() != null) {
            holder.txtAddress.setText(": " + searchArrayList.get(position).getAddress());
        }else {
            holder.txtAddress.setText(": Null");
        }
        if (request.getMobile() != null) {
            holder.txtNumber.setText(": " + searchArrayList.get(position).getMobile());
        }else {
            holder.txtNumber.setText(": Null");
        }
        if (request.getStatus() != null) {
            holder.txtStatus.setText(": " + searchArrayList.get(position).getStatus());
        }else {
            holder.txtStatus.setText(": Null");
        }

        holder.card_view_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(holder.card_view.getContext());
                dialog.setContentView(R.layout.dialogsetsalesperson);

                Button btnUpdate = (Button) dialog.findViewById(R.id.dialogButtonupdate);
                Button btnCancle = (Button) dialog.findViewById(R.id.dialogButtoncancle);
                final EditText SalesPerson = (EditText) dialog.findViewById(R.id.edit_attended_by);

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Customer customer = new Customer();
                        customer.setAddress(request.getAddress());
                        customer.setDiscussion(request.getMessage());
                        customer.setMobile(request.getMobile());
                        customer.setName(request.getName());
                        customer.setStatus(Constant.STATUS_REQUEST_VISITED);
                        customer.setCustomerId(Constant.CUSTOMERS_TABLE_REF.push().getKey());
                        customer.setCreatedDateTime(request.getCreatedDateTimeLong());
                        customer.setAttendedBy(SalesPerson.getText().toString());

                        userRepository.createCustomer(customer, new CallBack() {
                            @Override
                            public void onSuccess(Object object) {
                                Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }

                            @Override
                            public void onError(Object object) {

                            }
                        });
                    }
                });

                btnCancle.setOnClickListener(new View.OnClickListener() {
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
        CardView card_view,card_view_status;
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