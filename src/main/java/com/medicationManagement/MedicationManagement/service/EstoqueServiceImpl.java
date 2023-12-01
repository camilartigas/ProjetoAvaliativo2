package com.medicationManagement.MedicationManagement.service;

import com.medicationManagement.MedicationManagement.dto.EstoqueRequest;
import com.medicationManagement.MedicationManagement.dto.EstoqueResponse;
import com.medicationManagement.MedicationManagement.model.Estoque;
import com.medicationManagement.MedicationManagement.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EstoqueServiceImpl implements EstoqueService {

    private final EstoqueRepository estoqueRepository;

    @Autowired
    public EstoqueServiceImpl(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    @Override
    public EstoqueResponse adicionarMedicamentoAoEstoque(EstoqueRequest estoqueRequest) {
        Long cnpj = estoqueRequest.getCnpj();
        Integer numeroRegistro = estoqueRequest.getNroRegistro();
        Integer quantidade = estoqueRequest.getQuantidade();

        Optional<Estoque> optionalEstoque = estoqueRepository.findByCnpjAndNroRegistro(cnpj, numeroRegistro);
        Estoque estoque;

        if (optionalEstoque.isPresent()) {
            estoque = optionalEstoque.get();
            estoque.setQuantidade(estoque.getQuantidade() + quantidade);
        } else {
            estoque = new Estoque(cnpj, numeroRegistro, quantidade, LocalDateTime.now());
        }

        estoqueRepository.save(estoque);

        return new EstoqueResponse(
                estoque.getCnpj(),
                estoque.getNroRegistro(),
                estoque.getQuantidade(),
                estoque.getDataAtualizacao()
        );
    }
}
