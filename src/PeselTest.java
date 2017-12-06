import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class PeselTest {
    private Method getMethod(String methodName, Class result) throws NoSuchMethodException {
        Method method = PESEL.class.getDeclaredMethod(methodName, result);
        method.setAccessible(true);
        return method;
    }

    @Test
    public void testPeselValidation() {
        Method validate;
        try {
            validate = getMethod("validate", String.class);
        } catch (NoSuchMethodException e) {
            System.out.println("Method not found.");
            return;
        }
        Arrays.stream(SomePesels.pesels).forEach(p -> {
            try {
                System.out.println(p + " is " + ((boolean) validate.invoke(null, p) ? "VALID" : "INVALID"));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }


}
