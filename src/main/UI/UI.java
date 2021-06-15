

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

public class UI {
    public static void main(String[] args) throws FileNotFoundException {
        System.setErr(new PrintStream(new File("./testError.txt")));
        System.setOut(new PrintStream(new File("./testOut.txt")));
        Frame myFrame = new Frame();
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setBounds(50, 50, Frame.getScreenSize().width - 100, Frame.getScreenSize().height - 100);
        myFrame.setResizable(false);
        myFrame.setVisible(true);
//        AnalyseList analyse = new AnalyseList();
    }
}
class Frame extends JFrame implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private JPanel main_panel;

    private JMenuBar main_menu_bar;
    private JMenu menu_file;
    private JMenu menu_run;
    private JMenu menu_help;
    private JMenuItem file_open;
    private JMenuItem file_save;
    private JMenuItem file_saveas;
    private JMenuItem exit;
    private JMenuItem run_lex;
    private JLabel lb_lex_result;
    private JLabel lb_lex_error;
    private JLabel lb_text_edit;
    private JLabel lb_symbol;
    private JLabel lb_triples;
    private JLabel lb_sign;
    private JLabel lb_image;
    private JButton btn_start_lex;
    private JButton btn_cleardata;
    private JTextArea ta_input;
    private JScrollPane scrollpane_input;

    // 输出词法分析结果
    private JTable tb_lex_result;
    private DefaultTableModel tbmodel_lex_result;
    private JScrollPane scrollpane_lex_result;
    // 输出句法分析结果
    private JTable tb_lex_error;
    private DefaultTableModel tbmodel_lex_error;
    private JScrollPane scrollpane_lex_error;

    // 输出符号表
    private JTable tb_symbol_list;
    private DefaultTableModel tbmodel_symbol_list;
    private JScrollPane scrollpane_symbol_list;
    private DefaultTableModel tbmodel_sign;
    // 输出三地址指令
    private JTable tb_triples;
    private JTable tb_sign;

    private DefaultTableModel tbmodel_triples;
    private JScrollPane scrollpane_triples;
    private JScrollPane scrollpane_sign;
    private JMenuItem run_clear;
    private JMenuItem help_des;

    private JLabel lb_terminal;
    private JTextArea ta_output;
    private String file_name;
    private String file_all="./test.txt";
    ImageIcon image;
    FileProcess fileProcess=new FileProcess();

    private JScrollPane scrollpane_label;

    //初始化frame，
    public Frame(){
        this.setTitle("程序编译器");
        this.setSize(1300,800);
        initPanel();
    }

    public void initPanel(){
        int h = Frame.getScreenSize().height;
        int w = Frame.getScreenSize().width;
        // 创建菜单栏对象
        main_menu_bar = new JMenuBar();

        // 创建菜单对象
        menu_file = new JMenu("文件");
        menu_run = new JMenu("编辑");


        menu_help = new JMenu("帮助");
        menu_help.addActionListener(this);


        file_open = new JMenuItem("打开");
        file_save = new JMenuItem("保存");
        file_saveas = new JMenuItem("另存为");
        exit = new JMenuItem("退出");

        //设置监听
        file_open.addActionListener(this);
        file_save.addActionListener(this);
        file_saveas.addActionListener(this);
        exit.addActionListener(this);


        // 将菜单对象添加到菜单栏对象中
        menu_file.add(file_open);
        menu_file.add(file_save);
        menu_file.add(file_saveas);
        menu_file.add(exit);

        // 将菜单对象添加到菜单栏对象中
        main_menu_bar.add(menu_file);

        run_lex = new JMenuItem("运行");
        run_clear = new JMenuItem("清空");

        run_lex.addActionListener(this);
        menu_run.add(run_lex);
        run_clear.addActionListener(this);
        menu_run.add(run_clear);
        main_menu_bar.add(menu_run);

        help_des =new JMenuItem("详情");
        help_des.addActionListener(this);
        menu_help.add(help_des);
        main_menu_bar.add(menu_help);

        this.setJMenuBar(main_menu_bar);

        main_panel = new JPanel();
        main_panel.setLayout(null);
        lb_text_edit = new JLabel("代码编辑区");
        main_panel.add(lb_text_edit);
        lb_text_edit.setBounds(20, 10, 70,20);

        ta_input = new JTextArea();
        scrollpane_input = new JScrollPane(ta_input);
        main_panel.add(scrollpane_input);
        scrollpane_input.setBounds(20, 40, w/2-80, h/2-120);
        scrollpane_input.setRowHeaderView(new LineNumberHeaderView());

        btn_start_lex = new JButton("运行");
        main_panel.add(btn_start_lex);
        btn_start_lex.setBounds(w/2 - 270, 10, 100, 20);
        btn_start_lex.addActionListener(this);

        btn_cleardata = new JButton("清空");
        main_panel.add(btn_cleardata);
        btn_cleardata.setBounds(w/2 - 160, 10, 100, 20);
        btn_cleardata.addActionListener(this);

        lb_symbol = new JLabel("词法分析结果");
        main_panel.add(lb_symbol);
        lb_symbol.setBounds(w/2 - 40, 10, 80, 20);

        tbmodel_symbol_list = new DefaultTableModel(null, new String[]{"tokenType", "attributeValue", "linePosition", "lineNumber","id"});
        tb_symbol_list = new JTable(tbmodel_symbol_list);
        tb_symbol_list.setEnabled(false);
        scrollpane_symbol_list = new JScrollPane(tb_symbol_list);
        main_panel.add(scrollpane_symbol_list);
        scrollpane_symbol_list.setBounds(w / 2 - 40, 40, w / 2 - 80, h / 4 - 80);

        lb_triples = new JLabel("三地址");
        main_panel.add(lb_triples);
        lb_triples.setBounds(w / 2 - 40,h / 4 - 30, 80, 20);

        tbmodel_triples = new DefaultTableModel(null, new String[]{"三地址"});
        tb_triples = new JTable(tbmodel_triples);
        tb_triples.setEnabled(false);
        scrollpane_triples = new JScrollPane(tb_triples);
        main_panel.add(scrollpane_triples);
        scrollpane_triples.setBounds(w / 2 - 40, h / 4 , w / 4 - 45, h / 4 - 80);

        lb_sign = new JLabel("result");
        main_panel.add(lb_sign);
        lb_sign.setBounds(w / 4 * 3 - 75,h / 4 - 30, 80, 20);

        tbmodel_sign = new DefaultTableModel(null, new String[]{"name","value"});
        tb_sign = new JTable(tbmodel_sign);
        tb_sign.setEnabled(false);
        scrollpane_sign = new JScrollPane(tb_sign);
        main_panel.add(scrollpane_sign);
        scrollpane_sign.setBounds(w / 4 * 3 - 75, h / 4 , w / 4 - 45, h / 4 - 80);

        lb_image =new JLabel("语法树");
        main_panel.add(lb_image);
        lb_image.setBounds(w / 2 - 40,h / 2 - 70,70,20);
        //放图片
        ImageIcon image = new ImageIcon("DotGraph.png");
        //image.setImage(image.getImage().getScaledInstance(w / 2 - 40,h / 2 - 40, Image.SCALE_DEFAULT));
        JLabel label = new JLabel(image);
        //main_panel.add(label);
        scrollpane_label = new JScrollPane(label);
        main_panel.add(scrollpane_label);
        //label.setBounds(w / 2 - 40, h / 2 - 40, 200, 200);
        scrollpane_label.setBounds(w / 2 - 40, h / 2 - 40, w / 2 - 80, h / 2 - 130);
        label.setVisible(true);

        lb_terminal = new JLabel("控制台");
        main_panel.add(lb_terminal);
        lb_terminal.setBounds(20, h /2 - 70, 70, 20);

        ta_output = new JTextArea();
        scrollpane_input = new JScrollPane(ta_output);
        main_panel.add(scrollpane_input);
        scrollpane_input.setBounds(20, h / 2 - 40, w / 2 - 80, h / 2 - 130);
        add(main_panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if ((e.getSource() == btn_start_lex) || (e.getSource() == run_lex)) {
            // 进行判定k
//            clearTableData();
            if(ta_input.getText().equals("")){
                JOptionPane.showMessageDialog(main_panel, "您什么都没有输入啊！", "提示", JOptionPane.ERROR_MESSAGE);
                System.out.println("nothing input!");
            }

            else {

                // 词法分析
////                clearTableData();
                String temp=ta_input.getText();
                System.out.println("缓冲区 "+temp);
                try {
                    System.out.println("try");
                    fileProcess.FileWrite("./test.txt",temp);
                    System.out.println("try_after");

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
//                System.out.println(ta_input.getText());
                System.out.println("after");

               // Lexical
                Lexical text_lex = new Lexical();
                text_lex.Init();
                try {
                    text_lex.LexAnalysis("./test.txt");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                List list=text_lex.getRes();
                for (int i=0;i<list.size();i++){
                    Token token= (Token) list.get(i);
                    String type=token.tokenType;
                    String attr=token.attributeValue;
                    String postion=Integer.toString(token.linePosition);
                    String line=Integer.toString(token.lineNumber);
                    String id=Integer.toString(token.id);
                tbmodel_symbol_list.addRow(new String[]{type,attr,postion,line,id});

                }

                //语法分析
                Syntactic sny=new Syntactic();
                ParseTree parseTree = new ParseTree();
                sny.Init();
                sny.syntacticAnalysis(list);
                String out = Syntactic.usedGrammar;
                System.out.println("out:"+out);
                parseTree.createDotGraph(sny.getTreeGrammar(),"DotGraph");


                // String out=sny.getUsedGrammar();
                ta_output.append(out);
              //  System.out.println("out:"+out);

                //语义分析
                IdentifiersOperate identifiersOperate = new IdentifiersOperate();
                boolean typeCheck = IdentifiersOperate.typeCheck(text_lex.getRes());
                //输出符号表

                IdentifiersOperate.getIdentifiersMap().forEach((key, value) -> System.out.println("key: " + key + " value:" + value));
                if (typeCheck) {
                    Semantic semantic = new Semantic(text_lex.getRes(), IdentifiersOperate.getIdentifiersMap());
                    Semantic.TacGenerate();
                    //输出三地址
                    String tac=Semantic.tac;
                    String[] tacStrArray = tac.split("\n");
                    for(int i=0;i<tacStrArray.length;i++){
                        tbmodel_triples.addRow(new String[]{tacStrArray[i]});
                        System.out.println("UI输出"+tacStrArray[i]);
                    }
                    String tacCode = "./tacCode.txt";
//                    fileProcess.FileWrite(tacCode, Semantic.tac);

                    Compute compute = new Compute(IdentifiersOperate.getIdentifiersMap(),Semantic.tac);
                    Compute.process();
                    String res=Compute.res;
                    String[] resStrArray = res.split("\n");
                    for(int i=0;i<resStrArray.length;i++){
                        String[] devided=resStrArray[i].split(":");
                        String name=devided[0];
                        String value=devided[1];
                        tbmodel_sign.addRow(new String[]{name,value});
                        System.out.println("UI输出"+resStrArray[i]);
                    }
                    String resultCode = "./result.txt";
//                    fileProcess.FileWrite(resultCode, Compute.res);
                }
//
            }
        }
        else if(e.getSource() == btn_cleardata){
            ClearData.ClearData();
            ta_input.setText("");
            ta_output.setText("");
            clearTableData();
        }
        else if(e.getSource() == run_clear){
            ta_input.setText("");
            ta_output.setText("");
            clearTableData();

        }
        else if(e.getSource() == file_open){
//            String file_name;
            JFileChooser file_open_filechooser = new JFileChooser();
            file_open_filechooser.setCurrentDirectory(new File("."));
            file_open_filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = file_open_filechooser.showOpenDialog(main_panel);
            // 证明有选择
            if (result==JFileChooser.APPROVE_OPTION) {
                file_name = file_open_filechooser.getSelectedFile().getPath();
                // 读取文件，写到JTextArea里面

                try {
                    BufferedReader textReader = new BufferedReader(new InputStreamReader(new FileInputStream(file_name),"utf-8"));
                    String tmp="";
                    int tempbyte;
                    while ((tempbyte=textReader.read())!= -1) {
                        ta_input.append(""+(char)tempbyte);
                        tmp=tmp+(char)tempbyte;
                    }
                    fileProcess.FileWrite("./test.txt",tmp);
                    textReader.close();

                } catch (UnsupportedEncodingException unsupportedEncodingException) {
                    unsupportedEncodingException.printStackTrace();
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

//                File file = new File(file_name);
//                try{
//                    InputStream in = new FileInputStream(file);
//                    int tempbyte;
//                    while ((tempbyte=in.read()) != -1) {
//                        ta_input.append(""+(char)tempbyte);
//                    }
//                    in.close();
//                }
//                catch(Exception event){
//                    event.printStackTrace();
//                }
            }

        }
        else if(e.getSource() == help_des){
            JOptionPane.showMessageDialog(main_panel, "你有什么问题可以联系我！\n邮箱：du-william@outlook.com", "提示", JOptionPane.ERROR_MESSAGE);
            System.out.println("nothing input!");

        }
        else if(e.getSource() == exit){
            System.exit(1);
        }
        else {
            System.out.println("nothing！");
        }
    }

    public void clearTableData(){
//		System.out.println(tbmodel_lex_result.getRowCount());
        // 事先要是不给他们赋值的话就会造成，tbmodel_lex_error在删除的过程中会不断
//        // 地减少，然后就会出现很蛋疼的删不干净的情况
//        int error_rows = tbmodel_lex_error.getRowCount();
        int result_rows = tbmodel_sign.getRowCount();
        int triples_rows = tbmodel_triples.getRowCount();
        int symbols_rows = tbmodel_symbol_list.getRowCount();  //token的行数

//        for(int i=0;i<error_rows;i++)
//        {
//            tbmodel_lex_error.removeRow(0);
//            tb_lex_error.updateUI();
//        }
//
        for (int i=0;i<result_rows;i++){
            tbmodel_sign.removeRow(0);
            tb_sign.updateUI();
        }
//
        for(int i=0;i<triples_rows;i++){
            tbmodel_triples.removeRow(0);
            tb_triples.updateUI();
        }

        for(int i=0;i<symbols_rows;i++){
            tbmodel_symbol_list.removeRow(0);
            tb_symbol_list.updateUI();
        }

    }
    public static Dimension getScreenSize() {
        return screenSize;
    }
}
