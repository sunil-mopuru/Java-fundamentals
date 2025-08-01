import java.util.Scanner;

/**
 * Operators and Control Flow - Practical Examples
 * This class demonstrates various operators, control flow statements,
 * and their practical applications in Java.
 */
public class OperatorsAndControlFlow {
    
    public static void main(String[] args) {
        System.out.println("=== Operators and Control Flow Examples ===\n");
        
        // 1. Arithmetic Operators
        demonstrateArithmeticOperators();
        
        // 2. Comparison and Logical Operators
        demonstrateComparisonAndLogicalOperators();
        
        // 3. Assignment and Increment Operators
        demonstrateAssignmentOperators();
        
        // 4. Conditional Statements
        demonstrateConditionalStatements();
        
        // 5. Looping Statements
        demonstrateLoopingStatements();
        
        // 6. Break and Continue
        demonstrateBreakAndContinue();
        
        // 7. Practical Calculator Example
        demonstrateCalculator();
    }
    
    /**
     * Demonstrates arithmetic operators with practical examples
     */
    public static void demonstrateArithmeticOperators() {
        System.out.println("1. ARITHMETIC OPERATORS:");
        System.out.println("------------------------");
        
        int a = 15, b = 4;
        
        // Basic arithmetic operations
        int sum = a + b;
        int difference = a - b;
        int product = a * b;
        int quotient = a / b;
        int remainder = a % b;
        
        System.out.println("a = " + a + ", b = " + b);
        System.out.println("Addition: " + a + " + " + b + " = " + sum);
        System.out.println("Subtraction: " + a + " - " + b + " = " + difference);
        System.out.println("Multiplication: " + a + " * " + b + " = " + product);
        System.out.println("Division: " + a + " / " + b + " = " + quotient + " (integer division)");
        System.out.println("Modulus: " + a + " % " + b + " = " + remainder);
        
        // Floating-point arithmetic
        double x = 15.0, y = 4.0;
        double floatQuotient = x / y;
        System.out.println("Floating-point division: " + x + " / " + y + " = " + floatQuotient);
        
        // Operator precedence
        int result1 = 2 + 3 * 4;      // 2 + (3 * 4) = 14
        int result2 = (2 + 3) * 4;    // (2 + 3) * 4 = 20
        
        System.out.println("\nOperator Precedence:");
        System.out.println("2 + 3 * 4 = " + result1 + " (multiplication first)");
        System.out.println("(2 + 3) * 4 = " + result2 + " (parentheses first)");
        
        System.out.println();
    }
    
    /**
     * Demonstrates comparison and logical operators
     */
    public static void demonstrateComparisonAndLogicalOperators() {
        System.out.println("2. COMPARISON AND LOGICAL OPERATORS:");
        System.out.println("-----------------------------------");
        
        int age = 25;
        double salary = 50000.0;
        boolean hasExperience = true;
        
        // Comparison operators
        boolean isAdult = age >= 18;
        boolean isHighSalary = salary > 45000;
        boolean isYoung = age < 30;
        boolean isExperienced = hasExperience == true;
        
        System.out.println("Age: " + age + ", Salary: " + salary + ", Experience: " + hasExperience);
        System.out.println("Is adult (>= 18): " + isAdult);
        System.out.println("Is high salary (> 45000): " + isHighSalary);
        System.out.println("Is young (< 30): " + isYoung);
        System.out.println("Is experienced (== true): " + isExperienced);
        
        // Logical operators
        boolean canApplyForJob = isAdult && isHighSalary;
        boolean canApplyForInternship = isYoung || hasExperience;
        boolean cannotApply = !isAdult;
        
        System.out.println("\nLogical Operations:");
        System.out.println("Can apply for job (adult AND high salary): " + canApplyForJob);
        System.out.println("Can apply for internship (young OR experienced): " + canApplyForInternship);
        System.out.println("Cannot apply (NOT adult): " + cannotApply);
        
        // Short-circuit evaluation
        System.out.println("\nShort-circuit Evaluation:");
        boolean result1 = false && someExpensiveOperation();
        boolean result2 = true || someExpensiveOperation();
        System.out.println("false && expensive(): " + result1 + " (expensive() not called)");
        System.out.println("true || expensive(): " + result2 + " (expensive() not called)");
        
        System.out.println();
    }
    
    /**
     * Demonstrates assignment and increment operators
     */
    public static void demonstrateAssignmentOperators() {
        System.out.println("3. ASSIGNMENT AND INCREMENT OPERATORS:");
        System.out.println("-------------------------------------");
        
        // Basic assignment
        int value = 10;
        System.out.println("Initial value: " + value);
        
        // Compound assignment operators
        value += 5;  // value = value + 5
        System.out.println("After += 5: " + value);
        
        value -= 3;  // value = value - 3
        System.out.println("After -= 3: " + value);
        
        value *= 2;  // value = value * 2
        System.out.println("After *= 2: " + value);
        
        value /= 4;  // value = value / 4
        System.out.println("After /= 4: " + value);
        
        value %= 3;  // value = value % 3
        System.out.println("After %= 3: " + value);
        
        // Increment and decrement operators
        System.out.println("\nIncrement/Decrement Operators:");
        
        int x = 5;
        System.out.println("Initial x: " + x);
        
        int y = ++x;  // Pre-increment: x becomes 6, y gets 6
        System.out.println("After y = ++x: x = " + x + ", y = " + y);
        
        int z = x++;  // Post-increment: z gets 6, x becomes 7
        System.out.println("After z = x++: x = " + x + ", z = " + z);
        
        int w = --x;  // Pre-decrement: x becomes 6, w gets 6
        System.out.println("After w = --x: x = " + x + ", w = " + w);
        
        int v = x--;  // Post-decrement: v gets 6, x becomes 5
        System.out.println("After v = x--: x = " + x + ", v = " + v);
        
        System.out.println();
    }
    
