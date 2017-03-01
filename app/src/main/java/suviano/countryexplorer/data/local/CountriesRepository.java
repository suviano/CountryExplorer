package suviano.countryexplorer.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import suviano.countryexplorer.entities.Country;

public class CountriesRepository {
    private CountriesSQLHelper helper;

    public CountriesRepository(Context context) {
        this.helper = new CountriesSQLHelper(context);
    }

    private long insert(Country country) {
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CountriesSQLHelper.FLAG_ID, country.getId());
        contentValues.put(CountriesSQLHelper.STATUS, country.getStatus());
        contentValues.put(CountriesSQLHelper.SHORTNAME, country.getShortName());
        contentValues.put(CountriesSQLHelper.LONGNAME, country.getLongName());
        contentValues.put(CountriesSQLHelper.CALLINGCODE, country.getCallingCode());
        contentValues.put(CountriesSQLHelper.CULTURE, country.getCulture());
        contentValues.put(CountriesSQLHelper.ISO, country.getIso());
        contentValues.put(CountriesSQLHelper.VISIT_DATE, country.getVisitDate());
        long id = database.insert(CountriesSQLHelper.TABLE_COUNTRIES, null, contentValues);
        if (id != -1) {
            country.setDbId(id);
        }
        database.close();
        return id;
    }

    public void save(Country country) {
        if (country.getDbId() == 0) {
            insert(country);
        }
    }

    public int delete(Country country) {
        SQLiteDatabase database = helper.getWritableDatabase();
        int affected = database.delete(CountriesSQLHelper.TABLE_COUNTRIES,
                CountriesSQLHelper.ID + " = ?",
                new String[]{String.valueOf(country.getDbId())});
        database.close();
        return affected;
    }

    public List<Country> getCountries() {
        SQLiteDatabase database = helper.getReadableDatabase();
        String query = String.format("SELECT * FROM %s", CountriesSQLHelper.TABLE_COUNTRIES);
        Cursor cursor = database.rawQuery(query, null);
        List<Country> countries = new ArrayList<>();
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(CountriesSQLHelper.ID));
            String flagId = cursor.getString(cursor.getColumnIndex(CountriesSQLHelper.FLAG_ID));
            String status = cursor.getString(cursor.getColumnIndex(CountriesSQLHelper.STATUS));
            String shortName = cursor.getString(
                    cursor.getColumnIndex(CountriesSQLHelper.SHORTNAME));
            String longName = cursor.getString(
                    cursor.getColumnIndex(CountriesSQLHelper.LONGNAME));
            String callingCode = cursor.getString(
                    cursor.getColumnIndex(CountriesSQLHelper.CALLINGCODE));
            String culture = cursor.getString(cursor.getColumnIndex(CountriesSQLHelper.CULTURE));
            String iso = cursor.getString(cursor.getColumnIndex(CountriesSQLHelper.ISO));
            String visitDate = cursor.getString(
                    cursor.getColumnIndex(CountriesSQLHelper.VISIT_DATE));
            countries.add(new Country(
                    flagId, iso, shortName, longName, callingCode, status, culture, visitDate, id));
        }
        cursor.close();
        database.close();
        return countries;
    }
}
