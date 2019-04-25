package cn.norwin.jsm.visitor;

import cn.norwin.jsm.config.JsmFeatureConfig;
import cn.norwin.jsm.model.JsmFeaturesMap;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.HashMap;

public class JsmMetricsVisitor extends VoidVisitorAdapter<JsmFeaturesMap> {

    public void visit(CompilationUnit compilationUnit, JsmFeaturesMap jsmFeaturesMap) {

        super.visit(compilationUnit, jsmFeaturesMap);

        // ------------------------------------------------------------

        // comment
        Long blockCommentNumber = (long) 0;
        Long javadocCommentNumber = (long) 0;
        Long lineCommentNumber = (long) 0;
        Long commentLineNumber = (long) 0;

        for (Comment comment : compilationUnit.getComments()) {
            commentLineNumber += getLineNumber(comment.getContent());
            if (comment.isBlockComment()) {
                blockCommentNumber += 1;
            }
            else if (comment.isJavadocComment()) {
                javadocCommentNumber += 1;
            }
            else if (comment.isLineComment()) {
                lineCommentNumber += 1;
            }
        }

        // comment number
        jsmFeaturesMap.increaseCount(JsmFeatureConfig.commentNumber, (long) compilationUnit.getComments().size());
        // block comment number
        jsmFeaturesMap.increaseCount(JsmFeatureConfig.blockCommentNumber, blockCommentNumber);
        // javadoc comment number
        jsmFeaturesMap.increaseCount(JsmFeatureConfig.javadocCommentNumber, javadocCommentNumber);
        // line comment number
        jsmFeaturesMap.increaseCount(JsmFeatureConfig.lineCommentNumber, lineCommentNumber);

        // ------------------------------------------------------------

        // line

        // line number
        jsmFeaturesMap.increaseCount(JsmFeatureConfig.lineNumber, getLineNumber(compilationUnit.toString()));
        // code line number
        jsmFeaturesMap.increaseCount(JsmFeatureConfig.codeLineNumber, getLineNumber(compilationUnit.toString()) - commentLineNumber);
        // comment line number
        jsmFeaturesMap.increaseCount(JsmFeatureConfig.commentLineNumber, commentLineNumber);

        // ------------------------------------------------------------

        // package
        String packageName = "";
        if (compilationUnit.getPackageDeclaration().isPresent()) {
            packageName = compilationUnit.getPackageDeclaration().get().getName().asString();
        }

        // package length
        jsmFeaturesMap.increaseCount(JsmFeatureConfig.packageLength, (long) packageName.length());
        // package depth
        jsmFeaturesMap.increaseCount(JsmFeatureConfig.packageDepth, (long) packageName.split("\\.").length);

        // ------------------------------------------------------------

        // class interface or enum

        for (TypeDeclaration typeDeclaration : compilationUnit.getTypes()
             ) {

            if (typeDeclaration instanceof ClassOrInterfaceDeclaration) {

                // is class
                jsmFeaturesMap.increaseCount(JsmFeatureConfig.isClass);
                ClassOrInterfaceDeclaration classOrInterfaceDeclaration = (ClassOrInterfaceDeclaration) typeDeclaration;

                // is interface
                if(classOrInterfaceDeclaration.isInterface())
                    jsmFeaturesMap.increaseCount(JsmFeatureConfig.isInterface);

                // is final class
                if(classOrInterfaceDeclaration.isFinal())
                    jsmFeaturesMap.increaseCount(JsmFeatureConfig.isFinalClass);

                // is abstract class
                if(classOrInterfaceDeclaration.isAbstract())
                    jsmFeaturesMap.increaseCount(JsmFeatureConfig.isAbstractClass);

                // ------------------------------------------------------------

                // extended number
                jsmFeaturesMap.increaseCount(JsmFeatureConfig.extendedNumber, (long) classOrInterfaceDeclaration.getExtendedTypes().size());
                // implemented number
                jsmFeaturesMap.increaseCount(JsmFeatureConfig.implementedNumber, (long) classOrInterfaceDeclaration.getImplementedTypes().size());

                // ------------------------------------------------------------

                // fields

                Long fieldNumber = (long) 0;
                Long privateFieldNumber = (long) 0;
                Long protectedFieldNumber = (long) 0;
                Long publicFieldNumber = (long) 0;
                Long finalFieldNumber = (long) 0;
                Long staticFieldNumber = (long) 0;
                Long volatileFieldNumber = (long) 0;

                for (FieldDeclaration fieldDeclaration : classOrInterfaceDeclaration.getFields()) {

                    fieldNumber++;

                    if (fieldDeclaration.isPrivate())
                        privateFieldNumber++;

                    if (fieldDeclaration.isProtected())
                        protectedFieldNumber++;

                    if (fieldDeclaration.isPublic())
                        publicFieldNumber++;

                    if (fieldDeclaration.isFinal())
                        finalFieldNumber++;

                    if (fieldDeclaration.isStatic())
                        staticFieldNumber++;

                    if (fieldDeclaration.isVolatile())
                        volatileFieldNumber++;
                }

                // field number
                jsmFeaturesMap.increaseCount(JsmFeatureConfig.fieldNumber, fieldNumber);
                // private field number
                jsmFeaturesMap.increaseCount(JsmFeatureConfig.privateFieldNumber, privateFieldNumber);
                // protected field number
                jsmFeaturesMap.increaseCount(JsmFeatureConfig.protectedFieldNumber, protectedFieldNumber);
                // public field number
                jsmFeaturesMap.increaseCount(JsmFeatureConfig.publicFieldNumber, publicFieldNumber);
                // final field number
                jsmFeaturesMap.increaseCount(JsmFeatureConfig.finalFieldNumber, finalFieldNumber);
                // static field number
                jsmFeaturesMap.increaseCount(JsmFeatureConfig.staticFieldNumber, staticFieldNumber);
                // volatile field number
                jsmFeaturesMap.increaseCount(JsmFeatureConfig.volatileFieldNumber, volatileFieldNumber);

                // ------------------------------------------------------------

                // method

                Long methodNumber = (long) 0;
                Long privateMethodNumber = (long) 0;
                Long protectedMethodNumber = (long) 0;
                Long publicMethodNumber = (long) 0;

                Long parameterNumber = (long) 0;
                HashMap<String, Integer> usedTypeMap = new HashMap<>();

                Long thrownExceptionNumber = (long) 0;

                for (MethodDeclaration methodDeclaration : classOrInterfaceDeclaration.getMethods()) {

                    methodNumber++;

                    if(methodDeclaration.isPrivate())
                        privateMethodNumber++;

                    if(methodDeclaration.isProtected())
                        protectedMethodNumber++;

                    if(methodDeclaration.isPublic())
                        publicMethodNumber++;

                    // ------------------------------------------------------------

                    // parameters

                    parameterNumber += methodDeclaration.getParameters().size();

                    for (Parameter parameter : methodDeclaration.getParameters()) {
                        String type = parameter.getType().asString();
                        if (usedTypeMap.containsKey(type)) {
                            usedTypeMap.put(type, usedTypeMap.get(type) + 1);
                        }
                        else {
                            usedTypeMap.put(type, 1);
                        }
                    }

                    // ------------------------------------------------------------

                    // thrown exception number

                    thrownExceptionNumber += methodDeclaration.getThrownExceptions().size();

                    // ------------------------------------------------------------
                }

                // method number
                jsmFeaturesMap.increaseCount(JsmFeatureConfig.methodNumber, methodNumber);
                jsmFeaturesMap.increaseCount(JsmFeatureConfig.privateMethodNumber, privateMethodNumber);
                jsmFeaturesMap.increaseCount(JsmFeatureConfig.protectedMethodNumber, protectedMethodNumber);
                jsmFeaturesMap.increaseCount(JsmFeatureConfig.publicMethodNumber, publicMethodNumber);

                // ------------------------------------------------------------

                // parameter
                jsmFeaturesMap.increaseCount(JsmFeatureConfig.parameterNumber, parameterNumber);
                jsmFeaturesMap.increaseCount(JsmFeatureConfig.parameterUsedTypeNumber, (long) usedTypeMap.size());

                // ------------------------------------------------------------

                // thrown exception number
                jsmFeaturesMap.increaseCount(JsmFeatureConfig.thrownExceptionNumber, thrownExceptionNumber);

                // ------------------------------------------------------------

            }
            else if (typeDeclaration instanceof EnumDeclaration) {
                EnumDeclaration enumDeclaration = (EnumDeclaration) typeDeclaration;

                // is enum
                jsmFeaturesMap.increaseCount(JsmFeatureConfig.isEnum);
            }
            else if (typeDeclaration instanceof AnnotationDeclaration) {
                AnnotationDeclaration annotationDeclaration = (AnnotationDeclaration) typeDeclaration;
            }
            else {
                // unkonwn type
            }
        }

        // ------------------------------------------------------------

        // import

        Long importNumber = (long) 0;
        Long importDepthSum = (long) 0;
        HashMap<String, Integer> importSource = new HashMap<>();

        for (ImportDeclaration importDeclaration : compilationUnit.getImports()) {

            importNumber++;

            String importName = importDeclaration.getName().asString();
            String[] strings = importName.split("\\.");

            // import depth sum
            importDepthSum += strings.length;

            // import source number
            if (strings.length > 1) {
                String sourceName = strings[0] + "." +strings[1];
                if (importSource.containsKey(sourceName)) {
                    importSource.put(sourceName, importSource.get(sourceName) + 1);
                }
                else {
                    importSource.put(sourceName, 1);
                }
            }
        }

        // import number
        jsmFeaturesMap.increaseCount(JsmFeatureConfig.importNumber, importNumber);
        // import source number
        jsmFeaturesMap.increaseCount(JsmFeatureConfig.importSourceNumber, (long) importSource.size());
        // import depth sum
        jsmFeaturesMap.increaseCount(JsmFeatureConfig.importDepthSum, importDepthSum);

        // ------------------------------------------------------------

    }

    public Long getLineNumber(String content) {
        return (long) content.split("\n").length;
    }
}
