package com.nevitech.config;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfig {

    @Value("${app.name}")
    String appName;

    @Value("${master}")
    String master;

    @Value("${app.session.name}")
    String appSessionName;

    @Bean(destroyMethod = "stop")
    public SparkContext sparkConf() {

        /*return new SparkConf().setAppName(appName)
                .setMaster(master)
                .set("spark.executor.memory","3g")
                .set("spark.driver.memory", "3g");*/

        final SparkConf conf = new SparkConf().setAppName("RandomForestBugClassifier")
                .setMaster("local")
                .set("spark.executor.memory","3g")
                .set("spark.driver.memory", "3g");
        return new SparkContext(conf);
    }

    @Bean
    public SparkSession sparkSession() {
        return SparkSession
                .builder()
                .sparkContext(javaSparkContext().sc())
                .appName(appSessionName)
                .getOrCreate();
    }

    @Bean(destroyMethod = "stop")
    public JavaSparkContext javaSparkContext() {
        return new JavaSparkContext(sparkConf());
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
