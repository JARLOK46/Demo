package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ApartamentoService {

    private final ApartamentoRepository apartamentoRepository;

    public ApartamentoService(ApartamentoRepository apartamentoRepository) {
        this.apartamentoRepository = apartamentoRepository;
        apartamentoRepository.save(new Apartamento("apt-001", "101", "A", 68.0, "prop-001"));
        apartamentoRepository.save(new Apartamento("apt-002", "202", "B", 72.5, "prop-002"));
    }

    public List<Apartamento> findAll() {
        return apartamentoRepository.findAll();
    }

    public Apartamento findByPropietarioId(String propietarioId) {
        return apartamentoRepository.findByPropietarioId(propietarioId);
    }

    public Apartamento save(Apartamento apartamento) {
        return apartamentoRepository.save(apartamento);
    }

    public Apartamento update(String id, Apartamento detalles) {
        Apartamento existente = apartamentoRepository.findById(id);
        if (existente != null) {
            existente.setNumero(detalles.getNumero());
            existente.setTorre(detalles.getTorre());
            existente.setArea(detalles.getArea());
            existente.setPropietarioId(detalles.getPropietarioId());
            return apartamentoRepository.save(existente);
        }
        return null;
    }

    public boolean deleteById(String id) {
        Apartamento existente = apartamentoRepository.findById(id);
        if (existente != null) {
            apartamentoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
