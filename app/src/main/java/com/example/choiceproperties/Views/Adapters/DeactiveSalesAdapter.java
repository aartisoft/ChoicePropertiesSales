package com.example.choiceproperties.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.choiceproperties.Models.User;
import com.example.choiceproperties.R;

import java.util.List;

public class DeactiveSalesAdapter extends RecyclerView.Adapter<DeactiveSalesAdapter.ViewHolder> {

    private static List<User> searchArrayList;
    private Context context;
    private boolean isFromRequest;


    public DeactiveSalesAdapter(Context context, List<User> userArrayList, boolean isFromRequest) {
        this.context = context;
        this.searchArrayList = userArrayList;
        this.isFromRequest = isFromRequest;
    }

    @Override
    public DeactiveSalesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sales_adapter_layout, parent, false);
        DeactiveSalesAdapter.ViewHolder viewHolder = new ViewHolder(v);
        //  context = parent.getContext();
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final DeactiveSalesAdapter.ViewHolder holder, int position) {
        final User user = searchArrayList.get(position);

        holder.txtName.setText(searchArrayList.get(position).getUserName());
        holder.txtType.setText(searchArrayList.get(position)
                .getRole());
        holder.txtcontact.setText(searchArrayList.get(position).getMobileNumber());
        try {
            holder.txtstatus.setText(searchArrayList.get(position).getStatus());
        }catch (Exception e){}

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(holder.card_view.getContext(), Update_User_Activity.class);
//                intent.putExtra("user", user);
//                holder.card_view.getContext().startActivity(intent);
            }
        });
        holder.txtstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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