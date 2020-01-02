package br.com.hbsis.categoria;

import br.com.hbsis.fornecedor.Fornecedor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlterarCodigo {
    private final ICategoriaRepository iCategoriaRepository;


    public AlterarCodigo(ICategoriaRepository iCategoriaRepository) {
        this.iCategoriaRepository = iCategoriaRepository;

    }

    public void alteraCategoria(Fornecedor fornecedor){
        List<Categoria> listaCategoria = iCategoriaRepository.findAllByFornecedor_IdIs(fornecedor.getId());
        for(Categoria categoriaCompleta : listaCategoria){
            categoriaCompleta.setCodigo(codigoCategoria(fornecedor, categoriaCompleta));
            categoriaCompleta.setNome(categoriaCompleta.getNome());
            categoriaCompleta.setFornecedor(fornecedor);
            iCategoriaRepository.save(categoriaCompleta);
        }
    }

    public String codigoCategoria(Fornecedor fornecedor, Categoria categoria){
        String cnpj = fornecedor.getCnpj();
        String cnpjPronto = cnpj.substring(10);

        String x = categoria.getCodigo().substring(7, 10);
        String codigoZero = org.apache.commons.lang.StringUtils.leftPad(x, 3, "0");
        String codigo = "CAT" + cnpjPronto + codigoZero;

        return codigo;
    }
}
