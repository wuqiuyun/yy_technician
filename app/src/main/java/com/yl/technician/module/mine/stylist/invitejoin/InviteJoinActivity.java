package com.yl.technician.module.mine.stylist.invitejoin;

import android.text.TextUtils;
import android.view.Gravity;

import com.hyphenate.chat.EMMessage;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.component.greendao.DaoCallBackInterface;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityInviteJoinBinding;
import com.yl.technician.helper.AccountManager;
import com.yl.technician.model.constant.Constants;
import com.yl.technician.model.vo.bean.EventBean;
import com.yl.technician.model.vo.bean.SelfDefinedInfoBean;
import com.yl.technician.model.vo.bean.SendMsgBean;
import com.yl.technician.model.vo.bean.daobean.ChatNoFriendBean;
import com.yl.technician.model.vo.result.SendMsgResult;
import com.yl.technician.module.common.WebActivity;
import com.yl.technician.module.home.coupons.CouponsActivity;
import com.yl.technician.module.im.daoutil.ChatNoFriendDaoUtils;
import com.yl.technician.module.im.daoutil.UserFriendDaoUtils;
import com.yl.technician.util.easyutils.EasyUtil;
import com.yl.technician.widget.dialog.BaseEasyDialog;
import com.yl.technician.widget.dialog.EasyDialog;
import com.yl.technician.widget.dialog.ViewConvertListener;
import com.yl.technician.widget.dialog.ViewHolder;
import com.yl.technician.widget.dialog.YLDialog;

import org.greenrobot.eventbus.EventBus;

@CreatePresenter(InviteJoinPresenter.class)
public class InviteJoinActivity extends BaseMvpAppCompatActivity<IInviteJoinView, InviteJoinPresenter> implements IInviteJoinView {
    ActivityInviteJoinBinding mBinding;
    private SelfDefinedInfoBean toPersonBean;
    private ChatNoFriendDaoUtils chatNoFriendDaoUtils;
    private UserFriendDaoUtils userFriendDaoUtils;
    private String path;

    private String errorMsg;//保存该美发师和门店或者平台是否认证的错误提示

    private String storeid;//要申请入驻的门店的storeId  上个页面传入


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_invite_join;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        // 111.邀请平台美发师加入 112.邀请门店美发师加入  113平台美发师加入申请 114门店美发师加入申请
        mBinding = (ActivityInviteJoinBinding) viewDataBinding;
        chatNoFriendDaoUtils = new ChatNoFriendDaoUtils(this);
        userFriendDaoUtils = new UserFriendDaoUtils(this);

        mBinding.titleView.setRightTextClickListener(v->{
            WebActivity.startActivity(InviteJoinActivity.this, Constants.WEB_ENTER_EXOLAIN, "说明");
        });

        chatNoFriendDaoUtils.setOnIsertInterface(new DaoCallBackInterface.OnIsertInterface() {
            @Override
            public void onIsertInterface(boolean type) {
                if (type) {
                    DLog.d("发送保存成功");
                } else {
                    DLog.d("发送保存失败");
                }
            }
        });

        userFriendDaoUtils.setOnQuerySingleBackInterface(new DaoCallBackInterface.OnQuerySingleBackInterface() {

            @Override
            public void onQuerySingleBackInterface(Object entry, String id) {
                if (entry == null) {
                    chatNoFriendDaoUtils.queryWhereUser(toPersonBean.getImusername());
                }
            }
        });

        chatNoFriendDaoUtils.setOnQuerySingleBackInterface(new DaoCallBackInterface.OnQuerySingleBackInterface() {
            @Override
            public void onQuerySingleBackInterface(Object entry, String id) {
                if (entry == null) {
                    ChatNoFriendBean chatnobean = new ChatNoFriendBean();
                    chatnobean.setNickname(toPersonBean.getNickname());
                    chatnobean.setUserId(toPersonBean.getUserId());
                    chatnobean.setImusername(toPersonBean.getImusername());
                    chatnobean.setPath(toPersonBean.getPath());

                    chatNoFriendDaoUtils.insertUser(chatnobean);
                }
            }
        });


        mBinding.titleView.setLeftClickListener(v -> finish());
        toPersonBean = getIntent().getParcelableExtra(Constants.IM_SELF_BEAN);
        storeid = getIntent().getStringExtra("storeid");
        mBinding.tvPlatJoin.setOnClickListener(v -> {
            //申请，0入驻平台，1签约门店 ,
            showTagDialog("0");

        });
        mBinding.tvSelfJoin.setOnClickListener(v -> {
            //申请，0入驻平台，1签约门店 ,
            showTagDialog("1");

        });

    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void onSucceed(String apply) {
        if (!TextUtils.isEmpty(apply)) {
            if ("0".equals(apply)) {
                getMvpPresenter().sendMsg(storeid, AccountManager.getInstance().getStylistId(), 113, InviteJoinActivity.this);
            } else if ("1".equals(apply)) {
                getMvpPresenter().sendMsg(storeid, AccountManager.getInstance().getStylistId(), 114, InviteJoinActivity.this);
            }
        }
    }

