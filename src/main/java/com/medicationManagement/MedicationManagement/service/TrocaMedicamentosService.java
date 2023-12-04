package com.medicationManagement.MedicationManagement.service;

import com.medicationManagement.MedicationManagement.dto.TrocaMedicamentosRequest;
import com.medicationManagement.MedicationManagement.dto.TrocaMedicamentosResponse;

public interface TrocaMedicamentosService {
    TrocaMedicamentosResponse trocarMedicamentos(TrocaMedicamentosRequest request);
}
