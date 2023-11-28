package com.medicationManagement.MedicationManagement.service;


import com.medicationManagement.MedicationManagement.model.*;
import com.medicationManagement.MedicationManagement.repository.EstoqueRepository;
import com.medicationManagement.MedicationManagement.repository.FarmaciaRepository;
import com.medicationManagement.MedicationManagement.repository.MedicamentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InicializacaoService {

    private final FarmaciaRepository farmaciaRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final EstoqueRepository estoqueRepository;

    @Autowired
    public InicializacaoService(FarmaciaRepository farmaciaRepository, MedicamentoRepository medicamentoRepository, EstoqueRepository estoqueRepository) {
        this.farmaciaRepository = farmaciaRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.estoqueRepository = estoqueRepository;
    }

    @Transactional
    public void popularDadosIniciais() {

        // Inserir dados das farmácias
        Farmacia farmacia1 = new Farmacia(
                90561736000121L,
                "DevMed Ltda",
                "Farmácia DevMed",
                "devmed@farmacia.com",
                "(44)4444-4444",
                "(44)9444-4441",
                new Endereco(
                        8888899L,
                        "Rua Porto Rea",
                        67,
                        "Westeros",
                        "Berlim",
                        "SC",
                        "",
                        15.23456,
                        2.8678687
                ));
        farmaciaRepository.save(farmacia1);

        Farmacia farmacia2 = new Farmacia(
                43178995000198L,
                "MedHouse Ltda",
                "Farmácia MedHouse",
                "medhouse@farmacia.com",
                "(55)5555-5555",
                "(45)95555-5555",
                new Endereco(
                        8877799L,
                        "Rua Madrid",
                        76,
                        "Winterfell",
                        "Estocolmo",
                        "SC",
                        "",
                        19.254356,
                        3.8987687
                ));
        farmaciaRepository.save(farmacia2);

        // Inserir dados dos medicamentos
        Medicamento medicamento1 = new Medicamento(
                1010,
                "Programapan",
                "Matrix",
                "2x ao dia",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc eleifend",
                111.00f,
                TipoMedicamento.COMUM);
        medicamentoRepository.save(medicamento1);

        Medicamento medicamento2 = new Medicamento(
                7473,
                "Cafex",
                "Colombia Farm",
                "4x ao dia",
                "Pellentesque non ultricies mauris, ut lobortis elit. Cras nec cursus nibh",
                51.50f,
                TipoMedicamento.COMUM);
        medicamentoRepository.save(medicamento2);

        Medicamento medicamento3 = new Medicamento(
                2233,
                "Estomazol",
                "Acme",
                "1x ao dia",
                "Sed risus mauris, consectetur eget egestas vitae",
                22.50f,
                TipoMedicamento.COMUM);
        medicamentoRepository.save(medicamento3);

        Medicamento medicamento4 = new Medicamento(
                8880,
                "Gelox",
                "Ice",
                "2x ao dia",
                "Quisque quam orci, vulputate sit amet",
                11.50f,
                TipoMedicamento.COMUM);
        medicamentoRepository.save(medicamento4);

        Medicamento medicamento5 = new Medicamento(
                5656,
                "Aspirazol",
                "Acme",
                "3x ao dia",
                "Sed faucibus, libero iaculis pulvinar consequat, augue elit eleifend",
                10.50f,
                TipoMedicamento.CONTROLADO);
        medicamentoRepository.save(medicamento5);

        Medicamento medicamento6 = new Medicamento(
                4040,
                "Propolizol",
                "Bee",
                "5x ao dia",
                "Nunc euismod ipsum purus, sit amet finibus libero ultricies in",
                10.50f,
                TipoMedicamento.CONTROLADO);
        medicamentoRepository.save(medicamento6);


         //Inserir dados dos estoques
        Estoque estoque1 = new Estoque(
                90561736000121L,
                1010,
                12,
                LocalDateTime.now());
        estoqueRepository.save(estoque1);

        Estoque estoque2 = new Estoque(
                90561736000121L,
                7473,
                10,
                LocalDateTime.now());
        estoqueRepository.save(estoque2);

        Estoque estoque3 = new Estoque(
                43178995000198L,
                7473,
                2,
                LocalDateTime.now());
        estoqueRepository.save(estoque3);

        Estoque estoque4 = new Estoque(
                43178995000198L,
                2233,
                15,
                LocalDateTime.now());
        estoqueRepository.save(estoque4);

        Estoque estoque5 = new Estoque(
                43178995000198L,
                8880,
                16,
                LocalDateTime.now());
        estoqueRepository.save(estoque5);

        Estoque estoque6 = new Estoque(
                43178995000198L,
                4040,
                22,
                LocalDateTime.now());
        estoqueRepository.save(estoque6);

    }
}
