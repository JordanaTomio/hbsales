package br.com.hbsis.categoria;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorService;
import com.google.common.net.HttpHeaders;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;


@RestController
@RequestMapping("/categorias")
public class CategoriaRest {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(CategoriaRest.class);

    private final CategoriaService categoriaService;



    @Autowired
    public CategoriaRest(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;

    }
    @PostMapping
    public CategoriaDTO save(@RequestBody CategoriaDTO categoriaDTO) {
        LOGGER.info("Recebendo solicitação de persistência de categoria...");
        LOGGER.debug("Payaload: {}", categoriaDTO);

        return this.categoriaService.save(categoriaDTO);
    }


    @PostMapping("/import-csv")
    public void importCSV(@RequestParam("file") MultipartFile file) throws Exception {
        LOGGER.info("Recebendo solicitação de persistência de importação...");

        this.categoriaService.Import(file);

        LOGGER.info("Importado com sucesso...");


    }


    @GetMapping("/export-csv")
    public void exportCSV(HttpServletResponse response, @RequestBody CategoriaService categoriaService) throws Exception {
        LOGGER.info("Recebendo solicitação de persistência de exportação...");

        this.categoriaService.Export(response);

        LOGGER.info("Exportado com sucesso...");
    }


    @PutMapping("/{id}")
    public CategoriaDTO udpate(@PathVariable("id") Long id, @RequestBody CategoriaDTO categoriaDTO) {
        LOGGER.info("Recebendo Update para categoria de ID: {}", id);
        LOGGER.debug("Payload: {}", categoriaDTO);

        return this.categoriaService.update(categoriaDTO, id);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete para categoria de ID: {}", id);

        this.categoriaService.delete(id);
    }
}
