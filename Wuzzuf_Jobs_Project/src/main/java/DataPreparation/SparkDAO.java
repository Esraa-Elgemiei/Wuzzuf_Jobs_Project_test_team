package DataPreparation;

import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;


public class SparkDAO {

    public Dataset<Row> readCSV(String path) {

        // Create Spark Session to create connection to Spark
        final SparkSession sparkSession = SparkSession.builder ().appName ("Wuzzuf Jobs Project").master ("local[6]")
                .getOrCreate ();

        // Get DataFrameReader using SparkSession
        final DataFrameReader dataFrameReader = sparkSession.read ();

        // Set header option to true to specify that first row in file contains name of columns
        dataFrameReader.option ("header", "true");

        Dataset<Row> jobsDF = dataFrameReader.csv (path);


        // Print Schema to see column names, types and other metadata
        System.out.println("======================== DataSet Schema ========================");
        jobsDF.printSchema ();

        // Print First 20 rows of DataSet
        System.out.println("======================== First 20 rows of DataSet ========================");
        jobsDF.show();

        Dataset<Row> jobsNoNullDF=jobsDF.na().drop ();
        System.out.println("========================DataSet without Nulls Schema ========================");
        jobsNoNullDF.printSchema ();


        return jobsNoNullDF;
        }











   /* public DataFrame cleanDataFrame(DataFrame data){

        //OmitNullRows
        DataFrame cleanData= data.omitNullRows ();
        System.out.println ("Number of non Null rows is: "+cleanData.nrows ());

        //Remove Duplicates

        List<Job> jobList = getJobList().stream().distinct().collect(Collectors.toList());
        System.out.println ("Number of unique rows is: "+jobList.size());
        *//*List<Tuple> rows = cleanData.stream().distinct().collect(Collectors.toList());
        cleanData=DataFrame.of(rows);*//*
        return cleanData;
    }*/
}
