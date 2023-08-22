package ua.ros.spring.hotel.utils.validation;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GeneralValidator {
    /**
     * Check if user entered valid number.
     *
     * @param number
     *            user input to check
     * @return true if entered text is valid number, false otherwise
     */
    public boolean isValidNumber(final String number) {
        if (number == null || number.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    /**
     * Check if user entered valid number.
     *
     * @param number
     *            user input to check
     * @return true if entered text is valid number, false otherwise
     */
    public boolean isValidNumber(final Integer number) {
        if (number == null || number <= 0) {
            return false;
        }
        return true;
    }
    /**
     * Check if user entered valid number.
     *
     * @param number
     *            user input to check
     * @return true if entered text is valid number, false otherwise
     */
    public boolean isValidNumber(final Long number) {
        if (number == null || number <= 0) {
            return false;
        }
        return true;
    }
    /**
     * Check if user entered data has string format.
     *
     * @param string
     *            string to check
     * @return true, if string has valid format
     */
    public boolean isValidString(final String string) {
        if (string == null || string.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[a-zA-ZА-ЩЬЮЯҐІЇЄЫЭЪЁа-щьюяґіїєыэъё'\\s\\-]{3,}$");
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
    /**
     * Check if user entered data has description format.
     *
     * @param string
     *            string to check
     * @return true, if string has valid format
     */
    public boolean isValidDescription(final String string) {
        if (string == null || string.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[a-zA-ZА-ЩЬЮЯҐІЇЄЫЭЪЁа-щьюяґіїєыэъё\\d\\s\\-'.,;:+~`!?@#$^&*()={}|/]{3,}$");
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    /**
     * Check if user entered data has date format.
     *
     * @param date
     *            date to check
     * @return true, if data has date format
     */
    public boolean isValidDate(final String date) {
        if (date == null || date.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile(
                "^(?:19|20)[0-9]{2}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))$");
        Matcher matcher = pattern.matcher(date);

        return matcher.matches();
    }

    /**
     * Check if second period-of-time does not intersect first
     *
     * @param fCheckInData
     *             start of first period of time
     * @param fCheckOutData
     *              end of first period of time
     * @param sCheckInData
     *            start of second period of time
     * @param sCheckOutData
     *           end of second period of time
     * @return true, if second period does not intersect first
     */
    public boolean comparisonDataValidator(final Date fCheckInData, final Date fCheckOutData,
                                           final Date sCheckInData, final Date sCheckOutData) {
        LocalDate localFCheckInData = fCheckInData.toLocalDate();
        LocalDate localFCheckOutData = fCheckOutData.toLocalDate();
        LocalDate localSCheckInData = sCheckInData.toLocalDate();
        LocalDate localSCheckOutData = sCheckOutData.toLocalDate();
        //якщо дата виїзду раніше дати заїзду
        if (localSCheckOutData.isBefore(localSCheckInData))
        {
            return false;
        }
        //якщо дати співпадають
        if (localSCheckInData.equals(localFCheckInData)
                || localSCheckInData.equals(localFCheckOutData)
                || localSCheckOutData.equals(localFCheckInData)
                || localSCheckOutData.equals(localFCheckOutData))
        {
            return false;
        }
        //якщо дата заїзду в проміжку існуючого бронювання
        if (localSCheckInData.isAfter(localFCheckInData)
                && localSCheckInData.isBefore(localFCheckOutData))
        {
            return false;
        }
        //якщо дата виїзду в проміжку існуючого бронювання
        if (localSCheckOutData.isAfter(localFCheckInData)
                && localSCheckOutData.isBefore(localFCheckOutData))
        {
            return false;
        }
        //якщо існуюче бронювання в проміжку нового
        if (localSCheckInData.isBefore(localFCheckInData)
                && localSCheckOutData.isAfter(localFCheckOutData))
        {
            return false;
        }
        return true;
    }

    /**
     * Check if entered data isn`t less, than current data
     * @param CheckInData date to check
     * @return true, if data is`n belong to pastime
     */
    public boolean isDataInFutureTime(final Date CheckInData){
        Date currentTime = Date.valueOf(LocalDate.now());
        return !CheckInData.before(currentTime);
    }

    /**
     * Check whether string length greater than given length param.
     *
     * @param string
     *            string to check
     * @param length
     *            length
     * @return boolean
     */
    public boolean isValidLength(final String string, final int length) {
        if (string == null || string.isEmpty() || length <= 0) {
            return false;
        }
        return string.length() >= length;
    }

    /**
     * Check whether string length greater than given length param.
     *
     * @param string
     *            string to check
     * @param length
     *            length
     * @param maxlength
     *            max length
     * @return boolean
     */
    public boolean isValidLength(final String string, final int length, final int maxlength) {
        if (string == null || string.isEmpty() || length <= 0) {
            return false;
        }
        int size = string.length();
        return (size >= length) && (size <= maxlength);
    }

    /**
     * Check whether inputted string has valid email format
     *
     * @param email
     *            email to check
     * @return boolean
     */
    public boolean isValidEmail(final String email) {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
