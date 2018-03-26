package com.anrongtec.laucher.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.bean.focusperson.EKongPersonInfos;
import com.anrongtec.laucher.bean.sos.SosNewBean;
import com.anrongtec.laucher.netconfig.UserService;
import com.anrongtec.laucher.util.BitmapUtil;
import com.anrongtec.laucher.util.StringUtil;

import java.util.List;

/**
 * Created by huiliu on 2018/3/3.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class EKongAdapter extends RecyclerView.Adapter<EKongAdapter.EKongAdapterViewHolder> {

    private List<EKongPersonInfos> typeInfos;

    private Context context;

    private View.OnClickListener listener;

    public EKongAdapter(List<EKongPersonInfos> typeInfos, Context context, View.OnClickListener listener) {
        this.typeInfos = typeInfos;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public EKongAdapter.EKongAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message_info, parent, false);
        return new EKongAdapter.EKongAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EKongAdapter.EKongAdapterViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        final EKongPersonInfos message = typeInfos.get(position);
        if (message != null) {
            if (!TextUtils.isEmpty(message.getXp())) {
                if (BitmapUtil.stringtoBitmap(message.getXp()) != null) {
                    holder.ivPhoto.setImageBitmap(BitmapUtil.stringtoBitmap(message.getXp()));
                }
            } else {
                holder.ivPhoto.setImageResource(R.drawable.user_photo_icon);
            }
            String xxwb = message.getXxwb();
            holder.tv_come_time.setText(StringUtil.stampToDate(String.valueOf(message.getCreate_time())));
            holder.tv_name.setText(message.getXm());
            holder.tv_id.setText("身份证:" + message.getSfzh());
            String xzdxz = TextUtils.isEmpty(message.getCxdd()) ? "" : message.getCxdd();
            holder.tv_location.setText("地址:" + xzdxz);
            holder.tv_reason.setText(xxwb);
            int status = message.getStatus();
            holder.iv_focus_people_media.setVisibility(View.GONE);
            switch (status) {
                case 1:
                    holder.tv_item_person_attention.setText("扣留");
                    break;
                case 2:
                    holder.tv_item_person_attention.setText("已扣留");
                    break;
                default:
                    holder.tv_item_person_attention.setText("扣留");
                    break;
            }
//            holder.tv_station_start_station.setText(StationUtil.getStation(start));
//            holder.tv_station_end_station.setText(StationUtil.getStation(end));

            holder.ll_deal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toZhiYu(message.getSfzh());
                }
            });
            holder.ll_attention.setOnClickListener(listener);
            holder.ll_attention.setTag(position);

        }
        holder.rl_item_msg.setTag(position);
        holder.rl_item_msg.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return typeInfos == null ? 0 : typeInfos.size();
    }

    class EKongAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;
        ImageView iv_focus_people_media;
        TextView tv_name;
        TextView tv_state;
        TextView tv_id;
        TextView tv_location;
        TextView tv_reason;
        TextView tv_sex;
        TextView tv_station_start_station;
        TextView tv_station_end_station;
        TextView tv_age;
        TextView tv_come_time;
        TextView tv_item_person_attention;

        LinearLayout ll_attention;
        LinearLayout ll_deal;
        LinearLayout ll_phone;
        RelativeLayout rl_item_msg;

        EKongAdapterViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_item_msg_user_photo);
            iv_focus_people_media = (ImageView) itemView.findViewById(R.id.iv_focus_people_media);
            tv_name = (TextView) itemView.findViewById(R.id.tv_item_msg_name);
            tv_state = (TextView) itemView.findViewById(R.id.tv_item_msg_state);
            tv_sex = (TextView) itemView.findViewById(R.id.tv_item_msg_sex);
            tv_id = (TextView) itemView.findViewById(R.id.tv_item_person_id);
            tv_come_time = (TextView) itemView.findViewById(R.id.tv_item_person_come_time);
            tv_location = (TextView) itemView.findViewById(R.id.tv_item_person_location);
            tv_reason = (TextView) itemView.findViewById(R.id.tv_item_person_reason);
            tv_station_start_station = (TextView) itemView.findViewById(R.id.tv_item_person_start_station);
            tv_station_end_station = (TextView) itemView.findViewById(R.id.tv_item_person_end_station);
            ll_attention = (LinearLayout) itemView.findViewById(R.id.ll_focus_people_attention);
            ll_deal = (LinearLayout) itemView.findViewById(R.id.ll_focus_people_deal);
            ll_phone = (LinearLayout) itemView.findViewById(R.id.ll_focus_people_tel);
            rl_item_msg = (RelativeLayout) itemView.findViewById(R.id.rl_item_msg);
            tv_item_person_attention = (TextView) itemView.findViewById(R.id.tv_item_person_attention);
        }
    }

    private void toZhiYu(String id) {
        Intent intent = new Intent();
        SosNewBean userInfo = UserService.getNewUserInfo(context);
        String identifier = userInfo.getIdentifier();
        String code = userInfo.getCode();
        //系统登录民警身份证号码
        intent.putExtra("SYSTEM_USER_SFZH", identifier);
        //系统登录民警警号
        intent.putExtra("SYSTEM_USER_JH", code);
        //被盘查人员身份证号码
        intent.putExtra("BPCRY_SFZH", id);
        // com.mosty.ydjw.xlpc.person.xhw.VIEW；//agniwill（新虹伟）手机地址
        intent.setAction("com.mosty.ydjw.xlpc.person.VIEW");
        context.startActivity(intent);
    }
}
