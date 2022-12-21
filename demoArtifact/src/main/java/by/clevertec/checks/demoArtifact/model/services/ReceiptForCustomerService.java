package by.clevertec.checks.demoArtifact.model.services;

import by.clevertec.checks.demoArtifact.model.dto.FinalReceipt;
import by.clevertec.checks.demoArtifact.model.dto.ProductsForCustomer;
import by.clevertec.checks.demoArtifact.model.dto.ReceiptForCustomer;
import by.clevertec.checks.demoArtifact.model.dto.SalesReceipt;
import by.clevertec.checks.demoArtifact.model.services.api.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
public class ReceiptForCustomerService implements IReceiptForCustomerService {
    private final IFinalReceiptService finalReceiptService;
    private final ISalesReceiptService salesReceiptService;

    private final IProductService productService;

    public ReceiptForCustomerService(IFinalReceiptService finalReceiptService, ISalesReceiptService salesReceiptService,
                                     IProductService productService) {
        this.finalReceiptService = finalReceiptService;
        this.salesReceiptService = salesReceiptService;
        this.productService = productService;
    }

    @Override
    public ReceiptForCustomer formAndReceive(UUID idFinalReceipt) {
        FinalReceipt finalReceipt = finalReceiptService.get(idFinalReceipt);
        List<SalesReceipt> salesReceipts = salesReceiptService.getAllByIdFinalReceipt(idFinalReceipt);
        List<ProductsForCustomer> productsForCustomers = new ArrayList<>();
        for (SalesReceipt s : salesReceipts) {
            String name = productService.getByUuid(s.getIdProduct()).getName();
            ProductsForCustomer product = ProductsForCustomer.Builder.createBuilder()
                    .setName(name)
                    .setQuantity(s.getQuantity())
                    .setCost(s.getPrice())
                    .setSubtotal(s.getPrice() * s.getQuantity())
                    .setTotalWithQuantityDiscount(s.getTotal())
                    .build();
            productsForCustomers.add(product);
        }

        ReceiptForCustomer receiptForCustomer = ReceiptForCustomer.Builder.createBuilder()
                .setLocalDateTime(finalReceipt.getDtCreate())
                .setProducts(productsForCustomers)
                .setSubtotal(finalReceipt.getSubtotal())
                .setTotal(finalReceipt.getTotal())
                .build();

        return receiptForCustomer;
    }

    @Override
    public byte[] getReceiptExcel(UUID idFinalReceipt) {
        ReceiptForCustomer receiptForCustomer = this.formAndReceive(idFinalReceipt);
        LocalDateTime localDateTime = receiptForCustomer.getLocalDateTime();

        Double subtotal = receiptForCustomer.getSubtotal();
        Double total = receiptForCustomer.getTotal();

        String str = localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));


            Workbook book1 = new HSSFWorkbook();
            Sheet sheet = book1.createSheet("Чек от  " + str);

            Row rawTitle = sheet.createRow(0);
            Cell headTitleMain = rawTitle.createCell(0);
            headTitleMain.setCellValue("Чек от  " + str);

            Row row0 = sheet.createRow(2);
            Cell headTitle = row0.createCell(0);
            headTitle.setCellValue("Наименование товара");
            Cell headDescription = row0.createCell(1);
            headDescription.setCellValue("Количество");
            Cell headType = row0.createCell(2);
            headType.setCellValue("Стоимость за единицу");
            Cell headDescription1 = row0.createCell(3);
            headDescription1.setCellValue("Цена");
            Cell headTypeCurrency = row0.createCell(4);
            headTypeCurrency.setCellValue("Цена с учетом скидки за количество");



            List<ProductsForCustomer> products = receiptForCustomer.getProducts();
            int numRow = 3;
            for (ProductsForCustomer product: products) {
                Row row1 = sheet.createRow(numRow);
                Cell name = row1.createCell(0);
                name.setCellValue(product.getName());
                Cell quantity = row1.createCell(1);
                quantity.setCellValue(product.getQuantity());
                Cell cost = row1.createCell(2);
                cost.setCellValue(product.getCost());
                Cell subtotal1 = row1.createCell(3);
                subtotal1.setCellValue(product.getSubtotal());
                Cell total1 = row1.createCell(4);
                total1.setCellValue(product.getTotalWithQuantityDiscount());
                numRow++;
                }
            numRow ++;
        Row row1 = sheet.createRow(numRow);
        Cell name1 = row1.createCell(3);
        name1.setCellValue("Итого без скидки");
        Cell subtotal2 = row1.createCell(4);
        subtotal2.setCellValue(subtotal);
        numRow++;
        Row row2 = sheet.createRow(numRow);
        Cell name2 = row2.createCell(3);
        name2.setCellValue("Скидка");
        Cell discount = row2.createCell(4);
        discount.setCellValue("- " + (subtotal-total));
        numRow++;
        Row row3 = sheet.createRow(numRow);
        Cell name3 = row3.createCell(3);
        name3.setCellValue("Итого");
        Cell total2 = row3.createCell(4);
        total2.setCellValue(total);


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            book1.write(bos);
            bos.close();
        } catch (IOException e) {
            throw new ValidationException("Не удалось сохранить ексель файл");
        }

        byte[] bytes = bos.toByteArray();


        return bytes;
    }
}
