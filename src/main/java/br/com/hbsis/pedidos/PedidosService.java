package br.com.hbsis.pedidos;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.funcionario.FuncionarioService;
import br.com.hbsis.mail.Mail;
import br.com.hbsis.periodoVendas.Periodo;
import br.com.hbsis.periodoVendas.PeriodoService;
import br.com.hbsis.produtos.Produtos;
import br.com.hbsis.produtos.ProdutosService;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.MaskFormatter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

@Service
public class PedidosService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PedidosService.class);
    private final IPedidosRepository iPedidosRepository;
    private final FornecedorService fornecedorService;
    private final PeriodoService periodoService;
    private final FuncionarioService funcionarioService;
    private final ProdutosService produtosService;
    private final Mail mail;

    public PedidosService(IPedidosRepository iPedidosRepository, FornecedorService fornecedorService, PeriodoService periodoService, FuncionarioService funcionarioService, ProdutosService produtosService, Mail mail) {
        this.iPedidosRepository = iPedidosRepository;
        this.fornecedorService = fornecedorService;
        this.periodoService = periodoService;
        this.funcionarioService = funcionarioService;
        this.produtosService = produtosService;
        this.mail = mail;
    }

    public PedidosDTO save(PedidosDTO pedidosDTO) {

        this.validate(pedidosDTO);

        LOGGER.info("Salvando pedido");
        LOGGER.debug("Pedido: {}", pedidosDTO);

        new Produtos();
        new Periodo();
        new Fornecedor();
        new Funcionario();

        Pedidos pedidos = new Pedidos();
        Fornecedor fornecedorCompleto;
        Periodo periodoCompleto;
        Funcionario funcionarioCompleto;
        Produtos produtosCompleto;

        funcionarioCompleto = funcionarioService.findByIdFuncionario(pedidosDTO.getIdFuncionario());
        fornecedorCompleto = fornecedorService.findByIdFornecedor(pedidosDTO.getIdFornecedor());
        periodoCompleto = periodoService.findByIdPeriodo(pedidosDTO.getIdPeriodo());
        produtosCompleto = produtosService.findByIdProduto(pedidosDTO.getProduto());

        LocalDate hoje = LocalDate.now();
        String funcionarioUuid = funcionarioCompleto.getUuid();
        String produtoName = produtosCompleto.getNome();
        String cnpjFornecedor = fornecedorCompleto.getCnpj();
        int quantidade = pedidosDTO.getQuantidade();
        double preco = produtosCompleto.getPreco();
        double precoTotal = preco * quantidade;

        pedidos.setCodigo(pedidosDTO.getCodigo());
        pedidos.setDataCriacao(hoje);
        pedidos.setFornecedor(fornecedorCompleto);
        pedidos.setPeriodo(periodoCompleto);
        pedidos.setStatus(pedidosDTO.getStatus());
        pedidos.setValorTotal(precoTotal);
        pedidos.setFuncionario(funcionarioCompleto);
        pedidos.setProduto(produtosCompleto);
        pedidos.setQuantidade(pedidosDTO.getQuantidade());

        List<InvoiceItemDTOSet> invoiceItemDTOSetList = new ArrayList<>();
        InvoiceItemDTOSet invoiceItemDTOSet = new InvoiceItemDTOSet();
        invoiceItemDTOSet.setAmount(pedidosDTO.getQuantidade());
        invoiceItemDTOSet.setItemName(produtoName);
        invoiceItemDTOSetList.add(invoiceItemDTOSet);

        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setEmployeeUuid(funcionarioUuid);
        invoiceDTO.setTotalValue(precoTotal);
        invoiceDTO.setCnpjFornecedor(cnpjFornecedor);
        invoiceDTO.setInvoiceItemDTOSet(new HashSet<>(invoiceItemDTOSetList));
        this.validandoAPI(invoiceDTO);

        pedidos = this.iPedidosRepository.save(pedidos);
        mail.mailSave(pedidosDTO);
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

        if (StringUtils.isEmpty(String.valueOf(pedidosDTO.getIdPeriodo()))) {
            throw new IllegalArgumentException("Periodo de venda do pedido não deve ser nula/vazia");
        }

        new Fornecedor();
        Fornecedor fornecedorCompleto;
        new Periodo();
        Periodo periodoCompleto;
        new Produtos();
        Produtos produtoCompleto;

        produtoCompleto = produtosService.findByIdProduto(pedidosDTO.getProduto());
        periodoCompleto = periodoService.findByIdPeriodo(pedidosDTO.getIdPeriodo());
        fornecedorCompleto = fornecedorService.findByIdFornecedor(pedidosDTO.getIdFornecedor());

        this.validandoPeriodo(periodoCompleto, fornecedorCompleto);
        this.validandoFornecedor(produtoCompleto, fornecedorCompleto);
    }

    private void validandoAPI(InvoiceDTO invoiceDTO) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "f5a00032-1b67-11ea-978f-2e728ce88125");
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<InvoiceDTO> entidade = new HttpEntity<>(invoiceDTO, headers);
        ResponseEntity<InvoiceDTO> result = template.exchange("http://10.2.54.25:9999/api/invoice", HttpMethod.POST, entidade, InvoiceDTO.class); //out
    }

    private void validandoFornecedor(Produtos produtos, Fornecedor fornecedor) {
        LOGGER.info("Validando se produto pertence ao fornecedor");
        Long idFornecedor = produtos.getLinha().getCategoria().getFornecedor().getId();
        if (!idFornecedor.toString().equals(fornecedor.getId().toString())) {
            throw new IllegalArgumentException("Produto nao pertence ao fornecedor");
        } else {
            LOGGER.info("Produto pertence ao fornecedor!");
        }
    }

    private void validandoPeriodo(Periodo periodo, Fornecedor fornecedor) {
        LOGGER.info("Validando se existe um periodo de vendas para o fornecedor selecionado");
        Long idPeriodo = periodo.getFornecedor().getId();
        if (!idPeriodo.toString().equals(fornecedor.getId().toString())) {
            throw new IllegalArgumentException("Não existe um periodo de vendas para este fornecedor");
        } else {
            LOGGER.info("Existe um periodo de vendas para este fornecedor!");
        }
    }

    private List<Pedidos> findAll() {
        return iPedidosRepository.findAll();
    }

    public void exportPeriodo(HttpServletResponse response, Long id) throws IOException, ParseException {
        String nomearquivo = "exportandoPeriodoVendas.csv";
        response.setContentType("text/csv");
        response.setHeader(com.google.common.net.HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + nomearquivo + "\"");
        PrintWriter out = response.getWriter();

        ICSVWriter cvs = new CSVWriterBuilder(out)
                .withSeparator(';')
                .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build();

        String[] headerCSV = {"fornecedor", "nome_produto", "quantidade"};
        cvs.writeNext(headerCSV);
        for (Pedidos pedidos : findAll()) {
            Periodo periodoCompleto;
            periodoCompleto = periodoService.findByIdPeriodo(id);

            String quantidade = pedidos.getQuantidade().toString();
            String nomeProduto = pedidos.getProduto().getNome();

            if (pedidos.getPeriodo().getId().toString().equals(id.toString())) {
                String cnpjFornecedor = periodoCompleto.getFornecedor().getCnpj();
                MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
                mask.setValueContainsLiteralCharacters(false);
                String cnpjMask = mask.valueToString(cnpjFornecedor);

                String razaoFornecedor = periodoCompleto.getFornecedor().getRazao();
                String fornecedorPronto = razaoFornecedor + " - " + cnpjMask;

                cvs.writeNext(new String[]{fornecedorPronto, nomeProduto, quantidade});
            }
        }
    }

    public void exportFuncionario(HttpServletResponse response, Long id) throws IOException, ParseException {
        String nomearquivo = "exportandoVendasFuncionario.csv";
        response.setContentType("text/csv");
        response.setHeader(com.google.common.net.HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + nomearquivo + "\"");
        PrintWriter out = response.getWriter();

        ICSVWriter cvs = new CSVWriterBuilder(out)
                .withSeparator(';')
                .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build();

        String[] headerCSV = {"funcionario", "fornecedor", "nome_produto", "quantidade"};
        cvs.writeNext(headerCSV);

        for (Pedidos pedidos : findAll()) {
            if (pedidos.getFuncionario().getId().toString().equals(id.toString())) {
                Periodo periodoCompleto;
                periodoCompleto = periodoService.findByIdPeriodo(pedidos.getPeriodo().getId());

                String quantidade = pedidos.getQuantidade().toString();
                String nomeProduto = pedidos.getProduto().getNome();
                String cnpjFornecedor = periodoCompleto.getFornecedor().getCnpj();

                MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
                mask.setValueContainsLiteralCharacters(false);
                String cnpjMask = mask.valueToString(cnpjFornecedor);

                String razaoFornecedor = periodoCompleto.getFornecedor().getRazao();
                String fornecedorPronto = razaoFornecedor + " - " + cnpjMask;

                cvs.writeNext(new String[]{pedidos.getFuncionario().getNome(), fornecedorPronto, nomeProduto, quantidade});
            }
        }
    }

    public void visualizaPedidos(Long id) {
        for (Pedidos pedidos : findAll()) {
            String pedido;
            if (pedidos.getFuncionario().getId().toString().equals(id.toString())) {
                if (pedidos.getStatus().equals(StatusName.ATIVO) || pedidos.getStatus().equals(StatusName.RETIRADO)) {
                    pedido = pedidos.toString();
                    LOGGER.info(pedido);
                }
            }
        }
    }

    public void cancelaPedido(Long id) {
        new Pedidos();
        Pedidos pedidos;
        pedidos = this.findById(id);

        if (pedidos.getStatus().equals(StatusName.ATIVO) || pedidos.getStatus().equals(StatusName.RETIRADO)) {
            if (pedidos.getPeriodo().getInicioVendas().isBefore(LocalDate.now()) || pedidos.getPeriodo().getInicioVendas().isEqual(LocalDate.now()) && pedidos.getPeriodo().getFimVendas().isAfter(LocalDate.now()) || pedidos.getPeriodo().getFimVendas().isEqual(LocalDate.now())) {
                pedidos.setStatus(StatusName.CANCELADO);
                this.update(PedidosDTO.of(pedidos), id);
            }
        } else {
            throw new IllegalArgumentException("Não foi possível cancelar este pedido");
        }
    }

    private Pedidos findById(Long id) {
        return iPedidosRepository.findById(id).get();
    }

    public PedidosDTO update(PedidosDTO pedidosDTO, Long id) {
        Optional<Pedidos> pedidoExistenteOptional = this.iPedidosRepository.findById(id);

        this.validateUp(id);

        if (pedidoExistenteOptional.isPresent()) {
            Pedidos pedidoExiste = pedidoExistenteOptional.get();

            LOGGER.info("Atualizando pedido... id: [{}]", pedidoExiste.getId());
            LOGGER.debug("Payload: {}", pedidosDTO);
            LOGGER.debug("Pedido existente: {}", pedidoExiste);

            new Periodo();
            Periodo periodoCompleto;
            periodoCompleto = periodoService.findByIdPeriodo(pedidosDTO.getIdPeriodo());
            new Produtos();
            Produtos produtoCompleto;
            produtoCompleto = produtosService.findByIdProduto(pedidosDTO.getProduto());
            new Funcionario();
            Funcionario funcionarioCompleto;
            funcionarioCompleto = funcionarioService.findByIdFuncionario(pedidosDTO.getIdFuncionario());
            new Fornecedor();
            Fornecedor fornecedorCompleto;
            fornecedorCompleto = fornecedorService.findByIdFornecedor(pedidosDTO.getIdFornecedor());

            int quantidade = pedidosDTO.getQuantidade();
            double preco = produtoCompleto.getPreco();

            pedidoExiste.setPeriodo(periodoCompleto);
            pedidoExiste.setQuantidade(pedidosDTO.getQuantidade());
            pedidoExiste.setProduto(produtoCompleto);
            pedidoExiste.setFuncionario(funcionarioCompleto);
            pedidoExiste.setStatus(pedidosDTO.getStatus());
            pedidoExiste.setCodigo(pedidosDTO.getCodigo());
            pedidoExiste.setFornecedor(fornecedorCompleto);
            pedidoExiste.setValorTotal(quantidade * preco);

            pedidoExiste = this.iPedidosRepository.save(pedidoExiste);

            return PedidosDTO.of(pedidoExiste);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    private void validateUp(Long id) {
        new Pedidos();
        Pedidos pedidos;
        pedidos = this.findById(id);

        if (pedidos.getStatus().equals(StatusName.ATIVO)) {
            if (pedidos.getPeriodo().getInicioVendas().isBefore(LocalDate.now()) && pedidos.getPeriodo().getFimVendas().isAfter(LocalDate.now())) {
                LOGGER.info("Pedido válido!");
            } else {
                throw new IllegalArgumentException("Modificacao nao foi possivel pois pedido nao atende a todos os requisitos");
            }
        }
    }

    public void retiraPedido(Long id) {
        new Pedidos();
        Pedidos pedidos;
        pedidos = this.findById(id);
        if (pedidos.getPeriodo().getRetiradaPedido().toString().equals(LocalDate.now().plusDays(8).toString())) {
            if (pedidos.getStatus().equals(StatusName.ATIVO)) {
                pedidos.setStatus(StatusName.RETIRADO);
                this.update(PedidosDTO.of(pedidos), id);
            } else {
                throw new IllegalArgumentException("Pedido não está ativo");
            }
        } else {
            throw new IllegalArgumentException("Periodo de retirada invalido");
        }
    }
}