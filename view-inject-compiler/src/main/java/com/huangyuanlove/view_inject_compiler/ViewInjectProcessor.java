package com.huangyuanlove.view_inject_compiler;

import com.google.auto.service.AutoService;
import com.huangyuanlove.view_inject_annotation.BindView;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

@AutoService(Processor.class)
public class ViewInjectProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Map<TypeElement, List<VariableElement>> map = new HashMap<>();

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new LinkedHashSet<>();
        set.add(BindView.class.getCanonicalName());
        return set;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        map.clear();

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindView.class);

        collectInfo(elements);
        generateCode();

        return true;
    }

    private void collectInfo(Set<? extends Element> elements) {
        for (Element element : elements) {
            VariableElement variableElement = (VariableElement) element;
            TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();

            List<VariableElement> variableElementList = map.get(typeElement);
            if (variableElementList == null) {
                variableElementList = new ArrayList<>();
                map.put(typeElement, variableElementList);
            }
            variableElementList.add(variableElement);
        }
    }

    private void generateCode() {
        for (TypeElement typeElement : map.keySet()) {
            MethodSpec.Builder methodBuilder = MethodSpec.constructorBuilder()

                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.get(typeElement.asType()), "target")
                    .addParameter(ClassName.get("android.view", "View"), "view")
                    .addStatement("int resourceID = 0;\n");

            List<VariableElement> variableElementList = map.get(typeElement);
            for (VariableElement variableElement : variableElementList) {
                String varName = variableElement.getSimpleName().toString();
                String varType = variableElement.asType().toString();
                BindView bindView = variableElement.getAnnotation(BindView.class);
                int params = bindView.id();


                if (params <= 0) {
                    String idStr = bindView.idStr();

                    String s = "resourceID = view.getResources().getIdentifier(\"" + idStr + "\", \"id\", view.getContext().getPackageName())";
                    methodBuilder.addStatement(s);

                } else {
                    methodBuilder.addStatement("resourceID = ($L)", params);
                }
                methodBuilder.addStatement("target.$L = ($L) view.findViewById(resourceID)", varName, varType);


            }

            final String pkgName = getPackageName(typeElement);
            final String clsName = getClassName(typeElement, pkgName) + "$ViewInjector";

            TypeSpec typeSpec = TypeSpec.classBuilder(clsName)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(methodBuilder.build())
                    .build();

            JavaFile javaFile = JavaFile.builder(pkgName, typeSpec)
                    .build();

            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getPackageName(TypeElement type) {
        return elementUtils.getPackageOf(type).getQualifiedName().toString();
    }

    private String getClassName(TypeElement type, String pkgName) {
        int packageLength = pkgName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLength).replace('.', '$');
    }

}
