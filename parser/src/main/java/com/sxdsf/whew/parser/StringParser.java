package com.sxdsf.whew.parser;

import com.sxdsf.whew.Parser;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * com.sxdsf.whew.parser.StringParser
 *
 * @author 孙博闻
 * @date 2016/7/15 11:02
 * @desc 字符串的解析
 */
public class StringParser implements Parser<ResponseBody, String> {
    @Override
    public String parse(ResponseBody responseBody) throws IOException {
        String result = null;
        if (responseBody != null) {
            try {
                result = responseBody.string();
            } finally {
                responseBody.close();
            }

        }
        return result;
    }
}
