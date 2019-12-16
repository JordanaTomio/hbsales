package br.com.hbsis.produtos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;


@RestController
@RequestMapping("/produtos")
public class ProdutosRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutosRest.class);

    private final ProdutosService produtosService;

    public ProdutosRest(ProdutosService produtosService) {
        this.produtosService = produtosService;
    }

    @PostMapping
    public ProdutosDTO save(@RequestBody ProdutosDTO produtosDTO) throws ParseException {
        LOGGER.info("Recebendo solicitação de persistência de produto...");
        LOGGER.debug("Payaload: {}", produtosDTO);

        return this.produtosService.save(produtosDTO);
    }
    @PostMapping("/import-csv")
    public void importCSV() throws Exception {
        LOGGER.info("Recebendo solicitação de persistência de importação...");

        this.produtosService.Import();

        LOGGER.info("Importado com sucesso...");

    }
    @PostMapping("/import-fornecedor/{id}")
    public void importFornecedor(@PathVariable("id") Long id) throws Exception {
        LOGGER.info("Recebendo solicitação de persistência de importação por Fornecedor...");

        this.produtosService.ImportFornecedor(id);

        LOGGER.info("Importado com sucesso...");

    }

    @GetMapping("/export-csv")
    public void exportCSV(HttpServletResponse response, @RequestBody ProdutosService produtosService) throws Exception {
        LOGGER.info("Recebendo solicitação de persistência de exportação...");

        this.produtosService.Export(response);

        LOGGER.info("Exportado com sucesso...");
    }
    @PutMapping("/{id}")
    public ProdutosDTO udpate(@PathVariable("id") Long id, @RequestBody ProdutosDTO produtosDTO) {
        LOGGER.info("Recebendo Update para produto de ID: {}", id);
        LOGGER.debug("Payload: {}", produtosDTO);

        return this.produtosService.update(produtosDTO, id);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete para produto de ID: {}", id);

        this.produtosService.delete(id);
    }
}


