package com.company;


public class StrToBte {
    public static byte[] StrToBte(String pass){
        String fm = "%16s";
        String test= (String.format(fm,pass));
        byte[] b = test.getBytes();
        return b;

    }
}
