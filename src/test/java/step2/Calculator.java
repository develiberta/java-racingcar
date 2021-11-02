package step2;

import org.assertj.core.util.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Calculator {
    private final static String DELIMITER = " ";
    private final static String NUMBER_PATTERN = "\\d*$";
    private final static String OPERATOR_PATTERN = "[+\\-*/]";

    public int calculate(String input) {
        if(Strings.isNullOrEmpty(input)) {
            throw new IllegalArgumentException("입력 값은 null이거나 빈값일 수 없습니다.");
        }
        //idx 짝수 숫자 홀수 연산자
        String[] split = input.split(DELIMITER);

        List<Integer> numbers = getNumbers(split);
        List<String> operators = getOperators(split);

        //TODO idx 변수가 애매한거 같은데 해결할 방법이 없을까요?
        AtomicInteger idx = new AtomicInteger();
        return numbers.stream()
                .reduce((left, right) -> {
                    Operation operation = Operation.getOperation(operators.get(idx.getAndIncrement()));
                    return operation.apply(left, right);
                })
                .orElseThrow(IllegalArgumentException::new);
    }

    private List<String> getOperators(String[] split) {
        //TODO stream 으로 해결 할 수 없을까요?
        List<String> operators = new ArrayList<>();
        for (int i = 1; i < split.length; i += 2) {
            isOperator(split[i]);
            operators.add(split[i]);
        }
        return operators;
    }

    private List<Integer> getNumbers(String[] split) {
        //TODO stream 으로 해결 할 수 없을까요?
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < split.length; i += 2) {
            isNumber(split[i]);
            numbers.add(Integer.parseInt(split[i]));
        }
        return numbers;
    }

    /**
     * @param item
     * @throws IllegalArgumentException 빈 값이 오면 발생합니다.
     */
    private void isEmpty(String item) {
        if (Strings.isNullOrEmpty(item)) {
            throw new IllegalArgumentException("빈 값이거나 null 값이 올 수 없습니다.");
        }
    }

    /**
     * @param item
     * @throws IllegalArgumentException 숫자 값을 제외한 input 값이 오면 발생합니다.
     */
    private void isNumber(String item) {
        isEmpty(item);
        if (!item.matches(NUMBER_PATTERN)) {
            throw new IllegalArgumentException("입력 값이 숫자값이 아닙니다.");
        }
    }

    /**
     * @param item
     * @throws IllegalArgumentException 연산자를 제외한 input 값이 오면 발생합니다.
     */
    private void isOperator(String item) {
        isEmpty(item);
        if (!item.matches(OPERATOR_PATTERN)) {
            throw new IllegalArgumentException("입력 값이 연산자가 아닙니다.");
        }
    }


}
