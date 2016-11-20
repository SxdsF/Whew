package com.sxdsf.whew.sample;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sxdsf.whew.Call;
import com.sxdsf.whew.Callback;
import com.sxdsf.whew.Converter;
import com.sxdsf.whew.ThreadMode;
import com.sxdsf.whew.Whew;
import com.sxdsf.whew.WhewCall;
import com.sxdsf.whew.info.Response;
import com.sxdsf.whew.info.WhewResponse;
import com.sxdsf.whew.parser.StringParser;

public class MainActivity extends AppCompatActivity {

    private Button text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (Button) findViewById(R.id.text);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Whew whew = new Whew.Builder().build();
                whew.<String>get("https://www.baidu.com").
                        unify(new Converter<String>() {
                            @Override
                            public WhewCall<String> convert(WhewCall<String> whewCall) {
                                return whewCall.
                                        parser(new StringParser()).
                                        callbackOn(ThreadMode.MAIN);
                            }
                        }).
                        call(new Callback() {
                            @Override
                            public void onStart(Call call) {
                                System.out.println("onStart" + (Looper.getMainLooper() == Looper.myLooper()));
                            }

                            @Override
                            public void onError(Call call, Throwable t) {
                                System.out.println("onError" + (Looper.getMainLooper() == Looper.myLooper()));
                            }

                            @Override
                            public void onSuccess(Call call, Response response) {
                                WhewResponse<String> whewResponse = (WhewResponse<String>) response;
                                System.out.println(whewResponse.checkAndGet(String.class));
                                System.out.println("onSuccess" + (Looper.getMainLooper() == Looper.myLooper()) + call.request().mUrl + call.request().mMethod);
                            }

                            @Override
                            public void onCancel(Call call) {
                                System.out.println("onCancel" + (Looper.getMainLooper() == Looper.myLooper()));
                            }
                        });
//                Subscription s = Observable.
//                        create(new Observable.OnSubscribe<Integer>() {
//                            @Override
//                            public void call(Subscriber<? super Integer> subscriber) {
//                                if (!subscriber.isUnsubscribed()) {
//                                    subscriber.onNext(2);
//                                    subscriber.onCompleted();
//                                }
//                            }
//                        }).
//                        subscribeOn(Schedulers.newThread()).
//                        observeOn(AndroidSchedulers.mainThread()).
//                        map(new Func1<Integer, Integer>() {
//                            @Override
//                            public Integer call(Integer integer) {
//                                try {
//                                    Thread.sleep(4000);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                                return 3;
//                            }
//                        }).
//                        subscribe(new Subscriber<Integer>() {
//                            @Override
//                            public void onCompleted() {
//                                System.out.println("调用onCompleted");
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//
//                            }
//
//                            @Override
//                            public void onNext(Integer integer) {
//                                System.out.println("调用onNext" + integer);
//                            }
//                        });
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                s.unsubscribe();
            }
        });
    }
}
