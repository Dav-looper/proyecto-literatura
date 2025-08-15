package com.alura_challenge.literatura.autor;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AutorService {
    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    public List<Autor> listarAutoresVivosEnAnio(int anio) {
        return autorRepository.findByFechaNacimientoLessThanEqualAndFechaFallecimientoGreaterThanEqualOrFechaFallecimientoIsNull(anio);
    }
}
