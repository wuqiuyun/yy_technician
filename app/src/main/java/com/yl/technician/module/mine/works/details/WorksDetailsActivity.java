package com.yl.technician.module.mine.works.details;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityWorksDetailsBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.OpusDetailBean;
import com.yl.technician.model.vo.bean.ReCodeBean;
import com.yl.technician.module.im.sharetofriend.ShareToFriendActivity;
import com.yl.technician.util.ShareUtils;
import com.yl.technician.util.StringUtil;
import com.yl.technician.widget.bottomview.BottomViewFactory;
import com.yl.technician.widget.bottomview.base.BaseBottomView;
import com.yl.technician.widget.dialog.BaseEasyDialog;
import com.yl.technician.widget.dialog.EasyDialog;
import com.yl.technician.widget.dialog.ViewConvertListener;
import com.yl.technician.widget.dialog.ViewHolder;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * 作品详情
 * <p>
 * Create by zm on 2018/10/12Ø
 */

@CreatePresenter(WorksDetailsPresenter.class)
public class WorksDetailsActivity extends BaseMvpAppCompatActivity<IWorksDetailsView, WorksDetailsPresenter> implements IWorksDetailsView {

    ActivityWorksDetailsBinding mBinding;
    private BaseBottomView mDeleteOpusView;

    private WorksPageAdapter mWorksPageAdapter;

    private String pictrueId;//当前要删除的作品图片id

    private String opusId;
    private boolean isCollection = false;//是否已收藏
    private int collectionNum = 0;//收藏数
    private ArrayList<OpusDetailBean.pictrue> pictrues = new ArrayList<>();
    private boolean isMine = false;//是否是用户自己的作品

    private OpusDetailBean mDetail;//保存的作品详情bean
    private String headPortrait = "";//头像
    private String nickName = "";//昵称

