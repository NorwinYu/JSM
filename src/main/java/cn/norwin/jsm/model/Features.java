package cn.norwin.jsm.model;

import java.util.ArrayList;
import java.util.List;

public class Features {

    // Line

    private Long codeLineNumber;

    // class or interface

    private Integer isInterface;
    private Integer isFinalClass;
    private Integer isAbstractClass;
    private Integer isTestClass;


    // Method

    private Long methodNumber;
    private Long privateMethodNumber;
    private Long protectedMethodNumber;
    private Long publicMethodNumber;
    private Long withoutJavadocMethodNumber;

    // parameter

    private Long parameterNumber;

    public static List<String> getFeatureNameList() {

        List<String> featureList = new ArrayList<>();

        featureList.add("Code Line Number");

        featureList.add("Is Interface");
        featureList.add("Is Final Class");
        featureList.add("Is Abstract Class");
        featureList.add("Is Test Class");

        featureList.add("Method Number");
        featureList.add("Private Method Number");
        featureList.add("Protected Method Number");
        featureList.add("Public Method Number");
        featureList.add("Without Javadoc Method Number");

        featureList.add("Parameter Number");

        return featureList;
    }

    public void Features() {

    }

    public Features(Long codeLineNumber, Integer isInterface, Integer isFinalClass, Integer isAbstractClass, Integer isTestClass, Long methodNumber, Long privateMethodNumber, Long protectedMethodNumber, Long publicMethodNumber, Long withoutJavadocMethodNumber, Long parameterNumber) {
        this.codeLineNumber = codeLineNumber;
        this.isInterface = isInterface;
        this.isFinalClass = isFinalClass;
        this.isAbstractClass = isAbstractClass;
        this.isTestClass = isTestClass;
        this.methodNumber = methodNumber;
        this.privateMethodNumber = privateMethodNumber;
        this.protectedMethodNumber = protectedMethodNumber;
        this.publicMethodNumber = publicMethodNumber;
        this.withoutJavadocMethodNumber = withoutJavadocMethodNumber;
        this.parameterNumber = parameterNumber;
    }

    public Long getCodeLineNumber() {
        return codeLineNumber;
    }

    public void setCodeLineNumber(Long codeLineNumber) {
        this.codeLineNumber = codeLineNumber;
    }

    public Integer getIsInterface() {
        return isInterface;
    }

    public void setIsInterface(Integer isInterface) {
        this.isInterface = isInterface;
    }

    public Integer getIsFinalClass() {
        return isFinalClass;
    }

    public void setIsFinalClass(Integer isFinalClass) {
        this.isFinalClass = isFinalClass;
    }

    public Integer getIsAbstractClass() {
        return isAbstractClass;
    }

    public void setIsAbstractClass(Integer isAbstractClass) {
        this.isAbstractClass = isAbstractClass;
    }

    public Integer getIsTestClass() {
        return isTestClass;
    }

    public void setIsTestClass(Integer isTestClass) {
        this.isTestClass = isTestClass;
    }

    public Long getMethodNumber() {
        return methodNumber;
    }

    public void setMethodNumber(Long methodNumber) {
        this.methodNumber = methodNumber;
    }

    public Long getPrivateMethodNumber() {
        return privateMethodNumber;
    }

    public void setPrivateMethodNumber(Long privateMethodNumber) {
        this.privateMethodNumber = privateMethodNumber;
    }

    public Long getProtectedMethodNumber() {
        return protectedMethodNumber;
    }

    public void setProtectedMethodNumber(Long protectedMethodNumber) {
        this.protectedMethodNumber = protectedMethodNumber;
    }

    public Long getPublicMethodNumber() {
        return publicMethodNumber;
    }

    public void setPublicMethodNumber(Long publicMethodNumber) {
        this.publicMethodNumber = publicMethodNumber;
    }

    public Long getWithoutJavadocMethodNumber() {
        return withoutJavadocMethodNumber;
    }

    public void setWithoutJavadocMethodNumber(Long withoutJavadocMethodNumber) {
        this.withoutJavadocMethodNumber = withoutJavadocMethodNumber;
    }

    public Long getParameterNumber() {
        return parameterNumber;
    }

    public void setParameterNumber(Long parameterNumber) {
        this.parameterNumber = parameterNumber;
    }
}
