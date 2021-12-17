package de.fhswf.in.informatik.se.projektverwaltung.backend.services;

import de.fhswf.in.informatik.se.projektverwaltung.backend.entities.Company;
import de.fhswf.in.informatik.se.projektverwaltung.backend.repositories.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse CompanyService implementiert primär die Methoden aus dem Interface @{@link CompanyRepository}
 * um diese im Frontend dann verwenden zu können.
 *
 * @author  Ramon Günther & Ivonne Kneißig
 */
@Service
public class CompanyService {

    private final CompanyRepository repository;

    public CompanyService(CompanyRepository repository) {
        this.repository = repository;
    }

    public void saveCompany(Company company){
        repository.save(company);
    }

    public List<Company> getAllCompanies(){
        return repository.findAll();
    }

    public Company getCompanyById(Long id){
        return repository.getById(id);
    }

    public Company getCompanyByCompanyName(String companyName){
        return repository.getCompanyByCompanyName(companyName);
    }

    /**
     * Gibt alle Namen der gespeicherten Unternehmen als Liste zurück
     *
     * @return Liste aus den Namen der Unternehmen
     */
    public List<String> getAllCompanyNames(){
        List<String> companyNames = new ArrayList<>();
        for(Company company : repository.findAll()){
            companyNames.add(company.getCompanyName());
        }
        return companyNames;
    }
}
