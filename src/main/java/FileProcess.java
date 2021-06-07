import java.io.*;
import java.util.*;
public class FileProcess {

    public FileProcess() {
    }

    public List FileRead (String path) throws IOException {
        List input=new ArrayList<String>();
        File file = new File(path);
        if(!file.exists()||file.isDirectory()){
            throw new FileNotFoundException();
        }
        BufferedReader br=new BufferedReader(new FileReader(file));
        String temp=null;
        StringBuffer sb=new StringBuffer();
        temp=br.readLine();
        while(temp!=null){
            input.add(temp);
            sb.append(temp+"\n");
            temp=br.readLine();
        }
        return input;
    }

    public  void FileWrite(String path,String input) throws IOException{
        // File file=new File("./test.txt");
        File file=new File(path);
        if(!file.exists())
            file.createNewFile();
        FileOutputStream out=new FileOutputStream(file,false);
//        for(int i=0;i<8;i++){
            StringBuffer sb=new StringBuffer(input);
           // sb.append("这是第"+i+"行\n");
            out.write(sb.toString().getBytes("utf-8"));
//        }
        out.close();
    }




}
