package com.sxdsf.whew.parser;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.sxdsf.whew.Parser;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * com.sxdsf.whew.parser.GsonParser
 *
 * @author 孙博闻
 * @date 2016/7/15 11:02
 * @desc google的gson的解析
 */
public class GsonParser<T> implements Parser<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    public GsonParser(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T parse(ResponseBody responseBody) throws IOException {
        T t = null;
        if (responseBody != null) {
            JsonReader jsonReader = gson.newJsonReader(responseBody.charStream());
            try {
                t = adapter.read(jsonReader);
            } finally {
                responseBody.close();
            }
        }
        return t;
    }
}
