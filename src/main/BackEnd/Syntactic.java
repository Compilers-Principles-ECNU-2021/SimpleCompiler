import java.util.HashMap;
import java.util.*;

public class Syntactic {
    public static Stack stack=new Stack<String>();
    //要去查询的LLtable
    public static int[][] LLTable = new int[14][22];
    //要去置换的table
    public static HashMap tableReplace = new HashMap<Integer, String[]>();

    public static List grammar  = new ArrayList<String[]>();

    /**
     * 将LLTable初始化 将tableReplace初始化
     */
    void Init(){
        LLTable[0][0]=1;
        LLTable[1][0]=5;
        LLTable[1][2]=2;
        LLTable[1][7]=3;
        LLTable[1][8]=4;
        LLTable[2][0]=6;
        LLTable[3][0]=7;
        LLTable[3][1]=8;
        LLTable[3][2]=7;
        LLTable[3][7]=7;
        LLTable[3][8]=7;
        LLTable[4][2]=9;
        LLTable[5][7]=10;
        LLTable[6][8]=11;
        LLTable[7][3]=12;
        LLTable[7][8]=12;
        LLTable[7][20]=12;
        LLTable[8][11]=13;
        LLTable[8][12]=14;
        LLTable[8][13]=15;
        LLTable[8][14]=16;
        LLTable[8][15]=17;
        LLTable[9][3]=18;
        LLTable[9][8]=18;
        LLTable[9][20]=18;
        LLTable[10][4]=21;
        LLTable[10][10]=21;
        LLTable[10][11]=21;
        LLTable[10][12]=21;
        LLTable[10][13]=21;
        LLTable[10][14]=21;
        LLTable[10][15]=21;
        LLTable[10][16]=19;
        LLTable[10][17]=20;
        LLTable[11][3]=22;
        LLTable[11][8]=22;
        LLTable[11][20]=22;
        LLTable[12][4]=25;
        LLTable[12][10]=25;
        LLTable[12][11]=25;
        LLTable[12][12]=25;
        LLTable[12][13]=25;
        LLTable[12][14]=25;
        LLTable[12][15]=25;
        LLTable[12][16]=25;
        LLTable[12][17]=25;
        LLTable[12][18]=23;
        LLTable[12][19]=24;
        LLTable[13][3]=28;
        LLTable[13][8]=26;
        LLTable[13][20]=27;


        grammar .add(null);
        grammar .add(new String[]{"compoundstmt"});
        grammar .add(new String[]{"ifstmt"});
        grammar .add(new String[]{"whilestmt"});
        grammar .add(new String[]{"assgstmt"});
        grammar .add(new String[]{"compoundstmt"});
        grammar .add(new String[]{"0", "stmts", "1"});
        grammar .add(new String[]{"stmt", "stmts"});
        grammar .add(null);
        grammar .add(new String[]{"2", "3", "boolexpr", "4", "5", "stmt", "6", "stmt"});
        grammar .add(new String[]{"7", "3", "boolexpr", "4", "stmt"});
        grammar .add(new String[]{"8", "9", "arithexpr", "10"});
        grammar .add(new String[]{"arithexpr", "boolop", "arithexpr"});
        grammar .add(new String[]{"11"});
        grammar .add(new String[]{"12"});
        grammar .add(new String[]{"13"});
        grammar .add(new String[]{"14"});
        grammar .add(new String[]{"15"});
        grammar .add(new String[]{"multexpr", "arithexprprime"});
        grammar .add(new String[]{"16", "multexpr", "arithexprprime"});
        grammar .add(new String[]{"17", "multexpr", "arithexprprime"});
        grammar .add(null);
        grammar .add(new String[]{"simpleexpr", "multexprprime"});
        grammar .add(new String[]{"18", "simpleexpr", "multexprprime"});
        grammar .add(new String[]{"19", "simpleexpr", "multexprprime"});
        grammar .add(null);
        grammar .add(new String[]{"8"});
        grammar .add(new String[]{"20"});
        grammar .add(new String[]{"3", "arithexpr", "4"});

//        for(int i=1;i<grammar .size();i++){
//            tableReplace.put(i,grammar .get(i));
//        }
    }

    public Integer strToNum(String str) {
        Integer number = null;
        if (str.equals("program"))
            number = 0;
        if (str.equals("stmt"))
            number = 1;
        if (str.equals("compoundstmt"))
            number = 2;
        if (str.equals("stmts"))
            number = 3;
        if (str.equals("ifstmt"))
            number = 4;
        if (str.equals("whilestmt"))
            number = 5;
        if (str.equals("assgstmt"))
            number = 6;
        if (str.equals("boolexpr"))
            number = 7;
        if (str.equals("boolop"))
            number = 8;
        if (str.equals("arithexpr"))
            number = 9;
        if (str.equals("arithexprprime"))
            number = 10;
        if (str.equals("multexpr"))
            number = 11;
        if (str.equals("multexprprime"))
            number = 12;
        if (str.equals("simpleexpr"))
            number = 13;
        return number;
    }

