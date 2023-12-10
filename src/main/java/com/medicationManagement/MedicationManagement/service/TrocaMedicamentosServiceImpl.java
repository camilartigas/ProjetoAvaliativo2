package com.medicationManagement.MedicationManagement.service;

import com.medicationManagement.MedicationManagement.dto.TrocaMedicamentosRequest;
import com.medicationManagement.MedicationManagement.dto.TrocaMedicamentosResponse;
import com.medicationManagement.MedicationManagement.model.Estoque;
import com.medicationManagement.MedicationManagement.repository.EstoqueRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrocaMedicamentosServiceImpl implements TrocaMedicamentosService {

    // Simulando um repositório fictício para manipular os dados do estoque
    private final EstoqueRepository estoqueRepository;

    public TrocaMedicamentosServiceImpl(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    @Override
    public TrocaMedicamentosResponse trocarMedicamentos(TrocaMedicamentosRequest request) {
        List<String> erros = new ArrayList<>();

        // Validação dos campos da requisição
        if (request.getCnpjOrigem() == null) {
            erros.add("Por favor, digite o CNPJ de origem");
        }
        if (request.getCnpjDestino() == null) {
            erros.add("Por favor, digite o CNPJ de destino");
        }
        if (request.getNroRegistro() == null) {
            erros.add("Por favor, digite o número de registro");
        }

        // Validação da quantidade
        if (request.getQuantidade() == null || request.getQuantidade() <= 0) {
            erros.add("Por favor, digite uma quantidade válida (número positivo maior que zero)");
        }

        if (!erros.isEmpty()) {
            String mensagem = "Os seguintes erros foram encontrados: " + String.join(", ", erros);
            throw new RuntimeException(mensagem);
        }

        // Verificar se os CNPJs existem no sistema (RN02)
        if (!estoqueRepository.existsByCnpj(request.getCnpjOrigem()) ||
                !estoqueRepository.existsByCnpj(request.getCnpjDestino())) {
            throw new RuntimeException("CNPJ não localizado");
        }

        // Verificar se o registro existe no estoque de origem
        Estoque estoqueOrigem = estoqueRepository.findByCnpjAndNroRegistro(request.getCnpjOrigem(), request.getNroRegistro())
                .orElseThrow(() -> new RuntimeException("Registro não localizado no estoque de origem"));

        // Verificar se o registro existe no estoque de destino
        Estoque estoqueDestino = estoqueRepository.findByCnpjAndNroRegistro(request.getCnpjDestino(), request.getNroRegistro())
                .orElse(new Estoque(request.getCnpjDestino(), request.getNroRegistro(), 0)); // Se não existir, cria com quantidade zero

        // Validação de quantidade disponível (RN05)
        if (estoqueOrigem.getQuantidade() < request.getQuantidade()) {
            throw new RuntimeException("Quantidade insuficiente no estoque de origem");
        }

        // Atualização das quantidades nos estoques
        estoqueOrigem.setQuantidade(estoqueOrigem.getQuantidade() - request.getQuantidade());
        estoqueDestino.setQuantidade(estoqueDestino.getQuantidade() + request.getQuantidade());

        // Atualização da data
        LocalDateTime now = LocalDateTime.now();
        estoqueOrigem.setDataAtualizacao(now);
        estoqueDestino.setDataAtualizacao(now);

        // Salva as alterações no banco de dados
        estoqueRepository.save(estoqueOrigem);
        estoqueRepository.save(estoqueDestino);

        // Preparar a resposta
        TrocaMedicamentosResponse response = new TrocaMedicamentosResponse();
        response.setNroRegistro(request.getNroRegistro());
        response.setCnpjOrigem(request.getCnpjOrigem());
        response.setQuantidadeOrigem(estoqueOrigem.getQuantidade());
        response.setCnpjDestino(request.getCnpjDestino());
        response.setQuantidadeDestino(estoqueDestino.getQuantidade());

        return response;
    }

}
