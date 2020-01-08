package br.com.hbsis.mail;

import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.funcionario.FuncionarioService;
import br.com.hbsis.pedidos.PedidosDTO;
import br.com.hbsis.periodoVendas.Periodo;
import br.com.hbsis.periodoVendas.PeriodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class Mail {
    private static final Logger LOGGER = LoggerFactory.getLogger(FuncionarioService.class);

    @Autowired
    private final JavaMailSender mailSender;
    private final FuncionarioService funcionarioService;
    private final PeriodoService periodoService;

    public Mail(JavaMailSender mailSender, FuncionarioService funcionarioService, PeriodoService periodoService) {
        this.mailSender = mailSender;
        this.funcionarioService = funcionarioService;
        this.periodoService = periodoService;
    }

    public void mailSave(PedidosDTO pedidos) {
        new Funcionario();
        Funcionario funcionarioCompleto;
        new Periodo();
        Periodo periodoCompleto;

        SimpleMailMessage message = new SimpleMailMessage();
        funcionarioCompleto = funcionarioService.findByIdFuncionario(pedidos.getIdFuncionario());
        periodoCompleto = periodoService.findByIdPeriodo(pedidos.getIdPeriodo());

        message.setSubject("Mensagem de confirmação de retirada de pedido.");
        message.setText("Olá " + funcionarioCompleto.getNome() + ", seu pedido de código " + pedidos.getCodigo() + " foi finalizado. A data de retirada está prevista para " + periodoCompleto.getRetiradaPedido() + " .");
        message.setTo(funcionarioCompleto.getEmail());
        message.setFrom("dannatomio@gmail.com");

        try {
            mailSender.send(message);
            LOGGER.info("Pedido enviado!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