    public String numToStr(String str){
        String res="";
        if(str.equals("0")) res="{";
        if(str.equals("1")) res="}";
        if(str.equals("2")) res="if";
        if(str.equals("3")) res="(";
        if(str.equals("4")) res=")";
        if(str.equals("5")) res="then";
        if(str.equals("6")) res="else";
        if(str.equals("7")) res="while";
        if(str.equals("8")) res="ID";
        if(str.equals("9")) res="=";
        if(str.equals("10")) res=";";
        if(str.equals("11")) res="<";
        if(str.equals("12")) res=">";
        if(str.equals("13")) res="<=";
        if(str.equals("14")) res=">=";
        if(str.equals("15")) res="==";
        if(str.equals("16")) res="+";
        if(str.equals("17")) res="-";
        if(str.equals("18")) res="*";
        if(str.equals("19")) res="/";
        if(str.equals("20")) res="Num";
        if(str.equals("21")) res="$";
        return  res;
    }
    public  boolean syntacticAnalysis(List<Token> tokenList){
      //  int res=0;
        int times=0;
        Token tempToken = new Token();
        boolean flag = true;
        //result:  1 没有错误   2是产生式中终结符，但代码不对应   3产生式中是非终结符，但ll（1）语法分析表没有此转化    4代码尾端多余   5代码在尾端缺少
        stack.push("program");
        String temp="";
        //int i=0;
        while((!stack.isEmpty())&&times<tokenList.size()) {
            temp = (String) stack.peek();
            tempToken = tokenList.get(times);

            //接收终结符
            if (temp.equals(String.valueOf(tempToken.getId()))) {
                stack.pop();
                times++;
                System.out.println("接收" + tempToken.getAttributeValue());
                continue;
            } else {
                //推导出非终结符，需要查LLTable
                if (strToNum(temp) != null) {
                    //将非终结符转化为数字，也就是二维表上的坐标 0到13
                    int nonTerminalId = strToNum(temp);

                    //如果有产生式的话，就使用
                    if (LLTable[nonTerminalId][tempToken.getId()] != 0) {
                        stack.pop();
                        int grammarId = LLTable[nonTerminalId][tempToken.getId()];

                        String[] tempGrammar = (String[]) grammar.get(grammarId);

                        //空产生式的情况
                        if(tempGrammar==null) {
                            System.out.println("使用的语法："+temp + "->" + "空");
                            continue;
                        }
                      //  System.out.println("length "+tempGrammar.length);
                        String tempGrammarForOut="";
                        for (int j = tempGrammar.length - 1; j >= 0; j--) {
                            if (tempGrammar[j] != null) {
                                if(tempGrammar[j].charAt(0)>='0'&&tempGrammar[j].charAt(0)<='9'){
                                    tempGrammarForOut+=numToStr(tempGrammar[j]);
                                }
                                else tempGrammarForOut+=tempGrammar[j];
                                stack.push(tempGrammar[j]);
                            }
                            System.out.println("使用的语法："+temp + "->" + tempGrammarForOut);
                            tempGrammarForOut="";
                        }
                    }
                    //产生式推出是非终结符，但代码中的相应位与其组成的对，在ll（1）语法分析表中没有此转化
                    else{
                        System.err.print("error in Line " + tempToken.getLineNumber() + " position" + tempToken.getLinePosition());
                        errorPrint(temp);
                        System.err.println("产生式推出是非终结符，但代码中的相应位与其组成的对，在ll（1）语法分析表中没有此转化，无法进行下去");
                        System.err.println(" ");
                        flag = false;
                        return false;
                    }
                }

                //推导出终结符，但是无法匹配
                else {
                    flag = false;
                    stack.pop();
                    times++;
                    System.out.println("error in Line " + tempToken.getLineNumber() + " position" + tempToken.getLinePosition());
                    continue;
                }
            }
        }
            if(times==tokenList.size()&&stack.isEmpty()){
                if(flag) return true;
            }
            else if(times==tokenList.size()){
//                System.out.println("stack size"+tokenList.size());
//                for (int i = 0; i < stack.size(); i++) {
//                    System.out.println(stack.get(i));
//                }
                System.out.println("代码在尾端缺少");
                return  false;
            }
            else{
                System.out.println("在" + tempToken.getLineNumber()+"行 "+tempToken.getLinePosition() + "位置 代码在尾端冗余");
                return  false;
            }
            return true;
        }
//    public Stack<String> replace(Stack<String> match, int num) {
//        match.pop();
//        String[] newStr = (String[]) tableReplace.get(num);
//        for (int i = newStr.length - 1; i >= 0; i--) {
//            if (newStr[i] != null) {
//                match.push(newStr[i]);
//            }
//        }
//        return match;
//    }
    public void errorPrint(String str) {
        if (str.equals("program"))
            System.err.println(",应换成 {");
        if (str.equals("stmt"))
            System.err.println(",建议换成 {,if,while,变量");
        if (str.equals("compoundstmt"))
            System.err.println(",建议换成 {");
        if (str.equals("stmts"))
            System.err.println(",建议换成 {,},if,while,变量");
        if (str.equals("ifstmt"))
            System.err.println(",建议换成 if");
        if (str.equals("whilestmt"))
            System.err.println(",建议换成 while");
        if (str.equals("assgstmt"))
            System.err.println(",建议换成 变量");
        if (str.equals("boolexpr"))
            System.err.println(",建议换成 (,数字或变量");
        if (str.equals("boolop"))
            System.err.println(",建议换成 运算符或条件判断符");
        if (str.equals("arithexpr"))
            System.err.println(",建议换成 (,数字或变量");
        if (str.equals("arithexprprime"))
            System.err.println(",建议换成 ),;,+,-,条件判断符");
        if (str.equals("multexpr"))
            System.err.println(",建议换成 (,数字或变量");
        if (str.equals("multexprprime"))
            System.err.println(",建议换成 ),;,*,/,+,-,条件判断符");
        if (str.equals("simpleexpr"))
            System.err.println(",建议换成 (,数字或变量");
    }
    public static void main(String[] args) {
        Syntactic syntactic = new Syntactic();
        syntactic.Init();
//        for(Map.Entry<Integer,String[]>)
        tableReplace.forEach((key,value)-> System.out.println("key: "+key+" value: "+Arrays.toString((String[])value)));

        System.out.println(tableReplace.size());

    }





}
