package hashcode_equals;

import java.util.*;

/**
 * Comprehensive examples demonstrating hashCode() and equals() methods
 * with real-world scenarios and common pitfalls
 */
public class HashCodeEqualsExamples {
    
    public static void main(String[] args) {
        System.out.println("=== hashCode() and equals() Examples ===\n");
        
        // Example 1: Basic equals() and hashCode() demonstration
        basicEqualsHashCodeDemo();
        
        // Example 2: Default implementation problems
        defaultImplementationProblems();
        
        // Example 3: Correct implementation
        correctImplementationDemo();
        
        // Example 4: Common mistakes and pitfalls
        commonMistakesDemo();
        
        // Example 5: Performance comparison
        performanceComparison();
        
        // Example 6: Real-world scenarios
        realWorldScenarios();
        
        // Example 7: Advanced patterns
        advancedPatterns();
        
        // Example 8: Testing equals() and hashCode()
        testingEqualsHashCode();
    }
    
    /**
     * Example 1: Basic equals() and hashCode() demonstration
     */
    public static void basicEqualsHashCodeDemo() {
        System.out.println("1. Basic equals() and hashCode() Demonstration:");
        System.out.println("-----------------------------------------------");
        
        // Create two objects with same content
        Person p1 = new Person("Alice", 25, "alice@email.com");
        Person p2 = new Person("Alice", 25, "alice@email.com");
        Person p3 = new Person("Bob", 30, "bob@email.com");
        
        System.out.println("Person 1: " + p1);
        System.out.println("Person 2: " + p2);
        System.out.println("Person 3: " + p3);
        
        // Test equals()
        System.out.println("\nEquals Tests:");
        System.out.println("p1.equals(p2): " + p1.equals(p2));
        System.out.println("p2.equals(p1): " + p2.equals(p1)); // Symmetric
        System.out.println("p1.equals(p3): " + p1.equals(p3));
        System.out.println("p1.equals(p1): " + p1.equals(p1)); // Reflexive
        System.out.println("p1.equals(null): " + p1.equals(null));
        
        // Test hashCode()
        System.out.println("\nHashCode Tests:");
        System.out.println("p1.hashCode(): " + p1.hashCode());
        System.out.println("p2.hashCode(): " + p2.hashCode());
        System.out.println("p3.hashCode(): " + p3.hashCode());
        System.out.println("p1.hashCode() == p2.hashCode(): " + (p1.hashCode() == p2.hashCode()));
        System.out.println("p1.hashCode() == p3.hashCode(): " + (p1.hashCode() == p3.hashCode()));
        
        // Test contract
        System.out.println("\nContract Verification:");
        System.out.println("Equal objects have equal hash codes: " + 
            (p1.equals(p2) == (p1.hashCode() == p2.hashCode())));
        System.out.println();
    }
    
    /**
     * Example 2: Default implementation problems
     */
    public static void defaultImplementationProblems() {
        System.out.println("2. Default Implementation Problems:");
        System.out.println("----------------------------------");
        
        // Class without equals() and hashCode() overrides
        class DefaultPerson {
            private String name;
            private int age;
            
            public DefaultPerson(String name, int age) {
                this.name = name;
                this.age = age;
            }
            
            @Override
            public String toString() {
                return "DefaultPerson{name='" + name + "', age=" + age + "}";
            }
        }
        
        DefaultPerson dp1 = new DefaultPerson("Alice", 25);
        DefaultPerson dp2 = new DefaultPerson("Alice", 25);
        
        System.out.println("DefaultPerson 1: " + dp1);
        System.out.println("DefaultPerson 2: " + dp2);
        
        // Problems with default implementation
        System.out.println("\nProblems with Default Implementation:");
        System.out.println("dp1.equals(dp2): " + dp1.equals(dp2)); // false (reference equality)
        System.out.println("dp1.hashCode() == dp2.hashCode(): " + (dp1.hashCode() == dp2.hashCode())); // false
        
        // Problems with collections
        Set<DefaultPerson> set = new HashSet<>();
        set.add(dp1);
        set.add(dp2);
        System.out.println("Set size (should be 1, but is): " + set.size()); // 2 (duplicates!)
        
        Map<DefaultPerson, String> map = new HashMap<>();
        map.put(dp1, "Value1");
        map.put(dp2, "Value2");
        System.out.println("Map size (should be 1, but is): " + map.size()); // 2 (duplicates!)
        System.out.println();
    }
    
