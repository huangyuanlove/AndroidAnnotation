package com.huangyuanlove.view_inject_compiler.test;


import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        TypeSpec main = TypeSpec.classBuilder("TestAll")
                .addSuperinterface(ParameterizedTypeName.get((Comparable.class), String.class))
                .build();



        JavaFile javaFile = JavaFile.builder("com.huangyuanlove.view_inject_compiler.test", main).build();
        try {
            javaFile.writeTo(new File("view-inject-compiler/src/main/java"));
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

}
