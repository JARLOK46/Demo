package com.example.demo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class ApartamentoRepository {

    private final Map<String, Apartamento> apartamentos = new LinkedHashMap<>();

    public Apartamento save(Apartamento apartamento) {
        apartamentos.put(apartamento.getId(), apartamento);
        return apartamento;
    }

    public List<Apartamento> findAll() {
        return new ArrayList<>(apartamentos.values());
    }

    public Apartamento findByPropietarioId(String propietarioId) {
        return apartamentos.values().stream()
                .filter(apartamento -> propietarioId.equals(apartamento.getPropietarioId()))
                .findFirst()
                .orElse(null);
    }
}