    /**
     * Example 3: Correct implementation
     */
    public static void correctImplementationDemo() {
        System.out.println("3. Correct Implementation Demo:");
        System.out.println("------------------------------");
        
        // Using our properly implemented Person class
        Person p1 = new Person("Alice", 25, "alice@email.com");
        Person p2 = new Person("Alice", 25, "alice@email.com");
        Person p3 = new Person("Bob", 30, "bob@email.com");
        
        // Correct behavior with collections
        Set<Person> set = new HashSet<>();
        set.add(p1);
        set.add(p2);
        set.add(p3);
        System.out.println("Set size (correct): " + set.size()); // 2 (no duplicates)
        
        Map<Person, String> map = new HashMap<>();
        map.put(p1, "Value1");
        map.put(p2, "Value2"); // Overwrites p1's value
        map.put(p3, "Value3");
        System.out.println("Map size (correct): " + map.size()); // 2
        System.out.println("Map value for p1: " + map.get(p1)); // Value2
        System.out.println("Map value for p2: " + map.get(p2)); // Value2
        System.out.println("Map value for p3: " + map.get(p3)); // Value3
        
        // Test with different Person objects with same content
        Person p4 = new Person("Alice", 25, "alice@email.com");
        System.out.println("Map value for p4 (same content): " + map.get(p4)); // Value2
        System.out.println();
    }
    
    /**
     * Example 4: Common mistakes and pitfalls
     */
    public static void commonMistakesDemo() {
        System.out.println("4. Common Mistakes and Pitfalls:");
        System.out.println("--------------------------------");
        
        // Mistake 1: Only overriding equals()
        class OnlyEqualsPerson {
            private String name;
            private int age;
            
            public OnlyEqualsPerson(String name, int age) {
                this.name = name;
                this.age = age;
            }
            
            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (obj == null || getClass() != obj.getClass()) return false;
                
                OnlyEqualsPerson other = (OnlyEqualsPerson) obj;
                return age == other.age && Objects.equals(name, other.name);
            }
            
            // Missing hashCode() override - VIOLATES CONTRACT!
        }
        
        OnlyEqualsPerson oep1 = new OnlyEqualsPerson("Alice", 25);
        OnlyEqualsPerson oep2 = new OnlyEqualsPerson("Alice", 25);
        
        System.out.println("OnlyEqualsPerson - equals(): " + oep1.equals(oep2)); // true
        System.out.println("OnlyEqualsPerson - hashCode(): " + (oep1.hashCode() == oep2.hashCode())); // false (violates contract)
        
        Set<OnlyEqualsPerson> set = new HashSet<>();
        set.add(oep1);
        set.add(oep2);
        System.out.println("Set size (violates contract): " + set.size()); // 2 (should be 1)
        
        // Mistake 2: Using mutable fields
        class MutablePerson {
            private String name;
            private int age;
            private List<String> hobbies; // Mutable field
            
            public MutablePerson(String name, int age) {
                this.name = name;
                this.age = age;
                this.hobbies = new ArrayList<>();
            }
            
            public void addHobby(String hobby) {
                hobbies.add(hobby);
            }
            
            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (obj == null || getClass() != obj.getClass()) return false;
                
                MutablePerson other = (MutablePerson) obj;
                return age == other.age && 
                       Objects.equals(name, other.name) &&
                       Objects.equals(hobbies, other.hobbies);
            }
            
