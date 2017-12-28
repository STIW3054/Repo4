package RealTime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author Group4
 */
public class ExcelFile {
    
    static HSSFWorkbook wb = new HSSFWorkbook();
    static HSSFSheet sheet = wb.createSheet("sheet0");
    
    static int classcount = 0;
    static int codeLines = 0;
    static int commentLines = 0;
    static int blankLines = 0;
    static int c = 0;
    static int total = 0;
    
    static String semester;
    static String course;
    static String group;
    static String matrik;
    static String name;
    
    public static Map<String, Integer> map = new TreeMap<>();
    
    public static void addKeywords() {
        String[] keywordString = {"abstract", "assert", "boolean",
            "break", "byte", "case", "catch", "char", "class",
            "const", "continue", "default", "do", "double",
            "else", "enum", "extends", "for", "final", "finally",
            "float", "goto", "if", "implements", "import", "instanceof",
            "int", "interface", "long", "native", "new", "package",
            "private", "protected", "public", "return", "short",
            "static", "strictfp", "super", "switch", "synchronized",
            "this", "throw", "throws", "transient", "try",
            "void", "volatile", "while", "true", "false", "null"};
        for (String keywordString1 : keywordString) {
            map.put(keywordString1,0);
        }
    }
    
    public HSSFWorkbook CreateForm(HSSFSheet sheet , int Num) {
        
        HSSFRow Semester = sheet.createRow(0);
        Semester.createCell(0).setCellValue("Semester");
        Semester.createCell(1).setCellValue("A171");
        
        HSSFRow Course = sheet.createRow(1);
        Course.createCell(0).setCellValue("Course");
        Course.createCell(1).setCellValue("STIW3054");
        
        HSSFRow Group = sheet.createRow(2);
        Group.createCell(0).setCellValue("Group");
        Group.createCell(1).setCellValue("A");
        
        
        HSSFRow Keyword = sheet.createRow(5);
        Keyword.createCell(5).setCellValue("java keyword");
   
        sheet.createRow(7 + 3 * Num); 
        sheet.createRow(6 + 3 * Num);
        
        
        sheet.getRow(6 + 3*Num).createCell(0).setCellValue("Matrik");
        sheet.getRow(6 + 3*Num).createCell(1).setCellValue("LOC");
        sheet.getRow(6 + 3*Num).createCell(2).setCellValue("Blank");
        sheet.getRow(6 + 3*Num).createCell(3).setCellValue("Comment");
        sheet.getRow(6 + 3*Num).createCell(4).setCellValue("ActualLOC");
   
        return wb;
    }
    
    public static void treeFile(File f, HSSFSheet sheet, int Num) throws Exception {
        
        HSSFRow Semester = sheet.createRow(0);
        HSSFRow Course = sheet.createRow(1);
        HSSFRow Group = sheet.createRow(2);
        
        File[] childs = f.listFiles();
        if(childs != null){
            for (File child : childs) {
                if (!child.isDirectory()) {
                    if (child.getName().endsWith(".java")) {

                        BufferedReader br;
                        boolean comment = false;
                        br = new BufferedReader(new FileReader(child));
                        String line;
                        while ((line = br.readLine()) != null) {
                            line = line.trim();
                            if (line.matches("^[//s&&[^//n]]*$")) {
                                blankLines++;
                            } else if (line.startsWith("/*")
                                    && !line.endsWith("*/")) {
                                commentLines++;
                                comment = true;
                            } else if (true == comment) {
                                commentLines++;
                                if (line.endsWith("*/")) {
                                    comment = false;
                                }
                            } else if (line.startsWith("//")) {
                                commentLines++;                            
                            } else {
                                codeLines++;
                            }
                        }

                        try (Scanner input = new Scanner(child)) {
                            while (input.hasNextLine()) {
                                String sb = "";
                                sb += input.nextLine() + "\n";

                                String[] strs = sb.split("[\n .,;:!?(){}]");
                                for (String str : strs) {
                                    if (map.containsKey(str)) {
                                        int v = map.get(str) + 1;
                                        map.put(str,v);
                                    }
                                }
                            }                        
                        }
                    }
                } else {
                    treeFile(child, sheet, Num);
                }
            }        
            
            sheet.getRow(7 + 3*Num).createCell(0).setCellValue(f.getName());
            sheet.getRow(7 + 3*Num).createCell(1).setCellValue(blankLines + commentLines + codeLines);
            sheet.getRow(7 + 3*Num).createCell(2).setCellValue(blankLines);
            sheet.getRow(7 + 3*Num).createCell(3).setCellValue(commentLines);
            sheet.getRow(7 + 3*Num).createCell(4).setCellValue(codeLines);
        }
    }
    
    public static void Save(File Path, int Num,HSSFSheet sheet) throws Exception {
       
        
        addKeywords();
        
        treeFile(Path, sheet, Num);
        //遍历一下map 如果value>0 就output
        Set<Map.Entry<String, Integer>> set = map.entrySet();
        int c = 0, total = 0;
        for (Map.Entry<String, Integer> entry : set) {
            if (entry.getValue() > 0 && entry.getKey() != null) {
                c++;
                total += entry.getValue();
                sheet.getRow(6 + 3 * Num ).createCell(4 + c).setCellValue(entry.getKey());
                sheet.getRow(7 + 3 * Num).createCell(4 + c).setCellValue(entry.getValue());
            }
        }
        sheet.getRow(6 + 3 * Num ).createCell(5 + c).setCellValue("Total");
        sheet.getRow(7 + 3 * Num).createCell(5 + c).setCellValue(codeLines + total);
        
        
    }
    
    public static void CreateExcel(String Path) throws Exception{
        
        File ReposFold = new File(Path);
        File output = new File(ReposFold.getParentFile().toString());
        File outputResult = new File(output.toString() + LogFile.Se + "STIW3054-Result");
        File output1 = new File(output.toString() + LogFile.Se + "STIW3054-Result" + LogFile.Se + "Summary");
        outputResult.mkdir();
        output1.mkdir();
        ExcelFile form = new ExcelFile();
        
        File ReposFolder = new File(Path);
        File [] Repos = ReposFolder.listFiles();
        HSSFWorkbook wb = new HSSFWorkbook();
        
        for(int i=0;i<Repos.length;i++){
            classcount = 0;
            codeLines = 0;
            commentLines = 0;
            blankLines = 0;
            c = 0;
            total = 0;
            
            wb = form.CreateForm(sheet,i);
            
            Save(Repos[i],i,sheet);
        }
        FileOutputStream output2;
        output2 = new FileOutputStream(output1+LogFile.Se+"JavaKeywords.xls");
        wb.write(output2);
        System.out.println("\nScanning finished, Excel File Already Created!\n");
        output2.flush();
        output2.close();
        
    }
    
}
