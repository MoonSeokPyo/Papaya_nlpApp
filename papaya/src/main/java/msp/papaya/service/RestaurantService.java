package msp.papaya.service;

import msp.papaya.model.Restaurant;
import msp.papaya.model.Review;
import msp.papaya.model.Score;

import java.util.List;

public interface RestaurantService {
  Restaurant getRestaurantById(Integer id);
  Score getScoreByRestaurantId(Integer restaurantId);
  List<Review> getReviewsByRestaurantId(Integer restaurantId);
}
