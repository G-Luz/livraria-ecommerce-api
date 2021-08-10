package com.fatec.livrariaecommerce.dao;

import com.fatec.livrariaecommerce.models.domain.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroDao extends JpaRepository<Livro, Integer> {
    /*@Query("SELECT s FROM Livro s WHERE s.titulo = ''")
    Optional<List<Livros>> buscarLivros(String titulo);*/
}
