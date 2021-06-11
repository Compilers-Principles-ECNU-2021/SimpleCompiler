import java.util.*;

public class IdentifiersCheck {

    /*对于变量的操作，有一下几个操作
    1.在大括号前定义： int / real  identifiers = identifiers / num
    这个identifiers的类型必须是前面的类型，并且，你的identifiers不能是keywords，也就是不能是if while else then int real
    如果正确定义之后，，就因该给identifiers进行类型分类
    2.
     */

    private static HashMap intMap = new HashMap<String,Identifiers>();
    private static HashMap realMap = new HashMap<String,Identifiers>();

    public IdentifiersCheck() {
    }

    //类型检查，成功返回true，否则返回false
    public static boolean typeCheck(List<Token> tokenList){
        boolean success = true;
        int limit = 0;
        for ( limit = 0; limit < tokenList.size(); limit++) {
            Token token = (Token) tokenList.get(limit);
            if(token.getId()==0) break;
        }

        List tempList = new ArrayList<Token>();
        //排除comment id是-10
        for(int i=0;i<limit;i++){
            Token token = (Token) tokenList.get(i);
            if(token.getId()!=-10){
                tempList.add(token);
            }
        }
        //对初始化进行检测
        // 初始化情况有这几种
        // int a ; int a,b; int a=0; int a=1.1;
        //int int ;
        //8是identifiers 的id  数字的id是20  等号=的id是9 ,的id是-3
        for(int i=0;i<tempList.size();i++){
            Token tempToken = (Token) tempList.get(i);
            //以int开头的,这里面全是int，如果没有=就按照0计算
            if(tempToken.getAttributeValue().equals("int")){
                //找到;结尾的，这个定义就结束了
                int defineLimit=0;
                for( defineLimit=i;defineLimit<tempList.size();defineLimit++){
                    if(((Token)tempList.get(defineLimit)).getId()==10) break;
                }
                //对int的定义进行分析
                for(int index=i+1;index<defineLimit;index++,i=index){
                    tempToken = (Token) tempList.get(index);
                    if(tempToken.getId()==-3) continue;
                    if(tempToken.getId()==10) continue;
                    if(tempToken.getTokenType().equals("keywords")){
                        System.err.println("在第"+tempToken.getLineNumber()+"行，第"+tempToken.getLinePosition()+"位置，变量类型不能为keywords");
                        continue;
                    }
                    //说明是identifiers
                    if(tempToken.getId()==8){
                        Token nextToken = null,nextNextToken=null;
                        if(index<defineLimit-1)
                         nextToken = (Token) tempList.get(index+1);
                        if(index<defineLimit-2)
                            nextNextToken = (Token) tempList.get(index+1+1);
                        //如果形势是a=10
                        if(nextNextToken!=null&&nextToken!=null&&nextToken.getId()==9&&nextNextToken.getId()==20){

                            //加入不能付给double类型的判断
                            String value = nextNextToken.getAttributeValue();
                            if(value.contains(".")||value.contains("e")||value.contains("E"))
                            {
                                success = false;
                                System.err.println("在第"+tempToken.getLineNumber()+"行，第"+tempToken.getLinePosition()+"位置，real不能付给int");
                            }
                            if(!aleadyDefine(tempToken))
                            intMap.put(tempToken.getAttributeValue(),new Identifiers(tempToken.getAttributeValue(),"int","0"));
                            //realMap.put(tempToken.getAttributeValue(),new Identifiers(tempToken.getAttributeValue(),"real",nextNextToken.getAttributeValue()));
                            else {
                                System.err.println("在第" + tempToken.getLineNumber() + "行，第" + tempToken.getLinePosition() + "位置，变量" + tempToken.getAttributeValue() + "重复定义");
                                success = false;
                            }
                            index+=2;
                        }
                        //如果形势是int a，那么就值为0
                        if((nextToken!=null&&nextToken.getId()==-3)||nextToken==null){
                            if(!aleadyDefine(tempToken))
                                intMap.put(tempToken.getAttributeValue(),new Identifiers(tempToken.getAttributeValue(),"int","0"));
                                //realMap.put(tempToken.getAttributeValue(),new Identifiers(tempToken.getAttributeValue(),"real",nextNextToken.getAttributeValue()));
                            else {
                                System.err.println("在第" + tempToken.getLineNumber() + "行，第" + tempToken.getLinePosition() + "位置，变量" + tempToken.getAttributeValue() + "重复定义");
                                success = false;
                            }
                            //intMap.put(tempToken.getAttributeValue(),new Identifiers(tempToken.getAttributeValue(),"int","0"));
                            index++;
                        }
                    }
                }
            }
            if(tempToken.getAttributeValue().equals("real")){
                //找到;结尾的，这个定义就结束了
                int defineLimit=0;
                for( defineLimit=i;defineLimit<tempList.size();defineLimit++){
                    if(((Token)tempList.get(defineLimit)).getId()==10) break;
                }
                //对real的定义进行分析
                for(int index=i+1;index<defineLimit;index++,i=index){
                    tempToken = (Token) tempList.get(index);
                    if(tempToken.getId()==-3) continue;
                    if(tempToken.getId()==10) continue;
                    if(tempToken.getTokenType().equals("keywords")){
                        System.err.println("在第"+tempToken.getLineNumber()+"行，第"+tempToken.getLinePosition()+"位置，变量类型不能为keywords");
                        continue;
                    }
                    //说明是identifiers
                    if(tempToken.getId()==8){
                        Token nextToken = null,nextNextToken=null;
                        if(index<defineLimit-1)
                            nextToken = (Token) tempList.get(index+1);
                        if(index<defineLimit-2)
                            nextNextToken = (Token) tempList.get(index+1+1);
                        //如果形势是a=10
                        if(nextNextToken!=null&&nextToken!=null&&nextToken.getId()==9&&nextNextToken.getId()==20){
                            if(!aleadyDefine(tempToken))
                            realMap.put(tempToken.getAttributeValue(),new Identifiers(tempToken.getAttributeValue(),"real",nextNextToken.getAttributeValue()));
                            else {
                                System.err.println("在第" + tempToken.getLineNumber() + "行，第" + tempToken.getLinePosition() + "位置，变量" + tempToken.getAttributeValue() + "重复定义");
                                success = false;
                            }
                            index+=2;
                        }
                        //如果形势是real a，那么就值为0
                        if((nextToken!=null&&nextToken.getId()==-3)||nextToken==null){
                            //intMap.put(tempToken.getAttributeValue(),new Identifiers(tempToken.getAttributeValue(),"real","0.0"));
                            if(!aleadyDefine(tempToken))
                                realMap.put(tempToken.getAttributeValue(),new Identifiers(tempToken.getAttributeValue(),"real",nextNextToken.getAttributeValue()));
                            else {
                                System.err.println("在第" + tempToken.getLineNumber() + "行，第" + tempToken.getLinePosition() + "位置，变量" + tempToken.getAttributeValue() + "重复定义");
                                success = false;
                            }
                            index++;
                        }
                    }
                }
            }
        }
        return  success;
    }


    public static HashMap getIntMap() {
        return intMap;
    }

    public static HashMap getRealMap() {
        return realMap;
    }

    public  static  boolean aleadyDefine(Token token){
        if(intMap.containsKey(token.getAttributeValue())||realMap.containsKey(token.getAttributeValue())) {
            return true;
        }
        return false;
    }
}
