package com.upgrad.technical.service.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public class AuthenticationFailedException extends Exception{
    private final String code;
    private final String errorMessage;

    //Generate the constructor for this class with both the attributes as arguments to
    //set private final String code to the "code" parameter passed into the constructor
    //and private final String errorMessage to the "errorMessage" parameter passed into the constructor
    public AuthenticationFailedException(String code,String errorMessage){
        this.code=code;
        this.errorMessage=errorMessage;
    }


    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    @Override
    public  void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
    }

    @Override
    public  void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
    }

    public String getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}