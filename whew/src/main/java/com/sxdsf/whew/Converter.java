package com.sxdsf.whew;

import com.sxdsf.echo.Caster;
import com.sxdsf.whew.info.Response;

/**
 * com.sxdsf.whew.Converter
 *
 * @author 孙博闻
 * @date 2016/7/15 10:16
 * @desc 整体变换
 */
public abstract class Converter<T> implements Caster.Converter<Response, Response> {
    @Override
    public Caster<Response> call(Caster<Response> responseCaster) {
        return convert((WhewCall<T>) responseCaster);
    }

    public abstract WhewCall<T> convert(WhewCall<T> whewCall);
}
