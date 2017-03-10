package suviano.countryexplorer.activities.countries;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import suviano.countryexplorer.R;
import suviano.countryexplorer.activities.country.CountryActivity;
import suviano.countryexplorer.entities.Country;

import static suviano.countryexplorer.data.remote.FlagApi.loadFlag;

class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder> {
    private List<Country> countries;
    private Context context;
    private int country_list_item;

    CountriesAdapter(Context context, List<Country> countries, int country_list_item) {
        this.context = context;
        this.countries = countries;
        this.country_list_item = country_list_item;
    }

    @Override
    public CountriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(country_list_item, parent, false);
        CountriesViewHolder holder = new CountriesViewHolder(view);
        view.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(CountriesViewHolder holder, int position) {
        Country country = countries.get(position);
        loadFlag(context, country.getIso(), holder.getFlagImg(), true);
        holder.getShortnameTxt().setText(country.getShortName());
        holder.itemView.setOnClickListener(v -> countryInfo(country));
    }

    private void countryInfo(Country country) {
        Intent intent = new Intent(context, CountryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("COUNTRY_VISIT", country);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return countries != null ? countries.size() : 0;
    }

    static class CountriesViewHolder extends RecyclerView.ViewHolder {
        private ImageView flagImg;
        private TextView shortnameTxt;

        CountriesViewHolder(View itemView) {
            super(itemView);
            flagImg = (ImageView) itemView.findViewById(R.id.flag_img);
            shortnameTxt = (TextView) itemView.findViewById(R.id.shortname_txt);
            ViewCompat.setTransitionName(flagImg, "flag");
            ViewCompat.setTransitionName(shortnameTxt, "shortname");
        }

        ImageView getFlagImg() {
            return flagImg;
        }

        TextView getShortnameTxt() {
            return shortnameTxt;
        }
    }
}
