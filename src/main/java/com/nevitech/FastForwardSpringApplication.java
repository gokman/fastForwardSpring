package com.nevitech;

import com.nevitech.jobs.RandomForestBugClassifier46Features;
import com.nevitech.libsvm.LibSvmFileGenerator;

import org.apache.commons.io.FileUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.RandomForest;
import org.apache.spark.mllib.tree.model.RandomForestModel;
import org.apache.spark.mllib.util.MLUtils;
import org.dmg.pmml.KNNInput;
import org.dmg.pmml.KNNInputs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import scala.Tuple2;


@SpringBootApplication
public class FastForwardSpringApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(FastForwardSpringApplication.class, args);
	}

	@Autowired
	LibSvmFileGenerator libSvmFileGenerator;

	@Autowired
	JavaSparkContext javaSparkContext;


	@Override
	public void run(String... args) {

		// Load and parse the data file.
		String datapath = "./fastForward_libsvm_file_46_features.txt";
		JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(javaSparkContext.sc(), datapath).toJavaRDD();


		// Split the data into training and test sets (30% held out for testing)
		JavaRDD<LabeledPoint>[] splits = data.randomSplit(new double[]{0.7, 0.3});
		JavaRDD<LabeledPoint> trainingData = splits[0];
		JavaRDD<LabeledPoint> testData = splits[1];

		// Train a RandomForest model.
		// Empty categoricalFeaturesInfo indicates all features are continuous.
		Integer numClasses = 205;
		Map<Integer, Integer> categoricalFeaturesInfo = new HashMap<>();
		categoricalFeaturesInfo.put(0,4);
		categoricalFeaturesInfo.put(1,23);
		categoricalFeaturesInfo.put(2,6);
		categoricalFeaturesInfo.put(3,14);
		categoricalFeaturesInfo.put(4,6);
		categoricalFeaturesInfo.put(5,59);
		categoricalFeaturesInfo.put(6,3);
		for (int i = 7 ; i < 46 ; i ++) {
			categoricalFeaturesInfo.put(i,2);
		}
		Integer numTrees = 50; // Use more in practice.
		String featureSubsetStrategy = "auto"; // Let the algorithm choose.
		String impurity = "gini";
		Integer maxDepth = 30;
		Integer maxBins = 59;
		Integer seed = 12345;

		RandomForestModel model = RandomForest.trainClassifier(trainingData, numClasses,
				categoricalFeaturesInfo, numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins,
				seed);

		// Evaluate model on test instances and compute test error
		JavaPairRDD<Double, Double> predictionAndLabel =
				testData.mapToPair(p -> new Tuple2<>(model.predict(p.features()), p.label()));
		double testErr =
				predictionAndLabel.filter(pl -> !pl._1().equals(pl._2())).count() / (double) testData.count();

		System.out.println("Test Error: " + testErr);

		// Delete if model already present, and Save the new model
		try {
			FileUtils.forceDelete(new File("target/tmp/myRandomForestClassificationModel"));
			System.out.println("\nDeleting old model completed.");
		} catch (FileNotFoundException e1) {
		} catch (IOException e) {
		}

		// Save and load model
		model.save(javaSparkContext.sc(), "target/tmp/myRandomForestClassificationModel");
		RandomForestModel sameModel = RandomForestModel.load(javaSparkContext.sc(),
				"target/tmp/myRandomForestClassificationModel");

	}
}
