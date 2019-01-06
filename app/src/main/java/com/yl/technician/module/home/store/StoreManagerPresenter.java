package com.yl.technician.module.home.store;


import android.content.Context;
import android.text.TextUtils;

import com.yl.core.component.net.exception.ApiException;
import com.yl.technician.api.JoinStoreApi;
import com.yl.technician.api.RecomUserApi;
import com.yl.technician.api.StoreManageApi;
import com.yl.technician.api.StorestylistApi;
import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.base.mvp.BasePresenter;
import com.yl.technician.component.net.YLRxSubscriberHelper;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.vo.bean.StoreManageNexusStyScroolBean;
import com.yl.technician.model.vo.bean.StoreManageScopeBean;
import com.yl.technician.model.vo.bean.StoreManageScopeInfoBean;
import com.yl.technician.model.vo.result.CheckMsgResult;
import com.yl.technician.model.vo.result.FindReCodeResult;
import com.yl.technician.model.vo.result.NexuStatusResult;
import com.yl.technician.model.vo.result.StoreManageNexusStyScroolResult;
import com.yl.technician.model.vo.result.StoreManageScopeInfoResult;
import com.yl.technician.model.vo.result.StoreManageScopeResult;

import java.util.ArrayList;

/**
 * Created by zm on 2018/10/12.
 */
public class StoreManagerPresenter extends BasePresenter<IStoreManagerView> {


