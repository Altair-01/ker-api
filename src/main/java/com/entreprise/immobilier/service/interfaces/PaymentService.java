package com.entreprise.immobilier.service.interfaces;

import com.entreprise.immobilier.dto.PaymentDTO;
import com.entreprise.immobilier.model.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    List<Payment> getAllPayments();

    Optional<Payment> getPaymentById(Long id);

    Payment createPayment(PaymentDTO dto);

    Payment updatePayment(Long id, PaymentDTO dto);

    void deletePayment(Long id);

    List<Payment> getPaymentsByContract(Long contractId);
}
