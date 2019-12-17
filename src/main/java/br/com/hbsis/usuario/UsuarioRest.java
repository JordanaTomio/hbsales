package br.com.hbsis.usuario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Classe resposável por receber as requisições externas ao sistema
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioRest {
	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioRest.class);

	private final UsuarioService usuarioService;

	@Autowired
	public UsuarioRest(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@PostMapping
	public UsuarioDTO save(@RequestBody UsuarioDTO usuarioDTO) {
		LOGGER.info("Recebendo solicitação de persistência de usuário...");
		LOGGER.debug("Payaload: {}", usuarioDTO);

		return this.usuarioService.save(usuarioDTO);
	}

	@GetMapping("/{id}")
		public UsuarioDTO find(@PathVariable("id") Long id) {

			LOGGER.info("Recebendo find by ID... id: [{}]", id);

			return this.usuarioService.findById(id);
	}

	@PutMapping("/{id}")
	public UsuarioDTO udpate(@PathVariable("id") Long id, @RequestBody UsuarioDTO usuarioDTO) {
		LOGGER.info("Recebendo Update para Usuário de ID: {}", id);
		LOGGER.debug("Payload: {}", usuarioDTO);

		return this.usuarioService.update(usuarioDTO, id);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		LOGGER.info("Recebendo Delete para Usuário de ID: {}", id);

		this.usuarioService.delete(id);
	}
}
