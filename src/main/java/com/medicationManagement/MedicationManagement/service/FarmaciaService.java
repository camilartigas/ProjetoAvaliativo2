package com.medicationManagement.MedicationManagement.service;

import com.medicationManagement.MedicationManagement.dto.EnderecoRequest;
import com.medicationManagement.MedicationManagement.dto.FarmaciaRequest;
import com.medicationManagement.MedicationManagement.exception.EnderecoInvalidoException;
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

        // Verifica se CNPJ já está cadastrado
        if (farmaciaRepository.existsByCnpj(cnpj)) {
            throw new FarmaciaExistenteException("Já existe uma farmácia cadastrada com este CNPJ.");
        }

        EnderecoRequest enderecoRequest = farmaciaRequest.getEndereco();

        // Converte o EnderecoRequest para Endereco
        Endereco endereco = converterEnderecoRequestParaEndereco(enderecoRequest);

        // Verifica se o Endereco é nulo ou se algum campo obrigatório está vazio
        if (endereco == null || endereco.getCep() == null || endereco.getLogradouro() == null
                || endereco.getNumero() == null || endereco.getBairro() == null
                || endereco.getCidade() == null || endereco.getEstado() == null
                || endereco.getLatitude() == null || endereco.getLongitude() == null) {
            throw new EnderecoInvalidoException("O endereço é inválido ou está incompleto, apenas complemento poderá ficar sem preenchimento");
        }

        Farmacia novaFarmacia = criarFarmaciaAPartirDoRequest(farmaciaRequest, endereco);
        return farmaciaRepository.save(novaFarmacia);
    }

    private Endereco converterEnderecoRequestParaEndereco(EnderecoRequest enderecoRequest) {
        Endereco endereco = new Endereco();
        endereco.setCep(enderecoRequest.getCep());
        endereco.setLogradouro(enderecoRequest.getLogradouro());
        endereco.setNumero(enderecoRequest.getNumero());
        endereco.setBairro(enderecoRequest.getBairro());
        endereco.setCidade(enderecoRequest.getCidade());
        endereco.setEstado(enderecoRequest.getEstado());
        endereco.setComplemento(enderecoRequest.getComplemento());
        endereco.setLatitude(enderecoRequest.getLatitude());
        endereco.setLongitude(enderecoRequest.getLongitude());
        return endereco;
    }

    private Farmacia criarFarmaciaAPartirDoRequest(FarmaciaRequest farmaciaRequest, Endereco endereco) {
        Farmacia novaFarmacia = new Farmacia();
        novaFarmacia.setCnpj(farmaciaRequest.getCnpj());
        novaFarmacia.setRazaoSocial(farmaciaRequest.getRazaoSocial());
        novaFarmacia.setNomeFantasia(farmaciaRequest.getNomeFantasia());
        novaFarmacia.setEmail(farmaciaRequest.getEmail());
        novaFarmacia.setTelefone(farmaciaRequest.getTelefone());
        novaFarmacia.setCelular(farmaciaRequest.getCelular());
        novaFarmacia.setEndereco(endereco);
        return novaFarmacia;
    }
}
