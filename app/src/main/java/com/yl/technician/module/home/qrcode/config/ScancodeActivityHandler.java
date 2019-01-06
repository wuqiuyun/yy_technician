/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yl.technician.module.home.qrcode.config;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.google.zxing.Result;
import com.yl.technician.module.home.qrcode.ScanCodeActivity;
import com.yl.technician.module.home.qrcode.camera.CameraManager;
import com.yl.technician.module.home.qrcode.decode.DecodeThread;
import com.yl.technician.module.home.qrcode.view.ViewfinderResultPointCallback;
import com.yl.technician.util.ActivityUtil;

import java.lang.ref.WeakReference;

/**
 * This class handles all the messaging which comprises the state machine for
 * capture. 该类用于处理有关拍摄状态的所有信息
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class ScancodeActivityHandler extends Handler {

    private static final String TAG = ScancodeActivityHandler.class
            .getSimpleName();

    private final WeakReference<ScanCodeActivity> activity;
    private final DecodeThread decodeThread;
    private State state;
    private final CameraManager cameraManager;

    private enum State {
        PREVIEW, SUCCESS, DONE
    }

    public ScancodeActivityHandler(ScanCodeActivity activity, CameraManager cameraManager) {
        this.activity = new WeakReference<>(activity);
        decodeThread = new DecodeThread(activity, new ViewfinderResultPointCallback(
                activity.getViewfinderView()));
        decodeThread.start();
        state = State.SUCCESS;

        // Start ourselves capturing previews and decoding.
        // 开始拍摄预览和解码
        this.cameraManager = cameraManager;
        cameraManager.startPreview();
        restartPreviewAndDecode();
    }

    @Override
    public void handleMessage(Message message) {
        switch (message.what) {
            case ZxingConstant.RESTART_PREVIEW:
                // 重新预览
                restartPreviewAndDecode();
                break;
            case ZxingConstant.DECODE_SUCCEEDED:
                // 解码成功
                state = State.SUCCESS;
                if (!ActivityUtil.isActivityFinished(activity.get())) {
                    activity.get().handleDecode((Result) message.obj);
                }
                break;
            case ZxingConstant.DECODE_FAILED:
                // 尽可能快的解码，以便可以在解码失败时，开始另一次解码
                state = State.PREVIEW;
                cameraManager.requestPreviewFrame(decodeThread.getHandler(),
                        ZxingConstant.DECODE);
                break;
            case ZxingConstant.RETURN_SCAN_RESULT:
                if (!ActivityUtil.isActivityFinished(activity.get())) {
                    activity.get().setResult(Activity.RESULT_OK, (Intent) message.obj);
                    activity.get().finish();
                }
                break;
            case ZxingConstant.FLASH_OPEN:
                if (!ActivityUtil.isActivityFinished(activity.get())) {
                    activity.get().switchFlashImg(ZxingConstant.FLASH_OPEN);
                }
                break;
            case ZxingConstant.FLASH_CLOSE:
                if (!ActivityUtil.isActivityFinished(activity.get())) {
                    activity.get().switchFlashImg(ZxingConstant.FLASH_CLOSE);
                }
                break;
        }
    }

    /**
     * 完全退出
     */
    public void quitSynchronously() {
        state = State.DONE;
        cameraManager.stopPreview();
        Message quit = Message.obtain(decodeThread.getHandler(), ZxingConstant.QUIT);
        quit.sendToTarget();
        try {
            // Wait at most half a second; should be enough time, and onPause()
            // will timeout quickly
            decodeThread.join(500L);
        } catch (InterruptedException e) {
            // continue
        }

        // Be absolutely sure we don't send any queued up messages
        //确保不会发送任何队列消息
        removeMessages(ZxingConstant.DECODE_SUCCEEDED);
        removeMessages(ZxingConstant.DECODE_FAILED);
    }

    public void restartPreviewAndDecode() {
        if (state == State.SUCCESS) {
            state = State.PREVIEW;
            cameraManager.requestPreviewFrame(decodeThread.getHandler(),
                    ZxingConstant.DECODE);
            if (!ActivityUtil.isActivityFinished(activity.get())) {
                activity.get().drawViewfinder();
            }
        }
    }

}
