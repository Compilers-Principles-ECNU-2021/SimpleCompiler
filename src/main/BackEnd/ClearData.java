public class ClearData {

    //需要清空的内容
    // lexcial的tokenlist
    public  static void ClearData(){
        Lexical.destory();
        IdentifiersOperate.destory();
        Semantic.destory();
        Compute.destory();
        Syntactic.destory();
    }

}
