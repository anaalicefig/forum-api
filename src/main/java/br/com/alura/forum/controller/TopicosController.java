package br.com.alura.forum.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.modelo.Curso;

@RestController
public class TopicosController {

  @RequestMapping("/topicos")
  public List<TopicoDto> list() {
    Topico topico = new Topico("Duvida em Go", "Duvida com golang", new Curso("Go", "Programação"));

    return TopicoDto.converter(Arrays.asList(topico, topico, topico));
  }
}
