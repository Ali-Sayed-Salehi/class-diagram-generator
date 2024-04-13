package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlantUMLVisitorTest {

    @Test
    void testVisitClassElement() {
        ClassElement element = new ClassElement();
        element.setClassName("TestClass");
        element.setFields(List.of("private int id", "private String name"));
        element.setMethods(List.of("public int getId()", "public String getName()"));

        PlantUMLVisitor visitor = new PlantUMLVisitor();
        visitor.visit(element);

        String expectedPlantUML = "class TestClass {\n" +
                "\tprivate int id\n" +
                "\tprivate String name\n" +
                "\tpublic int getId()\n" +
                "\tpublic String getName()\n" +
                "}\n";
        assertEquals(expectedPlantUML, visitor.getPlantUML());
    }

    @Test
    void testVisitInterfaceElement() {
        InterfaceElement element = new InterfaceElement();
        element.setInterfaceName("TestInterface");
        element.setMethods(List.of("void doSomething()"));

        PlantUMLVisitor visitor = new PlantUMLVisitor();
        visitor.visit(element);

        String expectedPlantUML = "interface TestInterface {\n" +
                "\tvoid doSomething()\n" +
                "}\n";
        assertEquals(expectedPlantUML, visitor.getPlantUML());
    }
}
