package br.com.hbsis.funcionario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioRest {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(FuncionarioRest.class);

    private final FuncionarioService funcionarioService;

    @Autowired
    public FuncionarioRest(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    public FuncionarioDTO save(@RequestBody FuncionarioDTO funcionarioDTO) {
       LOGGER.info("Recebendo solicitação de persistência de cadastro de funcionários...");
       LOGGER.debug("Payaload: {}", funcionarioDTO);

        return this.funcionarioService.save(funcionarioDTO);
    }
}
