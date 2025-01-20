package mel.voll.challenge_forohub.domain.topico;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

public record DatosListadoTopicos(
        Long id,
        String titulo,
        String mensaje,
        String nombreCurso,
        String autor,
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDateTime fechaCreacion,
        String status
) {
    public DatosListadoTopicos(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getNombreCurso(),
                formatearAutor(topico.getAutor()),
                topico.getFechaCreacion(),
                topico.getStatus() ? "Privado" : "Publico"
        );
    }

    private static String formatearAutor(String autor) {
        if (autor == null || autor.isBlank()) {
            return "Anonimo";
        }
        return Arrays.stream(autor.split("\\."))
                .map(a -> a.substring(0, 1).toUpperCase() + a.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }

}
