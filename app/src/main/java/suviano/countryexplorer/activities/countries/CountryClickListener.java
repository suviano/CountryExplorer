package suviano.countryexplorer.activities.countries;

import suviano.countryexplorer.entities.Country;

interface CountryClickListener {
    void countryInfo(int position, Country country);
}
