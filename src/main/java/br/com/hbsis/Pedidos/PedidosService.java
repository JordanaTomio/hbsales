package br.com.hbsis.Pedidos;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.fornecedor.IFornecedorRepository;
import br.com.hbsis.periodoVendas.IPeriodoRepository;
import br.com.hbsis.periodoVendas.Periodo;
import br.com.hbsis.periodoVendas.PeriodoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PedidosService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PedidosService.class);
    private final IPedidosRepository iPedidosRepository;
    private final IFornecedorRepository iFornecedorRepository;
    private final IPeriodoRepository iPeriodoRepository;
    private final FornecedorService fornecedorService;
    private final PeriodoService periodoService;

    public PedidosService(IPedidosRepository iPedidosRepository, IFornecedorRepository iFornecedorRepository, IPeriodoRepository iPeriodoRepository, FornecedorService fornecedorService, PeriodoService periodoService) {
        this.iPedidosRepository = iPedidosRepository;
        this.iFornecedorRepository = iFornecedorRepository;
        this.iPeriodoRepository = iPeriodoRepository;
        this.fornecedorService = fornecedorService;
        this.periodoService = periodoService;
    }

    public PedidosDTO save(PedidosDTO pedidosDTO) {

        this.validate(pedidosDTO);

        LOGGER.info("Salvando pedido");
        LOGGER.debug("Pedido: {}", pedidosDTO);

        Pedidos pedidos = new Pedidos();
        Fornecedor fornecedorCompleto = new Fornecedor();
        Periodo periodoCompleto = new Periodo();

        pedidos.setCodigo(pedidosDTO.getCodigo());
        pedidos.setDataCriacao(pedidosDTO.getDataCriacao());

        fornecedorCompleto = fornecedorService.findByIdFornecedor(pedidosDTO.getIdFornecedor());
        periodoCompleto = periodoService.findByIdPeriodo(pedidosDTO.getIdPeriodo());

        pedidos.setFornecedor(fornecedorCompleto);
        pedidos.setPeriodo(periodoCompleto);
        pedidos.setProdutos(pedidosDTO.getProdutos());
        pedidos.setStatus(pedidosDTO.getStatus());


        pedidos = this.iPedidosRepository.save(pedidos);
        return PedidosDTO.of(pedidos);

    }
    private void validate(PedidosDTO pedidosDTO) {
        LOGGER.info("Validando pedido");

        if (pedidosDTO == null) {
            throw new IllegalArgumentException("PedidoDTO não deve ser nulo");
        }

        if (StringUtils.isEmpty(pedidosDTO.getCodigo())) {
            throw new IllegalArgumentException("Codigo do pedido não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(String.valueOf(pedidosDTO.getDataCriacao()))) {
            throw new IllegalArgumentException("Data da criação do pedido não deve ser nula/vazia");
        }
        if (StringUtils.isEmpty(String.valueOf(pedidosDTO.getProdutos()))) {
            throw new IllegalArgumentException("Lista de produtos não deve ser nula/vazia");
        }
        if (StringUtils.isEmpty(String.valueOf(pedidosDTO.getIdPeriodo()))) {
            throw new IllegalArgumentException("Periodo de venda do pedido não deve ser nula/vazia");
        }
    }




}
