package com.example.zxl.testrefresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Description:
 * Created by zxl on 2016/11/1109.
 */

public class SecondAdapter extends RecyclerView.Adapter<SecondAdapter.SecondViewHolder> {
    private Context mContext;
    private List<String> data;

    public SecondAdapter(Context mContext, List<String> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public SecondViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SecondViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_secondadapter, parent, false));
    }

    @Override
    public void onBindViewHolder(SecondViewHolder holder, final int position) {
        holder.tv_title.setText(data.get(position));
        holder.contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListerner != null)
                    onItemClickListerner.onItemClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public static class SecondViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private View contentView;

        public SecondViewHolder(View itemView) {
            super(itemView);
            contentView = itemView;
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

    private OnItemClickListerner onItemClickListerner;

    public interface OnItemClickListerner {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListerner(OnItemClickListerner onItemClickListerner) {
        this.onItemClickListerner = onItemClickListerner;
    }
}
