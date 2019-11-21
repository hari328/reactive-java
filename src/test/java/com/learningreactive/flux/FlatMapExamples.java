package com.learningreactive.flux;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FlatMapExamples {

  @Test
  public void simpleFlatMap() {
    Flux<String> fluxData = Flux.just(1, 2, 3, 5, 3).flatMap(s -> Flux.fromIterable(convertToList(s))).log();

    StepVerifier.create(fluxData)
      .expectSubscription()
      .expectNextCount(10)
      .verifyComplete();
  }

  private List<String> convertToList(Integer num) {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return Arrays.asList(String.valueOf(num), "abe");
  }
}