            @Override
            public int hashCode() {
                return Objects.hash(name, age, hobbies); // BAD: hobbies can change
            }
        }
        
        MutablePerson mp1 = new MutablePerson("Alice", 25);
        MutablePerson mp2 = new MutablePerson("Alice", 25);
        
        System.out.println("\nMutablePerson - Initial equals(): " + mp1.equals(mp2)); // true
        System.out.println("MutablePerson - Initial hashCode(): " + (mp1.hashCode() == mp2.hashCode())); // true
        
        // Change mutable field
        mp1.addHobby("Reading");
        System.out.println("MutablePerson - After mutation equals(): " + mp1.equals(mp2)); // false
        System.out.println("MutablePerson - After mutation hashCode(): " + (mp1.hashCode() == mp2.hashCode())); // false
        
        // Problem: Object is lost in HashMap
        Map<MutablePerson, String> map = new HashMap<>();
        map.put(mp1, "Value1");
        System.out.println("Map contains mp1 before mutation: " + map.containsKey(mp1)); // true
        mp1.addHobby("Swimming");
        System.out.println("Map contains mp1 after mutation: " + map.containsKey(mp1)); // false (lost!)
        System.out.println();
    }
    
    /**
     * Example 5: Performance comparison
     */
    public static void performanceComparison() {
        System.out.println("5. Performance Comparison:");
        System.out.println("--------------------------");
        
        int size = 100000;
        
        // Test with good hashCode() distribution
        List<Person> goodHashPersons = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            goodHashPersons.add(new Person("Person" + i, i, "email" + i + "@test.com"));
        }
        
        // Test with poor hashCode() distribution
        List<BadHashPerson> badHashPersons = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            badHashPersons.add(new BadHashPerson("Person" + i, i, "email" + i + "@test.com"));
        }
        
        // Performance test with HashMap
        long startTime = System.currentTimeMillis();
        Map<Person, String> goodMap = new HashMap<>();
        for (Person p : goodHashPersons) {
            goodMap.put(p, "value");
        }
        long goodPutTime = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        Map<BadHashPerson, String> badMap = new HashMap<>();
        for (BadHashPerson p : badHashPersons) {
            badMap.put(p, "value");
        }
        long badPutTime = System.currentTimeMillis() - startTime;
        
        System.out.println("Good hashCode() put time: " + goodPutTime + "ms");
        System.out.println("Bad hashCode() put time: " + badPutTime + "ms");
        System.out.println("Performance difference: " + (badPutTime - goodPutTime) + "ms");
        System.out.println();
    }
    
    /**
     * Example 6: Real-world scenarios
     */
    public static void realWorldScenarios() {
        System.out.println("6. Real-World Scenarios:");
        System.out.println("------------------------");
        
        // Scenario 1: Employee management system
        Employee emp1 = new Employee("EMP001", "Alice", "IT", 50000);
        Employee emp2 = new Employee("EMP001", "Alice", "IT", 50000);
        Employee emp3 = new Employee("EMP002", "Bob", "HR", 45000);
        
        Map<Employee, String> employeeMap = new HashMap<>();
        employeeMap.put(emp1, "Active");
        employeeMap.put(emp2, "Inactive"); // Should update emp1's status
        employeeMap.put(emp3, "Active");
        
        System.out.println("Employee Map:");
        employeeMap.forEach((emp, status) -> 
            System.out.println(emp.getEmployeeId() + " - " + emp.getName() + " - " + status));
        
        // Scenario 2: Student grade management
        Student student1 = new Student("STU001", "Charlie");
        Student student2 = new Student("STU001", "Charlie");
        
        student1.addCourse("Java");
        student1.addCourse("Python");
        student1.setGrade("Java", 85.5);
        student1.setGrade("Python", 92.0);
        
        student2.addCourse("Java");
        student2.setGrade("Java", 90.0);
        
        Set<Student> studentSet = new HashSet<>();
        studentSet.add(student1);
        studentSet.add(student2);
        
        System.out.println("\nStudent Set size: " + studentSet.size()); // Should be 1
        System.out.println("Student 1 equals Student 2: " + student1.equals(student2)); // true (same ID)
        
        // Scenario 3: Money value objects
        Money money1 = new Money(100.50, Currency.getInstance("USD"));
        Money money2 = new Money(100.50, Currency.getInstance("USD"));
        Money money3 = new Money(100.50, Currency.getInstance("EUR"));
        
        System.out.println("\nMoney comparisons:");
        System.out.println("money1.equals(money2): " + money1.equals(money2)); // true
        System.out.println("money1.equals(money3): " + money1.equals(money3)); // false
        System.out.println();
    }
    
    /**
     * Example 7: Advanced patterns
     */
    public static void advancedPatterns() {
        System.out.println("7. Advanced Patterns:");
        System.out.println("---------------------");
        
        // Pattern 1: Cached hashCode
        ExpensiveObject exp1 = new ExpensiveObject("Complex data structure");
        ExpensiveObject exp2 = new ExpensiveObject("Complex data structure");
        
        System.out.println("ExpensiveObject hashCode (first call): " + exp1.hashCode());
        System.out.println("ExpensiveObject hashCode (cached): " + exp1.hashCode());
        System.out.println("ExpensiveObject equals: " + exp1.equals(exp2));
        
        // Pattern 2: Builder pattern with equals/hashCode
        PersonBuilder pb1 = new PersonBuilder()
            .name("Alice")
            .age(25)
            .email("alice@email.com");
        
        PersonBuilder pb2 = new PersonBuilder()
            .name("Alice")
            .age(25)
            .email("alice@email.com");
        
        System.out.println("\nPersonBuilder equals: " + pb1.equals(pb2));
        System.out.println("PersonBuilder hashCode: " + (pb1.hashCode() == pb2.hashCode()));
        
        // Pattern 3: Composite key
        CompositeKey key1 = new CompositeKey("Alice", 25);
        CompositeKey key2 = new CompositeKey("Alice", 25);
        CompositeKey key3 = new CompositeKey("Alice", 30);
        
        Map<CompositeKey, String> compositeMap = new HashMap<>();
        compositeMap.put(key1, "Value1");
        compositeMap.put(key2, "Value2"); // Should update key1
        compositeMap.put(key3, "Value3");
        
        System.out.println("\nCompositeKey Map:");
        compositeMap.forEach((key, value) -> 
            System.out.println(key + " -> " + value));
        System.out.println();
    }
    
    /**
     * Example 8: Testing equals() and hashCode()
     */
    public static void testingEqualsHashCode() {
        System.out.println("8. Testing equals() and hashCode():");
        System.out.println("-----------------------------------");
        
        // Test reflexive property
        Person p1 = new Person("Alice", 25, "alice@email.com");
        System.out.println("Reflexive: p1.equals(p1) = " + p1.equals(p1)); // true
        
        // Test symmetric property
        Person p2 = new Person("Alice", 25, "alice@email.com");
        System.out.println("Symmetric: p1.equals(p2) = " + p1.equals(p2)); // true
        System.out.println("Symmetric: p2.equals(p1) = " + p2.equals(p1)); // true
        
        // Test transitive property
        Person p3 = new Person("Alice", 25, "alice@email.com");
        System.out.println("Transitive: p1.equals(p2) && p2.equals(p3) = " + 
            (p1.equals(p2) && p2.equals(p3))); // true
        System.out.println("Transitive: p1.equals(p3) = " + p1.equals(p3)); // true
        
        // Test null property
        System.out.println("Null: p1.equals(null) = " + p1.equals(null)); // false
        
        // Test consistency
        System.out.println("Consistency: p1.equals(p2) = " + p1.equals(p2)); // true
        System.out.println("Consistency: p1.equals(p2) = " + p1.equals(p2)); // true (same result)
        
        // Test hashCode contract
        System.out.println("HashCode contract: p1.equals(p2) == (p1.hashCode() == p2.hashCode()) = " + 
            (p1.equals(p2) == (p1.hashCode() == p2.hashCode()))); // true
        
        // Test with different objects
        Person p4 = new Person("Bob", 30, "bob@email.com");
        System.out.println("Different objects: p1.equals(p4) = " + p1.equals(p4)); // false
        System.out.println("Different objects: p1.hashCode() == p4.hashCode() = " + 
            (p1.hashCode() == p4.hashCode())); // false (likely)
        System.out.println();
    }
}

