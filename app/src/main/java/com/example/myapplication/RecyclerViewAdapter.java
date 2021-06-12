package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<SearchHolder> {

    private Context mContext;
    private List<Search> searchList;

    RecyclerViewAdapter(Context mContext, List<Search> searchList) {
        this.mContext = mContext;
        this.searchList = searchList;
    }

    @Override
    public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_row, parent, false);
        return new SearchHolder(mView);
    }

    @Override
    public void onBindViewHolder(final SearchHolder holder, int position) {
//        holder.mImage.setImageResource(searchList.get(position).getFlowerImage());
        Glide.with(mContext)
                .load(searchList.get(position).getPoster())
                .into(holder.mImage);
        holder.mTitle.setText(searchList.get(position).getTitle());
        holder.mYear.setText(searchList.get(position).getYear());
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }
}

class SearchHolder extends RecyclerView.ViewHolder {

    ImageView mImage;
    TextView mTitle;
    TextView mYear;

    SearchHolder(View itemView) {
        super(itemView);

        mImage = itemView.findViewById(R.id.ivImage);
        mTitle = itemView.findViewById(R.id.tvName);
        mYear = itemView.findViewById(R.id.tvYear);
    }
}