import java.util.*;
public class Semantic{
    public static int index = 0;
    public static int tempId ;
    public static int tableId ;
    static String tac = "";
    public static List tokenList = new ArrayList<Token>();
    private static HashMap identifiersMap = new HashMap<String,Identifiers>();
    //private static HashMap identifiersMap = new HashMap<String,Identifiers>();

    //构造函数，将tokenList截取到{为止
    public  Semantic(List<Token> list,HashMap IdentifiersMap){

        identifiersMap = IdentifiersMap;
        List newTokenList = new ArrayList<Token>();
        for(int j=0;j<list.size();j++){
            Token token = (Token) list.get(j);
            if(token.getId()!=-1)
                // list.remove(j);
                newTokenList.add(token);
        }

        tokenList = newTokenList;
        int i=0;
        for ( i = 0; i < tokenList.size(); i++) {

            if(((Token)tokenList.get(i)).getId()==0) break;
        }
        index = i;


    }

    public static String newTempId(){
        tempId++;
        return "t" + tempId;
    }

    public static String newTableId(){
        tableId++;
        return "L" + tableId;
    }

    public static void TacGenerate(){
        for (; index < tokenList.size(); ) {
            Token token = (Token) tokenList.get(index);
            switch (token.getId()){
                case 2: // if
                    ifProcess();
                    break;
                case 7: //while
                    whileProcess();
                    break;
                case 0: // {
                    index++;
                    Choose();
                    break;
                case 1: // }
                    index++;
                    break;
                default:
                    assgstmtProcess();
                    break;
            }
        }
    }

    public static void InChoose(){
        Token token = (Token) tokenList.get(index);
        switch (token.getId()){
            case 2: // if
                ifProcess();
                break;
            case 7: //while
                whileProcess();
                break;
            case 0: // {
                index++;
                Choose();
                break;
            case 1: // }
                index++;
                break;
            default:
                assgstmtProcess();
                InChoose();
                break;
        }
    }

    public static void Choose(){
        Token token = (Token) tokenList.get(index);
        switch (token.getId()){
            case 2: // if
                ifProcess();
                break;
            case 7: //while
                whileProcess();
                break;
            case 0: // {
                index++;
                InChoose();
                break;
            case 1: // }
                index++;
                break;
            default:
                assgstmtProcess();
                break;
        }
    }

    public static void ifProcess(){
        String bFalse = newTableId();
        String end = newTableId();
        index+=2;
        boolexpr(bFalse);
        index++;
        Choose();
        tac+="goto "+end+"\n";
        //this.out.println("goto "+end);
        tac+=bFalse+":"+"\n";
        //this.out.println(b_false+":");
        //i++;
        index++;
        Choose();
        //choose(receive);
        tac+=end+":"+"\n";
       // this.out.println(end+":");
    }

    public static void whileProcess(){
        //String begin = new_label();
        String begin = newTableId();
        tac+=begin + ":"+"\n";
      //  out.println(begin + ":");
        String bFalse = newTableId();
       // String b_false = new_label();
       // i += 2;
        index+=2;
        boolexpr(bFalse);
      //  boolexpr(receive, b_false);

        Choose();
        //choose(receive);
        tac+="goto " + begin+"\n";
        tac+=bFalse+":"+"\n";
       // this.out.println("goto " + begin);
        //this.out.println(b_false+":");
    }

    public static void assgstmtProcess(){
        String id = ((Token)tokenList.get(index)).getAttributeValue();
        String P = ((Identifiers)identifiersMap.get(id)).getTacName();
       // System.out.println("哈哈哈哈哈"+P);
     //   String P = ((Token)tokenList.get(index)).getAttributeValue();
       // String P = tool.get_ID_register(receive[i]);
        // String p_type = get_ID_type(receive[i]);
        //i += 2;
        index+=2;
        String T = arithexpr();
        //i++;
        index++;
        tac+=P + " = " + T+"\n";

       // out.println(P + " = " + T);
    }

