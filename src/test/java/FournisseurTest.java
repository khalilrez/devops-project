import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.rh.achat.entities.DetailFournisseur;
import tn.esprit.rh.achat.entities.Fournisseur;
import tn.esprit.rh.achat.repositories.DetailFournisseurRepository;
import tn.esprit.rh.achat.repositories.FournisseurRepository;
import tn.esprit.rh.achat.services.FournisseurServiceImpl;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class FournisseurTest {

    @Mock
    private FournisseurRepository fournisseurRepository;
    @Mock
    private DetailFournisseurRepository detailFournisseurRepository;

    @InjectMocks
    private FournisseurServiceImpl fournisseurService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddFournisseur() {
        Fournisseur expectedFournisseur = new Fournisseur();
        expectedFournisseur.setIdFournisseur(1L);
        when(fournisseurRepository.save(any(Fournisseur.class))).thenReturn(expectedFournisseur);
        when(fournisseurRepository.findById(anyLong())).thenReturn(Optional.of(expectedFournisseur));
        expectedFournisseur.setDetailFournisseur(new DetailFournisseur());
        fournisseurRepository.save(expectedFournisseur);
        Fournisseur createdFournisseur = fournisseurService.retrieveFournisseur(1L);

        verify(fournisseurRepository).findById(Mockito.anyLong());
        assertNotNull(createdFournisseur);
    }

    @Test
    public void testUpdateFournisseur() {
        Fournisseur expectedFournisseur = new Fournisseur();
        expectedFournisseur.setIdFournisseur(1L);
        when(fournisseurRepository.save(any(Fournisseur.class))).thenReturn(expectedFournisseur);
        expectedFournisseur.setLibelle("updated");
        expectedFournisseur.setDetailFournisseur(new DetailFournisseur());
        Fournisseur updatedFournisseur = fournisseurService.updateFournisseur(expectedFournisseur);

        verify(fournisseurRepository).save(any(Fournisseur.class));
        assertNotNull(updatedFournisseur);
        assertEquals(expectedFournisseur.getLibelle(), updatedFournisseur.getLibelle());
    }

    @Test
    public void testDeleteFournisseur() {
        Long id = 1L;
        fournisseurService.deleteFournisseur(id);

        verify(fournisseurRepository).deleteById(id);
    }

    @Test
    public void testFindFournisseurById() {
        Long id = 1L;
        Fournisseur expectedFournisseur = new Fournisseur();
        expectedFournisseur.setIdFournisseur(id);
        when(fournisseurRepository.findById(id)).thenReturn(Optional.of(expectedFournisseur));

        Fournisseur foundFournisseur = fournisseurService.retrieveFournisseur(id);

        verify(fournisseurRepository).findById(id);
        assertNotNull(foundFournisseur);
        assertEquals(id, foundFournisseur.getIdFournisseur());
    }

    @Test
    public void testFindAllFournisseurs() {
        List<Fournisseur> expectedFournisseurs = new ArrayList<>();
        expectedFournisseurs.add(new Fournisseur());
        expectedFournisseurs.add(new Fournisseur());
        when(fournisseurRepository.findAll()).thenReturn(expectedFournisseurs);

        List<Fournisseur> foundFournisseurs = fournisseurService.retrieveAllFournisseurs();

        verify(fournisseurRepository).findAll();
        assertNotNull(foundFournisseurs);
        assertEquals(expectedFournisseurs.size(), foundFournisseurs.size());
    }
}
