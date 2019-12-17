package br.com.hbsis.Pedidos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/pedidos")
public class PedidosRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PedidosRest.class);

    private final PedidosService pedidosService;

    public PedidosRest(PedidosService pedidosService) {
        this.pedidosService = pedidosService;
    }

    @PostMapping
    public PedidosDTO save(@RequestBody PedidosDTO pedidosDTO) throws ParseException {
        LOGGER.info("Recebendo solicitação de persistência de pedidos...");
        LOGGER.debug("Payaload: {}", pedidosDTO);

        return this.pedidosService.save(pedidosDTO);
    }
}
