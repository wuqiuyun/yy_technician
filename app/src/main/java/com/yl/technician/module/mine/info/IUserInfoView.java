package com.yl.technician.module.mine.info;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.StylistCenterInfoBean;

/**
 * Created by zm on 2018/9/29.
 */
public interface IUserInfoView extends IBaseView{
    
    void getStylistInfoSuc(StylistCenterInfoBean stylistInfo);
    
    void getStylistInfoFail();
    
    void updateHobbySuc();
    
    void updateIntroductionSuc();
    
    void updateStylistNameSuc();
    
    void updateBirthdaySuc();
    
    void updateHeadImgSuc();

    void updatePortraitImgSuc();

    void  onUploadFileSuccess(String filePath);

    void onUpdatePosition();
}
