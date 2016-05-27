package uspstracking;

import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/5/23.
 */
//I didn't get all the  tracking information from html.
// And when doing Test,I didn't use shippoAPI to compare,I just type in the expected result when doing Assert.
public class HtmlParserTest {

    @Test
    public void htmlParser() throws Exception {
        HtmlParser htmlParser = new HtmlParser();
        Document htmlDoc = htmlParser.getHtmlSource("https://tools.usps.com/go/TrackConfirmAction?qtc_tLabels1=9200199999977453249942");

        String htmlString;
        htmlString = htmlDoc.toString();
        System.out.println(htmlString);
    }

    @Test
    public void trackingNumberTest() throws Exception {
        HtmlParser htmlParser = new HtmlParser();
        Document htmlDoc = htmlParser.getHtmlSource("https://tools.usps.com/go/TrackConfirmAction?qtc_tLabels1=9200199999977453249942");
        TrackingInfo trackingInfo = new TrackingInfo();
        trackingInfo.tracking_number = htmlParser.getTrackingNumber(htmlDoc);
//        System.out.println(trackingInfo.tracking_number);
        Assert.assertEquals(trackingInfo.tracking_number,"9200199999977453249942");
    }

    @Test
    public void trackingStatusTest() throws Exception {
        HtmlParser htmlParser = new HtmlParser();
        Document htmlDoc = htmlParser.getHtmlSource("https://tools.usps.com/go/TrackConfirmAction?qtc_tLabels1=9200199999977453249942");
        TrackingInfo trackingInfo = new TrackingInfo();
        trackingInfo.tracking_status = htmlParser.getTrackingStatus(htmlDoc);
//        System.out.println(trackingInfo.tracking_status);
        Assert.assertEquals(trackingInfo.tracking_status,"Delivered, PO Box");
    }

    @Test
    public void trackingHistoryTest() throws Exception {
        HtmlParser htmlParser = new HtmlParser();
        TrackingInfo trackingInfo = new TrackingInfo();
        List<TrackingHistory> tracking_historyList = new ArrayList<TrackingHistory>();
        Document htmlDoc = htmlParser.getHtmlSource("https://tools.usps.com/go/TrackConfirmAction?qtc_tLabels1=9200199999977453249942");
        trackingInfo.tracking_historyList = htmlParser.getTrackingHistory(htmlDoc);
//        System.out.println(trackingInfo.tracking_historyList.size());
        Assert.assertEquals(trackingInfo.tracking_historyList.size(),6);
//     get history list
//        for (int i = 0; i <= trackingInfo.tracking_historyList.size() - 1; i++) {
//            System.out.println(trackingInfo.tracking_historyList.get(i).location_str);
//            System.out.println(trackingInfo.tracking_historyList.get(i).status_date_str);
//            System.out.println(trackingInfo.tracking_historyList.get(i).status_details);
//        }
    }

    @Test
    public void dateFormatTest ()throws Exception{
        HtmlParser htmlParser = new HtmlParser();
        Date status_date = htmlParser.formatDate("April 4, 2016 , 8:20 am");
        String formatDate = status_date.toString();
        Assert.assertEquals(formatDate,"Mon Apr 04 08:20:00 PDT 2016");

            }

    @Test
    public void locationFormatTest ()throws Exception {
          HtmlParser htmlParser = new HtmlParser();
          Location location = htmlParser.formatLocation("TAPPAHANNOCK, VA 22560");
//          System.out.println(location.city);
          Assert.assertEquals(location.city,"TAPPAHANNOCK");
//          System.out.println(location.country);
          Assert.assertEquals(location.country,"US");
//          System.out.println(location.state);
          Assert.assertEquals(location.state,"VA");
//          System.out.println(location.zip);
          Assert.assertEquals(location.zip,"22560");
        }

    }



