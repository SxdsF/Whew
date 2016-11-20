package com.sxdsf.whew.adapter;

import com.sxdsf.whew.Adapter;
import com.sxdsf.whew.WhewCall;

import rx.Observable;

/**
 * com.sxdsf.whew.adapter.RxJavaAdapter
 *
 * @author 孙博闻
 * @date 2016/7/14 16:08
 * @desc RxJava的结果适配
 */
public class RxJavaAdapter<T> implements Adapter<WhewCall<T>, Observable<T>> {
    @Override
    public Observable<T> adapt(WhewCall<T> whewCall) {
        return null;
    }
}
