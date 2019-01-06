package com.yl.technician.module.certification;

import android.content.Context;

import com.yl.technician.api.FileApi;
import com.yl.technician.api.StylistAuthApplyApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.requestbody.StylistAuthApplyRequestBody;
import com.yl.technician.model.vo.result.UploadImageResult;

import java.util.List;

/**
 * Created by lvlong on 2018/9/20.
 */
public class CertificationPresenter extends BasePresenter<ICertificationView> {

    /**
     * 提交认证信息
     * @param requestBody 认证信息
     */
    public void submitCertiData(Context context, StylistAuthApplyRequestBody requestBody) {
        if (!requestBody.checkParams()) {
            return;
        }
        new StylistAuthApplyApi().save(requestBody, new YLRxSubscriberHelper<BaseResponse>(context, true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                AccountManager.getInstance().setUserStatus(0);
                getMvpView().onSubmitCertiDataSuccess();
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
                getMvpView().showToast("上传成功.");
                getMvpView().onUploadFileSuccess(baseResponse.getData());
            }
        });
    }
}
