package suviano.countryexplorer.data.remote;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import suviano.countryexplorer.entities.Country;

public class CountriesRepository implements Repository {

    private static CountriesRepository INSTANCE = null;

    @NonNull
    private List<Country> countryList;
    @NonNull
    private ApiModuleForCountries apiModuleForCountries;

    private CountriesRepository() {
        this.apiModuleForCountries = new ApiModuleForCountries();
        this.countryList = new ArrayList<>();
    }

    public static CountriesRepository newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CountriesRepository();
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<Country>> getCountryFromMemory() {
        if (countryList.isEmpty()) {
            return Observable.just(countryList);
        }
        countryList.clear();
        return Observable.empty();
    }

    @Override
    public Observable<List<Country>> getCountryFromNetwork() {
        Observable<List<Country>> countriesObservable = this.apiModuleForCountries
                .newApiServiceInstance().activeCountries();
        return countriesObservable.single(countries -> true);
    }

    @Override
    public Observable<List<Country>> getCountryData() {
        //return getCountryFromMemory().switchIfEmpty(getCountryFromNetwork());
        return getCountryFromNetwork();
    }
}
