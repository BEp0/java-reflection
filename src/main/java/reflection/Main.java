package reflection;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

public class Main {

    public static final String REFLECTION_DOG = "reflection.Dog";
    public static final String PARAMETER_NAME = "sayHiTo";

    public static void main(String[] args) {
        try {
            exampleGetDeclaredConstructors();
            exampleConstructorByBuilder();
            exampleMethod();
            exampleMethodWithParameters();
            exampleInvokeWithMethodManipulator();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void exampleInvokeWithMethodManipulator() {
        var instance = Reflection
                .reflection(REFLECTION_DOG)
                .constructor("Little Dog")
                .useMethod("sayHiTo")
                .handleException((method, error) -> {
                    System.out.println("Error on method: " + method.getName());
                    throw new RuntimeException("Error on method: " + method.getName());
                })
                .invokeMethod("Vii");
        System.out.println(instance);
    }

    private static void exampleMethodWithParameters() throws InvocationTargetException, IllegalAccessException {
        var myClass = Reflection
                .reflection(REFLECTION_DOG)
                .getReflectedClass();

        var instance = Reflection
                .reflection(REFLECTION_DOG)
                .constructor(String.class)
                .build("Little Dog");

        var list = Stream.of(myClass.getMethods())
                .filter(method -> PARAMETER_NAME.equals(method.getName()))
                .toList();

        System.out.println(list.get(0).invoke(instance, "Jorge"));

    }

    private static void exampleMethod() throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {

        var myClass = Class.forName(REFLECTION_DOG);

        var declaredMethod = myClass.getDeclaredMethods()[0];
        var invoke = Reflection
                .reflection(REFLECTION_DOG)
                .constructor()
                .build();
        System.out.println(declaredMethod.invoke(invoke));
    }

    private static void exampleConstructorByBuilder() {
        var invoke = Reflection
                .reflection(REFLECTION_DOG)
                .constructor()
                .build();
        System.out.println(invoke);
    }

    private static void exampleGetDeclaredConstructors() throws ClassNotFoundException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException,
            InvocationTargetException {
        var dogClass = Class.forName(REFLECTION_DOG);
        var simpleConstructor = dogClass.getDeclaredConstructor();
        var constructorWithString = dogClass.getDeclaredConstructor(String.class);

        var dogao = simpleConstructor.newInstance();
        var dog = constructorWithString.newInstance("Dog Dog");

        System.out.println(dogao);
        System.out.println(dog);
    }
}