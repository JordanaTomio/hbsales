package br.com.hbsis.produtos;

import br.com.hbsis.categoria.Categoria;
import br.com.hbsis.categoria.CategoriaDTO;
import br.com.hbsis.categoria.CategoriaService;
import br.com.hbsis.categoria.ICategoriaRepository;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.fornecedor.IFornecedorRepository;
import br.com.hbsis.linha_categoria.ILinha_categoriaRepository;
import br.com.hbsis.linha_categoria.Linha_categoria;
import br.com.hbsis.linha_categoria.Linha_categoriaDTO;
import br.com.hbsis.linha_categoria.Linha_categoriaService;
import com.google.common.net.HttpHeaders;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final Linha_categoriaService linha_categoriaService;

    private final IFornecedorRepository iFornecedorRepository;

    private final FornecedorService fornecedorService;

    private final ILinha_categoriaRepository iLinha_categoriaRepository;

    private final ICategoriaRepository iCategoriaRepository;

    private final CategoriaService categoriaService;

    public ProdutosService(IProdutosRepository iProdutosRepository, Linha_categoriaService linha_categoriaService, IFornecedorRepository iFornecedorRepository, FornecedorService fornecedorService, ILinha_categoriaRepository iLinha_categoriaRepository, ICategoriaRepository iCategoriaRepository, CategoriaService categoriaService) {
        this.iProdutosRepository = iProdutosRepository;
        this.linha_categoriaService = linha_categoriaService;
        this.iFornecedorRepository = iFornecedorRepository;
        this.fornecedorService = fornecedorService;
        this.iLinha_categoriaRepository = iLinha_categoriaRepository;
        this.iCategoriaRepository = iCategoriaRepository;
        this.categoriaService = categoriaService;
    }

    public ProdutosDTO save(ProdutosDTO produtosDTO) throws ParseException {

        this.validate(produtosDTO);

        LOGGER.info("Salvando produto");
        LOGGER.debug("Produto: {}", produtosDTO);


        Produtos produtos = new Produtos();
        produtos.setId(produtosDTO.getId());
        produtos.setNome(produtosDTO.getNome());
        produtos.setPesoUnidade(produtosDTO.getPesoUnidade());

        produtos.setUnidadeCaixa(produtosDTO.getUnidadeCaixa());
        produtos.setValidade(produtosDTO.getValidade());
        produtos.setUnidadeMedida(produtosDTO.getUnidadeMedida());

        double preco = produtosDTO.getPreco();
        double pesoUni = produtosDTO.getPesoUnidade();
        produtos.setPesoUnidade(pesoUni);
        produtos.setPreco(preco);

        int zero = produtosDTO.getCodigo().length();
        String codigoZero;
        String codigo;


        String x = produtosDTO.getCodigo();
        codigoZero = StringUtils.leftPad(x, 10, "0");
        codigo = codigoZero.toUpperCase();
        produtos.setCodigo(codigo);

        Linha_categoria linhaCompleta = new Linha_categoria();
        linhaCompleta = linha_categoriaService.findByIdLinha(produtosDTO.getIdLinha());
        produtos.setLinha(linhaCompleta);

        produtos = this.iProdutosRepository.save(produtos);

        return produtosDTO.of(produtos);
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

            Linha_categoria linhaCompleta = new Linha_categoria();
            linhaCompleta = linha_categoriaService.findByIdLinha(produtosDTO.getIdLinha());
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

    public List<Produtos> findAll() {
        return iProdutosRepository.findAll();
    }

    public void Export(HttpServletResponse response) throws IOException, ParseException {

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


        String headerCSV[] = {"id", "nome", "codigo", "preço", "unidade_caixa", "peso_unidade", "validade", "cod_categoria_linha", "nome_categoria_linha", "cod_categoria", "nome_categoria", "cnpj_fornecedor", "razao_fornecedor"};
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


            Long codigoFornecedor = linha.getLinha().getCategoria().getFornecedor().getId();
            Fornecedor fornecedorCompleto = new Fornecedor();
            fornecedorCompleto = fornecedorService.findByIdFornecedor(codigoFornecedor);
            String cnpjA = fornecedorCompleto.getCnpj();
            String cnpjPronto = cnpjA.substring(10);
            String codigoZero;

            String x = linha.getLinha().getCategoria().getCodigo();
            codigoZero = StringUtils.leftPad(x, 3, "0");
            String codigoPronto = "CAT" + cnpjPronto + codigoZero;

            String y = linha.getCodigo();
            String codigoY = StringUtils.leftPad(y, 10, "0");
            String codigo = codigoY.toUpperCase();


            cvs.writeNext(new String[]{linha.getId().toString(), linha.getNome(), codigo, precoFormatado, Float.toString(linha.getUnidadeCaixa()), pesoUnid, data, linha.getLinha().getCodigoLinha(), linha.getLinha().getNomeLinha(), codigoPronto, linha.getLinha().getCategoria().getNome(), cnpjMask, linha.getLinha().getCategoria().getFornecedor().getRazao()});
        }

    }

    public void Import() throws IOException, ParseException {
        String arquivo = "C:\\Users\\jordana.tomio\\Desktop\\exportandoProdutos2";
        BufferedReader reader = new BufferedReader(new FileReader(arquivo));

        reader.readLine();
        String line = " ";
        String splitBy = ";";

        while ((line = reader.readLine()) != null) {
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

            produtosDTO.setPreco(Float.parseFloat(precoFormatado));
            produtosDTO.setUnidadeCaixa(Integer.parseInt(categoria[4]));
            produtosDTO.setPesoUnidade(Float.parseFloat(peso));
            produtosDTO.setValidade(validade);

            produtosDTO.setUnidadeMedida(medida);
            String codigo = categoria[7];
            String nomeCat = categoria[8];
            Linha_categoria linhaCompleta = new Linha_categoria();
            linhaCompleta = iLinha_categoriaRepository.findByCodigoLinha(codigo);
            Long idLinha = linhaCompleta.getId();
            produtosDTO.setIdLinha(idLinha);
            produtosDTO.setCodigo(categoria[2]);

            save(produtosDTO);
        }
    }

    public Optional <Produtos> findByIdProduto(Long id) {
        Optional<Produtos> produtosOptional = this.iProdutosRepository.findById(id);

        if (produtosOptional.isPresent()) {
            return produtosOptional;
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }


    public void ImportFornecedor(Long id) throws IOException, ParseException {
        ProdutosDTO produtoDTO = new ProdutosDTO();
        String arquivo = "C:\\Users\\jordana.tomio\\Desktop\\exportandoProdutos3";
        BufferedReader reader = new BufferedReader(new FileReader(arquivo));

        if (iFornecedorRepository.existsById(id)) {
            reader.readLine();
            String line = " ";
            String splitBy = ";";

            while ((line = reader.readLine()) != null) {
                Linha_categoria linhaCompleta = new Linha_categoria();
                Categoria categoriaCompleta = new Categoria();
                Fornecedor fornecedorCompleto = new Fornecedor();

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

                if (!iCategoriaRepository.existsByCodigo(codigoCategoria)) {
                    CategoriaDTO categoriaDTO = new CategoriaDTO();
                    LOGGER.info("Categoria não existente, criando nova...");

                    String cnpjFornecedor = categoria[11].replaceAll("[^0-9]", "");
                    String razaoFornecedor = categoria[12];
                    fornecedorCompleto = iFornecedorRepository.findByCnpjAndRazao(cnpjFornecedor, razaoFornecedor);
                    Long idFornecedor = fornecedorCompleto.getId();

                    categoriaDTO.setCodigo(codigoCategoria);
                    categoriaDTO.setIdFornecedor(idFornecedor);
                    categoriaDTO.setNome(nomeCategoria);
                    categoriaService.save(categoriaDTO);
                }

                if (!iLinha_categoriaRepository.existsByCodigoLinha(codigoLinha)) {
                    Linha_categoriaDTO linha_categoriaDTO = new Linha_categoriaDTO();
                    LOGGER.info("Linha de Categoria não existente, criando nova...");

                    categoriaCompleta = iCategoriaRepository.findByNomeAndCodigo(nomeCategoria, codigoCategoria);
                    Long idCategoria = categoriaCompleta.getId();

                    linha_categoriaDTO.setCodigoLinha(codigoLinha);
                    linha_categoriaDTO.setCategoria(idCategoria);
                    linha_categoriaDTO.setNomeLinha(nomeLinha);
                    linha_categoriaService.save(linha_categoriaDTO);

                } else {
                    Linha_categoriaDTO linha_categoriaDTO = new Linha_categoriaDTO();
                    linhaCompleta = iLinha_categoriaRepository.findByCodigoLinha(codigoLinha);
                    Long idCategoria = linhaCompleta.getId();
                    String catego = linhaCompleta.getCodigoLinha();

                    linha_categoriaDTO.setCodigoLinha(codigoLinha);
                    linha_categoriaDTO.setCategoria(idCategoria);
                    linha_categoriaDTO.setNomeLinha(nomeLinha);
                    linha_categoriaService.update(linha_categoriaDTO, idCategoria);
                }
                linhaCompleta = iLinha_categoriaRepository.findByCodigoLinha(codigoLinha);
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