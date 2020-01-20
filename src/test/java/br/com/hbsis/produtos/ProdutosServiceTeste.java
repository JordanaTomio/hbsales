package br.com.hbsis.produtos;

import br.com.hbsis.categoria.Categoria;
import br.com.hbsis.categoria.CategoriaDTO;
import br.com.hbsis.categoria.CategoriaService;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.linhaCategoria.LinhaCategoria;
import br.com.hbsis.linhaCategoria.LinhaCategoriaDTO;
import br.com.hbsis.linhaCategoria.LinhaCategoriaService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProdutosServiceTeste {
    @Mock
    private IProdutosRepository iProdutosRepository;

    @Mock
    private FornecedorService fornecedorService;

    @Mock
    private CategoriaService categoriaService;

    @Mock
    private LinhaCategoriaService linhaCategoriaService;

    @InjectMocks
    private ProdutosService produtosService;

    @Captor
    private ArgumentCaptor<Produtos> argumentCaptor;

    @Test
    public void save() {
        LocalDate data = LocalDate.now();

        CategoriaDTO categoriaDTO = new CategoriaDTO(1L, "Bebida", 1L, "123");
        FornecedorDTO fornecedorDTO = new FornecedorDTO(1L, "razao", "86896869000181", "nomeFantasia", "enderecoFantasia", "1435144865", "teste@gmail.com");
        LinhaCategoriaDTO linhaCategoriaDTO = new LinhaCategoriaDTO(1L, 1L, "Bud", "1257");
        ProdutosDTO produtosDTO = new ProdutosDTO(1L, 1L, "Budweiser", "4536", 12.345, 16, 123.4534, data, "g");

        Categoria categoriaMock = Mockito.mock(Categoria.class);
        Fornecedor fornecedorMock = Mockito.mock(Fornecedor.class);
        LinhaCategoria linhaCategoriaMock = Mockito.mock(LinhaCategoria.class);
        Produtos produtosMock = Mockito.mock(Produtos.class);

        when(categoriaMock.getId()).thenReturn(categoriaDTO.getId());
        when(categoriaMock.getNome()).thenReturn(categoriaDTO.getNome());
        when(categoriaMock.getCodigo()).thenReturn(categoriaDTO.getCodigo());

        when(fornecedorMock.getCnpj()).thenReturn(fornecedorDTO.getCnpj());
        when(fornecedorMock.getId()).thenReturn(fornecedorDTO.getId());
        when(fornecedorMock.getRazao()).thenReturn(fornecedorDTO.getRazao());

        when(linhaCategoriaMock.getId()).thenReturn(linhaCategoriaDTO.getId());
        when(linhaCategoriaMock.getCodigoLinha()).thenReturn(linhaCategoriaDTO.getCodigoLinha());
        when(linhaCategoriaMock.getNomeLinha()).thenReturn(linhaCategoriaDTO.getNomeLinha());

        when(produtosMock.getNome()).thenReturn(produtosDTO.getNome());
        when(produtosMock.getPreco()).thenReturn(produtosDTO.getPreco());
        when(produtosMock.getCodigo()).thenReturn(produtosDTO.getCodigo());
        when(produtosMock.getPesoUnidade()).thenReturn(produtosDTO.getPesoUnidade());
        when(produtosMock.getUnidadeCaixa()).thenReturn(produtosDTO.getUnidadeCaixa());
        when(produtosMock.getUnidadeMedida()).thenReturn(produtosDTO.getUnidadeMedida());
        when(produtosMock.getValidade()).thenReturn(produtosDTO.getValidade());
        when(produtosMock.getId()).thenReturn(produtosDTO.getId());

        when(linhaCategoriaService.findByIdLinha(any())).thenReturn(linhaCategoriaMock);
        when(categoriaService.findByIdCategoria(any())).thenReturn(categoriaMock);
        when(fornecedorService.findByIdFornecedor(any())).thenReturn(fornecedorMock);

        when(linhaCategoriaMock.getId()).thenReturn(produtosDTO.getIdLinha());
        when(categoriaMock.getId()).thenReturn(linhaCategoriaDTO.getCategoria());
        when(fornecedorMock.getId()).thenReturn(categoriaDTO.getIdFornecedor());

        when(this.iProdutosRepository.save(any())).thenReturn(produtosMock);

        this.produtosService.save(produtosDTO);

        verify(this.iProdutosRepository, times(1)).save(this.argumentCaptor.capture());

        Produtos createdProdutos = argumentCaptor.getValue();

        assertTrue(StringUtils.isNoneEmpty(createdProdutos.getCodigo()), "Codigo não deve ser nulo");
        assertTrue(StringUtils.isNoneEmpty(createdProdutos.getNome()), "Nome não deve ser nulo");
        assertTrue(StringUtils.isNoneEmpty(createdProdutos.getUnidadeMedida()), "Unidade de medida não deve ser nula");
        assertTrue(StringUtils.isNoneEmpty(String.valueOf(createdProdutos.getPreco())), "Preco não deve ser nulo");
        assertTrue(StringUtils.isNoneEmpty(String.valueOf(createdProdutos.getPesoUnidade())), "Peso por unidade não deve ser nulo");
        assertTrue(StringUtils.isNoneEmpty(createdProdutos.getUnidadeMedida()), "Unidade de medida não deve ser nulo");
        assertTrue(StringUtils.isNoneEmpty(String.valueOf(createdProdutos.getUnidadeCaixa())), "Unidade por caixa não deve ser nulo");
        assertTrue(StringUtils.isNoneEmpty(String.valueOf(createdProdutos.getValidade())), "Validade não deve ser nula");

        assertEquals(10, createdProdutos.getCodigo().length(), "Codigo deve ter 10 posições");

    }
    @Test
    public void produtoDTONull() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.produtosService.save(null);
        });
    }

    @Test
    public void produtoNomeNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            ProdutosDTO produtosDTO = new ProdutosDTO(1L, 1L, null, "4536", 12.345, 16, 123.4534, LocalDate.now(), "g");
            this.produtosService.save(produtosDTO);
        });
    }

    @Test
    public void produtoCodigoNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            ProdutosDTO produtosDTO = new ProdutosDTO(1L, 1L, "Teste", null, 12.345, 16, 123.4534, LocalDate.now(), "g");
            this.produtosService.save(produtosDTO);
        });
    }

    @Test
    public void produtoUnidadeNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            ProdutosDTO produtosDTO = new ProdutosDTO(1L, 1L, "Teste", "4536", 12.345, null, 123.4534, LocalDate.now(), "g");
            this.produtosService.save(produtosDTO);
        });
    }

    @Test
    public void produtoDataNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            ProdutosDTO produtosDTO = new ProdutosDTO(1L, 1L, "Teste", "4536", 12.345, 16, 123.4534, null, "g");
            this.produtosService.save(produtosDTO);
        });
    }

    @Test
    public void produtoMedidaNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            ProdutosDTO produtosDTO = new ProdutosDTO(1L, 1L, "Teste", "4536", 12.345, 16, 123.4534, LocalDate.now(), null);
            this.produtosService.save(produtosDTO);
        });
    }
    @Test
    public void produtoLinhaNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            ProdutosDTO produtosDTO = new ProdutosDTO(1L, null, "Teste", "4536", 12.345, 16, 123.4534, LocalDate.now(), "g");
            this.produtosService.save(produtosDTO);
        });
    }
}
