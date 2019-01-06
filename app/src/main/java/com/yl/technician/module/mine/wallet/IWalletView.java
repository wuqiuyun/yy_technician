package com.yl.technician.module.mine.wallet;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.BankSumBean;
import com.yl.technician.model.vo.bean.CashInfoAccountBean;
import com.yl.technician.model.vo.bean.CashInfoBean;
import com.yl.technician.model.vo.bean.CoinInfoAccountBean;
import com.yl.technician.model.vo.bean.CoinInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zm on 2018/9/29.
 */
public interface IWalletView extends IBaseView {
    //获取钱包余额
    void onGetCashInfoSuccess(CashInfoBean bean);

    //获取代币余额
    void onGetCoinInfoSuccess(CoinInfoBean bean);

    //获取钱包余额详情列表
//    void onGetCashInfoListSuccess(ArrayList<CashInfoAccountBean> bean);

    //获取代币余额详情列表
//    void onGetCoinInfoListSuccess(ArrayList<CoinInfoAccountBean> bean);

    //获取钱包余额详情列表失败
//    void onGetCashInfoListFail();

    //获取代币余额详情列表失败
//    void onGetCoinInfoListFail();
}
