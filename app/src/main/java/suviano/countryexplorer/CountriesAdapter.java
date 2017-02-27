package suviano.countryexplorer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import suviano.countryexplorer.entities.Country;

import static suviano.countryexplorer.data.ApiModuleForCountries.BASE_URL;

class CountriesAdapter extends RecyclerView.Adapter<CountriesViewHolder> implements View.OnClickListener {
    private List<Country> countries;
    private Context context;

    CountriesAdapter(Context context, List<Country> countries) {
        this.context = context;
        this.countries = countries;
    }

    @Override
    public CountriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.countrie_list_item, parent, false);
        CountriesViewHolder holder = new CountriesViewHolder(view);
        view.setTag(holder);
        //view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(CountriesViewHolder holder, int position) {
        Country country = countries.get(position);
        String path = String.format("world/countries/%s/flag", country.getCallingCode());
        Picasso.with(context).load(BASE_URL + path)
                .resize(60, 60).centerCrop().into(holder.getFlagImg());
        holder.getShortnameTxt().setText(country.getShortName());
    }

    @Override
    public int getItemCount() {
        return countries != null ? countries.size() : 0;
    }

    @Override
    public void onClick(View v) {

    }
}