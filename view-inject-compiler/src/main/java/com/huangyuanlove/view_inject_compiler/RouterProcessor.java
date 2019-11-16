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
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
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


                TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(routerModule.schema() + routerModule.host() + "$$Router")
                        .addField(routerMapClassName, "routerMap", Modifier.PRIVATE)
                        .addField(targetClassName, "target");
                typeSpecWrapper = new TypeSpecWrapper(typeSpecBuilder, "com.huangyuanlove.router");
                typeSpecWrapperMap.put(element, typeSpecWrapper);


                MethodSpec.Builder constructorBuilder = typeSpecWrapper.getMethodBuilder(MethodSpec.constructorBuilder().toString());
                if (constructorBuilder == null) {
                    constructorBuilder = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC).addException(Exception.class);
                }
                constructorBuilder.addStatement("this.target =  new $T()", targetClassName)
                        .addStatement("this.routerMap = new $T()", routerMapClassName);


                List<? extends Element> lists = element.getEnclosedElements();
                for (Element element1 : lists) {
                    ExecutableElement temp = (ExecutableElement)element1;

                    RouterPath routerPath = temp.getAnnotation(RouterPath.class);
                    if (routerPath != null) {


                        constructorBuilder.addStatement("this.routerMap.put($S,target.getClass().getMethod($S,$L))",routerPath.value(),element1.getSimpleName().toString(),paramsClassString(temp));
                        System.out.println(routerPath.value());



                    }

                }


                typeSpecWrapper.putMethodBuilder(constructorBuilder);


                ClassName paramWrapperName = ClassName.bestGuess("com.huangyuanlove.view_inject_api.router.RouterParamWrapper");
                ClassName routerDelegateName = ClassName.bestGuess("com.huangyuanlove.view_inject_api.router.RouterDelegate");

                MethodSpec.Builder invokeBuilder = MethodSpec.methodBuilder("invoke")
                        .addModifiers(Modifier.PUBLIC)
                        .addException(Exception.class)
                        .addParameter(String.class, "path")
                        .addParameter(paramWrapperName, "paramWrapper")
                        .addStatement("$T method = this.routerMap.get($L)",methodClassName,"path")
                        .beginControlFlow("if(method == null)")
                        .addStatement(" throw new Exception(\"can not find method which map \" +path)")
                        .endControlFlow()
                        .addStatement("return $T.invoke(method,target,paramWrapper)",routerDelegateName)
                        .returns(Object.class);

                typeSpecWrapper.putMethodBuilder(invokeBuilder);


            }




        }


        for (Map.Entry<Element, TypeSpecWrapper> entry : typeSpecWrapperMap.entrySet()) {

            entry.getValue().writeTo(processingEnv.getFiler());
        }


        return true;
    }

    private String paramsClassString(ExecutableElement temp ){

       if(temp == null){
           return null;
       }

        List<? extends VariableElement> parameters = temp.getParameters();

       if(parameters ==null || parameters.size() == 0){
           return  null;
       }

        String[] result = new String[parameters.size()];

       StringBuilder sb = new StringBuilder();

        for (int i = 0; i < parameters.size(); i++) {
            result[i] = parameters.get(i).asType().toString() +".class";
            sb.append(parameters.get(i).asType().toString() +".class");
            sb.append(",");
        }

        return sb.deleteCharAt(sb.length()-1).toString();



    }

}
