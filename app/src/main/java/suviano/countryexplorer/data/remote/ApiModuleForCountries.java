package suviano.countryexplorer.data.remote;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiModuleForCountries {

    public static final String BASE_URL = "http://sslapidev.mypush.com.br";

    private OkHttpClient httpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient().newBuilder().addInterceptor(interceptor).build();
    }

    private Retrofit newRetrofitInstance(String baseURL, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseURL).client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    CountriesApiService newApiServiceInstance() {
        return newRetrofitInstance(BASE_URL, httpClient()).create(CountriesApiService.class);
    }
}
