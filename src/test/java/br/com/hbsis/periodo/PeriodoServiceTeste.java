package br.com.hbsis.periodo;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.fornecedor.FornecedorService;

import br.com.hbsis.periodoVendas.IPeriodoRepository;
import br.com.hbsis.periodoVendas.Periodo;
import br.com.hbsis.periodoVendas.PeriodoDTO;
import br.com.hbsis.periodoVendas.PeriodoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PeriodoServiceTeste {

    @Mock
    private IPeriodoRepository iPeriodoRepository;

    @Mock
    private FornecedorService fornecedorService;

    @InjectMocks
    private PeriodoService periodoService;

    @Captor
    private ArgumentCaptor<Periodo> argumentCaptor;


    @Test
    public void save() {
        FornecedorDTO fornecedorDTO = new FornecedorDTO((long) 12, "razao", "86896869000181", "nomeFantasia", "enderecoFantasia", "1435144865", "teste@gmail.com");
        PeriodoDTO periodoDTO = new PeriodoDTO(1L, LocalDate.now(), LocalDate.now().plusDays(20), LocalDate.now().plusDays(21), "pedido pedido", 12L);

        Fornecedor fornecedorMock = Mockito.mock(Fornecedor.class);
        Periodo periodoMock = Mockito.mock(Periodo.class);

        when(fornecedorMock.getId()).thenReturn(fornecedorDTO.getId());
        when(fornecedorMock.getCnpj()).thenReturn(fornecedorDTO.getCnpj());

        when(periodoMock.getId()).thenReturn(periodoDTO.getId());
        when(periodoMock.getInicioVendas()).thenReturn(periodoDTO.getInicioVendas());
        when(periodoMock.getFimVendas()).thenReturn(periodoDTO.getFimVendas());
        when(periodoMock.getRetiradaPedido()).thenReturn(periodoDTO.getRetiradaPedido());
        when(periodoMock.getDescricao()).thenReturn(periodoDTO.getDescricao());

        when(fornecedorService.findByIdFornecedor(any())).thenReturn(fornecedorMock);

        when(fornecedorMock.getId()).thenReturn(periodoDTO.getIdFornecedor());

        when(this.iPeriodoRepository.save(any())).thenReturn(periodoMock);

        this.periodoService.save(periodoDTO);

        verify(this.iPeriodoRepository, times(1)).save(this.argumentCaptor.capture());
        Periodo createdPeriodo = argumentCaptor.getValue();

        assertTrue(StringUtils.isNoneEmpty(createdPeriodo.getInicioVendas().toString()), "Inicio de vendas não deve ser nulo");
        assertTrue(StringUtils.isNoneEmpty(createdPeriodo.getFimVendas().toString()), "Fim de vendas não deve ser nulo");
        assertTrue(StringUtils.isNoneEmpty(createdPeriodo.getRetiradaPedido().toString()), "Retirada de vendas não deve ser nulo");
        assertTrue(StringUtils.isNoneEmpty(createdPeriodo.getFornecedor().getId().toString()), "Fornecedor não deve ser nulo");

    }
    @Test
    public void periodoDTONull() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.periodoService.save(null);
        });
    }
    @Test
    public void periodoDescNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            PeriodoDTO periodoDTO = new PeriodoDTO(1L, LocalDate.now(), LocalDate.now().plusDays(20), LocalDate.now().plusDays(21), null, 12L);
            this.periodoService.save(periodoDTO);
        });
    }
    @Test
    public void periodoInicioVendasNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            PeriodoDTO periodoDTO = new PeriodoDTO(1L, null, LocalDate.now().plusDays(20), LocalDate.now().plusDays(21), "Vendaas", 12L);
            this.periodoService.save(periodoDTO);
        });
    }
    @Test
    public void periodoFimVendasNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            PeriodoDTO periodoDTO = new PeriodoDTO(1L, LocalDate.now(), null, LocalDate.now().plusDays(21), "Vendaas", 12L);
            this.periodoService.save(periodoDTO);
        });
    }
    @Test
    public void periodoRetiradaVendasNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            PeriodoDTO periodoDTO = new PeriodoDTO(1L, LocalDate.now(), LocalDate.now().plusDays(20), null, "Vendaas", 12L);
            this.periodoService.save(periodoDTO);
        });
    }
    @Test
    public void periodoFornecedorNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            PeriodoDTO periodoDTO = new PeriodoDTO(1L, LocalDate.now(), LocalDate.now().plusDays(20), LocalDate.now().plusDays(21), "Vendaas", null);
            this.periodoService.save(periodoDTO);
        });
    }
}
