package com.yl.technician.module.home.time;

import android.annotation.SuppressLint;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.DateUtil;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.recycleview.GridSpacingItemDecoration;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityTimeManage2Binding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.bean.TimeBean;
import com.yl.technician.model.vo.bean.TimeManageBean;
import com.yl.technician.model.vo.bean.TimeManageDayBean;
import com.yl.technician.model.vo.bean.TimeManageMonthBean;
import com.yl.technician.model.vo.result.TimeResult;
import com.yl.technician.util.FormatUtil;
import com.yl.technician.widget.dialog.BaseEasyDialog;
import com.yl.technician.widget.dialog.EasyDialog;
import com.yl.technician.widget.dialog.ViewConvertListener;
import com.yl.technician.widget.dialog.ViewHolder;
import com.yl.technician.widget.mytimepickview.CustomTimePicker;
import com.yl.technician.widget.mytimepickview.SelectTime;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 时间管理
 * Created by lyj on 2018/12/20.
 */

@CreatePresenter(TimeManagePresenter.class)
public class TimeManageActivity extends BaseMvpAppCompatActivity<TimeManageView, TimeManagePresenter> implements
        ClickHandler,
        TimeManageView,
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnYearChangeListener,
        CalendarView.OnMonthChangeListener,
        CalendarView.OnViewChangeListener,
        CalendarView.OnCalendarInterceptListener{

    ActivityTimeManage2Binding mBinding;

    private CustomTimePicker mTimePicker;

    private EasyDialog timeSelfDialog;

    private List<TimeBean> timeBeans = new ArrayList<TimeBean>();

    private List<TimeBean> timeNetBeans = new ArrayList<TimeBean>();

    private String businessStartTime = "08:30";//选择的服务开始时间
    private String businessEndTime = "23:30";//选择的服务结束时间

    private TimeManagerAdapter mServiceAdapter;

    //工作时间范围
    List<String > workTimes = new ArrayList<String>();

    private int mYear,mMonth,mDay;
    private int curToday;
    private String chooseDataStr;
    private String locktimesStr;
    private boolean isCurrentDay;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_time_manage2;
    }

    @Override
    protected void init() {
        mBinding = (ActivityTimeManage2Binding) viewDataBinding;
        mBinding.setClick(this);

        initView();
        initData();
    }

    private void initView() {

        mBinding.titleView.setLeftClickListener(view -> finish());

        mBinding.titleView.setRightTextClickListener(v -> {
            jumpRightClick();
        });

        mBinding.calendarView.setOnCalendarSelectListener(this);
        mBinding.calendarView.setOnYearChangeListener(this);
        mYear = mBinding.calendarView.getCurYear();
        //设置日期拦截事件，仅适用单选模式，当前有效
        mBinding.calendarView.setOnCalendarInterceptListener(this);
        mBinding.calendarView.setOnMonthChangeListener(this);
        mBinding.calendarView.setOnViewChangeListener(this);

    }

    private void initData() {
        mYear = mBinding.calendarView.getCurYear();
        mMonth = mBinding.calendarView.getCurMonth();
        mDay = mBinding.calendarView.getCurDay();

        curToday = Integer.parseInt(String.valueOf(mYear)+FormatUtil.FormatInt(mMonth)+FormatUtil.FormatInt(mDay));

        mBinding.tvMonthData.setText(String.valueOf(mYear)+"年"+FormatUtil.FormatInt(mMonth) + "月");
        getMvpPresenter().requestGetStylistTime(AccountManager.getInstance().getStylistId(), this);
        getMvpPresenter().getStylistStatusByStylistId();
    }

    /**
     * 右侧保存按钮
     * */
    private void jumpRightClick() {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.img_business_switch:
                mBinding.imgBusinessSwitch.setSelected(!mBinding.imgBusinessSwitch.isSelected());
                if (!mBinding.imgBusinessSwitch.isSelected()){
                    showCloseDialog();
                }else {
                    getMvpPresenter().requestStylistServiceSwitch(AccountManager.getInstance().getStylistId(), "1", TimeManageActivity.this);
                }
                break;

            case R.id.ll_service_time:         //服务时间
                initTimerPicker();
                mTimePicker.setColors();
                mTimePicker.show();
                break;
            case R.id.ll_month_left:         //上一个月
                mBinding.calendarView.scrollToPre();
                break;

            case R.id.ll_month_right:         //下一个月
                mBinding.calendarView.scrollToNext();
                break;
        }

    }

    private void initTimerPicker() {
        mTimePicker = new CustomTimePicker(this, "服务时间", businessStartTime, businessEndTime, new CustomTimePicker.ResultHandler() {
            @Override
            public void handle(SelectTime mSelectTime) {
                businessStartTime = mSelectTime.getStartTime();
                businessEndTime = mSelectTime.getEndTime();
                if (timeBeans!=null&&timeBeans.size()>0){
                    initChangeData();
                }else {
                    initDefaultData();
                }
                getMvpPresenter().requestUpdateOrSaves(timeNetBeans, TimeManageActivity.this);
            }

            @Override
            public void cancel() {

            }
        });
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
    }



    @Override
    public TimeResult getStylistTimeSettingSucs(TimeResult result) {
        if (result != null && result.getData() != null) {
            timeBeans = result.getData();
            if (timeBeans != null && timeBeans.size() > 0) {
                businessStartTime = timeBeans.get(0).getStarttime();
                businessEndTime = timeBeans.get(0).getEndtime();
                try {
                    //工作时间范围转换
                    workTimes = DateUtil.getDatesBetweenTwoDate(timeBeans.get(0).getStarttime(),timeBeans.get(0).getEndtime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                initChangeData();
            } else {
                initDefaultData();
            }
        } else {
            initDefaultData();
        }
        mBinding.tvServiceTime.setText(businessStartTime);
        mBinding.tvServiceTime1.setText(businessEndTime);
        return null;
    }

    private void initChangeData() {
        timeNetBeans.clear();
        for (int i = 0; i < 7; ++i) {
            TimeBean timeBean = new TimeBean();
            if (timeBeans != null&&timeBeans.size()>0) {
                timeBean.setId(timeBeans.get(i).getId());
            }
            timeBean.setStatus(1);
            timeBean.setWorkday(i + 1);
            timeBean.setStarttime(businessStartTime);
            timeBean.setEndtime(businessEndTime);
            timeBean.setStylistId(Long.parseLong(AccountManager.getInstance().getStylistId()));
            timeNetBeans.add(timeBean);
        }
    }

    private void initDefaultData() {
        timeNetBeans.clear();
        for (int i = 0; i < 7; ++i) {
            TimeBean timeBean = new TimeBean();
            timeBean.setId(1);
            timeBean.setWorkday(i + 1);
            timeBean.setStarttime(businessStartTime);
            timeBean.setEndtime(businessEndTime);
            timeBean.setStatus(1);
            timeBean.setStylistId(Long.parseLong(AccountManager.getInstance().getStylistId()));
            timeNetBeans.add(timeBean);
        }
    }

    private void getStylistTimeListByStylistIdAndDates() {
        getMvpPresenter().getStylistTimeListByStylistIdAndDates(this,
                String.valueOf(mYear)+FormatUtil.FormatInt(mMonth)+"01",
                String.valueOf(mYear)+FormatUtil.FormatInt(mMonth)+DateUtil.getSupportEndDayofMonth(mYear,mMonth));

    }

    @Override
    public void setStylistServiceSwitchSuc() {
    }

    @Override
    public void updateOrSavesSuc(List<TimeBean> timeBeanList) {
        showToast("服务时间设置成功");
        timeBeans = timeBeanList;
        if (timeBeans != null && timeBeans.size() > 0) {
            businessStartTime = timeBeans.get(0).getStarttime();
            businessEndTime = timeBeans.get(0).getEndtime();
            try {
                //工作时间范围转换
                workTimes = DateUtil.getDatesBetweenTwoDate(timeBeans.get(0).getStarttime(),timeBeans.get(0).getEndtime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            initChangeData();
        } else {
            initDefaultData();
        }
        mBinding.tvServiceTime.setText(businessStartTime);
        mBinding.tvServiceTime1.setText(businessEndTime);
    }

    @Override
    public void getStylistTimeSettingFailed() {
        initDefaultData();
    }

    @Override
    public void getStylistStatusByStylistId(int type) {
        if (type == 0){
            mBinding.imgBusinessSwitch.setSelected(false);
        }else {
            mBinding.imgBusinessSwitch.setSelected(true);
        }
    }

    @Override
    public void getStylistTimeByStylistIdAndDateSuc(TimeManageDayBean timeManageDayBean) {
        //选中日期的详情
        if (timeManageDayBean!=null){
            showDefinedDialog(timeManageDayBean);
        }else {
            ToastUtils.shortToast("选中当天没有数据");
        }
    }

    @Override
    public void getStylistTimeByStylistIdAndDateFailed() {
        //详情为null,没有任何数据的情况
        showDefinedDialog(null);
    }

    @Override
    public void getStylistTimeListByStylistIdAndDatesSuc(ArrayList<TimeManageMonthBean> timeManageMonthBean) {
        //选中月份日期详情列表 返回不是所有天数,只有数据库有记录的才返回
        //state 0 无任何操作 1 约满 2 有约 3 请假 4 休息
        Map<String, Calendar> map = new HashMap<>();
        if (timeManageMonthBean!=null&&timeManageMonthBean.size()>0){
            for (int i = 0;i<timeManageMonthBean.size();i++){
                //获取网络时间并且转换为日
                int day = DateUtil.str2Calendar(timeManageMonthBean.get(i).getDay()+"",DateUtil.FORMAT_YMD_MATH).get(java.util.Calendar.DAY_OF_MONTH);
                if (timeManageMonthBean.get(i).getState()==1){//1约满
                    map.put(getSchemeCalendar(mYear, mMonth,day, 0xFFf4f4f4, "约满").toString(),
                            getSchemeCalendar(mYear, mMonth,day, 0xFFf4f4f4, "约满"));
                }else if (timeManageMonthBean.get(i).getState()==2){//2有约
                    map.put(getSchemeCalendar(mYear, mMonth,day, 0xFFffa200, "有约").toString(),
                            getSchemeCalendar(mYear, mMonth,day, 0xFFffa200, "有约"));
                }else if (timeManageMonthBean.get(i).getState()==3){//3请假
                    map.put(getSchemeCalendar(mYear, mMonth,day, 0xFFffa200, "请假").toString(),
                            getSchemeCalendar(mYear, mMonth,day, 0xFFffa200, "请假"));
                }else if (timeManageMonthBean.get(i).getState()==4){//休息
                    map.put(getSchemeCalendar(mYear, mMonth,day, 0xFFffa200, "休息").toString(),
                            getSchemeCalendar(mYear, mMonth,day, 0xFFffa200, "休息"));
                }
            }
        }
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        mBinding.calendarView.setSchemeDate(map);
    }

    @Override
    public void updateOrSaveStylistTimeSuc() {
        showToast("休息时间设置成功");
        //更新当前月份
        getStylistTimeListByStylistIdAndDates();
    }

    //请假具体时间弹窗
    private void showDefinedDialog(TimeManageDayBean timeManageDayBean) {
        List<String> locktimes = null;
        List<String> resttimes = null;
        String hmStr = DateUtil.getTime(System.currentTimeMillis(), DateUtil.FORMAT_HM);//当前时间
        if (timeManageDayBean!=null){
            //锁定时间(已约时间)
            locktimes = new Gson().fromJson(timeManageDayBean.getLocktime(), ArrayList.class);
            //已请假时间
            resttimes = new Gson().fromJson(timeManageDayBean.getResttime(), ArrayList.class);
            chooseDataStr = timeManageDayBean.getDay()+"";
            locktimesStr = timeManageDayBean.getLocktime();
        }else {
            locktimesStr = "[]";
        }


        ArrayList<TimeManageBean> timeBeans = new ArrayList<>();

        if (workTimes != null && !workTimes.isEmpty()) {
            for (String workTime : workTimes) {
                TimeManageBean timeBean = new TimeManageBean();
                timeBean.setTime(workTime);
                if (locktimes != null && !locktimes.isEmpty()) {
                    for (String lockTime : locktimes) {
                        if (lockTime.equals(workTime)) {
                            locktimes.remove(workTime);
                            timeBean.setLock(true);
                            break;
                        }
                    }
                }
                if (resttimes != null && !resttimes.isEmpty()) {
                    for (String restTime : resttimes) {
                        if (restTime.equals(workTime)) {
                            timeBean.setChecked(true);
                            break;
                        }
                    }
                }
                timeBean.setEnable(true);
                if (isCurrentDay){//今天日期
                    int compareType = DateUtil.compareDate(hmStr,workTime);//1在前  0相等  -1在后
                    if (compareType>=0){//超过了的时间
                        timeBean.setEnable(false);
                        if (!timeBean.isChecked()){//是否已经选中 没有则置灰
                            timeBean.setLock(true);
                        }
                    }else {
                        timeBean.setEnable(true);
                    }

                }
                timeBeans.add(timeBean);
            }
            TimeManageBean timeBean = new TimeManageBean();
            timeBean.setTime("全天");
            if (!locktimesStr.equals("[]")){//有锁定时间
                timeBean.setLock(true);
                timeBean.setEnable(false);
            }else {
                if (isCurrentDay){//今天日期
                    timeBean.setLock(true);
                    timeBean.setEnable(false);
                }else {
                    timeBean.setEnable(true);
                }
            }
            timeBeans.add(timeBean);
        }

        timeSelfDialog = (EasyDialog) EasyDialog.init()
                .setLayoutId(R.layout.dialog_timemanage_leave)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseEasyDialog dialog) {

                        RecyclerView serviceRecycle = holder.getView(R.id.rv_time_manage_leave);
                        mServiceAdapter = new TimeManagerAdapter(TimeManageActivity.this);
                        mServiceAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                String isDate =  mServiceAdapter.getDatas().get(position).getTime();//item时间
                                if (isDate.equals("全天")){
                                    List<TimeManageBean> timeManageListBean =mServiceAdapter.getDatas();
                                    boolean isCkeck = timeManageListBean.get(position).isChecked();
                                    for (int i = 0;i<timeManageListBean.size();i++){
                                        if (!isCkeck&&!timeManageListBean.get(i).isLock()){
                                            mServiceAdapter.getDatas().get(i).setChecked(true);
                                        }else {
                                            mServiceAdapter.getDatas().get(i).setChecked(false);
                                        }
                                    }
                                }else {
                                    mServiceAdapter.getDatas().get(position).setChecked(!mServiceAdapter.getDatas().get(position).isChecked());
                                }
                                mServiceAdapter.notifyDataSetChanged();
                            }
                        });
                        serviceRecycle.setLayoutManager(new GridLayoutManager(TimeManageActivity.this, 4));
                        serviceRecycle.setAdapter(mServiceAdapter);
                        serviceRecycle.addItemDecoration(new GridSpacingItemDecoration(4, 50, false));
                        mServiceAdapter.setDatas(timeBeans, true);

                        TextView tvCancel = holder.getView(R.id.tv_manage_leave_cancel);//取消
                        tvCancel.setOnClickListener(v -> {
                            timeSelfDialog.dismiss();

                        });

                        TextView tvSure = holder.getView(R.id.tv_manage_leave_sure);//确定
                        tvSure.setOnClickListener(v -> {
                            //返回选中的时间
                            List<String> selectTime = new ArrayList<>();
                            for (int i =0;i<mServiceAdapter.getDatas().size();i++) {
                                if (mServiceAdapter.getDatas().get(i).isChecked()) {
                                    if (!mServiceAdapter.getDatas().get(i).getTime().equals("全天")&&
                                            !mServiceAdapter.getDatas().get(i).isLock()){
                                        selectTime.add(mServiceAdapter.getDatas().get(i).getTime());
                                    }
                                }
                            }
                            String restTimes = new Gson().toJson(selectTime);
                            String workTimesStr = new Gson().toJson(workTimes);
                            String upId = "0";
                            if (timeManageDayBean!=null){
                                upId = timeManageDayBean.getId()+"";
                            }

                            //保存后台需要的自定义时间传递格式的string或者list
                            getMvpPresenter().updateOrSaveStylistTime(
                                    TimeManageActivity.this,
                                    chooseDataStr,
                                    upId,
                                    locktimesStr,
                                    restTimes,
                                    workTimesStr);
                            timeSelfDialog.dismiss();

                        });

                    }
                })
                .setPosition(Gravity.BOTTOM)
                .show(TimeManageActivity.this.getSupportFragmentManager());
    }

    //关闭营业对话框
    private void showCloseDialog() {
        EasyDialog.init()
                .setLayoutId(R.layout.dialog_group_remove_member)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseEasyDialog dialog) {
                        holder.setText(R.id.tv_remove_title,"提示");
                        holder.setText(R.id.tv_remove_cancel,"取消");
                        holder.setText(R.id.tv_remove_content,"关闭服务开关后您将不再接受新的订单有未完成核销订单请及时处理");
                        holder.setText(R.id.tv_remove_cancel,"取消");
                        holder.getView(R.id.tv_remove_ok).setOnClickListener(v -> {
                            mBinding.imgBusinessSwitch.setSelected(false);
                            getMvpPresenter().requestStylistServiceSwitch(AccountManager.getInstance().getStylistId(), "0", TimeManageActivity.this);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.tv_remove_cancel).setOnClickListener(v -> {
                            mBinding.imgBusinessSwitch.setSelected(true);
                            dialog.dismiss();
                        });
                    }
                })
                .setPosition(Gravity.CENTER)
                .setMargin(45)
                .setOutCancel(false)
                .show(getSupportFragmentManager());
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }

    @Override
    public void onYearChange(int year) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onMonthChange(int year, int month) {
        //Log.e("onMonthChange", "  -- " + year + "  --  " + month);
        Calendar calendar = mBinding.calendarView.getSelectedCalendar();
        mYear = year;
        mMonth = month;
        mBinding.tvMonthData.setText(String.valueOf(mYear)+"年"+FormatUtil.FormatInt(mMonth) + "月");
        getStylistTimeListByStylistIdAndDates();
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    /**
     * 日期选择事件
     *
     * @param calendar calendar
     * @param isClick  isClick
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mYear = calendar.getYear();
        mMonth = calendar.getMonth();
        mDay = calendar.getDay();
        isCurrentDay = calendar.isCurrentDay();
        mBinding.tvMonthData.setText(String.valueOf(mYear)+"年"+String.valueOf(calendar.getMonth()) + "月");
        if (isClick) {
            String scheme = calendar.getScheme();
            chooseDataStr = String.valueOf(mYear)+FormatUtil.FormatInt(mMonth) +FormatUtil.FormatInt(mDay);
            int chooseData = Integer.parseInt(chooseDataStr);
            if (chooseData>=curToday){//今天之前的日期
                //请求当天的具体时段数据
                getMvpPresenter().getStylistTimeByStylistIdAndDate(this,String.valueOf(chooseData));
            }
//            Toast.makeText(this, String.valueOf(mYear)+"年"+FormatUtil.FormatInt(mMonth) + "月"+FormatUtil.FormatInt(mDay) + "日", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onViewChange(boolean isMonthView) {

    }

    /**
     * 屏蔽某些不可点击的日期，可根据自己的业务自行修改
     *
     * @param calendar calendar
     * @return 是否屏蔽某些不可点击的日期，MonthView和WeekView有类似的API可调用
     */
    @Override
    public boolean onCalendarIntercept(Calendar calendar) {
        //这里写拦截条件，返回true代表拦截，尽量以最高效的代码执行
        //Log.e("onCalendarIntercept", calendar.toString());
        int day = calendar.getDay();
        int calendDay = mBinding.calendarView.getCurDay();//获取今天的日期
        return day == 20 || day == 26;
    }

    @Override
    public void onCalendarInterceptClick(Calendar calendar, boolean isClick) {
        Toast.makeText(this,
                calendar.toString() + (isClick ? "拦截不可点击" : "拦截滚动到无效日期"),
                Toast.LENGTH_SHORT).show();
    }
}
