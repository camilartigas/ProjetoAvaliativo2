package com.medicationManagement.MedicationManagement.service;

import com.medicationManagement.MedicationManagement.dto.FarmaciaRequest;
import com.medicationManagement.MedicationManagement.model.Endereco;
import com.medicationManagement.MedicationManagement.model.Farmacia;
import com.medicationManagement.MedicationManagement.repository.FarmaciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FarmaciaService {

    private final FarmaciaRepository farmaciaRepository;

    @Autowired
    public FarmaciaService(FarmaciaRepository farmaciaRepository) {

        this.farmaciaRepository = farmaciaRepository;
    }

    public List<Farmacia> listarTodasFarmacias() {

        return farmaciaRepository.findAll();
    }

    public Farmacia obterFarmaciaPorCnpj(Long cnpj) {
        return farmaciaRepository.findByCnpj(cnpj);
    }

    public Farmacia cadastrarFarmacia(FarmaciaRequest farmaciaRequest) {
        Long cnpj = farmaciaRequest.getCnpj();
        if (farmaciaRepository.existsByCnpj(cnpj)) {
            throw new RuntimeException("Já existe uma farmácia cadastrada com este CNPJ.");
        }

        Farmacia novaFarmacia = new Farmacia();
        novaFarmacia.setCnpj(farmaciaRequest.getCnpj());
        novaFarmacia.setRazaoSocial(farmaciaRequest.getRazaoSocial());
        novaFarmacia.setNomeFantasia(farmaciaRequest.getNomeFantasia());
        novaFarmacia.setEmail(farmaciaRequest.getEmail());
        novaFarmacia.setTelefone(farmaciaRequest.getTelefone());
        novaFarmacia.setCelular(farmaciaRequest.getCelular());

        Endereco endereco = new Endereco();
        endereco.setCep(farmaciaRequest.getEndereco().getCep());
        endereco.setLogradouro(farmaciaRequest.getEndereco().getLogradouro());
        endereco.setNumero(farmaciaRequest.getEndereco().getNumero());
        endereco.setBairro(farmaciaRequest.getEndereco().getBairro());
        endereco.setCidade(farmaciaRequest.getEndereco().getCidade());
        endereco.setEstado(farmaciaRequest.getEndereco().getEstado());
        endereco.setComplemento(farmaciaRequest.getEndereco().getComplemento());
        endereco.setLatitude(farmaciaRequest.getEndereco().getLatitude());
        endereco.setLongitude(farmaciaRequest.getEndereco().getLongitude());

        novaFarmacia.setEndereco(endereco);

        return farmaciaRepository.save(novaFarmacia);
    }
}
