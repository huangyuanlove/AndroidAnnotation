package com.huangyuanlove.view_inject_compiler;


import com.google.auto.service.AutoService;
import com.huangyuanlove.view_inject_annotation.RouterModule;
import com.huangyuanlove.view_inject_annotation.RouterPath;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

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

        Set<? extends Element> routerModuleSet = roundEnv.getElementsAnnotatedWith(RouterModule.class);

        for (Element element : routerModuleSet) {
            RouterModule routerModule = element.getAnnotation(RouterModule.class);
            TypeSpecWrapper typeSpecWrapper = typeSpecWrapperMap.get(element);
            if (typeSpecWrapper == null) {
                String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
                TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(routerModule.schema() + "$$" + routerModule.host());
                typeSpecWrapper = new TypeSpecWrapper(typeSpecBuilder, packageName);
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


        return true;
    }
}
