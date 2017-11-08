package au.com.realestate.hometime.helper;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by Ahmed on 11/2/2017.
 */

class ServiceFactory {
    public static <T> T createRetrofitService(final Class<T> clazz, final String endpoint) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endpoint)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        T service = retrofit.create(clazz);
        return service;
    }
}
