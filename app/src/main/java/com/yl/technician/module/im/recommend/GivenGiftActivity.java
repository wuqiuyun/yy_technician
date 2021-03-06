package com.yl.technician.module.im.recommend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityGivengiftFriendBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.daobean.UserFriendsBean;
import com.yl.technician.module.im.friendinfo.PinyinComparator;
import com.yl.technician.module.im.friends.UserFriendsAdapter;
import com.yl.technician.util.StringUtil;
import com.yl.technician.widget.WaveSideBar;
import com.yl.technician.widget.dialog.BaseEasyDialog;
import com.yl.technician.widget.dialog.EasyDialog;
import com.yl.technician.widget.dialog.ViewConvertListener;
import com.yl.technician.widget.dialog.ViewHolder;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CreatePresenter(RecommFriendPresenter.class)
public class GivenGiftActivity extends BaseMvpAppCompatActivity<RecommFriendView, RecommFriendPresenter> implements RecommFriendView, OnRefreshListener {
    private ActivityGivengiftFriendBinding mBinding;
    private BaseQuickAdapter friendAdapter;
    List<UserFriendsBean> frindLists = null;
    private SmartRefreshLayout refreshLayout;

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        requestListData();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_givengift_friend;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        mBinding = (ActivityGivengiftFriendBinding) viewDataBinding;
        mBinding.titleView.setLeftClickListener(v -> {
            finish();
        });
        mBinding.titleView.setRightTextClickListener(v -> {
            finish();
        });
        mBinding.rvFriendList.setHasFixedSize(true);
        mBinding.rvFriendList.setNestedScrollingEnabled(false);
        mBinding.rvFriendList.setLayoutManager(new LinearLayoutManager(this));

        friendAdapter = new UserFriendsAdapter(R.layout.adapter_user_friends, null);
        friendAdapter.openLoadAnimation();
        mBinding.rvFriendList.setAdapter(friendAdapter);

        frindLists = new ArrayList<>();

        mBinding.sideBar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                for (int i = 0; i < frindLists.size(); i++) {
                    if (frindLists.get(i).getIndex().equals(index)) {
                        ((LinearLayoutManager) mBinding.rvFriendList.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });

        initEvent();
        initRefreshLoadLayout();
        requestListData();
    }


    @Override
    public void onFindAllContactsSuccess(List<UserFriendsBean> data) {
        refreshLayout.finishRefresh();
        if (data != null && data.size() > 0) {
            makeAdapterData(data);
        } else {
            frindLists.clear();
            friendAdapter.setNewData(null);
        }
    }

    @Override
    public void onFindAllContactsFail() {
        refreshLayout.finishRefresh();
    }

    private void requestListData() {
        getMvpPresenter().findAllContacts(AccountManager.getInstance().getUserId(), this);
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    protected void initEvent() {
        friendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showRecommDialog(frindLists.get(position));
            }
        });
    }

    protected void initRefreshLoadLayout() {
        refreshLayout = mBinding.refreshLayout;
        if (refreshLayout != null) {
            refreshLayout.setRefreshHeader(new ClassicsHeader(this));
            refreshLayout.setOnRefreshListener(this);
        }
    }

    private void makeAdapterData(List<UserFriendsBean> data) {
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); ++i) {
                String name = null;
                if (!TextUtils.isEmpty(data.get(i).getRemarks())) {
                    name = data.get(i).getRemarks();
                } else if (!TextUtils.isEmpty(data.get(i).getNickname())) {
                    name = data.get(i).getNickname();
                } else {
                    data.get(i).setIndex("#");
                }
                if (!TextUtils.isEmpty(name)) {
                    String pinyin = StringUtil.getPinYin(name);
                    String Fpinyin = pinyin.substring(0, 1).toUpperCase();
                    // 正则表达式，判断首字母是否是英文字母
                    if (Fpinyin.matches("[A-Z]")) {
                        data.get(i).setIndex(Fpinyin);
                    } else {
                        data.get(i).setIndex("#");
                    }
                }
            }
            PinyinComparator pinyinComparator = new PinyinComparator();
            Collections.sort(data, pinyinComparator);
            frindLists = data;
            friendAdapter.setNewData(frindLists);
        }
    }

    //弹出推荐好友确认框
    private void showRecommDialog(UserFriendsBean friendUserBean) {
        EasyDialog.init()
                .setLayoutId(R.layout.dialog_recomm_friend)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseEasyDialog dialog) {
                        holder.setText(R.id.tv_content_name, TextUtils.isEmpty(friendUserBean.getRemarks()) ? friendUserBean.getNickname() : friendUserBean.getRemarks());
                        holder.setText(R.id.tv_content, TextUtils.isEmpty(friendUserBean.getLabel()) ? "暂无标签" : friendUserBean.getLabel());
                        holder.setText(R.id.tv_to_nick_name, TextUtils.isEmpty(friendUserBean.getRemarks()) ? friendUserBean.getNickname() : friendUserBean.getRemarks());
                        holder.setText(R.id.tv_toperson, "转赠给");

                        ImageLoaderConfig config = null;
                        if (config == null) {
                            config = new ImageLoaderConfig.Builder().
                                    setAsBitmap(true).
                                    setPlaceHolderResId(R.drawable.im_avatar).
                                    setErrorResId(R.drawable.im_avatar).
                                    setDiskCacheStrategy(ImageLoaderConfig.DiskCache.SOURCE).
                                    setPrioriy(ImageLoaderConfig.LoadPriority.HIGH).build();
                        }
                        ImageLoader.loadImage(holder.getView(R.id.iv_head), friendUserBean.getPath(), config, null);

                        holder.setOnClickListener(R.id.tv_recomm_sure, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(Constants.GIFT_BACK, friendUserBean);
                                Intent intent = new Intent();
                                intent.putExtras(bundle);
                                setResult(Constants.GIFT_BACK_CODE, intent);
                                dialog.dismiss();

                                finish();
                            }
                        });

                        holder.setOnClickListener(R.id.tv_recomm_cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                })
                .setPosition(Gravity.CENTER)
                .setMargin(45)
                .setOutCancel(true)
                .setWidth(300)
                .show(this.getSupportFragmentManager());

    }

}
