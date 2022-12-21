package by.clevertec.checks.demoArtifact;

import by.clevertec.checks.demoArtifact.model.dto.Product;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class tetsts {
    public static void main(String[] args) throws IOException {
        List<Product> products = new ArrayList<>();

        try {
            FileInputStream file = new FileInputStream(new File("C:\\Users\\matve\\OneDrive\\Рабочий стол\\testProduct1.xlsx"));

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            boolean finish = false;
            int tmp = 1;
            while (!finish){
                System.out.println(tmp);
                try {
                    sheet.getRow(tmp).getCell(0).getRawValue();
                } catch (NullPointerException e){
                    break;
                }
                Product product = Product.Builder.createBuilder()
                        .setBarcode(Integer.parseInt(sheet.getRow(tmp).getCell(0).getRawValue()))
                        .setName(sheet.getRow(tmp).getCell(1).getRawValue())
                        .setQuantity(Integer.parseInt(sheet.getRow(tmp).getCell(2).getRawValue()))
                        .setCost(Double.parseDouble(sheet.getRow(tmp).getCell(3).getRawValue()))
                        .build();
                products.add(product);
                tmp++;
            }
            System.out.println(products);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}


