package uspstracking;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by jianhua on 2016/5/22.
 */

public class HtmlParser {

//get html source
    public Document getHtmlSource (String url){
        Document htmlDoc = null;
        Connection connection = Jsoup.connect(url);
        connection.userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36");
        try {
            htmlDoc = connection.followRedirects(true).get();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        return htmlDoc;
    }

//get tracking number
    public String getTrackingNumber(Document htmlDoc){
        String tracking_number = null;
        Elements elements = htmlDoc.getElementsByClass("tracking-number");
        Element element = elements.get(0);
        tracking_number = element.getElementsByClass("value").get(0).text();

        return tracking_number;
    }

//get tracking status
    public String getTrackingStatus (Document htmlDoc ){
//      Element tc = htmlDoc.getElementById("tc-hits");
//      String status = tc.getElementsByClass("info-text").get(0).text();
        String trackingStatus = htmlDoc.getElementById("tc-hits").getElementsByClass("info-text").get(0).text();

        return trackingStatus;
    }

//get tracking history
    public List<TrackingHistory> getTrackingHistory (Document htmlDoc) {
        List<TrackingHistory> tracking_historyList = new ArrayList<TrackingHistory>();

        Element details = htmlDoc.getElementsByClass("details").get(0);
        Elements detailsList = details.getElementsByClass("detail-wrapper");
//        System.out.println(detailsList.size());
          for(int i = 0; i<=detailsList.size()-1; i++){
            TrackingHistory trackingHistory = new TrackingHistory();

            String  locationString = detailsList.get(i).getElementsByClass("location").get(0).text();
//            Location location = stringToLocation(locationString);
//            trackingHistory.location = location;
              trackingHistory.location_str = locationString;

            String dateString = detailsList.get(i).getElementsByClass("date-time").get(0).text();
//            Date statusDate = stringToDate(dateString);
//            trackingHistory.status_date = statusDate;
              trackingHistory.status_date_str = dateString;

            trackingHistory.status_details = detailsList.get(i).getElementsByClass("info-text").get(0).text();

            tracking_historyList.add(trackingHistory);
          }
        return tracking_historyList;

    }

//format dateString to Date
    public Date formatDate (String dateString) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy MMMMM dd hh:mm aaa", Locale.US);
        String[] splitString = dateString.split(",");
        String temp = splitString[1];
        splitString[1] = splitString[0];
        splitString[0] = temp;
        String newDateString = splitString[0]+splitString[1]+splitString[2]+"";
        Date date = simpleDateFormat.parse(newDateString);
        return date;
     }

//format locationString to Location
    public Location formatLocation (String locationString){
        Location location = new Location();
        String[] splitString = locationString.split(",");
        location.city = splitString[0];
        location.state = splitString[1].replaceAll("[^a-zA-Z]","");
        location.zip = splitString[1].replaceAll("[^0-9]","");

        return location;
    }

//get trackingInfo
    public TrackingInfo getTrackingInfo (String url) {
        TrackingInfo trackingInfo = new TrackingInfo();

        return trackingInfo;
    }

}
