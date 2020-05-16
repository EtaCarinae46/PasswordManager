package controller.fxCtrls;

import java.util.Random;
import org.apache.commons.lang.ArrayUtils;

public class PasswordGenerator {

    private int length;
    private char[] charSet;

    public PasswordGenerator(int length, char... charSet) {
        this.length = length;
        this.charSet = charSet;
    }

    public String generate() {
        if (length < 0 || charSet == null || charSet.length == 0)
            return "";
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int rand = r.nextInt(charSet.length);
            char c = charSet[rand];
            sb.append(c);
        }
        return sb.toString();
    }

    public int getLength() {
        return this.length;
    }

    public static char[] getUpperCase() {
        return getChars(65, 26);
    }

    public static char[] getLowerCase() {
        return getChars(97, 26);
    }

    public static char[] getDigits() {
        return getChars(48, 10);
    }

    public static char[] getSpecial() {
        return new char[] {
                '!', '#', '$', '%', '&', '*', '+', '-', '.', '/',
                '?', '<', '>', '@', '~', ':', ';', '_' };
    }

    private static char[] getChars(int s, int l) {
        char[] letters = new char[l];
        for (int i = 0; i < l; i++)
            letters[i] = (char)(i + s);
        return letters;
    }

    public static char[] concat(char[]... arr) {
        char[] retVal = null;
        for (char[] c : arr)
            retVal = ArrayUtils.addAll(retVal, c);
        return retVal;
    }
}
