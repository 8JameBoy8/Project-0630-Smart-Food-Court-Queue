package com.example.demo.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Payment;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.service.QueueService;


@RestController
@CrossOrigin
public class QueueController {

    private final QueueService queueService;
    private final PaymentRepository paymentRepository;

    public QueueController(QueueService queueService, PaymentRepository paymentRepository) {
        this.queueService = queueService;
        this.paymentRepository = paymentRepository;
    }

    //ยืนยันรูป payment
    @PostMapping("/api/payments/{id}/verify")
    public String verify(@RequestBody Map<String, Object> data, @PathVariable int id) {
        return queueService.verifyPayment(
            id,
            (boolean) data.get("isValid")
        );
    }

    @GetMapping("/api/store/{storeId}/orders")
    public List<Map<String, Object>> getStoreQueueOrders(@PathVariable int storeId) {
        return queueService.getOrdersByStore(storeId);
    }

    //ขอข้อมูล order ของ id ที่ให้มา 
    @GetMapping("/api/order/{id}")
    public Map<String, Object> getOrder(@PathVariable int id) {
        return queueService.getOrder(id);
    }
    
    //ขอข้อมูล payment ของ id ที่ให้มา
    @GetMapping("/api/payment/{id}")
    public Map<String, Object> getPayment(@PathVariable int id) {
        return queueService.getPayment(id);
    }

    // ดึงรูปสลิปของ payment
    @GetMapping("/api/payments/{id}/slip-image")
    public ResponseEntity<?> getSlipImage(@PathVariable int id) {
        try {
            Payment p = paymentRepository.findByPaymentId(id);
            if (p == null || p.getSlipImagePath() == null) {
                return ResponseEntity.notFound().build();
            }

            Path filePath = Paths.get(p.getSlipImagePath());
            if (!filePath.isAbsolute()) {
                filePath = Paths.get(System.getProperty("user.dir")).resolve(filePath);
            }

            Resource resource = new FileSystemResource(filePath);
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            String contentType = Files.probeContentType(filePath);
            MediaType mediaType = MediaType.parseMediaType(
                contentType != null ? contentType : "image/jpeg"
            );

            return ResponseEntity.ok()
                .contentType(mediaType)
                .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("เกิดข้อผิดพลาดในการดึงรูป");
        }
    }

}