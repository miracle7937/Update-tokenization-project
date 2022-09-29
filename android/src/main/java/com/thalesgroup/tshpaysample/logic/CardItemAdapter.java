package com.thalesgroup.tshpaysample.logic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flutter_tokenization_plugin.R;

import java.util.List;

public class CardItemAdapter extends RecyclerView.Adapter<CardItemAdapter.ViewHolder>{
    List<CardModel> cardModelList;
    private Context context;
    private OnItemClickListener clickListener;


    public CardItemAdapter(List<CardModel> initData, Context context) {
        this.cardModelList = initData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_front,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.expDate.setText(cardModelList.get(position).getExpiryDate());
        holder.pan.setText(cardModelList.get(position).getMaskedFPan());
    }

    @Override
    public int getItemCount() {
        return cardModelList.size();
    }
    public void setClickListener(OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        TextView cardType, expDate, pan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardType =  itemView.findViewById(R.id.view_card_front_type);
            expDate = itemView.findViewById(R.id.view_card_front_exp);
            pan = itemView.findViewById(R.id.view_card_front_pan);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());

        }
    }


}
