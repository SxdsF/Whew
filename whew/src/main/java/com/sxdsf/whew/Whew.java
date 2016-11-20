package com.sxdsf.whew;

import com.sxdsf.whew.info.Method;

import okhttp3.OkHttpClient;

/**
 * com.sxdsf.whew.Whew
 *
 * @author 孙博闻
 * @date 2016/6/8 11:59
 * @desc 本网络模块的主类
 */
public class Whew {

    private final okhttp3.OkHttpClient mClient;

    private Whew(okhttp3.OkHttpClient client) {
        mClient = client;
    }

    public static class Builder {
        private okhttp3.OkHttpClient mClient;

        public Builder client(okhttp3.OkHttpClient client) {
            if (client == null) {
                throw new NullPointerException("client == null");
            }
            mClient = client;
            return this;
        }

        public Whew build() {
            if (mClient == null) {
                mClient = new okhttp3.OkHttpClient();
            }
            return new Whew(mClient);
        }
    }

    public <T> WhewCall<T> post(String url) {
        return WhewCall.<T>create().url(url).method(Method.POST).factory(new OkHttpClient());
    }

    public <T> WhewCall<T> get(String url) {
        return WhewCall.<T>create().url(url).method(Method.GET).factory(new OkHttpClient());
    }
}
