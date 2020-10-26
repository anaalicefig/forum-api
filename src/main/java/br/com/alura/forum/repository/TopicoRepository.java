package br.com.alura.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import br.com.alura.forum.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
  List<Topico> findByCursoNome(String nomeCurso);
}
