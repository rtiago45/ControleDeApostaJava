package controller;

import model.BetVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.BetRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/bets")
public class BetController {

    @Autowired
    private BetRepository betRepository;

    @GetMapping("/all")
    public List<BetVO> getAllBets() {
        return betRepository.findAll();
    }

    @PostMapping("/insertSimpleBet")
    public ResponseEntity<String> insertBetSimpleBet(@RequestBody BetVO bet) {
        try {
            BetVO savedBet = betRepository.save(bet);
            return new ResponseEntity<String>("Aposta simples criada com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao criar aposta simples: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
