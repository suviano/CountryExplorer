package suviano.countryexplorer.activities.countries;

import android.view.View;

import suviano.countryexplorer.entities.Country;

interface CountryClickListener {
    void countryInfo(View view, int position, Country country);
}
