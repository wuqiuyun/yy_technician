package com.yl.technician.module.login.information;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.view.TimePickerView;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.DateUtil;
import com.yl.core.util.KeyboardUtil;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityPerfectInformationBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.requestbody.DoUserDataRequestBody;
import com.yl.technician.module.certification.CertificationActivity;
import com.yl.technician.util.FilePathUtil;
import com.yl.technician.util.FormatUtil;
import com.yl.technician.util.PhoneUtil;
import com.yl.technician.util.compressutil.CompressPicUtil;
import com.yl.technician.util.compressutil.OnCompressListener;
import com.yl.technician.widget.bottomview.BottomViewFactory;
import com.yl.technician.widget.bottomview.SelectPhotoView;
import com.yl.technician.widget.bottomview.base.BaseBottomView;
import com.yl.technician.widget.picker.OptionPickerView;
import com.yl.technician.widget.picker.PickerViewFactory;
import com.yl.technician.widget.picker.imp.BirthdayTimePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * 完善资料
 * <p>
 * Created by lvlong on 2018/9/27.
 */
@CreatePresenter(PerfectInformationPresenter.class)
public class PerfectInformationActivity extends BaseMvpAppCompatActivity<IPerfectInformationView, PerfectInformationPresenter>
        implements ClickHandler , IPerfectInformationView {

    ActivityPerfectInformationBinding mBinding;

    private BaseBottomView mBaseBottomView;
    private String headUrl = "";

    private TimePickerView pvTime; // 时间选择器
    private OptionPickerView opView; // 条件选择器

    private ImageLoaderConfig config;

    private ArrayList<String> optionsItems = new ArrayList<>();
    {
        optionsItems.add("高级美发师");
        optionsItems.add("资深美发师");
        optionsItems.add("首席美发师");
        optionsItems.add("总监");
        optionsItems.add("督导");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_perfect_information;
    }

    @Override
    protected void init() {

        mBinding = (ActivityPerfectInformationBinding) viewDataBinding;
        mBinding.setClick(this);

        // 修改状态栏字体颜色
        StatusBarUtil.setLightMode(this);
        // 生日默认显示今天
        mBinding.tvBirthday.setText(DateUtil.date2Str(new Date(), DateUtil.FORMAT_YMD_));
        // 个人头衔
        mBinding.tvPosition.setText(optionsItems.get(0));
        // 初始化时间控件
        initTimePicker();
        // 初始化条件选择器
        initOptionPicker();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhoneUtil.REQUESTCODE_SYS_CAMERA: // 相机

                    Uri uri = null;
                    if (mBaseBottomView instanceof SelectPhotoView) {
                        uri = ((SelectPhotoView) mBaseBottomView).getUri();
                    }
                    if (uri == null) {
                        return;
                    }
                    PhoneUtil.toCrop(PerfectInformationActivity.this, uri, FilePathUtil.getCacheCrop() + "image_photo.jpg");
                    break;
                case PhoneUtil.REQUESTCODE_SYS_PICK_IMAGE: // 图库
                    PhoneUtil.toCrop(PerfectInformationActivity.this, data.getData(), FilePathUtil.getCacheCrop() + "image_photo.jpg");
                    break;

                case PhoneUtil.REQUESTCODE_SYS_CROP: // 裁剪
                    if (TextUtils.isEmpty(FilePathUtil.getCacheCrop() + "image_photo.jpg")) return;
                    getMvpPresenter().uploadImage(FilePathUtil.getCacheCrop() + "image_photo.jpg");
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_head_photo:    //上传头像
                KeyboardUtil.closeSoftKeyboard(this);
                if (mBaseBottomView == null) {
                    mBaseBottomView = BottomViewFactory.create(this, BottomViewFactory.Type.Avatar);
                }
                mBaseBottomView.showBottomView(true);
                break;

            case R.id.btn_ok:         //确定

                String confirmPassword = mBinding.etConfirmPassword.getText().toString().trim();
                String loginPassword = mBinding.etLoginPassword.getText().toString().trim();

                if (confirmPassword.length() >= 6 && loginPassword.length() >= 6){
                    if (confirmPassword.equals(loginPassword)){
                        if (!TextUtils.isEmpty(headUrl)&&headUrl!=""){
                            DoUserDataRequestBody requestBody =
                                    new DoUserDataRequestBody.Builder()
                                            .realName(mBinding.etUserName.getText().toString().trim())
                                            .birthday(mBinding.tvBirthday.getText().toString().trim())
                                            .confirmPwd(mBinding.etConfirmPassword.getText().toString().trim())
                                            .password(mBinding.etLoginPassword.getText().toString().trim())
                                            .position(mBinding.tvPosition.getText().toString().trim())
                                            .gender(getSex())
                                            .headPortrait(headUrl)
                                            .userId(AccountManager.getInstance().getUserId())
                                            .selfIntroduction(mBinding.etPersonalIntroduction.getText().toString())
                                            .build();
                            getMvpPresenter().doUserData(requestBody);
                        }else{
                            ToastUtils.shortToast("请上传头像");
                        }
                    }else {
                        ToastUtils.shortToast("密码不一致");
                    }
                }else {
                    ToastUtils.shortToast("密码最少6位");
                }


                break;
                
            case R.id.tv_birthday:  //选择生日
                KeyboardUtil.closeSoftKeyboard(this);
                if (pvTime != null) {
                    pvTime.show();
                }
                break;
            case R.id.tv_position:
                KeyboardUtil.closeSoftKeyboard(this);
                if (opView != null) {
                    opView.show();
                }
                break;
        }
    }

    private void initTimePicker() {
        pvTime = PickerViewFactory.createPickersView(BirthdayTimePicker.class).init(this, (date, v)
                -> mBinding.tvBirthday.setText(DateUtil.date2Str(date, DateUtil.FORMAT_YMD_)));
    }

    private void initOptionPicker() {
        opView = new OptionPickerView(this, (options1, options2, options3, v)
                -> mBinding.tvPosition.setText(optionsItems.get(options1)));
        opView.set(optionsItems);
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(FormatUtil.Formatstring(message));
    }

    @Override
    public void onDoUserDataSuccess() {

        AccountManager.getInstance().setHeadImg(headUrl);
        AccountManager.getInstance().setNickname(mBinding.etUserName.getText().toString().trim());

        // 跳转至用户认证
        CertificationActivity.startActivity(this, CertificationActivity.class);
        finish();
    }

    @Override
    public void onUploadFileSuccess(String filePath) {
        this.headUrl = filePath;
        if (config == null) {
            config = new ImageLoaderConfig.Builder().
                    setCropType(ImageLoaderConfig.CENTER_INSIDE).
                    setAsBitmap(true).
                    setCropCircle(true).
                    setPlaceHolderResId(R.drawable.icon_head_pic_def).
                    setErrorResId(R.drawable.icon_head_pic_def).
                    setDiskCacheStrategy(ImageLoaderConfig.DiskCache.SOURCE).
                    setPrioriy(ImageLoaderConfig.LoadPriority.HIGH).build();
        }
        ImageLoader.loadImage(mBinding.ivHeadPhoto, filePath, config, null);
    }

    /**
     * 获取性别
     * @return 1男 2女 3人妖
     */
    private int getSex() {
        if (mBinding.rbMan.isChecked()) {
            return 1;
        }
        if (mBinding.rbWoman.isChecked()) {
            return 2;
        }
        return 3;
    }
}
