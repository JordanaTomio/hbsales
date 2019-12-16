package br.com.hbsis.categoria;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.fornecedor.IFornecedorRepository;
import com.google.common.net.HttpHeaders;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.MaskFormatter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;


@Service
public class CategoriaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaService.class);

    private final IFornecedorRepository iFornecedorRepository;
    private final FornecedorService fornecedorService;
    private final ICategoriaRepository iCategoriaRepository;


    public CategoriaService(IFornecedorRepository iFornecedorRepository, FornecedorService fornecedorService, ICategoriaRepository iCategoriaRepository) {
        this.iFornecedorRepository = iFornecedorRepository;
        this.fornecedorService = fornecedorService;
        this.iCategoriaRepository = iCategoriaRepository;

    }

    public CategoriaDTO save(CategoriaDTO categoriaDTO) {

        this.validate(categoriaDTO);

        LOGGER.info("Salvando categoria");
        LOGGER.debug("Categoria: {}", categoriaDTO);

        Categoria categoria = new Categoria();

        categoria.setId(categoriaDTO.getId());
        categoria.setNome(categoriaDTO.getNome());
        categoria.setCodigo(categoriaDTO.getCodigo());

        Fornecedor fornecedorCompleto = new Fornecedor();
        fornecedorCompleto = fornecedorService.findByIdFornecedor(categoriaDTO.getIdFornecedor());
        categoria.setFornecedor(fornecedorCompleto);
        String cnpj = fornecedorCompleto.getCnpj();
        String cnpjPronto = cnpj.substring(10);

        int zero = categoriaDTO.getCodigo().length();
        String codigoZero;

        if (zero < 3) {
            String x = categoriaDTO.getCodigo();
            codigoZero = StringUtils.leftPad(x, 3, "0");
            String codigo = "CAT" + cnpjPronto + codigoZero;
            categoria.setCodigo(codigo);
        }
        categoria = this.iCategoriaRepository.save(categoria);

        return CategoriaDTO.of(categoria);
    }

    private void validate(CategoriaDTO categoriaDTO) {
        LOGGER.info("Validando categoria");

        if (categoriaDTO == null) {
            throw new IllegalArgumentException("CategoriaDTO não deve ser nulo");
        }

        if (StringUtils.isEmpty(categoriaDTO.getNome())) {
            throw new IllegalArgumentException("Nome da categoria não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(categoriaDTO.getIdFornecedor().toString())) {
            throw new IllegalArgumentException("Fornecedor não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(categoriaDTO.getCodigo())) {
            throw new IllegalArgumentException("Codigo não deve ser nulo/vazio");
        }

    }

    public CategoriaDTO findById(Long id) {
        Optional<Categoria> categoriaOptional = this.iCategoriaRepository.findById(id);

        if (categoriaOptional.isPresent()) {
            return CategoriaDTO.of(categoriaOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public Categoria findByIdCategoria(Long id) {
        Optional<Categoria> categoriaOptional = this.iCategoriaRepository.findById(id);

        if (categoriaOptional.isPresent()) {
            return categoriaOptional.get();
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }


    public CategoriaDTO update(CategoriaDTO categoriaDTO, Long id) {
        Optional<Categoria> categoriaExistenteOptional = this.iCategoriaRepository.findById(id);

        if (categoriaExistenteOptional.isPresent()) {
            Categoria categoriaExistente = categoriaExistenteOptional.get();

            LOGGER.info("Atualizando categoria... id: [{}]", categoriaExistente.getId());
            LOGGER.debug("Payload: {}", categoriaDTO);
            LOGGER.debug("Categoria existente: {}", categoriaExistente);

            categoriaDTO.setNome(categoriaDTO.getNome());
            categoriaDTO.setId(categoriaDTO.getId());
            categoriaDTO.setCodigo(categoriaDTO.getCodigo());
            categoriaDTO.setIdFornecedor(categoriaDTO.getIdFornecedor());

            categoriaExistente = this.iCategoriaRepository.save(categoriaExistente);

            return CategoriaDTO.of(categoriaExistente);
        }


        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {

        LOGGER.info("Executando delete para categoria de ID: [{}]", id);

        this.iCategoriaRepository.deleteById(id);
    }

    public List<Categoria> findAll() {
        return iCategoriaRepository.findAll();
    }

    public void Export(HttpServletResponse response) throws IOException, ParseException {

        String nomearquivo = "exportando.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + nomearquivo + "\"");

        PrintWriter out = response.getWriter();

        ICSVWriter cvs = new CSVWriterBuilder(out)
                .withSeparator(';')
                .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build();


        String headerCSV[] = {"id_categoria", "categoria", "codigo", "razao_fornecedor", "cnpj_fornecedor"};
        cvs.writeNext(headerCSV);
        for (Categoria linha : findAll()) {
            String cnpj = linha.getFornecedor().getCnpj();

            MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
            mask.setValueContainsLiteralCharacters(false);
            String cnpjMask = mask.valueToString(cnpj);

            cvs.writeNext(new String[]{linha.getId().toString(), linha.getNome(),linha.getCodigo(),linha.getFornecedor().getRazao(), cnpjMask});
        }

    }

    public void Import() throws IOException {
        //Reader br = Files.newBufferedReader(Paths.get("C:\\Users\\jordana.tomio\\Downloads\\exportando.csv"));
        String arquivo = "C:\\Users\\jordana.tomio\\Downloads\\exportando.csv";
        BufferedReader reader = new BufferedReader(new FileReader(arquivo));
       // CSVReader cs = new CSVReader(br, ';');

        reader.readLine();
        String line = " ";
        String splitBy = ";";

        while ((line = reader.readLine()) != null) {
            String[] categoria = line.split(splitBy);

            CategoriaDTO categoriaDTO = new CategoriaDTO();
            FornecedorDTO fornecedorDTO = new FornecedorDTO();

            categoriaDTO.setId(Long.parseLong(categoria[0]));
            categoriaDTO.setNome(categoria[1]);
            categoriaDTO.setCodigo(categoria[2]);

            String razao = categoria[3];
            String cnpj = categoria[4];
            cnpj = cnpj.replaceAll("[^0-9]", "");
            Fornecedor fornecedorCompleto = iFornecedorRepository.findByCnpjAndRazao(cnpj, razao);
            Long idFornecedor = fornecedorCompleto.getId();
            categoriaDTO.setIdFornecedor(idFornecedor);


            save(categoriaDTO);
        }
    }
}
