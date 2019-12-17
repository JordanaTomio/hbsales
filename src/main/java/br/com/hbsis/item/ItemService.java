package br.com.hbsis.item;

import br.com.hbsis.produtos.Produtos;
import br.com.hbsis.produtos.ProdutosService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);
    private final IItemRepository iItemRepository;
    private final ProdutosService produtosService;

    public ItemService(ItemService itemService, IItemRepository iItemRepository1, ProdutosService produtosService) {
        this.iItemRepository = iItemRepository1;
        this.produtosService = produtosService;
    }

    public ItemDTO save(ItemDTO itemDTO) {

        this.validate(itemDTO);

        LOGGER.info("Salvando item de pedido");
        LOGGER.debug("Item: {}", itemDTO);

        Item item = new Item();

        Optional <Produtos> produtoCompleto =  this.produtosService.findByIdProduto(itemDTO.getIdProduto());
        item.setProdutos(produtoCompleto.get());
        item.setQuantidade(itemDTO.getQuantidade());

        item = this.iItemRepository.save(item);
        return ItemDTO.of(item);

    }
    private void validate(ItemDTO itemDTO) {
        LOGGER.info("Validando itens de pedido");

        if (itemDTO == null) {
            throw new IllegalArgumentException("ItemDTO não deve ser nulo");
        }

        if (StringUtils.isEmpty(itemDTO.getIdProduto().toString())) {
            throw new IllegalArgumentException("Id do produto não deve ser nulo/vazio");
        }
        if (StringUtils.isEmpty(String.valueOf(itemDTO.getQuantidade()))) {
            throw new IllegalArgumentException("Quantidade de produtos não deve ser nula/vazia");
        }


    }



}
