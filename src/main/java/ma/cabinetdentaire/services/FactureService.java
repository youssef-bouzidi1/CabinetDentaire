package ma.cabinetdentaire.services;

import ma.cabinetdentaire.entity.Facture;
import ma.cabinetdentaire.repository.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class FactureService {
    @Autowired
    private FactureRepository factureRepository;
    public List<Facture> findAll(){return factureRepository.findAll();}
    public Optional<Facture> findById(Long id){return factureRepository.findById(id);}
    public Facture save(Facture facture){return factureRepository.save(facture);}
    public void deleteById(Long id){factureRepository.deleteById(id);}

    public List<Facture> saveAll(List<Facture> factures) {
        return factureRepository.saveAll(factures);
    }
}
