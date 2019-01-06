package com.yl.technician.module.im.redpacket.redrecords;

import android.app.Activity;
import android.text.TextUtils;

import com.yl.technician.api.RedBagApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.RedRecordResult;
import com.yl.core.component.net.exception.ApiException;

/**
 * Created by zhangzz on 2018/11/6.
 */
public class RedRecordsPresenter extends BasePresenter<RedRecordsView> {
    //status 状态 1 发出 2 接收
    //uiType 1红包记录  2转账记录
    public void findRedBagLogReq(int uiType, String userId, String status, String pageNo, String pageSize, Activity activity, boolean isRefresh) {
        if (TextUtils.isEmpty(userId)) {
            getMvpView().showToast("userId不能为空");
            return;
        }

        if (uiType == 1) {
            new RedBagApi().findRedBagLog(userId, status, pageNo, pageSize, new YLRxSubscriberHelper<RedRecordResult>(activity, true) {
                @Override
                public void _onNext(RedRecordResult baseResponse) {
                    getMvpView().requestSuccess(baseResponse, isRefresh);
                }

                @Override
                public void _onError(ApiException error) {
                    super._onError(error);
                    getMvpView().requestFail();
                }
            });
        } else {
            new RedBagApi().findTransferLog(userId, status, pageNo, pageSize, new YLRxSubscriberHelper<RedRecordResult>(activity, true) {
                @Override
                public void _onNext(RedRecordResult baseResponse) {
                    getMvpView().requestSuccess(baseResponse, isRefresh);
                }

                @Override
                public void _onError(ApiException error) {
                    super._onError(error);
                    getMvpView().requestFail();
                }
            });
        }
    }
}
