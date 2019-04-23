package cn.norwin.jsm.output.csv;

import cn.norwin.jsm.model.Features;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.List;

public class CSVOutput {

    public static void exportCSVFile(List<Features> featuresList, String path
            ) throws IOException, IllegalArgumentException, IllegalAccessException{

        // load file
        File file = new File(path);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                new FileOutputStream(file), "gbk");

        // export feature names

        int counter = 0;
        for(String title : Features.getFeatureNameList()){
            outputStreamWriter.write(title);
            if (counter < Features.getFeatureNameList().size() - 1) {
                outputStreamWriter.write(",");
            }
            counter ++;
        }
        outputStreamWriter.write("\r\n");

        // export feature list
        for (Features features : featuresList) {
            Field[] fields = features.getClass().getDeclaredFields();
            counter = 0;
            for(Field field : fields){
                field.setAccessible(true);
                outputStreamWriter.write(field.get(features).toString());
                if (counter < fields.length - 1) {
                    outputStreamWriter.write(",");
                }
                counter ++;
            }
        }
        outputStreamWriter.flush();
        outputStreamWriter.close();
    }
}
