package suviano.countryexplorer.data;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import suviano.countryexplorer.entities.Country;

public class CountriesRepository implements Repository {

    private static CountriesRepository INSTANCE = null;

    @NonNull
    List<Country> countryList;
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
    public Observable<Country> getCountryFromMemory() {
        if (countryList.isEmpty()) {
            return Observable.from(countryList);
        }
        countryList.clear();
        return Observable.empty();
    }

    @Override
    public Observable<Country> getCountryFromNetwork() {
        Observable<Country> countryObservable = this.apiModuleForCountries
                .newApiServiceInstance().listCountries();
        return countryObservable.single(country -> true);
    }

    @Override
    public Observable<Country> getCountryData() {
        return getCountryFromMemory().switchIfEmpty(getCountryFromNetwork());
    }
}
