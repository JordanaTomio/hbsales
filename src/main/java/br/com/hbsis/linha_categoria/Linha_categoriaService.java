package br.com.hbsis.linha_categoria;

import br.com.hbsis.categoria.Categoria;
import br.com.hbsis.categoria.CategoriaService;
import br.com.hbsis.categoria.ICategoriaRepository;
import com.google.common.net.HttpHeaders;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Optional;

@Service
public class Linha_categoriaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(Linha_categoriaService.class);

    private final ILinha_categoriaRepository iLinha_categoriaRepository;

    private final CategoriaService categoriaService;

    private final ICategoriaRepository iCategoriaRepository;

    public Linha_categoriaService(ILinha_categoriaRepository iLinha_categoriaRepository, CategoriaService categoriaService, ICategoriaRepository iCategoriaRepository) {
        this.iLinha_categoriaRepository = iLinha_categoriaRepository;
        this.categoriaService = categoriaService;
        this.iCategoriaRepository = iCategoriaRepository;
    }

    public Linha_categoriaDTO save(Linha_categoriaDTO linha_categoriaDTO) {

        this.validate(linha_categoriaDTO);

        LOGGER.info("Salvando linha da categoria");
        LOGGER.debug("Linha da categoria: {}", linha_categoriaDTO);

        Linha_categoria linha = new Linha_categoria();
        linha.setId(linha_categoriaDTO.getId());
        linha.setCodigoLinha(linha_categoriaDTO.getCodigoLinha());
        linha.setNomeLinha(linha_categoriaDTO.getNomeLinha());

        int zero = linha_categoriaDTO.getCodigoLinha().length();
        String codigoZero;

        if (zero < 10) {
            String x = linha_categoriaDTO.getCodigoLinha();
            codigoZero = StringUtils.leftPad(x, 10, "0");
            String codigo = codigoZero.toUpperCase();
            linha.setCodigoLinha(codigo);
        }


        Categoria categoriaCompleta = new Categoria();
        categoriaCompleta = categoriaService.findByIdCategoria(linha_categoriaDTO.getCategoria());

        Long categoriaId = categoriaCompleta.getId();
        linha.setCategoria(categoriaCompleta);

        linha = this.iLinha_categoriaRepository.save(linha);

        return Linha_categoriaDTO.of(linha);
    }
    private void validate(Linha_categoriaDTO linha_categoriaDTO) {
        LOGGER.info("Validando linha da categoria");

        if (linha_categoriaDTO == null) {
            throw new IllegalArgumentException("LinhaCategoriaDTO não deve ser nulo");
        }

        if (StringUtils.isEmpty(linha_categoriaDTO.getNomeLinha())) {
            throw new IllegalArgumentException("Nome da linha não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(linha_categoriaDTO.getCategoria().toString())) {
            throw new IllegalArgumentException("Categoria da linha não deve ser nula/vazia");
        }
    }
    public Linha_categoriaDTO findById(Long id) {
        Optional<Linha_categoria> linhaOptional = this.iLinha_categoriaRepository.findById(id);

        if (linhaOptional.isPresent()) {
            return Linha_categoriaDTO.of(linhaOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public Linha_categoria findByIdLinha(Long id) {
        Optional<Linha_categoria> linhaOptional = this.iLinha_categoriaRepository.findById(id);

        if (linhaOptional.isPresent()) {
            return linhaOptional.get();
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }



    public Linha_categoriaDTO update(Linha_categoriaDTO linha_categoriaDTO, Long id) {
        Optional<Linha_categoria> linhaExistenteOptional = this.iLinha_categoriaRepository.findById(id);

        if (linhaExistenteOptional.isPresent()) {
            Linha_categoria linhaExistente = linhaExistenteOptional.get();

            LOGGER.info("Atualizando linha... id: [{}]", linhaExistente.getId());
            LOGGER.debug("Payload: {}", linha_categoriaDTO);
            LOGGER.debug("Linha existente: {}", linhaExistente);

            linhaExistente.setNomeLinha(linha_categoriaDTO.getNomeLinha());
            linhaExistente.setCodigoLinha(linha_categoriaDTO.getCodigoLinha());
            Categoria categoriaCompleta = new Categoria();
            categoriaCompleta = categoriaService.findByIdCategoria(linha_categoriaDTO.getCategoria());
            linhaExistente.setCategoria(categoriaCompleta);

            linhaExistente = this.iLinha_categoriaRepository.save(linhaExistente);

            return Linha_categoriaDTO.of(linhaExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {

        LOGGER.info("Executando delete para linha de ID: [{}]", id);

        this.iLinha_categoriaRepository.deleteById(id);
    }
    public List<Linha_categoria> findAll(){
        return iLinha_categoriaRepository.findAll();
    }
    public void Export(HttpServletResponse response) throws IOException {

        String nomearquivo  = "exportandoLinhas.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + nomearquivo + "\"");

        PrintWriter out = response.getWriter();

        ICSVWriter cvs = new CSVWriterBuilder(out)
                .withSeparator(';')
                .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build();


        String headerCSV[] = {"id_linha", "codigo_categoria", "nome_categoria", "codigo", "nome_linha"};
        cvs.writeNext(headerCSV);
        for (Linha_categoria linha : findAll()) {

            cvs.writeNext(new String[]{linha.getId().toString(),linha.getCategoria().getCodigo(), linha.getCategoria().getNome(), linha.getCodigoLinha(), linha.getNomeLinha()});
        }

    }
    public void Import() throws IOException {
       // Reader br = Files.newBufferedReader(Paths.get("C:\\Users\\jordana.tomio\\Desktop\\import.csv"));
       // CSVReader cs = new CSVReader(br, ';');
        String arquivo = "C:\\Users\\jordana.tomio\\Downloads\\exportandoLinhas.csv";
        BufferedReader reader = new BufferedReader(new FileReader(arquivo));

        reader.readLine();
        String line = " ";
        String splitBy = ";";

        while ((line = reader.readLine()) != null) {
            Linha_categoriaDTO linhaDTO = new Linha_categoriaDTO();

            String[] categoria = line.split(splitBy);

            linhaDTO.setId(Long.parseLong(categoria[0]));

            String codigoCat = categoria[1];
            String nomeCat = categoria[2];
            Categoria categoriaCompleto = iCategoriaRepository.findByNomeAndCodigo(nomeCat, codigoCat);

            Long idCategoria = categoriaCompleto.getId();
            linhaDTO.setCategoria(idCategoria);

            linhaDTO.setCodigoLinha(categoria[3]);
            linhaDTO.setNomeLinha(categoria[4]);

            save(linhaDTO);
        }
    }

}
