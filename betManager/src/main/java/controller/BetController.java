package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import model.BetVO;
import model.MultipleBetVO;
import repository.BetRepository;

@RestController
@RequestMapping("/bets")
public class BetController {

    @Autowired
    private BetRepository betRepository;

    @GetMapping("/all")
    public List<BetVO> getAllBets() {
        return betRepository.findAll();
    }
    
    @PostMapping("/insertMultiple")
    public ResponseEntity<String> insertMultiple(@RequestBody MultipleBetVO multipleBetVO){
    	try {
    		betRepository.insert(multipleBetVO);
    		return new ResponseEntity<String>("Aposta criada com sucesso!", HttpStatus.OK);
    	} catch (Exception e) {
    		return new ResponseEntity<>("Erro ao tentar inserir aposta multipla.", HttpStatus.BAD_REQUEST);
    	}
    }
    
    @PostMapping("/deleteBet/{id}")
    public ResponseEntity<String> deleteBet(@PathVariable String id) {
        try {
            betRepository.deleteById(id);
            return new ResponseEntity<>("Aposta deletada com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao tentar deletar aposta.", HttpStatus.BAD_REQUEST);
        }
    }

}
