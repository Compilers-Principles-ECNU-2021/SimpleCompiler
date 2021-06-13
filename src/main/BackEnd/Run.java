import java.io.IOException;

public class Run {

    public  static FileProcess fileProcess = new FileProcess();
    public  static  Lexical lexical = new Lexical();
    public  static  Syntactic syntactic = new Syntactic();
    public  static  ParseTree parseTree = new ParseTree();
    public  static IdentifiersOperate identifiersOperate = new IdentifiersOperate();
    /**
     * 这是主程序
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //Scanner in = new Scanner()
//        String input=
//                "{"+
//                "a=2.23;\n" +
//                "while(a==8){\n" +
//                "if(1>2)\n" +
//                "then\n" +
//                "b=2*a;\n" +
//                "else\n" +
//                "{\n" +
//                "b=1+3;\n" +
//                "a=1;\n" +
//                "}\n" +
//                "}\n" +
//                "b=a/3;\n" +
//                "while(a<=2.23)\n" +
//                "a=2+(2*5);\n" +
//                "}\n";
        String sourceCode = "./test.txt";
        String usedGrammar = "./usedGrammar.txt";
        lexical.Init();
        lexical.LexAnalysis(sourceCode);
        for (Object str : lexical.getRes()) {
            System.out.println(str);
        }
        if (Lexical.success) {
            syntactic.Init();
            syntactic.syntacticAnalysis(lexical.getRes());
            parseTree.createDotGraph(syntactic.getUsedGrammar(),"DotGraph");
            //System.out.println(syntactic.getUsedGrammar());
        } else {
            System.out.println("词法错误，不进行语法分析");
        }

        fileProcess.FileWrite(usedGrammar, Syntactic.getUsedGrammar());
        boolean typeCheck = IdentifiersOperate.typeCheck(lexical.getRes());
        IdentifiersOperate.getIdentifiersMap().forEach((key, value) -> System.out.println("key: " + key + " value:" + value));
        if (typeCheck) {
            Semantic semantic = new Semantic(lexical.getRes(), IdentifiersOperate.getIdentifiersMap());
            Semantic.TacGenerate();
            String tacCode = "./tacCode.txt";
            fileProcess.FileWrite(tacCode, Semantic.tac);

            Compute compute = new Compute(IdentifiersOperate.getIdentifiersMap(),Semantic.tac);
            Compute.process();

            String resultCode = "./result.txt";

            fileProcess.FileWrite(resultCode, Compute.res);
        }
        else
            System.out.println("类型出错，不进行三地址和运算");
    }
}
