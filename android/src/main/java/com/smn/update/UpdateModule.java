package com.smn.update;

import com.facebook.react.bridge.*;

import com.github.snowdream.android.app.*;
import java.util.Map;

/**
 * Created by wwm on 2016-10-28.
 */

public class UpdateModule extends ReactContextBaseJavaModule {
    public UpdateModule(ReactApplicationContext reactContext) {
        super(reactContext);

    }

    @Override
    public String getName() {
        return "RNUpdate";
    }

    @ReactMethod
    public  void  hasNewVersion(final Promise promise){

    }

    @ReactMethod
    public void update(String url) {
        UpdateManager manager = new UpdateManager(getCurrentActivity());
        UpdateOptions options = new UpdateOptions.Builder(getCurrentActivity())
                .checkUrl(url)
                .updateFormat(UpdateFormat.JSON)
                .updatePeriod(new UpdatePeriod(UpdatePeriod.EACH_TIME))
                .checkPackageName(true)
                .build();

        manager.check(getCurrentActivity(), options);
    }
}
