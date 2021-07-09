package DataPreparation;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;

public class JobDAO implements JobDAOInterface
{
    @Override
    public Dataset<Row> readCSV(String path) {
        //omit the log generated by apache spark from the output.
        Logger.getLogger ("org").setLevel (Level.ERROR);

        // Create Spark Session to create connection to Spark
        final SparkSession sparkSession = SparkSession.builder ().appName ("Wuzzuf Jobs Project").master ("local[6]")
                .getOrCreate ();
        // Get DataFrameReader using SparkSession
        final DataFrameReader dataFrameReader = sparkSession.read ();

        // Set header option to true to specify that first row in file contains name of columns
        dataFrameReader.option ("header", "true");

        Dataset<Row> jobsDF = dataFrameReader.csv (path);

        // Print First 20 rows of DataSet
        System.out.println("======================== First 20 rows of DataSet ========================");
        jobsDF.show();

        return jobsDF;
    }

    @Override
    public void DataInfo(Dataset<Row> data) {
        // Print Schema to see column names, types and other metadata
        System.out.println("======================== DataSet Schema ========================");
        data.printSchema ();

        // Print Summary to see column statistics
        System.out.println("======================== DataSet Summary ========================");
        data.describe().show();
    }

    @Override
    public Dataset<Row> cleanDataFrame(Dataset<Row> data) {
        Dataset<Row> jobsNoNullDF=data.na().drop ();
        Dataset<Row> cleanDF = jobsNoNullDF.distinct();
        return cleanDF;
    }

    @Override
    public Map<String, Long> demandingCompanies(Dataset<Row> jobsDF) {
        // Create view and execute query to convert types as, by default, all columns have string types
        jobsDF.createOrReplaceTempView ("Wuzzuf_DF");
        // Create Spark Session to create connection to Spark
        final SparkSession sparkSession = SparkSession.builder ().appName ("Wuzzuf Jobs Project").master ("local[6]")
                .getOrCreate ();
        final Dataset<Row> companies = sparkSession
                .sql ("SELECT Company,Count(*) AS Available_Jobs FROM Wuzzuf_DF GROUP BY Company ORDER BY Count(*) DESC ");
        //demandingCompany.show();

        //create map of DataPreparation.Job Company & it's count in the file
        Map<String, Long> jobCompany = new HashMap<>();
        companies.collectAsList().forEach(row -> jobCompany.put(row.getString(0), row.getLong(1)));
        //jobCompany.entrySet().forEach(System.out::println);

        //sorted the map Descending
        Map<String, Long> sortedJobCompany = jobCompany.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        /*System.out.println("==================== sortedJobCompany =========================\n");
        sortedJobCompany.entrySet().forEach(System.out::println);*/
        //System.out.println("===============================================================================================\n");

        Map<String, Long> demandingCompanies  = sortedJobCompany.entrySet()
                .stream()
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        return demandingCompanies;
    }

    @Override
    public Map<String, Long> popularJobTitle(Dataset<Row> jobsDF) {
        // Create view and execute query to convert types as, by default, all columns have string types
        jobsDF.createOrReplaceTempView ("Wuzzuf_DF");
        // Create Spark Session to create connection to Spark
        final SparkSession sparkSession = SparkSession.builder ().appName ("Wuzzuf Jobs Project").master ("local[6]")
                .getOrCreate ();
        final Dataset<Row> titles = sparkSession
                .sql ("SELECT Title,Count(*) AS Needed_Jobs FROM Wuzzuf_DF GROUP BY Title ORDER BY Count(*) DESC ");
        //demandingJobTitle.show();

        //create map of DataPreparation.Job Title & it's count in the file
        Map<String, Long> jobTitle = new HashMap<>();
        titles.collectAsList().forEach(row -> jobTitle.put(row.getString(0), row.getLong(1)));
        //jobTitle.entrySet().forEach(System.out::println);

        //sorted the map Descending
        Map<String, Long> sortedJobTitles = jobTitle.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        /*System.out.println("==================== sortedJobTitles  =========================\n");
        sortedJobTitles .entrySet().forEach(System.out::println);
        System.out.println("===============================================================================================\n");*/

        Map<String, Long> demandingTitles  = sortedJobTitles .entrySet()
                .stream()
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        return demandingTitles;
    }

