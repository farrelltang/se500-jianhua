package uspstracking;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/23.
 */
public class TrackingInfo {

    public String carrier = "USPS";
    public String tracking_number;
    public String tracking_status;
    public List<TrackingHistory> tracking_historyList;
}
