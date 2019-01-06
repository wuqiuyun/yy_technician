package com.yl.technician.module.im.chat.chatview;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yl.technician.util.Utils;

/**
 * Created by zhangzz on 2018/10/12
 */

public class EmojiKeyboard {

    private Activity activity;

    //文本输入框
    private EditText editText;

    //表情面板
    private View emojiPanelView;
    //更多面板
    private View addMorePanelView;

    //内容View,即除了表情布局和输入框布局以外的布局
    //用于固定输入框一行的高度以防止跳闪
    private View contentView;

    private InputMethodManager inputMethodManager;

    private SharedPreferences sharedPreferences;

    private static final String EMOJI_KEYBOARD = "EmojiKeyboard";

    private static final String KEY_SOFT_KEYBOARD_HEIGHT = "SoftKeyboardHeight";

    private static final int SOFT_KEYBOARD_HEIGHT_DEFAULT = 654;

    private Handler handler;

    public EmojiKeyboard(Activity activity, EditText editText, View addMorePanelView, View emojiPanelView, View addMorePanelSwitchView, View emojiPanelSwitchView, View contentView) {
        init(activity, editText, addMorePanelView, emojiPanelView, addMorePanelSwitchView, emojiPanelSwitchView, contentView);
    }

    private void init(Activity activity, EditText editText, View addMorePanelView, View emojiPanelView, View addMorePanelSwitchView, View emojiPanelSwitchView, View contentView) {
        this.activity = activity;
        this.editText = editText;
        this.addMorePanelView = addMorePanelView;
        this.emojiPanelView = emojiPanelView;
        this.contentView = contentView;
        this.editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && EmojiKeyboard.this.emojiPanelView.isShown()) {
                    lockContentViewHeight();
                    hideEmojiPanel(true);
                    unlockContentViewHeight();
                }

