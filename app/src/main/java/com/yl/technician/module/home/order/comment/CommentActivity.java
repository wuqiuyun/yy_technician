package com.yl.technician.module.home.order.comment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.image.ImageLoaderConfig;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.yl.technician.R;
import com.yl.technician.base.BaseMvpAppCompatActivity;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.component.databind.ClickHandler;
import com.yl.technician.component.recycleview.GridSpacingItemDecoration;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ActivityCommentBinding;
import com.yl.technician.model.vo.bean.OrderCommentBean;
import com.yl.technician.module.home.evaluation.PhotoAdapter;
import com.yl.technician.module.home.personpay.qrcode.OrderCertificateActivity;
import com.yl.technician.util.FormatUtil;
import com.yl.technician.widget.PhotoView.PhotoViewActivity;
import com.yl.technician.widget.bottomview.BottomViewFactory;

import java.util.ArrayList;

/**
 * 查看评价
 */
@CreatePresenter(CommentPresenter.class)
public class CommentActivity extends BaseMvpAppCompatActivity<ICommentView, CommentPresenter> implements ICommentView, ClickHandler {
    private static final String EXTRAS_ORDER_ID = "order_id";

    private ActivityCommentBinding mBinding;
    private String orderId = "";
    private PhotoAdapter photoAdapter;
    private String commentId;
    private OrderCommentBean commentBean = new OrderCommentBean();
    public static void startActivity(Context context, String orderId) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_ORDER_ID, orderId);
        startActivity(context, CommentActivity.class, bundle);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void init() {
        hasExtras();
        initView();
        loadData();
    }

    private void hasExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderId = bundle.getString(EXTRAS_ORDER_ID);
        }
        if (TextUtils.isEmpty(orderId)) {
            finish();
            return;
        }
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        mBinding = (ActivityCommentBinding) viewDataBinding;
        mBinding.setClick(this);
        photoAdapter = new PhotoAdapter(this);
        mBinding.recycleView.setLayoutManager(new GridLayoutManager(getApplicationContext() , 3));
        mBinding.recycleView.addItemDecoration(new GridSpacingItemDecoration(3, 15, false));
        mBinding.recycleView.setAdapter(photoAdapter);
        // 返回
        mBinding.titleView.setLeftClickListener(v -> finish());

        photoAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(PhotoViewActivity.IMAGE_URL, (ArrayList<String>) commentBean.getImgPaths());
                bundle.putInt(PhotoViewActivity.SHOW_POSITION, position);
                PhotoViewActivity.startActivity(CommentActivity.this, PhotoViewActivity.class, bundle);
            }
        });
    }

    @Override
    public void setOrderCommentData(OrderCommentBean commentBean) {
        this.commentId = commentBean.getId();
        this.commentBean = commentBean;
        // 头像
        ImageLoaderConfig config = new ImageLoaderConfig.Builder().
                setCropType(ImageLoaderConfig.CENTER_INSIDE).
                setAsBitmap(true).
                setCropCircle(true).
                setPlaceHolderResId(R.drawable.icon_head_pic_def).
                setErrorResId(R.drawable.icon_head_pic_def).
                setDiskCacheStrategy(ImageLoaderConfig.DiskCache.SOURCE).
                setPrioriy(ImageLoaderConfig.LoadPriority.HIGH).build();
        ImageLoader.loadImage(mBinding.ivPhoto, commentBean.getHeadImg(), config, null);
        // 姓名
        mBinding.tvName.setText(FormatUtil.Formatstring(commentBean.getNickname()));
        // 性别
        setDrawableRight(mBinding.tvName, ContextCompat.getDrawable(this,
                commentBean.getGener() == 1 ? R.drawable.icon_boy : R.drawable.icon_girl));
        String replyStr = commentBean.getReply();
        // 服务项目
        mBinding.tvProjectName.setText(FormatUtil.Formatstring(commentBean.getServiceName()));
        //回复
        mBinding.etComment.setText(FormatUtil.Formatstring(commentBean.getReply()));
        mBinding.etComment.setEnabled(TextUtils.isEmpty(commentBean.getReply()));
        if (replyStr!=null&&!TextUtils.isEmpty(replyStr)){
            mBinding.tvSend.setVisibility(View.GONE);
        }else {
            mBinding.tvSend.setVisibility(View.VISIBLE);
        }
        // 评论
        mBinding.tvComment.setText(FormatUtil.Formatstring(commentBean.getComment()));

        // 图片
        photoAdapter.setDatas((ArrayList<String>) commentBean.getImgPaths(), true);
        mBinding.tvServiceScore.setText(String.format(getString(R.string.comment_score_service), commentBean.getServer()+""));
        mBinding.tvSkillScore.setText(String.format(getString(R.string.comment_score_skill), commentBean.getSkill()+""));
        // 评分
        mBinding.ratingBar.setOnTouchListener((v, event) -> true);
        mBinding.ratingBar1.setOnTouchListener((v, event) -> true);
        mBinding.ratingBar2.setOnTouchListener((v, event) -> true);
        mBinding.ratingBar.setRating(commentBean.getLevel());
        mBinding.ratingBar1.setRating(commentBean.getSkill());
        mBinding.ratingBar2.setRating(commentBean.getServer());
    }

    @Override
    public void replyStoreCommentSuccess() {
        mBinding.etComment.setEnabled(false);
        mBinding.tvSend.setVisibility(View.GONE);
    }

    private void loadData() {
        getMvpPresenter().getOrderComment(orderId);
    }

    private void setDrawableRight(TextView textView, Drawable drawableRight) {
        drawableRight.setBounds(0, 0,drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawableRight, null);
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(FormatUtil.Formatstring(message));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send:
                getMvpPresenter().replyStylistComment(mBinding.etComment.getText().toString().trim(), commentId);
                break;
        }
    }
}
