package suviano.countryexplorer.data;

import java.util.List;

import rx.Observable;
import suviano.countryexplorer.entities.Country;

public interface Repository {
    Observable<List<Country>> getCountries();
}
