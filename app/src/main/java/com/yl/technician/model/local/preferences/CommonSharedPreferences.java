package com.yl.technician.model.local.preferences;

import com.yl.technician.YLApplication;
import com.yl.technician.model.vo.bean.BasicDataBean;

/**
 * Created by zm on 2018/10/23.
 */
public class CommonSharedPreferences {
    // 门店认证成功是否显示
    private static final String CERTI_SUCCESS_SHOW = "CERTI_SUCCESS_SHOW";
    // 基础信息
    private static final String BASIC_DATA = "BASIC_DATA";
    // 邀请码
    private static final String INVITATION_CODE = "invitation_code";

    private static CommonSharedPreferences sInstance;

    private SharedPfUtils mSharedPfUtils;

    private CommonSharedPreferences() {
        mSharedPfUtils = new SharedPfUtils(YLApplication.getContext());
    }


    public static synchronized CommonSharedPreferences getInstance() {
        if(sInstance == null) {
            sInstance = new CommonSharedPreferences();
        }
        return sInstance;
    }

    /**
     * 是否第一次显示门店认证成功
     * @return true->显示
     */
    public boolean isCeritSuccessUiShow() {
        return mSharedPfUtils.getBoolean(CERTI_SUCCESS_SHOW, false);
    }

    /**
     * 门店认证成功已显示
     */
    public void setCeritSuccessUiShow() {
        mSharedPfUtils.saveBoolean(CERTI_SUCCESS_SHOW, true);
    }

    /**
     * 保存基本信息
     * @param bsicDataBean
     */
    public void saveBasicData(BasicDataBean bsicDataBean) {
        mSharedPfUtils.saveObject(BASIC_DATA, bsicDataBean);
    }

    public BasicDataBean getBasicDataBean() {
        return mSharedPfUtils.getObject(BASIC_DATA, BasicDataBean.class);
    }

    /**
     * 邀请码
     */
    public void saveInvitationCode(String code) {
        mSharedPfUtils.saveString(INVITATION_CODE,code);
    }

    public String getInvitationCode() {
        return mSharedPfUtils.getString(INVITATION_CODE, "");
    }
}
