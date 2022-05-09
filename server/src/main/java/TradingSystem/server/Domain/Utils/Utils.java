package TradingSystem.server.Domain.Utils;


import TradingSystem.server.Domain.Utils.Exception.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Utils {

    public static String DateToString(LocalDate d) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        return formatter.format(d);
    }

    public static Response CreateResponse(Exception e) {
        if (e instanceof AdminException)
            return new Response(e, "this action wont work because of admin rules. ");
        if (e instanceof AlreadyRegisterdException)
            return new Response(e, "there is a user who is register to the system from this network right now.");
        if (e instanceof AppointmentException)
            return new Response(e, "this action wont work because of appointment rules");
        if (e instanceof BasketException)
            return new Response(e, "cant preform this action on the shopping basket.");
        if (e instanceof LoginException)
            return new Response(e, "cant login.");
        if (e instanceof NoPremssionException)
            return new Response(e, "you dont have permission to do this action.");
        if (e instanceof NoUserRegisterdException)
            return new Response(e, "there is no user currently connected to the system.");
        if (e instanceof ObjectDoesntExsitException)
            return new Response(e, "the object doesnt Exists.");
        if (e instanceof ProductAddingException)
            return new Response(e, "cant add the product.");
        if (e instanceof ProductCreatingException)
            return new Response(e, "cant create the product.");
        if (e instanceof PurchaseException)
            return new Response(e, "cant preform the purchase.");
        if (e instanceof RegisterException)
            return new Response(e, "cant register to the system.");
        if (e instanceof SecuirtyException)
            return new Response(e, "this action violate our security protocols please try again.");
        if (e instanceof ShippingException)
            return new Response(e, "cant ship to the desired address");
        if (e instanceof StoreException)
            return new Response(e, "this action wont work because of appointment rules");
        if (e instanceof StoreMethodException)
            return new Response(e, "this action wont work because of store rules");
        if (e instanceof UserExcpetion)
            return new Response(e, "this action wont work because of user ruels");
        if (e instanceof UserNeverBoughtInTheStoreException)
            return new Response(e, "the user never bought from the store");
        if (e instanceof WrongPermterException)
            return new Response(e, "wrong parameter entered. ");
        return new Response(e, "the action didnt worked,try again");
    }

    public static Date StringToDate(String s) {
        Date output = null;
        try {
            output = new SimpleDateFormat("dd/MM/yyyy").parse(s);
        } catch (Exception e) {

        }
        return output;
    }

    public static String LocalDateToString(LocalDate d) {
        return d.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static Date LocalDateToDate(LocalDate d) {
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
     *
     * @param number - the string that represents the number
     * @param bound  - the upper bound
     * @return the number that the string represents if the condition stands, or -1 otherwise
     */
    public static int checkIfInBounds(String number, int bound) {
        try {
            int num = Integer.parseInt(number);
            return num > 0 && num < bound ? num : -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Prints the message of the response in case of an exception or a success message
     *
     * @param response       response object
     * @param successMessage the success message to print
     */
    public static void printMessageOrSuccess(Response<? extends Object> response, String successMessage) {
        if (response.WasException())
            System.out.println(response.getMessage());
        else
            System.out.println(successMessage);
    }

    /**
     * Prints an error message in case of an error issued with the response object
     * if no error, prints the value issued with the response object
     *
     * @param response reponse object
     * @param <T>      The type of value the response object wraps
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


    public static void emailCheck(String email) throws MarketException {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            throw new WrongPermterException("Email cannot be null");
        if (!pat.matcher(email).matches())
            throw new WrongPermterException("Invalid email");
    }

    public static void passwordCheck(String pw) throws MarketException {
        final int MinPasswordLength = 6;
        final int MaxPasswordLength = 12;
        boolean containsNum = false;
        boolean containsUpper = false;
        boolean containsLower = false;
        if (pw.length() < MinPasswordLength || pw.length() > MaxPasswordLength)
            throw new SecuirtyException("password length should be in range of 6-12");
        char[] pwArray = pw.toCharArray();
        for (char c : pwArray) {
            if (c >= '0' & c <= '9')
                containsNum = true;
            else if (c >= 'a' & c <= 'z')
                containsLower = true;
            else if (c >= 'A' & c <= 'Z')
                containsUpper = true;
            else
                throw new SecuirtyException("password should only upper & lower letter and digit");
        }
        if (!(containsLower && containsUpper && containsNum))
            throw new SecuirtyException("password should contain at least one upper & lower letter, and digit");
    }

    public static void nameCheck(String name) throws MarketException {
        final int MaxNamesLength = 10;
        if (name == null || name.equals(""))
            throw new UserExcpetion("Name cannot be null or empty spaces");
        //checks length of the name
        if (name.length() > MaxNamesLength)
            throw new UserExcpetion("Name length is too long");
        //check if contains only letters
        char[] arrayName = name.toLowerCase().toCharArray();
        for (char c : arrayName) {
            if (c < 'a' || c > 'z')
                throw new UserExcpetion("The name must contain letters only");
        }
    }

    /**
     * one-way encryption technique
     */
    public static String gen_pass(String password) {
        /* Plain-text password initialization. */
        String encryptedpassword = null;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            /* Add plain-text password bytes to digest using MD5 update() method. */
            m.update(password.getBytes());
            /* Convert the hash value into bytes */
            byte[] bytes = m.digest();
            /* The bytes array has bytes in decimal form. Converting it into hexadecimal format. */
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            /* Complete hashed password in hexadecimal format */
            encryptedpassword = s.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        /* Display the unencrypted and encrypted passwords. */
        return encryptedpassword;
    }


}
