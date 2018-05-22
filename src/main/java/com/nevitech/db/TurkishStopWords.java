package com.nevitech.db;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TurkishStopWords {

    public static final Set<String> list = new HashSet<>(
            Arrays.asList(new String[]{"a",
                    "acaba",
                    "altı",
                    "alti",
                    "altmış",
                    "altmis",
                    "ama",
                    "ancak",
                    "arada",
                    "artık",
                    "artik",
                    "asla",
                    "aslında",
                    "aslinda",
                    "ayrıca",
                    "ayrica",
                    "az",
                    "bana",
                    "bazen",
                    "bazı",
                    "bazi",
                    "bazıları",
                    "bazilari",
                    "belki",
                    "belkı",
                    "ben",
                    "benden",
                    "beni",
                    "benim",
                    "beri",
                    "beş",
                    "bes",
                    "bile",
                    "bilhassa",
                    "bin",
                    "bir",
                    "biraz",
                    "birçoğu",
                    "bircoğu",
                    "birçok",
                    "bircok",
                    "biri",
                    "birisi",
                    "birkaç",
                    "birkac",
                    "birşey",
                    "birsey",
                    "biz",
                    "bizden",
                    "bize",
                    "bizi",
                    "bizim",
                    "böyle",
                    "boyle",
                    "böylece",
                    "boylece",
                    "bu",
                    "buna",
                    "bunda",
                    "bundan",
                    "bunlar",
                    "bunları",
                    "bunlari",
                    "bunların",
                    "bunlarin",
                    "bunu",
                    "bunun",
                    "burada",
                    "bütün",
                    "butun",
                    "çoğu",
                    "cogu",
                    "çoğunu",
                    "cogunu",
                    "çok",
                    "cok",
                    "çünkü",
                    "cunku",
                    "da",
                    "daha",
                    "dahi",
                    "dan",
                    "de",
                    "defa",
                    "değil",
                    "degil",
                    "diğer",
                    "diger",
                    "diğeri",
                    "digeri",
                    "diğerleri",
                    "digerleri",
                    "diye",
                    "doksan",
                    "dokuz",
                    "dolayı",
                    "dolayi",
                    "dolayısıyla",
                    "dolayisiyla",
                    "dört",
                    "dort",
                    "e",
                    "edecek",
                    "eden",
                    "ederek",
                    "edilecek",
                    "ediliyor",
                    "edilmesi",
                    "ediyor",
                    "eğer",
                    "eger",
                    "elbette",
                    "elli",
                    "en",
                    "etmesi",
                    "etti",
                    "ettiği",
                    "ettigi",
                    "ettiğini",
                    "ettigini",
                    "fakat",
                    "falan",
                    "filan",
                    "gene",
                    "gereği",
                    "geregi",
                    "gerek",
                    "gibi",
                    "göre",
                    "gore",
                    "hala",
                    "halde",
                    "halen",
                    "hangi",
                    "hangisi",
                    "hani",
                    "hatta",
                    "hem",
                    "henüz",
                    "henuz",
                    "hep",
                    "hepsi",
                    "her",
                    "herhangi",
                    "herkes",
                    "herkese",
                    "herkesi",
                    "herkesin",
                    "hiç",
                    "hic",
                    "hiçbir",
                    "hicbir",
                    "hiçbiri",
                    "hicbiri",
                    "i",
                    "ı",
                    "için",
                    "icin",
                    "içinde",
                    "icinde",
                    "iki",
                    "ile",
                    "ilgili",
                    "ise",
                    "işte",
                    "itibaren",
                    "itibariyle",
                    "kaç",
                    "kac",
                    "kadar",
                    "karşın",
                    "karsin",
                    "kendi",
                    "kendilerine",
                    "kendine",
                    "kendini",
                    "kendisi",
                    "kendisine",
                    "kendisini",
                    "kez",
                    "ki",
                    "kim",
                    "kime",
                    "kimi",
                    "kimin",
                    "kimisi",
                    "kimse",
                    "kırk",
                    "kirk",
                    "madem",
                    "mi",
                    "mı",
                    "milyar",
                    "milyon",
                    "mu",
                    "mü",
                    "nasıl",
                    "nasil",
                    "ne",
                    "neden",
                    "nedenle",
                    "nerde",
                    "nerede",
                    "nereye",
                    "neyse",
                    "niçin",
                    "nicin",
                    "nin",
                    "nın",
                    "niye",
                    "nun",
                    "nün",
                    "o",
                    "öbür",
                    "obur",
                    "olan",
                    "olarak",
                    "oldu",
                    "olduğu",
                    "oldugu",
                    "olduğunu",
                    "oldugunu",
                    "olduklarını",
                    "olduklarini",
                    "olmadı",
                    "olmadi",
                    "olmadığı",
                    "olmadigi",
                    "olmak",
                    "olması",
                    "olmasi",
                    "olmayan",
                    "olmaz",
                    "olsa",
                    "olsun",
                    "olup",
                    "olur",
                    "olursa",
                    "oluyor",
                    "on",
                    "ön",
                    "ona",
                    "önce",
                    "once",
                    "ondan",
                    "onlar",
                    "onlara",
                    "onlardan",
                    "onları",
                    "onlari",
                    "onların",
                    "onlarin",
                    "onu",
                    "onun",
                    "orada",
                    "öte",
                    "ote",
                    "ötürü",
                    "oturu",
                    "otuz",
                    "öyle",
                    "oyle",
                    "oysa",
                    "pek",
                    "rağmen",
                    "ragmen",
                    "sana",
                    "sanki",
                    "şayet",
                    "şekilde",
                    "sekilde",
                    "sekiz",
                    "seksen",
                    "sen",
                    "senden",
                    "seni",
                    "senin",
                    "şey",
                    "sey",
                    "şeyden",
                    "seyden",
                    "şeye",
                    "seye",
                    "şeyi",
                    "seyi",
                    "şeyler",
                    "seyler",
                    "şimdi",
                    "simdi",
                    "siz",
                    "sız",
                    "sizden",
                    "size",
                    "sizi",
                    "sizin",
                    "sonra",
                    "şöyle",
                    "soyle",
                    "şu",
                    "şuna",
                    "suna",
                    "şunları",
                    "sunlari",
                    "şunu",
                    "sunu",
                    "ta",
                    "tabii",
                    "tam",
                    "tamam",
                    "tamamen",
                    "tarafından",
                    "tarafindan",
                    "trilyon",
                    "tüm",
                    "tum",
                    "tümü",
                    "tumu",
                    "u",
                    "ü",
                    "üç",
                    "uc",
                    "un",
                    "ün",
                    "üzere",
                    "uzere",
                    "var",
                    "vardı",
                    "vardi",
                    "ve",
                    "veya",
                    "ya",
                    "yani",
                    "yapacak",
                    "yapılan",
                    "yapilan",
                    "yapılması",
                    "yapilmasi",
                    "yapıyor",
                    "yapiyor",
                    "yapmak",
                    "yaptı",
                    "yapti",
                    "yaptığı",
                    "yaptigi",
                    "yaptığını",
                    "yaptigini",
                    "yaptıkları",
                    "yaptiklari",
                    "ye",
                    "yedi",
                    "yerine",
                    "yetmiş",
                    "yetmis",
                    "yi",
                    "yı",
                    "yine",
                    "yirmi",
                    "yoksa",
                    "yu",
                    "yüz",
                    "yuz",
                    "zaten",
                    "zira",
                    "zxtest"}));
}