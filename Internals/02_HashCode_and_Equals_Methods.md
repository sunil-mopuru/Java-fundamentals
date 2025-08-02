# hashCode() and equals() Methods in Java

The `hashCode()` and `equals()` methods are fundamental to Java's object-oriented design and are crucial for proper functioning of hash-based collections like HashMap, HashSet, and Hashtable. Understanding these methods is essential for writing correct and efficient Java code.

## Table of Contents

1. [Introduction to hashCode() and equals()](#introduction-to-hashcode-and-equals)
2. [The equals() Method](#the-equals-method)
3. [The hashCode() Method](#the-hashcode-method)
4. [The Contract Between hashCode() and equals()](#the-contract-between-hashcode-and-equals)
5. [Default Implementation](#default-implementation)
6. [Custom Implementation Guidelines](#custom-implementation-guidelines)
7. [Common Mistakes and Pitfalls](#common-mistakes-and-pitfalls)
8. [Best Practices](#best-practices)
9. [Real-World Examples](#real-world-examples)
10. [Performance Considerations](#performance-considerations)
11. [Interview Questions](#interview-questions)
12. [Summary](#summary)

## Introduction to hashCode() and equals()

### What are hashCode() and equals()?

- **equals()**: Determines if two objects are logically equal
- **hashCode()**: Returns an integer hash code for the object
- **Relationship**: Objects that are equal must have the same hash code

### Why are they important?

1. **Hash-based Collections**: HashMap, HashSet, Hashtable rely on these methods
2. **Object Comparison**: Used for comparing objects in collections
3. **Performance**: Proper implementation affects collection performance
4. **Correctness**: Incorrect implementation can cause bugs

## The equals() Method

### Purpose

The `equals()` method is used to determine if two objects are logically equal, not just reference equal.

### Default Implementation

```java
public boolean equals(Object obj) {
    return (this == obj);
}
```

### Custom equals() Implementation

```java
@Override
public boolean equals(Object obj) {
    // 1. Check if same reference
    if (this == obj) return true;

    // 2. Check if null
    if (obj == null) return false;

    // 3. Check if same class
    if (getClass() != obj.getClass()) return false;

    // 4. Cast to same type
    Person other = (Person) obj;

    // 5. Compare fields
    return Objects.equals(name, other.name) && age == other.age;
}
```

### equals() Contract

1. **Reflexive**: `x.equals(x)` must return `true`
2. **Symmetric**: `x.equals(y)` must return same as `y.equals(x)`
3. **Transitive**: If `x.equals(y)` and `y.equals(z)`, then `x.equals(z)`
4. **Consistent**: Multiple calls must return same result
5. **Null**: `x.equals(null)` must return `false`

## The hashCode() Method

### Purpose

The `hashCode()` method returns an integer hash code for the object, used by hash-based collections.

### Default Implementation

```java
public native int hashCode();
```

### Custom hashCode() Implementation

```java
@Override
public int hashCode() {
    return Objects.hash(name, age, email);
}
```

### hashCode() Contract

1. **Consistency**: Same object must return same hash code
2. **Equality**: Equal objects must have equal hash codes
3. **Performance**: Should be fast to compute
4. **Distribution**: Should distribute hash codes evenly

## The Contract Between hashCode() and equals()

### The Golden Rule

**If two objects are equal according to equals(), they must have the same hash code.**

### Implications

```java
// If a.equals(b) returns true, then:
a.hashCode() == b.hashCode()  // Must be true

// However, the reverse is NOT required:
// If a.hashCode() == b.hashCode(), a.equals(b) can be false
```

### Why This Contract Matters

1. **HashMap Performance**: Equal objects must hash to same bucket
2. **HashSet Correctness**: Prevents duplicate objects
3. **Collection Behavior**: Ensures proper functioning of hash-based collections

## Default Implementation

### Object Class Defaults

```java
// Default equals() - reference equality
public boolean equals(Object obj) {
    return (this == obj);
}

// Default hashCode() - memory address based
public native int hashCode();
```

### Problems with Default Implementation

```java
class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // No equals() and hashCode() overrides
}

// Usage
Person p1 = new Person("Alice", 25);
Person p2 = new Person("Alice", 25);

System.out.println(p1.equals(p2));  // false (reference equality)
System.out.println(p1.hashCode() == p2.hashCode());  // false
```

## Custom Implementation Guidelines

### equals() Implementation Steps

1. **Reference Check**: `if (this == obj) return true;`
2. **Null Check**: `if (obj == null) return false;`
3. **Class Check**: `if (getClass() != obj.getClass()) return false;`
4. **Cast**: Cast to the correct type
5. **Field Comparison**: Compare all relevant fields

### hashCode() Implementation Steps

1. **Use Objects.hash()**: For simple cases
2. **Manual Implementation**: For complex cases
3. **Include All Fields**: That are used in equals()
4. **Use Prime Numbers**: For better distribution

### Example Implementation

```java
public class Person {
    private String name;
    private int age;
    private String email;

    public Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Person person = (Person) obj;
        return age == person.age &&
               Objects.equals(name, person.name) &&
               Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, email);
    }
}
```

## Common Mistakes and Pitfalls

### 1. Inconsistent equals() and hashCode()

```java
// WRONG: Only overriding equals()
@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    Person person = (Person) obj;
    return Objects.equals(name, person.name);
}

// Missing hashCode() override - violates contract!
```

### 2. Using Mutable Fields

```java
// WRONG: Using mutable fields in hashCode()
public class Person {
    private String name;
    private int age;
    private List<String> hobbies; // Mutable field

    @Override
    public int hashCode() {
        return Objects.hash(name, age, hobbies); // BAD: hobbies can change
    }
}
```

### 3. Ignoring Case Sensitivity

```java
// WRONG: Inconsistent case handling
@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    Person person = (Person) obj;
    return name.equals(person.name); // Case sensitive
}

@Override
public int hashCode() {
    return name.toLowerCase().hashCode(); // Case insensitive - INCONSISTENT!
}
```

### 4. Not Handling Null Fields

```java
// WRONG: Can throw NullPointerException
@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    Person person = (Person) obj;
    return name.equals(person.name); // Throws NPE if name is null
}
```

## Best Practices

### 1. Use Objects.equals() for Null-Safe Comparison

```java
@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    Person person = (Person) obj;
    return Objects.equals(name, person.name) && // Null-safe
           Objects.equals(email, person.email);
}
```

### 2. Use Objects.hash() for Simple Cases

```java
@Override
public int hashCode() {
    return Objects.hash(name, age, email);
}
```

### 3. Use Immutable Objects as Keys

```java
// GOOD: Immutable key
public final class PersonKey {
    private final String name;
    private final int age;

    public PersonKey(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // No setters - immutable
}
```

### 4. Consider Using Builder Pattern

```java
public class Person {
    private final String name;
    private final int age;
    private final String email;

    private Person(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.email = builder.email;
    }

    // equals() and hashCode() methods...

    public static class Builder {
        private String name;
        private int age;
        private String email;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }
}
```

## Real-World Examples

### Example 1: Employee Class

```java
public class Employee {
    private final String employeeId;
    private String name;
    private String department;
    private double salary;

    public Employee(String employeeId, String name, String department, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Employee employee = (Employee) obj;
        return Objects.equals(employeeId, employee.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId);
    }

    // Getters and setters...
}
```

### Example 2: Complex Object with Collections

```java
public class Student {
    private final String studentId;
    private String name;
    private Set<String> courses;
    private Map<String, Double> grades;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.courses = new HashSet<>();
        this.grades = new HashMap<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Student student = (Student) obj;
        return Objects.equals(studentId, student.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }

    // Note: Only studentId is used in equals/hashCode
    // Mutable fields (courses, grades) are not included
}
```

### Example 3: Value Object Pattern

```java
public class Money {
    private final BigDecimal amount;
    private final Currency currency;

    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Money money = (Money) obj;
        return Objects.equals(amount, money.amount) &&
               Objects.equals(currency, money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    // Immutable - no setters
    public BigDecimal getAmount() { return amount; }
    public Currency getCurrency() { return currency; }
}
```

## Performance Considerations

### 1. Hash Code Distribution

```java
// BAD: Poor distribution
@Override
public int hashCode() {
    return 1; // All objects have same hash code
}

// GOOD: Better distribution
@Override
public int hashCode() {
    return Objects.hash(name, age, email);
}
```

### 2. Caching Hash Code

```java
public class ExpensiveObject {
    private final String data;
    private int cachedHashCode; // Cache for performance
    private boolean hashCodeComputed = false;

    @Override
    public int hashCode() {
        if (!hashCodeComputed) {
            cachedHashCode = Objects.hash(data);
            hashCodeComputed = true;
        }
        return cachedHashCode;
    }
}
```

### 3. Lazy Initialization

```java
public class LazyHashCode {
    private final List<String> items;
    private volatile int cachedHashCode;

    @Override
    public int hashCode() {
        int result = cachedHashCode;
        if (result == 0) {
            result = Objects.hash(items);
            cachedHashCode = result;
        }
        return result;
    }
}
```

## Interview Questions

### Q1: What happens if you override equals() but not hashCode()?

**Answer**: This violates the contract. Equal objects must have equal hash codes. This can cause:

- Duplicate objects in HashSet
- Incorrect behavior in HashMap
- Performance degradation

### Q2: Can two objects have the same hash code but not be equal?

**Answer**: Yes! This is called a hash collision. Equal objects must have equal hash codes, but the reverse is not required.

### Q3: What's the difference between == and equals()?

**Answer**:

- `==` compares object references (memory addresses)
- `equals()` compares object content (logical equality)

### Q4: How do you handle null fields in equals()?

**Answer**: Use `Objects.equals()` or explicit null checks:

```java
// Using Objects.equals()
return Objects.equals(name, other.name);

// Explicit null check
return (name == null ? other.name == null : name.equals(other.name));
```

### Q5: What's the best way to implement hashCode()?

**Answer**: Use `Objects.hash()` for simple cases:

```java
@Override
public int hashCode() {
    return Objects.hash(field1, field2, field3);
}
```

## Summary

### Key Points

1. **Contract**: Equal objects must have equal hash codes
2. **Consistency**: Both methods must be consistent
3. **Null Safety**: Handle null fields properly
4. **Performance**: Consider caching for expensive computations
5. **Immutability**: Use immutable objects when possible

### Best Practices

1. **Always override both**: equals() and hashCode() together
2. **Use Objects utility methods**: Objects.equals() and Objects.hash()
3. **Include all relevant fields**: In both methods
4. **Handle null values**: Safely
5. **Test thoroughly**: With unit tests
6. **Consider immutability**: For better performance and correctness

### Common Patterns

```java
@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    MyClass other = (MyClass) obj;
    return Objects.equals(field1, other.field1) &&
           Objects.equals(field2, other.field2);
}

@Override
public int hashCode() {
    return Objects.hash(field1, field2);
}
```

Understanding `hashCode()` and `equals()` is crucial for writing correct Java code, especially when working with collections. Proper implementation ensures correct behavior and good performance of hash-based data structures.
