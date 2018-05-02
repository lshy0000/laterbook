//package com.chuangfeigu.tools.sdk.poi;
//
//
//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Cell;
//
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by lshy on 2018-3-13.
// */
//
//public class ExcelUtils {
//    public static class CorpusWord {
//
//        public List<Sheet> sheet = new ArrayList<>();
//
//        public static class Sheet {
//            public List<Raw> raws = new ArrayList<>();
//
//            public static class Raw {
//                public String v1;
//                public String v2;
//                public String v3;
//                public String[] other;
//            }
//        }
//    }
//
//    public static CorpusWord excel(InputStream io) {
//        CorpusWord corpus = new CorpusWord();
//        try {
//            // 再将 POI 文档解析成 Excel 工作簿
//            HSSFWorkbook wb = new HSSFWorkbook(io);
//            HSSFRow row = null;
//            HSSFCell cell = null;
//            // 得到第 1 个工作簿
//            for (int p = 0; p < wb.getNumberOfSheets(); p++) {
//                HSSFSheet sheet = wb.getSheetAt(p);
//                if (sheet == null) continue;
//                // 得到最后一行的坐标
//                Integer lastRowNum = sheet.getLastRowNum();
//                System.out.println("lastRowNum => " + lastRowNum);
//                CorpusWord.Sheet shee = new CorpusWord.Sheet();
//                String cellValue = null;
//                // 从第 1 行开始读
//                for (int i = 0; i <= lastRowNum; i++) {
//                    row = sheet.getRow(i);
//                    if (row == null) continue;
//                    CorpusWord.Sheet.Raw raw = new CorpusWord.Sheet.Raw();
//                    for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
//                        cell = row.getCell(j);
//                        if (cell == null) continue;
//                        if (cell != null) {
//                            cellValue = get(cell);
//                        } else {
//                            cellValue = "null";
//                        }
//                        if (j == 0) {
//                            raw.v1 = cellValue;
//                        } else if (j == 1) {
//                            raw.v2 = cellValue;
//                        } else if (j == 2) {
//                            raw.v3 = cellValue;
//                        } else if (j >= 3) {
//                            raw.other = new String[row.getPhysicalNumberOfCells() - 3];
//                            for (int n = 3; n < row.getPhysicalNumberOfCells(); n++) {
//                                if (cell != null) {
//                                    cellValue = get(cell);
//                                } else {
//                                    cellValue = "null";
//                                }
//                                raw.other[n - 3] = cellValue;
//                            }
//                            break;
//                        }
//                    }
//                    shee.raws.add(raw);
//                }
//                corpus.sheet.add(shee);
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        return corpus;
//    }
//
//    private static String get(Cell cell) {
//        String re = "";
//        try {
//            re = cell.getStringCellValue();
//        } catch (Exception e) {
//            re = String.valueOf(cell.getNumericCellValue());
//        }
//        return re;
//    }
//}
