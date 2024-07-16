package controller;

import model.BetVO;
import model.MultipleBetVO;
import repository.BetRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BetControllerTest {

    @InjectMocks
    private BetController betController;

    @Mock
    private BetRepository betRepository;

    public BetControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBets() {
        BetVO bet1 = new BetVO();
        BetVO bet2 = new BetVO();
        List<BetVO> bets = Arrays.asList(bet1, bet2);
        when(betRepository.findAll()).thenReturn(bets);

        List<BetVO> result = betController.getAllBets();

        assertEquals(2, result.size());
        verify(betRepository, times(1)).findAll();
    }

    @Test
    public void testInsertMultipleSuccess() {
        MultipleBetVO multipleBetVO = new MultipleBetVO();
        when(betRepository.insert(multipleBetVO)).thenReturn(null);

        ResponseEntity<String> response = betController.insertMultiple(multipleBetVO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Aposta criada com sucesso!", response.getBody());
        verify(betRepository, times(1)).insert(multipleBetVO);
    }

    @Test
    public void testInsertMultipleFailure() {
        MultipleBetVO multipleBetVO = new MultipleBetVO();
        doThrow(new RuntimeException("Erro ao inserir")).when(betRepository).insert(multipleBetVO);

        ResponseEntity<String> response = betController.insertMultiple(multipleBetVO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro ao tentar inserir aposta multipla.", response.getBody());
        verify(betRepository, times(1)).insert(multipleBetVO);
    }
    
    @Test
    public void testDeleteBetSuccess() {
        String id = "6696ad6a9edf9714bddd6b39";
        doNothing().when(betRepository).deleteById(id);

        ResponseEntity<String> response = betController.deleteBet(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Aposta deletada com sucesso!", response.getBody());
        verify(betRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteBetFailure() {
        String id = "6696ad6a9edf9714bddd6b39";
        doThrow(new RuntimeException("Erro ao deletar")).when(betRepository).deleteById(id);

        ResponseEntity<String> response = betController.deleteBet(id);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro ao tentar deletar aposta.", response.getBody());
        verify(betRepository, times(1)).deleteById(id);
    }
}
