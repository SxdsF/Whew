package com.sxdsf.whew;

/**
 * com.sxdsf.whew.Adapter
 *
 * @author 孙博闻
 * @date 2016/7/14 16:03
 * @desc 结果适配器
 */
public interface Adapter<T extends WhewCall<?>, R> {

    /**
     * 把系统默认的适配成T
     *
     * @param whewCall 系统默认的调用
     * @return
     */
    R adapt(T whewCall);
}
