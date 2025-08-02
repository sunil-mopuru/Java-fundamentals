package hashcode_equals;

import java.util.*;

/**
 * Test class to demonstrate and validate equals() and hashCode() implementations
 */
public class EqualsHashCodeTest {
    
    public static void main(String[] args) {
        System.out.println("=== equals() and hashCode() Test Suite ===\n");
        
        // Test 1: Basic contract validation
        testBasicContract();
        
        // Test 2: Collection behavior
        testCollectionBehavior();
        
        // Test 3: Performance impact
        testPerformanceImpact();
        
        // Test 4: Edge cases
        testEdgeCases();
        
        // Test 5: Business logic scenarios
        testBusinessScenarios();
    }
    
    /**
     * Test 1: Basic contract validation
     */
    public static void testBasicContract() {
        System.out.println("1. Basic Contract Validation:");
        System.out.println("-----------------------------");
        
        // Create test objects
        Person p1 = new Person("Alice", 25, "alice@email.com");
        Person p2 = new Person("Alice", 25, "alice@email.com");
        Person p3 = new Person("Bob", 30, "bob@email.com");
        Person p4 = new Person("Alice", 25, "alice@email.com");
        
        // Test reflexive property
        System.out.println("✓ Reflexive: p1.equals(p1) = " + p1.equals(p1));
        
        // Test symmetric property
        boolean symmetric1 = p1.equals(p2);
        boolean symmetric2 = p2.equals(p1);
        System.out.println("✓ Symmetric: p1.equals(p2) = " + symmetric1 + ", p2.equals(p1) = " + symmetric2);
        System.out.println("  Symmetric holds: " + (symmetric1 == symmetric2));
        
        // Test transitive property
        boolean transitive1 = p1.equals(p2);
        boolean transitive2 = p2.equals(p4);
        boolean transitive3 = p1.equals(p4);
        System.out.println("✓ Transitive: p1.equals(p2) = " + transitive1 + 
                          ", p2.equals(p4) = " + transitive2 + 
                          ", p1.equals(p4) = " + transitive3);
        System.out.println("  Transitive holds: " + ((transitive1 && transitive2) == transitive3));
        
        // Test null property
        System.out.println("✓ Null: p1.equals(null) = " + p1.equals(null));
        
        // Test consistency
        boolean consistency1 = p1.equals(p2);
        boolean consistency2 = p1.equals(p2);
        System.out.println("✓ Consistency: p1.equals(p2) = " + consistency1 + 
                          " (multiple calls: " + consistency2 + ")");
        System.out.println("  Consistency holds: " + (consistency1 == consistency2));
        
        // Test hashCode contract
        boolean hashCodeContract = p1.equals(p2) == (p1.hashCode() == p2.hashCode());
        System.out.println("✓ HashCode Contract: p1.equals(p2) == (p1.hashCode() == p2.hashCode()) = " + hashCodeContract);
        
        System.out.println();
    }
    
    /**
     * Test 2: Collection behavior
     */
    public static void testCollectionBehavior() {
        System.out.println("2. Collection Behavior:");
        System.out.println("----------------------");
        
        // Test HashSet behavior
        Set<Person> personSet = new HashSet<>();
        Person p1 = new Person("Alice", 25, "alice@email.com");
        Person p2 = new Person("Alice", 25, "alice@email.com");
        Person p3 = new Person("Bob", 30, "bob@email.com");
        
        personSet.add(p1);
        personSet.add(p2); // Should not add duplicate
        personSet.add(p3);
        
        System.out.println("✓ HashSet size: " + personSet.size() + " (expected: 2)");
        System.out.println("✓ HashSet contains p1: " + personSet.contains(p1));
        System.out.println("✓ HashSet contains p2: " + personSet.contains(p2));
        System.out.println("✓ HashSet contains new Person with same data: " + 
            personSet.contains(new Person("Alice", 25, "alice@email.com")));
        
        // Test HashMap behavior
        Map<Person, String> personMap = new HashMap<>();
        personMap.put(p1, "Value1");
        personMap.put(p2, "Value2"); // Should update p1's value
        personMap.put(p3, "Value3");
        
        System.out.println("✓ HashMap size: " + personMap.size() + " (expected: 2)");
        System.out.println("✓ HashMap value for p1: " + personMap.get(p1));
        System.out.println("✓ HashMap value for p2: " + personMap.get(p2));
        System.out.println("✓ HashMap value for new Person with same data: " + 
            personMap.get(new Person("Alice", 25, "alice@email.com")));
        
        // Test ArrayList behavior
        List<Person> personList = new ArrayList<>();
        personList.add(p1);
        personList.add(p2);
        personList.add(p3);
        
        System.out.println("✓ ArrayList size: " + personList.size() + " (expected: 3)");
        System.out.println("✓ ArrayList indexOf p1: " + personList.indexOf(p1));
        System.out.println("✓ ArrayList indexOf p2: " + personList.indexOf(p2));
        System.out.println("✓ ArrayList contains p1: " + personList.contains(p1));
        System.out.println("✓ ArrayList contains new Person with same data: " + 
            personList.contains(new Person("Alice", 25, "alice@email.com")));
        
        System.out.println();
    }
    
