package com.medicationManagement.MedicationManagement.service;

import com.medicationManagement.MedicationManagement.dto.EstoqueDetalheDTO;
import com.medicationManagement.MedicationManagement.dto.EstoqueRequest;
import com.medicationManagement.MedicationManagement.dto.EstoqueResponse;
import com.medicationManagement.MedicationManagement.exception.CnpjNotFoundException;
import com.medicationManagement.MedicationManagement.exception.EstoqueNotFoundException;
import com.medicationManagement.MedicationManagement.model.Estoque;
import com.medicationManagement.MedicationManagement.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EstoqueServiceImpl implements EstoqueService {

    private final EstoqueRepository estoqueRepository;

    @Autowired
    public EstoqueServiceImpl(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
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

        Optional<Estoque> estoqueOptional = estoqueRepository.findByCnpjAndNroRegistro(cnpj, numeroRegistro);
        Estoque estoque;
        LocalDateTime dataAtualizacao = LocalDateTime.now();

        if (estoqueOptional.isPresent()) {
            estoque = estoqueOptional.get();
            estoque.setQuantidade(estoque.getQuantidade() + quantidade);
            estoque.setDataAtualizacao(dataAtualizacao);
        } else {
            estoque = new Estoque(cnpj, numeroRegistro, quantidade, dataAtualizacao);
        }

        estoqueRepository.save(estoque);

        return new EstoqueResponse(estoque.getCnpj(), estoque.getNroRegistro(),
                estoque.getQuantidade(), estoque.getDataAtualizacao());
    }

    @Override
    public EstoqueResponse venderMedicamentoDoEstoque(EstoqueRequest estoqueRequest) {
        Long cnpj = estoqueRequest.getCnpj();
        Integer numeroRegistro = estoqueRequest.getNroRegistro();
        Integer quantidade = estoqueRequest.getQuantidade();

        Optional<Estoque> estoqueOptional = estoqueRepository.findByCnpjAndNroRegistro(cnpj, numeroRegistro);

        if (estoqueOptional.isEmpty()) {
            throw new EstoqueNotFoundException("Registro de estoque não encontrado para a venda do medicamento");
        }

        Estoque estoque = estoqueOptional.get();

        if (!Objects.equals(estoque.getCnpj(), cnpj)) {
            throw new CnpjNotFoundException("CNPJ não encontrado no registro de estoque");
        }
        LocalDateTime dataAtualizacao = LocalDateTime.now();
        int novaQuantidade = estoque.getQuantidade() - quantidade;

        if (novaQuantidade < 0) {
            throw new RuntimeException("Quantidade insuficiente em estoque para venda");
        }

        estoque.setQuantidade(novaQuantidade);
        estoque.setDataAtualizacao(dataAtualizacao);

        if (novaQuantidade == 0) {
            estoqueRepository.delete(estoque);
        } else {
            estoqueRepository.save(estoque);
        }

        return new EstoqueResponse(estoque.getCnpj(), estoque.getNroRegistro(),
                estoque.getQuantidade(), estoque.getDataAtualizacao());
    }


}