    public static void boolexpr(String bFalse){
        String T1 = arithexpr();
        String boolop = ((Token)tokenList.get(index)).getAttributeValue();
        //i++;
        index++;
        String T2 = arithexpr();
        //i++;
        index++;
        switch (boolop) {
            case "<":
                tac+="if " + T1 + " >= " + T2 + " goto " + bFalse+"\n";
               // out.println("if " + T1 + " >= " + T2 + " goto " + b_false);
                break;
            case ">":
                tac+="if " + T1 + " <= " + T2 + " goto " + bFalse+"\n";
                //out.println("if " + T1 + " <= " + T2 + " goto " + b_false);
                break;
            case "<=":
                tac+="if " + T1 + " > " + T2 + " goto " + bFalse+"\n";
                //out.println("if " + T1 + " > " + T2 + " goto " + b_false);
                break;
            case ">=":
                tac+="if " + T1 + " < " + T2 + " goto " + bFalse+"\n";
               // out.println("if " + T1 + " < " + T2 + " goto " + b_false);
                break;
            case "==":
                tac+="if " + T1 + " != " + T2 + " goto " + bFalse+"\n";
                //out.println("if " + T1 + " != " + T2 + " goto " + b_false);
                break;
        }
    }

    public static String arithexpr(){
        String p = multexpr();
        String t = arithexprprime();
        String t1 = newTempId();
        tac+=t1 + " = " + p + t+"\n";
      //  out.println(t1 + " = " + p + t);
        return t1;
    }

    public static String multexprprime(){
        String p, t, t1;
        Token token = (Token) tokenList.get(index);
        switch (token.getAttributeValue()) {
            case "*":
                //i++;
                index++;
                p = simpleexpr();
                t = multexprprime();
                if (p.equals("") || t.equals(""))
                    return (" * " + p + t);
                t1 = newTempId();
                tac+=t1 + " = " + p + t+"\n";
              //  out.println(t1 + " = " + p + t);

                return (" * " + t1);
            case "/":
                //i++;
                index++;
                p = simpleexpr();
                t = multexprprime();
                if (p.equals("") || t.equals(""))
                    return (" / " + p + t);
                //t1 = new_t();
                t1 = newTempId();
                tac+=t1 + " = " + p + t+"\n";
               // out.println(t1 + " = " + p + t);
                return (" / " + t1);
            default:
                return "";
        }
    }
    public static String multexpr() {
        String p = simpleexpr();
        String t = multexprprime();
        if (p.equals("") || t.equals(""))
            return (p + t);
        String t1 = newTempId();
        tac+=t1 + " = " + p + t+"\n";
        //out.println(t1 + " = " + p + t);
        return t1;
    }

    public static String arithexprprime() {
        String p, t, t1;
        Token token = (Token) tokenList.get(index);
        switch (token.getAttributeValue()) {
            case "+":
                //i++;
                index++;
                p = multexpr();
                t = arithexprprime();
                if (p.equals("") || t.equals(""))
                    return (" + " + p + t);
                t1 = newTempId();
                tac+=t1 + " = " + p + t+"\n";
              //  out.println(t1 + " = " + p + t);
                return (" + " + t1);
            case "-":
               // i++;
                index++;
                p = multexpr();
                t = arithexprprime();
                if (p.equals("") || t.equals(""))
                    return (" - " + p + t);
                t1 = newTempId();
                tac+=t1 + " = " + p + t+"\n";
              //  out.println(t1 + " = " + p + t);
                return (" - " + t1);

            default:
                return "";
        }
    }

    public static String simpleexpr() {
        String p = "";
        Token token = (Token) tokenList.get(index);
        switch (token.getAttributeValue()) {
            case "(":
                // i++;
                index++;
                p = arithexpr();
                //i++;
                index++;
                break;
            default:
                //  if(receive[i].charAt(0)<='9'&&receive[i].charAt(0)>='0'){
                if (token.getId() == 20) {
                    p = token.getAttributeValue();
                    //i++;
                    index++;
                } else {
                   // String id = ((Token)tokenList.get(index)).getAttributeValue();
                   // System.out.println(token);
                   // System.out.println(token.getAttributeValue()+"jiehuo "+identifiersMap.containsKey(token.getAttributeValue()));
                    //identifiersMap.forEach((key,value)-> System.out.println("key: "+key+" value:"+value));
                    //p = ((Identifiers)identifiersMap.get(token.getAttributeValue())).getTacName();
                   // p = token.getAttributeValue();
                    //System.out.println("!!!!P="+p);
                    // p = tool.get_ID_register(receive[i]);
                    // i++;
                    String id = ((Token)tokenList.get(index)).getAttributeValue();
                    p = ((Identifiers)identifiersMap.get(id)).getTacName();
                    index++;
                }
                break;

        }
        return p;
    }

}
