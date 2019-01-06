package com.yl.technician.module.home.works.upload;

import android.content.Context;

import com.yl.technician.api.FileApi;
import com.yl.technician.api.StylistOpusTypeApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.model.vo.result.StylistOpusTypeResult;
import com.yl.technician.model.vo.result.UploadImageResult;

import java.util.List;


/*
 * Create by lvlong on  2018/11/2
 */

public class UploadWorksPresenter extends BasePresenter<UploadWorksView> {

    //获取脸型
    public void getFeature(){
        new StylistOpusTypeApi().getFeature(new YLRxSubscriberHelper<StylistOpusTypeResult>() {


            @Override
            public void _onNext(StylistOpusTypeResult stylistOpusTypeResult) {
                getMvpView().onGetFeature(stylistOpusTypeResult.getData());
            }
        });
    }

    //获取发长
    public void getHairstyle(){
        new StylistOpusTypeApi().getHairstyle(new YLRxSubscriberHelper<StylistOpusTypeResult>() {
            @Override
            public void _onNext(StylistOpusTypeResult stylistOpusTypeResult) {
                getMvpView().onGetHairstyle(stylistOpusTypeResult.getData());
            }
        });
    }

    //上传作品
    public void save(String describe , int featureId , int hairstyleId , List<String> opusPaths){
        new StylistOpusTypeApi().save(describe, featureId, hairstyleId, opusPaths, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().onSave();
            }
        });
    }

    /**
     * 上传文件
     * @param filePaths 文件地址
     */
    public void uploadImage(Context context, List<String> filePaths) {
        if (filePaths == null || filePaths.isEmpty()) {
            ToastUtils.shortToast("文件不存在");
            return;
        }

        new FileApi().uploadFile(filePaths, new YLRxSubscriberHelper<UploadImageResult>(context, true) {
            @Override
            public void _onNext(UploadImageResult baseResponse) {
                getMvpView().onUploadFileSuccess(baseResponse.getData());
            }
        });
    }

}
