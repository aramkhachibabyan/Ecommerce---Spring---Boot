package com.smartCode.ecommerce.util.constants;

import org.aspectj.util.IStructureModel;

public interface Message {
    String USERNAME_IS_NOT_AVAILABLE = "This username isn't available. Please try another";
    String PASSWORD_MATCHING = "Password does not match";
    String EMAIL_IS_NOT_AVAILABLE = "This email isn't available. Please try another";
    String PHONE_NUMBER_IS_NOT_AVAILABLE = "This phone number isn't available. Please try another";
    String INVALID_OLD_PASSWORD = "Invalid old password";
    String INVALID_CODE = "Invalid code";
    String INVALID_PASSWORD = "Invalid password";
    String USER_IS_NOT_VERIFIED = "Please verify first";
    String INVALID_CARD_NUMBER = "Invalid card number";

    static String userNotFound(Integer id){
        return String.format("User with id: %d not found",id);
    }
    static String userNotFound(String email){
        return String.format("User with email: %s not found",email);
    }
    static String productNotFound(Integer id){
        return String.format("Product with id: %d not found",id);
    }
}
