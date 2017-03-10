package suviano.countryexplorer.activities.countries;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import suviano.countryexplorer.R;
import suviano.countryexplorer.data.local.CountriesRepositoryLocal;
import suviano.countryexplorer.entities.Country;
import suviano.countryexplorer.utils.ReactiveEx;

public class CountriesVisitedFragment extends Fragment {

    RecyclerView recyclerView;
    CountriesRepositoryLocal countriesRepository;
    CountriesAdapter adapter;
    private Subscription subscription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.countriesRepository = CountriesRepositoryLocal.newInstance(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_countries, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_countries_list);
        recyclerView.setTag("fragment_visited_countries");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        searchCountries();
    }

    void refreshList(List<Country> countries) {
        adapter = new CountriesAdapter(
                getActivity(), countries, R.layout.countries_list_item_visited);
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
                .subscribe(this::refreshList);
    }
}
