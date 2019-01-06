package com.yl.technician.component.net;

import com.yl.core.component.net.RequestManager;
import com.yl.technician.util.HostUtil;

/**
 * Created by zm on 2018/9/9.
 */

public class YLRequestManager extends RequestManager {

    public static <T> T getRequest(Class<T> clazz) {
        T t = (T) sRequestManager.get(clazz);
        if (t == null) {
            t = YLRetrofitClient.createApi(clazz, HostUtil.getServerHost());
            sRequestManager.put(clazz, t);
        }
        return t;
    }
}
