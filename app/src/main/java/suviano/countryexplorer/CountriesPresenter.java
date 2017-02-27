package suviano.countryexplorer;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import suviano.countryexplorer.data.CountriesRepository;
import suviano.countryexplorer.entities.Country;

public class CountriesPresenter {
    @NonNull
    CountriesRepository countriesRepository;
    @Nullable
    Subscription subscription;

    public CountriesPresenter(@NonNull CountriesRepository countriesRepository) {
        this.countriesRepository = countriesRepository;
    }

    public void getCountries() {
        Observable<Country> countryData = countriesRepository.getCountryData();
        subscription = countryData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Country>() {
                    @Override
                    public void onCompleted() {
                        Log.wtf("COUNTRIES", "DONE");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Country country) {
                        Log.wtf("COUNTRIES", country.getLongName());
                    }
                });
    }

    public void unSubscribe() {
        if (subscription != null) {
            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }
}
