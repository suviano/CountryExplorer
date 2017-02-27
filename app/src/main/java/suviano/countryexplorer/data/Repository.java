package suviano.countryexplorer.data;

import java.util.List;

import rx.Observable;
import suviano.countryexplorer.entities.Country;

interface Repository {

    Observable<List<Country>> getCountryFromMemory();

    Observable<List<Country>> getCountryFromNetwork();

    Observable<List<Country>> getCountryData();
}
