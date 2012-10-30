package com.corntree.milpa.fly.api.rest.packet.gson;

import com.google.gson.Gson;

public class GsonFactory {

    private static ThreadLocal<Gson> gsonThreadLocal = new ThreadLocal<Gson>() {
        protected Gson initialValue() {
            return new Gson();
        };
    };

    public static Gson getThreadLocalGson() {
        return gsonThreadLocal.get();
    }
}
