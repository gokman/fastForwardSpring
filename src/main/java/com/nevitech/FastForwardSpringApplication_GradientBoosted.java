package com.nevitech;

import com.nevitech.libsvm.LibSvmFileGenerator;

import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.RandomForestClassificationModel;
import org.apache.spark.ml.classification.RandomForestClassifier;
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.ml.feature.*;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;


//@SpringBootApplication
public class FastForwardSpringApplication_GradientBoosted implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(FastForwardSpringApplication_GradientBoosted.class, args);
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


		// Load and parse the data file, converting it to a DataFrame.
		Dataset<Row> data = sparkSession.read().format("libsvm").load("zemberekli.txt");

		// Index labels, adding metadata to the label column.
		// Fit on whole dataset to include all labels in index.
		StringIndexerModel labelIndexer = new StringIndexer()
				.setInputCol("label")
				.setOutputCol("indexedLabel")
				.fit(data);
		// Automatically identify categorical features, and index them.
		// Set maxCategories so features with > 4 distinct values are treated as continuous.
		VectorIndexerModel featureIndexer = new VectorIndexer()
				.setInputCol("features")
				.setOutputCol("indexedFeatures")
				.setMaxCategories(2)
				.fit(data);

// Split the data into training and test sets (30% held out for testing)
		Dataset<Row>[] splits = data.randomSplit(new double[] {0.7, 0.3});
		Dataset<Row> trainingData = splits[0];
		Dataset<Row> testData = splits[1];

// Train a RandomForest model.
		RandomForestClassifier rf = new RandomForestClassifier()
				.setLabelCol("indexedLabel")
				.setFeaturesCol("indexedFeatures")
				.setNumTrees(100)
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
		predictions.select("predictedLabel", "label", "features").show(5);

// Select (prediction, true label) and compute test error
		MulticlassClassificationEvaluator evaluator = new MulticlassClassificationEvaluator()
				.setLabelCol("indexedLabel")
				.setPredictionCol("prediction")
				.setMetricName("accuracy");
		double accuracy = evaluator.evaluate(predictions);
		System.out.println("Basari = " + accuracy);
		System.out.println("Test Error = " + (1.0 - accuracy));

		RandomForestClassificationModel rfModel = (RandomForestClassificationModel)(model.stages()[2]);
		System.out.println("Learned classification forest model:\n" + rfModel.toDebugString());

	}
}
