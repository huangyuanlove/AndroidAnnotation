package com.huangyuanlove.view_inject_compiler;

import com.google.auto.service.AutoService;
import com.huangyuanlove.view_inject_annotation.ViewInject;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class ViewInjectProcessor extends AbstractProcessor {


    private Messager messager;
    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementUtils = processingEnv.getElementUtils();
        messager = processingEnv.getMessager();

    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationTypes = new LinkedHashSet<String>();
        annotationTypes.add(ViewInject.class.getCanonicalName());
        return annotationTypes;
    }


    private Map<String, ProxyInfo> proxyInfoMap = new HashMap<>();

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        proxyInfoMap.clear();
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ViewInject.class);
        for (Element element : elements) {
            checkAnnotationValid(element, ViewInject.class);
            VariableElement variableElement = (VariableElement) element;
            //class type
            TypeElement classElement = (TypeElement) variableElement.getEnclosingElement();
            //full class name
            String fqClassName = classElement.getQualifiedName().toString();

            ProxyInfo proxyInfo = proxyInfoMap.get(fqClassName);
            if (proxyInfo == null) {
                proxyInfo = new ProxyInfo(elementUtils, classElement);
                proxyInfoMap.put(fqClassName, proxyInfo);
            }

            ViewInject viewInject = variableElement.getAnnotation(ViewInject.class);
            int id = viewInject.id();
            proxyInfo.injectVariables.put(viewInject, variableElement);


        }

        for (String key : proxyInfoMap.keySet())
        {
            ProxyInfo proxyInfo = proxyInfoMap.get(key);
            try
            {
                System.out.println("ViewInjectProcessor create file "+proxyInfo.getProxyClassFullName());
                JavaFileObject jfo = processingEnv.getFiler().createSourceFile(
                        proxyInfo.getProxyClassFullName(),
                        proxyInfo.getTypeElement());
                Writer writer = jfo.openWriter();
                writer.write(proxyInfo.
                        generateJavaCode());
                writer.flush();
                writer.close();
            } catch (IOException e)
            {
                e.printStackTrace();

                error(proxyInfo.getTypeElement(),
                        "Unable to write injector for type %s: %s",
                        proxyInfo.getTypeElement(), e.getMessage());
            }

        }

        return false;
    }

    private boolean checkAnnotationValid(Element annotatedElement, Class clazz) {
        if (annotatedElement.getKind() != ElementKind.FIELD) {
            error(annotatedElement, "%s must be declared on field.", clazz.getSimpleName());
            return false;
        }
        if (ClassValidator.isPrivate(annotatedElement)) {
            error(annotatedElement, "%s() must can not be private.", annotatedElement.getSimpleName());
            return false;
        }

        return true;
    }

    private void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message, element);
    }
}
