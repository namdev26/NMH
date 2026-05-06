package com.costumestore.importingbillservice.service;

import com.costumestore.importingbillservice.dto.request.ImportingBillRequest;
import com.costumestore.importingbillservice.dto.response.ImportingBillResponse;

public interface ImportingBillService {
    ImportingBillResponse createImportingBill(ImportingBillRequest request);
}
