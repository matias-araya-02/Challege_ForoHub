package mel.voll.challenge_forohub.controller;

import mel.voll.challenge_forohub.domain.topico.*;
import mel.voll.challenge_forohub.domain.usuarios.Usuario;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    private Usuario usuario;

    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(
            @RequestBody @Valid DatosRegistroTopico datosRegistroTopico,
            UriComponentsBuilder uriComponentsBuilder) {

        String autor = SecurityContextHolder.getContext().getAuthentication().getName();
        Topico topico = topicoRepository.save(new Topico(datosRegistroTopico, autor));

        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getNombreCurso(),
                topico.getAutor(),
                topico.getFechaCreacion(),
                topico.getStatus() ? "Privado" : "Publico"
        );

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }


    @GetMapping
    public ResponseEntity<Page<DatosListadoTopicos>> listadoTopicos(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findByStatusFalse(paginacion).map(DatosListadoTopicos::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retornaDatosTopicoEspecifico(@PathVariable Long id) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isPresent()) {
            Topico topico = topicoOptional.get();
            var datosTopico = new DatosRespuestaTopico(topico.getTitulo(), topico.getMensaje(),
                    topico.getNombreCurso(), topico.getAutor(), topico.getFechaCreacion(), topico.getStatus() ? "Privado" : "Publico");
            return ResponseEntity.ok(datosTopico);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El topico con id " + id + " no fue encontrado.");
        }

    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> actualizaTopico(@PathVariable Long id,
                                             @RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {

        if (!id.equals(datosActualizarTopico.id())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El ID de la URL (" + id + ") no coincide con el ID del cuerpo (" + datosActualizarTopico.id() + ").");
        }

        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isPresent()) {
            Topico topico = topicoOptional.get();
            String autor = SecurityContextHolder.getContext().getAuthentication().getName();
            topico.actualizarDatos(datosActualizarTopico, autor);
            return ResponseEntity.ok(new DatosRespuestaTopico(topico.getTitulo(), topico.getMensaje(),
                    topico.getNombreCurso(), topico.getAutor(), topico.getFechaCreacion(), topico.getStatus() ? "Privado" : "Publico"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El topico con ID (" + id + ") no fue encontrado.");
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> eliminarTopico(@PathVariable Long id) {
        if (topicoRepository.existsById(id)) {
            topicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El tópico con ID (" + id + ") no fue encontrado.");
        }
    }

    @PutMapping("/{id}/toggle")
    @Transactional
    public ResponseEntity<?> toggleStatus(@PathVariable Long id) {
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isPresent()) {
            Topico topico = topicoOptional.get();
            topico.togglePrivado();
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El tópico con ID (" + id + ") no fue encontrado.");
        }
    }

}
