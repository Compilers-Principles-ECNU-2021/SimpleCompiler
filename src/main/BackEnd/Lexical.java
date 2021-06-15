import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

public class Lexical {
    private static Map keyWords = new HashMap<String,Integer>();
    private static Map operators = new HashMap<String,Integer>();
    private static Map delimiters = new HashMap<String,Integer>();
    private static Map space = new HashMap<String,Integer>();

    public static boolean isSuccess() {
        return success;
    }

    public static  boolean success = true;
    //100是identifiers
    //20是int
    //20是real
    // -1是comment
    public static FileProcess process = new FileProcess();
    public static List res = new ArrayList<Token>();

    public static void Init(){
        keyWords.put("if",2);
        keyWords.put("while",7);
        keyWords.put("else",6);
        keyWords.put("then",5);
        keyWords.put("int",-2);
        keyWords.put("real",-6);

       // +  -  /  *  =  ==  <  <=  >  >=
        operators.put("+",16);
        operators.put("-",17);
        operators.put("*",18);
        operators.put("/",19);
        operators.put("=",9);
        operators.put("==",15);
        operators.put(">",12);
        operators.put(">=",14);
        operators.put("<",11);
        operators.put("<=",13);

        // { } ( ) ; ,
        delimiters.put("(",3);
        delimiters.put(")",4);
        delimiters.put("{",0);
        delimiters.put("}",1);
        delimiters.put(";",10);
        delimiters.put(",",-3);

        space.put("\t",-4);
        space.put(" ",-5);
    }
    /**
     * 是否是字符
     * @param c
     * @return
     */
    private static boolean isLetter(char c)
    {
        return  Character.isLowerCase(c)||
                Character.isUpperCase(c)||
                c=='_';
    }
    /**
     * 是否是数字
     * @param c
     * @return
     */
    private static boolean isNum(char c)
    {
        return  Character.isDigit(c);
    }
    private static boolean stopForNum(char ch)
    {
        if(ch=='.'||operators.containsKey(String.valueOf(ch))|| delimiters.containsKey(String.valueOf(ch))|| space.containsKey(String.valueOf(ch)))
            return false;
        else return  true;
    }

