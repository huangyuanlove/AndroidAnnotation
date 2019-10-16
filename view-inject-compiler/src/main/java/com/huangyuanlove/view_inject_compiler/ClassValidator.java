package com.huangyuanlove.view_inject_compiler;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
public class ClassValidator {
    static boolean isPrivate(Element annotatedClass)
    {
        return annotatedClass.getModifiers().contains(Modifier.PRIVATE);
    }

    static String getClassName(TypeElement type, String packageName)
    {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen)
                .replace('.', '$');
    }
}
