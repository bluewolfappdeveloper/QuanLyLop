package tiger.quanlylop.quanlylop.APublicLibrary;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringFormat {

    private static StringFormat instance = new StringFormat();

    public static StringFormat getInstance() {
        if(instance == null) {
            instance = new StringFormat();

        }
        return instance;
    }

    public StringFormat()
    {

    }

    public String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}