    /**
     * Demonstrates conditional statements
     */
    public static void demonstrateConditionalStatements() {
        System.out.println("4. CONDITIONAL STATEMENTS:");
        System.out.println("---------------------------");
        
        // if-else statement
        int score = 85;
        System.out.println("Score: " + score);
        
        if (score >= 90) {
            System.out.println("Grade: A (Excellent)");
        } else if (score >= 80) {
            System.out.println("Grade: B (Good)");
        } else if (score >= 70) {
            System.out.println("Grade: C (Average)");
        } else if (score >= 60) {
            System.out.println("Grade: D (Below Average)");
        } else {
            System.out.println("Grade: F (Fail)");
        }
        
        // switch statement
        System.out.println("\nSwitch Statement Example:");
        int dayOfWeek = 3;
        
        switch (dayOfWeek) {
            case 1:
                System.out.println("Monday - Start of work week");
                break;
            case 2:
                System.out.println("Tuesday - Second day");
                break;
            case 3:
                System.out.println("Wednesday - Midweek");
                break;
            case 4:
                System.out.println("Thursday - Almost weekend");
                break;
            case 5:
                System.out.println("Friday - TGIF!");
                break;
            case 6:
            case 7:
                System.out.println("Weekend - Time to relax!");
                break;
            default:
                System.out.println("Invalid day of week");
        }
        
        // Nested if statements
        System.out.println("\nNested If Example:");
        int age = 25;
        boolean hasLicense = true;
        boolean hasInsurance = false;
        
        if (age >= 18) {
            if (hasLicense) {
                if (hasInsurance) {
                    System.out.println("You can drive legally");
                } else {
                    System.out.println("You need insurance to drive");
                }
            } else {
                System.out.println("You need a license to drive");
            }
        } else {
            System.out.println("You are too young to drive");
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates looping statements
     */
    public static void demonstrateLoopingStatements() {
        System.out.println("5. LOOPING STATEMENTS:");
        System.out.println("----------------------");
        
        // for loop
        System.out.println("For Loop (1 to 5):");
        for (int i = 1; i <= 5; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        
        // for loop with step
        System.out.println("For Loop (even numbers 2 to 10):");
        for (int i = 2; i <= 10; i += 2) {
            System.out.print(i + " ");
        }
        System.out.println();
        
        // while loop
        System.out.println("While Loop (countdown from 5):");
        int count = 5;
        while (count > 0) {
            System.out.print(count + " ");
            count--;
        }
        System.out.println();
        
        // do-while loop
        System.out.println("Do-While Loop (at least once):");
        int number = 1;
        do {
            System.out.print(number + " ");
            number++;
        } while (number <= 3);
        System.out.println();
        
        // Enhanced for loop (for-each)
        System.out.println("Enhanced For Loop (array elements):");
        String[] fruits = {"Apple", "Banana", "Orange", "Grape"};
        for (String fruit : fruits) {
            System.out.print(fruit + " ");
        }
        System.out.println();
        
        // Nested loops
        System.out.println("Nested Loops (multiplication table 1-3):");
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                System.out.print(i * j + "\t");
            }
            System.out.println();
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates break and continue statements
     */
    public static void demonstrateBreakAndContinue() {
        System.out.println("6. BREAK AND CONTINUE STATEMENTS:");
        System.out.println("--------------------------------");
        
        // Break example
        System.out.println("Break Example (stop at 5):");
        for (int i = 1; i <= 10; i++) {
            if (i == 5) {
                break;  // Exit loop when i equals 5
            }
            System.out.print(i + " ");
        }
        System.out.println();
        
        // Continue example
        System.out.println("Continue Example (skip even numbers):");
        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 0) {
                continue;  // Skip even numbers
            }
            System.out.print(i + " ");
        }
        System.out.println();
        
        // Break with labels
        System.out.println("Break with Labels (exit nested loop):");
        outerLoop: for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if (i == 2 && j == 2) {
                    break outerLoop;  // Exit both loops
                }
                System.out.print("(" + i + "," + j + ") ");
            }
            System.out.println();
        }
        
        System.out.println();
    }
    
    /**
     * Demonstrates a practical calculator using operators and control flow
     */
    public static void demonstrateCalculator() {
        System.out.println("7. PRACTICAL CALCULATOR EXAMPLE:");
        System.out.println("--------------------------------");
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter first number: ");
        double num1 = scanner.nextDouble();
        
        System.out.print("Enter second number: ");
        double num2 = scanner.nextDouble();
        
        System.out.print("Enter operation (+, -, *, /, %): ");
        String operation = scanner.next();
        
        double result = 0;
        boolean validOperation = true;
        
        switch (operation) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    System.out.println("Error: Division by zero!");
                    validOperation = false;
                }
                break;
            case "%":
                if (num2 != 0) {
                    result = num1 % num2;
                } else {
                    System.out.println("Error: Modulus by zero!");
                    validOperation = false;
                }
                break;
            default:
                System.out.println("Error: Invalid operation!");
                validOperation = false;
        }
        
        if (validOperation) {
            System.out.println("Result: " + num1 + " " + operation + " " + num2 + " = " + result);
        }
        
        scanner.close();
        System.out.println("\n=== End of Operators and Control Flow Examples ===");
    }
    
    /**
     * Helper method to demonstrate short-circuit evaluation
     */
    private static boolean someExpensiveOperation() {
        System.out.println("  Expensive operation executed!");
        return true;
    }
} 