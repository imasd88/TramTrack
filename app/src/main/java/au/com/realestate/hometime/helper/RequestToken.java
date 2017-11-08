package au.com.realestate.hometime.helper;

import au.com.realestate.hometime.models.ApiResponse;
import au.com.realestate.hometime.models.Token;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RequestToken {

    String BASE_URL = "http://ws3.tramtracker.com.au";
    TramsApi service = ServiceFactory.createRetrofitService(TramsApi.class, BASE_URL);

    public RequestToken(final RequestTrams.TramsListener listener) {
        service.token()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<ApiResponse<Token>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                listener.onError(e.getMessage());
                            }

                            @Override
                            public void onNext(ApiResponse<Token> tramApiResponse) {
                                if (tramApiResponse.hasError) {
                                    listener.onError(tramApiResponse.errorMessage);
                                    return;
                                }
                                listener.onTokenRefreshed(tramApiResponse.responseObject.get(0).value);
                            }
                        }
                );
    }
}
