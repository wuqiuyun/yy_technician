package com.yl.technician.module.home.card;

import android.text.TextUtils;

import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.FileApi;
import com.yl.technician.api.RecomUserApi;
import com.yl.technician.api.StylistCardApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.result.FindReCodeResult;
import com.yl.technician.model.vo.result.StylistCardResult;
import com.yl.technician.model.vo.result.UploadImageResult;

import java.util.ArrayList;
import java.util.List;


/*
 * Create by lvlong on  2018/10/30
 */

public class MyCardPresenter extends BasePresenter<MyCardView> {

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
    
    public void getStylistCard(String stylistId){
        String userId = AccountManager.getInstance().getUserId();
        if (TextUtils.isEmpty(userId)){
            getMvpView().showToast("用户Id为空");
            return;
        }
        if (TextUtils.isEmpty(stylistId)){
            getMvpView().showToast("美发师Id为空");
            return;
        }
        new StylistCardApi().getStylistCard(stylistId, userId,new YLRxSubscriberHelper<StylistCardResult>() {
            @Override
            public void _onNext(StylistCardResult stylistCardResult) {
                if (null != stylistCardResult.getData()) getMvpView().getStylistCardSuc(stylistCardResult.getData());
                else getMvpView().getStylistCardFail();
            }
        });
    }
    
    public void stylistCollection(String stylistId, int type){
        String userId = AccountManager.getInstance().getUserId();
        if (TextUtils.isEmpty(userId)){
            getMvpView().showToast("用户Id为空");
            return;
        }
        if (TextUtils.isEmpty(stylistId)){
            getMvpView().showToast("美发师Id为空");
            return;
        }
        new StylistCardApi().stylistCollection(stylistId, userId, String.valueOf(type), new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().stylistCollectionSuc();
            }

            @Override
            public void _onError(ApiException error) {
                getMvpView().stylistCollectionFail();
            }
        });
    }

    public void saveBackGround(String backGroundImg){
        new StylistCardApi().saveBackGround(backGroundImg, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().onSaveBackGround();

            }
        });
    }

    /**
     * 上传文件
     * @param filePath 文件地址
     */
    public void uploadImage(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            getMvpView().showToast("文件不存在.");
            return;
        }
        List<String> filePaths = new ArrayList<>();
        filePaths.add(filePath);
        new FileApi().uploadFile(filePaths, new YLRxSubscriberHelper<UploadImageResult>() {
            @Override
            public void _onNext(UploadImageResult baseResponse) {
                getMvpView().onUploadFileSuccess(baseResponse.getData().get(0));
            }
        });
    }
}
