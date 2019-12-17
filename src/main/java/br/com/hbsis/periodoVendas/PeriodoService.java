package br.com.hbsis.periodoVendas;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.fornecedor.IFornecedorRepository;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PeriodoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PeriodoService.class);

    private final IFornecedorRepository iFornecedorRepository;
    private final FornecedorService fornecedorService;
    private final IPeriodoRepository iPeriodoRepository;

    public PeriodoService(IFornecedorRepository iFornecedorRepository, FornecedorService fornecedorService, IPeriodoRepository iPeriodoRepository) {
        this.iFornecedorRepository = iFornecedorRepository;
        this.fornecedorService = fornecedorService;
        this.iPeriodoRepository = iPeriodoRepository;
    }


    public PeriodoDTO save(PeriodoDTO periodoDTO) {

        this.validate(periodoDTO);

        LOGGER.info("Salvando período de vendas");
        LOGGER.debug("Período de Vendas: {}", periodoDTO);

        Periodo periodo = new Periodo();

        periodo.setId(periodoDTO.getId());
        periodo.setInicioVendas(periodoDTO.getInicioVendas());
        periodo.setFimVendas(periodoDTO.getFimVendas());
        periodo.setDescricao(periodoDTO.getDescricao());
        periodo.setRetiradaPedido(periodoDTO.getRetiradaPedido());

        Fornecedor fornecedorCompleto = new Fornecedor();
        fornecedorCompleto = fornecedorService.findByIdFornecedor(periodoDTO.getIdFornecedor());
        periodo.setFornecedor(fornecedorCompleto);

        periodo = this.iPeriodoRepository.save(periodo);

        return PeriodoDTO.of(periodo);
    }

    public List<Periodo> findAll() {
        return iPeriodoRepository.findAll();
    }

    private void validate(PeriodoDTO periodoDTO) {
        LOGGER.info("Validando período");

        if (periodoDTO == null) {
            throw new IllegalArgumentException("PeriodoDTO não deve ser nulo");
        }

        if (StringUtils.isEmpty(periodoDTO.getInicioVendas().toString())) {
            throw new IllegalArgumentException("Inicio das vendas não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(periodoDTO.getFimVendas().toString())) {
            throw new IllegalArgumentException("Fim das vendas não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(periodoDTO.getIdFornecedor().toString())) {
            throw new IllegalArgumentException("Período de vendas deve ter um fornecedor");
        }
        String inicioVendas = periodoDTO.getInicioVendas().toString();
        String fimVendas = periodoDTO.getFimVendas().toString();
        String fornecedorAtual = periodoDTO.getIdFornecedor().toString();

        for (Periodo linha : findAll()) {
            String inicio = linha.getInicioVendas().toString();
            String fim = linha.getFimVendas().toString();
            String fornecedor = linha.getFornecedor().getId().toString();

            if (inicio.equals(inicioVendas) && fornecedorAtual.equals(fornecedor)) {
                throw new IllegalArgumentException("Fornecedor deve ter uma data inicial única");
            }
            if (fim.equals(fimVendas) && fornecedorAtual.equals(fornecedor)) {
                throw new IllegalArgumentException("Fornecedor deve ter uma data final única");
            }
        }
        LocalDate hoje = LocalDate.now();
        if (periodoDTO.getInicioVendas().isBefore(hoje)) {
            throw new IllegalArgumentException("Data inicial não deve ser anterior a hoje");
        }
        if (periodoDTO.getFimVendas().isBefore(hoje)) {
            throw new IllegalArgumentException("Data final não deve ser anterior a hoje");
        }

    }

    public PeriodoDTO findById(Long id) {
        Optional<Periodo> periodoOptional = this.iPeriodoRepository.findById(id);

        if (periodoOptional.isPresent()) {
            return PeriodoDTO.of(periodoOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public PeriodoDTO update(PeriodoDTO periodoDTO, Long id) {
        Optional<Periodo> periodoExistenteOptional = this.iPeriodoRepository.findById(id);

        if (periodoExistenteOptional.isPresent()) {
            Periodo periodoExistente = periodoExistenteOptional.get();

            LOGGER.info("Atualizando período de vendas... id: [{}]", periodoExistente.getId());
            LOGGER.debug("Payload: {}", periodoDTO);
            LOGGER.debug("Período de vendas existente: {}", periodoExistente);
            LocalDate hoje = LocalDate.now();

            if (hoje.isAfter(periodoExistente.getFimVendas())) {
                throw new IllegalArgumentException("Um Período de Vendas não pode ser mais alterado após o término de sua vigência");
            }


            periodoExistente.setInicioVendas(periodoDTO.getInicioVendas());
            periodoExistente.setFimVendas(periodoDTO.getFimVendas());
            periodoExistente.setDescricao(periodoDTO.getDescricao());
            periodoExistente.setRetiradaPedido(periodoDTO.getRetiradaPedido());

            Fornecedor fornecedorCompleto = new Fornecedor();
            fornecedorCompleto = fornecedorService.findByIdFornecedor(periodoDTO.getIdFornecedor());
            periodoExistente.setFornecedor(fornecedorCompleto);


            periodoExistente = this.iPeriodoRepository.save(periodoExistente);

            return PeriodoDTO.of(periodoExistente);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {

        LOGGER.info("Executando delete para período de vendas  de ID: [{}]", id);

        this.iPeriodoRepository.deleteById(id);
    }

    public Periodo findByIdPeriodo(Long id) {
        Optional<Periodo> periodoOptional = this.iPeriodoRepository.findById(id);

        if (periodoOptional.isPresent()) {
            return periodoOptional.get();
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }


}
