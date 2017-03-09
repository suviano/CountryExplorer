package suviano.countryexplorer.activities.countries;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import suviano.countryexplorer.entities.Country;

import static suviano.countryexplorer.data.remote.FlagApi.loadFlag;

class CountriesAdapter extends RecyclerView.Adapter<CountriesViewHolder>
        implements View.OnClickListener {
    private List<Country> countries;
    private Context context;
    private CountryClickListener countryClickListener;
    private int country_list_item;

    CountriesAdapter(Context context, List<Country> countries, int country_list_item,
                     @NonNull CountryClickListener countryClickListener) {
        this.context = context;
        this.countries = countries;
        this.country_list_item = country_list_item;
        this.countryClickListener = countryClickListener;
    }

    @Override
    public CountriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(country_list_item, parent, false);
        CountriesViewHolder holder = new CountriesViewHolder(view);
        view.setTag(holder);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(CountriesViewHolder holder, int position) {
        Country country = countries.get(position);
        loadFlag(context, country.getIso(), holder.getFlagImg(), true);
        holder.getShortnameTxt().setText(country.getShortName());
    }

    @Override
    public int getItemCount() {
        return countries != null ? countries.size() : 0;
    }

    @Override
    public void onClick(View v) {
        if (countryClickListener != null) {
            CountriesViewHolder viewHolder = (CountriesViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            countryClickListener.countryInfo(position, countries.get(position));
        }
    }
}
