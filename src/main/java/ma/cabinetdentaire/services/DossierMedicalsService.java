package ma.cabinetdentaire.services;

import ma.cabinetdentaire.entity.DossierMedicale;
import ma.cabinetdentaire.repository.DossierMedicaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DossierMedicalsService {

    private final DossierMedicaleRepository dossierMedicaleRepository;

    @Autowired
    public DossierMedicalsService(DossierMedicaleRepository dossierMedicaleRepository) {
        this.dossierMedicaleRepository = dossierMedicaleRepository;
    }

    public DossierMedicale save(DossierMedicale dossierMedicale) {
        return dossierMedicaleRepository.save(dossierMedicale);
    }

    public Optional<DossierMedicale> findById(Long id) {
        return dossierMedicaleRepository.findById(id);
    }

    public void deleteById(Long id) {
        dossierMedicaleRepository.deleteById(id);
    }
}