                if (event.getAction() == MotionEvent.ACTION_UP && EmojiKeyboard.this.addMorePanelView.isShown()) {
                    lockContentViewHeight();
                    hideAddMorePanel(true);
                    unlockContentViewHeight();
                }
                return false;
            }
        });
        this.contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (EmojiKeyboard.this.emojiPanelView.isShown()) {
                        hideEmojiPanel(false);
                    } else if (EmojiKeyboard.this.addMorePanelView.isShown()) {
                        hideAddMorePanel(false);
                    } else if (isSoftKeyboardShown()) {
                        hideSoftKeyboard();
                    }
                }
                return false;
            }
        });
        //用于弹出表情面板的View
        emojiPanelSwitchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EmojiKeyboard.this.addMorePanelView.isShown()) {
                    hideAddMorePanel(false);
                }
                if (EmojiKeyboard.this.emojiPanelView.isShown()) {
                    lockContentViewHeight();
                    hideEmojiPanel(true);
                    unlockContentViewHeight();
                } else {
                    if (isSoftKeyboardShown()) {
                        lockContentViewHeight();
                        showEmojiPanel();
                        unlockContentViewHeight();
                    } else {
                        showEmojiPanel();
                    }
                }
            }
        });

        //用于弹出更多面板的View
        addMorePanelSwitchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EmojiKeyboard.this.emojiPanelView.isShown()) {
                    hideEmojiPanel(false);
                }

                if (EmojiKeyboard.this.addMorePanelView.isShown()) {
                    lockContentViewHeight();
                    hideAddMorePanel(true);
                    unlockContentViewHeight();
                } else {
                    if (isSoftKeyboardShown()) {
                        lockContentViewHeight();
                        showAddMorePanel();
                        unlockContentViewHeight();
                    } else {
                        showAddMorePanel();
                    }
                }
            }
        });
        this.inputMethodManager = (InputMethodManager) this.activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        this.sharedPreferences = this.activity.getSharedPreferences(EMOJI_KEYBOARD, Context.MODE_PRIVATE);
        this.activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.handler = new Handler();
        init();
    }

    /**
     * 如果之前没有保存过键盘高度值
     * 则在进入Activity时自动打开键盘，并把高度值保存下来
     */
    private void init() {
        if (!sharedPreferences.contains(KEY_SOFT_KEYBOARD_HEIGHT)) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showSoftKeyboard(true);
                }
            }, 200);
        }
    }

    /**
     * 当点击返回键时需要先隐藏表情面板
     */
    public boolean interceptBackPress() {
        if (emojiPanelView.isShown()) {
            hideEmojiPanel(false);
            return true;
        }
        return false;
    }

    /**
     * 锁定内容View以防止跳闪
     */
    private void lockContentViewHeight() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) contentView.getLayoutParams();
        layoutParams.height = contentView.getHeight();
        layoutParams.weight = 0;
    }

    /**
     * 释放锁定的内容View
     */
    private void unlockContentViewHeight() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((LinearLayout.LayoutParams) contentView.getLayoutParams()).weight = 1;
            }
        }, 200);
    }

    /**
     * 获取键盘的高度
     */
    private int getSoftKeyboardHeight() {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //屏幕当前可见高度，不包括状态栏
        int displayHeight = rect.bottom - rect.top;
        //屏幕可用高度
        int availableHeight = Utils.getAvailableScreenHeight(activity);
        //用于计算键盘高度
        int softInputHeight = availableHeight - displayHeight - Utils.getStatusBarHeight(activity);
        Log.e("TAG-di", displayHeight + "");
        Log.e("TAG-av", availableHeight + "");
        Log.e("TAG-so", softInputHeight + "");
        if (softInputHeight != 0) {
            // 因为考虑到用户可能会主动调整键盘高度，所以只能是每次获取到键盘高度时都将其存储起来
            sharedPreferences.edit().putInt(KEY_SOFT_KEYBOARD_HEIGHT, softInputHeight).apply();
        }
        return softInputHeight;
    }

    /**
     * 获取本地存储的键盘高度值或者是返回默认值
     */
    private int getSoftKeyboardHeightLocalValue() {
        return sharedPreferences.getInt(KEY_SOFT_KEYBOARD_HEIGHT, SOFT_KEYBOARD_HEIGHT_DEFAULT);
    }

    /**
     * 判断是否显示了键盘
     */
    private boolean isSoftKeyboardShown() {
        return getSoftKeyboardHeight() != 0;
    }

    /**
     * 令编辑框获取焦点并显示键盘
     */
    private void showSoftKeyboard(boolean saveSoftKeyboardHeight) {
        editText.requestFocus();
        inputMethodManager.showSoftInput(editText, 0);
        if (saveSoftKeyboardHeight) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getSoftKeyboardHeight();
                }
            }, 200);
        }
    }

    /**
     * 隐藏键盘
     */
    private void hideSoftKeyboard() {
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * 显示表情面板
     */
    private void showEmojiPanel() {
        int softKeyboardHeight = getSoftKeyboardHeight();
        if (softKeyboardHeight == 0) {
            softKeyboardHeight = getSoftKeyboardHeightLocalValue();
        } else {
            hideSoftKeyboard();
        }
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        param.height = softKeyboardHeight;
        param.gravity = Gravity.CENTER;  //必须要加上这句，setMargins才会起作用，而且此句还必须在setMargins下面
        emojiPanelView.setLayoutParams(param);
        emojiPanelView.setVisibility(View.VISIBLE);
        if (emojiPanelVisibilityChangeListener != null) {
            emojiPanelVisibilityChangeListener.onShowEmojiPanel();
        }
    }

    /**
     * 隐藏表情面板，同时指定是否随后开启键盘
     */
    private void hideEmojiPanel(boolean showSoftKeyboard) {
        if (emojiPanelView.isShown()) {
            emojiPanelView.setVisibility(View.GONE);
            if (showSoftKeyboard) {
                showSoftKeyboard(false);
            }
            if (emojiPanelVisibilityChangeListener != null) {
                emojiPanelVisibilityChangeListener.onHideEmojiPanel();
            }
        }
    }


    /**
     * 显示更多面板
     */
    private void showAddMorePanel() {
        int softKeyboardHeight = getSoftKeyboardHeight();
        if (softKeyboardHeight == 0) {
            softKeyboardHeight = getSoftKeyboardHeightLocalValue();
        } else {
            hideSoftKeyboard();
        }

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        param.height = softKeyboardHeight;
        param.gravity = Gravity.CENTER;  //必须要加上这句，setMargins才会起作用，而且此句还必须在setMargins下面
        addMorePanelView.setLayoutParams(param);
        addMorePanelView.setVisibility(View.VISIBLE);
        if (emojiPanelVisibilityChangeListener != null) {
            emojiPanelVisibilityChangeListener.onShowEmojiPanel();
        }
    }


    /**
     * 隐藏更多面板，同时指定是否随后开启键盘
     */
    private void hideAddMorePanel(boolean showSoftKeyboard) {
        if (addMorePanelView.isShown()) {
            addMorePanelView.setVisibility(View.GONE);
            if (showSoftKeyboard) {
                showSoftKeyboard(false);
            }
            if (emojiPanelVisibilityChangeListener != null) {
                emojiPanelVisibilityChangeListener.onHideEmojiPanel();
            }
        }
    }

    public interface OnEmojiPanelVisibilityChangeListener {

        void onShowEmojiPanel();

        void onHideEmojiPanel();
    }

    private OnEmojiPanelVisibilityChangeListener emojiPanelVisibilityChangeListener;

    public void setEmoticonPanelVisibilityChangeListener(OnEmojiPanelVisibilityChangeListener emojiPanelVisibilityChangeListener) {
        this.emojiPanelVisibilityChangeListener = emojiPanelVisibilityChangeListener;
    }

}
