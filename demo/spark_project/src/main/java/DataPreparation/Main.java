package DataPreparation;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.Map;

public class Main {
    private static final String jobPath = "src/main/resources/data/Wuzzuf_Jobs.csv";

    public static void main(String[] args) {
        JobDAO dao = new JobDAO();
        //1.	Read data set and convert it to dataframe or Spark RDD and display some from it.
        Dataset<Row> jobDB = dao.readCSV(jobPath);
        /////////////////////////////////////////////////////////////////////////////////
        //2.	Display structure and "summary" of the data.
        dao.DataInfo(jobDB);
        /////////////////////////////////////////////////////////////////////////////////
        //3.	Clean the data (null, duplications)
        Dataset<Row> cleanDF = dao.cleanDataFrame(jobDB);
        dao.DataInfo(cleanDF);
        /////////////////////////////////////////////////////////////////////////////////
        //4.    Count the jobs for each company and display that in order
        // (What are the most demanding companies for jobs?)
        Map<String, Long> demandingCompanies = dao.demandingCompanies(cleanDF);

        System.out.println("\nThe most demanding companies for jobs are: ");
        System.out.println("================================================");
        demandingCompanies.forEach((k,v)->System.out.println(k+" company has "+v+" available jobs."));
        System.out.println("================================================");
        /////////////////////////////////////////////////////////////////////////////////
        //5.	Show step 4 in a pie chart
        dao.graphPieChart(demandingCompanies,"The most demanding companies for jobs");
        /////////////////////////////////////////////////////////////////////////////////
        dao.graphBarChart(demandingCompanies,"The most demanding companies for jobs", "companies","jobs count");
        /////////////////////////////////////////////////////////////////////////////////
        //6.	Find out What are the most popular job titles?
        Map<String, Long> popularJobTitles  = dao.popularJobTitle(cleanDF);

        System.out.println("The most popular job titles are: ");
        System.out.println("================================================");
        popularJobTitles.forEach((k,v)->System.out.println("job title: "+k+" was found "+v+" times."));
        System.out.println("================================================");
        /////////////////////////////////////////////////////////////////////////////////
        //7.	Show step 6 in bar chart
        dao.graphBarChart(popularJobTitles,"The most popular job titles", "job titles","count");
        /////////////////////////////////////////////////////////////////////////////////
        //8.	Find out the most popular areas?
        Map<String, Long> popularAreas = dao.popularAreas(cleanDF);

        System.out.println("The most popular Areas are: ");
        System.out.println("================================================");
        popularAreas.forEach((k,v)->System.out.println("Areas: "+k+" was found "+v+" times."));
        System.out.println("================================================");
        /////////////////////////////////////////////////////////////////////////////////
        //9.	 Show step 8 in bar chart
        dao.graphBarChart(popularAreas,"The most popular Areas", "Areas","count");
        /////////////////////////////////////////////////////////////////////////////////
        //10.	Print skills one by one and how many each repeated
        // and order the output to find out the most important skills required?
        Map<String, Long> skills = dao.importantSkills(cleanDF);

        System.out.println("The most important skills required are: ");
        System.out.println("================================================");
        skills.forEach((k,v)->System.out.println("Skill: "+k+" was found "+v+" times."));
        System.out.println("================================================");

        //Show step 10 in bar chart
        dao.graphBarChart(skills,"The most important skills required", "Skills","count");

        //Show step 10 in a pie chart
        dao.graphPieChart(skills,"The most important skills required for jobs");
        /////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////
        //11.	Factorize the YearsExp feature and convert it to numbers in new col. (Bonus)
        //12.	Apply K-means for job title and companies (Bonus)
    }
}
