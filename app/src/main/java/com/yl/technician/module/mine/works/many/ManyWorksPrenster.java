package com.yl.technician.module.mine.works.many;

import android.text.TextUtils;

import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.StylistCardApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.result.OpusListResult;

/**
 * Created by zm on 2018/10/13.
 */
public class ManyWorksPrenster extends BasePresenter<IManyWorksView> {

    public void getOpusList(String stylistId) {
        String userId = AccountManager.getInstance().getUserId();
        if (TextUtils.isEmpty(userId)) {
            getMvpView().showToast("用户Id为空");
            return;
        }
        if (TextUtils.isEmpty(stylistId)) {
            getMvpView().showToast("美发师Id为空");
            return;
        }
        new StylistCardApi().getOpusList(stylistId, userId, new YLRxSubscriberHelper<OpusListResult>() {
            @Override
            public void _onNext(OpusListResult opusListResult) {
                if (null != opusListResult.getData())
                    getMvpView().getOpusListSuc(opusListResult.getData());
                else getMvpView().getOpusListFail();
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().getOpusListFail();
            }
        });
    }

    public void getOpusListScreen(String stylistId, long screenId, int type) {
        if (TextUtils.isEmpty(stylistId)) {
            getMvpView().showToast("美发师Id为空");
            return;
        }

        new StylistCardApi().getOpusListScreen(stylistId, String.valueOf(screenId), String.valueOf(type), new YLRxSubscriberHelper<OpusListResult>() {
            @Override
            public void _onNext(OpusListResult opusListResult) {
                if (null != opusListResult.getData()) getMvpView().getOpusListScreenSuc(opusListResult.getData());
            }

            @Override
            public void _onError(ApiException error) {
            }
        });
    }
}
