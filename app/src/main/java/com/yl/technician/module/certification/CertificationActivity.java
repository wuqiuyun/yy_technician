package com.yl.technician.module.certification;


import android.content.Intent;
import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.view.View;

import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityCertificationBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.helper.AppManager;
import com.yl.technician.model.vo.requestbody.StylistAuthApplyRequestBody;
import com.yl.technician.module.main.MainActivity;
import com.yl.technician.util.BitmapUtils;
import com.yl.technician.util.FilePathUtil;
import com.yl.technician.util.FormatUtil;
import com.yl.technician.util.PhoneUtil;
import com.yl.technician.util.compressutil.CompressPicUtil;
import com.yl.technician.util.compressutil.OnCompressListener;
import com.yl.technician.widget.bottomview.BottomViewFactory;
import com.yl.technician.widget.bottomview.SelectPhotoView;
import com.yl.technician.widget.bottomview.base.BaseBottomView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户认证
 * <p>
 * Create by zm on 2018/11/2
 */
@CreatePresenter(CertificationPresenter.class)
public class CertificationActivity extends BaseMvpAppCompatActivity<ICertificationView,CertificationPresenter>implements ICertificationView,ClickHandler{

    private ActivityCertificationBinding mBinding;

    private BaseBottomView mBaseBottomView;

    private String cardFrontPath; // 身份证正面
    private String cardReversePath; // 身份证反面
    private String hardCardPath; // 手执证件照
    private String otherPath1; // 资质证明1
    private String otherPath2; // 资质证明2
    private List<String> qualificationPaths = new ArrayList<>(); // 资质证明

    private static final int CARD_FRONT = 0; // 身份证正面
    private static final int CARD_REVERSE = 1; // 身份证反面
    private static final int CARD_HARD = 2; // 手执证件照
    private static final int OTHER_ONE = 3; // 资质证明1
    private static final int OTHER_TWO = 4; // 资质证明2

    @IntDef({CARD_FRONT, CARD_REVERSE, CARD_HARD, OTHER_ONE, OTHER_TWO})
    @interface ImageType{}
    @ImageType int mImageType;

