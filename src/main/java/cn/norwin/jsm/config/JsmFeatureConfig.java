package cn.norwin.jsm.config;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class JsmFeatureConfig {


    // Config Features Begin

    // line

    public static final String lineNumber = "Line Number";
    public static final String codeLineNumber = "Code Line Number";
    public static final String commentLineNumber = "Comment Line Number";

    // class interface or enum

    public static final String isInterface = "Is Interface";
    public static final String isClass = "Is Class";
    public static final String isEnum = "Is Enum";
    public static final String isFinalClass = "Is FinalClass";
    public static final String isAbstractClass = "Is AbstractClass";

    // extended and implemented

    public static final String extendedNumber = "Extended number";
    public static final String implementedNumber = "Implemented number";

    // fields

    public static final String fieldNumber = "Field Number";
    public static final String privateFieldNumber = "Private Field Number";
    public static final String protectedFieldNumber = "Protected Field Number";
    public static final String publicFieldNumber = "Public Field Number";
    public static final String finalFieldNumber = "Final Field Number";
    public static final String staticFieldNumber = "Static Field Number";
    public static final String volatileFieldNumber = "Volatile Field Number";


    // method

    public static final String methodNumber = "Method Number";
    public static final String privateMethodNumber = "Private Method Number";
    public static final String protectedMethodNumber = "Protected Method Number";
    public static final String publicMethodNumber = "Public Method Number";

    // parameter

    public static final String parameterNumber = "Parameter Number";
    public static final String parameterUsedTypeNumber = "Parameter Used Type Number";

    // thrown exception number

    public static final String thrownExceptionNumber = "Thrown Exception Number";

    // comment

    public static final String commentNumber = "Comment Number";
    public static final String blockCommentNumber = "Block Comment Number";
    public static final String javadocCommentNumber = "Javadoc Comment Number";
    public static final String lineCommentNumber = "Line Comment Number";

    // package

    public static final String packageLength = "Package Length";
    public static final String packageDepth = "Package Depth";

    // import

    public static final String importNumber = "Import Number";
    public static final String importSourceNumber = "Import Source Number";
    public static final String importDepthSum = "Import Depth Sum";

    // Config Features End


    // get features

    public static List<String> getFeatureList() {

        List<String> resList = new ArrayList<>();

        // reflect to see declared fields

        Field[] fields = JsmFeatureConfig.class.getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            String featureName = "";
            try {
                featureName = field.get(JsmFeatureConfig.class).toString();
            } catch (IllegalAccessException e) {
                System.out.println(e);

            }

            // if find successfully

            if (!featureName.equals("")) {
                resList.add(featureName);
            }
        }

        return resList;
    }
}