    private String inviteCode = null;//邀请码

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_works_details;
    }

    @Override
    protected void init() {
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            opusId = bundle.getString(Constants.OPUS_ID);
            this.headPortrait = bundle.getString(Constants.HEAD_PORTRAIT);
            this.nickName = bundle.getString(Constants.NICK_NAME);
        } else {
            showToast("获取作品信息出错");
            finish();
            return;
        }
        initView();
        getMvpPresenter().getOpusDetail(opusId);//查询作品详情
        getMvpPresenter().findReCode();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);

        mBinding = (ActivityWorksDetailsBinding) viewDataBinding;
        // titleview
        mBinding.titleView.setLeftClickListener(v -> finish());
        mBinding.titleView.setRightImgClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        showShareDialog();
                    }
                }.start();
            }
        });
        mBinding.titleView.setSubRightImgClickListener(v -> {
            int type = isCollection ? 0 : 1;
            getMvpPresenter().oupsCollection(opusId, type);
        });

        mBinding.viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (pictrues.size() == 0) {
                    return;
                }
                mBinding.tvPageNum.setText(String.format(getString(R.string.opus_page), position + 1, pictrues.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void getOpusDetailSuc(OpusDetailBean detail) {
        this.mDetail = detail;
        pictrues.clear();
        pictrues.addAll(detail.getPictrues());
        
        isMine = String.valueOf(detail.getStylistId()).equals(AccountManager.getInstance().getStylistId());

        mBinding.tvDesc.setText(detail.getDescribe());
        mBinding.tvCollectionNum.setText(String.valueOf(detail.getCollection()));
        collectionNum = detail.getCollection();
        mBinding.tvForwardNum.setText(String.valueOf(detail.getReposted()));
        mBinding.tvLookNum.setText(String.valueOf(detail.getPageview()));
        mBinding.tvPageNum.setText(String.format(getString(R.string.opus_page), 1, pictrues.size()));
        isCollection = detail.isCollection();
        if (isCollection) {
            mBinding.titleView.setSubRightIcon(getResources().getDrawable(R.drawable.icon_collection));
        } else {
            mBinding.titleView.setSubRightIcon(getResources().getDrawable(R.drawable.icon_collection_false));
        }

        if (null == mWorksPageAdapter) {
            mWorksPageAdapter = new WorksPageAdapter(WorksDetailsActivity.this, pictrues);
            mBinding.viewPage.setAdapter(mWorksPageAdapter);
        } else {
            mWorksPageAdapter.notifyDataSetChanged();
        }
        mBinding.viewPage.setCurrentItem(0);
        if (!String.valueOf(mDetail.getStylistId()).equals(AccountManager.getInstance().getStylistId())){
            getMvpPresenter().opusPageview(opusId);//增加查看数
        }

    }

    @Override
    public void getOpusDetailFail() {
        showToast("获取作品详情失败了");
        finish();
    }


    @Override
    public void collectSuccess() {
        if (isCollection) {
            showToast("取消收藏成功");
            isCollection = false;
            collectionNum--;
            mBinding.titleView.setSubRightIcon(getResources().getDrawable(R.drawable.icon_collection_false));
        } else {
            showToast("收藏成功");
            isCollection = true;
            collectionNum++;
            mBinding.titleView.setSubRightIcon(getResources().getDrawable(R.drawable.icon_collection));
        }
        setResult(Constants.RESULT_REFRESH_CODE);
        mBinding.tvCollectionNum.setText(String.valueOf(collectionNum));
    }

    @Override
    public void collectFail() {
        if (isCollection) {
            showToast("取消收藏失败");
        } else {
            showToast("收藏失败");
        }
    }

    @Override
    public void deleteSingleSuc() {
        showToast("已删除单张作品");
        getMvpPresenter().getOpusDetail(opusId);//查询作品详情
    }

    @Override
    public void deleteAllSuc() {
        showToast("作品已删除！");
        finish();
    }

    public int getOpusNum() {
        if (null != pictrues) {
            return pictrues.size();
        } else {
            return 0;
        }
    }

    public void showBottomView(int position) {
        if (isMine) {//只能删自己的作品
            pictrueId = pictrues.get(position).getPictureId() + "";

            if (mDeleteOpusView == null) {
                mDeleteOpusView = BottomViewFactory.create(WorksDetailsActivity.this, BottomViewFactory.Type.Opus);
            }
            mDeleteOpusView.showBottomView(true);
        }
    }

    //删除作品
    public void deleteOpus(int type) {
        if (pictrues.size() == 1) {
            type = Constants.DELETE_ALL;
        }

        switch (type) {
            case Constants.DELETE_ONE:
                getMvpPresenter().deleteOpusPicture(pictrueId);
                break;
            case Constants.DELETE_ALL:
                getMvpPresenter().deleteOpus(opusId);
                break;
        }
    }

    //分享弹框
    private void showShareDialog() {
        EasyDialog.init()
                .setLayoutId(R.layout.dialog_share)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseEasyDialog dialog) {
                        String picImg = pictrues.get(mBinding.viewPage.getCurrentItem()).getPath();
                        String name = String.format(getString(R.string.dialog_share_title_appworks), nickName);
                        holder.getView(R.id.tv_share_cancel).setOnClickListener(v -> {
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_wechat).setOnClickListener(v -> {
                            ShareUtils.shareWechat(
                                    name,
                                    getShareParam(),
                                    getResources().getString(R.string.dialog_share_content),
                                    picImg,
                                    platformActionListener);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_wechatmoments).setOnClickListener(v -> {
                            ShareUtils.shareWechatMoments(
                                    name,
                                    getShareParam(),
                                    getResources().getString(R.string.dialog_share_content),
                                    picImg,
                                    platformActionListener);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_qq).setOnClickListener(v -> {
                            ShareUtils.shareQQ(
                                    name,
                                    getShareParam(),
                                    getResources().getString(R.string.dialog_share_content),
                                    picImg,
                                    platformActionListener);
                            dialog.dismiss();
                        });
                        holder.getView(R.id.ll_share_friend).setOnClickListener(v -> {
                            //分享的店铺的相关信息传递 没有传null 此页面门店的storeId必传
                            if (mDetail!=null) {
                                ShareToFriendActivity.startShareToFriendActivity(
                                        WorksDetailsActivity.this,
                                        102,
                                        "",
                                        nickName,
                                        mDetail.getOpusId() + "",
                                        "",
                                        headPortrait,
                                        mDetail.getDescribe());
                            }
                            dialog.dismiss();
                        });
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
            if (!String.valueOf(mDetail.getStylistId()).equals(AccountManager.getInstance().getStylistId())){
                getMvpPresenter().opusRepost(opusId);//增加分享数
            }
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
            param.append("&").append(Constants.WEB_OPUS_ID).append(opusId);
            param.append("&").append(Constants.WEB_NICKNAME).append(StringUtil.baseConvertStr(eName));
//            param.append("&").append(Constants.WEB_STYLIST_ID).append(stylistId);
//            param.append("&").append(Constants.WEB_STORE_ID).append("");
        } else {
            param.append("?").append(Constants.WEB_OPUS_ID).append(opusId);
            param.append("&").append(Constants.WEB_NICKNAME).append(StringUtil.baseConvertStr(eName));
//            param.append("?").append(Constants.WEB_STYLIST_ID).append(stylistId);
//            param.append("?").append(Constants.WEB_STORE_ID).append("");
        }

        return Constants.WEB_WORK_DETAILS + param.toString();
    }

    @Override
    public void findReCodeSuc(ReCodeBean reCode) {
        inviteCode = reCode.getInvitecode();
    }
}
