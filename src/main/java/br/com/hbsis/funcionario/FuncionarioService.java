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
        funcionario.setNomeFuncionario(funcionarioDTO.getNome());
        funcionario.setEmailFuncionario(funcionarioDTO.getEmail());
        funcionario.setUuidFuncionario(funcionarioDTO.getUuid());

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
        if (StringUtils.isEmpty(funcionarioDTO.getUuid())) {
            throw new IllegalArgumentException("Uuid não deve ser nulo/vazio");
        }

        this.validateWithAPIHBEmployee(funcionarioDTO);
    }

    private void validateWithAPIHBEmployee(FuncionarioDTO funcionarioDTO) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "//key");
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity httpEntity = new HttpEntity(funcionarioDTO, headers);
        ResponseEntity<EmployeeSavingDTO> response = template.exchange("http://nt-04053:9999/api/employees", HttpMethod.POST, httpEntity, EmployeeSavingDTO.class);
        funcionarioDTO.setUuid(Objects.requireNonNull(response.getBody()).getEmployeeUuid());
        funcionarioDTO.setNome(response.getBody().getEmployeeName());
    }
}
