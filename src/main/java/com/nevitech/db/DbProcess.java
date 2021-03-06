package com.nevitech.db;

import com.nevitech.nlp.TurkishDeasciifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import zemberek.morphology.TurkishMorphology;
import zemberek.morphology.analysis.WordAnalysis;

@Component
public class DbProcess {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    TurkishDeasciifier turkishDeasciifier;

    TurkishMorphology morphology = TurkishMorphology.createWithDefaults();

    public static final String rawJiraSet_summary_50row= "select summary from mss_playground.raw_jira_dataset order by created_at desc limit 50";

    public static final String rawJiraSet_instance_50row= "select task_key,summary,assignee,reporter from mss_playground.raw_jira_dataset order by created_at desc limit 50";

    public static final String rawJiraSet_summary_10000row = "select summary from mss_playground.raw_jira_dataset order by created_at desc limit 10000";

    public static final String rawJiraSet_instance_10000row = "select task_key,summary,assignee,reporter from mss_playground.raw_jira_dataset order by created_at desc limit 10000";
    public static final String rawJiraSet_summary_10000RowNormalized = "select summary from mss_playground.raw_jira_dataset dts,mss_playground.jira_top_20_assignee ass\n"+
            "where ass.assignee = dts.assignee\n"+
            "order by created_at desc limit 10000";
    public static final String rawJiraSet_instance_10000RowNormalized = "  select dts.task_key,dts.summary,dts.assignee,dts.reporter \n" +
            "    from mss_playground.raw_jira_dataset dts,mss_playground.jira_top_20_assignee ass \n" +
            "   where ass.assignee = dts.assignee\n" +
            "order by created_at desc limit 10000";
    public DbProcess() throws IOException {
    }

    public List<String> getData(String query){
        List<String> list = jdbcTemplate.queryForList(query, String.class);
        return list;
    }

    public List<InstanceModel> getDataWithTaskKey(String query){
        List<InstanceModel> list = jdbcTemplate.query(query, new BeanPropertyRowMapper(InstanceModel.class));
        return list;
    }

    public Map<String, Integer> getWordCount(List<String> list){

        Map<String, Integer> counts = list.parallelStream()
                .map(line -> line.replaceAll("[^A-Za-z]"," ")) //replace all non-alpha chars
                .map(String::toLowerCase)
                .flatMap(line -> Arrays.stream(line.trim().split("\\s+"))) //split to words
                .filter(word -> !TurkishStopWords.list.contains(word)) //remove stop words
                .filter(word -> !word.equals("")) //remove null values
                .filter(word -> word.length() > 1)
                .map(word -> {
                    turkishDeasciifier.setAsciiString(word);
                    return getStem(turkishDeasciifier.convertToTurkish());
                })
                .collect(Collectors.toMap(w -> w, w -> 1, Integer::sum));

        return counts;
    }

    public List<String> getCleanedWordList(List<String> list){


        List<String> counts = list.parallelStream()
                .map(line -> line.replaceAll("[^A-Za-z]"," ")) //replace all non-alpha chars
                .map(String::toLowerCase)
                .flatMap(line -> Arrays.stream(line.trim().split("\\s+"))) //split to words
                .filter(word -> !TurkishStopWords.list.contains(word)) //remove stop words
                .filter(word -> !word.equals("") || !word.equals(" ") || !word.equals(null)) //remove null values
                .filter(word -> word.length() > 1)
                .collect(Collectors.toList());

        return counts;
    }

    /**
     * feature listesi olusturan metot
     */
    //TODO tek ve cift harfli olan featurelar haric tutulabilir. incelenip karar verilecek
    public Map<String, Integer> getStemAndCount(List<String> wordList){

        List<String> result = new ArrayList<>();

        wordList.forEach(item -> {
            turkishDeasciifier.setAsciiString(item);
            result.add(getStem(turkishDeasciifier.convertToTurkish()));
        });

        return result.stream().collect(Collectors.toMap(w -> w, w -> 1, Integer::sum));
    }

    public Map<String, Integer> sortMap(Map<String, Integer> unsortedMap){
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        unsortedMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        return sortedMap;
    }

    public Map<String, Integer> limitMap(Map<String, Integer> mapParam, int size){
        return mapParam
                .entrySet()
                .stream()
                .limit(size)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (v1,v2) -> v1, LinkedHashMap::new)
                );
    }

    public String getStem(String word){
        WordAnalysis result = morphology.analyze(word);
        if(result.getAnalysisResults().size() != 0 )
            return result.getAnalysisResults().get(0).getStem();
        return "";
    }

    /**
     *
     * @param wordCountMap
     * @param dbInstanceData
     * @return
     */
    public Map<String,String> createInstances(Map<String, Integer> wordCountMap, List<InstanceModel> dbInstanceData) {
        Map<String,String> instances = new HashMap<>();
        for(InstanceModel instanceModel : dbInstanceData){
            String key = instanceModel.getTaskKey();
            StringBuffer vectorString = new StringBuffer();

            vectorString.append(instanceModel.getAssignee()+" ");
            //vectorString.append(instanceModel.getReporter()+" ");TODO buna simdilik gerek yok

            Set<String> words = new HashSet<>(Arrays.asList(instanceModel.getSummary().toLowerCase().split(" +")).parallelStream()
                    .map(line -> line.replaceAll("[^A-Za-z]"," "))
                    .filter(word -> !TurkishStopWords.list.contains(word)) //remove stop words
                    .collect(Collectors.toList()));

            //words.forEach(System.out::println);
            //System.out.println("\n");

            List<String> result = new ArrayList<>();

            words.forEach(item -> {
                turkishDeasciifier.setAsciiString(item);
                result.add(getStem(turkishDeasciifier.convertToTurkish()));
            });

            //result.forEach(System.out::println);
            //System.out.println("\n");

            int counter = 1;
            for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {

                if(counter != 1){//TODO bosluk feature olayi duzelince bu if kalkacak
                    if(result.contains(entry.getKey())){
                        vectorString.append(String.valueOf(counter-1)+":" + 1+" ");

                    }else{
                        vectorString.append(String.valueOf(counter-1)+":" + 0+" ");
                    }
                }

                counter++;
            }

            instances.put(key,vectorString.toString());
        }
        return instances;
    }

    /**
     * parametre olarak verilen map in value larini satir satir dosyaya yazar
     * @return
     */
    public void writeMapToFile(Map<String,String> mapParam, String fileName){

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            for (Map.Entry<String, String> entry : mapParam.entrySet()) {
                //bw.write(entry.getKey() + " " + entry.getValue()+"\n"); //task key ile birlikte dosyaya yazar
                bw.write(entry.getValue() + "\n");

            }

            System.out.println("yazma bitti =====================");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}