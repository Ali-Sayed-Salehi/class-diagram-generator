package org.example;

import java.util.List;

public class ClassElement implements Element {
    private String className;
    private List<String> fields;
    private List<String> methods;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
