# Java 8 Date/Time API Deep Dive

## Table of Contents

1. [Introduction](#introduction)
2. [Problems with Old Date/Time API](#problems-with-old-datetime-api)
3. [Core Classes](#core-classes)
4. [LocalDate](#localdate)
5. [LocalTime](#localtime)
6. [LocalDateTime](#localdatetime)
7. [ZonedDateTime](#zoneddatetime)
8. [Period and Duration](#period-and-duration)
9. [Formatting and Parsing](#formatting-and-parsing)
10. [Temporal Adjusters](#temporal-adjusters)
11. [Practical Examples](#practical-examples)
12. [Best Practices](#best-practices)
13. [Exercises](#exercises)
14. [Summary](#summary)

## Introduction

The Java 8 Date/Time API (also known as JSR-310) was introduced to address the numerous problems with the old `java.util.Date` and `java.util.Calendar` classes. It provides a comprehensive, immutable, and thread-safe date and time handling system.

### Key Features:

- **Immutability**: All classes are immutable and thread-safe
- **Fluent API**: Method chaining for easy manipulation
- **Clear Separation**: Separate classes for date, time, and date-time
- **Time Zone Support**: Comprehensive time zone handling
- **ISO-8601 Compliance**: Follows international standards
- **Extensible**: Easy to extend and customize

## Problems with Old Date/Time API

### Issues with `java.util.Date`:

```java
// Problems with old API
Date date = new Date(); // Mutable, not thread-safe
date.setYear(2023); // Deprecated method
date.setMonth(Calendar.JANUARY); // Confusing month indexing (0-based)

// Time zone issues
Date now = new Date(); // Always in system default timezone
// No easy way to work with different timezones
```

### Issues with `java.util.Calendar`:

```java
Calendar cal = Calendar.getInstance();
cal.set(2023, Calendar.JANUARY, 15); // Month is 0-based!
cal.add(Calendar.MONTH, 1); // Mutates the object

// Complex and error-prone
int year = cal.get(Calendar.YEAR);
int month = cal.get(Calendar.MONTH); // Returns 0-11
int day = cal.get(Calendar.DAY_OF_MONTH);
```

## Core Classes

The new Date/Time API is organized into several packages:

- `java.time`: Core classes for date and time
- `java.time.format`: Formatting and parsing
- `java.time.temporal`: Temporal adjusters and queries
- `java.time.zone`: Time zone support

### Main Classes:

1. **LocalDate**: Date without time (2023-01-15)
2. **LocalTime**: Time without date (14:30:45)
3. **LocalDateTime**: Date and time without timezone (2023-01-15T14:30:45)
4. **ZonedDateTime**: Date and time with timezone (2023-01-15T14:30:45+01:00)
5. **Instant**: Point in time (timestamp)
6. **Period**: Amount of time in years, months, days
7. **Duration**: Amount of time in seconds and nanoseconds

## LocalDate

`LocalDate` represents a date without time and timezone information.

### Creating LocalDate Objects

```java
// Current date
LocalDate today = LocalDate.now();

// Specific date
LocalDate specificDate = LocalDate.of(2023, 1, 15);
LocalDate specificDate2 = LocalDate.of(2023, Month.JANUARY, 15);

// From string (ISO format)
LocalDate fromString = LocalDate.parse("2023-01-15");

// From epoch day
LocalDate fromEpoch = LocalDate.ofEpochDay(19456);
```

### Working with LocalDate

```java
LocalDate date = LocalDate.of(2023, 1, 15);

// Getting components
int year = date.getYear();           // 2023
int month = date.getMonthValue();    // 1
Month monthEnum = date.getMonth();   // JANUARY
int day = date.getDayOfMonth();      // 15
DayOfWeek dayOfWeek = date.getDayOfWeek(); // SUNDAY
int dayOfYear = date.getDayOfYear(); // 15

// Manipulating dates
LocalDate tomorrow = date.plusDays(1);
LocalDate nextWeek = date.plusWeeks(1);
LocalDate nextMonth = date.plusMonths(1);
LocalDate nextYear = date.plusYears(1);

LocalDate yesterday = date.minusDays(1);
LocalDate lastWeek = date.minusWeeks(1);
LocalDate lastMonth = date.minusMonths(1);
LocalDate lastYear = date.minusYears(1);

// Comparing dates
boolean isBefore = date.isBefore(LocalDate.of(2023, 2, 1));
boolean isAfter = date.isAfter(LocalDate.of(2023, 1, 1));
boolean isEqual = date.isEqual(LocalDate.of(2023, 1, 15));
```

### Date Calculations

```java
LocalDate date1 = LocalDate.of(2023, 1, 15);
LocalDate date2 = LocalDate.of(2023, 3, 20);

// Period between dates
Period period = Period.between(date1, date2);
System.out.println(period.getDays());    // 5
System.out.println(period.getMonths());  // 2
System.out.println(period.getYears());   // 0

// Days between dates
long daysBetween = ChronoUnit.DAYS.between(date1, date2);
long weeksBetween = ChronoUnit.WEEKS.between(date1, date2);
long monthsBetween = ChronoUnit.MONTHS.between(date1, date2);
```

## LocalTime

`LocalTime` represents a time without date and timezone information.

### Creating LocalTime Objects

```java
// Current time
LocalTime now = LocalTime.now();

// Specific time
LocalTime specificTime = LocalTime.of(14, 30, 45);
LocalTime specificTime2 = LocalTime.of(14, 30, 45, 123456789);

// From string (ISO format)
LocalTime fromString = LocalTime.parse("14:30:45");

// Constants
LocalTime midnight = LocalTime.MIDNIGHT;
LocalTime noon = LocalTime.NOON;
LocalTime min = LocalTime.MIN;
LocalTime max = LocalTime.MAX;
```

### Working with LocalTime

```java
LocalTime time = LocalTime.of(14, 30, 45, 123456789);

// Getting components
int hour = time.getHour();           // 14
int minute = time.getMinute();       // 30
int second = time.getSecond();       // 45
int nano = time.getNano();           // 123456789

// Manipulating time
LocalTime plusHour = time.plusHours(1);
LocalTime plusMinutes = time.plusMinutes(30);
LocalTime plusSeconds = time.plusSeconds(45);
LocalTime plusNanos = time.plusNanos(1000000);

LocalTime minusHour = time.minusHours(1);
LocalTime minusMinutes = time.minusMinutes(30);
LocalTime minusSeconds = time.minusSeconds(45);
LocalTime minusNanos = time.minusNanos(1000000);

// Comparing times
boolean isBefore = time.isBefore(LocalTime.of(15, 0));
boolean isAfter = time.isAfter(LocalTime.of(14, 0));
```

### Time Calculations

```java
LocalTime time1 = LocalTime.of(9, 0);
LocalTime time2 = LocalTime.of(17, 30);

// Duration between times
Duration duration = Duration.between(time1, time2);
System.out.println(duration.toHours());     // 8
System.out.println(duration.toMinutes());   // 510
System.out.println(duration.toSeconds());   // 30600

// Hours between times
long hoursBetween = ChronoUnit.HOURS.between(time1, time2);
long minutesBetween = ChronoUnit.MINUTES.between(time1, time2);
```

## LocalDateTime

`LocalDateTime` represents a date and time without timezone information.

### Creating LocalDateTime Objects

```java
// Current date and time
LocalDateTime now = LocalDateTime.now();

// Specific date and time
LocalDateTime specific = LocalDateTime.of(2023, 1, 15, 14, 30, 45);
LocalDateTime specific2 = LocalDateTime.of(2023, Month.JANUARY, 15, 14, 30, 45);

// From LocalDate and LocalTime
LocalDate date = LocalDate.of(2023, 1, 15);
LocalTime time = LocalTime.of(14, 30, 45);
LocalDateTime dateTime = LocalDateTime.of(date, time);

// From string (ISO format)
LocalDateTime fromString = LocalDateTime.parse("2023-01-15T14:30:45");

// From individual components
LocalDateTime fromComponents = LocalDateTime.of(2023, 1, 15, 14, 30, 45, 123456789);
```

### Working with LocalDateTime

```java
LocalDateTime dateTime = LocalDateTime.of(2023, 1, 15, 14, 30, 45);

// Getting components
LocalDate date = dateTime.toLocalDate();
LocalTime time = dateTime.toLocalTime();

int year = dateTime.getYear();
int month = dateTime.getMonthValue();
int day = dateTime.getDayOfMonth();
int hour = dateTime.getHour();
int minute = dateTime.getMinute();
int second = dateTime.getSecond();

// Manipulating date and time
LocalDateTime plusDays = dateTime.plusDays(1);
LocalDateTime plusHours = dateTime.plusHours(2);
LocalDateTime plusWeeks = dateTime.plusWeeks(1);
LocalDateTime plusMonths = dateTime.plusMonths(1);

LocalDateTime minusDays = dateTime.minusDays(1);
LocalDateTime minusHours = dateTime.minusHours(2);
LocalDateTime minusWeeks = dateTime.minusWeeks(1);
LocalDateTime minusMonths = dateTime.minusMonths(1);

// Converting to other types
LocalDate dateOnly = dateTime.toLocalDate();
LocalTime timeOnly = dateTime.toLocalTime();
```

## ZonedDateTime

`ZonedDateTime` represents a date and time with timezone information.

### Creating ZonedDateTime Objects

```java
// Current date and time in system timezone
ZonedDateTime now = ZonedDateTime.now();

// Current date and time in specific timezone
ZoneId tokyoZone = ZoneId.of("Asia/Tokyo");
ZonedDateTime tokyoTime = ZonedDateTime.now(tokyoZone);

// Specific date and time in timezone
ZonedDateTime specific = ZonedDateTime.of(2023, 1, 15, 14, 30, 45, 0, tokyoZone);

// From LocalDateTime and ZoneId
LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 15, 14, 30, 45);
ZonedDateTime zoned = localDateTime.atZone(tokyoZone);

// From string
ZonedDateTime fromString = ZonedDateTime.parse("2023-01-15T14:30:45+09:00[Asia/Tokyo]");
```

### Working with ZonedDateTime

```java
ZonedDateTime tokyoTime = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));

// Getting components
LocalDateTime localDateTime = tokyoTime.toLocalDateTime();
LocalDate localDate = tokyoTime.toLocalDate();
LocalTime localTime = tokyoTime.toLocalTime();
ZoneId zoneId = tokyoTime.getZone();
ZoneOffset offset = tokyoTime.getOffset();

// Converting between timezones
ZoneId newYorkZone = ZoneId.of("America/New_York");
ZonedDateTime newYorkTime = tokyoTime.withZoneSameInstant(newYorkZone);

// Manipulating
ZonedDateTime plusDays = tokyoTime.plusDays(1);
ZonedDateTime plusHours = tokyoTime.plusHours(2);
ZonedDateTime plusMonths = tokyoTime.plusMonths(1);

// Converting to Instant
Instant instant = tokyoTime.toInstant();
```

### Time Zone Operations

```java
// Available time zones
Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
availableZoneIds.forEach(System.out::println);

// Common time zones
ZoneId utc = ZoneId.of("UTC");
ZoneId tokyo = ZoneId.of("Asia/Tokyo");
ZoneId newYork = ZoneId.of("America/New_York");
ZoneId london = ZoneId.of("Europe/London");

// Converting between timezones
ZonedDateTime tokyoTime = ZonedDateTime.now(tokyo);
ZonedDateTime newYorkTime = tokyoTime.withZoneSameInstant(newYork);
ZonedDateTime londonTime = tokyoTime.withZoneSameInstant(london);

System.out.println("Tokyo: " + tokyoTime);
System.out.println("New York: " + newYorkTime);
System.out.println("London: " + londonTime);
```

## Period and Duration

### Period

`Period` represents a period of time in years, months, and days.

```java
// Creating periods
Period period1 = Period.of(1, 2, 15);  // 1 year, 2 months, 15 days
Period period2 = Period.ofYears(1);
Period period3 = Period.ofMonths(6);
Period period4 = Period.ofDays(30);

// Period between dates
LocalDate date1 = LocalDate.of(2023, 1, 15);
LocalDate date2 = LocalDate.of(2024, 3, 30);
Period period = Period.between(date1, date2);

System.out.println(period.getYears());   // 1
System.out.println(period.getMonths());  // 2
System.out.println(period.getDays());    // 15

// Adding/subtracting periods
LocalDate newDate = date1.plus(period);
LocalDate oldDate = date2.minus(period);
```

### Duration

`Duration` represents a duration of time in seconds and nanoseconds.

```java
// Creating durations
Duration duration1 = Duration.ofDays(1);
Duration duration2 = Duration.ofHours(12);
Duration duration3 = Duration.ofMinutes(30);
Duration duration4 = Duration.ofSeconds(45);
Duration duration5 = Duration.ofMillis(500);
Duration duration6 = Duration.ofNanos(1000000);

// Duration between times
LocalTime time1 = LocalTime.of(9, 0);
LocalTime time2 = LocalTime.of(17, 30);
Duration duration = Duration.between(time1, time2);

System.out.println(duration.toHours());     // 8
System.out.println(duration.toMinutes());   // 510
System.out.println(duration.toSeconds());   // 30600

// Adding/subtracting durations
LocalTime newTime = time1.plus(duration);
LocalTime oldTime = time2.minus(duration);
```

## Formatting and Parsing

### DateTimeFormatter

The `DateTimeFormatter` class provides formatting and parsing capabilities.

```java
// Predefined formatters
DateTimeFormatter isoDate = DateTimeFormatter.ISO_LOCAL_DATE;
DateTimeFormatter isoTime = DateTimeFormatter.ISO_LOCAL_TIME;
DateTimeFormatter isoDateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

// Custom formatters
DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
DateTimeFormatter customFormatter2 = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");

// Localized formatters
DateTimeFormatter localizedFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
DateTimeFormatter localizedDateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
```

### Formatting Examples

```java
LocalDateTime dateTime = LocalDateTime.of(2023, 1, 15, 14, 30, 45);

// Using predefined formatters
String isoString = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
System.out.println(isoString); // 2023-01-15T14:30:45

// Using custom formatters
DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
String customString = dateTime.format(customFormatter);
System.out.println(customString); // 15/01/2023 14:30:45

// Localized formatting
DateTimeFormatter localizedFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
String localizedString = dateTime.format(localizedFormatter);
System.out.println(localizedString); // Jan 15, 2023, 2:30:45 PM
```

### Parsing Examples

```java
// Parsing ISO format
LocalDateTime parsed1 = LocalDateTime.parse("2023-01-15T14:30:45");

// Parsing custom format
DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
LocalDateTime parsed2 = LocalDateTime.parse("15/01/2023 14:30:45", customFormatter);

// Parsing with locale
DateTimeFormatter localizedFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
    .withLocale(Locale.US);
LocalDateTime parsed3 = LocalDateTime.parse("Jan 15, 2023, 2:30:45 PM", localizedFormatter);
```

## Temporal Adjusters

Temporal adjusters allow you to perform complex date/time manipulations.

### Built-in Adjusters

```java
LocalDate date = LocalDate.of(2023, 1, 15);

// First day of month
LocalDate firstDayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());

// Last day of month
LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());

// First day of next month
LocalDate firstDayOfNextMonth = date.with(TemporalAdjusters.firstDayOfNextMonth());

// Next Monday
LocalDate nextMonday = date.with(TemporalAdjusters.next(DayOfWeek.MONDAY));

// Previous Friday
LocalDate previousFriday = date.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));

// Last day of year
LocalDate lastDayOfYear = date.with(TemporalAdjusters.lastDayOfYear());

// First day of year
LocalDate firstDayOfYear = date.with(TemporalAdjusters.firstDayOfYear());
```

### Custom Adjusters

```java
// Custom adjuster for next working day
TemporalAdjuster nextWorkingDay = temporal -> {
    DayOfWeek dayOfWeek = DayOfWeek.from(temporal);
    int daysToAdd = 1;

    if (dayOfWeek == DayOfWeek.FRIDAY) {
        daysToAdd = 3; // Skip to Monday
    } else if (dayOfWeek == DayOfWeek.SATURDAY) {
        daysToAdd = 2; // Skip to Monday
    }

    return temporal.plus(daysToAdd, ChronoUnit.DAYS);
};

LocalDate date = LocalDate.of(2023, 1, 13); // Friday
LocalDate nextWorking = date.with(nextWorkingDay);
System.out.println(nextWorking); // 2023-01-16 (Monday)
```

## Practical Examples

### 1. **Date Range Generation**

```java
public static List<LocalDate> generateDateRange(LocalDate start, LocalDate end) {
    List<LocalDate> dates = new ArrayList<>();
    LocalDate current = start;

    while (!current.isAfter(end)) {
        dates.add(current);
        current = current.plusDays(1);
    }

    return dates;
}

// Usage
LocalDate start = LocalDate.of(2023, 1, 1);
LocalDate end = LocalDate.of(2023, 1, 31);
List<LocalDate> dateRange = generateDateRange(start, end);
```

### 2. **Business Day Calculator**

```java
public static boolean isBusinessDay(LocalDate date) {
    DayOfWeek dayOfWeek = date.getDayOfWeek();
    return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
}

public static LocalDate nextBusinessDay(LocalDate date) {
    LocalDate nextDay = date.plusDays(1);
    while (!isBusinessDay(nextDay)) {
        nextDay = nextDay.plusDays(1);
    }
    return nextDay;
}

public static int businessDaysBetween(LocalDate start, LocalDate end) {
    return (int) start.datesUntil(end.plusDays(1))
        .filter(DefaultMethodExamples::isBusinessDay)
        .count();
}
```

### 3. **Age Calculator**

```java
public static int calculateAge(LocalDate birthDate) {
    return Period.between(birthDate, LocalDate.now()).getYears();
}

public static String calculateAgeDetailed(LocalDate birthDate) {
    Period period = Period.between(birthDate, LocalDate.now());
    return String.format("%d years, %d months, %d days",
        period.getYears(), period.getMonths(), period.getDays());
}
```

### 4. **Time Zone Converter**

```java
public static ZonedDateTime convertTimeZone(ZonedDateTime source, ZoneId targetZone) {
    return source.withZoneSameInstant(targetZone);
}

public static String formatInTimeZone(LocalDateTime localDateTime, ZoneId zoneId,
                                    DateTimeFormatter formatter) {
    return localDateTime.atZone(zoneId).format(formatter);
}
```

### 5. **Date Validation**

```java
public static boolean isValidDate(int year, int month, int day) {
    try {
        LocalDate.of(year, month, day);
        return true;
    } catch (DateTimeException e) {
        return false;
    }
}

public static boolean isLeapYear(int year) {
    return LocalDate.of(year, 1, 1).isLeapYear();
}
```

## Best Practices

### 1. **Use Appropriate Classes**

```java
// Good: Use LocalDate for date-only operations
LocalDate date = LocalDate.of(2023, 1, 15);

// Good: Use LocalTime for time-only operations
LocalTime time = LocalTime.of(14, 30, 45);

// Good: Use LocalDateTime for date-time without timezone
LocalDateTime dateTime = LocalDateTime.of(date, time);

// Good: Use ZonedDateTime for timezone-aware operations
ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("UTC"));
```

### 2. **Handle Time Zones Properly**

```java
// Good: Always specify timezone when needed
ZonedDateTime tokyoTime = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));

// Good: Convert between timezones properly
ZonedDateTime newYorkTime = tokyoTime.withZoneSameInstant(ZoneId.of("America/New_York"));

// Avoid: Using system default timezone without consideration
ZonedDateTime now = ZonedDateTime.now(); // May cause issues
```

### 3. **Use Immutable Operations**

```java
// Good: All operations return new objects
LocalDate date = LocalDate.of(2023, 1, 15);
LocalDate tomorrow = date.plusDays(1); // date remains unchanged

// Good: Chain operations
LocalDateTime dateTime = LocalDateTime.now()
    .plusDays(1)
    .plusHours(2)
    .withMinute(0)
    .withSecond(0)
    .withNano(0);
```

### 4. **Format and Parse Safely**

```java
// Good: Use try-catch for parsing
try {
    LocalDate date = LocalDate.parse("2023-01-15");
} catch (DateTimeParseException e) {
    // Handle parsing error
}

// Good: Use appropriate formatters
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
LocalDate date = LocalDate.parse("15/01/2023", formatter);
```

## Exercises

### Exercise 1: Date Calculator

Create a utility class with methods to:

- Calculate days between two dates
- Find the next occurrence of a specific day of week
- Check if a date falls on a weekend
- Calculate the quarter of a year for a given date

### Exercise 2: Time Zone Converter

Create a class that can:

- Convert times between different time zones
- Display current time in multiple time zones
- Calculate time differences between cities
- Handle daylight saving time transitions

### Exercise 3: Date Range Utilities

Create utilities for:

- Generating date ranges
- Finding business days in a range
- Calculating working hours between two date-times
- Grouping dates by month, quarter, or year

### Exercise 4: Event Scheduler

Create an event scheduling system that:

- Stores events with date, time, and timezone
- Finds available time slots
- Handles recurring events
- Validates event conflicts

## Summary

The Java 8 Date/Time API provides a comprehensive, immutable, and thread-safe solution for date and time handling:

### Key Benefits:

- **Immutability**: All classes are immutable and thread-safe
- **Clear API**: Separate classes for different use cases
- **Time Zone Support**: Comprehensive timezone handling
- **ISO Compliance**: Follows international standards
- **Extensibility**: Easy to extend and customize

### Main Classes:

1. **LocalDate**: Date without time
2. **LocalTime**: Time without date
3. **LocalDateTime**: Date and time without timezone
4. **ZonedDateTime**: Date and time with timezone
5. **Period**: Time period in years, months, days
6. **Duration**: Time duration in seconds and nanoseconds
7. **Instant**: Point in time (timestamp)

### Best Practices:

1. Use appropriate classes for your use case
2. Handle time zones properly
3. Use immutable operations
4. Format and parse safely
5. Leverage built-in adjusters and formatters

The new Date/Time API makes working with dates and times much more reliable, readable, and maintainable compared to the old API.
