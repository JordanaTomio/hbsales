package br.com.hbsis.produtos;

import br.com.hbsis.categoria.Categoria;
import br.com.hbsis.categoria.CategoriaDTO;
import br.com.hbsis.categoria.CategoriaService;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.linhaCategoria.LinhaCategoria;
import br.com.hbsis.linhaCategoria.LinhaCategoriaDTO;
import br.com.hbsis.linhaCategoria.LinhaCategoriaService;
import com.google.common.net.HttpHeaders;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.MaskFormatter;
import java.io.*;
import java.text.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutosService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutosService.class);
    private final IProdutosRepository iProdutosRepository;
    private final LinhaCategoriaService linhaCategoriaService;
    private final FornecedorService fornecedorService;
    private final CategoriaService categoriaService;

    public ProdutosService(IProdutosRepository iProdutosRepository, LinhaCategoriaService linhaCategoriaService, FornecedorService fornecedorService, CategoriaService categoriaService) {
        this.iProdutosRepository = iProdutosRepository;
        this.linhaCategoriaService = linhaCategoriaService;
        this.fornecedorService = fornecedorService;
        this.categoriaService = categoriaService;
    }

    public ProdutosDTO save(ProdutosDTO produtosDTO) {
        new LinhaCategoria();
        LinhaCategoria linhaCompleta;
        this.validate(produtosDTO);

        LOGGER.info("Salvando produto");
        LOGGER.debug("Produto: {}", produtosDTO);

        double preco = produtosDTO.getPreco();
        double pesoUni = produtosDTO.getPesoUnidade();
        String x = produtosDTO.getCodigo();
        String codigoZero = StringUtils.leftPad(x, 10, "0");
        String codigo = codigoZero.toUpperCase();

        Produtos produtos = new Produtos();
        produtos.setId(produtosDTO.getId());
        produtos.setNome(produtosDTO.getNome());
        produtos.setPesoUnidade(produtosDTO.getPesoUnidade());
        produtos.setUnidadeCaixa(produtosDTO.getUnidadeCaixa());
        produtos.setValidade(produtosDTO.getValidade());
        produtos.setUnidadeMedida(produtosDTO.getUnidadeMedida());
        produtos.setPesoUnidade(pesoUni);
        produtos.setPreco(preco);
        produtos.setCodigo(codigo);

        linhaCompleta = linhaCategoriaService.findByIdLinha(produtosDTO.getIdLinha());
        produtos.setLinha(linhaCompleta);
        produtos = this.iProdutosRepository.save(produtos);

        return ProdutosDTO.of(produtos);
    }

    private void validate(ProdutosDTO produtosDTO) {
        LOGGER.info("Validando produtos");

        if (produtosDTO == null) {
            throw new IllegalArgumentException("ProdutosDTO não deve ser nulo");
        }

        if (StringUtils.isEmpty(produtosDTO.getNome())) {
            throw new IllegalArgumentException("Nome do produto não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(produtosDTO.getIdLinha().toString())) {
            throw new IllegalArgumentException("Linha de categoria não deve ser nula/vazia");
        }

        if (!(produtosDTO.getUnidadeMedida().contains("kg") || produtosDTO.getUnidadeMedida().contains("mg") || produtosDTO.getUnidadeMedida().contains("g"))) {
            throw new IllegalArgumentException("Unidade de medida inválida");
        }
    }

    public ProdutosDTO update(ProdutosDTO produtosDTO, Long id) {
        Optional<Produtos> produtoExistenteOptional = this.iProdutosRepository.findById(id);

        if (produtoExistenteOptional.isPresent()) {
            Produtos produtoExistente = produtoExistenteOptional.get();

            LOGGER.info("Atualizando produto... id: [{}]", produtoExistente.getId());
            LOGGER.debug("Payload: {}", produtosDTO);
            LOGGER.debug("Produto existente: {}", produtoExistente);

            produtoExistente.setNome(produtosDTO.getNome());
            produtoExistente.setValidade(produtosDTO.getValidade());
            produtoExistente.setUnidadeCaixa(produtosDTO.getUnidadeCaixa());
            produtoExistente.setPreco(produtosDTO.getPreco());
            produtoExistente.setPesoUnidade(produtosDTO.getPesoUnidade());
            produtoExistente.setCodigo(produtosDTO.getCodigo());
            produtoExistente.setUnidadeMedida(produtosDTO.getUnidadeMedida());

            new LinhaCategoria();
            LinhaCategoria linhaCompleta;
            linhaCompleta = linhaCategoriaService.findByIdLinha(produtosDTO.getIdLinha());
            produtoExistente.setLinha(linhaCompleta);

            produtoExistente = this.iProdutosRepository.save(produtoExistente);

            return ProdutosDTO.of(produtoExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {

        LOGGER.info("Executando delete para produto de ID: [{}]", id);

        this.iProdutosRepository.deleteById(id);
    }

    private List<Produtos> findAll() {
        return iProdutosRepository.findAll();
    }

    public void exportProdutos(HttpServletResponse response) throws IOException, ParseException {
        String nomearquivo = "exportandoProdutos.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + nomearquivo + "\"");

        PrintWriter out = response.getWriter();
        ICSVWriter cvs = new CSVWriterBuilder(out)
                .withSeparator(';')
                .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build();

        String[] headerCSV = {"id", "nome", "codigo", "preço", "unidade_caixa", "peso_unidade", "validade", "cod_categoria_linha", "nome_categoria_linha", "cod_categoria", "nome_categoria", "cnpj_fornecedor", "razao_fornecedor"};
        cvs.writeNext(headerCSV);
        for (Produtos linha : findAll()) {

            Double precoForm = linha.getPreco();
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            formatter.setMaximumFractionDigits(2);
            String precoFormatado = formatter.format(precoForm);

            String peso = Double.toString(linha.getPesoUnidade());
            String unidade = linha.getUnidadeMedida();
            String pesoUnid = peso + unidade;

            LocalDate validade = linha.getValidade();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String data = validade.format(fmt);

            String cnpj = linha.getLinha().getCategoria().getFornecedor().getCnpj();
            MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
            mask.setValueContainsLiteralCharacters(false);
            String cnpjMask = mask.valueToString(cnpj);

            String codLinhaCategoria = linha.getLinha().getCodigoLinha();
            String codLinhaCategoriaForm = StringUtils.leftPad(codLinhaCategoria, 10, "0");
            String codLinhaFormatado = "'" + codLinhaCategoriaForm;
            String y = linha.getCodigo();
            String codigoY = StringUtils.leftPad(y, 10, "0");
            String codigoX = "'" + codigoY;

            String codigoLinhaCat = linha.getLinha().getCategoria().getCodigo();

            cvs.writeNext(new String[]{linha.getId().toString(), linha.getNome(), codigoX, precoFormatado, Float.toString(linha.getUnidadeCaixa()), pesoUnid, data, codLinhaFormatado, linha.getLinha().getNomeLinha(), codigoLinhaCat, linha.getLinha().getCategoria().getNome(), cnpjMask, linha.getLinha().getCategoria().getFornecedor().getRazao()});
        }
    }

    public void importProdutos(MultipartFile file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

        reader.readLine();
        String line;
        String splitBy = ";";

        while ((line = reader.readLine()) != null) {
            String[] categoria = line.split(splitBy);
            String peso = categoria[5].replaceAll("[^0-9.]", "");
            String pesoForm = peso.substring(0, peso.length() - 1);
            String medida = categoria[5].replaceAll("[0-9]", "");
            String medidaForm = medida.replace(".", "");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dateString = categoria[6];
            LocalDate validade = LocalDate.parse(dateString, dateTimeFormatter);
            String preco = categoria[3];
            String precoForm = preco.substring(3);
            String precoFormatado = precoForm.replaceAll(",", ".");
            String codigo = categoria[7];
            String codigoForm = codigo.replace("'", "");
            String unidadeCaixa = categoria[4].replace("0", "");
            String uniCaixa = unidadeCaixa.replace(".", "");

            ProdutosDTO produtosDTO = new ProdutosDTO();
            produtosDTO.setId(Long.parseLong(categoria[0]));
            produtosDTO.setNome(categoria[1]);
            produtosDTO.setPreco(Float.parseFloat(precoFormatado));
            produtosDTO.setUnidadeCaixa(Integer.parseInt(uniCaixa));
            produtosDTO.setPesoUnidade(Float.parseFloat(pesoForm));
            produtosDTO.setValidade(validade);
            produtosDTO.setUnidadeMedida(medidaForm);

            LinhaCategoria linhaCompleta = new LinhaCategoria();
            linhaCompleta = linhaCategoriaService.findByCodigoLinhaCategoria(codigoForm);
            Long idLinha = linhaCompleta.getId();

            produtosDTO.setIdLinha(idLinha);
            produtosDTO.setCodigo(categoria[2].replace("'", ""));

            save(produtosDTO);
        }
    }

    public Produtos findByIdProduto(Long id) {
        Optional<Produtos> produtosOptional = this.iProdutosRepository.findById(id);

        if (produtosOptional.isPresent()) {
            return produtosOptional.get();
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void importFornecedor(Long id, MultipartFile file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

        if (fornecedorService.existsByIdFornecedor(id)) {
            reader.readLine();
            String line;
            String splitBy = ";";

            while ((line = reader.readLine()) != null) {
                new LinhaCategoria();
                LinhaCategoria linhaCompleta;
                new Categoria();
                Categoria categoriaCompleta;
                new Fornecedor();
                Fornecedor fornecedorCompleto;

                String[] categoria = line.split(splitBy);
                String peso = categoria[5].replaceAll("[^0-9.]", "");
                String medida = categoria[5].replaceAll("[0-9]", "");

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String dateString = categoria[6];
                LocalDate validade = LocalDate.parse(dateString, dateTimeFormatter);

                String preco = categoria[3];
                String precoForm = preco.substring(3);
                String precoFormatado = precoForm.replaceAll(",", ".");

                ProdutosDTO produtosDTO = new ProdutosDTO();
                produtosDTO.setId(Long.parseLong(categoria[0]));
                produtosDTO.setNome(categoria[1]);
                produtosDTO.setCodigo(categoria[2]);
                produtosDTO.setPreco(Float.parseFloat(precoFormatado));
                produtosDTO.setUnidadeCaixa(Integer.parseInt(categoria[4]));
                produtosDTO.setPesoUnidade(Float.parseFloat(peso));
                produtosDTO.setValidade(validade);
                produtosDTO.setUnidadeMedida(medida);

                String codigoLinha = categoria[7];
                String nomeLinha = categoria[8];
                String codigoCategoria = categoria[9];
                String nomeCategoria = categoria[10];

                if (!categoriaService.existsByIdCodigo(codigoCategoria)) {
                    CategoriaDTO categoriaDTO = new CategoriaDTO();
                    LOGGER.info("Categoria não existente, criando nova...");

                    String cnpjFornecedor = categoria[11].replaceAll("[^0-9]", "");
                    String razaoFornecedor = categoria[12];
                    fornecedorCompleto = fornecedorService.findFornecedor(cnpjFornecedor, razaoFornecedor);
                    Long idFornecedor = fornecedorCompleto.getId();

                    categoriaDTO.setCodigo(codigoCategoria);
                    categoriaDTO.setIdFornecedor(idFornecedor);
                    categoriaDTO.setNome(nomeCategoria);
                    categoriaService.save(categoriaDTO);
                } if (!linhaCategoriaService.existsByLinhaCodigo(codigoLinha)) {
                    LinhaCategoriaDTO linha_categoriaDTO = new LinhaCategoriaDTO();
                    LOGGER.info("Linha de Categoria não existente, criando nova...");

                    categoriaCompleta = categoriaService.findCategoriaNomeECodigo(nomeCategoria, codigoCategoria);
                    Long idCategoria = categoriaCompleta.getId();

                    linha_categoriaDTO.setCodigoLinha(codigoLinha);
                    linha_categoriaDTO.setCategoria(idCategoria);
                    linha_categoriaDTO.setNomeLinha(nomeLinha);
                    linhaCategoriaService.save(linha_categoriaDTO);

                } else {
                    LinhaCategoriaDTO linha_categoriaDTO = new LinhaCategoriaDTO();
                    linhaCompleta = linhaCategoriaService.findByCodigoLinhaCategoria(codigoLinha);
                    Long idCategoria = linhaCompleta.getId();

                    linha_categoriaDTO.setCodigoLinha(codigoLinha);
                    linha_categoriaDTO.setCategoria(idCategoria);
                    linha_categoriaDTO.setNomeLinha(nomeLinha);
                    linhaCategoriaService.update(linha_categoriaDTO, idCategoria);
                }
                linhaCompleta = linhaCategoriaService.findByCodigoLinhaCategoria(codigoLinha);
                Long idLinha = linhaCompleta.getId();
                produtosDTO.setIdLinha(idLinha);

                if (iProdutosRepository.existsById(id = produtosDTO.getId())) {
                    update(produtosDTO, id);

                } else {
                    save(produtosDTO);
                }
            }
        }
    }
}