package cn.norwin.jsm;

import cn.norwin.jsm.model.Features;
import cn.norwin.jsm.output.csv.CSVOutput;
import com.beust.jcommander.ParameterException;
import com.sun.xml.internal.xsom.impl.scd.Step;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class JsmApplication {

    @Parameter(
            names = { "--source", "-s" },
            converter = FileConverter.class,
            required = true,
            description = "Directory with all .java files"
    )
    private File sourceDir;

    @Parameter(
            names = { "--output", "-o" },
            converter = FileConverter.class,
            required = true,
            description = "Output directory with name and file type"
    )
    private File outputFile;

    public static void main(String[] args) {

        JsmApplication jsmApplication = new JsmApplication();

        try {

            JCommander.newBuilder()
                    .addObject(jsmApplication)
                    .build()
                    .parse(args);

            jsmApplication.analyse();

        }catch (ParameterException exception) {
            printHelp("[Error]: "+exception.getMessage());
        }

        //SpringApplication.run(JsmApplication.class, args);
    }

    public void analyse() {

        String searchPath = sourceDir.getPath();

        List<Features> featuresList = new ArrayList<>();
        Features features = new Features((long)0,0,0,0,0,(long)0,(long)0,(long)0,(long)0,(long)0,(long)0);
        featuresList.add(features);

        String outputPath = outputFile.getPath();

        if (outputPath.endsWith(".csv")) {
            try {
                CSVOutput.exportCSVFile(featuresList, outputPath);
            } catch (Exception e){

            }
        }
        else {
            printHelp("[Error]: Invalid output file type.");
        }
    }

    public static void printHelp(String message) {
        System.out.println("------------------\n" +
                "|\tJSM  v0.0.1  |\n" +
                "------------------\n" +
                "\n" +
                "--source or -s  =>  Directory with all .java files\n" +
                "--output or -o  =>  Output directory with name and file type" +
                "\n");
        System.out.println(message);
    }

}
