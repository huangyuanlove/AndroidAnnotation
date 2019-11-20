package com.huangyuanlove.view_inject_compiler;

import com.google.auto.service.AutoService;
import com.huangyuanlove.view_inject_annotation.NeedPermission;
import com.huangyuanlove.view_inject_annotation.OnDeny;
import com.huangyuanlove.view_inject_annotation.OnNeverAskAgain;
import com.huangyuanlove.view_inject_annotation.RuntimePermission;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

@AutoService(Processor.class)
public class PermissionProcessor  extends AbstractProcessor {


    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new LinkedHashSet<>();

        set.add(RuntimePermission.class.getCanonicalName());
        set.add(NeedPermission.class.getCanonicalName());
        set.add(OnDeny.class.getCanonicalName());
        set.add(OnNeverAskAgain.class.getCanonicalName());


        return set  ;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return true;
    }
}
