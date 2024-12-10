package msp.papaya.controller;

import msp.papaya.model.Score;
import msp.papaya.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {

  @Autowired
  private ScoreService scoreService;

  @GetMapping
  public List<Score> getAllScores() {
    return scoreService.getAllScores();
  }

  @PostMapping
  public Score saveScore(@RequestBody Score score) {
    return scoreService.saveScore(score);
  }

  // 추가적인 엔드포인트 구현
}
