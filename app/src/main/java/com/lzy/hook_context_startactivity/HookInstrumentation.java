package com.lzy.hook_context_startactivity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * 静态代理类
 */
class HookInstrumentation extends Instrumentation {

    private static final String TAG = "HookInstrumentation";

    private Instrumentation originalObject;

     HookInstrumentation(Instrumentation originalObject) {
        this.originalObject = originalObject;
    }

    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token, Activity target, Intent intent, int requestCode, Bundle options) {
        Log.i(TAG, "调用Application startActivity");

        try {
            Method execStartActivity = Instrumentation.class.getDeclaredMethod("execStartActivity", Context.class, IBinder.class, IBinder.class, Activity.class, Intent.class, int.class, Bundle.class);
            execStartActivity.setAccessible(true);
            return (ActivityResult) execStartActivity.invoke(originalObject, who, contextThread, token, target, intent, requestCode, options);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("error!");
        }
    }

}