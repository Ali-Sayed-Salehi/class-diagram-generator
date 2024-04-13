package org.example;

public interface Visitor {
    void visit(ClassElement c);
    void visit(InterfaceElement i);
}
