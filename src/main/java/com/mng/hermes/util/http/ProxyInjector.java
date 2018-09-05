package com.mng.hermes.util.http;

import com.mng.hermes.util.HttpRequest;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;

@Component
public class ProxyInjector {

    private Object proxy;
    private HttpRequest wrapperClass;

    public ProxyInjector(HttpRequest wrapperClass) {
        this.wrapperClass = wrapperClass;
        scanClasspath();
    }

    private void scanClasspath() {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("com.mng.hermes"))
                .setScanners(new SubTypesScanner(), new TypeAnnotationsScanner(), new FieldAnnotationsScanner())
        );
        Set<Class<?>> assemblers = reflections.getTypesAnnotatedWith(HttpRequestAssembler.class);
        Set<Field> injectionPoints = reflections.getFieldsAnnotatedWith(HttpRequestAssembler.class);
        Class[] proxyClasses = assemblers.toArray(new Class[0]);
        proxy = Proxy.newProxyInstance(
                ProxyInjector.class.getClassLoader(),
                proxyClasses,
                new RequestInvoker()
        );
        for (Field ip : injectionPoints) {
            ip.setAccessible(true);
            try {
                ip.set(wrapperClass, ip.getType().cast(proxy));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
