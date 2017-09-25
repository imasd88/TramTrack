package au.com.realestate.hometime.Helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Utils {


    /////////////
    // Convert .NET Date to Date
    ////////////
    private Date dateFromDotNetDate(String dotNetDate) {

        int startIndex = dotNetDate.indexOf("(") + 1;
        int endIndex = dotNetDate.indexOf("+");
        String date = dotNetDate.substring(startIndex, endIndex);

        Long unixTime = Long.parseLong(date);
        return new Date(unixTime);
    }

    public String simpleDate(String rawTime) {

        Date unixTime = dateFromDotNetDate(rawTime); //convert rawTime into readable unixTime
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mma", Locale.US); //create object of SimpleDateFormat

        StringBuilder simpleTime = new StringBuilder();
        simpleTime.append(simpleDateFormat.format(unixTime)); //convert unixTime to xx:xx AM/PM

        long currentDate = System.currentTimeMillis(); //get current local time in milliseconds
        long diffTime =
                unixTime.getTime() - currentDate; //calculate the time difference in milliseconds
        long remainingTime =
                TimeUnit.MINUTES.convert(diffTime, TimeUnit.MILLISECONDS); //convert the time in milliseconds to minutes

        if (remainingTime > 60) { //if time difference is more than 60min
            long remainingHours = remainingTime / 60; //convert to hour
            long remainingMins = remainingTime % 60; //convert to min
            simpleTime.append(" (").append(remainingHours).append("hr").append(remainingMins).append("min)"); //append time as (xxhrxxmin) to simpleTime
        } else { //else
            simpleTime.append(" (").append(remainingTime).append("min)"); //append time as (xxmin)
        }

        return simpleTime + "";
    }


}
