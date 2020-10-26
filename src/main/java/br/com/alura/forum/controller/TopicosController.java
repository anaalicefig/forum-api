package br.com.alura.forum.controller;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.TopicoForm;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

  @Autowired
  private TopicoRepository topicoRepository;

  @Autowired
  private CursoRepository cursoRepository;

  @GetMapping
  public List<TopicoDto> list(String nomeCurso) {
    if (nomeCurso == null) {
      List<Topico> topicos = topicoRepository.findAll();

      return TopicoDto.converter(topicos);
    }

    List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);

    return TopicoDto.converter(topicos);
  }

  @PostMapping
  public ResponseEntity<TopicoDto> create(@RequestBody TopicoForm data, UriComponentsBuilder uriBuilder) {
    Topico topico = data.converter(cursoRepository);

    topicoRepository.save(topico);

    URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

    return ResponseEntity.created(uri).body(new TopicoDto(topico));
  }
}
