package tn.esprit.rh.achat.test.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.StockRepository;
import tn.esprit.rh.achat.services.StockServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class StockServiceTest {

    @InjectMocks
    private StockServiceImpl stockService;

    @Mock
    private StockRepository stockRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveAllStocks() {
        List<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock("Stock1", 100, 50));
        stocks.add(new Stock("Stock2", 75, 30));

        when(stockRepository.findAll()).thenReturn(stocks);

        List<Stock> result = stockService.retrieveAllStocks();

        assertEquals(2, result.size());
    }

    @Test
    public void testAddStock() {
        Stock stock = new Stock("Stock1", 100, 50);

        when(stockRepository.save(stock)).thenReturn(stock);

        Stock result = stockService.addStock(stock);

        // Verify the result
        assertEquals("Stock1", result.getLibelleStock());
    }


    @Test
    public void testDeleteStock() {
        Long stockIdToDelete = 1L;

        doNothing().when(stockRepository).deleteById(stockIdToDelete);


        stockService.deleteStock(stockIdToDelete);

        verify(stockRepository, Mockito.times(1)).deleteById(stockIdToDelete);
    }
    @Test
    public void testUpdateStock() {
        // Arrange
        Stock stockToUpdate = new Stock("UpdatedStock", 150, 75);

        when(stockRepository.save(stockToUpdate)).thenReturn(stockToUpdate);

        Stock updatedStock = stockService.updateStock(stockToUpdate);

        assertEquals("UpdatedStock", updatedStock.getLibelleStock());
        assertEquals(150, updatedStock.getQte());
        assertEquals(75, updatedStock.getQteMin());

        Mockito.verify(stockRepository, Mockito.times(1)).save(stockToUpdate);
    }
}