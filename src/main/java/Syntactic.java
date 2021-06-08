import java.util.HashMap;
import java.util.*;

public class Syntactic {
    public static Stack match=new Stack<String>();
    //要去查询的LLtable
    public static int[][] LLTable = new int[14][22];
    //要去置换的table
    public static HashMap tableReplace = new HashMap<Integer, String[]>();

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
        LLTable[12][5]=25;
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

        List list = new ArrayList<String[]>();
        list.add(null);
        list.add(new String[]{"compoundstmt"});
        list.add(new String[]{"ifstmt"});
        list.add(new String[]{"whilestmt"});
        list.add(new String[]{"assgstmt"});
        list.add(new String[]{"compoundstmt"});
        list.add(new String[]{"0", "stmts", "1"});
        list.add(new String[]{"stmt", "stmts"});
        list.add(null);
        list.add(new String[]{"2", "3", "boolexpr", "4", "5", "stmt", "6", "stmt"});
        list.add(new String[]{"7", "3", "boolexpr", "4", "stmt"});
        list.add(new String[]{"8", "9", "arithexpr", "10"});
        list.add(new String[]{"arithexpr", "boolop", "arithexpr"});
        list.add(new String[]{"11"});
        list.add(new String[]{"12"});
        list.add(new String[]{"13"});
        list.add(new String[]{"14"});
        list.add(new String[]{"15"});
        list.add(new String[]{"multexpr", "arithexprprime"});
        list.add(new String[]{"16", "multexpr", "arithexprprime"});
        list.add(new String[]{"17", "multexpr", "arithexprprime"});
        list.add(null);
        list.add(new String[]{"simpleexpr", "multexprprime"});
        list.add(new String[]{"18", "simpleexpr", "multexprprime"});
        list.add(new String[]{"19", "simpleexpr", "multexprprime"});
        list.add(null);
        list.add(new String[]{"8"});
        list.add(new String[]{"20"});
        list.add(new String[]{"3", "arithexpr", "4"});

        for(int i=1;i<list.size();i++){
            tableReplace.put(i,list.get(i));
        }
    }

    public Integer StringToInt(String str) {
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

    public static void syntacticAnalysis(List<Token> tokenList){
        int res=0;
        //result:  1 没有错误   2是产生式中终结符，但代码不对应   3产生式中是非终结符，但ll（1）语法分析表没有此转化    4代码尾端多余   5代码在尾端缺少


        //match.push();
    }
    public Stack<String> replace(Stack<String> match, int num) {
        match.pop();
        String[] newStr = (String[]) tableReplace.get(num);
        for (int i = newStr.length - 1; i >= 0; i--) {
            if (newStr[i] != null) {
                match.push(newStr[i]);
            }
        }
        return match;
    }
    public static void main(String[] args) {
        Syntactic syntactic = new Syntactic();
        syntactic.Init();
//        for(Map.Entry<Integer,String[]>)
        tableReplace.forEach((key,value)-> System.out.println("key: "+key+" value: "+Arrays.toString((String[])value)));

        System.out.println(tableReplace.size());

    }





}