    @Override
    public  Map<String, Long> popularAreas(Dataset<Row> jobsDF) {
        // Create view and execute query to convert types as, by default, all columns have string types
        jobsDF.createOrReplaceTempView ("Wuzzuf_DF");
        // Create Spark Session to create connection to Spark
        final SparkSession sparkSession = SparkSession.builder ().appName ("Wuzzuf Jobs Project").master ("local[6]")
                .getOrCreate ();
        final Dataset<Row> areas = sparkSession
                .sql ("SELECT Location,Count(*) AS Counts FROM Wuzzuf_DF GROUP BY Location ORDER BY Count(*) DESC ");
        //demandingAreas.show();
        //create map of DataPreparation.Job Areas & it's count in the file
        Map<String, Long> jobAreas = new HashMap<>();
        areas.collectAsList().forEach(row -> jobAreas.put(row.getString(0), row.getLong(1)));
        //jobTitle.entrySet().forEach(System.out::println);

        //sorted the map Descending
        Map<String, Long> sortedJobAreas= jobAreas.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

      /*  System.out.println("==================== sortedJobAreas =========================\n");
        sortedJobAreas .entrySet().forEach(System.out::println);
        System.out.println("===============================================================================================\n");*/

        Map<String, Long> demandingAreas  = sortedJobAreas.entrySet()
                .stream()
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        return demandingAreas ;
    }

    @Override
    public  Map<String, Long>  importantSkills(Dataset<Row> jobsDF) {
        // Create view and execute query to convert types as, by default, all columns have string types
        jobsDF.createOrReplaceTempView ("Wuzzuf_DF");
        // Create Spark Session to create connection to Spark
        final SparkSession sparkSession = SparkSession.builder ().appName ("Wuzzuf Jobs Project").master ("local[6]")
                .getOrCreate ();
        final Dataset<Row> Skills = sparkSession.sql ("SELECT Skills  FROM Wuzzuf_DF ");
        //Skills.show();
        Map<String, Long> jobSkills = Skills.collectAsList().stream()
                                                                  .map(row -> row.getString(0).split(","))
                                                                  .flatMap(Arrays::stream)
                                                                  .map(skill -> skill.toLowerCase().trim())
                                                                  .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        //importantSkills.entrySet().forEach(System.out::println);
        //sorted the map Descending
        Map<String, Long> sortedJobSkills= jobSkills.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        /*System.out.println("==================== sortedJobSkills =========================\n");
        sortedJobSkills.entrySet().forEach(System.out::println);
        System.out.println("===============================================================================================\n");*/

        Map<String, Long> demandingSkills  = sortedJobSkills.entrySet()
                .stream()
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        return demandingSkills  ;
    }

    @Override
    public void graphPieChart(Map<String, Long> jobMap,String title) {
        // Create Chart
        PieChart chart = new PieChartBuilder().width (800).height (600).title (title).build ();
        // Customize Chart
        int size =jobMap.size();
        Color[] sliceColors = new Color[size];
        for (int i=0; i<size;i++){
            sliceColors[i] = Color.getHSBColor((float) (i+1) / size, 1, 4);
        }
        chart.getStyler ().setSeriesColors (sliceColors);
        // Series
        for (Map.Entry<String, Long> entry : jobMap.entrySet()) {
            chart.addSeries (entry.getKey(), entry.getValue());
        }
        // Show it
        new SwingWrapper(chart).displayChart ();
    }

    @Override
    public void graphBarChart(Map<String, Long> jobMap,String title,String xLable,String yLable) {
        //filter to get an array of passenger ages
        List<String> keys = jobMap.keySet().stream().collect(toList());
        List<Long> values = jobMap.values().stream().collect(toList());

        // Create Chart
        CategoryChart chart = new CategoryChartBuilder().width (1024).height (768).title (title).xAxisTitle (xLable).yAxisTitle (yLable).build ();
        // Customize Chart
        chart.getStyler ().setLegendPosition (Styler.LegendPosition.InsideNW);
        chart.getStyler ().setHasAnnotations (true);
        chart.getStyler ().setStacked (true);
        chart.getStyler().setXAxisLabelRotation(90);
        // Series
        chart.addSeries (yLable, keys, values);
        // Show it
        new SwingWrapper(chart).displayChart ();
    }
}