/**
 * Properly implemented Person class
 */
class Person {
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
    
    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + ", email='" + email + "'}";
    }
    
    // Getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }
}

/**
 * Person class with bad hashCode() implementation
 */
class BadHashPerson {
    private String name;
    private int age;
    private String email;
    
    public BadHashPerson(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        BadHashPerson other = (BadHashPerson) obj;
        return age == other.age &&
               Objects.equals(name, other.name) &&
               Objects.equals(email, other.email);
    }
    
    @Override
    public int hashCode() {
        return 1; // BAD: All objects have same hash code
    }
}

/**
 * Employee class with business key
 */
class Employee {
    private final String employeeId; // Business key
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
    
    @Override
    public String toString() {
        return "Employee{id='" + employeeId + "', name='" + name + "', dept='" + department + "'}";
    }
    
    public String getEmployeeId() { return employeeId; }
    public String getName() { return name; }
}

/**
 * Student class with mutable fields excluded from equals/hashCode
 */
class Student {
    private final String studentId; // Business key
    private String name;
    private Set<String> courses; // Mutable - excluded from equals/hashCode
    private Map<String, Double> grades; // Mutable - excluded from equals/hashCode
    
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
    
    public void addCourse(String course) {
        courses.add(course);
    }
    
    public void setGrade(String course, double grade) {
        grades.put(course, grade);
    }
    
