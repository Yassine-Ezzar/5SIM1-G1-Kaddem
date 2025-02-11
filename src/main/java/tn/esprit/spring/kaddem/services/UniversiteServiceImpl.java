package tn.esprit.spring.kaddem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;

@Service
public class UniversiteServiceImpl implements IUniversiteService {
    @Autowired
    UniversiteRepository universiteRepository;
    
    @Autowired
    DepartementRepository departementRepository;

    private static final Logger logger = LogManager.getLogger(UniversiteServiceImpl.class);

    public UniversiteServiceImpl() {
        // TODO Auto-generated constructor stub
    }

    public List<Universite> retrieveAllUniversites() {
        logger.info("Récupération de toutes les universités");
        List<Universite> universites = (List<Universite>) universiteRepository.findAll();
        logger.debug("Universités récupérées: {}", universites);
        return universites;
    }

    public Universite addUniversite(Universite u) {
        logger.info("Ajout de l'université: {}", u.getNomUniv());
        Universite addedUniversite = universiteRepository.save(u);
        logger.info("Université ajoutée avec succès: {}", addedUniversite);
        return addedUniversite;
    }

    public Universite updateUniversite(Universite u) {
        logger.info("Mise à jour de l'université: {}", u.getNomUniv());
        Universite updatedUniversite = universiteRepository.save(u);
        logger.info("Université mise à jour avec succès: {}", updatedUniversite);
        return updatedUniversite;
    }

    public Universite retrieveUniversite(Integer idUniversite) {
        logger.info("Récupération de l'université avec ID: {}", idUniversite);
        Universite u = universiteRepository.findById(idUniversite).orElse(null);
        if (u != null) {
            logger.debug("Université récupérée: {}", u);
        } else {
            logger.warn("Aucune université trouvée avec l'ID: {}", idUniversite);
        }
        return u;
    }

    public void deleteUniversite(Integer idUniversite) {
        logger.info("Suppression de l'université avec ID: {}", idUniversite);
        Universite u = retrieveUniversite(idUniversite);
        if (u != null) {
            universiteRepository.delete(u);
            logger.info("Université supprimée avec succès: {}", u.getNomUniv());
        } else {
            logger.warn("Aucune université trouvée à supprimer avec l'ID: {}", idUniversite);
        }
    }

    public void assignUniversiteToDepartement(Integer idUniversite, Integer idDepartement) {
        logger.info("Assignation de l'université ID {} au département ID {}", idUniversite, idDepartement);
        Universite u = universiteRepository.findById(idUniversite).orElse(null);
        Departement d = departementRepository.findById(idDepartement).orElse(null);
        if (u != null && d != null) {
            u.getDepartements().add(d);
            universiteRepository.save(u);
            logger.info("Université {} assignée au département {}", u.getNomUniv(), d.getNomDepart());
        } else {
            logger.warn("Erreur lors de l'assignation: Université ou département non trouvé.");
        }
    }

    public Set<Departement> retrieveDepartementsByUniversite(Integer idUniversite) {
        logger.info("Récupération des départements pour l'université ID: {}", idUniversite);
        Universite u = universiteRepository.findById(idUniversite).orElse(null);
        Set<Departement> departements = u != null ? u.getDepartements() : null;
        if (departements != null) {
            logger.debug("Départements récupérés pour l'université {}: {}", u.getNomUniv(), departements);
        } else {
            logger.warn("Aucun département trouvé pour l'université ID: {}", idUniversite);
        }
        return departements;
    }
}

