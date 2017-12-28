package RealTime;

//create PDF file (output, error, log)
//store to Summary folder
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
 
public class PDFfile {
    
    public static void writePDF(String finalDirectory, String filePath, String createdName){
        try{
            Document document = new Document();
            String separator = File.separator;
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(finalDirectory + separator + createdName + ".pdf"));
            document.open();

            //PdfContentByte cb = writer.getDirectContent();
           // cb.beginText();
            
            File[] targetFile = getOutFile(filePath);
            for(File outFile: targetFile){
                Paragraph p = new Paragraph();
                String fileName = outFile.getName();
                p.add(fileName);
                p.setAlignment(Element.ALIGN_LEFT);
                document.add(p);
                
                ArrayList<String> outLines = readOutFile(filePath, fileName);
                for(int i = 0; i < outLines.size(); i++){
                    Paragraph l = new Paragraph();
                    l.add(outLines.get(i));
                    document.add(l);
                }
            } 
            
            //cb.endText();

            document.close();
            System.out.println("Successfully generated PDF files!");
        } catch (Exception e){
            if(filePath.contains("Log"))
                System.out.println("\nNo Log Files!\n");
            else if(filePath.contains("Error"))
                System.out.println("\nNo Error Files!\n");
            else 
                System.out.println("\nNo Output Files!\n");
        }
    }
    
    public static File[] getOutFile(String filePath){
        File folder = new File(filePath);
        
        String filterName;
        if(filePath.contains("Log"))
            filterName = "log";
        else if(filePath.contains("Error"))
            filterName = "err";
        else 
            filterName = "out";
        
        FilenameFilter outFileFilter = new FilenameFilter()
        {    
            @Override
            public boolean accept(File directory, String name)
            {
                return (name.endsWith("." + filterName));
            }
        };
         
        File[] files = folder.listFiles(outFileFilter);
        return files;
    }
    
    public static ArrayList<String> readOutFile(String filePath, String fileName) throws FileNotFoundException, IOException{
        String separator = File.separator;
        File inFile = new File (filePath + separator + fileName);
        FileReader fileReader = new FileReader(inFile);
        ArrayList<String> readLines;
        try (BufferedReader bufReader = new BufferedReader(fileReader)) {
            readLines = new ArrayList<>();
            String str;
            str = bufReader.readLine();
            while (str != null) {
                readLines.add(str);
                str = bufReader.readLine();
            }
        }
        return readLines;
    }
}
