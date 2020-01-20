package br.com.hbsis.funcionario;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
public class FuncionarioServiceTeste {
    @Mock
    private IFuncionarioRepository iFuncionarioRepository;

    @Captor
    private ArgumentCaptor<Funcionario> argumentCaptor;

    @InjectMocks
    private FuncionarioService funcionarioService;

    @Test
    public void save() {
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO(1L, "Bruno", "vipatim859@seomail.top", "");

        Funcionario funcionarioMock = Mockito.mock(Funcionario.class);

        when(funcionarioMock.getId()).thenReturn(funcionarioDTO.getId());
        when(funcionarioMock.getNome()).thenReturn(funcionarioDTO.getNome());
        when(funcionarioMock.getEmail()).thenReturn(funcionarioDTO.getEmail());
        when(funcionarioMock.getUuid()).thenReturn(funcionarioDTO.getUuid());

        when(this.iFuncionarioRepository.save(any())).thenReturn(funcionarioMock);

        this.funcionarioService.save(funcionarioDTO);

        verify(this.iFuncionarioRepository, times(1)).save(this.argumentCaptor.capture());
        Funcionario createdFuncionario = argumentCaptor.getValue();

        assertTrue(StringUtils.isNoneEmpty(createdFuncionario.getNome()), "Nome não deve ser nula");
        assertTrue(StringUtils.isNoneEmpty(createdFuncionario.getEmail()), "Email não deve ser nulo");

    }

}
