package com.yl.technician.module.mine.info;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;


import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityPersonalImgBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.bean.StylistCenterInfoBean;
import com.yl.technician.util.BitmapUtils;
import com.yl.technician.util.FilePathUtil;
import com.yl.technician.util.FormatUtil;
import com.yl.technician.util.PhoneUtil;
import com.yl.technician.widget.bottomview.BottomViewFactory;
import com.yl.technician.widget.bottomview.SelectPhotoView;
import com.yl.technician.widget.bottomview.base.BaseBottomView;

import java.util.List;

/**
 * 封面照
 */
@CreatePresenter(UserInfoPresenter.class)
public class PersonalImgActivity extends BaseMvpAppCompatActivity<IUserInfoView, UserInfoPresenter> implements IUserInfoView, ClickHandler {
    private BaseBottomView mBaseBottomView;
    private ActivityPersonalImgBinding mBinding;
    private ImageLoaderConfig config;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_personal_img;
    }

    @Override
    protected void init() {


        initView();
    }

    private void initView() {
        mBinding = (ActivityPersonalImgBinding) viewDataBinding;
        mBinding.setClick(this);
        // 返回
        mBinding.titleView.setLeftClickListener(v -> finish());
        // 修改状态栏字体颜色
        StatusBarUtil.setLightMode(this);
        config = new ImageLoaderConfig.Builder().
                setAsBitmap(true).
                setPlaceHolderResId(R.drawable.icon_head_pic_def).
                setErrorResId(R.drawable.icon_head_pic_def).
                setCropType(ImageLoaderConfig.FIT_CENTER).
                setDiskCacheStrategy(ImageLoaderConfig.DiskCache.SOURCE).
                setPrioriy(ImageLoaderConfig.LoadPriority.HIGH).build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 获取个人资料
        getMvpPresenter().getStylistCenterInfo();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhoneUtil.REQUESTCODE_SYS_CAMERA: // 相机
                    String imagePath = ((SelectPhotoView) mBaseBottomView).getImagePath();
                    Uri uri = null;
                    if (mBaseBottomView instanceof SelectPhotoView) {
                        uri = ((SelectPhotoView) mBaseBottomView).getUri();
                    }
                    if (uri == null) {
                        return;
                    }
                        PhoneUtil.toCrop(PersonalImgActivity.this, uri, FilePathUtil.getCacheCrop() + "portrait_image.jpg",10,13);
                    break;

                case PhoneUtil.REQUESTCODE_SYS_PICK_IMAGE: // 图库
                        PhoneUtil.toCrop(PersonalImgActivity.this, data.getData(), FilePathUtil.getCacheCrop() + "portrait_image.jpg",10,13);
                    break;

                case PhoneUtil.REQUESTCODE_SYS_CROP: // 裁剪
                    if (TextUtils.isEmpty(FilePathUtil.getCacheCrop() + "portrait_image.jpg")) return;
                        getMvpPresenter().uploadImage(FilePathUtil.getCacheCrop() + "portrait_image.jpg",this);
                    break;
            }
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_photo: // 修改封面照
                if (mBaseBottomView == null) {
                    mBaseBottomView = BottomViewFactory.create(this, BottomViewFactory.Type.Avatar);
                }
                mBaseBottomView.showBottomView(true);
                break;
        }

    }
    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(FormatUtil.Formatstring(message));
    }

    @Override
    public void getStylistInfoSuc(StylistCenterInfoBean stylistInfo) {
        ImageLoader.loadImage(mBinding.ivPhoto, stylistInfo.getPortrait(), config, null);

    }

    @Override
    public void getStylistInfoFail() {

    }

    @Override
    public void updateHobbySuc() {

    }

    @Override
    public void updateIntroductionSuc() {

    }

    @Override
    public void updateStylistNameSuc() {

    }

    @Override
    public void updateBirthdaySuc() {

    }

    @Override
    public void updateHeadImgSuc() {

    }

    @Override
    public void updatePortraitImgSuc() {

    }

    @Override
    public void onUploadFileSuccess(String filePath) {
        ImageLoader.loadImage(mBinding.ivPhoto, filePath, config, null);
        getMvpPresenter().updatePortrait(filePath);
    }

    @Override
    public void onUpdatePosition() {

    }

}
