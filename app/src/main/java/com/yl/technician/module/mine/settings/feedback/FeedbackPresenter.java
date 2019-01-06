package com.yl.technician.module.mine.settings.feedback;

import android.content.Context;
import android.text.TextUtils;

import com.yl.technician.api.SettingsApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.result.InitAppFeedbackResult;

/**
 * Created by lvlong on 2018/10/8.
 */
public class FeedbackPresenter extends BasePresenter<IFeedbackView> {
   //初始化反馈页的信息
    public void initAppFeedback(Context context){
        new SettingsApi().initAppFeedback(new YLRxSubscriberHelper<InitAppFeedbackResult>(context,true) {
            @Override
            public void _onNext(InitAppFeedbackResult initAppFeedbackResult) {
                if (null != initAppFeedbackResult.getData())
                getMvpView().initAppFeedbackSuc(initAppFeedbackResult.getData());
            }
        });
    }
    
    //提交反馈
    public void submitFeedback(String content){
        if (TextUtils.isEmpty(content)){
            getMvpView().showToast("内容不能为空");
            return;
        }
        if (TextUtils.isEmpty(AccountManager.getInstance().getUserId())) {
            getMvpView().showToast("用户Id为空");
            return;
        }
        new SettingsApi().submitFeedback(content, AccountManager.getInstance().getUserId(),
                new YLRxSubscriberHelper<BaseResponse>() {
                    @Override
                    public void _onNext(BaseResponse baseResponse) {
                        getMvpView().submitSuc();
                    }
                });
    }
}
