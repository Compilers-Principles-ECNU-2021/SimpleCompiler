import java.util.*;

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
    public static void check(List<Token> tokenList){

        int index=0;

        // 排除comment
        while(tokenList.get(index).getId()==-1)
            index++;



    }

    public static HashMap getIntMap() {
        return intMap;
    }

    public static HashMap getRealMap() {
        return realMap;
    }
}
