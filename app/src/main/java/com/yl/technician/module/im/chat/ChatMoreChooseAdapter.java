package com.yl.technician.module.im.chat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yl.technician.R;
import com.yl.technician.model.vo.bean.ChatMoreChooseBean;


/**
 */
public class ChatMoreChooseAdapter extends BaseQuickAdapter<ChatMoreChooseBean, BaseViewHolder> {
    public ChatMoreChooseAdapter(int layoutResId) {
        super(layoutResId, null);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ChatMoreChooseBean bean) {
        if (bean != null) {
            baseViewHolder.setText(R.id.text, mContext.getString(bean.getName()));
            baseViewHolder.setBackgroundRes(R.id.image, bean.getResouseId());
        }
    }
}
