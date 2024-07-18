package kr.spring.subscription.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Component;

@Component
public class GetDate {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);

    public java.sql.Date getDate() {
        // 원하는 날짜를 설정합니다. 여기서는 예제로 오늘 날짜를 사용합니다.
        String desiredDateStr = "2024-07-18"; // 원하는 날짜를 문자열로 지정합니다.
        try {
            // 문자열을 java.util.Date로 파싱합니다.
            Date utilDate = dateFormat.parse(desiredDateStr);
            // java.util.Date를 java.sql.Date로 변환합니다.
            return convertFromJAVADateToSQLDate(utilDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static java.sql.Date convertFromJAVADateToSQLDate(java.util.Date javaDate) {
        if (javaDate != null) {
            return new java.sql.Date(javaDate.getTime());
        }
        return null;
    }
}