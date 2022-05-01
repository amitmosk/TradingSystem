package Domain.Utils;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class Utils {

    public static String DateToString(LocalDate d){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        return formatter.format(d);
    }

    public static Date StringToDate(String s) {
        Date output = null;
        try{
            output = new SimpleDateFormat("dd/MM/yyyy").parse(s);
        }
        catch (Exception e){

        }
        return output;
    }

    public static String LocalDateToString(LocalDate d){
        return d.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static Date LocalDateToDate(LocalDate d){
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return Date.from(d.atStartOfDay(defaultZoneId).toInstant());
    }

    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
    /**
     * Checks if a given string represents a number that is {@code 0<number<bound}
     * @param number - the string that represents the number
     * @param bound - the upper bound
     * @return the number that the string represents if the condition stands, or -1 otherwise
     */
    public static int checkIfInBounds(String number,int bound) {
        try {
            int num = Integer.parseInt(number);
            return num > 0 && num < bound ? num : -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Prints the message of the response in case of an exception or a success message
     * @param response response object
     * @param successMessage the success message to print
     */
    public static void printMessageOrSuccess(Response<? extends Object> response, String successMessage) {
        if(response.WasException())
            System.out.println(response.getMessage());
        else
            System.out.println(successMessage);
    }

    /**
     * Prints an error message in case of an error issued with the response object
     * if no error, prints the value issued with the response object
     * @param response reponse object
     * @param <T> The type of value the response object wraps
     */
    public static <T> void printErrorMessageOrListOfValues(Response<List<T>> response) {
        if (response.WasException())
            System.out.println(response.getMessage());
        else {
            for (T elem : response.getValue()) {
                System.out.println(elem + "\n");
            }
        }
    }


    public static void emailCheck(String email) throws Exception {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            throw new Exception("Email cannot be null");
        if (!pat.matcher(email).matches())
            throw new Exception("Invalid email");
    }

    public static void passwordCheck(String pw)throws Exception{
        final int MinPasswordLength = 6;
        final int MaxPasswordLength = 12;
        boolean containsNum = false;
        boolean containsUpper = false;
        boolean containsLower = false;
        if(pw.length() < MinPasswordLength || pw.length() > MaxPasswordLength)
            throw new Exception("password length should be in range of 6-12");
        char[] pwArray = pw.toCharArray();
        for (char c : pwArray) {
            if (c >= '0' & c <= '9')
                containsNum = true;
            else if (c >= 'a' & c <= 'z')
                containsLower = true;
            else if (c >= 'A' & c <= 'Z')
                containsUpper = true;
            else
                throw new Exception("password should only upper & lower letter and digit");
        }
        if(!(containsLower && containsUpper && containsNum))
            throw new Exception("password should contain at least one upper & lower letter, and digit");
    }

    public static void nameCheck(String name) throws Exception {
        final int MaxNamesLength = 10;
        if (name == null || name.equals(""))
            throw new Exception("Name cannot be null or empty spaces");
        //checks length of the name
        if (name.length() > MaxNamesLength)
            throw new Exception("Name length is too long");
        //check if contains only letters
        char[] arrayName = name.toLowerCase().toCharArray();
        for (char c : arrayName) {
            if (c < 'a' || c > 'z')
                throw new Exception("The name must contain letters only");
        }
    }




}