    /**
     * Test 3: Performance impact
     */
    public static void testPerformanceImpact() {
        System.out.println("3. Performance Impact:");
        System.out.println("---------------------");
        
        int size = 10000;
        
        // Test with good hashCode distribution
        List<Person> goodHashPersons = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            goodHashPersons.add(new Person("Person" + i, i, "email" + i + "@test.com"));
        }
        
        // Test with poor hashCode distribution
        List<BadHashPerson> badHashPersons = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            badHashPersons.add(new BadHashPerson("Person" + i, i, "email" + i + "@test.com"));
        }
        
        // HashMap performance test
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
        
        System.out.println("✓ Good hashCode() HashMap put time: " + goodPutTime + "ms");
        System.out.println("✓ Bad hashCode() HashMap put time: " + badPutTime + "ms");
        System.out.println("✓ Performance difference: " + (badPutTime - goodPutTime) + "ms");
        System.out.println("✓ Performance ratio: " + String.format("%.2f", (double) badPutTime / goodPutTime) + "x slower");
        
        // HashSet performance test
        startTime = System.currentTimeMillis();
        Set<Person> goodSet = new HashSet<>();
        for (Person p : goodHashPersons) {
            goodSet.add(p);
        }
        long goodSetTime = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        Set<BadHashPerson> badSet = new HashSet<>();
        for (BadHashPerson p : badHashPersons) {
            badSet.add(p);
        }
        long badSetTime = System.currentTimeMillis() - startTime;
        
        System.out.println("✓ Good hashCode() HashSet add time: " + goodSetTime + "ms");
        System.out.println("✓ Bad hashCode() HashSet add time: " + badSetTime + "ms");
        System.out.println("✓ Performance difference: " + (badSetTime - goodSetTime) + "ms");
        
        System.out.println();
    }
    
    /**
     * Test 4: Edge cases
     */
    public static void testEdgeCases() {
        System.out.println("4. Edge Cases:");
        System.out.println("-------------");
        
        // Test with null fields
        Person p1 = new Person("Alice", 25, null);
        Person p2 = new Person("Alice", 25, null);
        Person p3 = new Person("Alice", 25, "alice@email.com");
        
        System.out.println("✓ Null email equals: " + p1.equals(p2));
        System.out.println("✓ Null vs non-null email equals: " + p1.equals(p3));
        System.out.println("✓ Null email hashCode: " + (p1.hashCode() == p2.hashCode()));
        
        // Test with empty strings
        Person p4 = new Person("", 25, "alice@email.com");
        Person p5 = new Person("", 25, "alice@email.com");
        Person p6 = new Person("Alice", 25, "alice@email.com");
        
        System.out.println("✓ Empty name equals: " + p4.equals(p5));
        System.out.println("✓ Empty vs non-empty name equals: " + p4.equals(p6));
        System.out.println("✓ Empty name hashCode: " + (p4.hashCode() == p5.hashCode()));
        
        // Test with zero values
        Person p7 = new Person("Alice", 0, "alice@email.com");
        Person p8 = new Person("Alice", 0, "alice@email.com");
        Person p9 = new Person("Alice", 25, "alice@email.com");
        
        System.out.println("✓ Zero age equals: " + p7.equals(p8));
        System.out.println("✓ Zero vs non-zero age equals: " + p7.equals(p9));
        System.out.println("✓ Zero age hashCode: " + (p7.hashCode() == p8.hashCode()));
        
        // Test with different object types
        Object obj = new Object();
        System.out.println("✓ Different object type equals: " + p1.equals(obj));
        
        // Test with same object reference
        System.out.println("✓ Same reference equals: " + p1.equals(p1));
        
        System.out.println();
    }
    
    /**
     * Test 5: Business logic scenarios
     */
    public static void testBusinessScenarios() {
        System.out.println("5. Business Logic Scenarios:");
        System.out.println("----------------------------");
        
        // Scenario 1: Employee management
        Employee emp1 = new Employee("EMP001", "Alice", "IT", 50000);
        Employee emp2 = new Employee("EMP001", "Alice", "IT", 50000);
        Employee emp3 = new Employee("EMP002", "Bob", "HR", 45000);
        
        Map<Employee, String> employeeStatus = new HashMap<>();
        employeeStatus.put(emp1, "Active");
        employeeStatus.put(emp2, "Inactive"); // Should update emp1's status
        employeeStatus.put(emp3, "Active");
        
        System.out.println("✓ Employee map size: " + employeeStatus.size() + " (expected: 2)");
        System.out.println("✓ Employee EMP001 status: " + employeeStatus.get(emp1));
        System.out.println("✓ Employee EMP002 status: " + employeeStatus.get(emp3));
        
        // Scenario 2: Student grade management
        Student student1 = new Student("STU001", "Charlie");
        Student student2 = new Student("STU001", "Charlie");
        
        student1.addCourse("Java");
        student1.setGrade("Java", 85.5);
        student2.addCourse("Python");
        student2.setGrade("Python", 92.0);
        
        Set<Student> studentSet = new HashSet<>();
        studentSet.add(student1);
        studentSet.add(student2);
        
        System.out.println("✓ Student set size: " + studentSet.size() + " (expected: 1)");
        System.out.println("✓ Student equals: " + student1.equals(student2));
        
        // Scenario 3: Money value objects
        Money money1 = new Money(new java.math.BigDecimal("100.50"), java.util.Currency.getInstance("USD"));
        Money money2 = new Money(new java.math.BigDecimal("100.50"), java.util.Currency.getInstance("USD"));
        Money money3 = new Money(new java.math.BigDecimal("100.50"), java.util.Currency.getInstance("EUR"));
        
        System.out.println("✓ Same currency money equals: " + money1.equals(money2));
        System.out.println("✓ Different currency money equals: " + money1.equals(money3));
        
        // Scenario 4: Composite keys
        CompositeKey key1 = new CompositeKey("Alice", 25);
        CompositeKey key2 = new CompositeKey("Alice", 25);
        CompositeKey key3 = new CompositeKey("Alice", 30);
        
        Map<CompositeKey, String> compositeMap = new HashMap<>();
        compositeMap.put(key1, "Value1");
        compositeMap.put(key2, "Value2"); // Should update key1
        compositeMap.put(key3, "Value3");
        
        System.out.println("✓ Composite key map size: " + compositeMap.size() + " (expected: 2)");
        System.out.println("✓ Composite key (Alice, 25) value: " + compositeMap.get(key1));
        System.out.println("✓ Composite key (Alice, 30) value: " + compositeMap.get(key3));
        
        System.out.println();
    }
}

/**
 * Test class for demonstrating equals() and hashCode() validation
 */
class TestPerson {
    private String name;
    private int age;
    private String email;
    
    public TestPerson(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        TestPerson other = (TestPerson) obj;
        return age == other.age &&
               Objects.equals(name, other.name) &&
               Objects.equals(email, other.email);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, age, email);
    }
    
    @Override
    public String toString() {
        return "TestPerson{name='" + name + "', age=" + age + ", email='" + email + "'}";
    }
} 