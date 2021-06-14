import java.io.File;

    public class ParseTree {

        public static void createDotGraph(String dotFormat,String fileName)
        {
            GraphViz gv=new GraphViz();
            gv.addln(gv.start_graph());
            gv.add(dotFormat);
            gv.addln(gv.end_graph());
            // png为输出格式，还可改为pdf，gif，jpg等
            String type = "png";
            // gv.increaseDpi();
            gv.decreaseDpi();
            gv.decreaseDpi();
            File out = new File(fileName+"."+ type);
            gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
        }

//        public static void main(String[] args) throws Exception {
//            String dotFormat= "\"program\"-> {\"compoundstmt\" };\"compoundstmt\"-> {\"{\" \"stmts\" \"}\" };\"stmts\"-> {\"stmt\" \"stmts\" };\"stmt\"-> {\"assgstmt\" };\"assgstmt\"-> {\"ID\" \"=\" \"arithexpr\" \";\" };ID -> \"b\";\"arithexpr\"-> {\"multexpr\" \"arithexprprime\" };\"multexpr\"-> {\"simpleexpr\" \"multexprprime\" };\"simpleexpr\"-> {\"Num\" };Num -> \"10\";\"multexprprime\"->ε;\"arithexprprime\"->ε;\"stmts\"->ε;";
//                    //Syntactic.usedGrammar;
//            System.out.println(dotFormat);
//            createDotGraph(dotFormat, "DotGraph");
//        }
    }

