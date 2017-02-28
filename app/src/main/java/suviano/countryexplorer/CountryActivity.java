package suviano.countryexplorer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import suviano.countryexplorer.entities.Country;

import static suviano.countryexplorer.data.remote.ApiModuleForCountries.BASE_URL;
import static suviano.countryexplorer.entities.Country.VISIT;

public class CountryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_country);
        setSupportActionBar(toolbar);

        Country country = getIntent().getParcelableExtra(VISIT);

        ImageView imageView = (ImageView) findViewById(R.id.flag_country_img);
        TextView shortname = (TextView) findViewById(R.id.shortname_country_txt);
        shortname.setText(country.getShortName());
        TextView longname = (TextView) findViewById(R.id.longname_country_txt);
        longname.setText(country.getLongName());
        TextView callingCode = (TextView) findViewById(R.id.calling_code_country_txt);
        callingCode.setText(country.getCallingCode());
        String flagUrl = country.getFlagUrl(BASE_URL);
        Picasso.with(this.getApplicationContext())
                .load(flagUrl).into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_country, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
