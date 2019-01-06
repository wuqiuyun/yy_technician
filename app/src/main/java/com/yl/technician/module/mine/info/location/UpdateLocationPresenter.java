package com.yl.technician.module.mine.info.location;


import com.yl.technician.api.StylistCenterInfoApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.requestbody.UpdateAddressRequestBody;


/*
 *  @创建者:   27407
 *  @创建时间:  2018/10/14 14:53
 *  @描述：    TODO
 */

public class UpdateLocationPresenter extends BasePresenter<IUpdateLocationView> {
    //修改地址
    public void updateLocation(UpdateAddressRequestBody requestBody) {
        if (!requestBody.checkStoreParams()) {
            return;
        }
        new StylistCenterInfoApi().updateLocation(requestBody, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().updateSuc();
            }
        });
    }

}
