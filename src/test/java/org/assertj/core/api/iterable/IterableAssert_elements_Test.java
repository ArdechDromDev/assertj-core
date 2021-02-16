/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2020 the original author or authors.
 */
package org.assertj.core.api.iterable;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.util.AssertionsUtil.expectAssertionError;
import static org.assertj.core.util.FailureMessages.actualIsEmpty;

import java.util.Arrays;

import org.assertj.core.api.AssertFactory;
import org.assertj.core.api.ClassBasedNavigableIterableAssert;
import org.assertj.core.api.IterableAssert;
import org.assertj.core.api.ObjectAssert;
import org.assertj.core.api.StringAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for <code>{@link IterableAssert#elements(int)}</code>.
 *
 * @author Stefano Cordio
 */
@DisplayName("IterableAssert elements(int)")
class IterableAssert_elements_Test {

  private final Iterable<String> iterable = asList("Homer", "Marge", "Lisa", "Bart", "Maggie");

  @Test
  void should_fail_if_iterable_is_empty() {
    // GIVEN
    Iterable<String> iterable = emptyList();
    // WHEN
    AssertionError assertionError = expectAssertionError(() -> assertThat(iterable).elements(1));
    // THEN
    then(assertionError).hasMessage(actualIsEmpty());
  }
  @Test
  void should_pass_allowing_object_assertions_if_iterable_contains_enough_elements() {
    // WHEN
    IterableAssert<String> result = assertThat(iterable).elements(1);
    // THEN
    result.containsExactly("Marge");
  }

  @Test
  void should_pass_allowing_assertions_for_several_elements() {
    // WHEN
    IterableAssert<String> result = assertThat(iterable).elements(1, 2);
    // THEN
    result.containsExactly("Marge", "Lisa");
  }

  @Test
  void should_pass_allowing_assertions_for_several_unordered_elements() {
    // WHEN
    IterableAssert<String> result = assertThat(iterable).elements(2, 1);
    // THEN
    result.containsExactly("Lisa", "Marge");
  }

  @Test
  void should_pass_allowing_assertions_for_repeating_elements() {
  // WHEN
    IterableAssert<String> result = assertThat(iterable).elements(2, 1, 2);
    // THEN
    result.containsExactly("Lisa", "Marge", "Lisa");
  }

  @Test
  void should_fail_if_index_out_of_range() {
    AssertionError assertionError = expectAssertionError(() -> assertThat(iterable).elements(5));
    // THEN
    then(assertionError).hasMessageContaining("out of bound").hasMessageContaining("5");
  }

  @Test
  void should_fail_if_indices_contains_null() {
    AssertionError assertionError = expectAssertionError(() -> assertThat(iterable).elements(2, null));
    // THEN
    then(assertionError).hasMessageContaining("indices must not contain a null value");
  }

  @Test
  void should_fail_if_indices_is_null() {
    AssertionError assertionError = expectAssertionError(() -> assertThat(iterable).elements((Integer[])null));
    // THEN
    then(assertionError).hasMessageContaining("indices must not be null");
  }

  @Test
  void should_fail_if_iterable_is_null() {
    AssertionError assertionError = expectAssertionError(() -> assertThat((Iterable)null).elements(1));
    // THEN
    then(assertionError).hasMessageContaining("Expecting actual not to be null");
  }
}
