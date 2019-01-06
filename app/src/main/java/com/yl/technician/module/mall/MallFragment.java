package com.yl.technician.module.mall;

import android.support.v4.app.Fragment;

import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.technician.R;
import com.yl.technician.base.fragment.BaseMvpFragment;

/**
 * 消息
 * <p>
 * Created by zm on 2018/9/19.
 */
@CreatePresenter(MallPresenter.class)
public class MallFragment extends BaseMvpFragment<IMallView, MallPresenter> implements IMallView {

    public static Fragment newInstance() {
        return new MallFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mall;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void showToast(String message) {

    }
}
