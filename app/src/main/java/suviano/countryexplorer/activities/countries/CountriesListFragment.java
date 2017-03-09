package suviano.countryexplorer.activities.countries;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import suviano.countryexplorer.R;
import suviano.countryexplorer.activities.country.CountryActivity;
import suviano.countryexplorer.data.Repository;
import suviano.countryexplorer.data.remote.CountriesRepositoryRemote;
import suviano.countryexplorer.entities.Country;

public class CountriesListFragment extends Fragment implements CountryClickListener {

    RecyclerView recyclerView;
    Repository countriesRepository;
    CountriesAdapter adapter;
    private List<Country> countries;
    private Subscription subscription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.countriesRepository = CountriesRepositoryRemote.newInstance();
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

    @Override
    public void countryInfo(int position, Country country) {
        Intent intent = new Intent(getActivity(), CountryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("COUNTRY_VISIT", country);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    void refreshList(List<Country> countries) {
        adapter = new CountriesAdapter(getActivity(), countries, R.layout.country_list_item, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unSubscribe();
    }

    private void unSubscribe() {
        if (subscription != null) {
            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }

    private void searchCountries() {
        subscription = this.countriesRepository.getCountries().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Country>>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(getActivity(), "Loading finished", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.wtf("Contries list fragment", e.getCause() + "::" + e.getMessage());
                    }

                    @Override
                    public void onNext(List<Country> countries) {
                        refreshList(countries);
                    }
                });
    }

}
