package com.company;

public class Main {

    public static void main(String[] args) throws Exception {
        String cipher = AESEncryption.encrypt("I am having a great day");
        System.out.println("String encrypted = " + cipher);
        String decrypt = AESEncryption.decrypt(cipher);
        System.out.println("String Decrypted = " + decrypt);
    }
}


