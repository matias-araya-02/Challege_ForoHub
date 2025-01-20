package mel.voll.challenge_forohub.domain.topico;

import java.time.LocalDateTime;

public record DatosRespuestaTopico(
        String titulo,
        String mensaje,
        String nombreCurso,
        String autor,
        LocalDateTime fechaCreacion,
        String status
) {
}
