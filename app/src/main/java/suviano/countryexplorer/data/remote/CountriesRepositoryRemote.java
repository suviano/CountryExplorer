package suviano.countryexplorer.data.remote;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;
import suviano.countryexplorer.data.Repository;
import suviano.countryexplorer.entities.Country;

public class CountriesRepositoryRemote implements Repository {

    private static CountriesRepositoryRemote INSTANCE = null;

    @NonNull
    private ApiModuleForCountries apiModuleForCountries;

    private CountriesRepositoryRemote() {
        this.apiModuleForCountries = new ApiModuleForCountries();
    }

    public static CountriesRepositoryRemote newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CountriesRepositoryRemote();
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<Country>> getCountries() {
        Observable<List<Country>> countriesObservable = this.apiModuleForCountries
                .newApiServiceInstance().activeCountries();
        return countriesObservable.single(countries -> true);
    }
}
