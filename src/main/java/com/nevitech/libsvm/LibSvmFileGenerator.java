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

    final static String query_jira_172_word_features = "select class_assignee, reporter, priority, issue_type, w1, w2,w3, w4, w5, w6, w7, w8, w9, w10, w11, w12, w14, w15, w16, w17, w18, w19, w20, w21, w22, w23, w24, w26, w28, w30, \n" +
            "w31, w32, w33, w35, w36, w37, w39, w40, w45, w46, w47, w48, w49, w50, w51, w52, w53, w54, w55, w56, w57, w58, w59, w60, w61, w62, w63, w65, w66, w67, w69, \n" +
            "w70, w71, w73, w74, w75, w76, w77, w78, w80, w81, w82, w84, w86, w87, w88, w90, w91, w92, w93, w94, w95, w96, w97, w98, w100, w101, w102, w103, w104, w105, w106, \n" +
            "w107, w108, w109, w110, w111, w112, w113, w114, w115, w116, w117, w118, w119, w120, w121, w122, w123, w125, w126, w127, w129, w130, w133, w134, w135, w136, w137, w138, \n" +
            "w140, w141, w142, w143, w144, w145, w146, w147, w148, w149, w151, w153, w154, w155, w156, w157, w158, w159, w162, w163, w164, w165, w166, w167, w168, w169, w170, \n" +
            "w171, w172 \n" +
            "from mss_playground.feature_set_extended_jira";

    final static String query_jira_60_word_features = "select class_assignee, reporter, priority, issue_type, w1, w2,w3, w4, w5, w6, " +
            "w7, w8, w9, w10, w11, w12, w14, w15, w16, w17, w18, w19, w20, w21, w22, w23, w24, w26, w28, w30, \n" +
            "w31, w32, w33, w35, w36, w37, w39, w40, w45, w46, w47, w48, w49, w50, w51, w52, w53, w54, w55, " +
            "w56, w57, w58, w59, w60, w61, w62, w63, w65, w66, w67, w69 \n" +
            "from mss_playground.feature_set_extended_jira ej, raw_dataset_last_year ly "+
            "where ej.task_key=ly.task_key";


    final static String query_jira_3_word_features = "select class_assignee, reporter, priority, issue_type " +
            "from mss_playground.feature_set_extended_jira";

    final static String query_jira_999_word_features ="select aa.class_assignee,aa.issue_type,aa.priority,aa.reporter,aa.w1,aa.w2,aa.w3,aa.w4,aa.w5,aa.w6,aa.w7,aa.w8,aa.w9,aa.w10,aa.w11,aa.w12,aa.w13,\n" +
            "aa.w14,aa.w15,aa.w16,aa.w17,aa.w18,aa.w19,aa.w20,aa.w21,aa.w22,aa.w23,aa.w24,aa.w25,aa.w26,aa.w27,aa.w28,aa.w29,\n" +
            "aa.w30,aa.w31,aa.w32,aa.w33,aa.w34,aa.w35,aa.w36,aa.w37,aa.w38,aa.w39,aa.w40,aa.w41,aa.w42,aa.w43,aa.w44,aa.w45,\n" +
            "aa.w46,aa.w47,aa.w48,aa.w49,aa.w50,aa.w51,aa.w52,aa.w53,aa.w54,aa.w55,aa.w56,aa.w57,aa.w58,aa.w59,aa.w60,aa.w61,\n" +
            "aa.w62,aa.w63,aa.w64,aa.w65,aa.w66,aa.w67,aa.w68,aa.w69,aa.w70,aa.w71,aa.w72,aa.w73,aa.w74,aa.w75,aa.w76,aa.w77,\n" +
            "aa.w78,aa.w79,aa.w80,aa.w81,aa.w82,aa.w83,aa.w84,aa.w85,aa.w86,aa.w87,aa.w88,aa.w89,aa.w90,aa.w91,aa.w92,aa.w93,\n" +
            "aa.w94,aa.w95,aa.w96,aa.w97,aa.w98,aa.w99,aa.w100,aa.w101,aa.w102,aa.w103,aa.w104,aa.w105,aa.w106,aa.w107,aa.w108,aa.w109,\n" +
            "aa.w110,aa.w111,aa.w112,aa.w113,aa.w114,aa.w115,aa.w116,aa.w117,aa.w118,aa.w119,aa.w120,aa.w121,aa.w122,aa.w123,aa.w124,aa.w125,\n" +
            "aa.w126,aa.w127,aa.w128,aa.w129,aa.w130,aa.w131,aa.w132,aa.w133,aa.w134,aa.w135,aa.w136,aa.w137,aa.w138,aa.w139,aa.w140,aa.w141,\n" +
            "aa.w142,aa.w143,aa.w144,aa.w145,aa.w146,aa.w147,aa.w148,aa.w149,aa.w150,aa.w151,aa.w152,aa.w153,aa.w154,aa.w155,aa.w156,aa.w157,\n" +
            "aa.w158,aa.w159,aa.w160,aa.w161,aa.w162,aa.w163,aa.w164,aa.w165,aa.w166,aa.w167,aa.w168,aa.w169,aa.w170,aa.w171,aa.w172,aa.w173,\n" +
            "aa.w174,aa.w175,aa.w176,aa.w177,aa.w178,aa.w179,aa.w180,aa.w181,aa.w182,aa.w183,aa.w184,aa.w185,aa.w186,aa.w187,aa.w188,aa.w189,\n" +
            "aa.w190,aa.w191,aa.w192,aa.w193,aa.w194,aa.w195,aa.w196,aa.w197,aa.w198,aa.w199,aa.w200,aa.w201,aa.w202,aa.w203,aa.w204,aa.w205,\n" +
            "aa.w206,aa.w207,aa.w208,aa.w209,aa.w210,aa.w211,aa.w212,aa.w213,aa.w214,aa.w215,aa.w216,aa.w217,aa.w218,aa.w219,aa.w220,aa.w221,\n" +
            "aa.w222,aa.w223,aa.w224,aa.w225,aa.w226,aa.w227,aa.w228,aa.w229,aa.w230,aa.w231,aa.w232,aa.w233,aa.w234,aa.w235,aa.w236,aa.w237,\n" +
            "aa.w238,aa.w239,aa.w240,aa.w241,aa.w242,aa.w243,aa.w244,aa.w245,aa.w246,aa.w247,aa.w248,aa.w249,aa.w250,aa.w251,\n" +
            "aa.w252,aa.w253,aa.w254,aa.w255,aa.w256,aa.w257,aa.w258,aa.w259,aa.w260,aa.w261,aa.w262,aa.w263,aa.w264,aa.w265,aa.w266,aa.w267,\n" +
            "aa.w268,aa.w269,aa.w270,aa.w271,aa.w272,aa.w273,aa.w274,aa.w275,aa.w276,aa.w277,aa.w278,aa.w279,aa.w280,aa.w281,aa.w282,aa.w283,\n" +
            "aa.w284,aa.w285,aa.w286,aa.w287,aa.w288,aa.w289,aa.w290,aa.w291,aa.w292,aa.w293,aa.w294,aa.w295,aa.w296,aa.w297,aa.w298,aa.w299,\n" +
            "aa.w300,aa.w301,aa.w302,aa.w303,aa.w304,aa.w305,aa.w306,aa.w307,aa.w308,aa.w309,aa.w310,aa.w311,aa.w312,aa.w313,aa.w314,aa.w315,\n" +
            "aa.w316,aa.w317,aa.w318,aa.w319,aa.w320,aa.w321,aa.w322,aa.w323,aa.w324,aa.w325,aa.w326,aa.w327,aa.w328,aa.w329,aa.w330,aa.w331,\n" +
            "aa.w332,aa.w333,aa.w334,aa.w335,aa.w336,aa.w337,aa.w338,aa.w339,aa.w340,aa.w341,aa.w342,aa.w343,aa.w344,aa.w345,aa.w346,aa.w347,\n" +
            "aa.w348,aa.w349,aa.w350,aa.w351,aa.w352,aa.w353,aa.w354,aa.w355,aa.w356,aa.w357,aa.w358,aa.w359,aa.w360,aa.w361,aa.w362,aa.w363,\n" +
            "aa.w364,aa.w365,aa.w366,aa.w367,aa.w368,aa.w369,aa.w370,aa.w371,aa.w372,aa.w373,aa.w374,aa.w375,aa.w376,aa.w377,aa.w378,aa.w379,\n" +
            "aa.w380,aa.w381,aa.w382,aa.w383,aa.w384,aa.w385,aa.w386,aa.w387,aa.w388,aa.w389,aa.w390,aa.w391,aa.w392,aa.w393,aa.w394,aa.w395,\n" +
            "aa.w396,aa.w397,aa.w398,aa.w399,aa.w400,aa.w401,aa.w402,aa.w403,aa.w404,aa.w405,aa.w406,aa.w407,aa.w408,aa.w409,aa.w410,\n" +
            "aa.w411,aa.w412,aa.w413,aa.w414,aa.w415,aa.w416,aa.w417,aa.w418,aa.w419,aa.w420,aa.w421,aa.w422,aa.w423,aa.w424,\n" +
            "aa.w425,aa.w426,aa.w427,aa.w428,aa.w429,aa.w430,aa.w431,aa.w432,aa.w433,aa.w434,\n" +
            "aa.w435,aa.w436,aa.w437,aa.w438,aa.w439,aa.w440,aa.w441,aa.w442,aa.w443,aa.w444,aa.w445,aa.w446,aa.w447,aa.w448,aa.w449,aa.w450,\n" +
            "aa.w451,aa.w452,aa.w453,aa.w454,aa.w455,aa.w456,aa.w457,aa.w458,aa.w459,aa.w460,aa.w461,aa.w462,aa.w463,aa.w464,aa.w465,aa.w466,\n" +
            "aa.w467,aa.w468,aa.w469,aa.w470,aa.w471,aa.w472,aa.w473,aa.w474,aa.w475,aa.w476,aa.w477,aa.w478,aa.w479,aa.w480,aa.w481,aa.w482,\n" +
            "aa.w483,aa.w484,aa.w485,aa.w486,aa.w487,aa.w488,aa.w489,aa.w490,aa.w491,aa.w492,aa.w493,aa.w494,aa.w495,aa.w496,aa.w497,aa.w498,\n" +
            "aa.w499,aa.w500,aa.w501,aa.w502,aa.w503,aa.w504,aa.w505,aa.w506,aa.w507,aa.w508,aa.w509,aa.w510,aa.w511,aa.w512,aa.w513,aa.w514,\n" +
            "aa.w515,aa.w516,aa.w517,aa.w518,aa.w519,aa.w520,aa.w521,aa.w522,aa.w523,aa.w524,aa.w525,aa.w526,aa.w527,aa.w528,aa.w529,aa.w530,\n" +
            "aa.w531,aa.w532,aa.w533,aa.w534,aa.w535,aa.w536,aa.w537,aa.w538,aa.w539,aa.w540,aa.w541,aa.w542,aa.w543,aa.w544,aa.w545,aa.w546,\n" +
            "aa.w547,aa.w548,aa.w549,aa.w550,aa.w551,aa.w552,aa.w553,aa.w554,aa.w555,aa.w556,aa.w557,aa.w558,aa.w559,aa.w560,aa.w561,aa.w562,\n" +
            "aa.w563,aa.w564,aa.w565,aa.w566,aa.w567,aa.w568,aa.w569,aa.w570,aa.w571,aa.w572,aa.w573,aa.w574,aa.w575,aa.w576,aa.w577,aa.w578,\n" +
            "aa.w579,aa.w580,aa.w581,aa.w582,aa.w583,aa.w584,aa.w585,aa.w586,aa.w587,aa.w588,aa.w589,aa.w590,aa.w591,aa.w592,\n" +
            "aa.w593,aa.w594,aa.w595,aa.w596,aa.w597,aa.w598,aa.w599,aa.w600,aa.w601,aa.w602,aa.w603,aa.w604,aa.w605,aa.w606,aa.w607,aa.w608,\n" +
            "aa.w609,aa.w610,aa.w611,aa.w612,aa.w613,aa.w614,aa.w615,aa.w616,aa.w617,aa.w618,aa.w619,aa.w620,aa.w621,aa.w622,aa.w623,aa.w624,\n" +
            "aa.w625,aa.w626,aa.w627,aa.w628,aa.w629,aa.w630,aa.w631,aa.w632,aa.w633,aa.w634,aa.w635,aa.w636,aa.w637,aa.w638,aa.w639,aa.w640,\n" +
            "aa.w641,aa.w642,aa.w643,aa.w644,aa.w645,aa.w646,aa.w647,aa.w648,aa.w649,aa.w650,aa.w651,aa.w652,aa.w653,aa.w654,aa.w655,aa.w656,\n" +
            "aa.w657,aa.w658,aa.w659,aa.w660,aa.w661,aa.w662,aa.w663,aa.w664,aa.w665,aa.w666,aa.w667,aa.w668,aa.w669,aa.w670,aa.w671,aa.w672,\n" +
            "aa.w673,aa.w674,aa.w675,aa.w676,aa.w677,aa.w678,aa.w679,aa.w680,aa.w681,aa.w682,aa.w683,aa.w684,aa.w685,aa.w686,aa.w687,aa.w688,\n" +
            "aa.w689,aa.w690,aa.w691,aa.w692,aa.w693,aa.w694,aa.w695,aa.w696,aa.w697,aa.w698,aa.w699,aa.w700,aa.w701,aa.w702,aa.w703,aa.w704,\n" +
            "aa.w705,aa.w706,aa.w707,aa.w708,aa.w709,aa.w710,aa.w711,aa.w712,aa.w713,aa.w714,aa.w715,aa.w716,aa.w717,aa.w718,aa.w719,aa.w720,\n" +
            "aa.w721,aa.w722,aa.w723,aa.w724,aa.w725,aa.w726,aa.w727,aa.w728,aa.w729,aa.w730,aa.w731,aa.w732,aa.w733,aa.w734,aa.w735,aa.w736,\n" +
            "aa.w737,aa.w738,aa.w739,aa.w740,aa.w741,aa.w742,aa.w743,aa.w744,aa.w745,aa.w746,aa.w747,aa.w748,aa.w749,aa.w750,aa.w751,aa.w752,\n" +
            "aa.w753,aa.w754,aa.w755,aa.w756,aa.w757,aa.w758,aa.w759,aa.w760,aa.w761,aa.w762,aa.w763,aa.w764,aa.w765,aa.w766,aa.w767,aa.w768,\n" +
            "aa.w769,aa.w770,aa.w771,aa.w772,aa.w773,aa.w774,aa.w775,aa.w776,aa.w777,aa.w778,aa.w779,aa.w780,aa.w781,aa.w782,aa.w783,aa.w784,\n" +
            "aa.w785,aa.w786,aa.w787,aa.w788,aa.w789,aa.w790,aa.w791,aa.w792,aa.w793,aa.w794,aa.w795,aa.w796,aa.w797,aa.w798,aa.w799,aa.w800,\n" +
            "aa.w801,aa.w802,aa.w803,aa.w804,aa.w805,aa.w806,aa.w807,aa.w808,aa.w809,aa.w810,aa.w811,aa.w812,aa.w813,aa.w814,aa.w815,aa.w816,\n" +
            "aa.w817,aa.w818,aa.w819,aa.w820,aa.w821,aa.w822,aa.w823,aa.w824,aa.w825,aa.w826,aa.w827,aa.w828,aa.w829,aa.w830,aa.w831,aa.w832,\n" +
            "aa.w833,aa.w834,aa.w835,aa.w836,aa.w837,aa.w838,aa.w839,aa.w840,aa.w841,aa.w842,aa.w843,aa.w844,aa.w845,aa.w846,aa.w847,aa.w848,\n" +
            "aa.w849,aa.w850,aa.w851,aa.w852,aa.w853,aa.w854,aa.w855,aa.w856,aa.w857,\n" +
            "aa.w858,aa.w859,aa.w860,aa.w861,aa.w862,aa.w863,aa.w864,aa.w865,aa.w866,aa.w867,aa.w868,aa.w869,aa.w870,aa.w871,aa.w872,aa.w873,\n" +
            "aa.w874,aa.w875,aa.w876,aa.w877,aa.w878,aa.w879,aa.w880,aa.w881,aa.w882,aa.w883,aa.w884,aa.w885,aa.w886,aa.w887,aa.w888,aa.w889,\n" +
            "aa.w890,aa.w891,aa.w892,aa.w893,aa.w894,aa.w895,aa.w896,aa.w897,aa.w898,aa.w899,aa.w900,aa.w901,aa.w902,aa.w903,aa.w904,aa.w905,\n" +
            "aa.w906,aa.w907,aa.w908,aa.w909,aa.w910,aa.w911,aa.w912,aa.w913,aa.w914,aa.w915,aa.w916,aa.w917,aa.w918,aa.w919,aa.w920,aa.w921,\n" +
            "aa.w922,aa.w923,aa.w924,aa.w925,aa.w926,aa.w927,aa.w928,aa.w929,aa.w930,aa.w931,aa.w932,aa.w933,aa.w934,aa.w935,aa.w936,aa.w937,\n" +
            "aa.w938,aa.w939,aa.w940,aa.w941,aa.w942,aa.w943,aa.w944,aa.w945,aa.w946,aa.w947,aa.w948,aa.w949,aa.w950,aa.w951,aa.w952,aa.w953,\n" +
            "aa.w954,aa.w955,aa.w956,aa.w957,aa.w958,aa.w959,aa.w960,aa.w961,aa.w962,aa.w963,aa.w964,aa.w965,aa.w966,aa.w967,aa.w968,aa.w969,\n" +
            "aa.w970,aa.w971,aa.w972,aa.w973,aa.w974,aa.w975,aa.w976,aa.w977,aa.w978,aa.w979,aa.w980,aa.w981,aa.w982,aa.w983,aa.w984,aa.w985,\n" +
            "aa.w986,aa.w987,aa.w988,aa.w989,aa.w990,aa.w991,aa.w992,aa.w993,aa.w994,aa.w995,aa.w996,aa.w997,aa.w998,aa.w999\n" +
            "from mss_playground.feature_set_extended_jira_1000 aa, mss_playground.raw_jira_dataset rjs\n" +
            "where rjs.task_key = aa.task_key\n" +
            "order by created_at desc limit 10000";




    public void generateSvmFile(){

        try (PrintWriter writer = new PrintWriter("fastForward_libsvm_file_jira_999_features.txt", "UTF-8")){

            SqlRowSet list = jdbcTemplate.queryForRowSet(LibSvmFileGenerator.query_jira_999_word_features);

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
