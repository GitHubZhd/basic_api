package com.toefl.basic.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.toefl.basic.controller.TestController;
import com.toefl.basic.dto.ListeningMaterial;
import com.toefl.basic.dto.Lrc;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelUtil {

    private static Gson gson=new GsonBuilder().disableHtmlEscaping().create();

    public static Workbook getWorkbook(String filePath) {
        try {
            InputStream input = new FileInputStream(filePath); // 建立输入流
            Workbook wb = null;
            if (filePath.endsWith("xlsx")) {
                wb = new XSSFWorkbook(input);
            } else {
                wb = new HSSFWorkbook(input);
            }
            return wb;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> excelImport(Class<T> cls, Workbook wb, String sheetName) {
        List<T> list = new ArrayList<T>();
        try {
            Sheet sheet = null;
            if (sheetName != null) {
                sheet = wb.getSheet(sheetName);
            } else {
                sheet = wb.getSheetAt(0);
            }
            int rows = sheet.getLastRowNum();

            List<ListeningMaterial> listV=new ArrayList<>();
            for (int i = 0; i <= rows; i++) {
                Row row = sheet.getRow(i);
                T t = null;
                if (i > 0) {
                    t = cls.newInstance();
                }
                if (row != null) {
                    int cols = row.getLastCellNum();
                    String[] lrcArr = new String[0];
                    String[] messArr=new String[0];
                    String title = "";
                    for (int j = 0; j < cols; j++) {
                        Cell cell = row.getCell(j);
                        String value = null;
                        Object cellValue = null;
                        if (cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                            DecimalFormat df = new DecimalFormat("0");
                            cellValue = df.format(cell.getNumericCellValue());
                        } else if (cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                            cellValue = cell.getStringCellValue();
                        } else if (cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
                            cellValue = cell.getBooleanCellValue();
                        } else if (cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
                            cellValue = cell.getCellFormula();
                        } else if (cell != null && cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                            cellValue = "";
                        }
                        if (cellValue != null) {
                            value = String.valueOf(cellValue);
                        }
                        if(j==0){
                            String[] arr=value.split("@@");
                            lrcArr=arr;
                        }
                        if(j==1){
                            messArr=TestController.handleCn(value);
                        }
                        if(j==2){
                            title=value;
                        }
                    }

                    //======================================================
                    List<Lrc> list1=new ArrayList();

                    for (int k = 0,m=1; k <lrcArr.length && m<messArr.length ; k++,m++) {
                        Lrc lrc=new Lrc();
                        lrc.setTime(lrcArr[k].split("\\|\\|")[1]);
                        lrc.setEn(lrcArr[k].split("\\|\\|")[2]);
                        lrc.setCn(messArr[m]);
                        list1.add(lrc);
                    }

                    SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
                    String ss=gson.toJson(list1);
                    System.out.println(ss);
                    String fileName="toefl_old_"+sdf.format(new Date())+"_"+messArr[0].replace("。","")+".json";
                    Utils.write(fileName,ss);


                    String code=messArr[0].replace("。","");
                    File f = new File("D:\\zhanghaidong\\temp\\audio\\template\\template\\"+code) ;
                    if(!f.exists()){
                        f.mkdirs();
                    }
                    File[] lrcTemp=f.getParentFile().listFiles(new FileFilter(code+".json"));
                    if(lrcTemp!=null){
                        for(int q=0;q<lrcTemp.length;q++){
//                            lrcTemp[q].renameTo(f);
                            FileUtils.moveFileToDirectory(lrcTemp[q],f,false);
                        }
                    }

                    File[] mp3Temp=f.getParentFile().listFiles(new FileFilter(code+".mp3"));
                    if(mp3Temp!=null){
                        for(int q=0;q<mp3Temp.length;q++){
//                            mp3Temp[q].renameTo(f);
                            FileUtils.moveFileToDirectory(mp3Temp[q],f,false);
                        }
                    }

                    ListeningMaterial listeningMaterial=new ListeningMaterial();
                    listeningMaterial.setCategoryId("toefl_old");
                    listeningMaterial.setCategoryName("老托福听力Part C");
                    listeningMaterial.setTitle(title);
                    listeningMaterial.setId("toefl_old_"+sdf.format(new Date())+"_"+messArr[0].replace("。",""));
                    listeningMaterial.setMp3Name("toefl_old_"+sdf.format(new Date())+"_"+messArr[0].replace("。","")+".mp3");
                    listeningMaterial.setLrc("toefl_old_"+sdf.format(new Date())+"_"+messArr[0].replace("。","")+".json");
                    listV.add(listeningMaterial);

                    //======================================================
                }
                if (t != null) {
                    list.add(t);
                }
            }
            Utils.write("toefl_old.json",gson.toJson(listV));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static <T> List<T> excelImport(Class<T> cls, String filePath, String sheetName) {
        return excelImport(cls, getWorkbook(filePath), sheetName);
    }
}
