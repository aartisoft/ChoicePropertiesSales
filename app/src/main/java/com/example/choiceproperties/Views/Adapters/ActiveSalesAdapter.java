package com.example.choiceproperties.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.choiceproperties.CallBack.CallBack;
import com.example.choiceproperties.Constant.Constant;
import com.example.choiceproperties.Models.User;
import com.example.choiceproperties.R;
import com.example.choiceproperties.repository.LeedRepository;
import com.example.choiceproperties.repository.impl.LeedRepositoryImpl;
import com.example.choiceproperties.utilities.Utility;

import java.util.List;
import java.util.Map;

public class ActiveSalesAdapter extends RecyclerView.Adapter<ActiveSalesAdapter.ViewHolder> {

    private static List<User> searchArrayList;
    private Context context;
    private boolean isFromRequest;
    LeedRepository leedRepository;


    public ActiveSalesAdapter(Context context, List<User> userArrayList, boolean isFromRequest) {
        this.context = context;
        this.searchArrayList = userArrayList;
        this.isFromRequest = isFromRequest;
    }

    @Override
    public ActiveSalesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sales_active_adapter_layout, parent, false);
        ActiveSalesAdapter.ViewHolder viewHolder = new ViewHolder(v);
        //  context = parent.getContext();
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ActiveSalesAdapter.ViewHolder holder, int position) {
        final User user = searchArrayList.get(position);
        leedRepository = new LeedRepositoryImpl();

        holder.txtName.setText(searchArrayList.get(position).getUserName());
        holder.txtType.setText(searchArrayList.get(position)
                .getRole());
        holder.txtcontact.setText(searchArrayList.get(position).getMobileNumber());
        try {
            holder.txtstatus.setText(searchArrayList.get(position).getStatus());
        }catch (Exception e){}


        holder.txtstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLeedStatus(user);
            }

            private void setLeedStatus(User user) {
                user.setStatus(Constant.STATUS_DEACTIVE);
                updateLeed(user.getUserId(), user.getLeedStatusMap1());
            }

            private void updateLeed(String leedId, Map leedsMap) {

                leedRepository = new LeedRepositoryImpl();
                leedRepository.updateUser(leedId, leedsMap, new CallBack() {
                    @Override
                    public void onSuccess(Object object) {
//                progressDialogClass.dismissDialog();

                    }

                    @Override
                    public void onError(Object object) {
//                progressDialogClass.dismissDialog();
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

        TextView txtName, txtType, txtcontact, txtstatus;
        CardView card_view;
        LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);


            txtName = (TextView) itemView.findViewById(R.id.txtidvalue);
            txtType = (TextView) itemView.findViewById(R.id.txtcnamevalue);
            txtcontact = (TextView) itemView.findViewById(R.id.txtbankvalue);
            txtstatus = (TextView) itemView.findViewById(R.id.textview_user_status);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
            layout = (LinearLayout) itemView.findViewById(R.id.layoutdetails);

        }
    }

}