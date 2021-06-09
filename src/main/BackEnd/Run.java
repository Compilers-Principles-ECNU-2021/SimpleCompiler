import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Run {

    public  static FileProcess fileProcess = new FileProcess();
    public  static  Lexical lexical = new Lexical();
    /**
     * 这是主程序
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {


        //Scanner in = new Scanner()
        String input="//初始化\n" +
                "//定义变量b\n" +
                "int a,b,c;\n" +
                "real d,e,f;\n" +
                "{\n" +
                "a=2.23;\n" +
                "while(a==8){\n" +
                "if(1>2)\n" +
                "then\n" +
                "b=2*a;\n" +
                "else\n" +
                "{\n" +
                "b=1+3;\n" +
                "a=1;\n" +
                "}\n" +
                "}\n" +
                "b=a/3;\n" +
                "while(a<=2.23)\n" +
                "a=2+(2*5);\n" +
                "}\n";
        String path="./test.txt";

        fileProcess.FileWrite(path,input);

        lexical.Init();
        lexical.LexAnalysis(path);
        for(Object str:lexical.getRes()){
            System.out.println(str);
        }



//        String str="3.0e+10";
//        int e=10;
//
//        String regx = "\\d+\\.?\\d*[Ee]*[+-]*\\d+";
//        Pattern pattern = Pattern.compile(regx);
//        boolean isNumber = pattern.matcher(str).matches();
//        System.out.println(isNumber);
//        regx = "^[-\\+]?[.\\d]*$";
//        pattern = Pattern.compile(regx);
//        System.out.println(pattern.matcher(str).matches());


        //数字的浮点数 没有. e E + -
        //   int的匹配   [\d]
        //   real的匹配   [0-9]* (.)? [0-9]* (e|E)? [0-9]+
    }
}
