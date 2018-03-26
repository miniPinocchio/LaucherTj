package com.anrongtec.laucher.netconfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 *
 * @author huiliu
 * @date 2017/10/10
 *
 * @email liu594545591@126.com
 * @introduce
 */
public final class ApiServiceSosFactory {

    public static final String SOS_HOST = "http://20.3.2.47:8080";//正式环境
//    public static final String SOS_HOST = "http://20.3.2.47:8091";//测试环境

    private final NetInterface mApiService;

    private ApiServiceSosFactory() {
        Retrofit mRetrofit = new Retrofit.Builder()
                //添加网络请求的基地址
                .baseUrl(SOS_HOST)
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                //添加转换工厂，用于解析json并转化为javaBean
                .addConverterFactory(GsonConverterFactory.create())
                .client(DataLayer.getClient())
                .build();
        mApiService = mRetrofit.create(NetInterface.class);
    }



    private static class ApiServiceSOSFactoryHolder {
        private static final ApiServiceSosFactory INSTANCE = new ApiServiceSosFactory();
    }



    /**
     * 获取 ApiService 对象
     *
     * @return Api 接口对象
     */
    public static NetInterface getApi() {
        return ApiServiceSOSFactoryHolder.INSTANCE.mApiService;
    }


}
