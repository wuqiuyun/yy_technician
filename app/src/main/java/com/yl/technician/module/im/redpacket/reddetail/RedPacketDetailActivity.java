package com.yl.technician.module.im.redpacket.reddetail;

import android.content.Intent;
import android.view.View;

import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;
import com.yl.core.component.image.glide.GlideImageLoaderStrategy;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityRedPacketDetailBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.SelfDefinedInfoBean;
import com.yl.technician.module.im.redpacket.redrecords.RedRecordsActivity;

/**
 * Created by zhangzz on 2018/11/6
 */
@CreatePresenter(RedPacketDetailPresenter.class)
public class RedPacketDetailActivity extends BaseMvpAppCompatActivity<RedPacketDetailView, RedPacketDetailPresenter> implements RedPacketDetailView, View.OnClickListener {
    private ActivityRedPacketDetailBinding mBinding;
    protected SelfDefinedInfoBean selfDefinedInfoBean;//红包 转账 页面间传递 自定义的包装对象的key

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_red_packet_detail;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        mBinding = (ActivityRedPacketDetailBinding) viewDataBinding;
        selfDefinedInfoBean = getIntent().getParcelableExtra(Constants.EXTRA_RED_ITEM_BEAN);
        mBinding.titleView.setLeftClickListener(v -> {
            finish();
        });

        if (selfDefinedInfoBean != null) {
            mBinding.tvRedContent.setText(selfDefinedInfoBean.getContent());
            mBinding.tvNick.setText(selfDefinedInfoBean.getNickname());
            mBinding.tvRpId.setText("ID: " + selfDefinedInfoBean.getUserId());

            if (selfDefinedInfoBean.getImusername().equals(AccountManager.getInstance().getAccount().getImusername())) {
                mBinding.layoutRedSending.setVisibility(View.VISIBLE);
                mBinding.layoutRedHad.setVisibility(View.GONE);
                //0 失效 1 发送中 2 已接收
                if (selfDefinedInfoBean.getRedPacketStatus() == 2) {
                    mBinding.tvMoneySelf.setText(String.format(getResources().getString(R.string.rp_have_receive), selfDefinedInfoBean.getMoney()));
                } else if (selfDefinedInfoBean.getRedPacketStatus() == 0) {
                    mBinding.tvMoneySelf.setText(String.format(getResources().getString(R.string.rp_have_outtime), selfDefinedInfoBean.getMoney()));
                } else if (selfDefinedInfoBean.getRedPacketStatus() == 1) {
                    mBinding.tvMoneySelf.setText(String.format(getResources().getString(R.string.rp_havenot_receive), selfDefinedInfoBean.getMoney()));
                }
            } else {
                mBinding.tvRpMoney.setText(selfDefinedInfoBean.getMoney() + "");
                mBinding.layoutRedSending.setVisibility(View.GONE);
                mBinding.layoutRedHad.setVisibility(View.VISIBLE);
                if (selfDefinedInfoBean.getRedPacketStatus() == 2) {
                    mBinding.tvRpDesc.setText(getResources().getString(R.string.rp_desc));
                } else if (selfDefinedInfoBean.getRedPacketStatus() == 0) {
                    mBinding.tvRpDesc.setText(getResources().getString(R.string.rp_desc_outday));
                } else if (selfDefinedInfoBean.getRedPacketStatus() == 1) {
                    mBinding.tvRpDesc.setText(getResources().getString(R.string.rp_desc_sending));
                }
            }
            ImageLoaderConfig config = null;
            if (config == null){
                config = new ImageLoaderConfig.Builder().
                        setAsBitmap(true).
                        setPlaceHolderResId(R.drawable.im_avatar).
                        setErrorResId(R.drawable.im_avatar).
                        setDiskCacheStrategy(ImageLoaderConfig.DiskCache.SOURCE).
                        setPrioriy(ImageLoaderConfig.LoadPriority.HIGH).build();
            }
            ImageLoader.loadImage(mBinding.ivAvatar,selfDefinedInfoBean.getPath(), config, null);//加载成功前
        }

        mBinding.tvWatchHistory.setOnClickListener(v -> {
            Intent intent = new Intent(RedPacketDetailActivity.this, RedRecordsActivity.class);
            intent.putExtra("flag", 1);//1红包记录  2转账记录
            startActivity(intent);
        });
    }

    @Override
    public void requestSuccess() {

    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
