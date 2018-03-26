package com.anrongtec.laucher.ui.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anrongtec.laucher.R;
import com.anrongtec.laucher.TjApp;
import com.anrongtec.laucher.adapter.MainManagerAppsAdapter;
import com.anrongtec.laucher.bean.AppModel;
import com.anrongtec.laucher.bean.message.PermissionPower;
import com.anrongtec.laucher.bean.message.TodoEkongMessage;
import com.anrongtec.laucher.bean.message.TodoMessage;
import com.anrongtec.laucher.bean.photo.UploadPhoto;
import com.anrongtec.laucher.bean.photo.UploadPhotos;
import com.anrongtec.laucher.bean.sos.PoliceStatus;
import com.anrongtec.laucher.bean.sos.SosNewBean;
import com.anrongtec.laucher.bean.userinfo.DeptCode;
import com.anrongtec.laucher.bean.userinfo.LoginOtherData;
import com.anrongtec.laucher.bean.userinfo.LoginOtherDatas;
import com.anrongtec.laucher.bean.userinfo.Now;
import com.anrongtec.laucher.bean.userinfo.WeatherData;
import com.anrongtec.laucher.bean.userinfo.WeatherDatas;
import com.anrongtec.laucher.db.LiteOrmDBUtil;
import com.anrongtec.laucher.netconfig.Constant;
import com.anrongtec.laucher.netconfig.DataSource;
import com.anrongtec.laucher.netconfig.UserService;
import com.anrongtec.laucher.ui.activity.ClipImageActivity;
import com.anrongtec.laucher.ui.activity.EkongFocusActivity;
import com.anrongtec.laucher.ui.activity.LimitCarActivity;
import com.anrongtec.laucher.ui.activity.ManagerAppsActivity;
import com.anrongtec.laucher.ui.activity.ManagerSysAppsActivity;
import com.anrongtec.laucher.ui.activity.MapCenterActivity;
import com.anrongtec.laucher.ui.activity.MessageActivity;
import com.anrongtec.laucher.ui.activity.SearchActivity;
import com.anrongtec.laucher.ui.activity.TeamActivity;
import com.anrongtec.laucher.ui.activity.TodoActivity;
import com.anrongtec.laucher.ui.activity.WeatherActivity;
import com.anrongtec.laucher.util.ApkUtil;
import com.anrongtec.laucher.util.DialogUtil;
import com.anrongtec.laucher.util.DictionaryUtil;
import com.anrongtec.laucher.util.FileProvider7;
import com.anrongtec.laucher.util.FileUtil;
import com.anrongtec.laucher.util.GsonUtil;
import com.anrongtec.laucher.util.LogUtil;
import com.anrongtec.laucher.util.StringUtil;
import com.anrongtec.laucher.widget.CustomToast;
import com.bumptech.glide.Glide;
import com.xdja.feedbacksdk.ui.PublishFeedBackActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 *
 * @author huiliu
 */
public class MainFragment extends Fragment implements View.OnClickListener, Callback<String>, View.OnLongClickListener {

