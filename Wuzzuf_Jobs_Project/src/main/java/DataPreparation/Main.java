package DataPreparation;

import smile.data.DataFrame;
import smile.data.type.StructType;

import java.io.IOException;
import java.util.*;

public class Main {
    //static String jobPath = "src/main/resources/data/Wuzzuf_Jobs.csv";
    static String jobPath = "src/main/resources/data/Wuzzuf_Jobs_enhanced.csv";

    public static void main(String[] args) throws IOException {
        //1.	Read data set and convert it to dataframe or Spark RDD and display some from it.
        JobDAO dao = new JobDAO();
        //create dataFrame & it's structure
        DataFrame jobData = dao.readCSV(jobPath);
        //2.	Display structure and "summary????" of the data.
        StructType schema = jobData.schema();
        System.out.println("========================DataFrame schema =======================");
        System.out.println(schema);
        //show some records of data
        System.out.println("======================== DataFrame =======================");
        System.out.println(jobData.toString(10));
        //jobData.forEach(System.out::println);

        /////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////
        //3.	Clean the data (null, duplications)
        /*//List<Job> jobList = dao.getJobList().stream().distinct().collect(Collectors.toList());
        List<Tuple> jobList = jobData.stream().distinct().collect(Collectors.toList());
        DataFrame cleandata = DataFrame.of(jobList, schema);
        System.out.println("size of cleaned dataset: "+cleandata.size());*/
        /////////////////////////////////////////////////////////////////////////////////
       /* System.out.println("========================DataFrame lists========================");
        jobList.forEach(System.out::println);*/
        /*System.out.println("size of original dataset: "+jobData.size());
        System.out.println("size of original dataset: "+jobList.size());
        jobData=dao.cleanDataFrame(jobData);
        System.out.println("size of cleaned dataset: "+jobData.size());*/
        /////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////
        //4.    Count the jobs for each company and display that in order
        // (What are the most demanding companies for jobs?)
        Map<String, Long> demandingCompanies =dao.demandingCompanies();
        System.out.println("The most demanding companies for jobs are: ");
        System.out.println("================================================");
        demandingCompanies.forEach((k,v)->System.out.println(k+" company has "+v+" available jobs."));
        System.out.println("================================================");
        /////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////
        //5.	Show step 4 in a pie chart
        dao.graphPieChart(demandingCompanies,"The most demanding companies for jobs");
        /////////////////////////////////////////////////////////////////////////////////
        dao.graphBarChart(demandingCompanies,"The most demanding companies for jobs",
                            "companies","jobs count");
        /////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////
        //6.	Find out What are the most popular job titles?
        Map<String, Long> popularJobTitles = dao.popularJobTitle();
        System.out.println("The most popular job titles are: ");
        System.out.println("================================================");
        popularJobTitles.forEach((k,v)->System.out.println("job title: "+k+" was found "+v+" times."));
        System.out.println("================================================");
        /////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////
        //7.	Show step 6 in bar chart
        dao.graphBarChart(popularJobTitles,"The most popular job titles",
                "job titles","count");
        /////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////
        //8.	Find out the most popular areas?
        Map<String, Long> popularAreas = dao.popularAreas();
        System.out.println("The most popular Areas are: ");
        System.out.println("================================================");
        popularAreas.forEach((k,v)->System.out.println("Areas: "+k+" was found "+v+" times."));
        System.out.println("================================================");
        /////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////
        //9.	 Show step 8 in bar chart
        dao.graphBarChart(popularAreas,"The most popular Areas",
                "Areas","count");
        /////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////
        //10.	Print skills one by one and how many each repeated
        // and order the output to find out the most important skills required?



        /////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////
        //11.	Factorize the YearsExp feature and convert it to numbers in new col. (Bonus)
        //12.	Apply K-means for job title and companies (Bonus)
    }
}
