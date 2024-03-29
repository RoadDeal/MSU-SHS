/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.tools;

import java.io.File;

/**
 *
 * @author ABDUL
 */
public class FormValidation {

    public static boolean validateFirstName(String firstName){
        return firstName.matches( "([A-Z][a-z]+)+([ '-][A-Z][a-z]+)*" );
    }

    public static boolean validateLastName(String lastName){
        return lastName.matches( "([A-Z][a-z]+)+([ '-]([A-Z][a-z]+|[A-Z]+))*");
    }

    public static boolean validateMiddleName(String middleName){
        return middleName.matches( "([A-Z][a-z]+)+([ '-][A-Z][a-z]+)*" );
    }

    public static boolean validateAddress(String address){
        return address.matches( "([A-Z][a-z]+|\\d+)([ ',-]([a-zA-z]+[.,-]*|\\d+[.,-]*)*)*");
    }

    public static boolean validateStringField(String string){
        return string.matches( "[a-zA-z]+([ '-][a-zA-Z]+)*" );
    }

    public static boolean validateImageFileName(File file){
        return (file.isFile() && (file.getName().toString().lastIndexOf(".jpg")>0 || file.getName().toString().lastIndexOf(".png")>0));
    }

    public static boolean validateNumberField(String number){
        return number.matches( "\\d+([.]\\d+)*" );
    }

}
