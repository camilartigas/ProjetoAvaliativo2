package com.medicationManagement.MedicationManagement.service;

import com.medicationManagement.MedicationManagement.dto.FarmaciaRequest;
import com.medicationManagement.MedicationManagement.exception.FarmaciaExistenteException;
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

        //Verifica se CNPJ já está cadastrado
        if (farmaciaRepository.existsByCnpj(cnpj)) {
            throw new FarmaciaExistenteException("Já existe uma farmácia cadastrada com este CNPJ.");
        }

        Farmacia novaFarmacia = criarFarmaciaAPartirDoRequest(farmaciaRequest);
        return farmaciaRepository.save(novaFarmacia);
    }

    private void validarCamposObrigatorios(FarmaciaRequest farmaciaRequest) {

        if (farmaciaRequest.getCnpj() == null ||
                farmaciaRequest.getRazaoSocial() == null ||
                farmaciaRequest.getNomeFantasia() == null ||
                farmaciaRequest.getEmail() == null ||
                farmaciaRequest.getCelular() == null ||
                farmaciaRequest.getEndereco() == null ||
                farmaciaRequest.getEndereco().getCep() == null ||
                farmaciaRequest.getEndereco().getLogradouro() == null ||
                farmaciaRequest.getEndereco().getNumero() == null ||
                farmaciaRequest.getEndereco().getBairro() == null ||
                farmaciaRequest.getEndereco().getCidade() == null ||
                farmaciaRequest.getEndereco().getEstado() == null ||
                farmaciaRequest.getEndereco().getComplemento() == null ||
                farmaciaRequest.getEndereco().getLatitude() == null ||
                farmaciaRequest.getEndereco().getLongitude() == null) {
            throw new RuntimeException("Todos os campos obrigatórios devem ser preenchidos.");
        }
    }

    private Farmacia criarFarmaciaAPartirDoRequest(FarmaciaRequest farmaciaRequest) {
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

        return novaFarmacia;
    }
}
