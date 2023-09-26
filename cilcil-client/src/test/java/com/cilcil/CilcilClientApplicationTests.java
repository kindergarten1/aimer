package com.cilcil;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

//@SpringBootTest
class CilcilClientApplicationTests {


    @Test
    void contextLoads() {

        String sendOrgCode = "163";
        Integer b = 163;
        Integer[] a = {163,235,167};
        List<Integer> list = Arrays.asList(a);

        if(list.contains(b)){
            System.out.println(list);
        }
    }




}