    public void LexAnalysis(String path) throws IOException {
//        List input = process.FileRead("./test.txt");
        List input = process.FileRead(path);
       // System.out.println(operators.containsKey("+"));

        int lineNumber=0;
        int linePosition=0;
        int index=0;
        String tokenType="";
        String attributeValue="";
        int state=0;
        char ch='a';
        char chNext='a';
        System.out.println("the input code is");
        int k=0;
        for(Object str:input) {
            System.out.println("第"+k+"行："+str);
            k++;
        }
        System.out.println("end\n");
        for (int i = 0; i < input.size(); i++) {  //对每一行都要处理
            String temp=(String)input.get(i);
            temp=temp.trim();
            lineNumber=i+1;
            linePosition=0;
            index=0;
            System.out.println(temp);
            state=0;
            String tempWord="";
            String tempNum="";
            //每一行都要处理，直到处理完一整行
            while(index<temp.length()){
                ch=temp.charAt(index);
                switch (state){
                    case 0:  //初时状态0
                        //如果是空格或者\t
                        if(space.containsKey(String.valueOf(ch))){
                            state=0;
                            index++;
                            break;
                        }
                        //如果是delimiters
                        if(delimiters.containsKey(String.valueOf(ch))){
                            state=200;  //终结状态
                            res.add(new Token("delimiters",String.valueOf(ch),index+1,i+1, (Integer) delimiters.get(String.valueOf(ch))));
                            index++;
                            break;
                        }
                        //处理comment,会和除号的冲突
                        if(ch=='/'){
                            //不是最后一个字符
                            if(index<temp.length()-1){
                                chNext=temp.charAt(index+1);
                                //两个'//'说明是comment
                                if(chNext=='/'){
                                    res.add(new Token("comment",temp.substring(index),index+1,i+1,-1));
                                    state=0;
                                    index=temp.length();
                                    break;
                                }
                                //说明是除号/
                                else{
                                    res.add(new Token("operators",String.valueOf(ch),index+1,i+1,19));
                                    state=200;//终结状态
                                    index++;
                                    break;
                                }
                            }
                            //是最后一个字符，那么肯定是除号'/'
                            else{
                                res.add(new Token("operators",String.valueOf(ch),index+1,i+1,19));
                                state=200;//终结状态
                                index++;
                                break;
                            }
                        }
                        //处理剩余的除了'/'之外的operators
                        if(operators.containsKey(String.valueOf(ch))){
                            //不是最后一个字符,需要处理=和==，>和>=,<和<=的冲突
                            if(index<temp.length()-1) {
                                chNext = temp.charAt(index + 1);
                                //如果第二个还是operators，需要处理=,== | > >= < <=的冲突
                                if (operators.containsKey(String.valueOf(chNext))) {
                                    if (ch == '=' && chNext == '=') {
                                        res.add(new Token("operators", "==", index + 1, i+1, 15));
                                        state = 200;//终结状态
                                        index++;
                                        index++;
                                        break;
                                    } else if (ch == '>' && chNext == '='){
                                        res.add(new Token("operators", ">=", index + 1, i+1, 14));
                                        state = 200;//终结状态
                                        index++;
                                        index++;
                                        break;
                                    }
                                    else if (ch == '<' && chNext == '='){
                                        res.add(new Token("operators", "<=", index + 1, i+1, 13));
                                        state = 200;//终结状态
                                        index++;
                                        index++;
                                        break;
                                    }
                                    else{ //说明不是冲突的这几个符号，那就把第一个的符号拿掉就行
                                        res.add(new Token("operators",String.valueOf(ch),index+1,i+1, (Integer) operators.get(String.valueOf(ch))));
                                        state=200;//终结状态
                                        index++;
                                        break;
                                    }

                                }
                                //下一个字符不是operators,说明是单个字符的operators
                                else{
                                    res.add(new Token("operators",String.valueOf(ch),index+1,i+1, (Integer) operators.get(String.valueOf(ch))));
                                    state=200;//终结状态
                                    index++;
                                    break;
                                }
                            }
                            //说明是最后一个字符，肯定只能是operators的一个字符
                            else{
                                res.add(new Token("operators",String.valueOf(ch),index+1,i+1, (Integer) operators.get(String.valueOf(ch))));
                                state=200;//终结状态
                                index++;
                                break;
                            }
                        }
                        //首个是字符的,可能是keywords或者identifiers
                        if(isLetter(ch)){
                            //如果是这一行的最后一个字符，那么肯定就是identifiers
                            if(index==temp.length()-1){
                                res.add(new Token("identifiers",String.valueOf(ch),index+1,i+1, 8));
                                state=200;//终结状态
                            }
                            //如果不是这一行的最后一个字符，转到状态3
                            else {
                                state = 3;
                                tempWord += ch;
                            }
                            index++;
                            break;
                        }
                        if(isNum(ch)){
                            if(index==temp.length()-1){
                                res.add(new Token("Num",String.valueOf(ch),index+1-tempNum.length(),i+1, 20));
                                index++;
                                state=200;
                                break;
                            }
                            state=6;
                            tempNum+=ch;
                            index++;
                            break;
                        }
                        //处理首个字符的，包括KeyWords  if while else then int real 包括 identifiers
                    case 3:
                            boolean letterOrNumber = isLetter(ch)||isLetter(ch);
                            //是数字或者字符，最后一个需要判断，如果不是就继续状态3
                            if(letterOrNumber){
                                tempWord+=ch;
                                if(index==temp.length()-1){
                                    if(keyWords.containsKey(tempWord)){
                                        res.add(new Token("keywords", tempWord, index + 1 - tempWord.length(), i+1, (Integer) keyWords.get(tempWord)));
                                        state = 200;//终结状态
                                        index++;
                                        tempWord="";
                                        break;
                                    }
                                    //则是identifiers
                                    else{
                                        int a=0;
                                        if(tempWord.length()>64){
                                            System.err.println("在第"+(i+1)+"行，位置为"+(index+1-tempWord.length())+" 变量 "+tempWord+"长度为："+tempWord.length()+" 超出64！");
                                            state=404;
                                            index++;
                                            tempWord="";
                                            break;
                                        }
                                        res.add(new Token("identifiers", tempWord, index + 1 - tempWord.length(), i+1, 8));
                                        state = 200;//终结状态
                                        index++;
                                        tempWord="";
                                        break;
                                    }
                                }
                                else {
                                    state=3;
                                    index++;
                                    break;
                                }
                            }
                            //如果不是数字或者字符，则需要进行判断,并且这个要回退，也就是index不++
                            else {
                                //如果是keywords
                                if(keyWords.containsKey(tempWord)){
                                    res.add(new Token("keywords", tempWord, index + 1 - tempWord.length(), i+1, (Integer) keyWords.get(tempWord)));
                                    state = 200;//终结状态
//                                    index++;
                                    tempWord="";
                                    break;
                                }
                                //则是identifiers
                                else{
                                    if(tempWord.length()>64){
                                        System.err.println("在第"+(i+1)+"行，位置为"+(index+1-tempWord.length())+" 变量 "+tempWord+"长度为："+tempWord.length()+" 超出64！");
                                        state=404;
                                        index++;
                                        tempWord="";
                                        break;
                                    }
                                    res.add(new Token("identifiers", tempWord, index + 1 - tempWord.length(), i+1, 8));
                                    state = 200;//终结状态
//                                    index++;
                                    tempWord="";
                                    break;
                                }
                            }
                    //开头是数字的处理，把到下一个分隔符的都读取下，用正则匹配偷偷的哈哈哈
                    case 6:
                        //如果到了最后一个字符,就必须匹配了

                        String regxReal = "\\d+\\.?\\d*[Ee]*[+-]*\\d+";
                        String regxInt="\\d+";
                        Pattern patternInt = Pattern.compile(regxInt);
                        Pattern patternReal = Pattern.compile(regxReal);
                        boolean isNumInt = patternInt.matcher(tempNum).matches();
                        boolean isNumReal = patternReal.matcher(tempNum).matches();
                        if(!((operators.containsKey(String.valueOf(ch))|| delimiters.containsKey(String.valueOf(ch))|| space.containsKey(String.valueOf(ch))))){
                                tempNum+=ch;
                                index++;
                            }
                        //每次index++都因该看一下是不是outOfRange
                        if(index<temp.length())
                        ch=temp.charAt(index);
                        if((ch=='+'||ch=='-')&&(temp.charAt(index-1)=='e'||temp.charAt(index-1)=='E')){
                            tempNum+=ch;
                            index++;
                            if(index<temp.length())
                            ch=temp.charAt(index);
                        }
                        //读取完整个
                        while(index<temp.length()&&(!((operators.containsKey(String.valueOf(ch))|| delimiters.containsKey(String.valueOf(ch))|| space.containsKey(String.valueOf(ch)))))){
                            ch=temp.charAt(index);
                            tempNum+=ch;
                            index++;
                            if(index<temp.length())
                            ch=temp.charAt(index);
                            if((ch=='+'||ch=='-')&&(temp.charAt(index-1)=='e'||temp.charAt(index-1)=='E')){
                                tempNum+=ch;
                                index++;
                                if(index<temp.length())
                                    ch=temp.charAt(index);
                            }
                        }
                        //说明这个tempNum可以停止了
//                        if(operators.containsKey(ch)|| delimiters.containsKey(ch)|| space.containsKey(ch)){
                            isNumInt = patternInt.matcher(tempNum).matches();
                            isNumReal = patternReal.matcher(tempNum).matches();
                           if(isNumInt){
                               BigDecimal bigDecimal=new BigDecimal(tempNum);
                               //比int的最大值大
                               if(bigDecimal.compareTo(BigDecimal.valueOf(Integer.MAX_VALUE))==1){
                                   System.err.println("在第"+(i+1)+"行，位置为"+(index+1-tempNum.length())+" 整数： "+tempNum+" 超出Int范围！");
                                   tempNum="";
                                   state=404;
                                   break;
                               }
                               res.add(new Token("Num",tempNum,index+1-tempNum.length(),i+1, 20));
                               tempNum="";
                               state=0;
                               break;
                           }
                            else if(isNumReal){
                                //判断是否指数的最大值超过128
                               if(tempNum.contains("e")||tempNum.contains("E")){
                                   String[] NumReal=tempNum.split("e|E|\\+|-");
                                   //System.out.println(tempNum+" : "+NumReal.length);
                                   String exponent=NumReal[NumReal.length-1];
                                   if(Integer.valueOf(exponent)>128){
                                       System.err.println("在第"+(i+1)+"行，位置为"+(index+1-tempNum.length())+" 指数： "+tempNum+" 超出128范围！");
                                       tempNum="";
                                       state=404;
                                       break;
                                   }

                               }

                              // String[] NumReal=tempNum.split("e|E|\\+\\|-");


                                res.add(new Token("Num",tempNum,index+1-tempNum.length(),i+1, 20));
                                tempNum="";
                                state=0;
                                break;
                            }
                            else {
                                success = false;
                               System.err.println("在第"+(i+1)+"行，位置为"+(index+1-tempNum.length())+"变量不能以数字开头");
                               state=404;
                               tempNum="";
                               break;
                           }
                    case 200: //成功状态
                        state=0;
                        break;

                    case 404:
                        //报错
                        state=0;
                        break;
                }
            }
        }
        //System.err.println("UI出错误");
    }
    public void print(){
        System.out.println(res.toString());
    }

    public List getRes() {
        return res;
    }

    public void setRes(List res) {
        this.res = res;
    }

    public static void destory(){
        res.clear();

    }
}
