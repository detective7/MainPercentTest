package com.ssdy.mytextview;

import org.greenrobot.eventbus.EventBus;

/**
 * Name: EventBusUtil
 * Describe:
 * Created by ys on 2016/9/9 9:31.
 */
public class EventBusUtil {
    public static <T> void register(T context){
    if (!EventBus.getDefault().isRegistered(context)) {
            EventBus.getDefault().register(context);
        }
    }
    public static <T> void unregister(T context){
        if (EventBus.getDefault().isRegistered(context)) {
            EventBus.getDefault().unregister(context);
        }
    }

    public static  <T>  void postEvent(T t){
        EventBus.getDefault().post(t);
    }
    public static  <T>  void postStickyEvent(T t){
        EventBus.getDefault().postSticky(t);
    }
}
