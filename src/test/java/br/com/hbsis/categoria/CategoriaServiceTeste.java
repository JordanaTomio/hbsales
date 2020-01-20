package br.com.hbsis.categoria;

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
public class CategoriaServiceTeste {
    @Mock
    private ICategoriaRepository iCategoriaRepository;

    @Mock
    private FornecedorService fornecedorService;

    @Captor
    private ArgumentCaptor<Categoria> argumentCaptor;

    @InjectMocks
    private CategoriaService categoriaService;

    @Test
    public void save() {
        CategoriaDTO categoriaDTO = new CategoriaDTO(12L, "Bebida", 2L, "123");
        FornecedorDTO fornecedorDTO = new FornecedorDTO(2L, "razao", "86896869000181", "nomeFantasia", "enderecoFantasia", "1435144865", "teste@gmail.com");

        Categoria categoriaMock = Mockito.mock(Categoria.class);
        Fornecedor fornecedorMock = Mockito.mock(Fornecedor.class);

        when(categoriaMock.getId()).thenReturn(categoriaDTO.getId());
        when(categoriaMock.getNome()).thenReturn(categoriaDTO.getNome());
        when(categoriaMock.getCodigo()).thenReturn(categoriaDTO.getCodigo());

        when(fornecedorMock.getCnpj()).thenReturn(fornecedorDTO.getCnpj());
        when(fornecedorMock.getId()).thenReturn(fornecedorDTO.getId());
        when(fornecedorMock.getRazao()).thenReturn(fornecedorDTO.getRazao());

        when(fornecedorService.findByIdFornecedor(any())).thenReturn(fornecedorMock);

        when(fornecedorMock.getId()).thenReturn(categoriaDTO.getIdFornecedor());

        when(this.iCategoriaRepository.save(any())).thenReturn(categoriaMock);

        this.categoriaService.save(categoriaDTO);

        verify(this.iCategoriaRepository, times(1)).save(this.argumentCaptor.capture());
        Categoria createdCategoria = argumentCaptor.getValue();

        assertTrue(StringUtils.isNoneEmpty(createdCategoria.getCodigo()), "Codigo não deve ser nulo");
        assertTrue(StringUtils.isNoneEmpty(createdCategoria.getNome()), "Nome não deve ser nulo");
        assertTrue(StringUtils.isNoneEmpty(createdCategoria.getFornecedor().getRazao()), "Fornecedor não deve ser nulo");

        assertEquals(10, createdCategoria.getCodigo().length(), "Codigo deve ter 10 posições");
    }

    @Test
    public void categoriaDTONull() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.categoriaService.save(null);
        });
    }

    @Test
    public void categoriaNomeNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            CategoriaDTO categoriaDTO = new CategoriaDTO(12L, null, 2L, "123");
            this.categoriaService.save(categoriaDTO);
        });
    }
    
    @Test
    public void categoriaCodigoNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            CategoriaDTO categoriaDTO = new CategoriaDTO(12L, "bebidas", 2L, null);
            this.categoriaService.save(categoriaDTO);
        });
    }

    @Test
    public void categoriaFornecedorNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            CategoriaDTO categoriaDTO = new CategoriaDTO(12L, "bebidas", null, "123");
            this.categoriaService.save(categoriaDTO);
        });
    }
}
