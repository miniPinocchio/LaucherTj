package com.anrongtec.laucher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.bean.favorite.Rows;
import com.anrongtec.laucher.util.StringUtil;

import java.util.List;

/**
 *
 * @author huiliu
 * @date 2017/11/22
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.CollectViewHolder> {
    private Context mContext;
    private List<Rows> toDoList;
    private View.OnClickListener listener;

    public CollectAdapter(Context context, List<Rows> toDoList, View.OnClickListener listener) {
        mContext = context;
        this.toDoList = toDoList;
        this.listener = listener;
    }
    @Override
    public CollectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_favorate, parent, false);
        return new CollectViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(CollectViewHolder holder, int position) {
        Rows rows = toDoList.get(position);
//        holder.iv_favorite_img.setImageResource();
        holder.tv_favorite_title.setText(rows.getTitle());
        holder.tv_favorite_content.setText(rows.getContent());
        String stampTime = StringUtil.stampToDate(String.valueOf(rows.getWatch_time()));
        holder.tv_favorite_time.setText(stampTime);
        holder.ll_favorite_item.setOnClickListener(listener);
        holder.ll_favorite_item.setTag(position);
    }

    @Override
    public int getItemCount() {
        return toDoList.size() > 0 ? toDoList.size() : 0;
    }

    class CollectViewHolder extends RecyclerView.ViewHolder{
        LinearLayout ll_favorite_item;
        ImageView iv_favorite_img;
        TextView tv_favorite_title;
        TextView tv_favorite_content;
        TextView tv_favorite_time;

        public CollectViewHolder(View itemView) {
            super(itemView);
            iv_favorite_img = (ImageView) itemView.findViewById(R.id.iv_favorite_img);
            tv_favorite_title = (TextView) itemView.findViewById(R.id.tv_favorite_title);
            tv_favorite_content = (TextView) itemView.findViewById(R.id.tv_favorite_content);
            tv_favorite_time = (TextView) itemView.findViewById(R.id.tv_favorite_time);
            ll_favorite_item = (LinearLayout) itemView.findViewById(R.id.ll_favorite_item);
        }
    }
}
