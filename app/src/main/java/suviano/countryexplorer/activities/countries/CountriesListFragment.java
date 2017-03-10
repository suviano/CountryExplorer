package suviano.countryexplorer.activities.countries;

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

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import suviano.countryexplorer.R;
import suviano.countryexplorer.data.Repository;
import suviano.countryexplorer.data.remote.CountriesRepositoryRemote;
import suviano.countryexplorer.entities.Country;
import suviano.countryexplorer.utils.ReactiveEx;

public class CountriesListFragment extends Fragment {

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

    void refreshList(List<Country> countries) {
        this.countries = countries;
        adapter = new CountriesAdapter(getActivity(), countries, R.layout.country_list_item);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unSubscribe();
    }

    private void unSubscribe() {
        ReactiveEx.unSubscribe(subscription);
    }

    private void searchCountries() {
        subscription = this.countriesRepository.getCountries().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::refreshList,
                        throwable -> Log.wtf("COUNTRIES", throwable.getCause() + "::" + throwable.getMessage()),
                        () -> Toast.makeText(getActivity(), "Loading finished", Toast.LENGTH_SHORT).show()
                );
    }

}
