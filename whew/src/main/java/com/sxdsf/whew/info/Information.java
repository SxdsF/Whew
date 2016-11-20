package com.sxdsf.whew.info;

/**
 * com.sxdsf.whew.info.Information
 *
 * @author 孙博闻
 * @date 2016/7/14 15:09
 * @desc 信息接口
 */
public interface Information {

    /**
     * 检查类型并返回相应信息
     *
     * @param cls 检查的类型
     * @param <T>
     * @return
     */
    <T> T checkAndGet(Class<T> cls);
}