    public String getStudentId() { return studentId; }
    public String getName() { return name; }
}

/**
 * Money value object
 */
class Money {
    private final java.math.BigDecimal amount;
    private final java.util.Currency currency;
    
    public Money(java.math.BigDecimal amount, java.util.Currency currency) {
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
    
    @Override
    public String toString() {
        return amount + " " + currency.getCurrencyCode();
    }
}

/**
 * Expensive object with cached hashCode
 */
class ExpensiveObject {
    private final String data;
    private int cachedHashCode;
    private boolean hashCodeComputed = false;
    
    public ExpensiveObject(String data) {
        this.data = data;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        ExpensiveObject other = (ExpensiveObject) obj;
        return Objects.equals(data, other.data);
    }
    
    @Override
    public int hashCode() {
        if (!hashCodeComputed) {
            // Simulate expensive computation
            cachedHashCode = Objects.hash(data);
            hashCodeComputed = true;
        }
        return cachedHashCode;
    }
}

/**
 * Person with builder pattern
 */
class PersonBuilder {
    private String name;
    private int age;
    private String email;
    
    public PersonBuilder name(String name) {
        this.name = name;
        return this;
    }
    
    public PersonBuilder age(int age) {
        this.age = age;
        return this;
    }
    
    public PersonBuilder email(String email) {
        this.email = email;
        return this;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        PersonBuilder other = (PersonBuilder) obj;
        return age == other.age &&
               Objects.equals(name, other.name) &&
               Objects.equals(email, other.email);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, age, email);
    }
}

/**
 * Composite key example
 */
class CompositeKey {
    private final String name;
    private final int age;
    
    public CompositeKey(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        CompositeKey other = (CompositeKey) obj;
        return age == other.age && Objects.equals(name, other.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
    
    @Override
    public String toString() {
        return "(" + name + ", " + age + ")";
    }
} 