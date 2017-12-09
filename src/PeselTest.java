import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class PeselTest {
    private Method validate;

    private Method getMethod(String methodName, Class result) throws NoSuchMethodException {
        Method method = PESEL.class.getDeclaredMethod(methodName, result);
        method.setAccessible(true);
        return method;
    }

    @Before
    public void getValidateMethod() {
        try {
            validate = getMethod("validate", String.class);
        } catch (NoSuchMethodException e) {
            System.out.println("Method not found.");
        }
    }

    @Test
    public void testPeselValidation() {
        Arrays.stream(SomePesels.pesels).forEach(p -> {
            try {
                System.out.println(p + " is " + ((boolean) validate.invoke(null, p) ? "VALID" : "INVALID"));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void testDateExtraction() {
        Method extractDate;
        try {
            extractDate = getMethod("extractDate", String.class);
        } catch (NoSuchMethodException e) {
            System.out.println("Method not found.");
            return;
        }

        Arrays.stream(SomePesels.pesels)
                .filter(p -> {
                    try {
                        return (boolean) validate.invoke(null, p);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        return false;
                    }
                })
                .forEach(p -> {
                    try {
                        System.out.println(p + " was born at " + extractDate.invoke(null, p));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    public void testSexExtraction() {
        Method extractSex;
        try {
            extractSex = getMethod("extractSex", String.class);
        } catch (NoSuchMethodException e) {
            System.out.println("Method not found.");
            return;
        }
        Arrays.stream(SomePesels.pesels)
                .filter(p -> {
                    try {
                        return (boolean) validate.invoke(null, p);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        return false;
                    }
                })
                .forEach(p -> {
                    try {
                        System.out.println(p + " is of " + extractSex.invoke(null, p));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                });
    }


}
