package au.com.realestate.hometime.models;

import com.squareup.moshi.Json;

public class Tram {
    public static final int HEADER_TYPE = 0;
    public static final int TRAM_TYPE = 1;
    @Json(name = "Destination")
    public String destination;
    @Json(name = "PredictedArrivalDateTime")
    public String predictedArrival;
    @Json(name = "RouteNo")
    public String routeNo;
    private int mType = 1;
    private String direction;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getType() {
        return mType;
    }

    public void setType(int mType) {
        this.mType = mType;
    }
}
