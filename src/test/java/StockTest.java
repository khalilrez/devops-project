import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.StockRepository;
import tn.esprit.rh.achat.services.StockServiceImpl;


@ExtendWith(MockitoExtension.class)
public class StockTest {
    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllStocks() {
        // Given
        List<Stock> mockStockList = new ArrayList<>();
        mockStockList.add(new Stock("Stock 1", 50, 10));
        mockStockList.add(new Stock("Stock 2", 30, 5));

        // When
        when(stockRepository.findAll()).thenReturn(mockStockList);

        // Then
        List<Stock> result = stockService.retrieveAllStocks();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        verify(stockRepository, times(1)).findAll();
    }

    @Test
    void testAddStock() {
        // Given
        Stock stockToAdd = new Stock("New Stock", 20, 5);

        // When
        when(stockRepository.save(any(Stock.class))).thenReturn(stockToAdd);

        // Then
        Stock result = stockService.addStock(stockToAdd);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("New Stock", result.getLibelleStock());
        Assertions.assertEquals(Optional.of(20).get(), result.getQte());
        Assertions.assertEquals(Optional.of(5).get(), result.getQteMin());
        verify(stockRepository, times(1)).save(any(Stock.class));
    }

    @Test
    void testDeleteStock() {
        // Given
        Long stockIdToDelete = 1L;

        // When
        // No need for 'when' as we are testing void method

        // Then
        assertDoesNotThrow(() -> stockService.deleteStock(stockIdToDelete));
        verify(stockRepository, times(1)).deleteById(eq(stockIdToDelete));
    }

    @Test
    void testUpdateStock() {
        // Given
        Stock stockToUpdate = new Stock("Updated Stock", 30, 8);

        // When
        when(stockRepository.save(any(Stock.class))).thenReturn(stockToUpdate);

        // Then
        Stock result = stockService.updateStock(stockToUpdate);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Updated Stock", result.getLibelleStock());
        Assertions.assertEquals(Optional.of(30).get(), result.getQte());
        Assertions.assertEquals(Optional.of(8).get(), result.getQteMin());
        verify(stockRepository, times(1)).save(any(Stock.class));
    }

    @Test
    void testRetrieveStock() {
        // Given
        Long stockIdToRetrieve = 1L;
        Stock mockStock = new Stock("Mock Stock", 40, 7);

        // When
        when(stockRepository.findById(eq(stockIdToRetrieve))).thenReturn(Optional.of(mockStock));

        // Then
        Stock result = stockService.retrieveStock(stockIdToRetrieve);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Mock Stock", result.getLibelleStock());
        Assertions.assertEquals(Optional.of(40).get(), result.getQte());
        Assertions.assertEquals(Optional.of(7).get(), result.getQteMin());
        verify(stockRepository, times(1)).findById(eq(stockIdToRetrieve));
    }
}
