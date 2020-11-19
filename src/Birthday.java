import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Birthday {
    private String day;
    private String month;
    private String year;
    private Date date;
    private String birthday;
    private boolean isValid;

    public Birthday(String b){
        birthday = b;
        validateDate(birthday);
    }

    public void validateDate(String bDay){
        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
        String[] strings;
        // Check giving string matches d-m-yyyy or dd-mm-yyyy which are different date format
        if (bDay.matches("\\d+\\D\\d+\\D\\d+")){
            // Split match results by "-", "/" or other symbols
            strings = bDay.split("\\D");

            if (strings.length == 3){
                if (strings[0].length() == 1) strings[0] = "0" + strings[0];
                if (strings[1].length() == 1) strings[1] = "0" + strings[1];
                // give the bDay proper format: dd-mm-yyyy
                bDay = strings[0] + "-" + strings[1] + "-" + strings[2];
                day = strings[0];
                month = strings[1];
                year = strings[2];
                int dayInt = Integer.parseInt(strings[0]);
                int monthInt = Integer.parseInt(strings[1]);
                int yearInt = Integer.parseInt(strings[2]);

                // Validate the giving date legal or not
                if (dayInt < 1 || dayInt > 31){
                    isValid = false;
                }

                if (monthInt < 1 || monthInt > 12){
                    isValid = false;
                }

                if (yearInt < 1900 || yearInt > 2020){
                    isValid = false;
                }

            }
        } else {bDay = "00-00-0000"; isValid = false; }
        // Parse the string to date in order to further use and sort
        try{
            date = format.parse(bDay);
        }catch (ParseException parseException){
            date = null;
        }
    }

    // Check is date a valid date
    public boolean isValid(){
        return date != null && isValid;
    }

    public String dateString(){
        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
        return format.format(date);
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public Date getDate() {
        return date;
    }

    public String getBirthday() {
        return birthday;
    }
}
