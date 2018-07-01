package com.nevitech;

import com.nevitech.db.DbProcess;
import com.nevitech.db.InstanceModel;
import com.nevitech.libsvm.LibSvmFileGenerator;
import com.nevitech.nlp.TurkishDeasciifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;
import java.util.Map;


//@SpringBootApplication
public class FileGenerator implements CommandLineRunner{


	public static void main(String[] args) {
		SpringApplication.run(FileGenerator.class, args);
	}


	@Autowired
	DbProcess dbProcess;

	@Autowired
	TurkishDeasciifier turkishDeasciifier;

	@Autowired
	LibSvmFileGenerator gen;

	@Value("${app.feature.size}")
	private int featureSize;

	@Override
	public void run(String... args) throws IOException {

		gen.generateSvmFile();


	}
}