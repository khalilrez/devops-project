package tn.esprit.rh.achat.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.rh.achat.entities.SecteurActivite;
import tn.esprit.rh.achat.repositories.SecteurActiviteRepository;
import tn.esprit.rh.achat.services.SecteurActiviteServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SecteurActiviteServiceImplTest {

    @Mock
    private SecteurActiviteRepository secteurActiviteRepository;

    @InjectMocks
    private SecteurActiviteServiceImpl secteurActiviteService;

    @Test
    public void testRetrieveAllSecteurActivite() {
        // Arrange
        SecteurActivite secteur1 = new SecteurActivite(1L, "Secteur 1","Secteur 1",null);
        SecteurActivite secteur2 = new SecteurActivite(2L, "Secteur 2","Secteur 2",null);
        List<SecteurActivite> secteurActiviteList = Arrays.asList(secteur1, secteur2);
        Mockito.when(secteurActiviteRepository.findAll()).thenReturn(secteurActiviteList);

        // Act
        List<SecteurActivite> result = secteurActiviteService.retrieveAllSecteurActivite();

        // Assert
        assertEquals(2, result.size());
        assertEquals(secteur1, result.get(0));
        assertEquals(secteur2, result.get(1));
    }

    @Test
    public void testAddSecteurActivite() {
        // Arrange
        SecteurActivite secteur = new SecteurActivite(1L, "Secteur 1","Secteur 1",null);
        Mockito.when(secteurActiviteRepository.save(secteur)).thenReturn(secteur);

        // Act
        SecteurActivite result = secteurActiviteService.addSecteurActivite(secteur);

        // Assert
        assertEquals(secteur, result);
    }

    @Test
    public void testUpdateSecteurActivite() {
        // Arrange
        SecteurActivite secteur = new SecteurActivite(1L, "Secteur 1 Updated","Secteur 1 Updated",null);
        Mockito.when(secteurActiviteRepository.save(secteur)).thenReturn(secteur);

        // Act
        SecteurActivite result = secteurActiviteService.updateSecteurActivite(secteur);

        // Assert
        assertEquals(secteur, result);
    }

    @Test
    public void testRetrieveSecteurActivite() {
        // Arrange
        long id = 1L;
        SecteurActivite secteur = new SecteurActivite(id, "Secteur 1 Updated","Secteur 1 Updated",null);
        Mockito.when(secteurActiviteRepository.findById(id)).thenReturn(Optional.of(secteur));

        // Act
        SecteurActivite result = secteurActiviteService.retrieveSecteurActivite(id);

        // Assert
        assertEquals(secteur, result);
    }

    @Test
    public void testDeleteSecteurActivite() {
        // Arrange
        long id = 1L;

        // Act
        secteurActiviteService.deleteSecteurActivite(id);

        // Assert
        Mockito.verify(secteurActiviteRepository, Mockito.times(1)).deleteById(id);
    }
}
