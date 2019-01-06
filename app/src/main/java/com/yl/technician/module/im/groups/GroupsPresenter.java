package com.yl.technician.module.im.groups;

import android.text.TextUtils;

import com.yl.technician.api.GroupApi;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.model.vo.result.GroupListResult;
import com.yl.core.component.net.exception.ApiException;

/**
 * Created by zm on 2018/9/19.
 */
public class GroupsPresenter extends BasePresenter<GroupsView> {

    /**
     * 获取我的所有群
     */
    public void getMyGroups(String userId) {
        if (TextUtils.isEmpty(userId)) {
            getMvpView().showToast("用户id不能为空");
            return;
        }

        getGroups(userId);
    }

    private void getGroups(String userId) {
        new GroupApi().findAllGroup(userId, new YLRxSubscriberHelper<GroupListResult>() {
            @Override
            public void _onNext(GroupListResult result) {
                if (null != result && result.getData().size() > 0)
                    getMvpView().getGroupsSuccess(result.getData());
                else getMvpView().getGroupsEmpty();

            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().getGroupsFail();
            }

        });
    }
}
