package com.yl.technician.module.im.msgnotice;

import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.BulletinApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.BulletinListResult;

/**
 * Created by zm on 2018/9/19.
 */
public class MsgNoticePresenter extends BasePresenter<MsgNoticeView> {
    //获取公告列表
    public void getMsgList(int page, int size){
        new BulletinApi().findPageList(String.valueOf(page), String.valueOf(size), new YLRxSubscriberHelper<BulletinListResult>() {
            @Override
            public void _onNext(BulletinListResult bulletinListResult) {
                getMvpView().getMsgListSuc(bulletinListResult.getData());
            }

            @Override
            public void _onError(ApiException error) {
                getMvpView().getMsgListFail();
            }
        });
    }
}