package com.example.phemmelliot.concrypt.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phemmelliot.concrypt.R;

import java.util.ArrayList;
import com.example.phemmelliot.concrypt.model.Currency;

/**
 * Created by phemmelliot on 11/3/17.
 */

public class ConcryptAdapter extends RecyclerView.Adapter<ConcryptAdapter.ConcryptViewHolder> {
    private ArrayList<Currency> currencies;
    private Context context;
    final private onItemClickListener mOnItemClickListener;

    public interface onItemClickListener{
        void onItemClick(int itemIndex);
    }
    public ConcryptAdapter(ArrayList<Currency> currencies, Context context, onItemClickListener mOnItemClickListener)
    {
        this.currencies = currencies;
        this.context = context;
        this.mOnItemClickListener = mOnItemClickListener;
    }
    @Override
    public ConcryptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ConcryptViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConcryptViewHolder holder, int position) {
        holder.image.setImageResource(currencies.get(position).getImage());
        holder.textView.setText(currencies.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return currencies.size();
    }

    public class ConcryptViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;
        TextView textView;
        public ConcryptViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image1);
            textView = itemView.findViewById(R.id.text1);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnItemClickListener.onItemClick(getAdapterPosition());
        }
    }
}
