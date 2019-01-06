package com.yl.technician.module.home.works.upload;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.recycleview.GridSpacingItemDecoration;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityUploadWorksBinding;
import com.yl.technician.model.vo.bean.UploadWorksBean;
import com.yl.technician.module.home.qrcode.ScanCodeActivity;
import com.yl.technician.module.main.MainActivity;
import com.yl.technician.util.FilePathUtil;
import com.yl.technician.util.FormatUtil;
import com.yl.technician.util.PhoneUtil;
import com.yl.technician.util.compressutil.CompressPicUtil;
import com.yl.technician.util.compressutil.OnCompressListener;
import com.yl.technician.widget.PhotoView.PhotoViewActivity;
import com.yl.technician.widget.bottomview.BottomViewFactory;
import com.yl.technician.widget.bottomview.SelectPhotoView;
import com.yl.technician.widget.bottomview.base.BaseBottomView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/*上传作品
 * Create by lvlong on  2018/11/2
 */
@CreatePresenter(UploadWorksPresenter.class)
public class UploadWorksActivity extends BaseMvpAppCompatActivity<UploadWorksView, UploadWorksPresenter> implements UploadWorksView , ClickHandler{

    private ActivityUploadWorksBinding mBinding;

    private UploadWorksAdapter mHairLengthAdapter;

    private UploadWorksAdapter mFeatureAdapter;

    private int mNumber = 8 ; //限制数量->多少张
    private RecyclerView mRcvWorksShow;
    private AddImageAdapter mWorksShowAdapter;
    private String mName; // 图类型名

