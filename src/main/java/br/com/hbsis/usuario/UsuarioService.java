package br.com.hbsis.usuario;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Classe responsável pelo processamento da regra de negócio
 */
@Service
public class UsuarioService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);

	private final IUsuarioRepository iUsuarioRepository;

	public UsuarioService(IUsuarioRepository iUsuarioRepository) {
		this.iUsuarioRepository = iUsuarioRepository;
	}

	public UsuarioDTO save(UsuarioDTO usuarioDTO) {

		this.validate(usuarioDTO);

		LOGGER.info("Salvando usuário");
		LOGGER.debug("Usuario: {}", usuarioDTO);

		Usuario usuario = new Usuario();
		usuario.setLogin(usuarioDTO.getLogin());
		usuario.setSenha(usuarioDTO.getSenha());
		usuario.setUuid(UUID.randomUUID().toString());

		usuario = this.iUsuarioRepository.save(usuario);

		return UsuarioDTO.of(usuario);
	}

	private void validate(UsuarioDTO usuarioDTO) {
		LOGGER.info("Validando Usuario");

		if (usuarioDTO == null) {
			throw new IllegalArgumentException("UsuarioDTO não deve ser nulo");
		}

		if (StringUtils.isEmpty(usuarioDTO.getSenha())) {
			throw new IllegalArgumentException("Senha não deve ser nula/vazia");
		}

		if (StringUtils.isEmpty(usuarioDTO.getLogin())) {
			throw new IllegalArgumentException("Login não deve ser nulo/vazio");
		}
	}

	public UsuarioDTO findById(Long id) {
		Optional<Usuario> usuarioOptional = this.iUsuarioRepository.findById(id);

		if (usuarioOptional.isPresent()) {
			return UsuarioDTO.of(usuarioOptional.get());
		}

		throw new IllegalArgumentException(String.format("ID %s não existe", id));
	}

	public UsuarioDTO update(UsuarioDTO usuarioDTO, Long id) {
		Optional<Usuario> usuarioExistenteOptional = this.iUsuarioRepository.findById(id);

		if (usuarioExistenteOptional.isPresent()) {
			Usuario usuarioExistente = usuarioExistenteOptional.get();

			LOGGER.info("Atualizando usuário... id: [{}]", usuarioExistente.getId());
			LOGGER.debug("Payload: {}", usuarioDTO);
			LOGGER.debug("Usuario Existente: {}", usuarioExistente);

			usuarioExistente.setLogin(usuarioDTO.getLogin());
			usuarioExistente.setSenha(usuarioDTO.getSenha());

			usuarioExistente = this.iUsuarioRepository.save(usuarioExistente);

			return UsuarioDTO.of(usuarioExistente);
		}


		throw new IllegalArgumentException(String.format("ID %s não existe", id));
	}

	public void delete(Long id) {

		LOGGER.info("Executando delete para usuário de ID: [{}]", id);

		this.iUsuarioRepository.deleteById(id);
	}
}
