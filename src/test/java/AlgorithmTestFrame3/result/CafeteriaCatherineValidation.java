package AlgorithmTestFrame3.result;

import java.util.Objects;

public class CafeteriaCatherineValidation implements OJUnitValidation{
    private boolean correctness;
    private String message;
    @Override
    public boolean getCorrectness() {
        return correctness;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public boolean isCorrectness() {
        return correctness;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CafeteriaCatherineValidation{" +
                "correctness=" + correctness +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CafeteriaCatherineValidation that = (CafeteriaCatherineValidation) o;
        return correctness == that.correctness && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(correctness, message);
    }

    public CafeteriaCatherineValidation(boolean correctness, String message) {
        this.correctness = correctness;
        this.message = message;
    }
}
