package com.learningreactive.flux;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class FactoryExamples {

  @Test
  public void fluxFromIterable() {
    List<Integer> integerList = Arrays.asList(1, 3, 4, 6, 4, 2);
    Flux<Integer> fluxStream = Flux.fromIterable(integerList);

    StepVerifier.create(fluxStream)
      .expectNextCount(6)
      .verifyComplete();
  }

  @Test
  public void fluxFromArray() {
    Integer[] integers = {1, 2, 34, 34, 34};

    Flux<Integer> fluxStream = Flux.fromArray(integers);
    StepVerifier.create(fluxStream)
      .expectNextCount(5)
      .verifyComplete();
  }

  @Test
  public void fluxFromStream() {
    List<Integer> integerList = Arrays.asList(1, 3, 4, 6, 4, 2);
    Flux<Integer> integerFlux = Flux.fromStream(integerList.stream());

    StepVerifier.create(integerFlux)
      .expectNextCount(6)
      .verifyComplete();
  }


  @Test
  public void fluxJust() {
    Flux<Integer> integerFlux = Flux.just(1,2);

    StepVerifier.create(integerFlux)
      .expectNextCount(2)
      .verifyComplete();
  }


  @Test
  public void fluxRange() {
    Flux<Integer> range = Flux.range(20, 10);
    StepVerifier.create(range)
      .expectNext(20,21,22,23,24,25,26,27,28,29)
      .verifyComplete();

  }

  @Test
  public void fluxInfinite() throws InterruptedException {
    Flux<Long> range = Flux.interval(Duration.ofSeconds(200))
      .log();

    Thread.sleep(2000);
  }


  @Test
  public void fluxBackPressure() {
    Flux<Integer> range = Flux.range(20, 10);
    StepVerifier.create(range)
      .expectSubscription()
      .thenRequest(1)
      .expectNext(20)
      .thenRequest(2)
      .expectNext(21, 22)
      .thenCancel()
      .verify();

  }
}
