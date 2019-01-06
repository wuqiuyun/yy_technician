package com.yl.technician.widget.bottomview;

import android.Manifest;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.view.View;

import com.yl.technician.R;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.databinding.ViewDeleteOpusBinding;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.module.mine.works.details.WorksDetailsActivity;
import com.yl.technician.widget.bottomview.base.BaseBottomView;


/**
 * 选择照片的底部弹框
 *
 * Created by lz on 2018/11/17.
 */
public class DeleteOpusView extends BaseBottomView implements ClickHandler {
    ViewDeleteOpusBinding mBinding;
    Context mContext;

    public DeleteOpusView(Context context) {
        super(context, R.style.BottomViewTheme_Default, R.layout.view_delete_opus);
        mBinding = DataBindingUtil.bind(rootView);
        mBinding.setClick(this);
        this.mContext = context;
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(((WorksDetailsActivity)mContext).getOpusNum()).append("张)");
        mBinding.tvNum.setText(sb.toString());
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.tv_delete_single: //删除单张
                ((WorksDetailsActivity)mContext).deleteOpus(Constants.DELETE_ONE);
                dismissBottomView();
                break;

            case R.id.tv_delete_all: //删除整个作品
                ((WorksDetailsActivity)mContext).deleteOpus(Constants.DELETE_ALL);
                dismissBottomView();
                break;

            case R.id.btn_cancel: 
                dismissBottomView();
                break;
        }

    }
}
