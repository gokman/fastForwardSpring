package com.nevitech;

import com.nevitech.db.DbProcess;
import com.nevitech.db.InstanceModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@SpringBootApplication
public class FastForwardSpringApplication implements CommandLineRunner{


	public static void main(String[] args) {
		SpringApplication.run(FastForwardSpringApplication.class, args);
	}

	/*@Autowired
	LibSvmFileGenerator libSvmFileGenerator;

	@Autowired
	JavaSparkContext javaSparkContext;

	@Autowired
	SparkContext sparkContext;

	@Autowired
	SparkSession sparkSession;*/

	@Autowired
	DbProcess dbProcess;

	@Value("${app.feature.size}")
	private int featureSize;

	@Override
	public void run(String... args) throws IOException {

		//raw jira dataset tablosundan veri cek
		List<String> dbData = dbProcess.getData(DbProcess.rawJiraSet_summary_50row);
		Map<String, Integer> unsortedWordCountMap = dbProcess.getWordCount(dbData);
		Map<String, Integer> sortedWordCountMap = dbProcess.sortMap(unsortedWordCountMap);
		Map<String, Integer> limitedWordCountMap = dbProcess.limitMap(sortedWordCountMap,featureSize);

		//her jira icin vektor olustur
		List<InstanceModel> dbInstanceData = dbProcess.getDataWithTaskKey(DbProcess.rawJiraSet_instance_50row);
		Map<String,String> instances = dbProcess.createInstances(limitedWordCountMap, dbInstanceData);

		instances.entrySet().forEach(System.out::println);
	}
}