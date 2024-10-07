package helloJava.dynamicProxy;

import java.lang.reflect.Proxy;

public class JdkDynamicProxyMain {
    public static void main(String[] args) {
        dynamicA();
        dynamicB();
    }
    static void dynamicA() {
        AInterface target = new AImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target); // 동적 프록시에 적용할 핸들러 로직
        AInterface proxy = (AInterface) Proxy.newProxyInstance( // 동적 프록시는 java.lang.reflact.Proxy를 통해 생성 : 해당 인터페이스를 기반을 동적프록시 생성, 결과 반ㅠ
                AInterface.class.getClassLoader(),  // 클래스 로더 정보
                new Class[]{AInterface.class}, // 인터페이스
                handler); // 핸들러로직
        proxy.call();
        System.out.println("targetClass = " + target.getClass());
        System.out.println("proxyClass = " + proxy.getClass());
    }

    static void dynamicB() {
        BInterface target = new BImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);
        BInterface proxy = (BInterface) Proxy.newProxyInstance(
                BInterface.class.getClassLoader(),
                new Class[]{BInterface.class},
                handler);
        proxy.call();
        System.out.println("targetClass = " + target.getClass());
        System.out.println("proxyClass = " + proxy.getClass());

    }
}
