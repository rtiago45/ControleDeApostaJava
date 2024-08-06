package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import model.BetVO;
import model.Game;
import model.MultipleBetVO;
import repository.BetRepository;
import repository.MultipleBetRepository;

@RestController
@RequestMapping("/bets")
@CrossOrigin(origins = "http://localhost:4200")
public class BetController {

	@Autowired
	private BetRepository betRepository;

	@Autowired
	private MultipleBetRepository multipleBetRepository;

	@GetMapping("/all")
	public List<BetVO> getAllBets() {
		return betRepository.findAll();
	}

	@PostMapping("/insertMultiple")
	public ResponseEntity<String> insertMultipleBet(@RequestBody MultipleBetVO multipleBetVO) {
		try {
			multipleBetRepository.insert(multipleBetVO);
			return new ResponseEntity<>("Aposta criada com sucesso!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao tentar inserir aposta múltipla.", HttpStatus.BAD_REQUEST);
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

	@PutMapping("/updateMultipleBet/{id}")
	public ResponseEntity<String> updateMultipleBet(@PathVariable String id, @RequestBody MultipleBetVO multipleBetVO) {
		try {
			Optional<MultipleBetVO> existingBetOptional = multipleBetRepository.findById(id);
			if (existingBetOptional.isPresent()) {
				MultipleBetVO existingBet = existingBetOptional.get();

				if (multipleBetVO.getGame() != null) {
					existingBet.setGame(multipleBetVO.getGame());

					if (multipleBetVO.getGame().getTeams() != null) {
						existingBet.getGame().setTeams(multipleBetVO.getGame().getTeams());
					}

					if (multipleBetVO.getGame().getCompetition() != null) {
						existingBet.getGame().setCompetition(multipleBetVO.getGame().getCompetition());
					}

					// Atualizar o campo entrances como String
					if (multipleBetVO.getGame().getEntrances() != null) {
						existingBet.getGame().setEntrances(multipleBetVO.getGame().getEntrances());
					}
				}

				if (multipleBetVO.getGames() != null) {
					existingBet.setGames(multipleBetVO.getGames());
					for (Game game : multipleBetVO.getGames()) {
						if (game.getTeams() != null) {
							existingBet.getGame().setTeams(game.getTeams());
						}

						if (game.getCompetition() != null) {
							existingBet.getGame().setCompetition(game.getCompetition());
						}

						// Atualizar o campo entrances como String
						if (game.getEntrances() != null) {
							existingBet.getGame().setEntrances(game.getEntrances());
						}
					}
				}

				if (multipleBetVO.getOdd() != 0) {
					existingBet.setOdd(multipleBetVO.getOdd());
				}

				if (multipleBetVO.getValue() != 0) {
					existingBet.setValue(multipleBetVO.getValue());
				}

				if (multipleBetVO.isGreen() != existingBet.isGreen()) {
					existingBet.setGreen(multipleBetVO.isGreen());
				}
				multipleBetRepository.save(existingBet);
				return new ResponseEntity<>("Aposta atualizada com sucesso!", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Aposta não encontrada.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao tentar atualizar aposta: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/insertSimpleBet")
	public ResponseEntity<String> insertSimpleBet(@RequestBody BetVO bet) {
		try {
			BetVO savedBet = betRepository.save(bet);
			return ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body("{\"message\": \"Aposta simples criada com sucesso!\"}");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.contentType(MediaType.APPLICATION_JSON)
					.body("{\"message\": \"Erro ao criar aposta simples: " + e.getMessage() + "\"}");
		}
	}
}
