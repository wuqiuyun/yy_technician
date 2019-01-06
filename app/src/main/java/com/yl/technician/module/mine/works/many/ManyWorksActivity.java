package com.yl.technician.module.mine.works.many;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.component.recycleview.GridSpacingItemDecoration;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityManyWorksBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.OpusListBean;
import com.yl.technician.model.vo.bean.WorksLabelBean;
import com.yl.technician.module.mine.works.details.WorksDetailsActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;

/**
 * 美发师作品集
 */
@CreatePresenter(ManyWorksPrenster.class)
public class ManyWorksActivity extends BaseMvpAppCompatActivity<IManyWorksView, ManyWorksPrenster> implements IManyWorksView {

    ActivityManyWorksBinding mBinding;
    private ManyWorksAdapter adapter;

    private ArrayList<WorksLabelBean> mLabelBeans = new ArrayList<>();
    private ArrayList<OpusListBean.Opus> opusList = new ArrayList<>();

    private String stylistId;
    private String headPortrait = "";//头像
    private String nickName = "";//昵称

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_many_works;
    }

    @Override
    protected void init() {

        initView();
//        initData();
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);

        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            stylistId = bundle.getString(Constants.STYLIST_ID);
            this.headPortrait = bundle.getString(Constants.HEAD_PORTRAIT);
            this.nickName = bundle.getString(Constants.NICK_NAME);
        } else {
            showToast("没有获取到美发师Id");
            finish();
        }

        mBinding = (ActivityManyWorksBinding) viewDataBinding;
        // titleview
        mBinding.titleView.setLeftClickListener(v -> finish());
        // flowlayput
        mBinding.flowLayout.setOnTagClickListener((view, position, parent) -> {
            // TODO 处理选中事件
            return false;
        });

        mBinding.recycleView.setLayoutManager(new GridLayoutManager(this, 2));
        mBinding.recycleView.addItemDecoration(new GridSpacingItemDecoration(2, 30, false));
        adapter = new ManyWorksAdapter(this);
        adapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                OpusListBean.Opus opus = adapter.getDatas().get(position);
                Bundle opusBundle = new Bundle();
                opusBundle.putString(Constants.OPUS_ID, String.valueOf(opus.getStylistOpusId()));
                opusBundle.putString(Constants.HEAD_PORTRAIT, headPortrait);
                opusBundle.putString(Constants.NICK_NAME, nickName);
                WorksDetailsActivity.startActivity(ManyWorksActivity.this, WorksDetailsActivity.class, opusBundle);
            }
        });
        mBinding.recycleView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMvpPresenter().getOpusList(stylistId);
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void getOpusListSuc(OpusListBean opusListBean) {
        int total;
        //标题数目 + 作品列表展示
        if (null != opusListBean.getOpusList() && opusListBean.getOpusList().size() > 0) {
            opusList.clear();
            opusList.addAll(opusListBean.getOpusList());
            adapter.setDatas(opusList, true);
            total = opusListBean.getOpusList().size();
            if (total > 99) {
                mBinding.titleView.setSubTitleText("(99+)");
            } else {
                mBinding.titleView.setSubTitleText("(" + total + ")");
            }
        }

        mLabelBeans.clear();
        //label展示 发型
        if (null != opusListBean.getOpusHairstyleList() && opusListBean.getOpusHairstyleList().size() > 0) {
            for (OpusListBean.OpusHairstyle hair : opusListBean.getOpusHairstyleList()) {
                mLabelBeans.add(new WorksLabelBean(hair.getHairstyleName(), hair.getCount(), hair.getHairstyleId(), 1));
            }
        }
        //label展示 脸型
        if (null != opusListBean.getOpusFeaTureList() && opusListBean.getOpusFeaTureList().size() > 0) {
            for (OpusListBean.OpusFeaTure feature : opusListBean.getOpusFeaTureList()) {
                mLabelBeans.add(new WorksLabelBean(feature.getFeaTureName(), feature.getCount(), feature.getFeaTureId(), 2));
            }
        }

        mBinding.flowLayout.setAdapter(new TagAdapter<WorksLabelBean>(mLabelBeans) {
            @Override
            public View getView(FlowLayout parent, int position, WorksLabelBean label) {
                View view = getLayoutInflater().inflate(R.layout.item_works_label, null, false);
                TextView tvName = view.findViewById(R.id.tv_name);
                tvName.setText(label.getLabel());
                TextView tvNum = view.findViewById(R.id.tv_number);
                tvNum.setText(label.getNumber() + "");
                return view;
            }
        });

        mBinding.flowLayout.setOnTagClickListener((view, position, parent) -> {
            WorksLabelBean label = mLabelBeans.get(position);
            getMvpPresenter().getOpusListScreen(stylistId, label.getId(),label.getType());
            return true;
        });

    }

    @Override
    public void getOpusListFail() {
        showToast("获取作品集失败");
        finish();
    }

    /**
     * 加载筛选后的作品
     */
    @Override
    public void getOpusListScreenSuc(OpusListBean opusListBean) {
        if (null != opusListBean.getOpusList() && opusListBean.getOpusList().size() > 0) {
            opusList.clear();
            opusList.addAll(opusListBean.getOpusList());
            adapter.setDatas(opusList, true);
        }
    }

    @Override
    public void getOpusListScreenFail() {
        showToast("筛选作品集失败");
    }
}
