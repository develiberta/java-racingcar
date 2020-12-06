package com.nextstep.racingcar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class ParserTest {
    @DisplayName("자동차 이름 5자 초과 체크")
    @Test
    public void isInvalidName() {
        String name = "aaaaaa";
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Parser.parseCarNames(name))
                .withMessage(name + " is over limit");
    }

    @DisplayName("이름 , 단위로 분리 기능")
    @ParameterizedTest
    @MethodSource
    public void parseCarNames(String source, String[] expected){
        List<CarName> names = Parser.parseCarNames(source);
        int idx = 0;
        for (CarName name : names) {
            assertThat(name.getName()).isEqualTo(expected[idx++]);
        }
    }

    private static Stream<Arguments> parseCarNames(){
        return Stream.of(
                Arguments.of("aaa,bbb,ccc", new String[]{"aaa","bbb","ccc"})
        );
    }
}