package com.anrongtec.laucher.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.TjApp;
import com.anrongtec.laucher.bean.sign.SignData;
import com.anrongtec.laucher.bean.sign.SignInfo;
import com.anrongtec.laucher.bean.sign.SignInfoData;
import com.anrongtec.laucher.bean.sos.SosNewBean;
import com.anrongtec.laucher.netconfig.UserService;
import com.anrongtec.laucher.util.GsonUtil;
import com.anrongtec.laucher.util.LogUtil;
import com.anrongtec.laucher.util.StringUtil;
import com.anrongtec.laucher.widget.CustomToast;
import com.anrongtec.laucher.widget.mycalender.FlexibleCalendarView;
import com.anrongtec.laucher.widget.mycalender.entity.CalendarEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author huiliu
 */
public class SignStatisticsFragment extends BaseFragment implements Callback<String>, FlexibleCalendarView.OnMonthChangeListener {

    @BindView(R.id.tv_sign_month)
    TextView mTvSignMonth;
    @BindView(R.id.flex_table)
    FlexibleCalendarView mFlexTable;
    @BindView(R.id.tv_sign_days)
    TextView mTvSignDays;
    @BindView(R.id.tv_late_days)
    TextView mTvLateDays;
    Unbinder unbinder;
    @BindView(R.id.btn_next)
    Button mBtnNext;
    @BindView(R.id.btn_previous)
    Button mBtnPrevious;

    private SosNewBean mUserInfo;

    private int mType;
    private String mDateSign;
    private List<SignInfoData> mRows = new ArrayList<>();
    private Map<String, List<CalendarEvent>> eventMap;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 99:
                    addEvent();
                    break;
                default:
                    break;
            }
        }
    };
    private List<CalendarEvent> mEvents;

    public SignStatisticsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_statistics, container, false);
        unbinder = ButterKnife.bind(this, view);
        eventMap = new HashMap<>();
        initView();
        initSign(mDateSign);
        fillEvents();
        mFlexTable.setOnMonthChangeListener(this);
        return view;
    }

    private void initView() {
        mDateSign = StringUtil.stampToMonth(System.currentTimeMillis());
        mTvSignMonth.setText(mDateSign);
    }

    private void initSign(String month) {
        mType = 2;
        mUserInfo = UserService.getNewUserInfo(getActivity());
        TjApp.getRetrofit().getSign("code=" + mUserInfo.getCode() + "&timeNy=" +
                month, "1", "10").enqueue(SignStatisticsFragment.this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        String data = response.body();
        if (response.isSuccessful()) {
            resolveData(data);
        } else {
            CustomToast.INSTANCE.showToast(getActivity(), "服务器异常" + response.code());
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        CustomToast.INSTANCE.showToast(getActivity(), "网络异常");
    }

    /**
     * 解析数据
     *
     * @param data
     */
    private void resolveData(String data) {
        if (data != null) {
            SignInfo signInfo = GsonUtil.parseJsonWithGson(data, SignInfo.class);
            SignData signInfoData = signInfo.getData();
            mRows = signInfoData.getRows();
            mTvSignDays.setText("   本月签到天数: " + mRows.size() + " 天");
            mHandler.sendEmptyMessage(99);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onMonthChange(int year, int month, int direction) {
        mTvSignMonth.setText(year + "-" + (month + 1));
        String mMonth = String.valueOf(month + 1);
        if (month < 10) {
            mMonth = "0" + mMonth;
        }
        initSign(year + "-" + mMonth);
    }

    private void fillEvents() {
        mFlexTable.setEventDataProvider(new FlexibleCalendarView.EventDataProvider() {
            @Override
            public List<CalendarEvent> getEventsForTheDay(int year, int month, int day) {
                mEvents = getEvents(year, month, day);
                if (mEvents != null && mEvents.size() > 0) {
                    int size = mEvents.size();
                    LogUtil.d(size + "");
                    return mEvents;
                }
                return null;
            }
        });

    }

    private void addEvent() {
        for (SignInfoData data : mRows) {
            String date = StringUtil.stampToDate(data.getClock_in_time());
            int signYear = Integer.parseInt(date.substring(0, 4));
            int signMonth = Integer.parseInt(date.substring(5, 7)) - 1;
            int signDay = Integer.parseInt(date.substring(8, 10));
            List<CalendarEvent> mEventColors = new ArrayList<>();
            mEventColors.add(new CalendarEvent(android.R.color.holo_red_dark));
            eventMap.put(String.valueOf(signYear) + String.valueOf(signMonth) + String.valueOf(signDay), mEventColors);
            mFlexTable.refresh();
        }
    }

    private List<CalendarEvent> getEvents(int year, int month, int day) {
        return eventMap.get(String.valueOf(year) + String.valueOf(month) + String.valueOf(day));
    }

    @OnClick({R.id.btn_next, R.id.btn_previous})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                break;
            case R.id.btn_previous:
                break;
            default:
                break;
        }
    }
}
