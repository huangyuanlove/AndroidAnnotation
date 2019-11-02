package com.huangyuanlove.view_inject_compiler;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.Filer;

/**
 * Description:
 * Author: huangyuan
 * Create on: 2019-10-31
 * Email: huangyuan@chunyu.me
 */
public class TypeSpecWrapper {

    private TypeSpec.Builder typeSpecBuilder;
    private String packageName;
    private HashMap<String, MethodSpec.Builder> methodBuildMap;


    public TypeSpec build(){
        for(Map.Entry<String,MethodSpec.Builder> entry:methodBuildMap.entrySet()){
            typeSpecBuilder.addMethod(entry.getValue().build());
        }
        return typeSpecBuilder.build();
    }

    public TypeSpec.Builder setTypeSpecBuilder(TypeSpec.Builder builder){
        this.typeSpecBuilder = builder;
        return builder;

    }

    public MethodSpec.Builder putMethodBuilder(MethodSpec.Builder builder){

        return methodBuildMap.put(builder.build().name,builder);
    }

    public MethodSpec.Builder getMethodBuilder(String methodName){
        return methodBuildMap.get(methodName);
    }

    public void writeTo(Filer filer){
        JavaFile javaFile = JavaFile.builder(packageName, build())
                .build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    public Map<String, MethodSpec.Builder> getMethodBuildMap(){
        return  methodBuildMap;
    }

    public TypeSpec.Builder getTypeSpecBuilder(){
        return typeSpecBuilder;
    }



    public TypeSpecWrapper(TypeSpec.Builder typeSpecBuilder,String packageName){
        this.typeSpecBuilder = typeSpecBuilder;
        this.packageName = packageName;
        methodBuildMap = new HashMap<>();
    }



}
