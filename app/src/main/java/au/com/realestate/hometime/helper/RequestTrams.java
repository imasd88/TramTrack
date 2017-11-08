package au.com.realestate.hometime.helper;

import java.util.ArrayList;
import java.util.List;

import au.com.realestate.hometime.models.ApiResponse;
import au.com.realestate.hometime.models.Tram;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RequestTrams {
    String stopId = "4050";
    String BASE_URL = "http://ws3.tramtracker.com.au";
    TramsApi service = ServiceFactory.createRetrofitService(TramsApi.class, BASE_URL);

    public RequestTrams(String token, final boolean northTrams, final TramsListener listener) {
        if (!northTrams)
            stopId = "4060";
        service.getSchedule(stopId, token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<ApiResponse<Tram>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                listener.onError(e.getMessage());
                            }

                            @Override
                            public void onNext(ApiResponse<Tram> tramApiResponse) {
                                if (tramApiResponse.hasError) {
                                    listener.onError(tramApiResponse.errorMessage);
                                    return;
                                }
                                Tram tram = new Tram();
                                tram.setType(Tram.HEADER_TYPE);
                                tram.setDirection(northTrams ? "North" : "South");
                                List<Tram> tramList = new ArrayList<Tram>();
                                tramList.add(tram);
                                tramList.addAll(tramApiResponse.responseObject != null ? tramApiResponse.responseObject : new ArrayList<Tram>());
                                listener.onTramsLoaded(tramList, northTrams);
                            }
                        }
                );
    }


    public interface TramsListener {
        void onTokenRefreshed(String token);

        void onTramsLoaded(List<Tram> mTrams, boolean northTrams);

        void onError(String error);
    }
}
