package br.com.hbsis.pedidos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/pedidos")
public class PedidosRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PedidosRest.class);
    private final PedidosService pedidosService;

    @Autowired
    public PedidosRest(PedidosService pedidosService) {
        this.pedidosService = pedidosService;
    }

    @PostMapping
    public PedidosDTO save(@RequestBody PedidosDTO pedidosDTO) {
        LOGGER.info("Recebendo solicitação de persistência de pedidos...");
        LOGGER.debug("Payaload: {}", pedidosDTO);

        return this.pedidosService.save(pedidosDTO);
    }
    @GetMapping("/export-periodo/{id}")
    public void exportCSVPeriodo(HttpServletResponse response, @PathVariable("id") Long id) throws Exception {
        LOGGER.info("Recebendo solicitação de persistência de exportação...");

        this.pedidosService.exportPeriodo(response, id);

        LOGGER.info("Exportado com sucesso...");
    }
    @GetMapping("/export-funcionario/{id}")
    public void exportCSVFuncionario(HttpServletResponse response, @PathVariable("id") Long id) throws Exception {
        LOGGER.info("Recebendo solicitação de persistência de exportação...");

        this.pedidosService.exportFuncionario(response, id);

        LOGGER.info("Exportado com sucesso...");
    }
    @GetMapping("/visualiza-pedido/{id}")
    public void visualizaPedido(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo solicitação de persistência de visualização...");
        LOGGER.info("|--------------------------------------------------------------------------------------------------------|");

        this.pedidosService.visualizaPedidos(id);

        LOGGER.info("|--------------------------------------------------------------------------------------------------------|");
        LOGGER.info("Visualização...");
    }
    @PostMapping("/cancela-pedido/{id}")
    public void cancelaPedido(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo solicitação de persistência de cancelamento de pedido...");

        this.pedidosService.cancelaPedido(id);

        LOGGER.info("Pedido cancelado com sucesso...");
    }
    @PutMapping("/edita-pedido/{id}")
    public void editaPedido(@PathVariable("id") Long id,  @RequestBody PedidosDTO pedidosDTO) {
        LOGGER.info("Recebendo solicitação de persistência de modificacao de pedido...");

        this.pedidosService.update(pedidosDTO, id);

        LOGGER.info("Pedido modificado com sucesso...");
    }
    @GetMapping("/retira-pedido/{id}")
    public void retiraPedido(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo solicitação de persistência de retirada de pedido...");

        this.pedidosService.retiraPedido(id);

        LOGGER.info("Pedido retirado com sucesso...");
    }
}
