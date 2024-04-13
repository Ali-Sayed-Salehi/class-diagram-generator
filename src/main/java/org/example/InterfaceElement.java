package org.example;

import java.util.List;

public class InterfaceElement implements Element {
    private String interfaceName;
    private List<String> methods;

    // Constructor, getters, and setters
    // ...

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
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