    @Override
    protected void init() {
        mBinding = (ActivityCertificationBinding) viewDataBinding;
        mBinding.setClick(this);
        // 修改状态栏字体颜色
        StatusBarUtil.setLightMode(this);
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_certification;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhoneUtil.REQUESTCODE_SYS_CAMERA: // 相机
                    if (mBaseBottomView == null) return;
                    String imagePath = ((SelectPhotoView) mBaseBottomView).getImagePath();
                    switch (mImageType) {
                        case CARD_FRONT:
                            mBinding.ivCertificateFront.setImageBitmap(
                                    BitmapUtils.decodeSampledBitmapFromFile(imagePath,
                                            mBinding.ivCertificateFront.getWidth(), mBinding.ivCertificateFront.getHeight()));
                            break;
                        case CARD_REVERSE:
                            mBinding.ivCertificateReverse.setImageBitmap(
                                    BitmapUtils.decodeSampledBitmapFromFile(imagePath,
                                            mBinding.ivCertificateReverse.getWidth(), mBinding.ivCertificateReverse.getHeight()));
                            break;
                        case CARD_HARD:
                            mBinding.ivHandheldCertificate.setImageBitmap(
                                    BitmapUtils.decodeSampledBitmapFromFile(imagePath,
                                            mBinding.ivHandheldCertificate.getWidth(), mBinding.ivHandheldCertificate.getHeight()));
                            break;
                        case OTHER_ONE:
                            mBinding.ivCredentials1.setImageBitmap(
                                    BitmapUtils.decodeSampledBitmapFromFile(imagePath,
                                            mBinding.ivCredentials1.getWidth(), mBinding.ivCredentials1.getHeight()));
                            break;
                        case OTHER_TWO:
                            mBinding.ivCredentials2.setImageBitmap(
                                    BitmapUtils.decodeSampledBitmapFromFile(imagePath,
                                            mBinding.ivCredentials2.getWidth(), mBinding.ivCredentials2.getHeight()));
                            break;
                    }
                    compressPicAndUpload(imagePath);
                    break;

                case PhoneUtil.REQUESTCODE_SYS_PICK_IMAGE: // 图库
                    if (data == null || data.getData() == null) return;
                    switch (mImageType) {
                        case CARD_FRONT:
                            mBinding.ivCertificateFront.setImageBitmap(
                                    BitmapUtils.decodeSampledBitmapFromFile(FilePathUtil.getPath(data.getData()),
                                            mBinding.ivCertificateFront.getWidth(), mBinding.ivCertificateFront.getHeight()));
                            break;
                        case CARD_REVERSE:
                            mBinding.ivCertificateReverse.setImageBitmap(
                                    BitmapUtils.decodeSampledBitmapFromFile(FilePathUtil.getPath(data.getData()),
                                            mBinding.ivCertificateReverse.getWidth(), mBinding.ivCertificateReverse.getHeight()));
                            break;
                        case CARD_HARD:
                            mBinding.ivHandheldCertificate.setImageBitmap(
                                    BitmapUtils.decodeSampledBitmapFromFile(FilePathUtil.getPath(data.getData()),
                                            mBinding.ivHandheldCertificate.getWidth(), mBinding.ivHandheldCertificate.getHeight()));
                            break;
                        case OTHER_ONE:
                            mBinding.ivCredentials1.setImageBitmap(
                                    BitmapUtils.decodeSampledBitmapFromFile(FilePathUtil.getPath(data.getData()),
                                            mBinding.ivCredentials1.getWidth(), mBinding.ivCredentials1.getHeight()));
                            break;
                        case OTHER_TWO:
                            mBinding.ivCredentials2.setImageBitmap(
                                    BitmapUtils.decodeSampledBitmapFromFile(FilePathUtil.getPath(data.getData()),
                                            mBinding.ivCredentials2.getWidth(), mBinding.ivCredentials2.getHeight()));
                            break;
                    }
                    compressPicAndUpload(FilePathUtil.getPath(data.getData()));
                    break;
            }
        }
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(FormatUtil.Formatstring(message));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_handheld_certificate: //上传手持证件照
                mImageType = CARD_HARD;
                if (mBaseBottomView == null){
                    mBaseBottomView = BottomViewFactory.create(this, BottomViewFactory.Type.Avatar);
                }
                mBaseBottomView.showBottomView(true);
                break;

            case R.id.iv_certificate_front: //上传证件正面
                mImageType = CARD_FRONT;
                if (mBaseBottomView == null){
                    mBaseBottomView = BottomViewFactory.create(this, BottomViewFactory.Type.Avatar);
                }
                mBaseBottomView.showBottomView(true);
                break;

            case R.id.iv_certificate_reverse: //上传证件反面
                mImageType = CARD_REVERSE;
                if (mBaseBottomView == null){
                    mBaseBottomView = BottomViewFactory.create(this, BottomViewFactory.Type.Avatar);
                }
                mBaseBottomView.showBottomView(true);
                break;

            case R.id.iv_credentials_1: //上传资质证明一
                mImageType = OTHER_ONE;
                if (mBaseBottomView == null){
                    mBaseBottomView = BottomViewFactory.create(this, BottomViewFactory.Type.Avatar);
                }
                mBaseBottomView.showBottomView(true);
                break;

            case R.id.iv_credentials_2: //上传资质证明二
                mImageType = OTHER_TWO;
                if (mBaseBottomView == null){
                    mBaseBottomView = BottomViewFactory.create(this, BottomViewFactory.Type.Avatar);
                }
                mBaseBottomView.showBottomView(true);
                break;

            case R.id.btn_next: //下一步
                if (!TextUtils.isEmpty(otherPath1)) {
                    qualificationPaths.add(otherPath1);
                }
                if (!TextUtils.isEmpty(otherPath2)) {
                    qualificationPaths.add(otherPath2);
                }
                StylistAuthApplyRequestBody requestBody = new StylistAuthApplyRequestBody.Builder()
                        .realname(mBinding.etUserName.getText().toString().trim())
                        .cardno(mBinding.etCardno.getText().toString().trim())
                        .cardFront(cardFrontPath)
                        .cardReverse(cardReversePath)
                        .hardCard(hardCardPath)
                        .stylistId(AccountManager.getInstance().getStylistId())
                        .qualification(qualificationPaths)
                        .build();
                getMvpPresenter().submitCertiData(this, requestBody);
                break;
        }
    }

    @Override
    public void onSubmitCertiDataSuccess() {
        // 跳转至首页
        MainActivity.startActivity(CertificationActivity.this, MainActivity.HOME);
        finish();
    }

    @Override
    public void onUploadFileSuccess(List<String> filePaths) {
        String filePath = filePaths.get(0);
        switch (mImageType) {
            case CARD_FRONT:
                cardFrontPath = filePath;
                break;
            case CARD_REVERSE:
                cardReversePath = filePath;
                break;
            case CARD_HARD:
                hardCardPath = filePath;
                break;
            case OTHER_ONE:
                otherPath1 = filePath;
                break;
            case OTHER_TWO:
                otherPath2 = filePath;
                break;
        }
    }

    /**
     * 压缩后上传
     *
     * @param filePath
     */
    private void compressPicAndUpload(String filePath) {
        CompressPicUtil.with()
                .load(filePath)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        // 压缩成功后调用，返回压缩后的图片文件
                        List<String> filePaths = new ArrayList<>();
                        filePaths.add(file.getPath());
                        getMvpPresenter().uploadImage(CertificationActivity.this, filePaths);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 当压缩过程出现问题时调用 上传原图
                        List<String> filePaths = new ArrayList<>();
                        filePaths.add(filePath);
                        getMvpPresenter().uploadImage(CertificationActivity.this, filePaths);
                    }
                }).launch();
    }
}
