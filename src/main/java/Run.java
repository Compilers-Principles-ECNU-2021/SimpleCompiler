import java.util.regex.Pattern;

public class Run {
    public static void main(String[] args) {
        String str="3.0e+10";
        int e=10;

        String regx = "\\d+\\.?\\d*[Ee]*[+-]*\\d+";
        Pattern pattern = Pattern.compile(regx);
        boolean isNumber = pattern.matcher(str).matches();
        System.out.println(isNumber);
//        regx = "^[-\\+]?[.\\d]*$";
//        pattern = Pattern.compile(regx);
//        System.out.println(pattern.matcher(str).matches());


        //数字的浮点数 没有. e E + -
        //   int的匹配   [\d]
        //   real的匹配   [0-9]* (.)? [0-9]* (e|E)? [0-9]+
    }
}
