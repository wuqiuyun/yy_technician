package com.yl.technician.module.login.information;

import android.text.TextUtils;

import com.yl.technician.api.FileApi;
import com.yl.technician.api.StylistUserApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.requestbody.DoUserDataRequestBody;
import com.yl.technician.model.vo.result.UploadImageResult;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lvlong on 2018/9/27.
 */
public class PerfectInformationPresenter extends BasePresenter<IPerfectInformationView> {

    /**
     * 完善用户信息
     * @param requestBody
     */
    public void doUserData(DoUserDataRequestBody requestBody) {
        // 检测参数是否异常
        if (!requestBody.checkParams()) {
            return;
        }

        new StylistUserApi().doUserData(requestBody, new YLRxSubscriberHelper<BaseResponse>(){

            @Override
            public void _onNext(BaseResponse baseResponse) {
                AccountManager.getInstance().setStylistId((String) baseResponse.getData());
                getMvpView().onDoUserDataSuccess();
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
                getMvpView().showToast("图片上传成功.");
                getMvpView().onUploadFileSuccess(baseResponse.getData().get(0));
            }
        });
    }
}
