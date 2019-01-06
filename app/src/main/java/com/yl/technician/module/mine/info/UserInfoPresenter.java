package com.yl.technician.module.mine.info;

import android.content.Context;
import android.text.TextUtils;

import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.FileApi;
import com.yl.technician.api.StylistCenterInfoApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.result.StylistCenterInfoResult;
import com.yl.technician.model.vo.result.UploadImageResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zm on 2018/9/29.
 */
public class UserInfoPresenter extends BasePresenter<IUserInfoView> {

    //获取美发师个人资料
    public void getStylistCenterInfo() {
        String userId = AccountManager.getInstance().getUserId();
        if(TextUtils.isEmpty(userId)){
            getMvpView().showToast("用户id为空");
            return;
        }
        
        new StylistCenterInfoApi().getStylistCenterInfo(userId, new YLRxSubscriberHelper<StylistCenterInfoResult>() {
            @Override
            public void _onNext(StylistCenterInfoResult result) {
                if (null != result.getData()) getMvpView().getStylistInfoSuc(result.getData());
                else getMvpView().getStylistInfoFail();
            }

            @Override
            public void _onError(ApiException error) {
                getMvpView().getStylistInfoFail();
            }
        });
    }

    //修改爱好
    public void updateHobby(String hobby) {
        String userId = AccountManager.getInstance().getUserId();
        if(TextUtils.isEmpty(userId)){
            getMvpView().showToast("用户id为空");
            return;
        }
        
        new StylistCenterInfoApi().updateHobby(userId, hobby, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().updateHobbySuc();
            }
        });
    }

    //修改个人介绍
    public void updateIntroduction(String introduction) {
        String userId = AccountManager.getInstance().getUserId();
        if(TextUtils.isEmpty(userId)){
            getMvpView().showToast("用户id为空");
            return;
        }
        
        new StylistCenterInfoApi().updateIntroduction(userId, introduction, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().updateIntroductionSuc();
            }
        });
    }

    //更新生日
    public void updateBirthday(String birthday){
        String userId = AccountManager.getInstance().getUserId();
        if(TextUtils.isEmpty(userId)){
            getMvpView().showToast("用户id为空");
            return;
        }
        
        new StylistCenterInfoApi().updateBirthday(userId, birthday, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().updateBirthdaySuc();
            }
        });
    }

    //修改头像
    public void updateHeadImg(String headImg){
        String userId = AccountManager.getInstance().getUserId();
        if(TextUtils.isEmpty(userId)){
            getMvpView().showToast("用户id为空");
            return;
        }
        
        new StylistCenterInfoApi().updateHeadImg(userId, headImg, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                AccountManager.getInstance().setHeadImg(headImg);
                getMvpView().updateHeadImgSuc();
            }
        });
    }


    //修改形象照
    public void updatePortrait(String portraitImg){
        String userId = AccountManager.getInstance().getUserId();
        if(TextUtils.isEmpty(userId)){
            getMvpView().showToast("用户id为空");
            return;
        }

        new StylistCenterInfoApi().updatePortrait(userId, portraitImg, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().updatePortraitImgSuc();
            }
        });
    }

    public void updatePosition(String position){
        new StylistCenterInfoApi().updatePosition(position, new YLRxSubscriberHelper<BaseResponse>() {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().onUpdatePosition();
            }
        });
    }

    /**
     * 上传文件
     * @param filePath 文件地址
     */
    public void uploadImage(String filePath, Context context) {
        if (TextUtils.isEmpty(filePath)) {
            getMvpView().showToast("文件不存在.");
            return;
        }
        List<String> filePaths = new ArrayList<>();
        filePaths.add(filePath);
        new FileApi().uploadFile(filePaths, new YLRxSubscriberHelper<UploadImageResult>(context,true) {
            @Override
            public void _onNext(UploadImageResult baseResponse) {
                getMvpView().onUploadFileSuccess(baseResponse.getData().get(0));
            }
        });
    }
}
