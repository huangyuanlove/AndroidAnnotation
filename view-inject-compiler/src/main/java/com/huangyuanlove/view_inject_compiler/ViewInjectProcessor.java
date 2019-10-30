package com.huangyuanlove.view_inject_compiler;

import com.google.auto.service.AutoService;
import com.huangyuanlove.view_inject_annotation.BindView;
import com.huangyuanlove.view_inject_compiler.other.ProcessorUtils;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

@AutoService(Processor.class)
public class ViewInjectProcessor extends AbstractProcessor {


    private Messager messager;
    private Elements elementUtils;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementUtils = processingEnv.getElementUtils();
        messager = processingEnv.getMessager();
        filer= processingEnvironment.getFiler();

    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationTypes = new LinkedHashSet<String>();
        annotationTypes.add(BindView.class.getCanonicalName());
        return annotationTypes;
    }



    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        new ProcessorUtils(messager,elementUtils,filer).process(set,roundEnvironment);

        return false;
    }


}
