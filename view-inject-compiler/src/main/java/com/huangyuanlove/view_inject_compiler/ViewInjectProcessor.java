package com.huangyuanlove.view_inject_compiler;

import com.google.auto.service.AutoService;
import com.huangyuanlove.view_inject_annotation.BindView;
import com.huangyuanlove.view_inject_annotation.ClickResponder;
import com.huangyuanlove.view_inject_annotation.IntentValue;
import com.huangyuanlove.view_inject_annotation.LongClickResponder;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

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
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

@AutoService(Processor.class)
public class ViewInjectProcessor extends AbstractProcessor {


    private Elements elementUtils;
    private Map<TypeElement, List<Element>> bindViewMap = new HashMap<>();
    private Map<TypeElement, List<Element>> clickResponderMap = new HashMap<>();
    private Map<TypeElement, List<Element>> longClickResponderMap = new HashMap<>();
    private Map<TypeElement, List<Element>> intentValueMap = new HashMap<>();


    private Map<TypeElement, TypeSpecWrapper> typeSpecWrapperMap = new HashMap<>();

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new LinkedHashSet<>();
        set.add(BindView.class.getCanonicalName());
        set.add(ClickResponder.class.getCanonicalName());
        set.add(LongClickResponder.class.getCanonicalName());
        set.add(IntentValue.class.getCanonicalName());
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
        bindViewMap.clear();
        clickResponderMap.clear();
        longClickResponderMap.clear();
        typeSpecWrapperMap.clear();
        intentValueMap.clear();

        Set<? extends Element> bindViewSet = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        Set<? extends Element> onClickSet = roundEnvironment.getElementsAnnotatedWith(ClickResponder.class);
        Set<? extends Element> onLongClickSet = roundEnvironment.getElementsAnnotatedWith(LongClickResponder.class);
        Set<? extends Element> intentValueSet = roundEnvironment.getElementsAnnotatedWith(IntentValue.class);


        collectBindViewInfo(bindViewSet);
        collectClickResponderInfo(onClickSet);
        collectLongClickResponderInfo(onLongClickSet);
        collectIntentValueInfo(intentValueSet);


        generateCode();


        for (Map.Entry<TypeElement, TypeSpecWrapper> entry : typeSpecWrapperMap.entrySet()) {

            entry.getValue().writeTo(processingEnv.getFiler());
        }

