package com.medicationManagement.MedicationManagement.service;

import com.medicationManagement.MedicationManagement.dto.EstoqueDetalheDTO;
import com.medicationManagement.MedicationManagement.model.Estoque;
import com.medicationManagement.MedicationManagement.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;

    @Autowired
    public EstoqueService(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    public List<EstoqueDetalheDTO> consultarEstoquePorCnpj(Long cnpj) {
        List<Estoque> estoques = estoqueRepository.findByCnpj(cnpj);

        List<EstoqueDetalheDTO> estoquesDetalhes = new ArrayList<>();
        for (Estoque estoque : estoques) {
            EstoqueDetalheDTO detalheDTO = new EstoqueDetalheDTO();
            detalheDTO.setNroRegistro(estoque.getNroRegistro());
            detalheDTO.setNome(estoque.getMedicamento().getNome());
            detalheDTO.setQuantidade(estoque.getQuantidade());
            detalheDTO.setDataAtualizacao(estoque.getDataAtualizacao());

            estoquesDetalhes.add(detalheDTO);
        }

        return estoquesDetalhes;
    }
}
