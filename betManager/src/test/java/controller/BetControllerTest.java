package controller;

import model.BetVO;
import model.Game;
import model.MultipleBetVO;
import model.OneEntranceDetail;
import repository.BetRepository;
import repository.MultipleBetRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BetControllerTest {

    @InjectMocks
    private BetController betController;

    @Mock
    private BetRepository betRepository;
    
    @Mock
    private MultipleBetRepository multipleBetRepository;

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
        when(multipleBetRepository.insert(multipleBetVO)).thenReturn(null);

        ResponseEntity<String> response = betController.insertMultipleBet(multipleBetVO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Aposta criada com sucesso!", response.getBody());
        verify(multipleBetRepository, times(1)).insert(multipleBetVO);
    }

    @Test
    public void testInsertMultipleFailure() {
        MultipleBetVO multipleBetVO = new MultipleBetVO();
        doThrow(new RuntimeException("Erro ao tentar inserir aposta multipla.")).when(multipleBetRepository).insert(multipleBetVO);

        ResponseEntity<String> response = betController.insertMultipleBet(multipleBetVO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro ao tentar inserir aposta multipla.", response.getBody());
        verify(multipleBetRepository, times(1)).insert(multipleBetVO);
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
    
    @Test
    public void testUpdateMultipleBetSuccess() {
        String id = "6696ad6a9edf9714bddd6b39";
        MultipleBetVO existingBet = new MultipleBetVO();
        MultipleBetVO updatedBet = new MultipleBetVO();
        updatedBet.setValue(100.0);
        updatedBet.setOdd(2.5);
        when(multipleBetRepository.findById(id)).thenReturn(Optional.of(existingBet));
        when(multipleBetRepository.save(existingBet)).thenReturn(existingBet);

        ResponseEntity<String> response = betController.updateMultipleBet(id, updatedBet);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Aposta atualizada com sucesso!", response.getBody());
        assertEquals(100.0, existingBet.getValue());
        assertEquals(2.5, existingBet.getOdd());
        verify(multipleBetRepository, times(1)).findById(id);
        verify(multipleBetRepository, times(1)).save(existingBet);
    }

    @Test
    public void testUpdateMultipleBetPartialSuccess() {
        String id = "6696ad6a9edf9714bddd6b39";
        MultipleBetVO existingBet = new MultipleBetVO();
        existingBet.setValue(50.0);
        existingBet.setOdd(1.5);
        existingBet.setGreen(true);

        Game game = new Game();
        ArrayList<Game> gamesList = new ArrayList<>();
        OneEntranceDetail oneEntrance = new OneEntranceDetail();
        oneEntrance.setDescription("Teste");
        ArrayList<OneEntranceDetail> entranceList = new ArrayList<>();
        entranceList.add(oneEntrance);
        game.setEntrances(entranceList);
        gamesList.add(game);

        MultipleBetVO updatedBet = new MultipleBetVO();
        updatedBet.setValue(100.0); // Atualiza o valor
        updatedBet.setGame(game);
        updatedBet.setGames(gamesList);
        updatedBet.setGreen(false); // Atualiza o estado

        when(multipleBetRepository.findById(id)).thenReturn(Optional.of(existingBet));
        when(multipleBetRepository.save(existingBet)).thenReturn(existingBet);

        ResponseEntity<String> response = betController.updateMultipleBet(id, updatedBet);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Aposta atualizada com sucesso!", response.getBody());
        assertEquals(100.0, existingBet.getValue()); // Verifica se o valor foi atualizado
        assertEquals(false, existingBet.isGreen()); // Verifica se o estado foi atualizado
        verify(multipleBetRepository, times(1)).findById(id);
        verify(multipleBetRepository, times(1)).save(existingBet);
    }

    @Test
    public void testUpdateMultipleBetNotFound() {
        String id = "6696ad6a9edf9714bddd6b39";
        MultipleBetVO updatedBet = new MultipleBetVO();
        when(multipleBetRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<String> response = betController.updateMultipleBet(id, updatedBet);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Aposta não encontrada.", response.getBody());
        verify(multipleBetRepository, times(1)).findById(id);
        verify(multipleBetRepository, times(0)).save(any(MultipleBetVO.class));
    }

    @Test
    public void testUpdateMultipleBetWithoutAttributes() {
        String id = "6696ad6a9edf9714bddd6b39";
        MultipleBetVO existingBet = new MultipleBetVO();
        existingBet.setValue(50.0);
        existingBet.setOdd(1.5);
        existingBet.setGreen(true);
        when(multipleBetRepository.findById(id)).thenReturn(Optional.of(existingBet));

        // Enviar um objeto vazio para atualização
        MultipleBetVO updatedBet = new MultipleBetVO();

        ResponseEntity<String> response = betController.updateMultipleBet(id, updatedBet);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Aposta atualizada com sucesso!", response.getBody());
        assertEquals(50.0, existingBet.getValue()); // Verifica que o valor não foi alterado
        verify(multipleBetRepository, times(1)).findById(id);
        verify(multipleBetRepository, times(1)).save(existingBet);
    }

    @Test
    public void testUpdateMultipleBetFailure() {
        String id = "6696ad6a9edf9714bddd6b39";
        MultipleBetVO existingBet = new MultipleBetVO();
        when(multipleBetRepository.findById(id)).thenReturn(Optional.of(existingBet));
        MultipleBetVO updatedBet = new MultipleBetVO();

        // Simula uma falha ao salvar
        doThrow(new RuntimeException("Erro ao atualizar")).when(multipleBetRepository).save(existingBet);

        ResponseEntity<String> response = betController.updateMultipleBet(id, updatedBet);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro ao tentar atualizar aposta.", response.getBody());
        verify(multipleBetRepository, times(1)).findById(id);
        verify(multipleBetRepository, times(1)).save(existingBet);
    }

    
}
