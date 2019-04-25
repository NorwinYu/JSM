package cn.norwin.jsm;

import cn.norwin.jsm.model.JsmFeaturesMap;
import cn.norwin.jsm.visitor.JsmMetricsVisitor;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class JsmFileVisitor extends SimpleFileVisitor<Path> {

    private List<JsmFeaturesMap> jsmFeaturesMapList;
    private Long counter;

    public JsmFileVisitor(List<JsmFeaturesMap> jsmFeaturesMapList) {
        this.jsmFeaturesMapList = jsmFeaturesMapList;
        counter = (long) 0;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
            throws IOException {

        // print visit action
        System.out.println("[Run] id: "+counter+" Visit File: " + file);

        counter++;

        // check out file type is Java or not

        if (!isJava(file)) {
            return FileVisitResult.CONTINUE;
        }

        JsmFeaturesMap jsmFeaturesMap = new JsmFeaturesMap();

        // init compilation unit
        CompilationUnit compilationUnit = JavaParser.parse(file.toFile());

        // metrics visitor
        new JsmMetricsVisitor().visit(compilationUnit, jsmFeaturesMap);

        // add to the feature map list
        jsmFeaturesMapList.add(jsmFeaturesMap);

        return FileVisitResult.CONTINUE;
    }

    public Boolean isJava(Path path) {
        return path.toString().endsWith(".java");
    }
}
