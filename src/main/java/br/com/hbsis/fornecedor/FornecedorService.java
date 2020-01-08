package br.com.hbsis.fornecedor;

import br.com.hbsis.categoria.AlterarCodigo;
import com.microsoft.sqlserver.jdbc.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FornecedorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FornecedorService.class);
    private final IFornecedorRepository iFornecedorRepository;
    private final AlterarCodigo alterarCodigo;

    public FornecedorService(IFornecedorRepository iFornecedorRepository, AlterarCodigo alterarCodigo) {
        this.iFornecedorRepository = iFornecedorRepository;
        this.alterarCodigo = alterarCodigo;
    }

    public FornecedorDTO save(FornecedorDTO fornecedorDTO) {

        this.validate(fornecedorDTO);

        LOGGER.info("Salvando fornecedor");
        LOGGER.debug("Fornecedor: {}", fornecedorDTO);

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setCnpj(fornecedorDTO.getCnpj());
        fornecedor.setNomeFantasia(fornecedorDTO.getNomeFantasia());
        fornecedor.setEmail(fornecedorDTO.getEmail());
        fornecedor.setEndereco(fornecedorDTO.getEndereco());
        fornecedor.setRazao(fornecedorDTO.getRazao());
        fornecedor.setTelefone(fornecedorDTO.getTelefone());

        fornecedor = this.iFornecedorRepository.save(fornecedor);

        return FornecedorDTO.of(fornecedor);
    }

    private void validate(FornecedorDTO fornecedorDTO) {
        LOGGER.info("Validando Fornecedor");

        if (fornecedorDTO == null) {
            throw new IllegalArgumentException("FornecedorDTO não deve ser nulo");
        }

        if (StringUtils.isEmpty(fornecedorDTO.getCnpj())) {
            throw new IllegalArgumentException("CNPJ não deve ser nulo/vazio");
        }
        if (fornecedorDTO.getCnpj().length() != 14) {
            System.out.println(fornecedorDTO.getCnpj());
            throw new IllegalArgumentException("CNPJ não deve ter menos do que 14 digitos");
        }

        if (StringUtils.isEmpty(fornecedorDTO.getNomeFantasia())) {
            throw new IllegalArgumentException("Nome não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(fornecedorDTO.getEndereco())) {
            throw new IllegalArgumentException("Endereco não deve ser nulo/vazio");
        }
    }

    public FornecedorDTO findById(Long id) {
        Optional<Fornecedor> fornecedorOptional = this.iFornecedorRepository.findById(id);

        if (fornecedorOptional.isPresent()) {
            Fornecedor fornecedor = fornecedorOptional.get();
            return FornecedorDTO.of(fornecedor);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public Fornecedor findByIdFornecedor(Long id) {
        Optional<Fornecedor> fornecedorOptional = this.iFornecedorRepository.findById(id);

        if (fornecedorOptional.isPresent()) {
            return fornecedorOptional.get();
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public FornecedorDTO update(FornecedorDTO fornecedorDTO, Long id) {
        Optional<Fornecedor> fornecedorExistenteOptional = this.iFornecedorRepository.findById(id);

        if (fornecedorExistenteOptional.isPresent()) {
            Fornecedor fornecedorExistente = fornecedorExistenteOptional.get();

            LOGGER.info("Atualizando fornecedor... id: [{}]", fornecedorExistente.getId());
            LOGGER.debug("Payload: {}", fornecedorDTO);
            LOGGER.debug("Fornecedor Existente: {}", fornecedorExistente);

            fornecedorExistente.setCnpj(fornecedorDTO.getCnpj());
            fornecedorExistente.setNomeFantasia(fornecedorDTO.getNomeFantasia());
            fornecedorExistente.setTelefone(fornecedorDTO.getTelefone());
            fornecedorExistente.setRazao(fornecedorDTO.getRazao());
            fornecedorExistente.setEmail(fornecedorDTO.getEmail());
            fornecedorExistente.setEndereco(fornecedorDTO.getEndereco());
            alterarCodigo.alteraCategoria(fornecedorExistente);
            fornecedorExistente = this.iFornecedorRepository.save(fornecedorExistente);

            return FornecedorDTO.of(fornecedorExistente);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }


    public void delete(Long id) {
        LOGGER.info("Executando delete para fornecedor de ID: [{}]", id);

        this.iFornecedorRepository.deleteById(id);
    }

    public Fornecedor findFornecedor(String cnpj, String razao) {
        iFornecedorRepository.findByCnpjAndRazao(cnpj, razao);
        return iFornecedorRepository.findByCnpjAndRazao(cnpj, razao);
    }

    public boolean existsByIdFornecedor(Long id) {
        iFornecedorRepository.existsById(id);
        return iFornecedorRepository.existsById(id);
    }
}