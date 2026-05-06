package com.costumestore.webclient.controller;

import com.costumestore.webclient.dto.BillStatDto;
import com.costumestore.webclient.dto.CostumeLineStatDto;
import com.costumestore.webclient.dto.CostumeStatDto;
import com.costumestore.webclient.dto.ImportResultDto;
import com.costumestore.webclient.model.Bill;
import com.costumestore.webclient.model.Costume;
import com.costumestore.webclient.model.CostumeDetail;
import com.costumestore.webclient.model.ImportingBill;
import com.costumestore.webclient.model.Manager;
import com.costumestore.webclient.model.Supplier;
import com.costumestore.webclient.service.CostumeClientService;
import com.costumestore.webclient.service.ImportHandleClientService;
import com.costumestore.webclient.service.StatisticClientService;
import com.costumestore.webclient.service.SupplierClientService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    public static final String SESSION_COSTUMES = "importCostumeList";
    public static final String SESSION_SUPPLIERS = "importSupplierList";
    public static final String SESSION_LINE_STATS = "listCostumeLineStat";
    public static final String SESSION_COSTUME_STATS = "listCostumeStat";
    public static final String SESSION_BILL_STATS = "listBillStat";
    public static final String SESSION_STAT_START = "statStartTime";
    public static final String SESSION_STAT_END = "statEndTime";
    public static final String SESSION_MANAGER_ID = "currentManagerId";

    private final CostumeClientService costumeClientService;
    private final SupplierClientService supplierClientService;
    private final ImportHandleClientService importHandleClientService;
    private final StatisticClientService statisticClientService;

    @GetMapping({"/", "/home"})
    public String managerHome() {
        return "managerHome";
    }

    /**
     * Import form: load costumes (CostumeService), store in session; load suppliers (SupplierService), store in session; show importingCostume.
     */
    @GetMapping("/importing-costume")
    public String getImportingCostumeForm(
            @RequestParam(value = "managerId", required = false) Long managerId,
            Model model,
            HttpSession session) {
        log.info("getImportingCostumeForm: fetch costumes and suppliers");
        List<Costume> costumes = costumeClientService.getCostumeListToImport().stream()
                .map(Costume::fromDto)
                .toList();
        List<Supplier> suppliers = supplierClientService.getSupplierList().stream()
                .map(Supplier::fromDto)
                .toList();

        session.setAttribute(SESSION_COSTUMES, costumes);
        session.setAttribute(SESSION_SUPPLIERS, suppliers);
        if (managerId != null) {
            session.setAttribute(SESSION_MANAGER_ID, managerId);
        } else if (session.getAttribute(SESSION_MANAGER_ID) == null) {
            // Fallback for demo mode when authentication is not integrated yet.
            session.setAttribute(SESSION_MANAGER_ID, 4L);
        }

        model.addAttribute("costumes", costumes);
        model.addAttribute("suppliers", suppliers);
        model.addAttribute("managerId", session.getAttribute(SESSION_MANAGER_ID));
        model.addAttribute("pageTitle", "Nhập Trang Phục");
        return "importingCostume";
    }

    /**
     * Import submit: build request and let web-client orchestrate import flow.
     */
    @PostMapping("/importing-costume")
    public String handleImportBill(
            @RequestParam("supplierId") Long supplierId,
            @RequestParam("costumeIds") List<Long> costumeIds,
            @RequestParam("importPrices") List<BigDecimal> importPrices,
            @RequestParam("quantities") List<Integer> quantities,
            @RequestParam(value = "notes", required = false) List<String> notes,
            Model model,
            HttpSession session) {

        Long managerId = (Long) session.getAttribute(SESSION_MANAGER_ID);
        if (managerId == null) {
            log.warn("Manager session missing; redirect to form");
            return "redirect:/importing-costume?error=true";
        }
        log.info("handleImportBill: manager={}, supplier={}, items={}", managerId, supplierId, costumeIds.size());

        @SuppressWarnings("unchecked")
        List<Costume> sessionCostumes = (List<Costume>) session.getAttribute(SESSION_COSTUMES);
        @SuppressWarnings("unchecked")
        List<Supplier> sessionSuppliers = (List<Supplier>) session.getAttribute(SESSION_SUPPLIERS);

        if (sessionCostumes == null || sessionSuppliers == null) {
            log.warn("Session missing import lists; redirect to form");
            return "redirect:/importing-costume?error=true";
        }

        Map<Long, Costume> costumeById = sessionCostumes.stream()
                .collect(Collectors.toMap(Costume::getId, Function.identity()));
        Map<Long, Supplier> supplierById = sessionSuppliers.stream()
                .collect(Collectors.toMap(Supplier::getId, Function.identity()));

        Supplier supplier = supplierById.get(supplierId);
        if (supplier == null) {
            log.warn("Unknown supplierId {}", supplierId);
            return "redirect:/importing-costume?error=true";
        }
        if (costumeIds.isEmpty() || costumeIds.size() != importPrices.size() || costumeIds.size() != quantities.size()) {
            log.warn("Invalid request size: costumeIds={}, importPrices={}, quantities={}",
                    costumeIds.size(), importPrices.size(), quantities.size());
            return "redirect:/importing-costume?error=true";
        }

        List<CostumeDetail> details = new ArrayList<>();
        for (int i = 0; i < costumeIds.size(); i++) {
            Long cid = costumeIds.get(i);
            Costume c = costumeById.get(cid);
            if (c == null) {
                log.warn("Unknown costumeId {}", cid);
                return "redirect:/importing-costume?error=true";
            }
            BigDecimal price = importPrices.get(i);
            Integer quantity = quantities.get(i);
            if (price == null || price.compareTo(BigDecimal.ZERO) <= 0 || quantity == null || quantity <= 0) {
                log.warn("Invalid import item at index {}: costumeId={}, price={}, quantity={}", i, cid, price, quantity);
                return "redirect:/importing-costume?error=true";
            }
            String note = notes != null && notes.size() > i ? notes.get(i) : "";
            details.add(new CostumeDetail(c, price, quantity, note));
        }

        BigDecimal totalAmount = details.stream()
                .map(detail -> {
                    BigDecimal price = detail.getImportPrice() != null ? detail.getImportPrice() : BigDecimal.ZERO;
                    int qty = detail.getQuantity() != null ? detail.getQuantity() : 0;
                    return price.multiply(BigDecimal.valueOf(qty));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Bill bill = new Bill();
        ImportingBill importingBill = ImportingBill.forBill(bill);
        importingBill.setCostumeDetails(details);
        importingBill.setSupplier(supplier);
        importingBill.setManager(new Manager(managerId));
        importingBill.setTotalAmount(totalAmount);

        try {
            ImportResultDto result = importHandleClientService.handleImportBill(importingBill.toImportRequest());
            // Import flow completed, remove cached import lists to avoid stale selections.
            session.removeAttribute(SESSION_COSTUMES);
            session.removeAttribute(SESSION_SUPPLIERS);
            model.addAttribute("result", result);
            model.addAttribute("pageTitle", "Nhập Thành Công");
            return "importingSuccess";
        } catch (Exception e) {
            log.error("Import failed: {}", e.getMessage());
            model.addAttribute("error", "Error: " + e.getMessage());
            return "redirect:/importing-costume?error=true";
        }
    }

    @GetMapping("/statistics/costume-line-form")
    public String getCostumeLineStatForm(
            @RequestParam(value = "error", required = false) String error,
            Model model,
            HttpSession session) {
        if ("time-range".equals(error)) {
            model.addAttribute("timeRangeError", "Thời gian bắt đầu phải nhỏ hơn hoặc bằng thời gian kết thúc.");
        }
        // Clear previous statistic flow state to avoid stale session data.
        session.removeAttribute(SESSION_LINE_STATS);
        session.removeAttribute(SESSION_COSTUME_STATS);
        session.removeAttribute(SESSION_BILL_STATS);
        session.removeAttribute(SESSION_STAT_START);
        session.removeAttribute(SESSION_STAT_END);
        model.addAttribute("pageTitle", "Thống Kê Doanh Thu Theo Dòng Trang Phục");
        return "costumeLineStatForm";
    }

    @GetMapping("/statistics/costume-lines")
    public String getCostumeLineStat(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endTime,
            Model model,
            HttpSession session) {

        if (startTime == null) {
            startTime = (LocalDateTime) session.getAttribute(SESSION_STAT_START);
        }
        if (endTime == null) {
            endTime = (LocalDateTime) session.getAttribute(SESSION_STAT_END);
        }
        if (startTime == null || endTime == null) {
            return "redirect:/statistics/costume-line-form";
        }
        if (startTime.isAfter(endTime)) {
            return "redirect:/statistics/costume-line-form?error=time-range";
        }

        log.info("getCostumeLineStat: {} .. {}", startTime, endTime);
        List<CostumeLineStatDto> stats = statisticClientService.getCostumeLineStat(startTime, endTime);
        if (stats == null) {
            stats = Collections.emptyList();
        }
        session.setAttribute(SESSION_STAT_START, startTime);
        session.setAttribute(SESSION_STAT_END, endTime);
        session.setAttribute(SESSION_LINE_STATS, stats);

        model.addAttribute("stats", stats);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("pageTitle", "Doanh Thu Theo Dòng Trang Phục");
        return "costumeLineStat";
    }

    @GetMapping("/statistics/costumes")
    public String getCostumeStat(
            @RequestParam Long costumeLineId,
            Model model,
            HttpSession session) {

        LocalDateTime startTime = (LocalDateTime) session.getAttribute(SESSION_STAT_START);
        LocalDateTime endTime = (LocalDateTime) session.getAttribute(SESSION_STAT_END);
        if (startTime == null || endTime == null) {
            return "redirect:/statistics/costume-line-form";
        }

        @SuppressWarnings("unchecked")
        List<CostumeLineStatDto> lineStats = (List<CostumeLineStatDto>) session.getAttribute(SESSION_LINE_STATS);
        if (lineStats == null) {
            lineStats = Collections.emptyList();
        }
        CostumeLineStatDto selectedLine = lineStats.stream()
                .filter(line -> line.getCostumeLineId() != null && line.getCostumeLineId().equals(costumeLineId))
                .findFirst()
                .orElse(null);
        String costumeLineName = selectedLine == null ? "Không xác định" : selectedLine.getCostumeLineName();

        log.info("getCostumeStat: line {} {} .. {}", costumeLineId, startTime, endTime);
        List<CostumeStatDto> stats = statisticClientService.getCostumeStat(costumeLineId, startTime, endTime);
        if (stats == null) {
            stats = Collections.emptyList();
        }
        session.setAttribute(SESSION_COSTUME_STATS, stats);

        model.addAttribute("stats", stats);
        model.addAttribute("costumeLineId", costumeLineId);
        model.addAttribute("costumeLineName", costumeLineName);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("pageTitle", "Trang Phục Theo Dòng: " + costumeLineName);
        return "costumeStat";
    }

    @GetMapping("/statistics/bills")
    public String getBillListByCostume(
            @RequestParam Long costumeId,
            Model model,
            HttpSession session) {

        LocalDateTime startTime = (LocalDateTime) session.getAttribute(SESSION_STAT_START);
        LocalDateTime endTime = (LocalDateTime) session.getAttribute(SESSION_STAT_END);
        if (startTime == null || endTime == null) {
            return "redirect:/statistics/costume-line-form";
        }

        @SuppressWarnings("unchecked")
        List<CostumeStatDto> costumeStats = (List<CostumeStatDto>) session.getAttribute(SESSION_COSTUME_STATS);
        if (costumeStats == null) {
            costumeStats = Collections.emptyList();
        }
        CostumeStatDto selectedCostume = costumeStats.stream()
                .filter(costume -> costume.getCostumeId() != null && costume.getCostumeId().equals(costumeId))
                .findFirst()
                .orElse(null);
        String costumeName = selectedCostume == null ? "Không xác định" : selectedCostume.getCostumeName();

        log.info("getBillListByCostume: costume {}", costumeId);
        List<BillStatDto> bills = statisticClientService.getBillListByCostume(costumeId, startTime, endTime);
        if (bills == null) {
            bills = Collections.emptyList();
        }
        session.setAttribute(SESSION_BILL_STATS, bills);

        model.addAttribute("bills", bills);
        model.addAttribute("costumeId", costumeId);
        model.addAttribute("costumeName", costumeName);
        model.addAttribute("pageTitle", "Hoa don ban: " + costumeName);
        return "billResult";
    }
}

