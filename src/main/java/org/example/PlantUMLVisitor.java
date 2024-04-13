package org.example;

import java.util.ArrayList;
import java.util.List;

public class PlantUMLVisitor implements Visitor {
    private StringBuilder plantUMLBuilder = new StringBuilder();

    @Override
    public void visit(ClassElement element) {
        // Add class declaration to PlantUML
        plantUMLBuilder.append("class ").append(element.getClassName()).append(" {\n");

        // Add fields
        element.getFields().forEach(field -> plantUMLBuilder.append("\t").append(field).append("\n"));

        // Add methods
        element.getMethods().forEach(method -> plantUMLBuilder.append("\t").append(method).append("\n"));

        plantUMLBuilder.append("}\n");
    }

    @Override
    public void visit(InterfaceElement element) {
        // Add interface declaration to PlantUML
        plantUMLBuilder.append("interface ").append(element.getInterfaceName()).append(" {\n");

        // Add methods
        element.getMethods().forEach(method -> plantUMLBuilder.append("\t").append(method).append("\n"));

        plantUMLBuilder.append("}\n");
    }

    public String getPlantUML() {
        return plantUMLBuilder.toString();
    }

}
