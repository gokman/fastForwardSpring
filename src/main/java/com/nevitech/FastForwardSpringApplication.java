package com.nevitech;

import com.nevitech.db.DbProcess;
import com.nevitech.db.InstanceModel;
import com.nevitech.nlp.TurkishDeasciifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import zemberek.morphology.TurkishMorphology;
import zemberek.morphology.analysis.WordAnalysis;


//@SpringBootApplication
public class FastForwardSpringApplication implements CommandLineRunner{


	public static void main(String[] args) {
		SpringApplication.run(FastForwardSpringApplication.class, args);
	}


	@Autowired
	DbProcess dbProcess;

	@Autowired
	TurkishDeasciifier turkishDeasciifier;

	@Value("${app.feature.size}")
	private int featureSize;

	@Override
	public void run(String... args) throws IOException {

		//raw jira dataset tablosundan veri cek
		List<String> dbData = dbProcess.getData(DbProcess.rawJiraSet_summary_50row);
		List<String> clearedWordList = dbProcess.getCleanedWordList(dbData);
		Map<String, Integer> wordCountMap = dbProcess.getStemAndCount(clearedWordList);
		Map<String, Integer> sortedWordCountMap = dbProcess.sortMap(wordCountMap);
		Map<String, Integer> limitedWordCountMap = dbProcess.limitMap(sortedWordCountMap, featureSize);

		//limitedWordCountMap.forEach((k,v)->System.out.println("Item : " + k + " Count : " + v));
		int counterMap = 0;
		for (Map.Entry<String, Integer> entry : limitedWordCountMap.entrySet()) {
			System.out.println(counterMap + "-->" + "Key : " + entry.getKey() + " Value : " + entry.getValue());
			counterMap++;
		}


		//her jira icin vektor olustur
		List<InstanceModel> dbInstanceData = dbProcess.getDataWithTaskKey(DbProcess.rawJiraSet_instance_50row);
		Map<String,String> instances = dbProcess.createInstances(limitedWordCountMap, dbInstanceData);

		dbProcess.writeMapToFile(instances,"zemberekli.txt");


	}
}