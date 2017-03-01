package suviano.countryexplorer.activities.country;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import suviano.countryexplorer.R;
import suviano.countryexplorer.data.local.CountriesRepository;
import suviano.countryexplorer.entities.Country;

import static suviano.countryexplorer.data.remote.ApiModuleForCountries.BASE_URL;
import static suviano.countryexplorer.entities.Country.COUNTRY;

public class CountryActivity extends AppCompatActivity
        implements ActionMenuItemView.OnClickListener, DatePickerDialog.OnDateSetListener {

    private ActionMenuItemView saveVisit;
    private Country country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_country);
        setSupportActionBar(toolbar);

        country = getIntent().getParcelableExtra(COUNTRY);

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
        saveVisit = (ActionMenuItemView) findViewById(R.id.save_visit);
        saveVisit.setOnClickListener(this);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == saveVisit.getId()) {
            VisitDatePickerDialog visitDatePickerDialog = new VisitDatePickerDialog();
            visitDatePickerDialog.show(getSupportFragmentManager(), "visitDate");
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar visitDate = Calendar.getInstance();
        visitDate.set(year, month, dayOfMonth);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        dateFormat.setTimeZone(visitDate.getTimeZone());

        String visit = dateFormat.format(visitDate.getTime());
        country.setVisitDate(visit);

        CountriesRepository repository = new CountriesRepository(getApplicationContext());
        repository.save(country);

        Toast.makeText(this,
                "Visit to " + country.getShortName() + " saved!", Toast.LENGTH_SHORT).show();
    }
}
