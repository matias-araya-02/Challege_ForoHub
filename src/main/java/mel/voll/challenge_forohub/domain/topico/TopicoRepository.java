package mel.voll.challenge_forohub.domain.topico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Page<Topico> findByStatusFalse(Pageable paginacion);

/*
    @Query("""
            select t.private
            from Topico t
            where
            t.id = :id
            """)
    boolean findPrivadoById(Long id);
*/

}
