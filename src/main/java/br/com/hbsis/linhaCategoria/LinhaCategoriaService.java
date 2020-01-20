package br.com.hbsis.linhaCategoria;

import br.com.hbsis.categoria.Categoria;
import br.com.hbsis.categoria.CategoriaService;
import com.google.common.net.HttpHeaders;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@Service
public class LinhaCategoriaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LinhaCategoriaService.class);

    private final ILinhaCategoriaRepository iLinhaCategoriaRepository;

    private final CategoriaService categoriaService;

    public LinhaCategoriaService(ILinhaCategoriaRepository iLinhaCategoriaRepository, CategoriaService categoriaService) {
        this.iLinhaCategoriaRepository = iLinhaCategoriaRepository;
        this.categoriaService = categoriaService;
    }

    public LinhaCategoriaDTO save(LinhaCategoriaDTO linhaCategoriaDTO) {

        this.validate(linhaCategoriaDTO);

        LOGGER.info("Salvando linha da categoria");
        LOGGER.debug("Linha da categoria: {}", linhaCategoriaDTO);

        LinhaCategoria linha = new LinhaCategoria();
        linha.setId(linhaCategoriaDTO.getId());
        linha.setCodigoLinha(linhaCategoriaDTO.getCodigoLinha());
        linha.setNomeLinha(linhaCategoriaDTO.getNomeLinha());

        int zero = linhaCategoriaDTO.getCodigoLinha().length();
        String codigoZero;

        if (zero < 10) {
            String x = linhaCategoriaDTO.getCodigoLinha();
            codigoZero = StringUtils.leftPad(x, 10, "0");
            String codigo = codigoZero.toUpperCase();
            linha.setCodigoLinha(codigo);
        }

        new Categoria();
        Categoria categoriaCompleta;
        categoriaCompleta = categoriaService.findByIdCategoria(linhaCategoriaDTO.getCategoria());
        linha.setCategoria(categoriaCompleta);

        linha = this.iLinhaCategoriaRepository.save(linha);

        linhaCategoriaDTO.setId(linha.getId());

        return linhaCategoriaDTO;
    }

    private void validate(LinhaCategoriaDTO linhaCategoriaDTO) {
        LOGGER.info("Validando linha da categoria");

        if (linhaCategoriaDTO == null) {
            throw new IllegalArgumentException("LinhaCategoriaDTO não deve ser nulo");
        }
        if (linhaCategoriaDTO.getCategoria() == null) {
            throw new IllegalArgumentException("Categoria não deve ser nulo");
        }
        if (StringUtils.isEmpty(linhaCategoriaDTO.getNomeLinha())) {
            throw new IllegalArgumentException("Nome da linha não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(linhaCategoriaDTO.getCategoria().toString())) {
            throw new IllegalArgumentException("Categoria da linha não deve ser nula/vazia");
        }
        if (StringUtils.isEmpty(linhaCategoriaDTO.getCodigoLinha())) {
            throw new IllegalArgumentException("Codigo da linha não deve ser nula/vazia");
        }

    }

    public LinhaCategoria findByIdLinha(Long id) {
        Optional<LinhaCategoria> linhaOptional = this.iLinhaCategoriaRepository.findById(id);

        if (linhaOptional.isPresent()) {
            return linhaOptional.get();
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public LinhaCategoriaDTO update(LinhaCategoriaDTO linha_categoriaDTO, Long id) {
        Optional<LinhaCategoria> linhaExistenteOptional = this.iLinhaCategoriaRepository.findById(id);

        if (linhaExistenteOptional.isPresent()) {
            new Categoria();
            Categoria categoriaCompleta;
            LinhaCategoria linhaExistente = linhaExistenteOptional.get();

            LOGGER.info("Atualizando linha... id: [{}]", linhaExistente.getId());
            LOGGER.debug("Payload: {}", linha_categoriaDTO);
            LOGGER.debug("Linha existente: {}", linhaExistente);

            String x = linha_categoriaDTO.getCodigoLinha();
            String codigoZero = StringUtils.leftPad(x, 10, "0");

            linhaExistente.setNomeLinha(linha_categoriaDTO.getNomeLinha());
            linhaExistente.setCodigoLinha(codigoZero);
            categoriaCompleta = categoriaService.findByIdCategoria(linha_categoriaDTO.getCategoria());
            linhaExistente.setCategoria(categoriaCompleta);

            linhaExistente = this.iLinhaCategoriaRepository.save(linhaExistente);

            return LinhaCategoriaDTO.of(linhaExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {

        LOGGER.info("Executando delete para linha de ID: [{}]", id);

        this.iLinhaCategoriaRepository.deleteById(id);
    }

    public List<LinhaCategoria> findAll() {
        return iLinhaCategoriaRepository.findAll();
    }

    public void export(HttpServletResponse response) throws IOException {

        String nomearquivo = "exportandoLinhas.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + nomearquivo + "\"");

        PrintWriter out = response.getWriter();

        ICSVWriter cvs = new CSVWriterBuilder(out)
                .withSeparator(';')
                .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build();

        String[] headerCSV = {"id_linha", "codigo_categoria", "nome_categoria", "codigo", "nome_linha"};
        cvs.writeNext(headerCSV);
        for (LinhaCategoria linha : findAll()) {
            String codigoLinha = "'" + linha.getCodigoLinha();
            cvs.writeNext(new String[]{linha.getId().toString(), linha.getCategoria().getCodigo(), linha.getCategoria().getNome(), codigoLinha, linha.getNomeLinha()});
        }
    }

    public void importCategoria(MultipartFile file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

        reader.readLine();
        String line;
        String splitBy = ";";

        while ((line = reader.readLine()) != null) {
            LinhaCategoriaDTO linhaDTO = new LinhaCategoriaDTO();

            String[] categoria = line.split(splitBy);

            linhaDTO.setId(Long.parseLong(categoria[0]));
            String codigoCat = categoria[1];
            String nomeCat = categoria[2].replaceAll("'", "");
            String codigoLinha = categoria[3].replaceAll("'", "");

            Categoria categoriaCompleto = categoriaService.findCategoriaNomeECodigo(nomeCat, codigoCat);
            Long idCategoria = categoriaCompleto.getId();
            linhaDTO.setCategoria(idCategoria);

            linhaDTO.setCodigoLinha(codigoLinha);
            linhaDTO.setNomeLinha(categoria[4]);

            save(linhaDTO);
        }
    }

    public boolean existsByLinhaCodigo(String codigoLinha) {
        iLinhaCategoriaRepository.existsByCodigoLinha(codigoLinha);
        return iLinhaCategoriaRepository.existsByCodigoLinha(codigoLinha);
    }

    public LinhaCategoria findByCodigoLinhaCategoria(String codigoLinha) {
        return iLinhaCategoriaRepository.findByCodigoLinha(codigoLinha);
    }
}
