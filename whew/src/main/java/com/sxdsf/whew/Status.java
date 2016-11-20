package com.sxdsf.whew;

/**
 * com.sxdsf.whew.Status
 *
 * @author 孙博闻
 * @date 2016/7/13 20:15
 * @desc 任务的生命只有一次，所有的状态也只会出现一次，一般以ready开始，经历executed，
 * 以canceled，error，done其中的一个为结束
 */
public enum Status {

    /**
     * 准备状态，表明任务已经准备好，等待执行
     */
    READY,
    /**
     * 执行状态，表明任务正在执行
     */
    EXECUTED,
    /**
     * 取消状态，表明任务被取消了
     */
    CANCELED,
    /**
     * 错误状态，表明任务发生了错误
     */
    ERROR,
    /**
     * 完成状态，表明任务已经完成
     */
    DONE
}
