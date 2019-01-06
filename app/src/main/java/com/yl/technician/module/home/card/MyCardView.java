package com.yl.technician.module.home.card;

import com.yl.technician.base.mvp.IBaseView;
import com.yl.technician.model.vo.bean.ReCodeBean;
import com.yl.technician.model.vo.bean.StylistCardBean;


/*
 * Create by lvlong on  2018/10/30
 */

public interface MyCardView extends IBaseView {
    void getStylistCardSuc(StylistCardBean data);
    
    void getStylistCardFail();
    
    void stylistCollectionSuc();
    
    void stylistCollectionFail();

    void  onUploadFileSuccess(String filePath);

    void onSaveBackGround();

    void findReCodeSuc(ReCodeBean reCode);
}
