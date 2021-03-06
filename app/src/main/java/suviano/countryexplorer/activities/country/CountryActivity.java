package suviano.countryexplorer.activities.country;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import suviano.countryexplorer.R;
import suviano.countryexplorer.data.local.CountriesRepositoryLocal;
import suviano.countryexplorer.entities.Country;

import static suviano.countryexplorer.data.remote.FlagApi.loadFlag;
import static suviano.countryexplorer.entities.Country.COUNTRY;

public class CountryActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener {

    private Country country;
    private CountriesRepositoryLocal repository;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_country);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        repository =
                CountriesRepositoryLocal.newInstance(getApplicationContext());

        country = getIntent().getParcelableExtra(COUNTRY);

        ImageView imageView = (ImageView) findViewById(R.id.flag_country_img);
        TextView shortname = (TextView) findViewById(R.id.shortname_country_txt);
        shortname.setText(country.getShortName());
        TextView longname = (TextView) findViewById(R.id.longname_country_txt);
        longname.setText(country.getLongName());
        TextView callingCode = (TextView) findViewById(R.id.calling_code_country_txt);
        callingCode.setText(country.getCallingCode());
        loadFlag(getApplicationContext(), country.getIso(), imageView, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_country, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_visit) {
            VisitDatePickerDialog visitDatePickerDialog = new VisitDatePickerDialog();
            visitDatePickerDialog.show(getSupportFragmentManager(), "visitDate");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null) {
            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar visitDate = Calendar.getInstance();
        visitDate.set(year, month, dayOfMonth);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        dateFormat.setTimeZone(visitDate.getTimeZone());

        String visit = dateFormat.format(visitDate.getTime());

        subscription = repository
                .getCountry(this.country.getLongName())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(countryLocal -> {
                            if (countryLocal == null)
                                saveVisit(visit);
                        }
                );
    }

    private void saveVisit(String visit) {
        country.setVisitDate(visit);
        repository.saveCountry(country);
        Toast.makeText(this,
                "Visit to " + this.country.getShortName() + " saved!", Toast.LENGTH_SHORT).show();
    }
}