    @Override
    public void onFail(String msg) {

    }

    @Override
    public void sendMsg(SendMsgResult sendMsgResult, int type) {
        if (sendMsgResult != null) {
            SendMsgBean sendMsgBean = sendMsgResult.getData();
            if (sendMsgBean != null) {
                sendInviteMsg(sendMsgBean.getMsgId(), type);
                showDLDialog();
            }
        }
    }

    private void sendInviteMsg(String msgId, int type) {
        if (toPersonBean != null) {
            userFriendDaoUtils.queryWhereUser(toPersonBean.getImusername());

            SelfDefinedInfoBean chatNoFriendBean = new SelfDefinedInfoBean();
            chatNoFriendBean.setImusername(AccountManager.getInstance().getAccount().getImusername());
            chatNoFriendBean.setNickname(TextUtils.isEmpty(AccountManager.getInstance().getAccount().getNickname()) ? AccountManager.getInstance().getUsername() : AccountManager.getInstance().getAccount().getNickname());
            chatNoFriendBean.setPath(AccountManager.getInstance().getAccount().getHeadImg());
            chatNoFriendBean.setGender(AccountManager.getInstance().getAccount().getGender());
            String userId = AccountManager.getInstance().getUserId();
            if (!TextUtils.isEmpty(userId)) {
                chatNoFriendBean.setUserId(Long.parseLong(userId));
                chatNoFriendBean.setToUserId(Long.parseLong(userId));
            }

            chatNoFriendBean.setToImusername(AccountManager.getInstance().getAccount().getImusername());
            chatNoFriendBean.setToNickname(TextUtils.isEmpty(AccountManager.getInstance().getAccount().getNickname()) ? AccountManager.getInstance().getUsername() : AccountManager.getInstance().getAccount().getNickname());
            chatNoFriendBean.setToPath(AccountManager.getInstance().getAccount().getHeadImg());
            chatNoFriendBean.setToGender(AccountManager.getInstance().getAccount().getGender());

            //111.邀请平台美发师加入 112.邀请门店美发师加入
            // 113平台美发师加入申请 114门店美发师加入申请 只有这种时候 门店端才能点击操作

            chatNoFriendBean.setDetailId(AccountManager.getInstance().getAccount().getStylistId());
            chatNoFriendBean.setEnterRecordID(msgId);
            chatNoFriendBean.setDefinedMsgType(type);
            chatNoFriendBean.setMsgStatus(1);
            chatNoFriendBean.setAddress(path);//接口请求获取当前门店地址
            chatNoFriendBean.setContent("美发师申请入驻");

            chatNoFriendBean.setRecvImusername(toPersonBean.getImusername());
            chatNoFriendBean.setRecvNickname(toPersonBean.getNickname());
            chatNoFriendBean.setRecvPath(toPersonBean.getPath());
            chatNoFriendBean.setRecvUserId(toPersonBean.getUserId());


            EMMessage message = EasyUtil.getEmManager().sendOrderAddMoneyMes(chatNoFriendBean, toPersonBean.getImusername());
            EventBus.getDefault().post(new EventBean.ConversationRefreshEvent(2));
        }
    }

    /**
     * 加入门店弹窗
     * */
    private void showDLDialog() {
        new YLDialog.Builder(this)
                .setTitle("提示")
                .setType(YLDialog.DIALOG_TYPE_NORMAL)
                .setMessage("已发送申请通知,等待对方同意")
                .setPositiveButton("确认", (dialog, which) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }

    /**
     * 入驻提示弹框确认
     * type   //申请，0入驻平台，1签约门店 ,
     */
    private void showTagDialog(String type) {
        EasyDialog.init()
                .setLayoutId(R.layout.dialog_join_store_desc)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    public void convertView(ViewHolder holder, final BaseEasyDialog dialog) {
                        if ("0".equals(type)){
                            holder.setText(R.id.tv_join_title, R.string.im_platform_join);
                            holder.setText(R.id.tv_join_desc, R.string.im_platform_join_desc);
                            holder.setText(R.id.tv_join_money, R.string.im_platform_join_money);
                        } else {
                            holder.setText(R.id.tv_join_title, R.string.im_store_join);
                            holder.setText(R.id.tv_join_desc, R.string.im_store_join_desc);
                            holder.setText(R.id.tv_join_money, R.string.im_store_join_money);
                        }
                        holder.getView(R.id.tv_join_cancel).setOnClickListener(v -> {
                            dialog.dismiss();
                        });
                        holder.getView(R.id.tv_join_ok).setOnClickListener(v -> {
                            getMvpPresenter().nexusStore(type, storeid, AccountManager.getInstance().getStylistId(), InviteJoinActivity.this);
                            dialog.dismiss();
                        });
                    }
                })
                .setPosition(Gravity.CENTER)
                .setMargin(30)
                .setOutCancel(true)
                .show(this.getSupportFragmentManager());
    }


}
