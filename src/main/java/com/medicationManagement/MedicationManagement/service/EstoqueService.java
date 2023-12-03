package com.medicationManagement.MedicationManagement.service;

import com.medicationManagement.MedicationManagement.dto.EstoqueDetalheDTO;
import com.medicationManagement.MedicationManagement.dto.EstoqueRequest;
import com.medicationManagement.MedicationManagement.dto.EstoqueResponse;

import java.util.List;

public interface EstoqueService {
    List<EstoqueDetalheDTO> consultarEstoquePorCnpj(Long cnpj);
    EstoqueResponse adicionarMedicamentoAoEstoque(EstoqueRequest estoqueRequest);
    EstoqueResponse venderMedicamentoDoEstoque(EstoqueRequest estoqueRequest);

}
