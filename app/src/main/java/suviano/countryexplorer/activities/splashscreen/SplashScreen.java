package suviano.countryexplorer.activities.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import suviano.countryexplorer.activities.countries.CountriesActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, CountriesActivity.class);
        startActivity(intent);
        finish();
    }
}
