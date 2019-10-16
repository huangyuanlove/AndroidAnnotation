package com.huangyuanlove.view_inject_compiler;

import com.huangyuanlove.view_inject_annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

public class ProxyInfo {
    private String packageName;
    private String proxyClassName;
    private TypeElement typeElement;

    public Map<ViewInject, VariableElement> injectVariables = new HashMap<>();

    public static final String PROXY = "ViewInject";

    public ProxyInfo(Elements elementUtils, TypeElement classElement) {
        this.typeElement = classElement;
        PackageElement packageElement = elementUtils.getPackageOf(classElement);
        String packageName = packageElement.getQualifiedName().toString();
        //classname
        String className = ClassValidator.getClassName(classElement, packageName);
        this.packageName = packageName;
        this.proxyClassName = className + "$$" + PROXY;
    }


    public String generateJavaCode() {
        System.out.println("generateJavaCode");
        StringBuilder builder = new StringBuilder();
        builder.append("// Generated code. Do not modify!\n");
        builder.append("package ").append(packageName).append(";\n\n");
        builder.append("import com.huangyuanlove.view_inject_api.*;\n");
        builder.append('\n');

        builder.append("public class ").append(proxyClassName).append(" implements " + ProxyInfo.PROXY + "<" + typeElement.getQualifiedName() + ">");
        builder.append(" {\n");

        generateMethods(builder);
        builder.append('\n');

        builder.append("}\n");
        return builder.toString();

    }


    private void generateMethods(StringBuilder builder) {
        builder.append("@Override\n ");
        builder.append("public void inject(" + typeElement.getQualifiedName() + " host, Object source ) {\n");
        for (ViewInject inject : injectVariables.keySet()) {
            VariableElement element = injectVariables.get(inject);
            String name = element.getSimpleName().toString();
            String type = element.asType().toString();

            builder.append(" if(source instanceof android.app.Activity){\n");
            builder.append("int id = 0;");
            if (inject.id() == -1) {
                builder.append("id =(((android.app.Activity)source).getResources().getIdentifier(\"");
                builder.append(inject.idStr());
                builder.append("\",\"id\", ((android.app.Activity)source).getPackageName()));");
            }else{
                builder.append("id = ");
                builder.append(inject.id());
                builder.append(";\n");
            }

            builder.append("host." + name).append(" = ");
            builder.append("(" + type + ")(((android.app.Activity)source).findViewById(  id ));\n");
            builder.append("\n}else{\n");
            builder.append("int id = 0;");
            if (inject.id() == -1) {
                builder.append("id =(((android.app.Activity)source).getResources().getIdentifier(\"");
                builder.append(inject.idStr());
                builder.append("\",\"id\", ((android.app.Activity)source).getPackageName()));");
            }else{
                builder.append("id = ");
                builder.append(inject.id());
                builder.append(";\n");
            }


            builder.append("host." + name).append(" = ");
            builder.append("(" + type + ")(((android.view.View)source).findViewById( id ));\n");
            builder.append("\n}");

        }
        builder.append("  }\n");


    }

    public String getProxyClassFullName() {
        return packageName + "." + proxyClassName;
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }
}
