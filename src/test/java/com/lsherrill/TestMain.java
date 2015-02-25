package com.lsherrill;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestMain {

    Main main = new Main();

    @Test
    public void lastThree() throws Exception {
        assertEquals("1", main.lastThree("1"));
        assertEquals("12", main.lastThree("12"));
        assertEquals("123", main.lastThree("123"));
        assertEquals("234", main.lastThree("1234"));
        assertEquals("345", main.lastThree("12345"));
        assertEquals("456", main.lastThree("123456"));
        assertEquals("567", main.lastThree("1234567"));
    }

    @Test
    public void chunkify1() throws Exception {
        List<String> list = main.chunkify("1");
        assertEquals(1, list.size());
        assertEquals("1", list.get(0));
    }

    @Test
    public void chunkify3() throws Exception {
        List<String> list = main.chunkify("123");
        assertEquals(1, list.size());
        assertEquals("123", list.get(0));
    }

    @Test
    public void chunkify4() throws Exception {
        List<String> list = main.chunkify("1234");
        assertEquals(2, list.size());
        assertEquals("1", list.get(0));
        assertEquals("234", list.get(1));
    }

    @Test
    public void chunkify13() throws Exception {
        List<String> list = main.chunkify("1234567890123");
        assertEquals(5, list.size());
        assertEquals("1", list.get(0));
        assertEquals("234", list.get(1));
        assertEquals("567", list.get(2));
        assertEquals("890", list.get(3));
        assertEquals("123", list.get(4));
    }

    @Test
    public void convertDecimal() throws Exception {
        assertEquals(" and 25/100", main.convertDecimal("25"));
    }

    @Test
    public void pronounceBase() throws Exception {
        assertEquals("", main.pronounceCluster(""));
        assertEquals("", main.pronounceCluster("0"));
        assertEquals("", main.pronounceCluster("00"));
        assertEquals("", main.pronounceCluster("000"));
        assertEquals("one", main.pronounceCluster("1"));
        assertEquals("one", main.pronounceCluster("01"));
        assertEquals("one", main.pronounceCluster("001"));
        assertEquals("ten", main.pronounceCluster("10"));
        assertEquals("twenty", main.pronounceCluster("20"));
        assertEquals("eleven", main.pronounceCluster("11"));
        assertEquals("twenty-two", main.pronounceCluster("22"));
        assertEquals("one hundred twenty-three", main.pronounceCluster("123"));
        assertEquals("one hundred one", main.pronounceCluster("101"));
    }

    @Test
    public void pronounce() throws Exception {
        assertEquals("zero", "Zero dollars", main.pronounce("0"));
        assertEquals("001", "One dollar", main.pronounce("001"));
        assertEquals("1", "One dollar", main.pronounce("1"));
        assertEquals("10", "Ten dollars", main.pronounce("10"));
        assertEquals("11", "Eleven dollars", main.pronounce("11"));
        assertEquals("20", "Twenty dollars", main.pronounce("20"));
        assertEquals("21", "Twenty-one dollars", main.pronounce("21"));
        assertEquals("100", "One hundred dollars", main.pronounce("100"));
        assertEquals("101", "One hundred one dollars", main.pronounce("101"));
        assertEquals("110", "One hundred ten dollars", main.pronounce("110"));
        assertEquals("190", "One hundred ninety dollars", main.pronounce("190"));
        assertEquals("1000", "One thousand dollars", main.pronounce("1000"));
        assertEquals("1234", "One thousand two hundred thirty-four dollars", main.pronounce("1234"));
        assertEquals("1,000,000,000", "One billion dollars", main.pronounce("1000000000"));
        assertEquals("1,000,000,000,001", "One trillion one dollars", main.pronounce("1000000000001"));
        assertEquals("1,234,567,890", "One billion two hundred thirty-four million five hundred sixty-seven thousand eight hundred ninety dollars", main.pronounce("1234567890"));

        assertEquals("One hundred one and 42/100 dollars", main.pronounce("101.42"));
        assertEquals("One hundred one and 02/100 dollars", main.pronounce("101.02"));

        assertEquals("Amount not recognized.", main.pronounce("101A.02"));
        assertEquals("Amount not recognized.", main.pronounce("101.2"));
        assertEquals("Amount not recognized.", main.pronounce("101.234"));
        assertEquals("Amount not recognized.", main.pronounce("10-1.234"));
        assertEquals("Negative amounts not allowed.", main.pronounce("-101"));
        assertEquals("Amount too large, quadrillion not supported.", main.pronounce("1000000000000000"));
    }
}
