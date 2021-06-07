import java.util.HashMap;
import java.util.Stack;

public class Syntactic {
    Stack math=new Stack<String>();
    int[][] LLTable = new int[13][21];

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



    }





}
