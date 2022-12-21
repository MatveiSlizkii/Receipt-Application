package by.clevertec.checks.demoArtifact.controller.web.controllers.rest;

import by.clevertec.checks.demoArtifact.model.dto.FinalReceipt;
import by.clevertec.checks.demoArtifact.model.dto.Product;
import by.clevertec.checks.demoArtifact.model.dto.ProductInfo;
import by.clevertec.checks.demoArtifact.model.dto.ReceiptForCustomer;
import by.clevertec.checks.demoArtifact.model.services.api.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/api/receipt")
public class RestReceiptController {
    private final IReceiptForCustomerService receiptForCustomerService;
    private final ISalesReceiptService salesReceiptService;
    private final IFinalReceiptService finalReceiptService;

    public RestReceiptController(IReceiptForCustomerService receiptForCustomerService,
                                 ISalesReceiptService salesReceiptService,
                                 IFinalReceiptService finalReceiptService) {
        this.receiptForCustomerService = receiptForCustomerService;
        this.salesReceiptService = salesReceiptService;
        this.finalReceiptService = finalReceiptService;
    }

    @GetMapping(value = {"get/{uuid}", "/get/{uuid}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ReceiptForCustomer index2(@PathVariable UUID uuid) {
        return receiptForCustomerService.formAndReceive(uuid);
    }

    @GetMapping(value = {"getForExcel/{uuid}", "/getForExcel/{uuid}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Resource> index(@PathVariable UUID uuid) {
        HttpHeaders header = new HttpHeaders();
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + uuid + ".xls");
        byte[] bytes = receiptForCustomerService.getReceiptExcel(uuid);
        return new ResponseEntity<>(new ByteArrayResource(bytes), header, HttpStatus.OK);
    }


    @PostMapping(value = {"save", "/save"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ReceiptForCustomer create(@RequestParam String idDiscountCard,
                                     @RequestBody ProductInfo data) {

       FinalReceipt finalReceipt = finalReceiptService.saveAll(UUID.fromString(idDiscountCard), data);

        return receiptForCustomerService.formAndReceive(finalReceipt.getId());
    }

    @DeleteMapping(value = {"delete/{uuid}", "/delete/{uuid}"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus delete(@PathVariable UUID uuid) {
        finalReceiptService.delete(uuid);
        return HttpStatus.OK;
    }

}
