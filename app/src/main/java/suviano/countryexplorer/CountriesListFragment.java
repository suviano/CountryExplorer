package suviano.countryexplorer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import suviano.countryexplorer.data.CountriesRepository;
import suviano.countryexplorer.entities.Country;

public class CountriesListFragment extends Fragment {

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
        recyclerView = (RecyclerView) view.findViewById(R.id.movies_list);
        recyclerView.setTag("fragment_countries");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (countries == null) {
            searchCountries();
        }
        /*else {
            refreshList(countries);
        }*/
    }

    public void refreshList(List<Country> countries) {
        CountriesAdapter adapter = new CountriesAdapter(getActivity(), countries);
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
        Observable<List<Country>> countryData = this.countriesRepository.getCountryData();
        subscription = countryData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::refreshList);
    }

}
