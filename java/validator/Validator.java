package validator;

public interface Validator<E> {
    void validate(E element) throws ValidationException;
}