    private static final String EXTRAS_DATA = "extras_data";
    private static final String EXTRAS_NAME = "extras_name";
    private static final String EXTRAS_NUMBER = "extras_number";
    private List<String> filePaths=new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_upload_works;
    }

    @Override
    protected void init() {

        mBinding = (ActivityUploadWorksBinding) viewDataBinding;
        mBinding.setClick(this);

        initView();

        getMvpPresenter().getFeature();
        getMvpPresenter().getHairstyle();

    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        mBinding.titleView.setLeftClickListener(v -> finish());

        //作品展示
        mRcvWorksShow = mBinding.rcvWorksShow;
        RecyclerView rcvHairLength = mBinding.rcvHairLength;//发长
        RecyclerView rcvFeature = mBinding.rcvFeature;      //脸型

        setRcvWorksShow(mRcvWorksShow);

        setRcvHairLength(rcvHairLength);

        setRcvFeature(rcvFeature);

        initInput();
    }

    //处理输入限制
    private void initInput(){
        TextWatcher mTextWatcher = new TextWatcher() {
            private CharSequence temp;
            private int editStart ;
            private int editEnd ;
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
                editStart = mBinding.etWorksDescribe.getSelectionStart();
                editEnd = mBinding.etWorksDescribe.getSelectionEnd();
                mBinding.tvNumber.setText(String.format(FormatUtil.Formatstring(temp.length() + "")));

            }
        };

        mBinding.etWorksDescribe.addTextChangedListener(mTextWatcher);
    }


    private void setRcvWorksShow(RecyclerView rcvWorksShow) {

        mRcvWorksShow.setLayoutManager(new GridLayoutManager(this, 4));
        mWorksShowAdapter = new AddImageAdapter(this);
        mWorksShowAdapter.setOnDeleteItemListener(new AddImageAdapter.OnDeleteItemListener() {
            @Override
            public void onDeleteItem(View view, int position) {
                mWorksShowAdapter.getDatas().remove(position);
                mWorksShowAdapter.notifyDataSetChanged();
                mBinding.ivAdd.setVisibility(View.VISIBLE);
            }

            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(PhotoViewActivity.IMAGE_URL, mWorksShowAdapter.getDatas());
                bundle.putInt(PhotoViewActivity.SHOW_POSITION, position);
                PhotoViewActivity.startActivity(UploadWorksActivity.this, PhotoViewActivity.class, bundle);
            }

            @Override
            public void OnItemLongClickListener(View view, int position) {

            }
        });
        mRcvWorksShow.setAdapter(mWorksShowAdapter);
    }


    private void setRcvHairLength(RecyclerView rcvHairLength) {

        mHairLengthAdapter = new UploadWorksAdapter(this);
        mHairLengthAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                for (UploadWorksBean bean : mHairLengthAdapter.getDatas()) {
                    bean.setChecked(false);
                }
                UploadWorksBean worksBean = mHairLengthAdapter.getDatas().get(position);
                mHairLengthAdapter.getDatas().get(position).setChecked(!worksBean.isChecked());
                mHairLengthAdapter.notifyDataSetChanged();
            }
        });
        rcvHairLength.setLayoutManager(new GridLayoutManager(this, 3));
        rcvHairLength.setAdapter(mHairLengthAdapter);
        rcvHairLength.addItemDecoration(new GridSpacingItemDecoration(3, 50, false));

    }

    private void setRcvFeature(RecyclerView rcvFeature) {

        mFeatureAdapter = new UploadWorksAdapter(this);
        mFeatureAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                for (UploadWorksBean bean : mFeatureAdapter.getDatas()) {
                    bean.setChecked(false);
                }
                UploadWorksBean worksBean = mFeatureAdapter.getDatas().get(position);
                mFeatureAdapter.getDatas().get(position).setChecked(!worksBean.isChecked());
                mFeatureAdapter.notifyDataSetChanged();

            }
        });
        rcvFeature.setLayoutManager(new GridLayoutManager(this, 3));
        rcvFeature.addItemDecoration(new GridSpacingItemDecoration(3, 50, false));
        rcvFeature.setAdapter(mFeatureAdapter);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.iv_add :          //添加
                new RxPermissions(this)
                        .request(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe( grant -> {
                            if (grant) {
                                openPhotoAlbum();
                            }
                        });

                break;

            case R.id.btn_save :        //保存
                if (mWorksShowAdapter.getDatas().size() <= 0) {
                    ToastUtils.shortToast("至少上传一张图片");
                    return;
                }
                String describe = mBinding.etWorksDescribe.getText().toString().trim();
                int featureId = mFeatureAdapter.getSelectWorks();
                int hairstyleId = mHairLengthAdapter.getSelectWorks();
                getMvpPresenter().save(describe , featureId , hairstyleId , mWorksShowAdapter.getDatas());
                break;
        }

    }

    /**
     * 打开相册
     */
    private void openPhotoAlbum() {
        Album.image(this) // Image selection.
                .multipleChoice()
                .camera(true)
                .columnCount(3)
                .selectCount(mNumber-mWorksShowAdapter.getDatas().size())
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        ArrayList<String> pathLists = new ArrayList<>();
                        for (AlbumFile albumFile:result) {
                            // 拿到用户选择的图片路径List：
                            pathLists.add(albumFile.getPath());
                        }
                        compressPicAndUpload(pathLists);
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                    }
                })
                .start();
    }

    //获取脸型的回调
    @Override
    public void onGetFeature(List<UploadWorksBean> list) {

        mFeatureAdapter.setDatas((ArrayList<UploadWorksBean>) list, true);
    }

    //获取发长的回调
    @Override
    public void onGetHairstyle(List<UploadWorksBean> list) {

        mHairLengthAdapter.setDatas((ArrayList<UploadWorksBean>) list, true);

    }

    @Override
    public void onSave() {
        finish();
    }

    @Override
    public void onUploadFileSuccess(List<String> filePaths) {
        mWorksShowAdapter.getDatas().addAll(filePaths);
        mWorksShowAdapter.notifyDataSetChanged();
        if (mWorksShowAdapter.getDatas().size()==mNumber){
            mBinding.ivAdd.setVisibility(View.GONE);
        }else {
            mBinding.ivAdd.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(FormatUtil.Formatstring(message));
    }

    /**
     * 压缩后上传
     *
     * @param pathList
     */
    private void compressPicAndUpload( List<String> pathList) {
        filePaths.clear();
        for (String filePath:pathList){
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
                            filePaths.add(file.getPath());
                            //压缩完添加入上传数组,size等于选择的就开始上传
                            if (filePaths.size()==pathList.size()){
                                getMvpPresenter().uploadImage(UploadWorksActivity.this,filePaths);
                            }
                        }
                        @Override
                        public void onError(Throwable e) {
                            // 当压缩过程出现问题时调用 上传原图
                            filePaths.add(filePath);
                            //压缩完添加入上传数组,size等于选择的就开始上传
                            if (filePaths.size()==pathList.size()){
                                getMvpPresenter().uploadImage(UploadWorksActivity.this,filePaths);
                            }
                        }
                    }).launch();
        }
    }
}
