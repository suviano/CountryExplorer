package suviano.countryexplorer.data;

import rx.Observable;
import suviano.countryexplorer.entities.Country;

public interface Repository {

    Observable<Country> getCountryFromMemory();

    Observable<Country> getCountryFromNetwork();

    Observable<Country> getCountryData();
}
