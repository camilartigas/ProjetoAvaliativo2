package com.medicationManagement.MedicationManagement.service;

import com.medicationManagement.MedicationManagement.dto.EstoqueDetalheDTO;
import com.medicationManagement.MedicationManagement.dto.EstoqueRequest;
import com.medicationManagement.MedicationManagement.dto.EstoqueResponse;
import com.medicationManagement.MedicationManagement.exception.CnpjNotFoundException;
import com.medicationManagement.MedicationManagement.exception.EstoqueNotFoundException;
import com.medicationManagement.MedicationManagement.exception.MedicamentoNotFoundException;
import com.medicationManagement.MedicationManagement.model.Estoque;
import com.medicationManagement.MedicationManagement.model.Medicamento;
import com.medicationManagement.MedicationManagement.repository.EstoqueRepository;
import com.medicationManagement.MedicationManagement.repository.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EstoqueServiceImpl implements EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final MedicamentoRepository medicamentoRepository;

    @Autowired
    public EstoqueServiceImpl(EstoqueRepository estoqueRepository, MedicamentoRepository medicamentoRepository) {
        this.estoqueRepository = estoqueRepository;
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

        Optional<Estoque> estoqueOptional = estoqueRepository.findByCnpjAndNroRegistro(cnpj, numeroRegistro);
        Estoque estoque;
        LocalDateTime dataAtualizacao = LocalDateTime.now();

        if (estoqueOptional.isPresent()) {
            estoque = estoqueOptional.get();
            estoque.setQuantidade(estoque.getQuantidade() + quantidade);
            estoque.setDataAtualizacao(dataAtualizacao);
        } else {
            // Verifica se o medicamento existe pelo número de registro
            Optional<Medicamento> medicamentoOptional = medicamentoRepository.findByNumeroRegistro(numeroRegistro);
            Medicamento medicamento;

            if (medicamentoOptional.isPresent()) {
                // Se o medicamento existir, cria um novo registro no estoque
                medicamento = medicamentoOptional.get();
                estoque = new Estoque(cnpj, numeroRegistro, quantidade, dataAtualizacao);
                estoque.setMedicamento(medicamento);
            } else {
                // Se o medicamento não existir, lança uma exceção
                throw new MedicamentoNotFoundException("Medicamento não encontrado pelo número de registro.");
            }
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
