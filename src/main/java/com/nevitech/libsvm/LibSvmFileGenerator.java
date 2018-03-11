package com.nevitech.libsvm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

@Component
public class LibSvmFileGenerator {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static final String query_46_features = " \n " +
            "select class_assignee,\n" +
            "\t   product_cat,\n" +
            "\t   component_cat,\n" +
            "\t   resolution_cat,\n" +
            "\t   flag_cat,\n" +
            "\t   hardware_cat,\n" +
            "\t   keywords_cat,\n" +
            "\t   priority_cat,\n" +
            "\t   w1,w2,w3,w4,w5,w6,w7,w8,w9,w10\n" +
            "\t   w11,w12,w13,w14,w15,w16,w17,w18,w19,w20,\n" +
            "\t   w21,w22,w23,w24,w25,w26,w27,w28,w29,w30,\n" +
            "\t   w31,w32,w33,w34,w35,w36,w37,w38,w39,w40\n" +
            "  from mss_playground.feature_set_extended";

    public static final String query_111_features = "\n" +
            "select class_assignee,\n" +
            "\t   product_cat,\n" +
            "\t   component_cat,\n" +
            "\t   resolution_cat,\n" +
            "\t   flag_cat,\n" +
            "\t   hardware_cat,\n" +
            "\t   keywords_cat,\n" +
            "\t   priority_cat,\n" +
            "\t   reporter_cat \n" +
            "\t   w1,w2,w3,w4,w5,w6,w7,w8,w9,w10\n" +
            "\t   w11,w12,w13,w14,w15,w16,w17,w18,w19,w20,\n" +
            "\t   w21,w22,w23,w24,w25,w26,w27,w28,w29,w30,\n" +
            "\t   w31,w32,w33,w34,w35,w36,w37,w38,w39,w40,\n" +
            "\t   w41,w42,w43,w44,w45,w46,w47,w48,w49,w50\n" +
            "\t   w51,w52,w53,w54,w55,w56,w57,w58,w59,w60\n" +
            "\t   w61,w62,w63,w64,w65,w66,w67,w68,w69,w70\n" +
            "\t   w71,w72,w73,w74,w75,w76,w77,w78,w79,w80\n" +
            "\t   w81,w82,w83,w84,w85,w86,w87,w88,w89,w90\n" +
            "\t   w91,w92,w93,w94,w95,w96,w97,w98,w99,w100\n" +
            "\t   w101,w102,w103\n" +
            "  from mss_playground.feature_set_extended";

    public static final String query_108_features = " \n " +
            "select class_assignee,\n" +
            "\t   product_cat,\n" +
            "\t   flag_cat,\n" +
            "\t   hardware_cat,\n" +
            "\t   priority_cat,\n" +
            "\t   w1,w2,w3,w4,w5,w6,w7,w8,w9,w10\n" +
            "\t   w11,w12,w13,w14,w15,w16,w17,w18,w19,w20,\n" +
            "\t   w21,w22,w23,w24,w25,w26,w27,w28,w29,w30,\n" +
            "\t   w31,w32,w33,w34,w35,w36,w37,w38,w39,w40,\n" +
            "\t   w41,w42,w43,w44,w45,w46,w47,w48,w49,w50\n" +
            "\t   w51,w52,w53,w54,w55,w56,w57,w58,w59,w60\n" +
            "\t   w61,w62,w63,w64,w65,w66,w67,w68,w69,w70\n" +
            "\t   w71,w72,w73,w74,w75,w76,w77,w78,w79,w80\n" +
            "\t   w81,w82,w83,w84,w85,w86,w87,w88,w89,w90\n" +
            "\t   w91,w92,w93,w94,w95,w96,w97,w98,w99,w100\n" +
            "\t   w101,w102,w103\n" +
            "  from mss_playground.feature_set_extended\n" +
            "  order by class_assignee";


    public void generateSvmFile(){

        try (PrintWriter writer = new PrintWriter("fastForward_libsvm_file_46_features.txt", "UTF-8")){

            SqlRowSet list = jdbcTemplate.queryForRowSet(LibSvmFileGenerator.query_46_features);

            String line = "";
            while(list.next()){
                for (int i = 1 ; i <= list.getMetaData().getColumnCount() ; i ++){

                    if (i == 1)
                        line = line + list.getString(i);
                    else
                        line = line + " " + (i-1) + ":" + list.getString(i);
                }

                writer.println(line);
                line = "";
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


}
