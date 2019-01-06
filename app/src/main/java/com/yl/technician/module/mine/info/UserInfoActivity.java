package com.yl.technician.module.mine.info;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
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
import com.yl.technician.databinding.ActivityUserInfoBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.bean.EventBean;
import com.yl.technician.model.vo.bean.StylistCenterInfoBean;
import com.yl.technician.module.mine.info.location.UpdateLocationActivity;
import com.yl.technician.module.mine.info.updatename.UpdateNameActivity;
import com.yl.technician.util.BitmapUtils;
import com.yl.technician.util.FilePathUtil;
import com.yl.technician.util.FormatUtil;
import com.yl.technician.util.PhoneUtil;
import com.yl.technician.util.compressutil.CompressPicUtil;
import com.yl.technician.util.compressutil.OnCompressListener;
import com.yl.technician.widget.bottomview.BottomViewFactory;
import com.yl.technician.widget.bottomview.SelectPhotoView;
import com.yl.technician.widget.bottomview.base.BaseBottomView;
import com.yl.technician.widget.dialog.BaseEasyDialog;
import com.yl.technician.widget.dialog.EasyDialog;
import com.yl.technician.widget.dialog.ViewConvertListener;
import com.yl.technician.widget.dialog.ViewHolder;
import com.yl.technician.widget.mytimepickview.CustomDatePicker;
import com.yl.technician.widget.picker.OptionPickerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * 个人资料
 */
@CreatePresenter(UserInfoPresenter.class)
public class UserInfoActivity extends BaseMvpAppCompatActivity<IUserInfoView, UserInfoPresenter> implements IUserInfoView, ClickHandler {

    ActivityUserInfoBinding mBinding;
    private BaseBottomView mSelectPhotoView;


    private CustomDatePicker mDatePicker;
    private String birthDate;

    private OptionPickerView mOptionPickerView;

    private ArrayList<String> personalTitles = new ArrayList<>();

    private boolean isShow;

    private ImageLoaderConfig config;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void init() {

        mBinding = (ActivityUserInfoBinding) viewDataBinding;
        mBinding.setClick(this);

        initData();
        initView();

        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            isShow = true;
        }
        StatusBarUtil.setLightMode(this);

