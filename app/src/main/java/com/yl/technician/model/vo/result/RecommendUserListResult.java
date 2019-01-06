package com.yl.technician.model.vo.result;

import com.yl.technician.base.data.BaseResponse;
import com.yl.technician.model.vo.bean.UserIncomeInfoBean;

import java.util.ArrayList;

/**
 * Created by zm on 2019/1/4.
 */
public class RecommendUserListResult extends BaseResponse<RecommendUserListResult.Data> {

    public static final class Data {
        double allIncome;
        int inviteSize;
        ArrayList<UserIncomeInfoBean> userIncomeInfos;

        public double getAllIncome() {
            return allIncome;
        }

        public void setAllIncome(double allIncome) {
            this.allIncome = allIncome;
        }

        public int getInviteSize() {
            return inviteSize;
        }

        public void setInviteSize(int inviteSize) {
            this.inviteSize = inviteSize;
        }

        public ArrayList<UserIncomeInfoBean> getUserIncomeInfos() {
            return userIncomeInfos;
        }

        public void setUserIncomeInfos(ArrayList<UserIncomeInfoBean> userIncomeInfos) {
            this.userIncomeInfos = userIncomeInfos;
        }
    }
}
