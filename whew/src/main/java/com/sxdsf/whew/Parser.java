package com.sxdsf.whew;

import java.io.IOException;

/**
 * com.sxdsf.whew.Parser
 *
 * @author 孙博闻
 * @date 2016/7/15 10:49
 * @desc 解析接口
 */
public interface Parser<T, R> {

    /**
     * 解析类
     *
     * @param t 要被解析的内容
     * @return
     */
    R parse(T t) throws IOException;
}
