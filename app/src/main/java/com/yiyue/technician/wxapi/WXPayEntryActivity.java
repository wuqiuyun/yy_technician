package com.yiyue.technician.wxapi;

import android.app.Activity;
import android.os.Bundle;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;


/**
 * Created by lyj on 2018/10/30.
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onReq(BaseReq baseReq) {

	}

	@Override
	public void onResp(BaseResp baseResp) {

	}
}