package suviano.countryexplorer.activities.countries;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import suviano.countryexplorer.activities.country.CountryActivity;
import suviano.countryexplorer.R;
import suviano.countryexplorer.data.remote.CountriesRepository;
import suviano.countryexplorer.entities.Country;

public class CountriesListFragment extends Fragment implements CountryClickListener {

    RecyclerView recyclerView;

    List<Country> countries;

    CountriesRepository countriesRepository;

    private Subscription subscription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.countriesRepository = CountriesRepository.newInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_countries, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_countries_list);
        recyclerView.setTag("fragment_countries");
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (countries == null) {
            searchCountries();
        } else {
            refreshList(countries);
        }
    }

    public void refreshList(List<Country> countries) {
        CountriesAdapter adapter = new CountriesAdapter(getActivity(), countries);
        adapter.countryInfo(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unSubscribe();
    }

    public void unSubscribe() {
        if (subscription != null) {
            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }

    void searchCountries() {
        subscription = this.countriesRepository.getCountryData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::refreshList);
    }

    @Override
    public void countryInfo(View view, int position, Country country) {
        Intent intent = new Intent(getActivity(), CountryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("COUNTRY_VISIT", country);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
