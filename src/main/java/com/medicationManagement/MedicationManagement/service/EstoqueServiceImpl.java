package com.medicationManagement.MedicationManagement.service;

import com.medicationManagement.MedicationManagement.dto.EstoqueDetalheDTO;
import com.medicationManagement.MedicationManagement.dto.EstoqueRequest;
import com.medicationManagement.MedicationManagement.dto.EstoqueResponse;
import com.medicationManagement.MedicationManagement.exception.*;
import com.medicationManagement.MedicationManagement.model.Estoque;
import com.medicationManagement.MedicationManagement.model.Farmacia;
import com.medicationManagement.MedicationManagement.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EstoqueServiceImpl implements EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final FarmaciaService farmaciaService;
    private final MedicamentoService medicamentoService;


    @Autowired
    public EstoqueServiceImpl(EstoqueRepository estoqueRepository, FarmaciaService farmaciaService, MedicamentoService medicamentoService) {
        this.estoqueRepository = estoqueRepository;
        this.farmaciaService = farmaciaService;
        this.medicamentoService = medicamentoService;
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
        Farmacia farmaciaEncontrada = farmaciaService.obterFarmaciaPorCnpj(estoqueRequest.getCnpj());
        if (farmaciaEncontrada == null) {
            throw new FarmaciaNotFoundException("Falha na operação: CNPJ não encontrado no sistema.");
        }

        boolean medicamentoExiste = medicamentoService.existeMedicamentoComNumeroRegistro(estoqueRequest.getNroRegistro());
        if (!medicamentoExiste) {
            throw new MedicamentoNotFoundException("Falha na operação: Número de registro não encontrado no sistema.");
        }

        if (estoqueRequest.getQuantidade() <= 0) {
            throw new QuantidadeInvalidaException("A quantidade deve ser um número positivo maior que zero.");
        }

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
            estoque = new Estoque();
            estoque.setCnpj(cnpj);
            estoque.setNroRegistro(numeroRegistro);
            estoque.setQuantidade(quantidade);
            estoque.setDataAtualizacao(dataAtualizacao);
        }

        estoqueRepository.save(estoque);

        return new EstoqueResponse(
                estoque.getCnpj(),
                estoque.getNroRegistro(),
                estoque.getQuantidade(),
                estoque.getDataAtualizacao()
        );
    }


    public EstoqueResponse venderMedicamentoDoEstoque(EstoqueRequest estoqueRequest) {
        Long cnpj = estoqueRequest.getCnpj();
        Integer numeroRegistro = estoqueRequest.getNroRegistro();
        Integer quantidade = estoqueRequest.getQuantidade();

        // Verifica se algum dos campos está vazio
        if (cnpj == null || numeroRegistro == null || quantidade == null) {
            String mensagem = "Todos os campos (CNPJ, número de registro e quantidade) devem ser preenchidos.\n";

            if (cnpj == null) {
                mensagem += " CNPJ não preenchido. Por favor, coloque o CNPJ!\n";
            }
            if (numeroRegistro == null) {
                mensagem += " Número de registro não preenchido. Por favor, coloque o número de registro\n";
            }
            if (quantidade == null) {
                mensagem += "- Quantidade não preenchida. Por favor, coloque a quantidade!\n";
            }
            throw new RuntimeException(mensagem);
        }

        if (quantidade <= 0) {
            throw new RuntimeException("A quantidade deve ser um número positivo maior que zero.");
        }

        // Verifica se o CNPJ existe no banco de dados
        List<Estoque> estoquePorCnpj = estoqueRepository.findByCnpj(cnpj);

        if (estoquePorCnpj.isEmpty()) {
            throw new CnpjNotFoundException("CNPJ não encontrado");
        }

        // Busca o estoque específico por CNPJ e número de registro
        Optional<Estoque> estoquePorCnpjENumeroRegistro = estoqueRepository.findByCnpjAndNroRegistro(cnpj, numeroRegistro);

        if (estoquePorCnpjENumeroRegistro.isEmpty()) {
            throw new EstoqueNotFoundException("Medicamento não encontrado no estoque");
        }

        Estoque estoque = estoquePorCnpjENumeroRegistro.get();

        // Verifica se a quantidade em estoque é suficiente para a venda
        if (estoque.getQuantidade() < quantidade) {
            throw new RuntimeException("Quantidade insuficiente em estoque para venda");
        }

        // Atualiza a quantidade em estoque após a venda
        int novaQuantidade = estoque.getQuantidade() - quantidade;
        estoque.setQuantidade(novaQuantidade);
        estoque.setDataAtualizacao(LocalDateTime.now());

        // Se a quantidade em estoque se esgotar, remove o registro de estoque
        if (novaQuantidade == 0) {
            estoqueRepository.delete(estoque);
        } else {
            estoqueRepository.save(estoque);
        }

        // Retorna uma resposta com os detalhes da venda
        return new EstoqueResponse(
                estoque.getCnpj(),
                estoque.getNroRegistro(),
                estoque.getQuantidade(),
                estoque.getDataAtualizacao()
        );
    }
}
