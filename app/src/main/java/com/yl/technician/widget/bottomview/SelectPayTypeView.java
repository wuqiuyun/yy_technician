package com.yl.technician.widget.bottomview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.yl.technician.R;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.databinding.ViewDeleteOpusBinding;
import com.yl.technician.databinding.ViewMapSelectBinding;
import com.yl.technician.databinding.ViewSelectPaytypeBinding;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.module.home.personpay.qrcode.OrderCertificateActivity;
import com.yl.technician.module.mine.works.details.WorksDetailsActivity;
import com.yl.technician.widget.bottomview.base.BaseBottomView;


/**
 * 选择支付方式
 *
 * Created by lz on 2018/11/17.
 */
public class SelectPayTypeView extends BaseBottomView implements ClickHandler {
    ViewSelectPaytypeBinding mBinding;

    public SelectPayTypeView(Context context) {
        super(context, R.style.BottomViewTheme_Default, R.layout.view_select_paytype);
        mBinding = DataBindingUtil.bind(rootView);
        mBinding.setClick(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.tv_pay_app: //APP支付
                ((OrderCertificateActivity)mContext).payTypeOpus(Constants.PAYTYPE_APP);
                dismissBottomView();
                break;

            case R.id.tv_pay_weixin: //微信支付
                ((OrderCertificateActivity)mContext).payTypeOpus(Constants.PAYTYPE_WECHAT);
                dismissBottomView();
                break;

            case R.id.btn_cancel: 
                dismissBottomView();
                break;
        }

    }
}
