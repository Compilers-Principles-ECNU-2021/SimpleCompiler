import java.math.BigDecimal;
import java.util.*;

public class Compute {
    //定义的
    private static HashMap identifiersMap = new HashMap<String,Identifiers>();
    // t0  id0
    private static HashMap tempIdMap = new HashMap<String,Double>();

    private static HashMap tempIdType = new HashMap<String,String>();

    private static HashMap labelTable = new HashMap<String, Integer>();
    private static String tac ="";
    private static HashMap tempId = new HashMap<String,Double>();
    public static String res="";
    private static HashMap tempIdToId = new HashMap<String,String>();

    // 按空格分过的字符串
    static String[] s_parse = new String[4];

    public Compute(HashMap identifiersMapOut,String tacOut) {
        identifiersMap = identifiersMapOut;
        tac = tacOut;


        identifiersMap.forEach((key,value)->tempIdToId.put(((Identifiers)value).getTacName(),key)
                );

        identifiersMap.forEach((key,value)->tempIdMap.put(((Identifiers)value).getTacName(),Double.parseDouble(((Identifiers)value).getValue())
        ));

        identifiersMap.forEach((key,value)->tempIdType.put(((Identifiers)value).getTacName(),((Identifiers)value).getType()));
    }

    public static void process(){
        String[] tacProcess = tac.split("\n");

        for (int i = 0; i < tacProcess.length; i++) {
            if(tacProcess[i].charAt(0)=='L'){
                labelTable.put(tacProcess[i],i);
            }
        }
        for (int i = 0; i < tacProcess.length; i++) {
            analysis(tacProcess[i]);
            i = caculate(s_parse[0], s_parse[1], s_parse[2], s_parse[3], i);
        }
        print();
    }
    public static void analysis(String s) {
        char c = s.charAt(0);
        //每一行最多6个操作数
        String[] re_s = new String[6];

        //re_s存放每一个token,string 数组
        re_s = s.split(" ");
        switch (c) {
            case 'i': // id  id0 = t1
                if (s.indexOf('d') == 1) {
                    s_parse[0] = re_s[0];
                    s_parse[1] = re_s[2];
                    if (re_s.length > 3) {  //id开头的运算
                        s_parse[2] = re_s[3];
                        s_parse[3] = re_s[4];
                    } else { // id开头的赋值
                        s_parse[2] = re_s[1];
                    }

                } else {   // if
                    s_parse[0] = re_s[5];
                    s_parse[1] = re_s[1];
                    s_parse[2] = re_s[2];
                    s_parse[3] = re_s[3];
                }
                break;
            case 'g':   //goto
                s_parse[0] = re_s[1];
                s_parse[2] = re_s[0];
                break;
            case 'L':  //L
                s_parse[2] = "L";
                break;
            default:  // t0 t1
                s_parse[0] = re_s[0];
                s_parse[1] = re_s[2];
                if (re_s.length > 3) {  // 这一行是一个运算
                    s_parse[2] = re_s[3];
                    s_parse[3] = re_s[4];
                } else {  //这一行是一个赋值
                    s_parse[2] = re_s[1];
                }
        }
      //  return null;
    }

    public static double getValue(String name) {
        if (name.charAt(0) >= '0' && name.charAt(0) <= '9') {
            double num = 0.0;
            if (name.indexOf("e") >= 0 || name.indexOf("E") >= 0) {
                BigDecimal db = new BigDecimal(name);
                num = Integer.valueOf(db.toPlainString());
            } else {
                if (name.indexOf('.') >= 0)
                    num = Double.valueOf(name);
                else
                    num = Integer.valueOf(name);
            }
            return num;
        }
        if (tempIdMap.containsKey(name)) {
            return (double) tempIdMap.get(name);
        }
        // 当符号表中没有temp变量时
        tempIdMap.put(name, 0.0);
        return (double) tempIdMap.get(name);
    }
    @SuppressWarnings("all")
    public static int caculate(String r, String t1, String op, String t2, int line) {

        double op1, op2, dest;
//        if(tempIdType.containsKey(r)){
//            if(tempIdType.get(r).equals("int")){
//
//            }
//        }
        if(op.charAt(0)=='L') return line;
        op1 = getValue(t1);
        op2 = 0;
        if (t2 != null)
            op2 = getValue(t2);
        switch (op) {
            case "=":
                dest = op1;
                if(tempIdType.containsKey(r)){
                    if(tempIdType.get(r).equals("int")){
                        dest = (int) dest;
                        dest = (double) dest;
                    }
                }
                tempIdMap.put(r, dest);
                break;
            case "+":
                dest = op1 + op2;
                tempIdMap.put(r, dest);
                break;
            case "-":
                dest = op1 - op2;
                tempIdMap.put(r, dest);
                break;
            case "*":
                dest = op1 * op2;
                tempIdMap.put(r, dest);
                break;
            case "/":
                dest = op1 / op2;
                tempIdMap.put(r, dest);
                break;
            case ">":
                if (op1 > op2) {
                    line = (int) labelTable.get(r + ":");
                }
                break;
            case "<":
                if (op1 < op2) {
                    line = (int) labelTable.get(r + ":");
                }
                break;
            case ">=":
                if (op1 >= op2) {
                    line = (int) labelTable.get(r + ":");
                }
                break;
            case "<=":
                if (op1 <= op2) {
                    line = (int) labelTable.get(r + ":");
                }
                break;
            case "==":
                if (op1 == op2) {
                    line = (int) labelTable.get(r + ":");
                }
                break;
            case "!=":
                if (op1 != op2) {
                    line = (int) labelTable.get(r + ":");
                }
                break;
            case "goto":
                line = (int) labelTable.get(r + ":");
                break;
            case "L":
                break;
        }
        return line;
    }

    public static void print(){
        //res

       // String[] value ={"",""};
        int value = 0;
        for(Object str:tempIdMap.keySet()){
            if(tempIdToId.containsKey(str)){
                Identifiers identifiers = (Identifiers) identifiersMap.get(tempIdToId.get(str));
                if( ((Identifiers) identifiersMap.get(tempIdToId.get(str))).getType().equals("int")){
                       // String a = (String) tempIdMap.get(str);
//                        value = ((String) tempIdMap.get(str)).split(".");
                    double temp = (double) tempIdMap.get(str);
                  //  value = (int) temp;
                    res+=tempIdToId.get(str)+": "+(int)temp+"\n";
                    System.out.println(tempIdToId.get(str)+": "+(int)temp);
                    }
                else {
                    double temp = (double) tempIdMap.get(str);
                    res += tempIdToId.get(str) + ": " + temp+ "\n";
                    System.out.println(tempIdToId.get(str) + ": " + temp);
                }
                tempIdToId.remove(str);
            }
        }
    }

    public static void destory(){
        identifiersMap.clear();
        tempIdMap.clear();
        labelTable.clear();
        tempIdToId.clear();
        tac = "";
        res = "";
    }
}
