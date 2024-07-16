package controller;

import model.BetVO;
import repository.BetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
