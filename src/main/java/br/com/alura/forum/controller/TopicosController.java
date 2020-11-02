package br.com.alura.forum.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import br.com.alura.forum.controller.dto.ShowTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.controller.form.UpdateTopicoForm;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

  @Autowired
  private TopicoRepository topicoRepository;

  @Autowired
  private CursoRepository cursoRepository;

  @GetMapping
  public Page<TopicoDto> list(@RequestParam(required = false) String nomeCurso, @RequestParam int pagina,
      @RequestParam int quantidade) {

    Pageable paginacao = PageRequest.of(pagina, quantidade);

    if (nomeCurso == null) {
      Page<Topico> topicos = topicoRepository.findAll(paginacao);

      return TopicoDto.converter(topicos);
    }

    Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);

    return TopicoDto.converter(topicos);
  }

  @PostMapping
  @Transactional
  public ResponseEntity<TopicoDto> create(@RequestBody @Valid TopicoForm data, UriComponentsBuilder uriBuilder) {
    Topico topico = data.converter(cursoRepository);

    topicoRepository.save(topico);

    URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

    return ResponseEntity.created(uri).body(new TopicoDto(topico));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ShowTopicoDto> show(@PathVariable Long id) {
    Optional<Topico> topico = topicoRepository.findById(id);

    if (!topico.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(new ShowTopicoDto(topico.get()));
  }

  @PutMapping("/{id}")
  @Transactional
  public ResponseEntity<TopicoDto> update(@PathVariable Long id, @RequestBody @Valid UpdateTopicoForm data,
      UriComponentsBuilder uriBuilder) {

    Optional<Topico> optional = topicoRepository.findById(id);

    if (!optional.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    Topico topico = data.update(id, topicoRepository);

    return ResponseEntity.ok(new TopicoDto(topico));
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<?> delete(@PathVariable Long id) {
    Optional<Topico> topico = topicoRepository.findById(id);

    if (!topico.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    topicoRepository.deleteById(id);

    return ResponseEntity.ok().build();
  }
}