        EventBus.getDefault().register(this);
    }

    private void initView() {
        // 返回
        mBinding.titleView.setLeftClickListener(v -> finish());
        mBinding.rbMan.setClickable(false);
        mBinding.rbWoman.setClickable(false);
        // 头像
        config = new ImageLoaderConfig.Builder().
                setCropType(ImageLoaderConfig.CENTER_INSIDE).
                setAsBitmap(true).
                setCropCircle(true).
                setPlaceHolderResId(R.drawable.icon_head_pic_def).
                setErrorResId(R.drawable.icon_head_pic_def).
                setDiskCacheStrategy(ImageLoaderConfig.DiskCache.SOURCE).
                setPrioriy(ImageLoaderConfig.LoadPriority.HIGH).build();
        ImageLoader.loadImage(mBinding.civHeadPhoto, AccountManager.getInstance().getAccount().getHeadImg(), config, null);

        mBinding.tvNickname.setText(FormatUtil.Formatstring(AccountManager.getInstance().getAccount().getNickname()));

        initOptionPickerView();
        getMvpPresenter().getStylistCenterInfo();
    }

    private void initData() {
        personalTitles.add("高级美发师");
        personalTitles.add("资深美发师");
        personalTitles.add("首席美发师");
        personalTitles.add("总监");
        personalTitles.add("督导");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.rl_personal_title:
                mOptionPickerView.show();
                break;

            case R.id.rl_head_photo: //修改头像
                KeyboardUtil.closeSoftKeyboard(this);
                if (mSelectPhotoView == null) {
                    mSelectPhotoView = BottomViewFactory.create(this, BottomViewFactory.Type.Avatar);
                }
                mSelectPhotoView.showBottomView(true);
                break;

            case R.id.rl_nickname:  //修改昵称
                Bundle nameBundle = new Bundle();
                nameBundle.putString("name", mBinding.tvNickname.getText().toString());
                UpdateNameActivity.startActivity(UserInfoActivity.this, UpdateNameActivity.class, nameBundle);
                break;

            case R.id.rl_birthday: //修改生日
                initDatePicker();
                birthDate = mBinding.tvBirthday.getText().toString();
                if (TextUtils.isEmpty(birthDate)) {
                    birthDate = DateUtil.date2Str(new Date(), DateUtil.FORMAT_YMD);
                } else {
                    birthDate = birthDate.replaceAll("/", "-");
                }
                mDatePicker.show(birthDate);
                break;

            case R.id.rl_hobby: //修改爱好
                showHobbyDialog();
                break;

            case R.id.rl_location: //修改地址
                startActivity(this, UpdateLocationActivity.class);
                break;

            case R.id.rl_personal_introduction: //个人介绍
                showIntroDialog();
                break;
            case R.id.rl_personal_image: //形象照
                startActivity(this,PersonalImgActivity.class);
                break;
        }

    }

    private void showHobbyDialog() {
        EasyDialog.init()
                .setLayoutId(R.layout.dialog_info_edit)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseEasyDialog dialog) {
                        holder.setText(R.id.dialog_title, "填写爱好");
                        holder.setText(R.id.tv_left, "爱好:");
                        holder.setText(R.id.tv_total, "50");
                        EditText et = (holder.getView(R.id.et_content));
                        et.setHint("最多可填50字");
                        TextWatcher mTextWatcher = new TextWatcher() {
                            private CharSequence temp;
                            private int editStart;
                            private int editEnd;

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                temp = s;
                            }

                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count,
                                                          int after) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                editStart = et.getSelectionStart();
                                editEnd = et.getSelectionEnd();
                                holder.setText(R.id.tv_num, temp.length() + "");
                                if (temp.length() > 50) {
                                    showToast("最多可填50字");
                                    s.delete(editStart - 1, editEnd);
                                    int tempSelection = editStart;
                                    et.setText(s);
                                    et.setSelection(tempSelection);
                                }
                            }
                        };
                        et.addTextChangedListener(mTextWatcher);

                        holder.getView(R.id.tv_ok).setOnClickListener(v -> {
                            String hobby = et.getText().toString().trim();
                            if (TextUtils.isEmpty(hobby)) {
                                showToast("请输入爱好");
                                return;
                            }
                            getMvpPresenter().updateHobby(hobby);
                            mBinding.tvHobby.setText(hobby);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.tv_cancel).setOnClickListener(v -> {
                            dialog.dismiss();
                        });
                    }
                })
                .setPosition(Gravity.CENTER)
                .setMargin(45)
                .setOutCancel(false)
                .show(getSupportFragmentManager());
    }

    private void initOptionPickerView() {
        mOptionPickerView = new OptionPickerView(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //showToast(personalTitles.get(options1));
                getMvpPresenter().updatePosition(personalTitles.get(options1));
                mBinding.tvProfessor.setText(personalTitles.get(options1));
            }
        });

        mOptionPickerView.set(personalTitles);
    }

    private void showIntroDialog() {
        EasyDialog.init()
                .setLayoutId(R.layout.dialog_info_edit)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseEasyDialog dialog) {
                        holder.setText(R.id.dialog_title, "填写个人介绍");
                        holder.setText(R.id.tv_left, "个人介绍：");
                        holder.setText(R.id.tv_total, "200");
                        EditText et = (holder.getView(R.id.et_content));
                        et.setHint("最多可填200字");
                        TextWatcher mTextWatcher = new TextWatcher() {
                            private CharSequence temp;
                            private int editStart;
                            private int editEnd;

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                temp = s;
                            }

                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count,
                                                          int after) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                editStart = et.getSelectionStart();
                                editEnd = et.getSelectionEnd();
                                holder.setText(R.id.tv_num, temp.length() + "");
                                if (temp.length() > 200) {
                                    showToast("最多可填200字");
                                    s.delete(editStart - 1, editEnd);
                                    int tempSelection = editStart;
                                    et.setText(s);
                                    et.setSelection(tempSelection);
                                }
                            }
                        };
                        et.addTextChangedListener(mTextWatcher);

                        holder.getView(R.id.tv_ok).setOnClickListener(v -> {
                            String intro = et.getText().toString().trim();
                            if (TextUtils.isEmpty(intro)) {
                                intro = "这家伙很懒，什么都没有写";
                            }
                            getMvpPresenter().updateIntroduction(intro);
                            mBinding.tvIntro.setText(intro);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.tv_cancel).setOnClickListener(v -> {
                            dialog.dismiss();
                        });
                    }
                })
                .setPosition(Gravity.CENTER)
                .setMargin(45)
                .setOutCancel(false)
                .show(getSupportFragmentManager());
    }

    private void initDatePicker() {
        String mNowDate = DateUtil.date2Str(new Date(), DateUtil.FORMAT_YMD);
        mDatePicker = new CustomDatePicker(this, "1930-01-01", mNowDate, time -> {
            mBinding.tvBirthday.setText(time.replaceAll("-", "\\/"));
            getMvpPresenter().updateBirthday(mBinding.tvBirthday.getText().toString());
        });
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void getStylistInfoSuc(StylistCenterInfoBean stylistInfo) {

        mBinding.tvNickname.setText(stylistInfo.getNickame());

        if (stylistInfo.getSex() == 1) {
            mBinding.rbMan.setChecked(true);
            mBinding.rbMan.setVisibility(View.VISIBLE);
            mBinding.rbWoman.setVisibility(View.GONE);
        } else {
            mBinding.rbWoman.setChecked(true);
            mBinding.rbMan.setVisibility(View.GONE);
            mBinding.rbWoman.setVisibility(View.VISIBLE);
        }

        mBinding.tvProfessor.setText(FormatUtil.Formatstring(stylistInfo.getPosition()));

        mBinding.tvBirthday.setText(stylistInfo.getBirthday());

        mBinding.tvHobby.setText(FormatUtil.Formatstring(stylistInfo.getHobby()));


        String introStr = TextUtils.isEmpty(stylistInfo.getSelfIntroduction()) ? "暂无介绍" : stylistInfo.getSelfIntroduction();
        mBinding.tvIntro.setText(introStr);

        mBinding.tvLocation.setText(FormatUtil.Formatstring(stylistInfo.getShowLocation()));

        if (isShow) {
            showIntroDialog();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhoneUtil.REQUESTCODE_SYS_CAMERA: // 相机
                    Uri uri = null;
                    if (mSelectPhotoView instanceof SelectPhotoView) {
                        uri = ((SelectPhotoView) mSelectPhotoView).getUri();
                    }
                    if (uri == null)return;
                        PhoneUtil.toCrop(UserInfoActivity.this, uri, FilePathUtil.getCacheCrop() + "image_photo.jpg");
                    break;

                case PhoneUtil.REQUESTCODE_SYS_PICK_IMAGE: // 图库
                        PhoneUtil.toCrop(UserInfoActivity.this, data.getData(), FilePathUtil.getCacheCrop() + "image_photo.jpg");
                    break;

                case PhoneUtil.REQUESTCODE_SYS_CROP: // 裁剪
                    if (TextUtils.isEmpty(FilePathUtil.getCacheCrop() + "image_photo.jpg")) return;
                    mBinding.civHeadPhoto.setImageBitmap(BitmapUtils.decodeSampledBitmapFromFile(
                            FilePathUtil.getCacheCrop() + "image_photo.jpg", mBinding.civHeadPhoto.getWidth(), mBinding.civHeadPhoto.getHeight()));
                        getMvpPresenter().uploadImage(FilePathUtil.getCacheCrop() + "image_photo.jpg",this);
                    break;
            }
        }
    }

    @Override
    public void getStylistInfoFail() {
        showToast("获取个人资料失败！");
//        finish();
    }

    @Override
    public void updateHobbySuc() {
        showToast("爱好更新成功");
    }

    @Override
    public void updateIntroductionSuc() {
        showToast("个人介绍已更新");
    }

    @Override
    public void updateStylistNameSuc() {

    }

    @Override
    public void updateBirthdaySuc() {
        showToast("生日更新成功");
    }

    @Override
    public void updateHeadImgSuc() {
        showToast("头像更新成功");
    }

    @Override
    public void updatePortraitImgSuc() {
        showToast("形象照更新成功");
    }

    @Override
    public void onUploadFileSuccess(String filePath) {
            // 头像
            getMvpPresenter().updateHeadImg(filePath);
            ImageLoader.loadImage(mBinding.civHeadPhoto, filePath, config, null);
    }

    @Override
    public void onUpdatePosition() {
        showToast("头衔更新成功");
    }

    //在其他页面修改资料成功后返回 更新本页面相应信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBean.UpdateUserSuc event) {
        getMvpPresenter().getStylistCenterInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        getMvpPresenter().getStylistCenterInfo();
    }
}
