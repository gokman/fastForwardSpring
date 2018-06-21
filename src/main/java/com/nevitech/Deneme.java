package com.nevitech;

import com.nevitech.db.DbProcess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Deneme {

    public static void main(String args[]) throws IOException {
        List<String> denemelist = new ArrayList<>();
        denemelist.add("ali");
        denemelist.add("ali");
        denemelist.add("eve");
        denemelist.add("evden");
        denemelist.add("okuldan");

        DbProcess dbProcess = new DbProcess();

        Map<String, Integer> resultMap = dbProcess.getStemAndCount(denemelist);

        //System.out.println(resultMap.get("ali"));

        //resultMap.forEach((k,v)->System.out.println("Item : " + k + " Count : " + v));
    }
}
