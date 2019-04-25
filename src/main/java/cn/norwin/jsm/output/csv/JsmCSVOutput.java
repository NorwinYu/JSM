package cn.norwin.jsm.output.csv;

import cn.norwin.jsm.config.JsmFeatureConfig;
import cn.norwin.jsm.model.JsmFeaturesMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class JsmCSVOutput {

    public static void exportCSVFile(List<JsmFeaturesMap> featuresMaps, String path
            ) throws IOException, IllegalArgumentException {

        // load file
        File file = new File(path);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                new FileOutputStream(file), "gbk");

        // export feature names

        int counter = 0;
        for(String title : JsmFeatureConfig.getFeatureList()){
            outputStreamWriter.write(title);
            if (counter < JsmFeatureConfig.getFeatureList().size() - 1) {
                outputStreamWriter.write(",");
            }
            counter ++;
        }
        outputStreamWriter.write("\r\n");

        // export feature map
        for (JsmFeaturesMap features : featuresMaps) {
            for (int i = 0; i < JsmFeatureConfig.getFeatureList().size(); i++) {
                outputStreamWriter.write(features.getCount(JsmFeatureConfig.getFeatureList().get(i)).toString());
                if (i < JsmFeatureConfig.getFeatureList().size() - 1) {
                    outputStreamWriter.write(",");
                }
            }
            outputStreamWriter.write("\r\n");
        }

        outputStreamWriter.flush();
        outputStreamWriter.close();
    }
}
