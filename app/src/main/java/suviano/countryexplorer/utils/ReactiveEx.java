package suviano.countryexplorer.utils;

import rx.Subscription;

public class ReactiveEx {

    public static void unSubscribe(Subscription subscription) {
        if (subscription != null) {
            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }
}
