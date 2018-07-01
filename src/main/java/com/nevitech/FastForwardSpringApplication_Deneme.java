package com.nevitech;

import com.nevitech.libsvm.LibSvmFileGenerator;

import org.apache.commons.io.FileUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.NaiveBayes;
import org.apache.spark.ml.classification.NaiveBayesModel;
import org.apache.spark.ml.classification.RandomForestClassificationModel;
import org.apache.spark.ml.classification.RandomForestClassifier;
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.ml.feature.IndexToString;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.StringIndexerModel;
import org.apache.spark.ml.feature.VectorIndexer;
import org.apache.spark.ml.feature.VectorIndexerModel;
import org.apache.spark.mllib.tree.RandomForest;
import org.apache.spark.mllib.tree.model.RandomForestModel;
import org.apache.spark.mllib.util.MLUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;


@SpringBootApplication
public class FastForwardSpringApplication_Deneme implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(FastForwardSpringApplication_Deneme.class, args);
	}

	@Autowired
	LibSvmFileGenerator libSvmFileGenerator;

	@Autowired
	JavaSparkContext javaSparkContext;

	@Autowired
	SparkContext sparkContext;

	@Autowired
	SparkSession sparkSession;

	@Override
	public void run(String... args) {


		// $example on$
		// Load and parse the data file, converting it to a DataFrame.
		Dataset<Row> data = sparkSession.read().format("libsvm").load("./fastForward_libsvm_file_jira_999_features.txt");
		data.show(10);
		// Index labels, adding metadata to the label column.
		// Fit on whole dataset to include all labels in index.
		StringIndexerModel labelIndexer = new StringIndexer()
				.setInputCol("label")
				.setOutputCol("indexedLabel")
				.fit(data);
		// Automatically identify categorical features, and index them.
		// Set maxCategories so features with > 1545 distinct values are treated as continuous.
		VectorIndexerModel featureIndexer = new VectorIndexer()
				.setInputCol("features")
				.setOutputCol("indexedFeatures")
				.setMaxCategories(420)
				.fit(data);

		// Split the data into training and test sets (30% held out for testing)
		Dataset<Row>[] splits = data.randomSplit(new double[] {0.7, 0.3});
		Dataset<Row> trainingData = splits[0];
		Dataset<Row> testData = splits[1];


		// Train a RandomForest model.
		RandomForestClassifier rf = new RandomForestClassifier()
				.setLabelCol("indexedLabel")
				.setFeaturesCol("indexedFeatures")
				.setNumTrees(300)
				.setMaxBins(60);

		// Convert indexed labels back to original labels.
		IndexToString labelConverter = new IndexToString()
				.setInputCol("prediction")
				.setOutputCol("predictedLabel")
				.setLabels(labelIndexer.labels());

		// Chain indexers and forest in a Pipeline
		Pipeline pipeline = new Pipeline()
				.setStages(new PipelineStage[] {labelIndexer, featureIndexer, rf, labelConverter});

		// Train model. This also runs the indexers.
		PipelineModel model = pipeline.fit(trainingData);

		// Make predictions.
		Dataset<Row> predictions = model.transform(testData);

		// Select example rows to display.
		predictions.select("predictedLabel", "label", "features").show(50);

		System.out.println("-----Printing features-----");
		predictions.select("features").show(100);

		// Select (prediction, true label) and compute test error
		MulticlassClassificationEvaluator evaluator = new MulticlassClassificationEvaluator()
				.setLabelCol("indexedLabel")
				.setPredictionCol("prediction")
				.setMetricName("accuracy");
		double accuracy = evaluator.evaluate(predictions);
		System.out.println("Test Error = " + (1.0 - accuracy));

		RandomForestClassificationModel rfModel = (RandomForestClassificationModel)(model.stages()[2]);
		System.out.println("Learned classification forest model:\n" + rfModel.toDebugString());
		// $example off$

		sparkSession.stop();

	}
}
