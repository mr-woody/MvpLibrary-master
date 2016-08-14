package com.woodys.core.control;


import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by malk on 16/7/25.
 */

public class ActionManager {


    public static final String TAG = ActionManager.class.getSimpleName();
    private static ActionManager instance;

    private Map<String, TreeSet<Action>> mActionMap = new HashMap<>();

    public static ActionManager getInstance() {
        if (instance == null) {
            instance = new ActionManager();
        }
        return instance;
    }

    public void addAction(String tag, Action action) {
        TreeSet<Action> actions = mActionMap.get(tag);
        if (actions == null) {
            actions = new TreeSet<>();
            mActionMap.put(tag, actions);
        }
        actions.add(action);

    }

    public void executeAction(String tag) {
        executeAction(tag, null);
    }

    public void executeAction(String tag, Subscriber<String> subscriber) {
        TreeSet<Action> actions = mActionMap.get(tag);
        for (Action action : actions) {
            Log.d(TAG, "methodName=" + action.getMethodName());
        }
        if (actions == null) {
            return;
        }
        Observable.from(actions).map(action -> {
            Class[] paramsClass = null;
            if (action.getParams() != null) {
                paramsClass = new Class[action.getParams().length];
                for (int i = 0, j = action.getParams().length; i < j; i++) {
                    paramsClass[i] = action.getParams()[i].getClass();
                }
            }
            try {
                Method method = action.getObject().getClass().getMethod(action.getMethodName(), paramsClass);
                method.invoke(action.getObject(), action.getParams());
                return " execute " + action.getMethodName() + " success ";
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                return e.getMessage();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                return e.getMessage();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return e.getMessage();

            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                if (subscriber != null)
                    subscriber.onCompleted();
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                if (subscriber != null)
                    subscriber.onError(e);
                Log.e(TAG, "onError" + e.getMessage());

            }

            @Override
            public void onNext(String s) {
                if (subscriber != null)
                    subscriber.onNext(s);
                Log.d(TAG, "onNext" + s);

            }
        });
    }


    public class Action implements Comparable<Action> {


        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public Object[] getParams() {
            return params;
        }

        public void setParams(Object[] params) {
            this.params = params;
        }

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }

        private Object object;
        private String methodName;
        private Object[] params;
        private Integer order;//执行顺序

        public Action(Object object, String methodName, Object[] params, Integer order) {
            this.object = object;
            this.methodName = methodName;
            this.params = params;
            this.order = order;
        }

        @Override
        public int compareTo(Action another) {
            return order.compareTo(another.order);
        }
    }

}
