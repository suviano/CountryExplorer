package suviano.countryexplorer.data.remote;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;
import suviano.countryexplorer.entities.Country;

interface CountriesApiService {
    @GET("/world/countries/active")
    Observable<List<Country>> activeCountries();
}
