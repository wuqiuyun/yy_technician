package com.yl.technician.module.home.works;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.component.recycleview.GridSpacingItemDecoration;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityMyWorksBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.MyWorksBean;
import com.yl.technician.module.home.works.upload.UploadWorksActivity;
import com.yl.technician.module.mine.works.details.WorksDetailsActivity;
import com.yl.technician.util.FormatUtil;

import java.util.ArrayList;
import java.util.List;


/*我的作品
 * Create by lvlong on  2018/11/2
 */
@CreatePresenter(MyWorksPresenter.class)
public class MyWorksActivity extends BaseMvpAppCompatActivity<MyWorksView, MyWorksPresenter> implements MyWorksView {

    private ActivityMyWorksBinding mBinding;
    private MyWorksAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_works;
    }

    @Override
    protected void init() {

        mBinding = (ActivityMyWorksBinding) viewDataBinding;

        initView();

        getMvpPresenter().getStylistOpusByStylistId();

    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        mBinding.titleView.setLeftClickListener(v -> finish());
        mBinding.titleView.setRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadWorksActivity.startActivity(MyWorksActivity.this, UploadWorksActivity.class);
            }
        });

        RecyclerView recyclerView = mBinding.rcvWorks;
        mAdapter = new MyWorksAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 30, false));
        mAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                MyWorksBean myWorks = mAdapter.getDatas().get(position);
                Bundle opusBundle = new Bundle();
                opusBundle.putString(Constants.OPUS_ID, myWorks.getId());
                opusBundle.putString(Constants.HEAD_PORTRAIT, AccountManager.getInstance().getUserHeadImg());
                opusBundle.putString(Constants.NICK_NAME, AccountManager.getInstance().getNickname());
                WorksDetailsActivity.startActivity(MyWorksActivity.this, WorksDetailsActivity.class, opusBundle);
            }
        });
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(FormatUtil.Formatstring(message));
    }

    @Override
    public void onGetStylistOpusByStylistId(List<MyWorksBean> list) {

        mAdapter.setDatas((ArrayList<MyWorksBean>) list, true);

        if (list.size() > 99) {
            mBinding.titleView.setSubTitleText("(" + "99+" + ")");
        } else {

            mBinding.titleView.setSubTitleText("(" + list.size() + ")");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getMvpPresenter().getStylistOpusByStylistId();
    }
}
