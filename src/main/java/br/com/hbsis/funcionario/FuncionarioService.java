package br.com.hbsis.funcionario;

import com.microsoft.sqlserver.jdbc.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Collections;
import java.util.Objects;

@Service
public class FuncionarioService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FuncionarioService.class);
    private final IFuncionarioRepository iFuncionarioRepository;

    @Autowired
    public FuncionarioService(IFuncionarioRepository iFuncionarioRepository) {
        this.iFuncionarioRepository = iFuncionarioRepository;
    }

    public FuncionarioDTO save(FuncionarioDTO funcionarioDTO) {
        this.validate(funcionarioDTO);

        LOGGER.info("Cadastrando funcionario");
        LOGGER.debug("Funcionario: {}", funcionarioDTO);

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(funcionarioDTO.getNome());
        funcionario.setEmail(funcionarioDTO.getEmail());
        funcionario.setUuid(funcionarioDTO.getUuid());

        funcionario = this.iFuncionarioRepository.save(funcionario);

        return FuncionarioDTO.of(funcionario);
    }

    private void validate(FuncionarioDTO funcionarioDTO) {
        LOGGER.info("Validando Funcionario");

        if (funcionarioDTO == null) {
            throw new IllegalArgumentException("FuncionarioDTO não deve ser nulo");
        }

        if (StringUtils.isEmpty(funcionarioDTO.getNome())) {
            throw new IllegalArgumentException("Nome não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(funcionarioDTO.getEmail())) {
            throw new IllegalArgumentException("Email não deve ser nulo/vazio");
        }

        this.validaAPI(funcionarioDTO);
    }

    private void validaAPI(FuncionarioDTO funcionarioDTO) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "f5a00032-1b67-11ea-978f-2e728ce88125");
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity httpEntity = new HttpEntity(funcionarioDTO, headers);
        ResponseEntity<EmployeeDTO> response = template.exchange("http://10.2.54.25:9999/api/employees", HttpMethod.POST, httpEntity, EmployeeDTO.class);
        funcionarioDTO.setUuid(Objects.requireNonNull(response.getBody()).getEmployeeUuid());
        funcionarioDTO.setNome(response.getBody().getEmployeeName());
    }

    public Funcionario findByIdFuncionario(Long id){
        return this.iFuncionarioRepository.findById(id).get();
    }

}
