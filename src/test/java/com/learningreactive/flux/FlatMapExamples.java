package com.learningreactive.flux;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static reactor.core.scheduler.Schedulers.parallel;

public class FlatMapExamples {

  @Test
  public void simpleFlatMap() {
    Flux<String> fluxData = Flux.just(1, 2, 3, 5, 3).flatMap(s -> Flux.fromIterable(convertToList(s))).log();

    StepVerifier.create(fluxData)
      .expectSubscription()
      .expectNextCount(10)
      .verifyComplete();
  }

  @Test
  public void simpleParellelFlatMap() {
    Flux<String> fluxData =
      Flux.just(1, 2, 3, 5, 3,34, 45, 567, 457,23 ,23, 21,12 ,4,234)
        .window(3) // Flux<Flux<Integer>>
        .flatMap(eleOfThree -> eleOfThree.map(this::convertToList)).subscribeOn(parallel()) // Flux<List<String>>
        .flatMap(Flux::fromIterable)   // Flux<String>
        .log();

    StepVerifier.create(fluxData)
      .expectSubscription()
      .expectNextCount(30)
      .verifyComplete();
  }

  @Test
  public void simpleParellelFlatMapSequential() {
    Flux<String> fluxData =
      Flux.just(1, 2, 3, 5, 3,34, 45, 567, 457,23 ,23, 21,12 ,4,234)
        .window(3) // Flux<Flux<Integer>>
        .flatMapSequential(eleOfThree -> eleOfThree.map(this::convertToList)).subscribeOn(parallel()) // Flux<List<String>>
        .flatMap(Flux::fromIterable)   // Flux<String>
        .log();

    StepVerifier.create(fluxData)
      .expectSubscription()
      .expectNextCount(30)
      .verifyComplete();
  }

  private List<String> convertToList(Integer num) {
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return Arrays.asList(String.valueOf(num), "abe");
  }
}
