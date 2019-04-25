package cn.norwin.jsm;

import cn.norwin.jsm.model.JsmFeaturesMap;
import cn.norwin.jsm.output.csv.JsmCSVOutput;
import com.beust.jcommander.ParameterException;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

    public static void main(String[] args) throws Exception {

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

        List<JsmFeaturesMap> jsmFeaturesMapList = new ArrayList<>();

        if (outputFile.getPath().endsWith(".csv")) {

            try {
                Files.walkFileTree(sourceDir.toPath(), new JsmFileVisitor(jsmFeaturesMapList));

                JsmCSVOutput.exportCSVFile(jsmFeaturesMapList, outputFile.getPath());
            }
            catch (IOException e) {
                e.printStackTrace();
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
