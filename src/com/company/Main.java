package com.company;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

class MyThread extends Thread {
    private char _start;
    private char _end;
    private static String[] _hashes = new String[] {
            "1115dd800feaacefdf481f1f9070374a2a81e27880f187396db67958b207cbad",
            "3a7bd3e2360a3d29eea436fcfb7e44c735d117c42d1c1835420b6b9942dd4f1b",
            "74e1bb62f8dabb8125a58852b63bdf6eaef667cb56ac7f7cdba6d7305c50a22f"
    };

    private final char START = 'a';
    private final char END = 'z';

    public MyThread(char startCh, char endCh) {
        this._start = startCh;
        this._end = endCh;
    }

    public void run() {
        String password = String.valueOf(_start).repeat(5);
        char[] passwordArr = password.toCharArray();

        for (char i = _start; i < _end; i++) {
            passwordArr[0] = i;

            for (char j = START; j <= END; j++) {
                passwordArr[1] = j;

                for (char k = START; k <= END; k++) {
                    passwordArr[2] = k;

                    for (char m = START; m <= END; m++) {
                        passwordArr[3] = m;

                        for (char n = START; n <= END; n++) {
                            passwordArr[4] = n;

                            String passwordHash = convertToHash(new String(passwordArr));
//                            if (passwordHash.equals(_hashes[0]) | passwordHash.equals(_hashes[1]) | passwordHash.equals(_hashes[2])) {
                            if (Arrays.stream(_hashes).anyMatch(hash -> hash.equals(passwordHash))) {
                                System.out.println(new String(passwordArr) + " - " + passwordHash);
                            }
                        }
                    }
                }
            }
        }
    }

    public static String convertToHash(String str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[]hashInBytes = md.digest(str.getBytes(StandardCharsets.UTF_8));

        //bytes to hex
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        MyThread myThread1 = new MyThread('a', 'g');
        MyThread myThread2 = new MyThread('g', 'n');
        MyThread myThread3 = new MyThread('n', 't');
        MyThread myThread4 = new MyThread('t', (char) ('z' + 1));

        myThread1.start();
        myThread2.start();
        myThread3.start();
        myThread4.start();
        myThread1.join();
        myThread2.join();
        myThread3.join();
        myThread4.join();

        long finish = System.currentTimeMillis();
        System.out.println((finish - start) / 1000 + "s.");
    }
}
