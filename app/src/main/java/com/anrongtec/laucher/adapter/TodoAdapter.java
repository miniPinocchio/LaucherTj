package com.anrongtec.laucher.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.bean.todo.TodoWorkDetail;
import com.anrongtec.laucher.util.LogUtil;

import java.util.List;

/**
 * @author huiliu
 * @date 2017/11/20
 * @email liu594545591@126.com
 * @introduce
 */
public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private Context mContext;
    private List<TodoWorkDetail> toDoList;
    private View.OnClickListener listener;

    public TodoAdapter(Context context, List<TodoWorkDetail> toDoList, View.OnClickListener listener) {
        mContext = context;
        this.toDoList = toDoList;
        this.listener = listener;
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_to_do_task, parent, false);
        return new TodoViewHolder(inflate);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        TodoWorkDetail workDetail = toDoList.get(position);
        holder.ll_item_to_do.setTag(workDetail);
        holder.ll_item_to_do.setOnClickListener(listener);
        LogUtil.d(workDetail.getType() + "");
        switch (workDetail.getType()) {
            case 1:
                holder.iv_to_do_img.setImageResource(R.drawable.img_to_do_meeting);
                holder.tv_to_do_type.setTextColor(R.color.cherokee);
                holder.tv_to_do_type.setText("会议");
                break;
            case 2:
                holder.iv_to_do_img.setImageResource(R.drawable.img_to_do_email);
                holder.tv_to_do_type.setTextColor(R.color.dull_cyan);
                holder.tv_to_do_type.setText("邮件");
                break;
            case 3:
                holder.iv_to_do_img.setImageResource(R.drawable.img_to_do_document);
                holder.tv_to_do_type.setTextColor(R.color.deep_chestnut);
                holder.tv_to_do_type.setText("公文");
                break;
            case 4:
                holder.iv_to_do_img.setImageResource(R.drawable.img_to_do_supervise);
                holder.tv_to_do_type.setTextColor(R.color.air_force_blue);
                holder.tv_to_do_type.setText("督办");
                break;
            case 5:
                holder.iv_to_do_img.setImageResource(R.drawable.img_to_do_notice);
                holder.tv_to_do_type.setTextColor(R.color.air_force_blues);
                holder.tv_to_do_type.setText("通知公告");
                break;
            default:
                break;

        }
        holder.tv_to_do_title.setText(workDetail.getTitle());
        holder.tv_to_do_create.setText(workDetail.getName());
//        holder.tv_to_do_content.setText(workDetail.get);
        holder.tv_to_do_time.setText(workDetail.getShowTime());
    }

    @Override
    public int getItemCount() {
        return toDoList.size() > 0 ? toDoList.size() : 0;
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_item_to_do;
        ImageView iv_to_do_img;
        TextView tv_to_do_title;
        TextView tv_to_do_type;
        TextView tv_to_do_create;
        TextView tv_to_do_content;
        TextView tv_to_do_time;

        public TodoViewHolder(View itemView) {
            super(itemView);
            iv_to_do_img = (ImageView) itemView.findViewById(R.id.iv_to_do_img);
            tv_to_do_title = (TextView) itemView.findViewById(R.id.tv_to_do_title);
            tv_to_do_type = (TextView) itemView.findViewById(R.id.tv_to_do_type);
            tv_to_do_create = (TextView) itemView.findViewById(R.id.tv_to_do_create);
            tv_to_do_content = (TextView) itemView.findViewById(R.id.tv_to_do_content);
            tv_to_do_time = (TextView) itemView.findViewById(R.id.tv_to_do_time);
            ll_item_to_do = (LinearLayout) itemView.findViewById(R.id.ll_item_to_do);
        }
    }
}
