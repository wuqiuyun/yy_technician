package com.yl.technician.module.home;

import com.yl.core.component.log.DLog;
import com.yl.technician.api.StylistApi;
import com.yl.technician.api.StylistAuthApplyApi;
import com.yl.technician.api.UserLoacationApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.requestbody.SaveLocationBody;
import com.yl.technician.model.vo.result.BannerResult;
import com.yl.technician.model.vo.result.GetStylistAuthApplyResult;
import com.yl.technician.model.vo.result.GetStylistOrderStatisticalResult;

/**
 * Created by zm on 2018/9/19.
 */
public class HomePresenter extends BasePresenter<IHomeView> {

    /**
     * 获取认证信息
     */
    public void getStylistAuthApplyInfo() {
        new StylistAuthApplyApi().getStylistAuthApplyByStylistId(new YLRxSubscriberHelper<GetStylistAuthApplyResult>() {
            @Override
            public void _onNext(GetStylistAuthApplyResult baseResponse) {
                getMvpView().onGetStoreAuthApplyInfoSuccess(baseResponse.getData());
            }
        });
    }

    /**
     * 获取订单统计
     */
    public void getStylistOrderStatistical() {
        new StylistApi().getStylistOrderStatistical(new YLRxSubscriberHelper<GetStylistOrderStatisticalResult>() {
            @Override
            public void _onNext(GetStylistOrderStatisticalResult baseResponse) {
                getMvpView().onGetStoreOrderStatisticalSuccess(baseResponse.getData());
            }
        });
    }

    // 保存用户地位信息
    public void saveLocation(SaveLocationBody saveLocationBody) {
        new UserLoacationApi().save(saveLocationBody, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                DLog.d("用户定位信息保存成功!");
            }
        });
    }

    // 修改城市
    public void changesave(String cityId,String cityName) {
        new UserLoacationApi().changesave(cityId,cityName, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                // TODO 不处理
            }
        });
    }

    /**
     * banner
     */
    public void getBanner() {
        new StylistApi().getBanner( new YLRxSubscriberHelper<BannerResult>() {
            @Override
            public void _onNext(BannerResult baseResponse) {
                if (null!= baseResponse.getData()) {
                    getMvpView().getBannerSuccess( baseResponse.getData());
                }
            }
        });
    }
}
