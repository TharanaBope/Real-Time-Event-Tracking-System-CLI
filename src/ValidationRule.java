/**
 * Interface for validation rules.
 * This interface is used to define custom validation logic for integer inputs.
 * It contains a single method `isValid` that checks if a given value meets the validation criteria.
 *
 * Usage:
 * Implement this interface to create specific validation rules for input values.
 * Example:
 * - To check if a value is positive, the implementation would return `value > 0`.
 * - To check if a value falls within a range, the implementation would return `value >= min && value <= max`.
 */
public interface ValidationRule {
    //validates the given integer value
    boolean isValid(int value);
}
