package suviano.countryexplorer;

import android.view.View;

import suviano.countryexplorer.entities.Country;

interface CountryClickListener {
    void countryInfo(View view, int position, Country country);
}
