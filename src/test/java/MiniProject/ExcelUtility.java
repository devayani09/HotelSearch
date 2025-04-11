package MiniProject;

import java.io.FileInputStream;  
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
    // Path to the Excel file
    private String excelFilePath;

    // Constructor to initialize the Excel file path
    public ExcelUtility(String excelFilePath) {
        this.excelFilePath = excelFilePath;
    }

    // Method to write hotel data to the Excel file
    public void writeHotelData(String[][] hotelData) throws IOException {
        // Create a new workbook and sheet
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Hotels");
        // Create header row and set column names
        XSSFRow header = sheet.createRow(0);
        header.createCell(0).setCellValue("Hotel Name");
        header.createCell(1).setCellValue("Price");
        header.createCell(2).setCellValue("Rating");

        // Iterate over hotel data and write to the sheet
        for (int i = 0; i < hotelData.length; i++) {
            XSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(hotelData[i][0]);
            row.createCell(1).setCellValue(hotelData[i][1]);
            row.createCell(2).setCellValue(hotelData[i][2]);
        }

        // Write the workbook to the file
        FileOutputStream fileOut = new FileOutputStream(excelFilePath);
        workbook.write(fileOut);
        workbook.close();
        fileOut.close();
        
    }

    // Method to read data from the Excel file
    public void readExcelData() throws IOException {
        // Open the Excel file
        FileInputStream file = new FileInputStream(excelFilePath);
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheet("Hotels");

        // Print the data from the sheet
        System.out.println("\nReading from Excel File:");
        for (Row row : sheet) {
            for (Cell cell : row) {
                System.out.print(cell.getStringCellValue() + "\t");
            }
            System.out.println();
        }
        workbook.close();
        file.close();
    }
}