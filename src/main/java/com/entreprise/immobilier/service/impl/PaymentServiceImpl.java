package com.entreprise.immobilier.service.impl;

import com.entreprise.immobilier.dto.PaymentDTO;
import com.entreprise.immobilier.model.Contract;
import com.entreprise.immobilier.model.Payment;
import com.entreprise.immobilier.repository.ContractRepository;
import com.entreprise.immobilier.repository.PaymentRepository;
import com.entreprise.immobilier.service.interfaces.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ContractRepository contractRepository;

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    public Payment createPayment(PaymentDTO dto) {
        Contract contract = contractRepository.findById(dto.getContractId())
                .orElseThrow(() -> new EntityNotFoundException("Contrat introuvable."));

        Payment payment = Payment.builder()
                .contract(contract)
                .amount(dto.getAmount())
                .status(dto.getStatus())
                .paymentMethod(dto.getPaymentMethod())
                .build();

        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(Long id, PaymentDTO dto) {
        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paiement introuvable."));

        Contract contract = contractRepository.findById(dto.getContractId())
                .orElseThrow(() -> new EntityNotFoundException("Contrat introuvable."));

        existingPayment.setContract(contract);
        existingPayment.setAmount(dto.getAmount());
        existingPayment.setStatus(dto.getStatus());
        existingPayment.setPaymentMethod(dto.getPaymentMethod());

        return paymentRepository.save(existingPayment);
    }

    @Override
    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new EntityNotFoundException("Paiement introuvable.");
        }
        paymentRepository.deleteById(id);
    }

    @Override
    public List<Payment> getPaymentsByContract(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new EntityNotFoundException("Contrat introuvable."));
        return paymentRepository.findByContract(contract);
    }
}
