package com.yl.technician.module.im.conversation;

import com.hyphenate.chat.EMConversation;
import com.yl.technician.base.mvp.IBaseView;

import java.util.List;

/**
 * Created by zhangzz on 2018/10/19.
 */
public interface ConversationView extends IBaseView {
    void loadConversationSuccess(List<EMConversation> emConversationList);
    void loadConversationFail();
}
