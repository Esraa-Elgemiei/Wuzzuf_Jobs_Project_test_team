package DataPreparation;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.util.Map;

public interface JobDAOInterface {
    public Dataset<Row>  readCSV(String path);
    public void  DataInfo(Dataset<Row> data);
    public Dataset<Row> cleanDataFrame(Dataset<Row> data);
    public Map<String, Long> demandingCompanies(Dataset<Row> jobsDF);
    public Map<String, Long> popularJobTitle(Dataset<Row> jobsDF);
    public Map<String, Long> popularAreas(Dataset<Row> jobsDF);
    public Map<String, Long> importantSkills(Dataset<Row> jobsDF);
    public void graphPieChart(Map<String, Long> jobMap,String title);
    public void graphBarChart(Map<String, Long> jobMap,String title,String xLable,String yLable);


}
