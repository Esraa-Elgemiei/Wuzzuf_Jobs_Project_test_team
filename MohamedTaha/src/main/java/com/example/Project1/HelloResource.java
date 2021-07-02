package com.example.Project1;

import data.Smile;
import smile.data.DataFrame;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@Path("/hello-world")
public class HelloResource {

    @GET
    @Produces("text/plain")
    /** if you want this hello method to work:
     * type /api/hello-world
     * To see changes Click: Run and Redeploy*/

    public String hello() {

        /** ******************************************* */
        Smile s = new Smile ();
        DataFrame trainData = s.readCSV (s.trainPath);
        DataFrame testData = s.readCSV (s.testPath);
        s.getTrainDataSummery (trainData);

        //s.processTrainData (trainData);
        //s.plotData (trainData);
        /** ********** Printing to a string *************/
/**
        // Create a stream to hold the output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        // IMPORTANT: Save the old System.out!
        PrintStream old = System.out;
        // Tell Java to use your special stream
        System.setOut(ps);
        // Print some output: goes to your special stream

        System.out.println("Any");
        // Put things back
        System.out.flush();
        System.setOut(old);

        String first = baos.toString();*/
        String summary = "Something is wrong";
        try {
            summary = s.getTrainDataSummery (trainData);
        } catch (Exception e){
            summary = "Not Done";
        }
        return summary;
    }

}