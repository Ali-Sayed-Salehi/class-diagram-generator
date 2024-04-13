package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JavaToPlantUMLConverterTest {

    private JavaToPlantUMLConverter converter;

    @BeforeEach
    void setUp() {
        converter = new JavaToPlantUMLConverter();
    }

    @Test
    void testParseJavaFile() {
        converter.parseJavaFile("C:\\Users\\alals\\repos\\class-diagram-generator\\src\\main\\java\\org\\example");
        assertEquals(7, converter.getElements().size());
    }

    @Test
    void testParseInvalidJavaFile() {
        assertThrows(IllegalArgumentException.class, () -> converter.parseJavaFile("invalid.java"));
    }

    @Test
    void testGeneratePlantUML() {
        ClassElement classElement = new ClassElement();
        classElement.setClassName("TestClass");
        classElement.setFields(List.of("private int id", "private String name"));
        classElement.setMethods(List.of("public int getId()", "public String getName()"));
        converter.getElements().add(classElement);

        InterfaceElement interfaceElement = new InterfaceElement();
        interfaceElement.setInterfaceName("TestInterface");
        interfaceElement.setMethods(List.of("void doSomething()"));
        converter.getElements().add(interfaceElement);

        String expectedPlantUML = "class TestClass {\n" +
                "\tprivate int id\n" +
                "\tprivate String name\n" +
                "\n" +
                "\tpublic int getId()\n" +
                "\tpublic String getName()\n" +
                "}\n" +
                "interface TestInterface {\n" +
                "\tvoid doSomething()\n" +
                "}\n";
        assertEquals(expectedPlantUML, converter.generatePlantUML());
    }
}
