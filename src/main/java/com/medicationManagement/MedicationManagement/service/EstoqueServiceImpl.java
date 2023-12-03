package com.medicationManagement.MedicationManagement.service;

import com.medicationManagement.MedicationManagement.dto.EstoqueDetalheDTO;
import com.medicationManagement.MedicationManagement.dto.EstoqueRequest;
import com.medicationManagement.MedicationManagement.dto.EstoqueResponse;
import com.medicationManagement.MedicationManagement.exception.FarmaciaNotFoundException;
import com.medicationManagement.MedicationManagement.exception.MedicamentoNotFoundException;
import com.medicationManagement.MedicationManagement.exception.QuantidadeInvalidaException;
import com.medicationManagement.MedicationManagement.model.Estoque;
import com.medicationManagement.MedicationManagement.model.Farmacia;
import com.medicationManagement.MedicationManagement.model.Medicamento;
import com.medicationManagement.MedicationManagement.repository.EstoqueRepository;
import com.medicationManagement.MedicationManagement.repository.FarmaciaRepository;
import com.medicationManagement.MedicationManagement.repository.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EstoqueServiceImpl implements EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final FarmaciaRepository farmaciaRepository;
    private final MedicamentoRepository medicamentoRepository;

    @Autowired
    public EstoqueServiceImpl(EstoqueRepository estoqueRepository, FarmaciaRepository farmaciaRepository, MedicamentoRepository medicamentoRepository) {
        this.estoqueRepository = estoqueRepository;
        this.farmaciaRepository = farmaciaRepository;
        this.medicamentoRepository = medicamentoRepository;
    }

    @Override
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

    @Override
    public EstoqueResponse adicionarMedicamentoAoEstoque(EstoqueRequest estoqueRequest) {
        Long cnpj = estoqueRequest.getCnpj();
        Integer numeroRegistro = estoqueRequest.getNroRegistro();
        Integer quantidade = estoqueRequest.getQuantidade();

        Farmacia farmacia = farmaciaRepository.findByCnpj(cnpj);
        Medicamento medicamento = medicamentoRepository.findByNumeroRegistro(numeroRegistro)
                .orElseThrow(() -> new MedicamentoNotFoundException("Medicamento com número de Registro fornecido não encontrado"));

        LocalDateTime dataAtualizacao = LocalDateTime.now();

        if (farmacia == null) {
            throw new FarmaciaNotFoundException("Farmácia com CNPJ fornecido não encontrada");
        }

        if (quantidade <= 0) {
            throw new QuantidadeInvalidaException("A quantidade deve ser um número positivo maior que zero");
        }

        Estoque estoque = estoqueRepository.findByCnpjAndNroRegistro(cnpj, numeroRegistro)
                .orElse(new Estoque(cnpj, numeroRegistro, 0, dataAtualizacao));

        estoque.setQuantidade(estoque.getQuantidade() + quantidade);
        estoque.setDataAtualizacao(dataAtualizacao);

        estoqueRepository.save(estoque);

        return new EstoqueResponse(estoque.getCnpj(), estoque.getNroRegistro(),
                estoque.getQuantidade(), estoque.getDataAtualizacao());
    }
}
