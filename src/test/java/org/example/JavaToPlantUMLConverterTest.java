package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
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
    void testParseJavaFiles(@TempDir Path tempDir) throws IOException {
        // Create sample .java files in the temporary directory
        createJavaFile(tempDir.toFile(), "TestClass.java", "class TestClass {}");
        createJavaFile(tempDir.toFile(), "TestInterface.java", "interface TestInterface {}");

        // Parse the .java files
        JavaToPlantUMLConverter converter = new JavaToPlantUMLConverter();
        converter.parseJavaFiles(tempDir.toString());

        // Assert that the number of elements parsed is correct
        assertEquals(2, converter.getElements().size());

    }

    private void createJavaFile(File directory, String fileName, String content) throws IOException {
        File file = new File(directory, fileName);
        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.close();
    }

    @Test
    void testParseJavaFile() {
        converter.parseJavaFile(
                "C:\\Users\\alals\\repos\\class-diagram-generator\\src\\main\\java\\org\\example\\ClassElement.java");
        assertEquals(1, converter.getElements().size());
    }

    @Test
    void testCreateJavaElementWithClass() {
        JavaToPlantUMLConverter converter = new JavaToPlantUMLConverter();
        String classContent = "class TestClass {\n" +
                "\tprivate int id\n" +
                "\tprivate String name\n" +
                "\n" +
                "\tpublic int getId()\n" +
                "\tpublic String getName()\n" +
                "}\n";
        Element element = converter.createJavaElement(classContent);
        assertEquals(ClassElement.class, element.getClass());
        assertEquals("TestClass", ((ClassElement) element).getClassName());
    }

    @Test
    void testCreateJavaElementWithInterface() {
        JavaToPlantUMLConverter converter = new JavaToPlantUMLConverter();
        String interfaceContent = "interface TestInterface {\n" +
                "\tvoid doSomething()\n" +
                "}\n";
        Element element = converter.createJavaElement(interfaceContent);
        assertEquals(InterfaceElement.class, element.getClass());
        assertEquals("TestInterface", ((InterfaceElement) element).getInterfaceName());
    }

    @Test
    void testCreateJavaElementWithInvalidContent() {
        JavaToPlantUMLConverter converter = new JavaToPlantUMLConverter();
        String invalidContent = "invalid content";
        assertThrows(IllegalArgumentException.class, () -> converter.createJavaElement(invalidContent));
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
                "\tpublic int getId()\n" +
                "\tpublic String getName()\n" +
                "}\n" +
                "interface TestInterface {\n" +
                "\tvoid doSomething()\n" +
                "}\n";
        assertEquals(expectedPlantUML, converter.generatePlantUML());
    }
}
