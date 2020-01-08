package br.com.hbsis.linhaCategoria;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/linhas")
public class LinhaCategoriaRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(LinhaCategoriaRest.class);
    private final LinhaCategoriaService linha_categoriaService;

    @Autowired
    public LinhaCategoriaRest(LinhaCategoriaService linha_categoriaService) {
        this.linha_categoriaService = linha_categoriaService;
    }
    @PostMapping
    public LinhaCategoriaDTO save(@RequestBody LinhaCategoriaDTO linha_categoriaDTO) {
        LOGGER.info("Recebendo solicitação de persistência de linha de categoria...");
        LOGGER.debug("Payaload: {}", linha_categoriaDTO);

        return this.linha_categoriaService.save(linha_categoriaDTO);
    }

    @PostMapping("/import-csv")
    public void importCSV(@RequestParam("file") MultipartFile file) throws Exception {
        LOGGER.info("Recebendo solicitação de persistência de importação...");

        this.linha_categoriaService.importCategoria(file);

        LOGGER.info("Importado com sucesso...");
    }

    @GetMapping("/export-csv")
    public void exportCSV(HttpServletResponse response, @RequestBody LinhaCategoriaService linha_categoriaService) throws Exception {
        LOGGER.info("Recebendo solicitação de persistência de exportação...");

        this.linha_categoriaService.export(response);

        LOGGER.info("Exportado com sucesso...");
    }

    @PutMapping("/{id}")
    public LinhaCategoriaDTO udpate(@PathVariable("id") Long id, @RequestBody LinhaCategoriaDTO linha_categoriaDTO) {
        LOGGER.info("Recebendo Update para linha de categoria de ID: {}", id);
        LOGGER.debug("Payload: {}", linha_categoriaDTO);

        return this.linha_categoriaService.update(linha_categoriaDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete para linha de categoria de ID: {}", id);

        this.linha_categoriaService.delete(id);
    }
}
