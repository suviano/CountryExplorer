package suviano.countryexplorer.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CountriesSQLHelper extends SQLiteOpenHelper {

    public static final String TABLE_COUNTRIES;

    public static final String ID;

    public static final String FLAG_ID;
    public static final String ISO;
    public static final String SHORTNAME;
    public static final String LONGNAME;
    public static final String CALLINGCODE;
    public static final String STATUS;
    public static final String CULTURE;
    public static final String VISIT_DATE;

    private static final int VERSION;
    private static final String DATABASE_NAME;

    static {
        DATABASE_NAME = "world";
        VERSION = 1;
        TABLE_COUNTRIES = "country";
        ID = "_id";
        FLAG_ID = "flagId";
        ISO = "iso";
        SHORTNAME = "shortname";
        LONGNAME = "longname";
        CALLINGCODE = "callingCode";
        STATUS = "status";
        CULTURE = "culture";
        VISIT_DATE = "visitDate";
    }

    public CountriesSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE '%s' ('%s' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "'%s' TEXT NOT NULL,'%s' TEXT NOT NULL, '%s' TEXT NOT NULL, " +
                        "'%s' TEXT NOT NULL, '%s' TEXT NOT NULL, '%s' TEXT NOT NULL, " +
                        "'%s' TEXT NOT NULL, %s TEXT NOT NULL )",
                TABLE_COUNTRIES, ID, FLAG_ID, ISO, SHORTNAME,
                LONGNAME, CALLINGCODE, STATUS, CULTURE, VISIT_DATE));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