    /**
     * 获取我的推荐码
     */
    public void findReCode(){
        new RecomUserApi().findReCode(new YLRxSubscriberHelper<FindReCodeResult>() {
            @Override
            public void _onNext(FindReCodeResult findReCodeResult) {
                if (null != findReCodeResult.getData()) getMvpView().findReCodeSuc(findReCodeResult.getData());
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }
    
    /**
     * 我的管理门店入驻/签约理发师（名称、图片）
     *
     * nexus (string, optional): 入驻/签约，0入驻，1签约 ,
     * storeId (integer, optional): 门店id（非本人门店必传） ,
     * userId (integer, optional): 用户id
     * */
    public void getNexusStyScrool(int nexus,String storeId) {
        new StoreManageApi().getNexusStyScrool(nexus,storeId,new YLRxSubscriberHelper<StoreManageNexusStyScroolResult>() {
            @Override
            public void _onNext(StoreManageNexusStyScroolResult baseResponse) {
                ArrayList<StoreManageNexusStyScroolBean> storeManageNexusStyScroolBean = baseResponse.getData();
                // callback
                getMvpView().getNexusStyScroolSucceed(storeManageNexusStyScroolBean);
            }
        });
    }

    /**
     * 门店位置信息
     *
     * lat (number, optional): 用户定位纬度 ,
     * lng (number, optional): 用户定位经度 ,
     * storeId (integer, optional): 门店id ,
     * userId (integer, optional): 用户id
     * */
    public void getStoreInfo(String lat, String lng,String storeId) {
        new StoreManageApi().getStoreInfo(lat,lng,storeId,new YLRxSubscriberHelper<StoreManageScopeInfoResult>() {
            @Override
            public void _onNext(StoreManageScopeInfoResult baseResponse) {
                StoreManageScopeInfoBean storeManageScopeInfoBean = baseResponse.getData();
                // callback
                getMvpView().getStoreInfoSucceed(storeManageScopeInfoBean);
            }
        });
    }

    /**
     * 门店顾客评价
     *
     * storeId (integer, optional): 门店ID，非本人门店必传 ,
     * userId (integer, optional): 用户id
     * */
    public void getStoreScore(String storeId) {

        new StoreManageApi().getStoreScore(storeId,new YLRxSubscriberHelper<StoreManageScopeResult>() {
            @Override
            public void _onNext(StoreManageScopeResult baseResponse) {
                StoreManageScopeBean storeManageScopeBean = baseResponse.getData();
                // callback
                getMvpView().getStoreScoreSucceed(storeManageScopeBean);
            }
        });
    }

    /**
     * 门店服务范围
     *
     * storeId (integer, optional): 门店ID，非本人门店必传 ,
     * userId (integer, optional): 用户id
     * */
    public void getStoreServerScope(String storeId) {

        new StoreManageApi().getStoreServerScope(storeId,new YLRxSubscriberHelper<StoreManageScopeResult>() {
            @Override
            public void _onNext(StoreManageScopeResult baseResponse) {
                StoreManageScopeBean storeManageScopeBean = baseResponse.getData();
                // callback
                getMvpView().getStoreServerScoreSucceed(storeManageScopeBean);
            }
        });
    }
    
    public void updateCollectionType(String isCollection, String storeId,String userId,Context context){
        new StoreManageApi().updateCollectionType(isCollection, storeId, userId, new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().showToast(baseResponse.getMessage());
                getMvpView().updateCollectionTypeSuc();
            }
        });
    }
    /**
     * 入驻/签约门店
     */
    public void nexusStore(String apply,String storeId,String stylistId,Context context){
        new JoinStoreApi().nexusStore(apply, storeId, stylistId,AccountManager.getInstance().getUserId(), new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().showToast(baseResponse.getMessage());
                getMvpView().onSucceed();
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
                getMvpView().showToast(error.getMessage());
            }
        });
    }
    /**
     * 解约
     */
    public void relieve(String stylistId , String storeId, Context context){
        new JoinStoreApi().relieve(stylistId, storeId, new YLRxSubscriberHelper<BaseResponse>(context,true) {
            @Override
            public void _onNext(BaseResponse baseResponse) {
                getMvpView().showToast(baseResponse.getMessage());
                getMvpView().onSucceed();
            }
        });
    }

    /**
     * 查询申请状态
     * @param storeId
     */
    public void getNexuStatus( String storeId,Context context){
        new JoinStoreApi().getNexuStatus(AccountManager.getInstance().getUserId(), storeId, new YLRxSubscriberHelper<NexuStatusResult>(context,true) {
            @Override
            public void _onNext(NexuStatusResult nexuStatusResult) {
                getMvpView().getNexuStatus(nexuStatusResult.getData());
            }
        });
    }

    @Override
    public void onDetachMvpView() {
        super.onDetachMvpView();
    }

    /**
     * 门店与美发师解约
     *
     * @param nexus     签约0,入驻1
     * @param storeId   门店ID
     * @param stylistId (integer, optional): 美发师ID
     *                  isAgree true为同意加入申请
     */
    public void nexus(String nexus, String storeId, String stylistId, boolean isAgree, String msgId, Context context) {
        if (TextUtils.isEmpty(nexus)) {
            nexus = "0";
        }
        if (TextUtils.isEmpty(storeId)) {
            ToastUtils.shortToast("门店ID不能为空");
            return;
        }
        if (TextUtils.isEmpty(stylistId)) {
            ToastUtils.shortToast("美发师ID不能为空");
            return;
        }

        if (isAgree) {
            new StorestylistApi().nexus(nexus, storeId, stylistId, msgId, new YLRxSubscriberHelper<BaseResponse>(context, true) {
                @Override
                public void _onNext(BaseResponse baseResponse) {
                    // callback
                    getMvpView().nexusSuccess(true);
                }

                @Override
                public void _onError(ApiException error) {
                    super._onError(error);
                }
            });
        } else {
            new StorestylistApi().refuse(nexus, storeId, stylistId, msgId, new YLRxSubscriberHelper<BaseResponse>(context, true) {
                @Override
                public void _onNext(BaseResponse baseResponse) {
                    // callback
                    getMvpView().nexusSuccess(false);
                }

                @Override
                public void _onError(ApiException error) {
                    super._onError(error);
                }
            });
        }
    }

    /**
     * 消息处理状态接口
     * @param msgId
     * @param context
     */
    public void checkMsg(String msgId, Context context) {
        new StorestylistApi().checkMsg(msgId, new YLRxSubscriberHelper<CheckMsgResult>(context, true) {
            @Override
            public void _onNext(CheckMsgResult checkMsgResult) {
                // callback
                getMvpView().checkMsg(checkMsgResult);
            }

            @Override
            public void _onError(ApiException error) {
                super._onError(error);
            }
        });
    }
}
