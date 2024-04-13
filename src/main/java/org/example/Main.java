package org.example;

public class Main {

    public static void main(String[] args) {
        JavaToPlantUMLConverter converter = new JavaToPlantUMLConverter();
        converter.parseJavaFiles("C:\\Users\\alals\\repos\\Ptidej\\PADL\\src\\main\\java\\padl");
        String plantUML = converter.generatePlantUML();
        System.out.println(plantUML);;
    }
}