    private final int MAX_ONLINE_TIME = 2560;
    @BindView(R.id.rl_my_task)
    RelativeLayout mRlMyTask;
    @BindView(R.id.rl_my_status)
    RelativeLayout mRlMyStatus;
    @BindView(R.id.rl_my_focus)
    RelativeLayout mRlMyFocus;
    @BindView(R.id.rl_my_resource)
    RelativeLayout mRlMyResource;
    Unbinder unbinder;
    @BindView(R.id.tv_main_user_name)
    TextView mTvMainUserName;
    @BindView(R.id.tv_main_user_id)
    TextView mTvMainUserId;
    @BindView(R.id.iv_main_user_status)
    ImageView mIvMainUserStatus;
    @BindView(R.id.rv_main_tab_app)
    RecyclerView mNormalRv;
    @BindView(R.id.rv_main_tab_system_app)
    RecyclerView mSystemRv;
    @BindView(R.id.tv_main_search)
    TextView mTvMainSearch;
    @BindView(R.id.tv_more_app)
    TextView mTvMoreApp;
    @BindView(R.id.tv_more_system_app)
    TextView mTvMoreSystemApp;
    @BindView(R.id.tv_to_do_content)
    TextView mTvToDoContent;
    @BindView(R.id.tv_my_task_time)
    TextView mTvMyTaskTime;
    @BindView(R.id.tv_net_limit_car)
    TextView tvNetLimitCar;
    @BindView(R.id.ll_net_limit_car)
    LinearLayout mLlNetLimitCar;
    @BindView(R.id.iv_main_user_photo)
    CircleImageView mivPhoto;
    @BindView(R.id.tv_main_status_select)
    TextView tvMainStatusSelect;
    @BindView(R.id.ll_status)
    LinearLayout llStatus;
    @BindView(R.id.iv_main_user_weather)
    ImageView ivMainUserWeather;
    @BindView(R.id.tv_main_status_weather_location)
    TextView tvMainStatusWeatherLocation;
    @BindView(R.id.tv_main_status_weather_temper)
    TextView tvMainStatusWeatherTemper;
    @BindView(R.id.ll_net_weather_info)
    LinearLayout llNetWeatherInfo;
    @BindView(R.id.iv_my_task)
    ImageView ivMyTask;
    @BindView(R.id.tv_un_working)
    TextView tvUnWorking;
    @BindView(R.id.tv_my_task)
    TextView tvMyTask;
    @BindView(R.id.iv_my_status)
    ImageView ivMyStatus;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.iv_my_focus)
    ImageView ivMyFocus;
    @BindView(R.id.tv_focus)
    TextView tvFocus;
    @BindView(R.id.tv_my_focus)
    TextView tvMyFocus;
    @BindView(R.id.tv_my_status_time)
    TextView tvMyStatusTime;
    @BindView(R.id.iv_my_resource)
    ImageView ivMyResource;
    @BindView(R.id.tv_resource)
    TextView tvResource;
    @BindView(R.id.tv_my_resource)
    TextView tvMyResource;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.tv_system_tool)
    TextView tvSystemTool;
    @BindView(R.id.iv_crown)
    ImageView mIvCrown;
    @BindView(R.id.iv_icon_medal)
    ImageView mIvIconMedal;
    @BindView(R.id.iv_my_ekong)
    ImageView mIvMyEkong;
    @BindView(R.id.tv_title_ekong)
    TextView mTvTitleEkong;
    @BindView(R.id.tv_my_ekong_tips)
    TextView mTvMyEkongTips;
    @BindView(R.id.tv_my_ekong_time)
    TextView mTvMyEkongTime;
    @BindView(R.id.rl_my_ekong)
    RelativeLayout mRlMyEkong;


    private List<ResolveInfo> mApps;

    private List<AppModel> mModelLimit;
    private List<AppModel> mSystemModels;
    private List<AppModel> mSystemModelLimit;
    private MainManagerAppsAdapter mAppAdapter;
    private MainManagerAppsAdapter mSysAppAdapter;
    private SosNewBean mUserInfo;
    private LocalBroadcastManager mManager;
    private TodoNoticeReceiver mNoticeReceiver;
    private FocusNoticeReceiver mFocusNoticeReceiver;
    private EKongNoticeReceiver mEKongNoticeReceiver;
    private ManagerAppsReceiver mManagerReceiver;
    private ManagerSysAppsReceiver mSysManagerReceiver;
    private TextView mTvMyTask;
    private TextView mTxtState;

    //    private HorizontalPageLayoutManager horizontalPageLayoutManager;
    //    private PagingScrollHelper scrollHelper;

    private String year;
    private String month;
    private String day;
    private Calendar cal;
    private String my_date;
    private int netType;
    private String xh_name;

    private String filter;//改变状态接口

    //头像2
    private CircleImageView headImage2;
    //调用照相机返回图片文件
    private File tempFile;
    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;


    private LinearLayout ll_line_time;
    private LinearLayout ll_line_regular;
    private LinearLayout ll_task_regular;
    private LinearLayout ll_task;
    private ImageView iv_arrow;
    private ImageView iv_arrow1;
    private TextView tv_star;
    private TextView tv_moon;
    private TextView tv_sun;
    private TextView tv_crown;
    private TextView tv_bronze;
    private TextView tv_bronze1;
    private TextView tv_bronze2;
    private TextView tv_bronze3;
    private TextView tv_bronze4;
    private TextView tv_bronze5;
    private LinearLayout ll_help;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 99:
                    mModelLimit.clear();
                    mModelLimit.addAll(ApkUtil.getAppsFromLiteOrm(getActivity(), false));
                    mAppAdapter.notifyDataSetChanged();
                    break;
                case 100:
                    mSystemModels.clear();
                    mSystemModels.addAll(ApkUtil.getAppsFromLiteOrm(getActivity(), true));
                    mSysAppAdapter.notifyDataSetChanged();
                    break;
                case 101:

                    break;
                case 990:
                    uploadPhotoUrl();
                    break;
                case 200:
                    netType = 2;
                    TjApp.getRetrofit().getServerLocalData("code=" + mUserInfo.getCode() + "&id=" + mUserInfo.getId() + "&depId="
                            + mUserInfo.getDepid() + "&depCode=" + mUserInfo.getDepcode() + "&today=" + "2018-01-24"
                    ).enqueue(MainFragment.this);
                    break;
                default:
                    break;
            }
        }
    };
    private DeptCode mDeptCode;
    private Bitmap mBitMap;
    private String mCropImagePath;
    private UploadPhoto mPhoto;
    private String BASE_URL = "http://20.3.2.47:90";
    private LinearLayout mLlWorking;
    private LinearLayout mLlDuty;
    private LinearLayout mLlMeeting;
    private LinearLayout mLlOutWork;
    private LinearLayout mLlHoliday;
    private LinearLayout mLlIll;
    private String mSubstring = "";
    private boolean show1;
    private boolean show2;
    private boolean show3;
    private boolean show4;
    private int currentPage = 1;
    private ImageView iv_arrow2;
    private TextView text_version;
    private LinearLayout ll_problem_tactics;
    private ImageView iv_arrow3;
    private LinearLayout tv_problem_tactics;
    private StateInfoPop mStateInfoPop;
    private TextView mTv_level;
    private LinearLayout mLl_rank;
    private ImageView mMaxCrown;
    private ImageView mIvMedal;
    private String mStatus;
    private AppStateBroadcastReceiver mAppStateBroadcastReceiver;
    private WeatherData mDatasData;
    private String mDepName;
    private String mAtt_url;
    private LoginOtherData mDatasData1;

    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        mTvMyTask = (TextView) view.findViewById(R.id.tv_my_task);
        unbinder = ButterKnife.bind(this, view);
        mManager = LocalBroadcastManager.getInstance(getActivity());
        initData();
        registerForUninstallApps();
        registerForNotice();
        registerFocusForNotice();
        registerEKongForNotice();
        registerForManagerApps();
        registerForSysManagerApps();
        return view;
    }

    /**
     * E控、警辅核采消息提醒
     */
    private void registerEKongForNotice() {
        IntentFilter filter = new IntentFilter(Constant.NOTICE_EKONG_ACTION);
        mEKongNoticeReceiver = new EKongNoticeReceiver();
        mManager.registerReceiver(mEKongNoticeReceiver, filter);
    }

    /**
     * 注册app卸载广播
     */
    private void registerForUninstallApps() {
        IntentFilter filter = new IntentFilter();
        // APP卸载成功之后
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addDataScheme("package");
        mAppStateBroadcastReceiver = new AppStateBroadcastReceiver();
        mManager.registerReceiver(mAppStateBroadcastReceiver, filter);
    }


    /**
     * app卸载状态监听
     */
    private class AppStateBroadcastReceiver extends BroadcastReceiver {
        private String action = null;
        String packageName = "";

        @Override
        public void onReceive(Context context, Intent intent) {
            action = intent.getAction();
            packageName = intent.getData().getSchemeSpecificPart();
            switch (action) {
                case Intent.ACTION_PACKAGE_REMOVED:// app卸载
                    LiteOrmDBUtil.deleteWhere(AppModel.class, "appPackName", new Object[]{packageName});
                    mHandler.sendEmptyMessage(99);
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 上传头像
     */
    private void uploadPhotoUrl() {
        String code = UserService.getNewUserInfo(getActivity()).getCode();
        String attUrl = mPhoto.getAttUrl();
        netType = 3;
        TjApp.getRetrofit().uploadPhoto("code=" + code + "&photoUrl=" + attUrl).enqueue(MainFragment.this);
    }

    private String getDate() {
        cal = Calendar.getInstance();
        year = String.valueOf(cal.get(Calendar.YEAR));
        month = String.valueOf(cal.get(Calendar.MONTH) + 1);
        day = String.valueOf(cal.get(Calendar.DATE));
        my_date = year + "-" + month + "-" + day;
        return my_date;
    }

    /**
     * 注册系统app变更广播
     */
    private void registerForSysManagerApps() {
        IntentFilter filter = new IntentFilter(Constant.MANAGER_SYS_APPS);
        mSysManagerReceiver = new ManagerSysAppsReceiver();
        mManager.registerReceiver(mSysManagerReceiver, filter);
    }

    /**
     * 注册app变更广播
     */
    private void registerForManagerApps() {
        IntentFilter filter = new IntentFilter(Constant.MANAGER_APPS);
        mManagerReceiver = new ManagerAppsReceiver();
        mManager.registerReceiver(mManagerReceiver, filter);
    }

    /**
     * 注册消息接收广播
     */
    private void registerForNotice() {
        IntentFilter filter = new IntentFilter(Constant.NOTICE_ACTION);
        mNoticeReceiver = new TodoNoticeReceiver();
        mManager.registerReceiver(mNoticeReceiver, filter);
    }

    /**
     * 注册关注消息接收广播
     */
    private void registerFocusForNotice() {
        IntentFilter filter = new IntentFilter(Constant.NOTICE_FOCUS_ACTION);
        mFocusNoticeReceiver = new FocusNoticeReceiver();
        mManager.registerReceiver(mFocusNoticeReceiver, filter);
    }

    private void initData() {
        mUserInfo = UserService.getNewUserInfo(getActivity());
        mTvMainUserName.setText(mUserInfo != null ? mUserInfo.getName() : null);
        mTvMainUserId.setText(mUserInfo.getCode());

        mHandler.sendEmptyMessageDelayed(200, 200);
        mModelLimit = new ArrayList<>();
        mSystemModels = new ArrayList<>();
        mTvMainUserId.setOnLongClickListener(this);

        mModelLimit.addAll(ApkUtil.getAppsFromLiteOrm(getActivity(), false));
        mSystemModels.addAll(ApkUtil.getAppsFromLiteOrm(getActivity(), true));
        mAppAdapter = new MainManagerAppsAdapter(getActivity(), mModelLimit);
        mSysAppAdapter = new MainManagerAppsAdapter(getActivity(), mSystemModels);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4, GridLayoutManager.VERTICAL, false);
        GridLayoutManager layoutManager1 = new GridLayoutManager(getActivity(), 4, GridLayoutManager.VERTICAL, false);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        layoutManager1.setSmoothScrollbarEnabled(true);
        layoutManager1.setAutoMeasureEnabled(true);

        mNormalRv.setLayoutManager(layoutManager);
        mNormalRv.setHasFixedSize(true);
        mNormalRv.setNestedScrollingEnabled(false);
        mSystemRv.setLayoutManager(layoutManager1);
        mSystemRv.setHasFixedSize(true);
        mSystemRv.setNestedScrollingEnabled(false);
        mNormalRv.setAdapter(mAppAdapter);
        mSystemRv.setAdapter(mSysAppAdapter);
    }

    private void initPersonLevel() {
        if (mUserInfo != null && mDatasData1 != null) {
            if (!TextUtils.isEmpty(mDatasData1.getOnlineTime())) {
                String onlineTime = mDatasData1.getOnlineTime();
                if (Integer.parseInt(onlineTime) > MAX_ONLINE_TIME) {
                    mIvCrown.setVisibility(View.VISIBLE);
                }
            }
            if (!TextUtils.isEmpty(mDatasData1.getCompleteTask())) {
                int completeTask = Integer.parseInt(mDatasData1.getCompleteTask());
                if (completeTask >= 0 && completeTask < 10) {
                    mIvIconMedal.setImageResource(R.drawable.honor1);
                } else if (completeTask >= 10 && completeTask < 30) {
                    mIvIconMedal.setImageResource(R.drawable.honor2);
                } else if (completeTask >= 30 && completeTask < 100) {
                    mIvIconMedal.setImageResource(R.drawable.honor3);
                } else if (completeTask >= 100 && completeTask < 300) {
                    mIvIconMedal.setImageResource(R.drawable.honor4);
                } else if (completeTask >= 300) {
                    mIvIconMedal.setImageResource(R.drawable.honor5);
                }
            }
        }

    }

    public static final String ARGS_PAGE = "args_page";

    public static MainFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARGS_PAGE, page);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rl_my_task, R.id.rl_my_status, R.id.rl_my_focus, R.id.rl_my_resource, R.id.tv_main_search, R.id.tv_more_app, R.id.tv_more_system_app,
            R.id.ll_net_limit_car, R.id.iv_main_user_photo, R.id.ll_net_weather_info, R.id.ll_status, R.id.rl_my_ekong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_my_ekong:
                LiteOrmDBUtil.deleteAll(TodoEkongMessage.class);
                mTvMyEkongTips.setVisibility(View.INVISIBLE);
                startActivity(new Intent(getActivity(), EkongFocusActivity.class));
                break;
            case R.id.rl_my_task:
                LiteOrmDBUtil.deleteAll(TodoMessage.class);
                mTvMyTask.setVisibility(View.INVISIBLE);
                mTvToDoContent.setText("");
                mTvMyTaskTime.setText("");
                startActivity(new Intent(getActivity(), TodoActivity.class));
                break;
            case R.id.rl_my_status:
                startActivity(new Intent(getActivity(), TeamActivity.class));
                break;
            case R.id.rl_my_focus:
                tvMyFocus.setVisibility(View.INVISIBLE);
                LiteOrmDBUtil.deleteAll(TodoMessage.class);
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.rl_my_resource:
//                startActivity(new Intent(getActivity(), MapActivity.class));
                startActivity(new Intent(getActivity(), MapCenterActivity.class));
                break;
            case R.id.iv_main_user_photo:
                PersonInfoPop personInfoPop = new PersonInfoPop(getActivity(), this);
                personInfoPop.showAtLocation(view, Gravity.TOP | Gravity.START, 0, 100);
                break;
            case R.id.tv_main_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.tv_more_app:
                startActivity(new Intent(getActivity(), ManagerAppsActivity.class));
                break;
            case R.id.tv_more_system_app:
                startActivity(new Intent(getActivity(), ManagerSysAppsActivity.class));
                break;
            case R.id.ll_net_limit_car:
                startActivity(new Intent(getActivity(), LimitCarActivity.class));
                break;
            case R.id.ll_net_weather_info:
                startActivity(new Intent(getActivity(), WeatherActivity.class));
                break;
            case R.id.ll_status:
                mStateInfoPop = new StateInfoPop(getActivity(), this);
                mStateInfoPop.showAsDropDown(view);
                break;
            default:
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消注册应用内广播接收器
        mManager.unregisterReceiver(mNoticeReceiver);
        mManager.unregisterReceiver(mManagerReceiver);
        mManager.unregisterReceiver(mSysManagerReceiver);
        mManager.unregisterReceiver(mFocusNoticeReceiver);
        mManager.unregisterReceiver(mAppStateBroadcastReceiver);
        mManager.unregisterReceiver(mEKongNoticeReceiver);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_feed_back:
                Intent intent = new Intent(getActivity(), PublishFeedBackActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_upload_photo:
                uploadHeadImage();
                break;
            case R.id.ll_line_regular:
                currentPage = 1;
                showRegular();
                break;
            case R.id.ll_task_regular:
                currentPage = 2;
                showRegular();
                break;
            case R.id.ll_help:
                currentPage = 3;
                showRegular();
                break;
            case R.id.ll_problem_tactics:
                currentPage = 4;
                showRegular();
                break;
            case R.id.ll_online:
                //1在岗, 2值班, 3会议,4	出差,5休假,6病休,7离线,8在线
                mStatus = "8";
                tvMainStatusSelect.setText("在线");
                mIvMainUserStatus.setImageResource(R.drawable.icon_online);
                changeState();
                break;
            case R.id.ll_working:
                mStatus = "1";
                tvMainStatusSelect.setText("在岗");
                mIvMainUserStatus.setImageResource(R.drawable.icon_working);
                changeState();
                break;
            case R.id.ll_duty:
                mStatus = "2";
                tvMainStatusSelect.setText("值班");
                mIvMainUserStatus.setImageResource(R.drawable.icon_duty);
                changeState();
                break;
            case R.id.ll_meeting:
                mStatus = "3";
                tvMainStatusSelect.setText("会议");
                mIvMainUserStatus.setImageResource(R.drawable.icon_meeting);
                changeState();
                break;
            case R.id.ll_out_work:
                mStatus = "4";
                tvMainStatusSelect.setText("出差");
                mIvMainUserStatus.setImageResource(R.drawable.icon_out_work);
                changeState();
                break;
            case R.id.ll_holiday:
                mStatus = "5";
                tvMainStatusSelect.setText("休假");
                mIvMainUserStatus.setImageResource(R.drawable.icon_holiday);
                changeState();
                break;
            case R.id.ll_ill:
                mStatus = "6";
                tvMainStatusSelect.setText("病休");
                mIvMainUserStatus.setImageResource(R.drawable.icon_ill);
                changeState();
                break;
            case R.id.tv_problem1:
                DialogUtil.createNoticeDialog(getActivity(), DataSource.PROBLEM_ONE);
                break;
            case R.id.tv_problem2:
                DialogUtil.createNoticeDialog(getActivity(), DataSource.PROBLEM_TWO);
                break;
            case R.id.tv_problem3:
                DialogUtil.createNoticeDialog(getActivity(), DataSource.PROBLEM_THREE);
                break;
            case R.id.tv_problem4:
                DialogUtil.createNoticeDialog(getActivity(), DataSource.PROBLEM_FOUR);
                break;
            case R.id.tv_problem5:
                DialogUtil.createNoticeDialog(getActivity(), DataSource.PROBLEM_FIVE);
                break;
            default:
                break;
        }
        if (mStateInfoPop != null && mStateInfoPop.isShowing()) {
            mStateInfoPop.dismiss();
        }

    }

    private void changeState() {
        netType = 4;
        String code = mUserInfo.getCode();
        TjApp.getRetrofit().changeState("code=" + code + "&status=" + mStatus).enqueue(this);
    }

    private void showRegular() {
        if (currentPage == 1) {
            iv_arrow.setImageResource(R.drawable.arrow_2);
            ll_line_time.setVisibility(View.VISIBLE);
            iv_arrow1.setImageResource(R.drawable.arrow_1);
            ll_task.setVisibility(View.GONE);
            iv_arrow1.setImageResource(R.drawable.arrow_1);
            text_version.setVisibility(View.GONE);
            iv_arrow3.setImageResource(R.drawable.arrow_1);
            tv_problem_tactics.setVisibility(View.GONE);
            if (show1) {
                iv_arrow.setImageResource(R.drawable.arrow_1);
                ll_line_time.setVisibility(View.GONE);
                show1 = false;
                show2 = false;
                show3 = false;
                show4 = false;
            } else {
                show1 = true;
                show2 = false;
                show3 = false;
                show4 = false;
            }
        }

        if (currentPage == 2) {
            iv_arrow.setImageResource(R.drawable.arrow_1);
            ll_line_time.setVisibility(View.GONE);
            iv_arrow1.setImageResource(R.drawable.arrow_2);
            ll_task.setVisibility(View.VISIBLE);
            iv_arrow1.setImageResource(R.drawable.arrow_1);
            text_version.setVisibility(View.GONE);
            iv_arrow3.setImageResource(R.drawable.arrow_1);
            tv_problem_tactics.setVisibility(View.GONE);
            if (show2) {
                iv_arrow1.setImageResource(R.drawable.arrow_1);
                ll_task.setVisibility(View.GONE);
                show1 = false;
                show2 = false;
                show3 = false;
                show4 = false;
            } else {
                show1 = false;
                show2 = true;
                show3 = false;
                show4 = false;
            }
        }

        if (currentPage == 3) {
            iv_arrow.setImageResource(R.drawable.arrow_1);
            ll_line_time.setVisibility(View.GONE);
            iv_arrow1.setImageResource(R.drawable.arrow_1);
            ll_task.setVisibility(View.GONE);
            iv_arrow1.setImageResource(R.drawable.arrow_2);
            text_version.setVisibility(View.VISIBLE);
            iv_arrow3.setImageResource(R.drawable.arrow_1);
            tv_problem_tactics.setVisibility(View.GONE);
            if (show3) {
                iv_arrow2.setImageResource(R.drawable.arrow_1);
                text_version.setVisibility(View.GONE);
                show1 = false;
                show2 = false;
                show3 = false;
                show4 = false;
            } else {
                show1 = false;
                show2 = false;
                show3 = true;
                show4 = false;
            }
        }

        if (currentPage == 4) {
            iv_arrow.setImageResource(R.drawable.arrow_1);
            ll_line_time.setVisibility(View.GONE);
            iv_arrow1.setImageResource(R.drawable.arrow_1);
            ll_task.setVisibility(View.GONE);
            iv_arrow1.setImageResource(R.drawable.arrow_1);
            text_version.setVisibility(View.GONE);
            iv_arrow3.setImageResource(R.drawable.arrow_2);
            tv_problem_tactics.setVisibility(View.VISIBLE);
            if (show4) {
                iv_arrow3.setImageResource(R.drawable.arrow_1);
                tv_problem_tactics.setVisibility(View.GONE);
                show1 = false;
                show2 = false;
                show3 = false;
                show4 = false;
            } else {
                show1 = false;
                show2 = false;
                show3 = false;
                show4 = true;
                show4 = false;

                show1 = true;
            }
        }
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
            if (netType == 1) {
                LoginOtherDatas datas = GsonUtil.parseJsonWithGson(data, LoginOtherDatas.class);
                mDatasData1 = datas.getData();
                initPersonLevel();
            } else if (netType == 2) {
                WeatherDatas datas = GsonUtil.parseJsonWithGson(data, WeatherDatas.class);
                if (datas != null) {
                    mDatasData = datas.getData();
                    mDepName = mDatasData.getDepName();
                    String limit = mDatasData.getLimit();

                    mAtt_url = mDatasData.getAtt_url();
                    if (mAtt_url != null) {
                        Glide.with(getActivity())
                                .load(BASE_URL + mAtt_url)
                                .crossFade()
                                .into(mivPhoto);
                    }
                    List<PermissionPower> permissionPower = mDatasData.getPermissionPower();
                    for (int i = 0; i < permissionPower.size(); i++) {
                        PermissionPower power = permissionPower.get(i);
                        String p_name = power.getP_name();
                        switch (p_name) {
                            case "无消息推送权限":
                                break;
                            case "二道防线":
                                mRlMyFocus.setVisibility(View.VISIBLE);
                                break;
                            case "e控权限":
                                mRlMyEkong.setVisibility(View.VISIBLE);
                                break;
                            default:
                                break;
                        }
                    }

                    boolean attentionPower = mDatasData.getAttentionPower();
                    if (attentionPower) {
                        mRlMyFocus.setVisibility(View.VISIBLE);
                    }

                    Now now = mDatasData.getNow();
                    if (now != null) {
                        tvMainStatusWeatherLocation.setText(now.getText());
                        tvMainStatusWeatherTemper.setText(now.getTemperature() + "°");
                        int code = Integer.parseInt(now.getCode());
                        showWeather(code);
                    }

                    String personStatus = mDatasData.getPersonStatus();
                    switch (personStatus) {
                        case "8":
                            //1在岗, 2值班, 3会议,4出差,5休假,6病休,7离线,8在线
                            tvMainStatusSelect.setText("在线");
                            mIvMainUserStatus.setImageResource(R.drawable.icon_online);
                            break;
                        case "1":
                            tvMainStatusSelect.setText("在岗");
                            mIvMainUserStatus.setImageResource(R.drawable.icon_working);
                            break;
                        case "2":
                            tvMainStatusSelect.setText("值班");
                            mIvMainUserStatus.setImageResource(R.drawable.icon_duty);
                            break;
                        case "3":
                            tvMainStatusSelect.setText("会议");
                            mIvMainUserStatus.setImageResource(R.drawable.icon_meeting);
                            break;
                        case "4":
                            tvMainStatusSelect.setText("出差");
                            mIvMainUserStatus.setImageResource(R.drawable.icon_out_work);
                            break;
                        case "5":
                            tvMainStatusSelect.setText("休假");
                            mIvMainUserStatus.setImageResource(R.drawable.icon_holiday);
                            break;
                        case "6":
                            tvMainStatusSelect.setText("病休");
                            mIvMainUserStatus.setImageResource(R.drawable.icon_ill);
                            break;
                        default:
                            break;
                    }

                    tvNetLimitCar.setText(limit);
//                    mHandler.sendEmptyMessageDelayed(101, 1000);
                }
                getServerRemote();
            } else if (netType == 3) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String string = jsonObject.optString("msg");
                    CustomToast.INSTANCE.showToast(getActivity(), string);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (netType == 4) {
                PoliceStatus status = GsonUtil.parseJsonWithGson(data, PoliceStatus.class);
                if (status != null) {
                    CustomToast.INSTANCE.showToast(getActivity(), status.getMsg());
                }
            }
        }
    }

    private void getServerRemote() {
        netType = 1;
        String code = mUserInfo.getCode();
        String id = mUserInfo.getId();
        TjApp.getRetrofit().getThirdSqlData("code=" + code + "&id=" + id).enqueue(this);
    }


    /**
     * 发版关掉usb功能
     */
    @Override
    public boolean onLongClick(View v) {
//        DevicePolicyManager mDevicePolicyManager = DevicePolicyManager.getInstance(getActivity());
//        PeripheralPolicy peripheralPolicy =
//                mDevicePolicyManager.getPeripheralPolicy();
//        boolean b = peripheralPolicy.enableUsb(true);
//        boolean b1 = peripheralPolicy.enableWifi(true);
//        if (b && b1) {
//            CustomToast.INSTANCE.showToast(getActivity(), "开启usb&wifi成功");
//        } else {
//            CustomToast.INSTANCE.showToast(getActivity(), "开启usb失败");
//        }
        return false;
    }


    /**
     * 接收消息推送 的广播
     */
    private class TodoNoticeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), Constant.NOTICE_ACTION)) {
                List<TodoMessage> queryAll = LiteOrmDBUtil.getQueryAll(TodoMessage.class);
                if (queryAll != null && mTvMyTask != null) {
                    mTvMyTask.setVisibility(View.VISIBLE);
                    mTvMyTask.setText(String.valueOf(queryAll.size()));
                    mTvToDoContent.setText(queryAll.get(0).getTitle());
                    mTvMyTaskTime.setText(StringUtil.stampToDate(queryAll.get(0).getTime()));
                }
            }
        }
    }

    /**
     * 接收关注消息推送 的广播
     */
    private class FocusNoticeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), Constant.NOTICE_FOCUS_ACTION)) {
                List<TodoMessage> queryAll0 = LiteOrmDBUtil.getQueryByWhere(TodoMessage.class, "type", new Object[]{99});
                List<TodoMessage> queryAll1 = LiteOrmDBUtil.getQueryByWhere(TodoMessage.class, "type", new Object[]{98});

                int queryAll = queryAll0.size() + queryAll1.size();
                if (queryAll > 0 && tvMyFocus != null) {
                    tvMyFocus.setVisibility(View.VISIBLE);
                    tvMyFocus.setText(String.valueOf(queryAll));
                }
            }
        }
    }

    /**
     * 接收E控、警辅核采消息推送 的广播
     */
    private class EKongNoticeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), Constant.NOTICE_EKONG_ACTION)) {
                List<TodoEkongMessage> queryAll2 = LiteOrmDBUtil.getQueryByWhere(TodoEkongMessage.class, "type", new Object[]{97});
                List<TodoEkongMessage> queryAll3 = LiteOrmDBUtil.getQueryByWhere(TodoEkongMessage.class, "type", new Object[]{96});
                int queryAll = queryAll2.size() + queryAll3.size();
                if (queryAll > 0 && mTvMyEkongTips != null) {
                    mTvMyEkongTips.setVisibility(View.VISIBLE);
                    mTvMyEkongTips.setText(String.valueOf(queryAll));
                }
            }
        }
    }

    /**
     * 接收app管理推送 的广播
     */
    private class ManagerAppsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), Constant.MANAGER_APPS)) {
                mHandler.sendEmptyMessage(99);
            }
        }
    }

    /**
     * 接收系统app管理推送 的广播
     */
    private class ManagerSysAppsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), Constant.MANAGER_SYS_APPS)) {
                mHandler.sendEmptyMessage(100);
            }
        }
    }

    public class PersonInfoPop extends PopupWindow {

        private View mMenuView;


        public PersonInfoPop(Activity context, View.OnClickListener itemsOnClick) {
            super(context);
            LayoutInflater inflater = context.getLayoutInflater();
            mMenuView = inflater.inflate(R.layout.select_popupwindows, null);

            ll_line_regular = (LinearLayout) mMenuView.findViewById(R.id.ll_line_regular);
            ll_task_regular = (LinearLayout) mMenuView.findViewById(R.id.ll_task_regular);
            ll_line_time = (LinearLayout) mMenuView.findViewById(R.id.ll_line_time);
            ll_problem_tactics = (LinearLayout) mMenuView.findViewById(R.id.ll_problem_tactics);
            ll_help = (LinearLayout) mMenuView.findViewById(R.id.ll_help);
            ll_task = (LinearLayout) mMenuView.findViewById(R.id.ll_task);
            iv_arrow = (ImageView) mMenuView.findViewById(R.id.iv_arrow);
            iv_arrow1 = (ImageView) mMenuView.findViewById(R.id.iv_arrow1);
            iv_arrow2 = (ImageView) mMenuView.findViewById(R.id.iv_arrow2);
            iv_arrow3 = (ImageView) mMenuView.findViewById(R.id.iv_arrow3);
            tv_star = (TextView) mMenuView.findViewById(R.id.tv_star);
            tv_moon = (TextView) mMenuView.findViewById(R.id.tv_moon);
            tv_sun = (TextView) mMenuView.findViewById(R.id.tv_sun);
            tv_crown = (TextView) mMenuView.findViewById(R.id.tv_crown);
            text_version = (TextView) mMenuView.findViewById(R.id.text_version);
            tv_problem_tactics = mMenuView.findViewById(R.id.tv_problem_tactics);
            //等级图标
            mTv_level = mMenuView.findViewById(R.id.tv_level);
            mLl_rank = mMenuView.findViewById(R.id.rl_person_level);
            mMaxCrown = mMenuView.findViewById(R.id.iv_pop_crown);
            mIvMedal = mMenuView.findViewById(R.id.iv_medal);

            //帮助弹框
            TextView problem1 = mMenuView.findViewById(R.id.tv_problem1);
            TextView problem2 = mMenuView.findViewById(R.id.tv_problem2);
            TextView problem3 = mMenuView.findViewById(R.id.tv_problem3);
            TextView problem4 = mMenuView.findViewById(R.id.tv_problem4);
            TextView problem5 = mMenuView.findViewById(R.id.tv_problem5);

            tv_bronze = (TextView) mMenuView.findViewById(R.id.tv_bronze);
            tv_bronze1 = (TextView) mMenuView.findViewById(R.id.tv_bronze1);
            tv_bronze2 = (TextView) mMenuView.findViewById(R.id.tv_bronze2);
            tv_bronze3 = (TextView) mMenuView.findViewById(R.id.tv_bronze3);
            tv_bronze4 = (TextView) mMenuView.findViewById(R.id.tv_bronze4);
            tv_bronze5 = (TextView) mMenuView.findViewById(R.id.tv_bronze5);

            TextView tv_upload = (TextView) mMenuView.findViewById(R.id.tv_upload_photo);
            headImage2 = (CircleImageView) mMenuView.findViewById(R.id.iv_main_user_photo);


            TextView tv_main_user_name = (TextView) mMenuView.findViewById(R.id.tv_main_user_name);
            TextView tv_main_user_id = (TextView) mMenuView.findViewById(R.id.tv_main_user_id);
            TextView tv_main_user_days = (TextView) mMenuView.findViewById(R.id.tv_main_user_days);
            tv_main_user_name.setText(mUserInfo.getName());
            tv_main_user_id.setText(mDepName);
            tv_main_user_days.setText(mUserInfo.getCode());

            if (mDeptCode != null) {
                tv_main_user_days.setText(mDeptCode.getName());
            }

            if (mBitMap != null) {
                headImage2.setImageBitmap(mBitMap);
            } else if (mAtt_url != null) {
                Glide.with(getActivity())
                        .load(BASE_URL + mAtt_url)
                        .crossFade()
                        .into(headImage2);
            }
            RelativeLayout mRlFeedBack = (RelativeLayout) mMenuView.findViewById(R.id.rl_feed_back);
            mRlFeedBack.setOnClickListener(itemsOnClick);
            ll_line_regular.setOnClickListener(itemsOnClick);
            ll_task_regular.setOnClickListener(itemsOnClick);
            ll_problem_tactics.setOnClickListener(itemsOnClick);
            ll_help.setOnClickListener(itemsOnClick);
            tv_upload.setOnClickListener(itemsOnClick);
            problem1.setOnClickListener(itemsOnClick);
            problem2.setOnClickListener(itemsOnClick);
            problem3.setOnClickListener(itemsOnClick);
            problem4.setOnClickListener(itemsOnClick);
            problem5.setOnClickListener(itemsOnClick);


            tv_main_user_id.setText(mDepName);
            initMedal();
            initRegularData();
            initLevel();
            initVersion();

            //设置SelectPicPopupWindow的View
            this.setContentView(mMenuView);
            //设置SelectPicPopupWindow弹出窗体的宽
            this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            //设置SelectPicPopupWindow弹出窗体的高
            this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            //设置SelectPicPopupWindow弹出窗体可点击
            this.setFocusable(true);
            //设置SelectPicPopupWindow弹出窗体动画效果
            this.setAnimationStyle(R.style.AnimBottom);
            //实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0x00000000);
            //设置SelectPicPopupWindow弹出窗体的背景
            this.setBackgroundDrawable(dw);
            //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
            mMenuView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                @SuppressLint("ClickableViewAccessibility")
                public boolean onTouch(View v, MotionEvent event) {
                    int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                    int y = (int) event.getY();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (y < height) {
                            dismiss();
                        }
                    }
                    return true;
                }
            });
        }

    }


    private void initVersion() {
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageManager packageManager = getActivity().getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packInfo != null) {
//            text_version.setText("当前版本号：" + packInfo.versionName);
        }
    }

    /**
     * 勋章
     */
    private void initMedal() {
        if (mDatasData1 != null && !TextUtils.isEmpty(mDatasData1.getCompleteTask())) {
            int completeTask = Integer.parseInt(mDatasData1.getCompleteTask());
            if (completeTask >= 0 && completeTask < 10) {
                mIvMedal.setImageResource(R.drawable.honor1);
            } else if (completeTask >= 10 && completeTask < 30) {
                mIvMedal.setImageResource(R.drawable.honor2);
            } else if (completeTask >= 30 && completeTask < 100) {
                mIvMedal.setImageResource(R.drawable.honor3);
            } else if (completeTask >= 100 && completeTask < 300) {
                mIvMedal.setImageResource(R.drawable.honor4);
            } else if (completeTask >= 300) {
                mIvMedal.setImageResource(R.drawable.honor5);
            }
        }
    }

    /**
     * 等级
     */
    private void initLevel() {
        if (mDatasData1 != null && !TextUtils.isEmpty(mDatasData1.getOnlineTime())) {
            String onlineTime = mDatasData1.getOnlineTime();
            ArrayList<Integer> level = DictionaryUtil.getLevel(onlineTime);
            Integer star = level.get(0);
            Integer moon = level.get(1);
            Integer sun = level.get(2);
            Integer crown = level.get(3);
            if (Integer.parseInt(onlineTime) < MAX_ONLINE_TIME) {
                if (crown != 0) {
                    for (int i = 0; i < crown; i++) {
                        ImageView imageView = new ImageView(getActivity());
                        imageView.setLayoutParams(new ViewGroup.LayoutParams(50, 50));
                        imageView.setImageResource(R.drawable.level4);
                        mLl_rank.addView(imageView);
                    }
                }
                if (sun != 0) {
                    for (int i = 0; i < sun; i++) {
                        ImageView imageView = new ImageView(getActivity());
                        imageView.setLayoutParams(new ViewGroup.LayoutParams(50, 50));
                        imageView.setImageResource(R.drawable.level3);
                        mLl_rank.addView(imageView);
                    }
                }
                if (moon != 0) {
                    for (int i = 0; i < moon; i++) {
                        ImageView imageView = new ImageView(getActivity());
                        imageView.setLayoutParams(new ViewGroup.LayoutParams(40, 40));
                        imageView.setImageResource(R.drawable.level2);
                        mLl_rank.addView(imageView);
                    }
                }
                if (star >= 0) {
                    //第一次上线 在线时长为0 显示1级
                    if (star == 0) {
                        star += 1;
                    }
                    for (int i = 0; i < star; i++) {
                        ImageView imageView = new ImageView(getActivity());
                        imageView.setLayoutParams(new ViewGroup.LayoutParams(50, 50));
                        imageView.setImageResource(R.drawable.level1);
                        mLl_rank.addView(imageView);
                    }

                }
            } else {
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageResource(R.drawable.level1);
                mLl_rank.addView(imageView);
                mMaxCrown.setVisibility(View.VISIBLE);
            }
            mTv_level.setText(Integer.valueOf(onlineTime) / 10 + "级");
        }
    }

    private void initRegularData() {
        Drawable drawable = getResources().getDrawable(R.drawable.icon_star);
        drawable.setBounds(0, 0, 42, 42);
        tv_star.setCompoundDrawables(null, null, drawable, null);

        Drawable drawable1 = getResources().getDrawable(R.drawable.icon_crown);
        drawable1.setBounds(0, 0, 42, 42);
        tv_crown.setCompoundDrawables(null, null, drawable1, null);

        Drawable drawable2 = getResources().getDrawable(R.drawable.icon_moon);
        drawable2.setBounds(0, 0, 42, 42);
        tv_moon.setCompoundDrawables(null, null, drawable2, null);

        Drawable drawable3 = getResources().getDrawable(R.drawable.icon_sun);
        drawable3.setBounds(0, 0, 42, 42);
        tv_sun.setCompoundDrawables(null, null, drawable3, null);


        Drawable drawable4 = getResources().getDrawable(R.drawable.icon_bronze);
        drawable4.setBounds(0, 0, 35, 52);
        tv_bronze.setCompoundDrawables(null, null, drawable4, null);

        Drawable drawable5 = getResources().getDrawable(R.drawable.icon_bronze1);
        drawable5.setBounds(0, 0, 35, 52);
        tv_bronze1.setCompoundDrawables(null, null, drawable5, null);

        Drawable drawable6 = getResources().getDrawable(R.drawable.icon_bronze2);
        drawable6.setBounds(0, 0, 35, 52);
        tv_bronze2.setCompoundDrawables(null, null, drawable6, null);

        Drawable drawable7 = getResources().getDrawable(R.drawable.icon_bronze3);
        drawable7.setBounds(0, 0, 38, 52);
        tv_bronze3.setCompoundDrawables(null, null, drawable7, null);

        Drawable drawable8 = getResources().getDrawable(R.drawable.icon_bronze4);
        drawable8.setBounds(0, 0, 42, 52);
        tv_bronze4.setCompoundDrawables(null, null, drawable8, null);

        Drawable drawable9 = getResources().getDrawable(R.drawable.icon_bronze5);
        drawable9.setBounds(0, 0, 42, 60);
        tv_bronze5.setCompoundDrawables(null, null, drawable9, null);

    }

    /**
     * 上传头像
     */
    private void uploadHeadImage() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_popupwindow, null);
        TextView btnCarema = (TextView) view.findViewById(R.id.btn_camera);
        TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        View parent = LayoutInflater.from(getActivity()).inflate(R.layout.activity_main, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = 0.5f;
        getActivity().getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getActivity().getWindow().setAttributes(params);
            }
        });

        btnCarema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到调用系统相机
                    gotoCamera();
                }
                popupWindow.dismiss();
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            READ_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到相册
                    gotoPhoto();
                }
                popupWindow.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    /**
     * 跳转到相册
     */
    private void gotoPhoto() {
        Log.d("evan", "*****************打开图库********************");
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
    }


    /**
     * 跳转到照相机
     */
    private void gotoCamera() {
        Log.d("evan", "*****************打开相机********************");
        //创建拍照存储的图片文件
        tempFile = new File(FileUtil.checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"), System.currentTimeMillis() + ".jpg");

        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置7.0中共享文件，分享路径定义在xml/file_paths.xml
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri uriForFile = FileProvider7.getUriForFile(getActivity(), tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, REQUEST_CAPTURE);
    }

    /**
     * 外部存储权限申请返回
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoCamera();
            }
        } else if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoPhoto();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {
                    gotoClipActivity(Uri.fromFile(tempFile));
                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    gotoClipActivity(uri);
                }
                break;
            case REQUEST_CROP_PHOTO:  //剪切图片返回
                if (resultCode == RESULT_OK) {
                    final Uri uri = intent.getData();
                    if (uri == null) {
                        return;
                    }
                    mCropImagePath = FileUtil.getRealFilePathFromUri(getActivity().getApplicationContext(), uri);
                    mBitMap = BitmapFactory.decodeFile(mCropImagePath);
                    mivPhoto.setImageBitmap(mBitMap);
                    headImage2.setImageBitmap(mBitMap);
                    //此处后面可以将bitMap转为二进制上传后台网络
                    uploadPhoto();
                }
                break;
            default:
                break;
        }
    }


    /**
     * 打开截图界面
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(getActivity(), ClipImageActivity.class);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CROP_PHOTO);
    }

    /**
     * 上传图片 /photo/upload
     */
    private void uploadPhoto() {
        File file = new File(mCropImagePath);
        OkHttpUtils.post()//
                .addFile("mFile", file.getName(), file)//
                .url(Constant.UPLOAD_PHOTO)
                .build()//
                .connTimeOut(50000)
                .readTimeOut(50000)
                .writeTimeOut(50000)
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {


        @Override
        public void onError(okhttp3.Call call, Exception e) {
            LogUtil.e(e.toString());
            CustomToast.INSTANCE.showToast(getActivity(), "网络异常");
        }

        @Override
        public void onResponse(String response) {
            UploadPhotos photos = GsonUtil.parseJsonWithGson(response, UploadPhotos.class);
            if (photos != null && photos.getStatus() == 0) {
                mPhoto = photos.getData();
                mHandler.sendEmptyMessage(990);
            } else {
                CustomToast.INSTANCE.showToast(getActivity(), "上传失败");
            }
        }
    }

    private void showWeather(int code) {
        switch (code) {
            case 0:
                ivMainUserWeather.setImageResource(R.drawable.weather0);
                break;
            case 1:
                ivMainUserWeather.setImageResource(R.drawable.weather1);
                break;
            case 2:
                ivMainUserWeather.setImageResource(R.drawable.weather2);
                break;
            case 3:
                ivMainUserWeather.setImageResource(R.drawable.weather3);
                break;
            case 30:
                ivMainUserWeather.setImageResource(R.drawable.weather30);
                break;
            case 31:
                ivMainUserWeather.setImageResource(R.drawable.weather31);
                break;
            case 32:
                ivMainUserWeather.setImageResource(R.drawable.weather32);
                break;
            case 33:
                ivMainUserWeather.setImageResource(R.drawable.weather33);
                break;
            case 4:
                ivMainUserWeather.setImageResource(R.drawable.weather4);
                break;
            case 5:
                ivMainUserWeather.setImageResource(R.drawable.weather5);
                break;
            case 6:
                ivMainUserWeather.setImageResource(R.drawable.weather6);
                break;
            case 7:
                ivMainUserWeather.setImageResource(R.drawable.weather7);
                break;
            case 8:
                ivMainUserWeather.setImageResource(R.drawable.weather8);
                break;
            case 9:
                ivMainUserWeather.setImageResource(R.drawable.weather9);
                break;
            case 10:
                ivMainUserWeather.setImageResource(R.drawable.weather10);
                break;
            case 11:
                ivMainUserWeather.setImageResource(R.drawable.weather11);
                break;
            case 12:
                ivMainUserWeather.setImageResource(R.drawable.weather12);
                break;
            case 13:
                ivMainUserWeather.setImageResource(R.drawable.weather13);
                break;
            case 14:
                ivMainUserWeather.setImageResource(R.drawable.weather14);
                break;
            case 15:
                ivMainUserWeather.setImageResource(R.drawable.weather15);
                break;
            case 16:
                ivMainUserWeather.setImageResource(R.drawable.weather16);
                break;
            case 17:
                ivMainUserWeather.setImageResource(R.drawable.weather17);
                break;
            case 18:
                ivMainUserWeather.setImageResource(R.drawable.weather18);
                break;
            case 19:
                ivMainUserWeather.setImageResource(R.drawable.weather19);
                break;
            case 20:
                ivMainUserWeather.setImageResource(R.drawable.weather20);
                break;
            case 21:
                ivMainUserWeather.setImageResource(R.drawable.weather21);
                break;
            case 22:
                ivMainUserWeather.setImageResource(R.drawable.weather22);
                break;
            case 23:
                ivMainUserWeather.setImageResource(R.drawable.weather23);
                break;
            case 24:
                ivMainUserWeather.setImageResource(R.drawable.weather24);
                break;
            case 25:
                ivMainUserWeather.setImageResource(R.drawable.weather25);
                break;
            case 26:
                ivMainUserWeather.setImageResource(R.drawable.weather26);
                break;
            case 27:
                ivMainUserWeather.setImageResource(R.drawable.weather27);
                break;
            case 28:
                ivMainUserWeather.setImageResource(R.drawable.weather28);
                break;
            case 29:
                ivMainUserWeather.setImageResource(R.drawable.weather29);
                break;
            case 34:
                ivMainUserWeather.setImageResource(R.drawable.weather34);
                break;
            case 35:
                ivMainUserWeather.setImageResource(R.drawable.weather35);
                break;
            case 36:
                ivMainUserWeather.setImageResource(R.drawable.weather36);
                break;
            case 37:
                ivMainUserWeather.setImageResource(R.drawable.weather37);
                break;
            case 38:
                ivMainUserWeather.setImageResource(R.drawable.weather38);
                break;
            case 99:
                ivMainUserWeather.setImageResource(R.drawable.weather99);
                break;
            default:
                break;

        }
    }


    public class StateInfoPop extends PopupWindow {
        LinearLayout mLlOnline;
        LinearLayout mLlWorking;
        LinearLayout mLlDuty;
        LinearLayout mLlMeeting;
        LinearLayout mLlOutWork;
        LinearLayout mLlHoliday;
        LinearLayout mLlIll;
        private View mMenuView;

        public StateInfoPop(Activity context, View.OnClickListener itemsOnClick) {
            super(context);
            LayoutInflater inflater = context.getLayoutInflater();
            mMenuView = inflater.inflate(R.layout.pop_man_status, null);
            mLlOnline = mMenuView.findViewById(R.id.ll_online);
            mLlWorking = mMenuView.findViewById(R.id.ll_working);
            mLlDuty = mMenuView.findViewById(R.id.ll_duty);
            mLlMeeting = mMenuView.findViewById(R.id.ll_meeting);
            mLlOutWork = mMenuView.findViewById(R.id.ll_out_work);
            mLlHoliday = mMenuView.findViewById(R.id.ll_holiday);
            mLlIll = mMenuView.findViewById(R.id.ll_ill);
            mLlOnline.setOnClickListener(itemsOnClick);
            mLlWorking.setOnClickListener(itemsOnClick);
            mLlDuty.setOnClickListener(itemsOnClick);
            mLlMeeting.setOnClickListener(itemsOnClick);
            mLlOutWork.setOnClickListener(itemsOnClick);
            mLlHoliday.setOnClickListener(itemsOnClick);
            mLlIll.setOnClickListener(itemsOnClick);
            mMenuView.setOnClickListener(itemsOnClick);

            this.setContentView(mMenuView);
            this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            this.setFocusable(true);
            //实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0x00000000);
            //设置SelectPicPopupWindow弹出窗体的背景
            this.setBackgroundDrawable(dw);
            //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
            mMenuView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                @SuppressLint("ClickableViewAccessibility")
                public boolean onTouch(View v, MotionEvent event) {
                    int height = mMenuView.findViewById(R.id.pop_layout2).getTop();
                    int y = (int) event.getY();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (y < height) {
                            dismiss();
                        }
                    }
                    return true;
                }
            });
        }

        @Override
        public void showAsDropDown(View anchor) {
            if (Build.VERSION.SDK_INT >= 24) {
                Rect visibleFrame = new Rect();
                anchor.getGlobalVisibleRect(visibleFrame);
                int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
                setHeight(height);
            }
            super.showAsDropDown(anchor);
        }
    }


}
