package helloJava.variable;

import java.lang.reflect.Type;

public class ChangeVariable {
    public static void main(String[] args) {
        long longVar = 500000000L;
        float fVar = longVar;
        double dVar = longVar;

        System.out.println("longVar Type : " + ((Object)longVar).getClass());
        System.out.println("fVar Type    : " + ((Object)fVar).getClass());
        System.out.println("dVar Type    : " + ((Object)dVar).getClass());
    }

}
