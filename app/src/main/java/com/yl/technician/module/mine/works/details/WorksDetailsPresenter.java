package com.yl.technician.module.mine.works.details;

import android.text.TextUtils;

import com.yl.core.component.log.DLog;
import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.RecomUserApi;
import com.yl.technician.api.StylistCardApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.result.FindReCodeResult;
import com.yl.technician.model.vo.result.OpusDetailResult;

/**
 * Created by zm on 2018/10/12.
 */
public class WorksDetailsPresenter extends BasePresenter<IWorksDetailsView> {

    /**
     * 获取我的推荐码
     */
    public void findReCode(){
        new RecomUserApi().findReCode(new YLRxSubscriberHelper<FindReCodeResult>() {
            @Override
            public void _onNext(FindReCodeResult findReCodeResult) {
                if (null != findReCodeResult.getData()) getMvpView().findReCodeSuc(findReCodeResult.getData());
                else getMvpView().showToast("获取我的推荐码失败");
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }
    
    public void getOpusDetail(String opusId){
        String userId = AccountManager.getInstance().getUserId();
        if (TextUtils.isEmpty(userId)){
            getMvpView().showToast("用户ID为空");
            return;
        }
        if (TextUtils.isEmpty(opusId)){
            getMvpView().showToast("作品ID为空");
            return;
        }
        
        new StylistCardApi().getOpusDetail(opusId, userId, new YLRxSubscriberHelper<OpusDetailResult>() {
            @Override
            public void _onNext(OpusDetailResult opusDetailResult) {
                if (null != opusDetailResult) getMvpView().getOpusDetailSuc(opusDetailResult.getData());
                else getMvpView().getOpusDetailFail();
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().getOpusDetailFail();
            }
        });
    }

    public void oupsCollection(String opusId, int type){
        String userId = AccountManager.getInstance().getUserId();
        if (TextUtils.isEmpty(userId)){
            getMvpView().showToast("用户ID为空");
            return;
        }
        if (TextUtils.isEmpty(opusId)){
            getMvpView().showToast("作品ID为空");
            return;
        }
        new StylistCardApi().oupsCollection(opusId, userId, String.valueOf(type), new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().collectSuccess();
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().collectFail();
            }
        });
    }
    
    //浏览美发师作品
    public void opusPageview(String opusId){
        if (TextUtils.isEmpty(opusId)){
            getMvpView().showToast("作品ID为空");
            return;
        }
        
        new StylistCardApi().opusCount(opusId, "2", new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                DLog.d("StylistCardApi","增加浏览数成功");
            }
        });
    }

    //转发分享美发师作品
    public void opusRepost(String opusId){
        if (TextUtils.isEmpty(opusId)){
            getMvpView().showToast("作品ID为空");
            return;
        }

        new StylistCardApi().opusCount(opusId, "1", new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                DLog.d("StylistCardApi","增加转发数成功");
            }
        });
    }
    
    //删除单张图片
    public void deleteOpusPicture(String pictureId){
        if(TextUtils.isEmpty(pictureId)){
            getMvpView().showToast("id为空");
            return;
        }
        
        new StylistCardApi().deleteOpusPicture(pictureId, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().deleteSingleSuc();
            }
        });
    }

    //删除整个作品
    public void deleteOpus(String opusId){
        if(TextUtils.isEmpty(opusId)){
            getMvpView().showToast("作品id为空");
            return;
        }

        new StylistCardApi().deleteOpus(opusId, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().deleteAllSuc();
            }
        });
    }
}
