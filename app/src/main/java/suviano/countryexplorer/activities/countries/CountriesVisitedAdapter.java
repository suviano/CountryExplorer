package suviano.countryexplorer.activities.countries;

import android.content.Context;
import android.graphics.Color;
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

        defineDelete(position, country.isSelected(), countriesViewHolder);

        countriesViewHolder.itemView.setOnClickListener(v -> countryInfo(position));
        countriesViewHolder.itemView.setOnLongClickListener(v -> {
            countries.get(position).setSelected(!countries.get(position).isSelected());
            v.setBackgroundColor(
                    countries.get(position).isSelected() ?
                            Color.parseColor("#FF455296") :
                            Color.WHITE
            );
            defineDelete(position, country.isSelected(), countriesViewHolder);
            return true;
        });
    }

    @Override
    public void onItemDismiss(int position) {
        deleteItem(position);
    }

    private void deleteItem(int position) {
        String id = countries.get(position).getId();

        countries.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, countries.size());

        if (!CountriesRepositoryLocal.newInstance(context).deleteCountry(id)) {
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        }
    }

    private void defineDelete(int position, boolean selected, CountriesViewHolder view) {
        if (selected) {
            view.getDeleteVisit().setVisibility(View.GONE);
        } else {
            view.getDeleteVisit().setVisibility(View.VISIBLE);
            view.getDeleteVisit().setOnClickListener(v -> deleteItem(position));
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
