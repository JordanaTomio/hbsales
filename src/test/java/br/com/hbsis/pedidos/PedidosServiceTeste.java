package br.com.hbsis.pedidos;

import br.com.hbsis.categoria.Categoria;
import br.com.hbsis.categoria.CategoriaDTO;
import br.com.hbsis.categoria.CategoriaService;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.funcionario.FuncionarioDTO;
import br.com.hbsis.funcionario.FuncionarioService;
import br.com.hbsis.linhaCategoria.LinhaCategoria;
import br.com.hbsis.linhaCategoria.LinhaCategoriaDTO;
import br.com.hbsis.linhaCategoria.LinhaCategoriaService;
import br.com.hbsis.periodoVendas.IPeriodoRepository;
import br.com.hbsis.periodoVendas.Periodo;
import br.com.hbsis.periodoVendas.PeriodoDTO;
import br.com.hbsis.periodoVendas.PeriodoService;
import br.com.hbsis.produtos.Produtos;
import br.com.hbsis.produtos.ProdutosDTO;
import br.com.hbsis.produtos.ProdutosService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PedidosServiceTeste {
    @Mock
    private FornecedorService fornecedorService;

    @Mock
    private LinhaCategoriaService linhaCategoriaService;

    @Mock
    private CategoriaService categoriaService;

    @Mock
    private PeriodoService periodoService;

    @Mock
    private ProdutosService produtosService;

    @Mock
    private FuncionarioService funcionarioService;

    @Mock
    private IPedidosRepository iPedidosRepository;

    @Captor
    private ArgumentCaptor<Pedidos> argumentCaptor;

    @InjectMocks
    private PedidosService pedidosService;

    @Test
    public void save() {
        CategoriaDTO categoriaDTO = new CategoriaDTO(12L, "Bebida", 2L, "123");
        FornecedorDTO fornecedorDTO = new FornecedorDTO(2L, "razao", "86896869000181", "nomeFantasia", "enderecoFantasia", "1435144865", "teste@gmail.com");
        LinhaCategoriaDTO linhaCategoriaDTO = new LinhaCategoriaDTO(2L, 12L, "Bud", "1257");
        PeriodoDTO periodoDTO = new PeriodoDTO(1L, LocalDate.now(), LocalDate.now().plusDays(20), LocalDate.now().plusDays(21), "pedido pedido", 2L);
        ProdutosDTO produtosDTO = new ProdutosDTO(1L, 1L, "Budweiser", "4536", 12.345, 16, 123.4534, LocalDate.now(), "g");
        PedidosDTO pedidosDTO = new PedidosDTO(1L, "Teste", StatusName.ATIVO, LocalDate.now(), 2L, 1L, 15, 1L, 4, 1L );
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO(1L, "Bruno", "vipatim859@seomail.top", "5e0641f8fa1a6011832fff79");

        Categoria categoriaMock = Mockito.mock(Categoria.class);
        Fornecedor fornecedorMock = Mockito.mock(Fornecedor.class);
        LinhaCategoria linhaCategoriaMock = Mockito.mock(LinhaCategoria.class);
        Periodo periodoMock = Mockito.mock(Periodo.class);
        Produtos produtosMock = Mockito.mock(Produtos.class);
        Pedidos pedidosMock = Mockito.mock(Pedidos.class);
        Funcionario funcionarioMock = Mockito.mock(Funcionario.class);

        when(funcionarioMock.getId()).thenReturn(funcionarioDTO.getId());
        when(funcionarioMock.getNome()).thenReturn(funcionarioDTO.getNome());
        when(funcionarioMock.getUuid()).thenReturn(funcionarioDTO.getUuid());
        when(funcionarioMock.getEmail()).thenReturn(funcionarioDTO.getEmail());

        when(pedidosMock.getCodigo()).thenReturn(produtosDTO.getCodigo());
        when(pedidosMock.getStatus()).thenReturn(pedidosDTO.getStatus());
        when(pedidosMock.getQuantidade()).thenReturn(pedidosDTO.getQuantidade());
        when(pedidosMock.getId()).thenReturn(pedidosDTO.getId());
        when(pedidosMock.getDataCriacao()).thenReturn(pedidosDTO.getDataCriacao());
        when(pedidosMock.getValorTotal()).thenReturn(pedidosDTO.getValorTotal());

        when(produtosMock.getNome()).thenReturn(produtosDTO.getNome());
        when(produtosMock.getPreco()).thenReturn(produtosDTO.getPreco());
        when(produtosMock.getCodigo()).thenReturn(produtosDTO.getCodigo());
        when(produtosMock.getPesoUnidade()).thenReturn(produtosDTO.getPesoUnidade());
        when(produtosMock.getUnidadeCaixa()).thenReturn(produtosDTO.getUnidadeCaixa());
        when(produtosMock.getUnidadeMedida()).thenReturn(produtosDTO.getUnidadeMedida());
        when(produtosMock.getValidade()).thenReturn(produtosDTO.getValidade());
        when(produtosMock.getId()).thenReturn(produtosDTO.getId());

        when(periodoMock.getId()).thenReturn(periodoDTO.getId());
        when(periodoMock.getInicioVendas()).thenReturn(periodoDTO.getInicioVendas());
        when(periodoMock.getFimVendas()).thenReturn(periodoDTO.getFimVendas());
        when(periodoMock.getRetiradaPedido()).thenReturn(periodoDTO.getRetiradaPedido());
        when(periodoMock.getDescricao()).thenReturn(periodoDTO.getDescricao());

        when(categoriaMock.getId()).thenReturn(categoriaDTO.getId());
        when(categoriaMock.getNome()).thenReturn(categoriaDTO.getNome());
        when(categoriaMock.getCodigo()).thenReturn(categoriaDTO.getCodigo());

        when(fornecedorMock.getCnpj()).thenReturn(fornecedorDTO.getCnpj());
        when(fornecedorMock.getId()).thenReturn(fornecedorDTO.getId());
        when(fornecedorMock.getRazao()).thenReturn(fornecedorDTO.getRazao());

        when(linhaCategoriaMock.getId()).thenReturn(linhaCategoriaDTO.getId());
        when(linhaCategoriaMock.getCodigoLinha()).thenReturn(linhaCategoriaDTO.getCodigoLinha());
        when(linhaCategoriaMock.getNomeLinha()).thenReturn(linhaCategoriaDTO.getNomeLinha());

        when(funcionarioService.findByIdFuncionario(any())).thenReturn(funcionarioMock);
        when(periodoService.findByIdPeriodo(any())).thenReturn(periodoMock);
        when(produtosService.findByIdProduto(any())).thenReturn(produtosMock);
        when(linhaCategoriaService.findByIdLinha(any())).thenReturn(linhaCategoriaMock);
        when(categoriaService.findByIdCategoria(any())).thenReturn(categoriaMock);
        when(fornecedorService.findByIdFornecedor(any())).thenReturn(fornecedorMock);

        when(fornecedorMock.getId()).thenReturn(pedidosDTO.getIdFornecedor());
        when(periodoMock.getId()).thenReturn(pedidosDTO.getIdPeriodo());
        when(funcionarioMock.getId()).thenReturn(pedidosDTO.getIdFuncionario());
        when(produtosMock.getId()).thenReturn(pedidosDTO.getProduto());
        when(linhaCategoriaMock.getId()).thenReturn(produtosDTO.getIdLinha());
        when(fornecedorMock.getId()).thenReturn(periodoDTO.getIdFornecedor());
        when(categoriaMock.getId()).thenReturn(linhaCategoriaDTO.getCategoria());
        when(fornecedorMock.getId()).thenReturn(categoriaDTO.getIdFornecedor());

        when(this.iPedidosRepository.save(any())).thenReturn(pedidosMock);

        this.pedidosService.save(pedidosDTO);

        verify(this.iPedidosRepository, times(1)).save(this.argumentCaptor.capture());

        Pedidos createdPedidos = argumentCaptor.getValue();

    }
}
