package suviano.countryexplorer.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import suviano.countryexplorer.entities.Country;

public class CountriesRepositoryLocal {
    @Nullable
    private static CountriesRepositoryLocal INSTANCE;

    @NonNull
    private final BriteDatabase databaseHelper;

    @NonNull
    private Func1<Cursor, Country> countryMapperFunc;

    private CountriesRepositoryLocal(@NonNull Context context) {
        CountriesSQLHelper helper = new CountriesSQLHelper(context);
        SqlBrite sqlBrite = SqlBrite.create();
        databaseHelper = sqlBrite.wrapDatabaseHelper(helper, Schedulers.io());
        countryMapperFunc = this::getCountryCursor;
    }

    public static CountriesRepositoryLocal getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CountriesRepositoryLocal(context);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @NonNull
    private Country getCountryCursor(@NonNull Cursor cursor) {
        String idFlag = cursor.getString(cursor.getColumnIndexOrThrow(CountriesSQLHelper.FLAG_ID));
        String shortName = cursor.getString(
                cursor.getColumnIndexOrThrow(CountriesSQLHelper.SHORTNAME));
        String longName = cursor.getString(
                cursor.getColumnIndexOrThrow(CountriesSQLHelper.LONGNAME));
        String callingCode = cursor.getString(
                cursor.getColumnIndexOrThrow(CountriesSQLHelper.CALLINGCODE));
        String visitDate = cursor.getString(
                cursor.getColumnIndexOrThrow(CountriesSQLHelper.VISIT_DATE));
        return new Country(idFlag, shortName, longName, callingCode, visitDate);
    }

    public void saveCountry(@NonNull Country country) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CountriesSQLHelper.FLAG_ID, country.getId());
        contentValues.put(CountriesSQLHelper.STATUS, country.getStatus());
        contentValues.put(CountriesSQLHelper.SHORTNAME, country.getShortName());
        contentValues.put(CountriesSQLHelper.LONGNAME, country.getLongName());
        contentValues.put(CountriesSQLHelper.CALLINGCODE, country.getCallingCode());
        contentValues.put(CountriesSQLHelper.CULTURE, country.getCulture());
        contentValues.put(CountriesSQLHelper.ISO, country.getIso());
        contentValues.put(CountriesSQLHelper.VISIT_DATE, country.getVisitDate());

        databaseHelper.insert(CountriesSQLHelper.TABLE_COUNTRIES,
                contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Observable<Country> getCountry(@NonNull String countryLongname) {
        String query = String.format("SELECT * FROM %s as country WHERE country.%s = ? ",
                CountriesSQLHelper.TABLE_COUNTRIES,
                CountriesSQLHelper.LONGNAME);
        return databaseHelper
                .createQuery(CountriesSQLHelper.TABLE_COUNTRIES, query, countryLongname)
                .mapToOneOrDefault(countryMapperFunc, null);
    }

    public Observable<List<Country>> getCountries() {

        String query = String.format("SELECT %s, %s, %s, %s, %s FROM %s",
                CountriesSQLHelper.VISIT_DATE,
                CountriesSQLHelper.CALLINGCODE,
                CountriesSQLHelper.FLAG_ID,
                CountriesSQLHelper.LONGNAME,
                CountriesSQLHelper.SHORTNAME,
                CountriesSQLHelper.TABLE_COUNTRIES);
        return databaseHelper
                .createQuery(CountriesSQLHelper.TABLE_COUNTRIES, query)
                .mapToList(countryMapperFunc);
    }
}
