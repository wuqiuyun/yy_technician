package com.yl.technician.module.home.service.combo.multiterm;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.yl.core.component.log.DLog;
import com.yl.technician.R;
import com.yl.technician.base.adapter.BaseRecycleViewAdapter;
import com.yl.technician.component.toast.ToastUtils;
import com.yl.technician.databinding.ItemMultitermComboBinding;
import com.yl.technician.databinding.ItemServiceSettingTwoBinding;
import com.yl.technician.model.vo.bean.ServerTypeBean;
import com.yl.technician.model.vo.bean.ServiceSettingTwoBean;
import com.yl.technician.model.vo.requestbody.SaveServerRequestBody;

import java.util.ArrayList;
import java.util.List;


/*
 * Create by lvlong on  2018/10/24
 */

public class MultitermComboAdapter extends BaseRecycleViewAdapter<SaveServerRequestBody.PackageOptionsBean> {

    private ArrayList<ServerTypeBean> mServerTypeBeans;
    private ArrayList<String> mServerType;
    private Context mContext;

    public MultitermComboAdapter(Context context) {
        mContext = context;
        mServerTypeBeans = new ArrayList<>();
        mServerType = new ArrayList<>();
    }
    //初始化下拉菜单
    public void initSpData(ArrayList<ServerTypeBean> serverTypeBeans) {
        mServerTypeBeans.clear();
        mServerTypeBeans .addAll(serverTypeBeans);
        for (ServerTypeBean s :mServerTypeBeans) {
            mServerType.add(s.getName());
        }
    }


    @NonNull
    @Override
    public MultitermComboHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_multiterm_combo, parent, false);
        return new MultitermComboHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MultitermComboHolder viewHolder = (MultitermComboHolder) holder;
        viewHolder.setIsRecyclable(false);
        viewHolder.mBinding.tvIndex.setText("项目"+NumberFormatUtil.formatInteger(position+1));
        SaveServerRequestBody.PackageOptionsBean packageOptionsBean = mDatas.get(position);
        ArrayAdapter adapter2 = new ArrayAdapter(mContext, R.layout.support_simple_spinner_dropdown_item,mServerType);
        viewHolder.mBinding.spType.setAdapter(adapter2);
        viewHolder.mBinding.spType.setSelection(packageOptionsBean.getPosition());
        viewHolder.mBinding.etContent.setText(packageOptionsBean.getOptionvalue());





        viewHolder.mBinding.etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mDatas.get(position).setOptionvalue(editable.toString());
                if (TextUtils.isEmpty(editable.toString())){
                    mDatas.get(position).setOptionv(false);
                }else {
                    mDatas.get(position).setOptionv(true);
                }
            }
        });




        viewHolder.mBinding.spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //将选择的元素显示出来
                viewHolder.mBinding.tvContentName.setVisibility(View.VISIBLE);
                viewHolder.mBinding.etContent.setVisibility(View.VISIBLE);
                mDatas.get(position).setPosition(i);
                mDatas.get(position).setCategoryId(String.valueOf(mServerTypeBeans.get(i).getId()));
                String contentName="";
                if (mServerTypeBeans.get(i).getOptions()!=null&&mServerTypeBeans.get(i).getOptions().size()!=0){
                    contentName = mServerTypeBeans.get(i).getOptions().get(0).getOptionname();
                    mDatas.get(position).setOptionname(contentName);
                    mDatas.get(position).setOptiontitle(String.valueOf(mServerTypeBeans.get(i).getOptions().get(0).getOptiontitle()));
                    mDatas.get(position).setOptionId(String.valueOf(mServerTypeBeans.get(i).getOptions().get(0).getId()));
                }else {
                    viewHolder.mBinding.tvContentName.setVisibility(View.GONE);
                    viewHolder.mBinding.etContent.setVisibility(View.GONE);
                }

                viewHolder.mBinding.tvContentName.setText(contentName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public class MultitermComboHolder extends BaseViewHolder {
        ItemMultitermComboBinding mBinding;

        public MultitermComboHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
