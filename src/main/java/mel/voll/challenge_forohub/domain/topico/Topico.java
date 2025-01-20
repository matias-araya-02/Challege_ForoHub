package mel.voll.challenge_forohub.domain.topico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @Column(unique = true)
    private String mensaje;
    private LocalDateTime fechaCreacion;
    private Boolean status; //privado(true/1) o publico(false/0)
    private String autor;
    @Column(name = "nombre_del_curso")
    private String nombreCurso;

    public Topico(DatosRegistroTopico datosRegistroTopico, String autor) {
        this.status = false;
        this.titulo = datosRegistroTopico.titulo();
        this.mensaje = datosRegistroTopico.mensaje();
        this.nombreCurso = datosRegistroTopico.nombreCurso();
        this.fechaCreacion = LocalDateTime.now();
        this.autor = autor;
    }

    public void actualizarDatos(DatosActualizarTopico datosActualizarTopico, String autor) {

        this.status = false;
        this.fechaCreacion = LocalDateTime.now();
        this.autor = autor;

        if (datosActualizarTopico.titulo() != null) {
            this.titulo = datosActualizarTopico.titulo();
        }
        if (datosActualizarTopico.mensaje() != null) {
            this.mensaje = datosActualizarTopico.mensaje();
        }
        if (datosActualizarTopico.nombreCurso() != null) {
            this.nombreCurso = datosActualizarTopico.nombreCurso();
        }
    }

    public void togglePrivado() {
        this.status = !this.status;
    }

}