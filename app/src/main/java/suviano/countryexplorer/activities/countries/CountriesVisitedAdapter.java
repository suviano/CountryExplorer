package suviano.countryexplorer.activities.countries;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import suviano.countryexplorer.R;
import suviano.countryexplorer.data.local.CountriesRepositoryLocal;
import suviano.countryexplorer.entities.Country;

import static suviano.countryexplorer.data.remote.FlagApi.loadFlag;

class CountriesVisitedAdapter extends CountriesAdapter implements EventsAdapter {


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
        Country country = countries.get(position);

        CountriesViewHolder countriesViewHolder = (CountriesViewHolder) holder;

        loadFlag(context, country.getIso(), countriesViewHolder.getFlagImg(), true);
        countriesViewHolder.getShortnameTxt().setText(country.getShortName());
        countriesViewHolder.itemView.setOnClickListener(v -> countryInfo(position));
        countriesViewHolder.getDeleteVisit().setOnClickListener(v -> deleteItem(position));
    }

    @Override
    public void onItemDismiss(int position) {
        deleteItem(position);
    }

    private void deleteItem(int position) {

        boolean b = CountriesRepositoryLocal.newInstance(context)
                .deleteCountry(countries.get(position).getId());
        if (b) {
            countries.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, countries.size());
        } else {
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
