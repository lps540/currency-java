package com.lsherrill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    Map<String, String> onesMap = new HashMap();
    Map<String, String> tensMap = new HashMap();
    Map<String, String> teensMap = new HashMap();
    Map<Integer, String> clusterMap = new HashMap();

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    public Main() {
        initialize();
    }

    public void run() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            System.out.println("\nEnter number (q to quit): ");
            String currency = bufferedReader.readLine();
            if (currency.equals("q")) {
                System.out.println("Finished. Thank you.");
                System.exit(0);
            }
            System.out.println(pronounce(currency));
        }
    }

    /*
     * Top of parser.
     */
    public String pronounce(String currency) {
        String s = currency.replaceAll(",", "");

        String error = validate(s);
        if (error != null) {
            return error;
        }

        int index = s.indexOf('.');
        String firstPart = (index > -1) ? s.substring(0, index) : s;
        String decimalPart = (index > -1) ? s.substring(index+1, index + 3) : "";

        String t = pronounce1(chunkify(firstPart)).trim() + convertDecimal(decimalPart);

        if (t.equals("")) {
            return "Zero dollars";
        } else if (t.equals("one")) {
            return "One dollar";
        } else {
            return t.substring(0, 1). toUpperCase() + t.substring(1) + " dollars";
        }
    }

    String pronounce1(List<String> list) {
        int size = list.size();

        if (size == 1) {
            return pronounceBase(list.get(0));
        } else if (list.get(0).equals("000")) {
            return pronounceBase(list.get(0)) + pronounce1(list.subList(1, size));
        } else {
            return pronounceBase(list.get(0)) +  " " + clusterMap.get(size) + " " + pronounce1(list.subList(1, size));
        }
    }

    /*
     * Pronounce a three-digit cluster
     */
    String pronounceBase(String s) {
        switch (s.length()) {
            case 3:
                if (s.substring(0, 1).equals("0")) {
                    return pronounceBase(s.substring(1));
                } else {
                    return onesMap.get(s.substring(0, 1)) + " hundred " + pronounceBase(s.substring(1));
                }
            case 2:
                if (s.substring(0, 1).equals("0")) {
                    return pronounceBase(s.substring(1));
                } else if (s.substring(0, 1).equals("1")) {
                    return teensMap.get(s.substring(1, 2));
                } else if (s.substring(1).trim().equals("0")) {
                    return tensMap.get(s.substring(0, 1)) + pronounceBase(s.substring(1));
                } else {
                    return tensMap.get(s.substring(0, 1)) + "-" + pronounceBase(s.substring(1));
                }

            case 1:
                return onesMap.get(s);
        }
        return "";
    }

    /*
     * Break currency string into 3-digit clusters
     */
    List<String> chunkify(String s) {
        List list = chunkify1(new ArrayList(), s);
        Collections.reverse(list);
        return list;
    }

    List<String> chunkify1(List<String> list, String s) {
        list.add(lastThree(s));
        if (s.length() > 3) {
            chunkify1(list, s.substring(0, s.length() - 3));
        }
        return list;
    }

    String lastThree(String s) {
        return (s.length() < 4) ? s : s.substring(s.length() - 3, s.length());
    }

    String convertDecimal(String s) {
        return (s.equals("")) ? "" : " and " + s + "/" + 100;
    }

    private String validate(String s) {
        if (s.startsWith("-")) {
            return "Negative amounts not allowed.";
        } else if (!s.matches("\\d*(\\.\\d\\d)?")) {
            return "Amount not recognized.";
        } else if (s.length() > 13) {
            return "Amount too large, quadrillion not supported.";
        }
        return null;
    }

    private void initialize() {
        onesMap.put("0", "");
        onesMap.put("1", "one");
        onesMap.put("2", "two");
        onesMap.put("3", "three");
        onesMap.put("4", "four");
        onesMap.put("5", "five");
        onesMap.put("6", "six");
        onesMap.put("7", "seven");
        onesMap.put("8", "eight");
        onesMap.put("9", "nine");

        teensMap.put("0", "ten");
        teensMap.put("1", "eleven");
        teensMap.put("2", "twelve");
        teensMap.put("3", "thirteen");
        teensMap.put("4", "fourteen");
        teensMap.put("5", "fifteen");
        teensMap.put("6", "sixteen");
        teensMap.put("7", "seventeen");
        teensMap.put("8", "eighteen");
        teensMap.put("9", "nineteen");

        tensMap.put("0", "");
        tensMap.put("2", "twenty");
        tensMap.put("3", "thirty");
        tensMap.put("4", "forty");
        tensMap.put("5", "fifty");
        tensMap.put("6", "sixty");
        tensMap.put("7", "seventy");
        tensMap.put("8", "eighty");
        tensMap.put("9", "ninety");

        clusterMap.put(1, "");
        clusterMap.put(2, "thousand");
        clusterMap.put(3, "million");
        clusterMap.put(4, "billion");
        clusterMap.put(5, "trillion");
    }
}
