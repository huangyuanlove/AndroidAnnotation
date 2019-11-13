package com.huangyuanlove.view_inject_compiler;


import com.google.auto.service.AutoService;
import com.huangyuanlove.view_inject_annotation.RouterModule;
import com.huangyuanlove.view_inject_annotation.RouterPath;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.lang.reflect.Method;
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
import javax.lang.model.util.Elements;

@AutoService(Processor.class)
public class RouterProcessor extends AbstractProcessor {


    private Elements elementUtils;

    private Map<Element, TypeSpecWrapper> typeSpecWrapperMap = new HashMap<>();

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new LinkedHashSet<>();
        set.add(RouterModule.class.getCanonicalName());
        set.add(RouterPath.class.getCanonicalName());


        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        typeSpecWrapperMap.clear();

        Set<? extends Element> routerModuleSet = roundEnv.getElementsAnnotatedWith(RouterModule.class);

        for (Element element : routerModuleSet) {
            RouterModule routerModule = element.getAnnotation(RouterModule.class);
            TypeSpecWrapper typeSpecWrapper = typeSpecWrapperMap.get(element);
            if (typeSpecWrapper == null) {
                String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
                ClassName hashMapClassName = ClassName.bestGuess("java.util.HashMap");
                ClassName methodClassName = ClassName.bestGuess("java.lang.reflect.Method");
                ClassName stringClassName = ClassName.bestGuess("java.lang.String");
                ParameterizedTypeName routerMapClassName = ParameterizedTypeName.get(hashMapClassName, stringClassName, methodClassName);


                ClassName targetClassName = ClassName.get(packageName, element.getSimpleName().toString());


                 packageName = "com.huangyuanlove.router";


                TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(routerModule.schema() + routerModule.host() + "$$Router")
                        .addField(routerMapClassName, "routerMap", Modifier.PRIVATE)
                        .addField(targetClassName, "target");
                typeSpecWrapper = new TypeSpecWrapper(typeSpecBuilder, packageName);
                typeSpecWrapperMap.put(element, typeSpecWrapper);


                MethodSpec.Builder constructorBuilder = typeSpecWrapper.getMethodBuilder(MethodSpec.constructorBuilder().toString());
                if (constructorBuilder == null) {
                    constructorBuilder = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC).addException(Exception.class);
                }
                constructorBuilder.addStatement("this.target = $T.class.newInstance()", targetClassName)
                        .addStatement("this.routerMap = new $T()", routerMapClassName);


                typeSpecWrapper.putMethodBuilder(constructorBuilder);



                ClassName bundleClassName = ClassName.bestGuess("android.os.Bundle");
                ClassName contextClassName = ClassName.bestGuess("android.content.Context");

                MethodSpec.Builder invokeBuilder = MethodSpec.methodBuilder("invoke")
                        .addModifiers(Modifier.PUBLIC)
                        .addException(Exception.class)
                        .addParameter(contextClassName,"context")
                        .addParameter(String.class, "path")
                        .addParameter(bundleClassName, "bundle")
                        .beginControlFlow("if (this.routerMap.get($S)!=null)","path")
                        .addStatement("this.routerMap.get($S).invoke(this.target,context,bundle)","path")
                        .endControlFlow()
                        .beginControlFlow("else")

                        .addStatement("throw new Exception($S + path + $S + target.getClass().getSimpleName())","can not find path: "," in")
                        .endControlFlow()
                        .returns(void.class);

                typeSpecWrapper.putMethodBuilder(invokeBuilder);


            }


            List<? extends Element> lists = element.getEnclosedElements();
            for (Element element1 : lists) {

                RouterPath routerPath = element1.getAnnotation(RouterPath.class);
                if (routerPath != null) {
                    System.out.println(element1.getSimpleName().toString());
                    System.out.println(routerPath.value());
                }

            }

        }


        for (Map.Entry<Element, TypeSpecWrapper> entry : typeSpecWrapperMap.entrySet()) {

            entry.getValue().writeTo(processingEnv.getFiler());
        }


        return true;
    }
}
