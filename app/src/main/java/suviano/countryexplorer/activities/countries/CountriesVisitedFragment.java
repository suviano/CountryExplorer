package suviano.countryexplorer.activities.countries;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import suviano.countryexplorer.R;
import suviano.countryexplorer.data.local.CountriesRepositoryLocal;
import suviano.countryexplorer.entities.Country;

public class CountriesVisitedFragment extends CountriesListFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.countriesRepository = CountriesRepositoryLocal.newInstance(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_countries, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_countries_list);
        recyclerView.setTag("fragment_countries_visited");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    @Override
    public void refreshList(List<Country> countries) {
        adapter = new CountriesAdapter(
                getActivity(), countries, R.layout.countries_list_item_visited, this);
        recyclerView.setAdapter(adapter);
    }
}
