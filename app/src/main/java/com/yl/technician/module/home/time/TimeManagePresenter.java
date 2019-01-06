package com.yl.technician.module.home.time;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.RecomUserApi;
import com.yl.technician.api.StylistTimeSettingApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.bean.TimeBean;
import com.yl.technician.model.vo.bean.TimeManageDayBean;
import com.yl.technician.model.vo.bean.TimeManageMonthBean;
import com.yl.technician.model.vo.result.FindReCodeResult;
import com.yl.technician.model.vo.result.TimeManageDayResult;
import com.yl.technician.model.vo.result.TimeManageMonthResult;
import com.yl.technician.model.vo.result.TimeResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangzz on 2018/11/5.
 */
public class TimeManagePresenter extends BasePresenter<TimeManageView> {
    public void requestGetStylistTime(String stylistId, Context activity) {
        if (TextUtils.isEmpty(stylistId)) {
            if (getMvpView() != null) {
                getMvpView().showToast("stylistId不能为空");
            }
            return;
        }

        new StylistTimeSettingApi().getStylistTimeSettingByStylistId(stylistId, new YLRxSubscriberHelper<TimeResult>(activity, true) {
            @Override
            public void _onNext(TimeResult result) {
                if (getMvpView() != null) {
                    getMvpView().getStylistTimeSettingSucs(result);
                }
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                if (getMvpView() != null) {
                    getMvpView().getStylistTimeSettingFailed();
                }
            }
        });
    }

    public void requestStylistServiceSwitch(String stylistId, String status, Context activity) {
        if (TextUtils.isEmpty(stylistId)) {
            if (getMvpView() != null) {
                getMvpView().showToast("stylistId不能为空");
            }
            return;
        }
        if (TextUtils.isEmpty(status)) {
            if (getMvpView() != null) {
                getMvpView().showToast("status不能为空");
            }
            return;
        }

        new StylistTimeSettingApi().setStylistServiceByStylistId(stylistId, status, new YLRxSubscriberHelper<BaseResponse>(activity, true) {
            @Override
            public void _onNext(BaseResponse result) {
                if (getMvpView() != null) {
                    getMvpView().setStylistServiceSwitchSuc();
                }
            }
        });
    }

    public void requestUpdateOrSaves(List<TimeBean> timeBeans, Context activity) {
        if (timeBeans == null && timeBeans.size() < 1) {
            if (getMvpView() != null) {
                getMvpView().showToast("时间列表不能为空");
            }

            return;
        }

        new StylistTimeSettingApi().updateOrSaves(timeBeans, new YLRxSubscriberHelper<BaseResponse<List<TimeBean>>>(activity, true) {
            @Override
            public void _onNext(BaseResponse<List<TimeBean>> result) {
                if (getMvpView() != null) {
                    List<TimeBean> timeBeanList = result.getData();
                    getMvpView().updateOrSavesSuc(timeBeanList);
                }
            }
        });
    }


    public void getStylistStatusByStylistId() {

        new StylistTimeSettingApi().getStylistStatusByStylistId(new YLRxSubscriberHelper<BaseResponse<Integer>>() {
            @Override
            public void _onNext(BaseResponse<Integer> baseResponse) {
                if (getMvpView() != null) {
                    getMvpView().getStylistStatusByStylistId(baseResponse.getData());
                }
            }
        });

    }

    public void getStylistTimeByStylistIdAndDate(Context activity,String date) {

        new StylistTimeSettingApi().getStylistTimeByStylistIdAndDate(date,new YLRxSubscriberHelper<TimeManageDayResult>(activity,true) {
            @Override
            public void _onNext(TimeManageDayResult baseResponse) {
                if (getMvpView() != null) {
                    if (baseResponse.getData()!=null){
                        TimeManageDayBean timeManageDayBean = baseResponse.getData();
                        getMvpView().getStylistTimeByStylistIdAndDateSuc(timeManageDayBean);
                    }else {
                        getMvpView().getStylistTimeByStylistIdAndDateFailed();
                    }

                }
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }

    public void getStylistTimeListByStylistIdAndDates(Context activity,String startdate,String enddate) {

        new StylistTimeSettingApi().getStylistTimeListByStylistIdAndDates(startdate,enddate,new YLRxSubscriberHelper<TimeManageMonthResult>(activity,true) {
            @Override
            public void _onNext(TimeManageMonthResult baseResponse) {
                if (getMvpView() != null) {
                    ArrayList<TimeManageMonthBean> timeManageMonthBean = baseResponse.getData();
                    getMvpView().getStylistTimeListByStylistIdAndDatesSuc(timeManageMonthBean);
                }
            }
        });
    }


    public void updateOrSaveStylistTime( Context activity,String day,String id,
                                        String locktime,String resttime,
                                        String worktime) {

        new StylistTimeSettingApi().updateOrSaveStylistTime(day,id,locktime,resttime,worktime,new YLRxSubscriberHelper<BaseResponse>(activity,true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                if (getMvpView() != null) {
                    getMvpView().updateOrSaveStylistTimeSuc();
                }
            }
        });
    }

}
