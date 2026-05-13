package com.example.ecc.api;


import android.util.Log;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.functions.Function;
import org.reactivestreams.Publisher;
import java.util.concurrent.TimeUnit;

// Change implementation to use Flowable and Publisher
public class ExponentialBackoffRetry implements Function<Flowable<Throwable>, Publisher<?>> {
    private static final String TAG = "ExponentialBackoffRetry";
    private final int maxRetries;
    private final int baseDelaySeconds;

    public ExponentialBackoffRetry(int maxRetries, int baseDelaySeconds) {
        this.maxRetries = maxRetries;
        this.baseDelaySeconds = baseDelaySeconds;
    }

    @Override
    public Publisher<?> apply(Flowable<Throwable> errors) {
        return errors
                .zipWith(Flowable.range(1, maxRetries + 1), (error, attempt) -> {
                    if (attempt > maxRetries) {
                        throw error; // Max retries reached, propagate error
                    }
                    return attempt;
                })
                .flatMap(attempt -> {
                    long delay = (long) Math.pow(baseDelaySeconds, attempt);
                    Log.w(TAG, "API request failed. Retrying in " + delay + " seconds (Attempt " + attempt + " of " + maxRetries + ").");

                    // Use Flowable.timer to maintain compatibility
                    return Flowable.timer(delay, TimeUnit.SECONDS);
                });
    }
}