package com.yl.technician.module.mine;

import android.support.v4.app.Fragment;
import android.view.View;

import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.technician.R;
import com.yl.technician.base.fragment.BaseMvpFragment;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.databinding.FragmentMeBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.module.mine.collect.CollectActivity;
import com.yl.technician.module.mine.footprint.FootprintActivity;
import com.yl.technician.module.mine.info.UserInfoActivity;
import com.yl.technician.module.mine.pplarz.PopularizeActivity;
import com.yl.technician.module.mine.settings.SettingsActivity;
import com.yl.technician.module.mine.wallet.WalletActivity;

/**
 * 我的
 * <p>
 * Created by zm on 2018/9/19.
 */
@CreatePresenter(MinePresenter.class)
public class MineFragment extends BaseMvpFragment<IMineView, MinePresenter> implements IMineView, ClickHandler{

    private FragmentMeBinding mBinding;

    private ImageLoaderConfig config;

    public static Fragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {
        mBinding = (FragmentMeBinding) viewDataBinding;
        mBinding.setClick(this);
        mBinding.titleView.setRightTextClickListener(v -> SettingsActivity.startActivity(getContext(), SettingsActivity.class));

        mBinding.tvUserName.setText(AccountManager.getInstance().getNickname());
        mBinding.tvUserId.setText("ID:"+ AccountManager.getInstance().getUserIdNo());
        ImageLoader.loadImage(mBinding.ivPhoto, AccountManager.getInstance().getAccount().getHeadImg(), config, null);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_wallet: // 我的钱包
                WalletActivity.startActivity(getContext(), WalletActivity.class);
                break;
            case R.id.mine_collect: // 我的收藏
                CollectActivity.startActivity(getContext(), CollectActivity.class);
                break;
            case R.id.mine_footprint: // 我的足迹
                FootprintActivity.startActivity(getContext(), FootprintActivity.class);
                break;
            case R.id.mine_recommend: // 推荐用户
                PopularizeActivity.startActivity(getContext(), PopularizeActivity.class);
                break;
            case R.id.btn_user_info: // 个人资料
                UserInfoActivity.startActivity(getContext(), UserInfoActivity.class);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
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
        mBinding.tvUserName.setText(AccountManager.getInstance().getNickname());
        ImageLoader.loadImage(mBinding.ivPhoto, AccountManager.getInstance().getAccount().getHeadImg(), config, null);
    }
}
