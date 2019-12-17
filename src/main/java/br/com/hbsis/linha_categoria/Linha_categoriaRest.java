package br.com.hbsis.linha_categoria;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/linhas")
public class Linha_categoriaRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(Linha_categoriaRest.class);

    private final Linha_categoriaService linha_categoriaService;

    @Autowired
    public Linha_categoriaRest(Linha_categoriaService linha_categoriaService) {
        this.linha_categoriaService = linha_categoriaService;
    }
    @PostMapping
    public Linha_categoriaDTO save(@RequestBody Linha_categoriaDTO linha_categoriaDTO) {
        LOGGER.info("Recebendo solicitação de persistência de linha de categoria...");
        LOGGER.debug("Payaload: {}", linha_categoriaDTO);

        return this.linha_categoriaService.save(linha_categoriaDTO);
    }
    @PostMapping("/import-csv")
    public void importCSV() throws Exception {
        LOGGER.info("Recebendo solicitação de persistência de importação...");

        this.linha_categoriaService.Import();

        LOGGER.info("Importado com sucesso...");

    }

    @GetMapping("/export-csv")
    public void exportCSV(HttpServletResponse response, @RequestBody Linha_categoriaService linha_categoriaService) throws Exception {
        LOGGER.info("Recebendo solicitação de persistência de exportação...");

        this.linha_categoriaService.Export(response);

        LOGGER.info("Exportado com sucesso...");
    }


    @PutMapping("/{id}")
    public Linha_categoriaDTO udpate(@PathVariable("id") Long id, @RequestBody Linha_categoriaDTO linha_categoriaDTO) {
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
