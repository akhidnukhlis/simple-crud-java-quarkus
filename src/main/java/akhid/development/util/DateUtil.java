package akhid.development.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {
    public static String convertFromLocalDateTimeToString(LocalDateTime dateFrom) {
        LocalDateTime ldt = dateFrom;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant()));
    }
}

