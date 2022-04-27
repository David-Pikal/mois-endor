package cz.uhk.mois.endor.planservice.planservice.util;

import java.sql.Date;

public class DateParser {

    public static Date formatDate(String dateString) {
        Date endD = null;
        if (!dateString.equals("")) {
            endD = Date.valueOf(dateString);
        }

        return endD;
    }
}
