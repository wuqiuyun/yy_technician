package com.yl.technician.module.im.transfer.transrecords;

import android.text.TextUtils;
import android.view.View;

import com.hyphenate.chat.EMMessage;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityTransferAccountsBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.ChatAdapterItemTypeBean;
import com.yl.technician.model.vo.bean.EventBean;
import com.yl.technician.model.vo.bean.SelfDefinedInfoBean;
import com.yl.technician.model.vo.bean.daobean.UserFriendsBean;
import com.yl.technician.model.vo.result.RedBagSendResult;
import com.yl.technician.util.easyutils.EasyUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zhangzz on 2018/11/9
 */
@CreatePresenter(TransferRecordsPresenter.class)
public class TransferRecordsActivity extends BaseMvpAppCompatActivity<TransferRecordsView, TransferRecordsPresenter> implements TransferRecordsView, View.OnClickListener {
    private ActivityTransferAccountsBinding mBinding;

    protected UserFriendsBean friendUserBean;//保存好友头像使用的本地查询的bean


    private String editContent;//备注
    private String money;//金额

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_transfer_accounts;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        mBinding = (ActivityTransferAccountsBinding) viewDataBinding;
        mBinding.btnInsetMoney.setOnClickListener(this);

        friendUserBean = (UserFriendsBean) getIntent().getSerializableExtra(Constants.EXTRA_FRIEND_USER_BEAN);

    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_inset_money:
                money = mBinding.edtInputMoney.getText().toString();
                if (TextUtils.isEmpty(money)) {
                    showToast(getResources().getString(R.string.rp_cannot_null));
                    return;
                }
                editContent = mBinding.edtInputMessage.getText().toString();

                getMvpPresenter().sendTransferReq(friendUserBean.getId() + "", TextUtils.isEmpty(editContent) ? getResources().getString(R.string.words_numal) : editContent,
                        AccountManager.getInstance().getUserId(), money, this);
                break;
        }
    }

    @Override
    public void requestSuccess(RedBagSendResult redBagSendResult) {
        if (friendUserBean != null && redBagSendResult != null && redBagSendResult.getData() != null) {
            SelfDefinedInfoBean chatNoFriendBean = new SelfDefinedInfoBean();
            chatNoFriendBean.setImusername(AccountManager.getInstance().getAccount().getImusername());
            chatNoFriendBean.setNickname(TextUtils.isEmpty(AccountManager.getInstance().getAccount().getNickname()) ? AccountManager.getInstance().getUsername() : AccountManager.getInstance().getAccount().getNickname());
            chatNoFriendBean.setPath(AccountManager.getInstance().getAccount().getHeadImg());

            String userId = AccountManager.getInstance().getUserId();
            if (!TextUtils.isEmpty(userId)) {
                chatNoFriendBean.setUserId(Long.parseLong(userId));
            }
            chatNoFriendBean.setDefinedMsgType(301);//红包 301.转账
            chatNoFriendBean.setRedPacketStatus(1);//发送未领取
            chatNoFriendBean.setTradeId(redBagSendResult.getData().getId());//红包id设置

            chatNoFriendBean.setMoney(Double.parseDouble(money));
            chatNoFriendBean.setContent(TextUtils.isEmpty(editContent) ? getResources().getString(R.string.words_numal) : editContent);

            EMMessage message = EasyUtil.getEmManager().sendOrderAddMoneyMes(chatNoFriendBean, friendUserBean.getImusername());
            EventBus.getDefault().post(new EventBean.RedPacketMsgUpdate(0, chatNoFriendBean, message, ChatAdapterItemTypeBean.CHAT_SEND_TRANS_ACCOUNT));
            finish();
        }
    }
}
