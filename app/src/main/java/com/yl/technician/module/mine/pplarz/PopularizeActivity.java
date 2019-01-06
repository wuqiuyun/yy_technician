package com.yl.technician.module.mine.pplarz;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityPopularizeBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.local.preferences.CommonSharedPreferences;
import com.yl.technician.model.vo.bean.FindInviteBean;
import com.yl.technician.model.vo.bean.ReCodeBean;
import com.yl.technician.model.vo.bean.RoleIncomeBean;
import com.yl.technician.model.vo.result.RecommendResult;
import com.yl.technician.module.common.WebActivity;
import com.yl.technician.module.mine.pplarz.banding.BandingRefereesActivity;
import com.yl.technician.module.mine.pplarz.details.PopularizeDetailsActivity;
import com.yl.technician.module.mine.pplarz.qrcode.InviteQRCodeActivity;
import com.yl.technician.util.FormatUtil;
import com.yl.technician.util.ShareUtils;
import com.yl.technician.util.StringUtil;
import com.yl.technician.widget.dialog.BaseEasyDialog;
import com.yl.technician.widget.dialog.EasyDialog;
import com.yl.technician.widget.dialog.ViewConvertListener;
import com.yl.technician.widget.dialog.ViewHolder;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

@CreatePresenter(PopularizePresenter.class)
public class PopularizeActivity extends BaseMvpAppCompatActivity<IPopularizeView, PopularizePresenter>
    implements IPopularizeView, ClickHandler {

    ActivityPopularizeBinding mBinding;

    private String shareImg,shareTitle,shareType;
    private String inviteCode;//邀请码

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_popularize;
    }

    @Override
    protected void init() {
        initView();
        initEvent();
        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        getMvpPresenter().findInvite();
    }

    private void initView() {
        // 状态栏
        StatusBarUtil.setLightMode(this);
        mBinding = (ActivityPopularizeBinding) viewDataBinding;
        mBinding.setClick(this);
        // 返回
        mBinding.titleView.setLeftClickListener(v -> finish());
        // 说明
        mBinding.titleView.setRightTextClickListener(v -> {
            String url = Constants.WEB_RECOMMEND_EXOLAIN;
            WebActivity.startActivity(PopularizeActivity.this, url, "说明");
        });
        // 推荐码
        inviteCode = CommonSharedPreferences.getInstance().getInvitationCode();
    }

    private void initEvent() {
        mBinding.storeIncome.getRoot().setOnClickListener(v -> {
            PopularizeDetailsActivity.actionStart(this, PopularizeDetailsActivity.STORE);
        });
        mBinding.stylistIncome.getRoot().setOnClickListener(v -> {
            PopularizeDetailsActivity.actionStart(this, PopularizeDetailsActivity.STYLIST);
        });
        mBinding.userIncome.getRoot().setOnClickListener(v -> {
            PopularizeDetailsActivity.actionStart(this, PopularizeDetailsActivity.USER);
        });
    }

    private void loadData() {
        getMvpPresenter().recommend(this);
        getMvpPresenter().findReCode();
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(FormatUtil.Formatstring(message));
    }

    @Override
    public void setIncomeData(RecommendResult.Data data) {
        mBinding.tvIncomeAmount.setText(String.valueOf(data.getIncometotal()));
        for (RoleIncomeBean bean : data.getIncomeList()) {
            switch (bean.getRoletype()) {
                case 1:
                    mBinding.storeIncome.tvTitle.setText("门店(" + bean.getRecommendnumber() +"家)");
                    mBinding.storeIncome.tvAmount.setText(String.valueOf(bean.getIncomeall()));
                    break;
                case 2:
                    mBinding.stylistIncome.tvTitle.setText("美发师(" + bean.getRecommendnumber() +"个)");
                    mBinding.stylistIncome.tvAmount.setText(String.valueOf(bean.getIncomeall()));
                    break;
                case 3:
                    mBinding.userIncome.tvTitle.setText("用户(" + bean.getRecommendnumber() +"个)");
                    mBinding.userIncome.tvAmount.setText(String.valueOf(bean.getIncomeall()));
                    break;
            }
        }
    }

    @Override
    public void findInviteSuc(FindInviteBean findInvite) {
        if (findInvite == null) { // 未绑定
            mBinding.llNoBinding.getRoot().setVisibility(View.VISIBLE);
            mBinding.llHasBinding.setVisibility(View.GONE);
            mBinding.llNoBinding.tvTitle.setText("推荐人");
            mBinding.llNoBinding.tvRight.setText("您暂无推荐人，可进行绑定");

            mBinding.llNoBinding.getRoot().setOnClickListener(v -> {
                PopularizeActivity.startActivity(PopularizeActivity.this, BandingRefereesActivity.class);
            });
        }else { // 已绑定
            mBinding.llHasBinding.setVisibility(View.VISIBLE);
            mBinding.llNoBinding.getRoot().setVisibility(View.GONE);
            // 头像
            ImageLoaderConfig config = new ImageLoaderConfig.Builder().
                    setCropType(ImageLoaderConfig.CENTER_INSIDE).
                    setAsBitmap(true).
                    setCropCircle(true).
                    setPlaceHolderResId(R.drawable.icon_head_pic_def).
                    setErrorResId(R.drawable.icon_head_pic_def).
                    setDiskCacheStrategy(ImageLoaderConfig.DiskCache.SOURCE).
                    setPrioriy(ImageLoaderConfig.LoadPriority.NORMAL).build();
            ImageLoader.loadImage(mBinding.ivPhoto, findInvite.getHeadImg(), config, null);
            // 用户名
            mBinding.tvUserName.setText(FormatUtil.Formatstring(findInvite.getNickName()));
            // 绑定时间
            mBinding.tvTime.setText(findInvite.getCreateTime());
        }
    }

    @Override
    public void findReCodeSuc(ReCodeBean reCode) {
        CommonSharedPreferences.getInstance().saveInvitationCode(reCode.getInvitecode());
        inviteCode = reCode.getInvitecode();
        shareImg = reCode.getShareImg();
        shareTitle = reCode.getShareTitle();
        shareType = reCode.getShareType()+"";
    }

    //分享弹框
    private void showShareDialog() {
        EasyDialog.init()
                .setLayoutId(R.layout.dialog_share)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseEasyDialog dialog) {
                        holder.getView(R.id.tv_share_cancel).setOnClickListener(v -> {
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_wechat).setOnClickListener(v -> {
                            ShareUtils.shareWechat(
                                    String.format(getString(R.string.dialog_share_title_appcode) ,inviteCode),
                                    getShareParam(),
                                    getResources().getString(R.string.dialog_share_content),
                                    AccountManager.getInstance().getAccount().getHeadImg(),
                                    platformActionListener);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_wechatmoments).setOnClickListener(v -> {
                            ShareUtils.shareWechatMoments(
                                    String.format(getString(R.string.dialog_share_title_appcode) ,inviteCode),
                                    getShareParam(),
                                    getResources().getString(R.string.dialog_share_content),
                                    AccountManager.getInstance().getAccount().getHeadImg(),
                                    platformActionListener);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_qq).setOnClickListener(v -> {
                            ShareUtils.shareQQ(
                                    String.format(getString(R.string.dialog_share_title_appcode) ,inviteCode),
                                    getShareParam(),
                                    getResources().getString(R.string.dialog_share_content),
                                    AccountManager.getInstance().getAccount().getHeadImg(),
                                    platformActionListener);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_qrcode).setVisibility(View.VISIBLE);
                        holder.getView(R.id.ll_share_qrcode).setOnClickListener(v -> {
                            InviteQRCodeActivity.startActivity(PopularizeActivity.this, InviteQRCodeActivity.class);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_friend).setVisibility(View.GONE);
                    }
                })
                .setPosition(Gravity.BOTTOM)
                .setMargin(0)
                .setOutCancel(true)
                .show(this.getSupportFragmentManager());
    }

    /**
     * 分享回调
     */
    PlatformActionListener platformActionListener = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            DLog.e("kid", "分享成功");
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            DLog.e("kid", "分享失败");
        }

        @Override
        public void onCancel(Platform platform, int i) {
            DLog.e("kid", "分享取消");
        }
    };

    //生成分享附加参数
    private String getShareParam() {
        StringBuilder param = new StringBuilder();//分享附加参数
        String eName = AccountManager.getInstance().getNickname();
        //邀请码不为空
        if (!TextUtils.isEmpty(inviteCode)) {
            param.append("?").append(Constants.WEB_CODE).append(inviteCode);
            param.append("&").append(Constants.WEB_NICKNAME).append(StringUtil.baseConvertStr(eName));
//            param.append("&").append(Constants.WEB_OPUS_ID).append("");
//            param.append("&").append(Constants.WEB_STORE_ID).append("");
//            param.append("&").append(Constants.WEB_STYLIST_ID).append("");
        } else {
            param.append("?").append(Constants.WEB_NICKNAME).append(StringUtil.baseConvertStr(eName));
//            param.append("?").append(Constants.WEB_OPUS_ID).append("");
//            param.append("?").append(Constants.WEB_STORE_ID).append("");
//            param.append("?").append(Constants.WEB_STYLIST_ID).append("");
        }

        return Constants.WEB_REGISTER + param.toString();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_share_code:
                showShareDialog();
                break;
        }
    }
}
