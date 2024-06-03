package ma.cabinetdentaire.services;

import ma.cabinetdentaire.entity.AntecedentMedicale;
import ma.cabinetdentaire.repository.AntecedentMedicaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AntecedentMedicaleService {

    @Autowired
    private AntecedentMedicaleRepository antecedentMedicaleRepository;

    public List<AntecedentMedicale> getAllAntecedents() {
        return antecedentMedicaleRepository.findAll();
    }

    public AntecedentMedicale getAntecedentById(Long id) {
        return antecedentMedicaleRepository.findById(id).orElse(null);
    }

    public AntecedentMedicale createAntecedent(AntecedentMedicale antecedentMedicale) {
        return antecedentMedicaleRepository.save(antecedentMedicale);
    }

    public AntecedentMedicale updateAntecedent(Long id, AntecedentMedicale antecedentMedicale) {
        AntecedentMedicale existingAntecedent = getAntecedentById(id);
        if (existingAntecedent != null) {
            existingAntecedent.setDescription(antecedentMedicale.getDescription());
            existingAntecedent.setDateAntecedent(antecedentMedicale.getDateAntecedent());
            existingAntecedent.setPatient(antecedentMedicale.getPatient());
            existingAntecedent.setCategorieAntecedentMedical(antecedentMedicale.getCategorieAntecedentMedical());
            return antecedentMedicaleRepository.save(existingAntecedent);
        }
        return null;
    }

    public void deleteAntecedent(Long id) {
        antecedentMedicaleRepository.deleteById(id);
    }
}
