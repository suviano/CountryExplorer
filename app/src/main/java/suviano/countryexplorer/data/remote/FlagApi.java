package suviano.countryexplorer.data.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import suviano.countryexplorer.R;

public class FlagApi {
    private static final String flagBaseUrl = "https://flagspot.net/images/%s/%s.gif";
    private static final String TAG = FlagApi.class.getSimpleName();

    public static void loadFlag(@NonNull Context context, @NonNull String isoCode,
                                @NonNull ImageView image, boolean resize) {
        RequestCreator error = Picasso.with(context).load(flagUrl(isoCode))
                .placeholder(R.drawable.flag_placeholder).error(R.drawable.flag_placeholder);
        if (resize) {
            error = error.resize(200, 150).centerInside();
        }
        error.into(image);
    }

    private static String flagUrl(String isoCode) {
        String flagUrl = "";
        try {
            flagUrl = String.format(flagBaseUrl,
                    isoCode.substring(0, 1), isoCode);
        } catch (Exception ex) {
            Log.wtf(TAG, ex.getCause());
        }
        return flagUrl.toLowerCase();
    }
}
