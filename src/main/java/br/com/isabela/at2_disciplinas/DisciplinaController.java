package br.com.isabela.at2_disciplinas;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {

    private Map<Integer, Disciplina> disciplinas = new HashMap<>();

    private AtomicInteger proximoId = new AtomicInteger(1);

    @PostConstruct
    public void init() {
        int id1 = proximoId.getAndIncrement();
        Disciplina d1 = new Disciplina(id1, "Engenharia de Software III", "ES3");
        disciplinas.put(id1, d1);

        int id2 = proximoId.getAndIncrement();
        Disciplina d2 = new Disciplina(id2, "Integração Contínua e Entrega Contínua", "CI/CD");
        disciplinas.put(id2, d2);
    }

    @GetMapping
    public Collection<Disciplina> listarTodas() {
        return disciplinas.values();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Disciplina> buscarPorId(@PathVariable Integer id) {
        Disciplina disciplina = disciplinas.get(id);
        if (disciplina == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(disciplina);
    }

    @PostMapping
    public ResponseEntity<Disciplina> criar(@RequestBody Disciplina novaDisciplina) {
        int id = proximoId.getAndIncrement();
        novaDisciplina.setId(id);

        disciplinas.put(id, novaDisciplina);

        return ResponseEntity.status(HttpStatus.CREATED).body(novaDisciplina);
    }
}