        return true;
    }

    private void collectIntentValueInfo(Set<? extends Element> elements) {
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element.getEnclosingElement();
            List<Element> elementList = intentValueMap.get(typeElement);
            if (elementList == null) {
                elementList = new ArrayList<>();
                intentValueMap.put(typeElement, elementList);
            }
            elementList.add(element);
        }
    }

    private void collectLongClickResponderInfo(Set<? extends Element> elements) {
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element.getEnclosingElement();
            List<Element> elementList = longClickResponderMap.get(typeElement);
            if (elementList == null) {
                elementList = new ArrayList<>();
                longClickResponderMap.put(typeElement, elementList);
            }
            elementList.add(element);
        }
    }


    private void collectBindViewInfo(Set<? extends Element> elements) {
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element.getEnclosingElement();
            List<Element> elementList = bindViewMap.get(typeElement);
            if (elementList == null) {
                elementList = new ArrayList<>();
                bindViewMap.put(typeElement, elementList);
            }
            elementList.add(element);
        }
    }

    private void collectClickResponderInfo(Set<? extends Element> elements) {
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element.getEnclosingElement();
            List<Element> elementList = clickResponderMap.get(typeElement);
            if (elementList == null) {
                elementList = new ArrayList<>();
                clickResponderMap.put(typeElement, elementList);
            }
            elementList.add(element);
        }
    }


    private void generateCode() {

        generateBindViewCode();
        generateClickResponderCode();
        generateOnLongClickResponderCode();
        generateIntentValueCode();

    }


    private MethodSpec.Builder generateConstructorMethodBuilder(TypeElement typeElement) {
        final String pkgName = getPackageName(typeElement);
        final String clsName = getClassName(typeElement, pkgName) + "$ViewInjector";
        TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(clsName)
                .addModifiers(Modifier.PUBLIC);

        TypeSpecWrapper typeSpecWrapper = typeSpecWrapperMap.get(typeElement);
        if (typeSpecWrapper == null) {
            typeSpecWrapper = new TypeSpecWrapper(typeSpecBuilder, pkgName);
            typeSpecWrapperMap.put(typeElement, typeSpecWrapper);
        }
        MethodSpec.Builder methodBuilder = typeSpecWrapper.getMethodBuilder(MethodSpec.constructorBuilder().build().name);
        if (methodBuilder == null) {
            methodBuilder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.get(typeElement.asType()), "target", Modifier.FINAL)
                    .addParameter(ClassName.get("android.view", "View"), "view")
                    .addStatement("int resourceID = 0");
            typeSpecWrapper.putMethodBuilder(methodBuilder);
        }
        return methodBuilder;

    }


    private void generateBindViewCode() {
        for (TypeElement typeElement : bindViewMap.keySet()) {
            MethodSpec.Builder methodBuilder = generateConstructorMethodBuilder(typeElement);

            List<Element> elements = bindViewMap.get(typeElement);
            for (Element element : elements) {
                processorBindView(element, methodBuilder);
            }
        }

    }


    private void generateOnLongClickResponderCode() {
        for (TypeElement typeElement : longClickResponderMap.keySet()) {
            MethodSpec.Builder methodBuilder = generateConstructorMethodBuilder(typeElement);

            List<Element> elements = longClickResponderMap.get(typeElement);
            for (Element element : elements) {
                processorLongClickResponder(element, methodBuilder);
            }


        }

    }


    private void generateClickResponderCode() {
        for (TypeElement typeElement : clickResponderMap.keySet()) {
            MethodSpec.Builder methodBuilder = generateConstructorMethodBuilder(typeElement);

            List<Element> elements = clickResponderMap.get(typeElement);
            for (Element element : elements) {
                processorClickResponder(element, methodBuilder);

            }
        }

    }


    private void generateIntentValueCode() {
        for (TypeElement typeElement : intentValueMap.keySet()) {
            MethodSpec.Builder methodBuilder = generateParseBundleMethodCode(typeElement);

            List<Element> elements = intentValueMap.get(typeElement);
            for (Element element : elements) {
                processorIntentValue(element, methodBuilder);
            }
        }

    }

    private MethodSpec.Builder generateParseBundleMethodCode(TypeElement typeElement) {
        final String pkgName = getPackageName(typeElement);
        final String clsName = getClassName(typeElement, pkgName) + "$ViewInjector";
        TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(clsName)
                .addModifiers(Modifier.PUBLIC);

        TypeSpecWrapper typeSpecWrapper = typeSpecWrapperMap.get(typeElement);
        if (typeSpecWrapper == null) {
            typeSpecWrapper = new TypeSpecWrapper(typeSpecBuilder, pkgName);
            typeSpecWrapperMap.put(typeElement, typeSpecWrapper);
        }
        MethodSpec.Builder methodBuilder = typeSpecWrapper.getMethodBuilder("parseBundle");
        if (methodBuilder == null) {

            ClassName className = ClassName.get("android.os", "Bundle");


            ParameterSpec parameterSpec = ParameterSpec.builder(className, "bundle")

                    .build();


            methodBuilder = MethodSpec.methodBuilder("parseBundle")
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.get(typeElement.asType()), "target")
                    .addParameter(parameterSpec)
                    .returns(void.class);

        }
        typeSpecWrapper.putMethodBuilder(methodBuilder);


        return methodBuilder;
    }


    private String getClassName(TypeElement type, String pkgName) {
        int packageLength = pkgName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLength).replace('.', '$');
    }

    private String getPackageName(TypeElement type) {
        return elementUtils.getPackageOf(type).getQualifiedName().toString();
    }


    private void processorBindView(Element element, MethodSpec.Builder methodBuilder) {
        VariableElement variableElement = (VariableElement) element;
        String varName = variableElement.getSimpleName().toString();
        String varType = variableElement.asType().toString();
        BindView bindView = variableElement.getAnnotation(BindView.class);

        int params = bindView.id();


        if (params <= 0) {
            String idStr = bindView.idStr();
            methodBuilder.addStatement("resourceID = view.getResources().getIdentifier($S,$S, view.getContext().getPackageName())", idStr, "id");

        } else {
            methodBuilder.addStatement("resourceID = ($L)", params);
        }
        methodBuilder.addStatement("target.$L = ($L) view.findViewById(resourceID)", varName, varType);

    }


    private void processorIntentValue(Element element, MethodSpec.Builder methodBuilder) {
        VariableElement variableElement = (VariableElement) element;
        String varName = variableElement.getSimpleName().toString();
        String varType = variableElement.asType().toString();


        IntentValue intentValue = variableElement.getAnnotation(IntentValue.class);

        switch (element.asType().getKind()) {
            case BOOLEAN:

                methodBuilder.beginControlFlow("if(bundle.containsKey($S))", intentValue.key())
                        .addStatement("target.$L = bundle.getBoolean($S)", varName, intentValue.key())
                        .endControlFlow();


                break;


            case SHORT:
                methodBuilder.beginControlFlow("if(bundle.containsKey($S))", intentValue.key())
                        .addStatement("target.$L = bundle.getShort($S)", varName, intentValue.key())
                        .endControlFlow();
                break;

            case BYTE:
                methodBuilder.beginControlFlow("if(bundle.containsKey($S))", intentValue.key())
                        .addStatement("target.$L = bundle.getByte($S)", varName, intentValue.key())
                        .endControlFlow();
                break;

            case INT:
                methodBuilder.beginControlFlow("if(bundle.containsKey($S))", intentValue.key())
                        .addStatement("target.$L = bundle.getInt($S)", varName, intentValue.key())
                        .endControlFlow();
                break;

            case CHAR:
                methodBuilder.beginControlFlow("if(bundle.containsKey($S))", intentValue.key())
                        .addStatement("target.$L = bundle.getChar($S)", varName, intentValue.key())
                        .endControlFlow();
                break;

            case LONG:
                methodBuilder.beginControlFlow("if(bundle.containsKey($S))", intentValue.key())
                        .addStatement("target.$L = bundle.getLong($S)", varName, intentValue.key())
                        .endControlFlow();
                break;

            case FLOAT:
                methodBuilder.beginControlFlow("if(bundle.containsKey($S))", intentValue.key())
                        .addStatement("target.$L = bundle.getFloat($S)", varName, intentValue.key())
                        .endControlFlow();
                break;

            case DOUBLE:
                methodBuilder.beginControlFlow("if(bundle.containsKey($S))", intentValue.key())
                        .addStatement("target.$L = bundle.getDouble($S)", varName, intentValue.key())
                        .endControlFlow();
                break;

            case ARRAY:
                break;

            case DECLARED:
                break;

        }


    }


    private void processorClickResponder(Element element, MethodSpec.Builder methodBuilder) {
        ExecutableElement executableElement = (ExecutableElement) element;
        ClickResponder clickView = executableElement.getAnnotation(ClickResponder.class);
        int[] ids = clickView.id();
        String[] idStrs = clickView.idStr();


        if (ids.length > 0) {

            for (int id : ids) {
                if (id == 0) {
                    continue;
                }
                MethodSpec innerMethodSpec = MethodSpec.methodBuilder("onClick")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(void.class)
                        .addParameter(ClassName.get("android.view", "View"), "v")
                        .addStatement("target.$L($L)", executableElement.getSimpleName().toString(), "v")
                        .build();
                TypeSpec innerTypeSpec = TypeSpec.anonymousClassBuilder("")
                        .addSuperinterface(ClassName.bestGuess("View.OnClickListener"))
                        .addMethod(innerMethodSpec)
                        .build();
                methodBuilder.addStatement("view.findViewById($L).setOnClickListener($L)", id, innerTypeSpec);
            }
        }
        if (idStrs.length > 0) {

            for (String idStr : idStrs) {
                if (idStr == null || idStr.length() <= 0) {
                    continue;
                }

                MethodSpec innerMethodSpec = MethodSpec.methodBuilder("onClick")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(void.class)
                        .addParameter(ClassName.get("android.view", "View"), "v")
                        .addStatement("target.$L($L)", executableElement.getSimpleName().toString(), "v")
                        .build();
                TypeSpec innerTypeSpec = TypeSpec.anonymousClassBuilder("")
                        .addSuperinterface(ClassName.bestGuess("View.OnClickListener"))
                        .addMethod(innerMethodSpec)
                        .build();

                methodBuilder.addStatement("resourceID = view.getResources().getIdentifier($S,$S, view.getContext().getPackageName())", idStr, "id");

                methodBuilder.addStatement("view.findViewById($L).setOnClickListener($L)", "resourceID", innerTypeSpec);

            }
        }
    }


    private void processorLongClickResponder(Element element, MethodSpec.Builder methodBuilder) {
        ExecutableElement executableElement = (ExecutableElement) element;

        LongClickResponder longClickResponder = executableElement.getAnnotation(LongClickResponder.class);
        int ids[] = longClickResponder.id();
        String idStrs[] = longClickResponder.idStr();
        if (ids.length > 0) {
            for (int id : ids) {
                if (id <= 0) {
                    continue;
                }

                MethodSpec innerMethodSpec = MethodSpec.methodBuilder("onLongClick")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(boolean.class)
                        .addParameter(ClassName.get("android.view", "View"), "v")
                        .addStatement("target.$L($L)", executableElement.getSimpleName().toString(), "v")
                        .addStatement("return true")
                        .build();
                TypeSpec innerTypeSpec = TypeSpec.anonymousClassBuilder("")
                        .addSuperinterface(ClassName.bestGuess("View.OnLongClickListener"))
                        .addMethod(innerMethodSpec)
                        .build();
                methodBuilder.addStatement("view.findViewById($L).setOnLongClickListener($L)", id, innerTypeSpec);


            }
        }


        if (idStrs.length > 0) {

            for (String idStr : idStrs) {
                if (idStr == null || idStr.length() <= 0) {
                    continue;
                }

                MethodSpec innerMethodSpec = MethodSpec.methodBuilder("onLongClick")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(boolean.class)
                        .addParameter(ClassName.get("android.view", "View"), "v")
                        .addStatement("target.$L($L)", executableElement.getSimpleName().toString(), "v")
                        .addStatement("return true")
                        .build();
                TypeSpec innerTypeSpec = TypeSpec.anonymousClassBuilder("")
                        .addSuperinterface(ClassName.bestGuess("View.OnLongClickListener"))
                        .addMethod(innerMethodSpec)
                        .build();

                methodBuilder.addStatement("resourceID = view.getResources().getIdentifier($S,$S, view.getContext().getPackageName())", idStr, "id");

                methodBuilder.addStatement("view.findViewById($L).setOnLongClickListener($L)", "resourceID", innerTypeSpec);

            }
        }


    }


}
