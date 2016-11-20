package com.sxdsf.whew.info;

/**
 * com.sxdsf.whew.info.WhewResponse
 *
 * @author 孙博闻
 * @date 2016/7/14 15:10
 * @desc 真正的返回
 */
public class WhewResponse<T> extends Response {

    private final T mBody;

    public WhewResponse(T body) {
        mBody = body;
    }

    @Override
    public <R> R checkAndGet(Class<R> cls) {
        R r = null;
        if (cls != null && mBody != null && mBody.getClass() == cls) {
            r = cls.cast(mBody);
        }
        return r;
    }
}
