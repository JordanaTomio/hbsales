package br.com.hbsis.fornecedor;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FornecedorServiceTeste {
    @Mock
    private IFornecedorRepository iFornecedorRepository;

    @Captor
    private ArgumentCaptor<Fornecedor> argumentCaptor;

    @InjectMocks
    private FornecedorService fornecedorService;

    @Test
    public void save() {
        FornecedorDTO fornecedorDTO = new FornecedorDTO((long) 12, "razao", "86896869000181", "nomeFantasia", "enderecoFantasia", "1435144865", "teste@gmail.com");

        Fornecedor fornecedorMock = Mockito.mock(Fornecedor.class);

        when(fornecedorMock.getId()).thenReturn(fornecedorDTO.getId());
        when(fornecedorMock.getCnpj()).thenReturn(fornecedorDTO.getCnpj());
        when(fornecedorMock.getRazao()).thenReturn(fornecedorDTO.getRazao());
        when(fornecedorMock.getEmail()).thenReturn(fornecedorDTO.getEmail());
        when(fornecedorMock.getTelefone()).thenReturn(fornecedorDTO.getTelefone());
        when(fornecedorMock.getEndereco()).thenReturn(fornecedorDTO.getEndereco());
        when(fornecedorMock.getNomeFantasia()).thenReturn(fornecedorDTO.getNomeFantasia());

        when(this.iFornecedorRepository.save(any())).thenReturn(fornecedorMock);

        this.fornecedorService.save(fornecedorDTO);

        verify(this.iFornecedorRepository, times(1)).save(this.argumentCaptor.capture());
        Fornecedor createdFornecedor = argumentCaptor.getValue();

        assertTrue(StringUtils.isNoneEmpty(createdFornecedor.getRazao()), "Razão não deve ser nula");
        assertTrue(StringUtils.isNoneEmpty(createdFornecedor.getNomeFantasia()), "Nome fantasia não deve ser nulo");
        assertTrue(StringUtils.isNoneEmpty(createdFornecedor.getCnpj()), "CNPJ não deve ser nulo");
        assertTrue(StringUtils.isNoneEmpty(createdFornecedor.getEmail()), "Email não deve ser nulo");
        assertTrue(StringUtils.isNoneEmpty(createdFornecedor.getTelefone()), "Telefone não deve ser nulo");
        assertTrue(StringUtils.isNoneEmpty(createdFornecedor.getEndereco()), "Endereco não deve ser nulo");

        assertEquals(14, createdFornecedor.getCnpj().length(), "CNPJ deve ter 14 posições");
    }
    @Test
    public void fornecedorDTONull() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.fornecedorService.save(null);
        });
    }

    @Test
    public void fornecedorComCNPJNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            FornecedorDTO fornecedorDTO = new FornecedorDTO((long) 12, "razao", null, "nomeFantasia", "enderecoFantasia", "1435144865", "teste@gmail.com");
            this.fornecedorService.save(fornecedorDTO);
        });
    }

    @Test
    public void fornecedorComNomeNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            FornecedorDTO fornecedorDTO = new FornecedorDTO(12L, "razao", "86896869000181", null, "enderecoFantasia", "1435144865", "teste@gmail.com");
            this.fornecedorService.save(fornecedorDTO);
        });
    }

    @Test
    public void fornecedorComEnderecoNull(){
        assertThrows(IllegalArgumentException.class, () -> {
            FornecedorDTO fornecedorDTO = new FornecedorDTO(12L, "razao", "86896869000181", "nomeFantasia", null, "1435144865", "teste@gmail.com");
            this.fornecedorService.save(fornecedorDTO);
        });
    }

    @Test
    public void fornecedorComTelefoneNull(){
        assertThrows(IllegalArgumentException.class, () -> {
            FornecedorDTO fornecedorDTO = new FornecedorDTO(12L, "razao", "86896869000181", "nomeFantasia", "enderecoFantasia", null, "teste@gmail.com");
            this.fornecedorService.save(fornecedorDTO);
        });
    }
    @Test
    public void fornecedorComEmailNull(){
        assertThrows(IllegalArgumentException.class, () -> {
            FornecedorDTO fornecedorDTO = new FornecedorDTO(12L, "razao", "86896869000181", "nomeFantasia", "enderecoFantasia", "1435144865", null);
            this.fornecedorService.save(fornecedorDTO);
        });
    }

}


