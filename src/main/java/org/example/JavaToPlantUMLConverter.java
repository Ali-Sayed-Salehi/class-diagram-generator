package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaToPlantUMLConverter {
    private final List<Element> elements = new ArrayList<>();

    public void parseJavaFiles(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".java"));
        if (files == null) {
            throw new IllegalArgumentException("Invalid directory path");
        }
        for (File file : files) {
            parseJavaFile(file.getAbsolutePath());
        }
    }

    public void parseJavaFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            StringBuilder classContent = new StringBuilder();
            boolean insideClass = false;
            while ((line = br.readLine()) != null) {
                if (line.contains("class") || line.contains("interface")) {
                    insideClass = true;
                    classContent.append(line).append("\n");
                } else if (insideClass) {
                    classContent.append(line).append("\n");
                    if (line.contains("}")) {
                        insideClass = false;
                        Element element = createJavaElement(classContent.toString());
                        elements.add(element);
                        classContent.setLength(0);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Element createJavaElement(String classContent) {
        // Extract class or interface name
        Pattern pattern = Pattern.compile("(class|interface)\\s+(\\w+)");
        Matcher matcher = pattern.matcher(classContent);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid Java file content");
        }
        String elementType = matcher.group(1);
        String elementName = matcher.group(2);

        // Extract fields and methods
        List<String> fields = new ArrayList<>();
        List<String> methods = new ArrayList<>();
        pattern = Pattern.compile("(private|protected|public)\\s+(\\w+\\s+)*(\\w+)(\\(.*?\\))");
        matcher = pattern.matcher(classContent);
        while (matcher.find()) {
            String accessModifier = matcher.group(1);
            String returnType = matcher.group(2);
            String methodName = matcher.group(3);
            String params = matcher.group(4);
            if (elementType.equals("class")) {
                fields.add(accessModifier + " " + returnType + methodName + params);
            } else {
                methods.add(returnType + methodName + params);
            }
        }

        if (elementType.equals("class")) {
            ClassElement classElement = new ClassElement();
            classElement.setClassName(elementName);
            classElement.setFields(fields);
            classElement.setMethods(methods);
            return classElement;
        } else {
            InterfaceElement interfaceElement = new InterfaceElement();
            interfaceElement.setInterfaceName(elementName);
            interfaceElement.setMethods(methods);
            return interfaceElement;
        }
    }

    public String generatePlantUML() {
        PlantUMLVisitor visitor = new PlantUMLVisitor();
        elements.forEach(element -> element.accept(visitor));
        return visitor.getPlantUML();
    }

}
