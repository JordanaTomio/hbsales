package br.com.hbsis.linhaCategoria;

import br.com.hbsis.categoria.Categoria;
import br.com.hbsis.categoria.CategoriaDTO;
import br.com.hbsis.categoria.CategoriaService;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.fornecedor.FornecedorService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LinhaCategoriaServiceTeste {
    @Mock
    private ILinhaCategoriaRepository iLinhaCategoriaRepository;

    @Mock
    private FornecedorService fornecedorService;

    @Mock
    private CategoriaService categoriaService;

    @Captor
    private ArgumentCaptor<LinhaCategoria> argumentCaptor;

    @InjectMocks
    private LinhaCategoriaService linhaCategoriaService;


    @Test
    public void save() {
        CategoriaDTO categoriaDTO = new CategoriaDTO(12L, "Bebida", 2L, "123");
        FornecedorDTO fornecedorDTO = new FornecedorDTO(2L, "razao", "86896869000181", "nomeFantasia", "enderecoFantasia", "1435144865", "teste@gmail.com");
        LinhaCategoriaDTO linhaCategoriaDTO = new LinhaCategoriaDTO(2L, 12L, "Bud", "1257");

        Categoria categoriaMock = Mockito.mock(Categoria.class);
        Fornecedor fornecedorMock = Mockito.mock(Fornecedor.class);
        LinhaCategoria linhaCategoriaMock = Mockito.mock(LinhaCategoria.class);

        when(categoriaMock.getId()).thenReturn(categoriaDTO.getId());
        when(categoriaMock.getNome()).thenReturn(categoriaDTO.getNome());
        when(categoriaMock.getCodigo()).thenReturn(categoriaDTO.getCodigo());

        when(fornecedorMock.getCnpj()).thenReturn(fornecedorDTO.getCnpj());
        when(fornecedorMock.getId()).thenReturn(fornecedorDTO.getId());
        when(fornecedorMock.getRazao()).thenReturn(fornecedorDTO.getRazao());

        when(linhaCategoriaMock.getId()).thenReturn(linhaCategoriaDTO.getId());
        when(linhaCategoriaMock.getCodigoLinha()).thenReturn(linhaCategoriaDTO.getCodigoLinha());
        when(linhaCategoriaMock.getNomeLinha()).thenReturn(linhaCategoriaDTO.getNomeLinha());

        when(categoriaService.findByIdCategoria(any())).thenReturn(categoriaMock);
        when(fornecedorService.findByIdFornecedor(any())).thenReturn(fornecedorMock);

        when(categoriaMock.getId()).thenReturn(linhaCategoriaDTO.getCategoria());
        when(fornecedorMock.getId()).thenReturn(categoriaDTO.getIdFornecedor());

        when(this.iLinhaCategoriaRepository.save(any())).thenReturn(linhaCategoriaMock);

        this.linhaCategoriaService.save(linhaCategoriaDTO);

        verify(this.iLinhaCategoriaRepository, times(1)).save(this.argumentCaptor.capture());

        LinhaCategoria createdLinha = argumentCaptor.getValue();

        assertTrue(StringUtils.isNoneEmpty(createdLinha.getCodigoLinha()), "Codigo não deve ser nulo");
        assertTrue(StringUtils.isNoneEmpty(createdLinha.getNomeLinha()), "Nome não deve ser nulo");
        assertTrue(StringUtils.isNoneEmpty(createdLinha.getCategoria().getNome()), "Categoria não deve ser nulo");

        assertEquals(10, createdLinha.getCodigoLinha().length(), "Codigo deve ter 10 posições");

    }
    @Test
    public void linhaDTONull() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.linhaCategoriaService.save(null);
        });
    }

    @Test
    public void linhaNomeNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            LinhaCategoriaDTO linhaCategoriaDTO = new LinhaCategoriaDTO(2L, 2L, null, "1234");
            this.linhaCategoriaService.save(linhaCategoriaDTO);
        });
    }

    @Test
    public void linhaCodigoNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            LinhaCategoriaDTO linhaCategoriaDTO = new LinhaCategoriaDTO(2L, 2L, "Teste", null);
            this.linhaCategoriaService.save(linhaCategoriaDTO);
        });
    }
    @Test
    public void linhaCategoriaNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            LinhaCategoriaDTO linhaCategoriaDTO = new LinhaCategoriaDTO(2L, null, "Teste", "1234");
            this.linhaCategoriaService.save(linhaCategoriaDTO);
        });
    }
}
