package com.medicationManagement.MedicationManagement.service;

import com.medicationManagement.MedicationManagement.dto.TrocaMedicamentosRequest;
import com.medicationManagement.MedicationManagement.dto.TrocaMedicamentosResponse;
import com.medicationManagement.MedicationManagement.model.Estoque;
import com.medicationManagement.MedicationManagement.repository.EstoqueRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TrocaMedicamentosServiceImpl implements TrocaMedicamentosService {

    // Simulando um repositório fictício para manipular os dados do estoque
    private final EstoqueRepository estoqueRepository;

    public TrocaMedicamentosServiceImpl(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    @Override
    public TrocaMedicamentosResponse trocarMedicamentos(TrocaMedicamentosRequest request) {
        // Validação dos campos da requisição
        if (request.getCnpjOrigem() == null || request.getCnpjDestino() == null ||
                request.getNroRegistro() == null || request.getQuantidade() == null ||
                request.getQuantidade() <= 0) {
            throw new RuntimeException("Campos da requisição inválidos");
        }

        // Verificar se os CNPJs existem no sistema (RN02)
        if (!estoqueRepository.existsByCnpj(request.getCnpjOrigem()) ||
                !estoqueRepository.existsByCnpj(request.getCnpjDestino())) {
            throw new RuntimeException("CNPJ não localizado");
        }

        // Verificar se o registro existe nos estoques (RN04)
        Estoque estoqueOrigem = estoqueRepository.findByCnpjAndNroRegistro(request.getCnpjOrigem(), request.getNroRegistro())
                .orElseThrow(() -> new RuntimeException("Registro não localizado no estoque de origem"));

        Estoque estoqueDestino = estoqueRepository.findByCnpjAndNroRegistro(request.getCnpjDestino(), request.getNroRegistro())
                .orElseThrow(() -> new RuntimeException("Registro não localizado no estoque de destino"));

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
