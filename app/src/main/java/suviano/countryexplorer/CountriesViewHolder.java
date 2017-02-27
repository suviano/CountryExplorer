package suviano.countryexplorer;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

class CountriesViewHolder extends RecyclerView.ViewHolder {
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

    void setFlagImg(ImageView flagImg) {
        this.flagImg = flagImg;
    }

    TextView getShortnameTxt() {
        return shortnameTxt;
    }

    void setShortnameTxt(TextView shortnameTxt) {
        this.shortnameTxt = shortnameTxt;
    }
}
