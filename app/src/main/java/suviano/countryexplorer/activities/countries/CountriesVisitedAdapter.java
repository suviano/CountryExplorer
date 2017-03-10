package suviano.countryexplorer.activities.countries;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import suviano.countryexplorer.R;
import suviano.countryexplorer.data.local.CountriesRepositoryLocal;
import suviano.countryexplorer.entities.Country;

import static suviano.countryexplorer.data.remote.FlagApi.loadFlag;

class CountriesVisitedAdapter extends CountriesAdapter implements EventsAdapter {

    boolean deletingVisit;
    SparseBooleanArray selectedCountries = new SparseBooleanArray();

    CountriesVisitedAdapter(Context context, List<Country> countries, int country_list_item) {
        super(context, countries, country_list_item);
    }

    @Override
    public CountriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(country_list_item, parent, false);
        CountriesViewHolder holder = new CountriesViewHolder(view);
        view.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        deletingVisit = selectedCountries.size() > 0;

        Country country = countries.get(position);
        CountriesViewHolder countriesViewHolder = (CountriesViewHolder) holder;

        loadFlag(context, country.getIso(), countriesViewHolder.getFlagImg(), true);
        countriesViewHolder.getShortnameTxt().setText(country.getShortName());

        defineDelete(position, country.isSelected(), countriesViewHolder);

        countriesViewHolder.itemView.setOnClickListener(v -> countryInfo(position));

        countriesViewHolder.itemView.setOnLongClickListener(v -> {
            if (selectedCountries.get(position)) {
                selectedCountries.put(position, false);
                v.setBackgroundColor(Color.WHITE);
            } else {
                selectedCountries.put(position, true);
                v.setBackgroundColor(Color.parseColor("#FF455296"));

            }
            deletingVisit = selectedCountries.size() > 0;
            defineDelete(position, selectedCountries.get(position), countriesViewHolder);
            return true;
        });
    }

    @Override
    public void onItemDismiss(int position) {
        deleteItem(position);
    }

    private void defineDelete(int position, boolean selected, CountriesViewHolder view) {
        if (selected) {
            view.getDeleteVisit().setOnClickListener(v -> deleteItems());
        } else {
            view.getDeleteVisit().setVisibility(View.VISIBLE);
            view.getDeleteVisit().setOnClickListener(v -> deleteItem(position));
        }
    }

    private void deleteItem(int position) {
        String id = countries.get(position).getId();
        if (!CountriesRepositoryLocal.newInstance(context).deleteCountry(id)) {
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteItems() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < selectedCountries.size(); i++) {
            items.add(countries.get(selectedCountries.keyAt(i)).getId());
        }
        if (!CountriesRepositoryLocal.newInstance(context).deleteCountries(items)) {
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        }
    }

    private static class CountriesViewHolder extends CountriesAdapter.CountriesViewHolder {
        private ImageView deleteVisit;

        CountriesViewHolder(View itemView) {
            super(itemView);
            deleteVisit = (ImageView) itemView.findViewById(R.id.delete_visit);
        }

        ImageView getDeleteVisit() {
            return deleteVisit;
        }
    }

}
