import java.time.DateTimeException;
import java.time.LocalDate;

public class PESEL {

    private static final String PESEL_REGEX = "[0-9]{2}([02468][0-9]|[13579][0-2])(0[1-9]|[12][0-9]|3[0-1])[0-9]{5}";

    private static boolean validate(String pesel) {
        if(!pesel.matches(PESEL_REGEX)) return false;

        try {
            extractDate(pesel);
        } catch (PeselException e) {
            return false;
        }

        return Character.getNumericValue(pesel.charAt(10)) == getControlNumber(pesel);
    }

    private static int getControlNumber(String pesel) {
        return (Character.getNumericValue(pesel.charAt(0)) * 9 +
        Character.getNumericValue(pesel.charAt(1)) * 7 +
        Character.getNumericValue(pesel.charAt(2)) * 3 +
        Character.getNumericValue(pesel.charAt(3)) +
        Character.getNumericValue(pesel.charAt(4)) * 9 +
        Character.getNumericValue(pesel.charAt(5)) * 7 +
        Character.getNumericValue(pesel.charAt(6)) * 3 +
        Character.getNumericValue(pesel.charAt(7)) +
        Character.getNumericValue(pesel.charAt(8)) * 9 +
        Character.getNumericValue(pesel.charAt(9)) * 7) %10;
    }

    private static LocalDate extractDate(String pesel) throws PeselException {
        int day = Integer.parseInt(pesel.substring(4,6));
        int year = Integer.parseInt(pesel.substring(0,2));
        int month = Integer.parseInt(pesel.substring(2,4));

        if (month >= 80) {
            year += 1800;
            month -= 80;
        }
        else if(month >= 60) {
            year += 2200;
            month -= 60;
        }
        else if(month >= 40) {
            year += 2100;
            month -= 60;
        }
        else if(month >= 20) {
            year += 2000;
            month -= 20;
        }
        else
            year += 1900;
        try {
            return LocalDate.of(year, month, day);
        }
        catch (DateTimeException e) {
            throw new PeselException(e);
        }
    }

    private Sex extractSex(String pesel) {
        if(pesel.charAt(9) % 2 == 0)
            return Sex.Female;
        else
            return Sex.Male;
    }
}
