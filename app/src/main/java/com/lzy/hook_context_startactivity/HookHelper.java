package com.lzy.hook_context_startactivity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

class HookHelper {

    /**
     * Hook ActivityThread类中的mInstrumentation字段
     */
    static void hookActivityThreadInstrumentation() {

        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            Object currentActivityThread = currentActivityThreadMethod.invoke(null);

            Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
            mInstrumentationField.setAccessible(true);
            Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(currentActivityThread);

            Instrumentation hookInstrumentation = new HookInstrumentation(mInstrumentation);

            mInstrumentationField.set(currentActivityThread, hookInstrumentation);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Hook Activity类中的mInstrumentation字段
     */
    static void hookActivityInstrumentation(Context context) {
        try {
            Field mInstrumentationField2 = Activity.class.getDeclaredField("mInstrumentation");
            mInstrumentationField2.setAccessible(true);
            Instrumentation mInstrumentation2 = (Instrumentation) mInstrumentationField2.get(context);

            Instrumentation hookInstrumentation2 = new HookInstrumentation2(mInstrumentation2);

            mInstrumentationField2.set(context, hookInstrumentation2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}