package br.com.hbsis.periodoVendas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/periodo")
public class PeriodoRest {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(PeriodoRest.class);

    private final PeriodoService periodoService;

    @Autowired
    public PeriodoRest(PeriodoService periodoService) {
        this.periodoService = periodoService;

    }
    @PostMapping
    public PeriodoDTO save(@RequestBody PeriodoDTO periodoDTO) {
        LOGGER.info("Recebendo solicitação de persistência de periodo de vendas...");
        LOGGER.debug("Payaload: {}", periodoDTO);

        return this.periodoService.save(periodoDTO);
    }
    @PutMapping("/{id}")
    public PeriodoDTO udpate(@PathVariable("id") Long id, @RequestBody PeriodoDTO periodoDTO) {
        LOGGER.info("Recebendo Update para periodo de ID: {}", id);
        LOGGER.debug("Payload: {}", periodoDTO);

        return this.periodoService.update(periodoDTO, id);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete para periodo de ID: {}", id);

        this.periodoService.delete(id);
    }
}

