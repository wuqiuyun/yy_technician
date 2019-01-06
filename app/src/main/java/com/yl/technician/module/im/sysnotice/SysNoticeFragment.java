package com.yl.technician.module.im.sysnotice;

import android.support.v7.widget.LinearLayoutManager;

import com.yl.core.component.log.DLog;
import com.yl.technician.R;
import com.yl.technician.base.fragment.BaseMvpFragment;
import com.yl.technician.component.greendao.DaoCallBackInterface;
import com.yl.technician.databinding.FragmentSystemMsgBinding;
import com.yl.technician.model.local.preferences.CommonSharedPreferences;
import com.yl.technician.model.vo.bean.EventBean;
import com.yl.technician.model.vo.bean.SysNoticeBean;
import com.yl.technician.model.vo.bean.daobean.SysMessageBean;
import com.yl.technician.module.im.daoutil.SysMessageDaoUtils;
import com.yl.technician.util.FormatUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhangzz on 2018/9/28
 * 系统通知fragment
 */
public class SysNoticeFragment extends BaseMvpFragment<SysNoticeView, SysNoticePresenter> implements SysNoticeView {
    private FragmentSystemMsgBinding mBinding;
    private SysNoticeAdapter adapter;
    private SysMessageDaoUtils sysMessageDaoUtils;
    private  ArrayList<SysMessageBean> mList = new ArrayList<>();

    public static SysNoticeFragment getInstance() {
        return new SysNoticeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_system_msg;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mBinding = (FragmentSystemMsgBinding) viewDataBinding;
        mBinding.rvSysMsgs.setHasFixedSize(true);
        mBinding.rvSysMsgs.setNestedScrollingEnabled(false);
        mBinding.rvSysMsgs.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new SysNoticeAdapter(R.layout.adapter_system_nitice);
        adapter.openLoadAnimation();
        mBinding.rvSysMsgs.setAdapter(adapter);

        sysMessageDaoUtils = new SysMessageDaoUtils(getContext());
        sysMessageDaoUtils.setOnQueryAllInterface(new DaoCallBackInterface.OnQueryAllInterface<SysMessageBean>() {
            @Override
            public void onQueryAllBatchInterface(List<SysMessageBean> list) {
                if (null != list && list.size() > 0) {
                    mList.clear();
                    Collections.reverse(list);//倒序排放，使时间最后的排上面
                    mList.addAll(list);
                    adapter.setNewData(mList);
                }
            }

            @Override
            public void onQueryAllBatchFailInterface() {
                DLog.e("sysMessageDaoUtils", "--------------------onQueryAllBatchFailInterface");
            }
        });

        sysMessageDaoUtils.setOnDeleteInterface(type -> {
            if (type){
                mList.clear();
                adapter.setNewData(mList);
            }
        });
    }

    /**
     * 收到新的推送 刷新显示
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBean.NewSysMessage event) {
        if (event != null) {
            loadData();
        }
    }

    @Override
    protected void loadData() {
        sysMessageDaoUtils.queryAll();
    }

    @Override
    public void showToast(String message) {

    }

    //清空数据
    public void clearData(){
        if (null != sysMessageDaoUtils){
            sysMessageDaoUtils.deleteAll();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (null != sysMessageDaoUtils){
            sysMessageDaoUtils.closeConnection();
        }
    }
}
