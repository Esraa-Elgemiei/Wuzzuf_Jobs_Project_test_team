package DataPreparation;

import org.apache.commons.csv.CSVFormat;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;
import smile.data.DataFrame;
import smile.data.Tuple;
import smile.io.Read;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class JobDAO
{
    private DataFrame jobDataFrame;

    public DataFrame readCSV(String path) {

        CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader ();
        DataFrame df = null;
        try {
            df = Read.csv (path, format);
            System.out.println("========================DataFrame Structure========================");
            System.out.println(df.structure ());
            /*System.out.println("========================DataFrame Summary========================");
            System.out.println(df.summary ());*/

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace ();
        }
        jobDataFrame = df;
        return df;
    }
    public DataFrame cleanDataFrame(DataFrame data){

        //OmitNullRows
        DataFrame cleanData= data.omitNullRows ();
        //System.out.println ("Number of non Null rows is: "+cleanData.nrows ());

        //Remove Duplicates

        /*List<Job> jobList = getJobList().stream().distinct().collect(Collectors.toList());
        System.out.println ("Number of unique rows is: "+jobList.size());*/
        /*List<Tuple> rows = cleanData.stream().distinct().collect(Collectors.toList());
        cleanData=DataFrame.of(rows);*/
        return cleanData;
    }

    public Map<String, Long> demandingCompanies(){

        //create list of jobs
        List<Job> jobList = getJobList();

        //create map of company name & the number of jobs related to it
        Map<String, Long> jobCompany = jobList.stream()
                                                .collect(Collectors.groupingBy(job -> job.getCompany()
                                                            , Collectors.counting()));

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
    public Map<String, Long> popularJobTitle(){

        //create list of jobs
        List<Job> jobList = getJobList();

        //create map of Job Title & it's count in the file
        Map<String, Long> jobTitles = jobList.stream()
                .collect(Collectors.groupingBy(job -> job.getTitle()
                        , Collectors.counting()));

        //sorted the map Descending
        Map<String, Long> sortedJobTitles = jobTitles.entrySet()
                                                    .stream()
                                                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                                                    .collect(Collectors.toMap(
                                                            Map.Entry::getKey,Map.Entry::getValue,
                                                            (e1, e2) -> e1, LinkedHashMap::new));

        System.out.println("==================== sortedJobTitles  =========================\n");
        sortedJobTitles .entrySet().forEach(System.out::println);
        System.out.println("===============================================================================================\n");

        Map<String, Long> demandingTitles  = sortedJobTitles .entrySet()
                                                                .stream()
                                                                .limit(10)
                                                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                                                        (e1, e2) -> e1, LinkedHashMap::new));
        return demandingTitles;
    }
    public Map<String, Long> popularAreas(){

        //create list of jobs
        List<Job> jobList = getJobList();

        //create map of Job Areas & it's count in the file
        Map<String, Long> jobAreas = jobList.stream()
                .collect(Collectors.groupingBy(job -> job.getLocation()
                        , Collectors.counting()));

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
    public void graphBarChart(Map<String, Long> jobMap,String title,String xLable,String yLable) {
        //filter to get an array of passenger ages
        List<String> keys = jobMap.keySet().stream().collect(Collectors.toList());
        List<Long> values = jobMap.values().stream().collect(Collectors.toList());

        // Create Chart
        CategoryChart chart = new CategoryChartBuilder().width (1024).height (768).title (title).xAxisTitle (xLable).yAxisTitle (yLable).build ();
        // Customize Chart
        chart.getStyler ().setLegendPosition (Styler.LegendPosition.InsideNW);
        chart.getStyler ().setHasAnnotations (true);
        chart.getStyler ().setStacked (true);

        // Series
        chart.addSeries (yLable, keys, values);
        // Show it
        new SwingWrapper (chart).displayChart ();
    }

    public List<Job> getJobList() {
        assert jobDataFrame != null;
        List<Job> jobs = new ArrayList<>();
        for (Tuple t : jobDataFrame.stream().collect(Collectors.toList())) {
            Job job = new Job();
            job.setTitle((String) t.get("Title"));
            job.setCompany((String) t.get("Company"));
            job.setLocation((String) t.get("Location"));
            job.setType((String) t.get("Type"));
            job.setLevel((String) t.get("Level"));
            job.setYearsExp((String) t.get("YearsExp"));
            job.setCountry((String) t.get("Country"));
            job.setSkills((String) t.get("Skills"));
            jobs.add(job);
        }
        return jobs;
    }
}
