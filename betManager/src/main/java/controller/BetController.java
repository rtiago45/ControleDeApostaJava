package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import model.Person;
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
import repository.PersonRepository;
import service.PersonService;
import service.SequenceGeneratorService;

@RestController
@RequestMapping("/bets")
@CrossOrigin(origins = "http://localhost:4200")
public class BetController {

	@Autowired
	private BetRepository betRepository;

	@Autowired
	private MultipleBetRepository multipleBetRepository;

	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	@Autowired
	private PersonService personService;

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


				multipleBetRepository.save(existingBet);
				return new ResponseEntity<>("Aposta atualizada com sucesso!", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Aposta não encontrada.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao tentar atualizar aposta: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/lastBetId")
	public ResponseEntity<String> getLastBetId() {
		try {
			List<BetVO> bets = betRepository.findAll();
			if (bets.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.contentType(MediaType.APPLICATION_JSON)
						.body("No bets found");
			}
			BetVO lastBet = bets.stream()
					.max((bet1, bet2) -> Long.compare(Long.parseLong(bet1.getId()), Long.parseLong(bet2.getId())))
					.orElseThrow();
			return ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(lastBet.getId());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.contentType(MediaType.APPLICATION_JSON)
					.body("Error retrieving last bet ID");
		}
	}

	@GetMapping("/averageOdd")
	public ResponseEntity<String> getAverageOdd() {
		try {
			List<BetVO> bets = betRepository.findAll();
			if (bets.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.contentType(MediaType.APPLICATION_JSON)
						.body(null);
			}
			double averageOdd = bets.stream()
					.mapToDouble(BetVO::getOdd)
					.average()
					.orElse(0.0);

			// Add 1 to the average odd
			averageOdd += 1;

			DecimalFormat df = new DecimalFormat("#.##");
			String formattedAverageOdd = df.format(averageOdd).replace(",", ".");

			return ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(formattedAverageOdd);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.contentType(MediaType.APPLICATION_JSON)
					.body(null);
		}
	}

	@PostMapping("/insertSimpleBet")
	public ResponseEntity<BetVO> insertSimpleBet(@RequestBody BetVO bet) {
		try {
			// Gerar ID para a aposta
			long generatedId = sequenceGeneratorService.generateSequence(BetVO.SEQUENCE_NAME);
			bet.setId(Long.toString(generatedId));

			if (bet.getDate() == null) {
				bet.setDate(new Date());
			}

			// Salvar a aposta
			BetVO savedBet = betRepository.save(bet);

			// Calcular o valor ganho ou perdido
			double winnings;
			if (bet.isGreenOrRed()) {
				winnings = bet.getValue() * bet.getOdd();
				System.out.println("Winnings: " + winnings);
			} else {
				Optional<Person> personOptional = personService.findById("1");
				if (personOptional.isPresent()) {
					Person person = personOptional.get();
					winnings = person.getSaldoAtual() - bet.getValue();
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND)
							.contentType(MediaType.APPLICATION_JSON)
							.body(null);
				}
			}

			try {
				personService.updateSaldoAtual("1", winnings);
			} catch (RuntimeException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.contentType(MediaType.APPLICATION_JSON)
						.body(null);
			}

			return ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(savedBet);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.contentType(MediaType.APPLICATION_JSON)
					.body(null);
		}
	}
}