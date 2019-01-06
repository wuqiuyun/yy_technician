package com.yl.technician.widget.bottomview;

import android.Manifest;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yl.technician.R;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ViewSelectPhotoBinding;
import com.yl.technician.util.FilePathUtil;
import com.yl.technician.util.PhoneUtil;
import com.yl.technician.widget.bottomview.base.BaseBottomView;


/**
 * 选择照片的底部弹框
 *
 * Created by lvlong on 2018/10/13.
 */
public class SelectPhotoView extends BaseBottomView implements ClickHandler {
    ViewSelectPhotoBinding mPhotoBinding;

    private Uri mUri;
    private String imagePath; // 图片保存地址

    public SelectPhotoView(Context context) {
        super(context, R.style.BottomViewTheme_Default, R.layout.view_select_photo);
        mPhotoBinding = DataBindingUtil.bind(rootView);
        mPhotoBinding.setClick(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.tv_phone_photos: //去手机相册
                new RxPermissions((FragmentActivity) mContext)
                        .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(granted -> {
                            if (granted) {
                                PhoneUtil.takePicFromGallery(mContext);
                                dismissBottomView();
                            }
                        });
                break;

            case R.id.tv_camera: //去拍照
                new RxPermissions((FragmentActivity) mContext)
                        .request(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(granted -> {
                            if (granted) {
                                imagePath = FilePathUtil.getCacheImagePick();
                                mUri = PhoneUtil.takePickByCamera(mContext, imagePath);
                                dismissBottomView();
                            }else {
                                ToastUtils.shortToast("您拒绝了相机使用权限");
                            }
                        });
                break;

            case R.id.btn_cancel: //取消
                dismissBottomView();
                break;
        }

    }

    public Uri getUri() {
        return mUri;
    }

    public String getImagePath() {
        return imagePath;
    }
}
