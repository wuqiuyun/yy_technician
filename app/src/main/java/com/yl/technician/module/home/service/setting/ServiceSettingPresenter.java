package com.yl.technician.module.home.service.setting;

import android.content.Context;
import android.text.TextUtils;

import com.yl.core.component.log.DLog;
import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.CategoryApi;
import com.yl.technician.api.FileApi;
import com.yl.technician.api.StylistServerApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.requestbody.SaveServerRequestBody;
import com.yl.technician.model.vo.result.CategoryByIdResult;
import com.yl.technician.model.vo.result.SarverInfoResult;
import com.yl.technician.model.vo.result.UploadImageResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvlong on 2018/10/9.
 */
public class ServiceSettingPresenter extends BasePresenter<ServiceSettingView> {
    //获取服务信息
    public void getServiceInfo(String serviceId) {
        new StylistServerApi().getServiceInfo(serviceId, new YLRxSubscriberHelper<SarverInfoResult>() {
            @Override
            public void _onNext(SarverInfoResult result) {
                getMvpView().onSuccess(result.getData());
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }
    //保存服务信息
    public void save(SaveServerRequestBody saveServerRequestBody, Context context) {
        if (TextUtils.isEmpty(saveServerRequestBody.getPrice())) {
            getMvpView().showToast("请输入服务所需价格");
            return;
        }
        //有子项,价格可为0
        if (saveServerRequestBody.getServiceType()!= Constants.SERVICE_TYPE_2){
            if (Float.valueOf(saveServerRequestBody.getPrice())<=0) {
                getMvpView().showToast("服务价格不可为0");
                return;
            }
        }
        new StylistServerApi().save(saveServerRequestBody, new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse result) {
                getMvpView().saveSuccess();
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }

    //获取单选可选类目
    public void getCategoryById(String categoryId) {
        new CategoryApi().getCategoryById(categoryId, new YLRxSubscriberHelper<CategoryByIdResult>() {
            @Override
            public void _onNext(CategoryByIdResult result) {
                DLog.d(result.toString());
                getMvpView().getServerTypeSuccess(result.getData());
            }
            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }
    /**
     * 上传文件
     * @param filePath 文件地址
     */
    public void uploadImage(Context context, String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            getMvpView().showToast("文件不存在.");
            return;
        }
        List<String> filePaths = new ArrayList<>();
        filePaths.add(filePath);
        new FileApi().uploadFile(filePaths, new YLRxSubscriberHelper<UploadImageResult>(context,true) {
            @Override
            public void _onNext(UploadImageResult baseResponse) {
                getMvpView().showToast("图片上传成功.");
                getMvpView().onUploadFileSuccess(baseResponse.getData().get(0));
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().showToast("图片上传失败,请稍后再试");
            }
        });
    }
}
