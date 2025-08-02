package datetime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Comprehensive examples demonstrating Java 8 Date/Time API
 * This class showcases various aspects of the new date/time API including
 * LocalDate, LocalTime, LocalDateTime, ZonedDateTime, Period, Duration,
 * formatting, parsing, and practical utilities.
 */
public class DateTimeExamples {
    
    public static void main(String[] args) {
        System.out.println("=== Java 8 Date/Time API Deep Dive ===\n");
        
        localDateExamples();
        localTimeExamples();
        localDateTimeExamples();
        zonedDateTimeExamples();
        periodAndDurationExamples();
        formattingAndParsingExamples();
        temporalAdjustersExamples();
        practicalExamples();
        exerciseSolutions();
    }
    
    // ==================== LOCALDATE EXAMPLES ====================
    
    private static void localDateExamples() {
        System.out.println("1. LocalDate Examples:");
        
        // Creating LocalDate objects
        LocalDate today = LocalDate.now();
        System.out.println("Today: " + today);
        
        LocalDate specificDate = LocalDate.of(2023, 1, 15);
        System.out.println("Specific date: " + specificDate);
        
        LocalDate fromString = LocalDate.parse("2023-01-15");
        System.out.println("From string: " + fromString);
        
        // Getting components
        System.out.println("Year: " + specificDate.getYear());
        System.out.println("Month: " + specificDate.getMonthValue());
        System.out.println("Month enum: " + specificDate.getMonth());
        System.out.println("Day: " + specificDate.getDayOfMonth());
        System.out.println("Day of week: " + specificDate.getDayOfWeek());
        System.out.println("Day of year: " + specificDate.getDayOfYear());
        
        // Manipulating dates
        LocalDate tomorrow = specificDate.plusDays(1);
        LocalDate nextWeek = specificDate.plusWeeks(1);
        LocalDate nextMonth = specificDate.plusMonths(1);
        LocalDate nextYear = specificDate.plusYears(1);
        
        System.out.println("Tomorrow: " + tomorrow);
        System.out.println("Next week: " + nextWeek);
        System.out.println("Next month: " + nextMonth);
        System.out.println("Next year: " + nextYear);
        
        // Comparing dates
        boolean isBefore = specificDate.isBefore(LocalDate.of(2023, 2, 1));
        boolean isAfter = specificDate.isAfter(LocalDate.of(2023, 1, 1));
        boolean isEqual = specificDate.isEqual(LocalDate.of(2023, 1, 15));
        
        System.out.println("Is before 2023-02-01: " + isBefore);
        System.out.println("Is after 2023-01-01: " + isAfter);
        System.out.println("Is equal to 2023-01-15: " + isEqual);
        
        // Date calculations
        LocalDate date1 = LocalDate.of(2023, 1, 15);
        LocalDate date2 = LocalDate.of(2023, 3, 20);
        
        Period period = Period.between(date1, date2);
        System.out.println("Period: " + period.getDays() + " days, " + 
                          period.getMonths() + " months, " + 
                          period.getYears() + " years");
        
        long daysBetween = ChronoUnit.DAYS.between(date1, date2);
        long weeksBetween = ChronoUnit.WEEKS.between(date1, date2);
        long monthsBetween = ChronoUnit.MONTHS.between(date1, date2);
        
        System.out.println("Days between: " + daysBetween);
        System.out.println("Weeks between: " + weeksBetween);
        System.out.println("Months between: " + monthsBetween);
        
        System.out.println();
    }
    
    // ==================== LOCALTIME EXAMPLES ====================
    
    private static void localTimeExamples() {
        System.out.println("2. LocalTime Examples:");
        
        // Creating LocalTime objects
        LocalTime now = LocalTime.now();
        System.out.println("Current time: " + now);
        
        LocalTime specificTime = LocalTime.of(14, 30, 45);
        System.out.println("Specific time: " + specificTime);
        
        LocalTime fromString = LocalTime.parse("14:30:45");
        System.out.println("From string: " + fromString);
        
        // Constants
        System.out.println("Midnight: " + LocalTime.MIDNIGHT);
        System.out.println("Noon: " + LocalTime.NOON);
        System.out.println("Min: " + LocalTime.MIN);
        System.out.println("Max: " + LocalTime.MAX);
        
        // Getting components
        LocalTime time = LocalTime.of(14, 30, 45, 123456789);
        System.out.println("Hour: " + time.getHour());
        System.out.println("Minute: " + time.getMinute());
        System.out.println("Second: " + time.getSecond());
        System.out.println("Nano: " + time.getNano());
        
        // Manipulating time
        LocalTime plusHour = time.plusHours(1);
        LocalTime plusMinutes = time.plusMinutes(30);
        LocalTime plusSeconds = time.plusSeconds(45);
        
        System.out.println("Plus hour: " + plusHour);
        System.out.println("Plus minutes: " + plusMinutes);
        System.out.println("Plus seconds: " + plusSeconds);
        
        // Comparing times
        boolean isBefore = time.isBefore(LocalTime.of(15, 0));
        boolean isAfter = time.isAfter(LocalTime.of(14, 0));
        
        System.out.println("Is before 15:00: " + isBefore);
        System.out.println("Is after 14:00: " + isAfter);
        
        // Time calculations
        LocalTime time1 = LocalTime.of(9, 0);
        LocalTime time2 = LocalTime.of(17, 30);
        
        Duration duration = Duration.between(time1, time2);
        System.out.println("Duration hours: " + duration.toHours());
        System.out.println("Duration minutes: " + duration.toMinutes());
        System.out.println("Duration seconds: " + duration.toSeconds());
        
        long hoursBetween = ChronoUnit.HOURS.between(time1, time2);
        long minutesBetween = ChronoUnit.MINUTES.between(time1, time2);
        
        System.out.println("Hours between: " + hoursBetween);
        System.out.println("Minutes between: " + minutesBetween);
        
        System.out.println();
    }
    
    // ==================== LOCALDATETIME EXAMPLES ====================
    
    private static void localDateTimeExamples() {
        System.out.println("3. LocalDateTime Examples:");
        
        // Creating LocalDateTime objects
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Current date and time: " + now);
        
        LocalDateTime specific = LocalDateTime.of(2023, 1, 15, 14, 30, 45);
        System.out.println("Specific date and time: " + specific);
        
        LocalDate date = LocalDate.of(2023, 1, 15);
        LocalTime time = LocalTime.of(14, 30, 45);
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        System.out.println("From date and time: " + dateTime);
        
        LocalDateTime fromString = LocalDateTime.parse("2023-01-15T14:30:45");
        System.out.println("From string: " + fromString);
        
        // Getting components
        LocalDate dateOnly = dateTime.toLocalDate();
        LocalTime timeOnly = dateTime.toLocalTime();
        
        System.out.println("Date only: " + dateOnly);
        System.out.println("Time only: " + timeOnly);
        
        System.out.println("Year: " + dateTime.getYear());
        System.out.println("Month: " + dateTime.getMonthValue());
        System.out.println("Day: " + dateTime.getDayOfMonth());
        System.out.println("Hour: " + dateTime.getHour());
        System.out.println("Minute: " + dateTime.getMinute());
        System.out.println("Second: " + dateTime.getSecond());
        
        // Manipulating date and time
        LocalDateTime plusDays = dateTime.plusDays(1);
        LocalDateTime plusHours = dateTime.plusHours(2);
        LocalDateTime plusWeeks = dateTime.plusWeeks(1);
        LocalDateTime plusMonths = dateTime.plusMonths(1);
        
        System.out.println("Plus days: " + plusDays);
        System.out.println("Plus hours: " + plusHours);
        System.out.println("Plus weeks: " + plusWeeks);
        System.out.println("Plus months: " + plusMonths);
        
        // Chaining operations
        LocalDateTime chained = LocalDateTime.now()
            .plusDays(1)
            .plusHours(2)
            .withMinute(0)
            .withSecond(0)
            .withNano(0);
        
        System.out.println("Chained operations: " + chained);
        
        System.out.println();
    }
    
    // ==================== ZONEDDATETIME EXAMPLES ====================
    
    private static void zonedDateTimeExamples() {
        System.out.println("4. ZonedDateTime Examples:");
        
        // Creating ZonedDateTime objects
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println("Current zoned date time: " + now);
        
        ZoneId tokyoZone = ZoneId.of("Asia/Tokyo");
        ZonedDateTime tokyoTime = ZonedDateTime.now(tokyoZone);
        System.out.println("Tokyo time: " + tokyoTime);
        
        LocalDateTime localDateTime = LocalDateTime.of(2023, 1, 15, 14, 30, 45);
        ZonedDateTime zoned = localDateTime.atZone(tokyoZone);
        System.out.println("Zoned from local: " + zoned);
        
        // Getting components
        LocalDateTime localDateTime2 = tokyoTime.toLocalDateTime();
        LocalDate localDate = tokyoTime.toLocalDate();
        LocalTime localTime = tokyoTime.toLocalTime();
        ZoneId zoneId = tokyoTime.getZone();
        ZoneOffset offset = tokyoTime.getOffset();
        
        System.out.println("Local date time: " + localDateTime2);
        System.out.println("Local date: " + localDate);
        System.out.println("Local time: " + localTime);
        System.out.println("Zone ID: " + zoneId);
        System.out.println("Zone offset: " + offset);
        
        // Converting between timezones
        ZoneId newYorkZone = ZoneId.of("America/New_York");
        ZoneId londonZone = ZoneId.of("Europe/London");
        
        ZonedDateTime newYorkTime = tokyoTime.withZoneSameInstant(newYorkZone);
        ZonedDateTime londonTime = tokyoTime.withZoneSameInstant(londonZone);
        
        System.out.println("Tokyo: " + tokyoTime);
        System.out.println("New York: " + newYorkTime);
        System.out.println("London: " + londonTime);
        
        // Available time zones
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        System.out.println("Number of available time zones: " + availableZoneIds.size());
        
        // Common time zones
        ZoneId utc = ZoneId.of("UTC");
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        ZoneId newYork = ZoneId.of("America/New_York");
        ZoneId london = ZoneId.of("Europe/London");
        
        ZonedDateTime utcTime = ZonedDateTime.now(utc);
        System.out.println("UTC: " + utcTime);
        
        // Converting to Instant
        Instant instant = tokyoTime.toInstant();
        System.out.println("Instant: " + instant);
        
        System.out.println();
    }
    
    // ==================== PERIOD AND DURATION EXAMPLES ====================
    
    private static void periodAndDurationExamples() {
        System.out.println("5. Period and Duration Examples:");
        
        // Period examples
        Period period1 = Period.of(1, 2, 15);  // 1 year, 2 months, 15 days
        Period period2 = Period.ofYears(1);
        Period period3 = Period.ofMonths(6);
        Period period4 = Period.ofDays(30);
        
        System.out.println("Period 1: " + period1);
        System.out.println("Period 2: " + period2);
        System.out.println("Period 3: " + period3);
        System.out.println("Period 4: " + period4);
        
        LocalDate date1 = LocalDate.of(2023, 1, 15);
        LocalDate date2 = LocalDate.of(2024, 3, 30);
        Period period = Period.between(date1, date2);
        
        System.out.println("Period between dates: " + period.getYears() + " years, " +
                          period.getMonths() + " months, " + period.getDays() + " days");
        
        // Adding/subtracting periods
        LocalDate newDate = date1.plus(period);
        LocalDate oldDate = date2.minus(period);
        
        System.out.println("Date1 plus period: " + newDate);
        System.out.println("Date2 minus period: " + oldDate);
        
        // Duration examples
        Duration duration1 = Duration.ofDays(1);
        Duration duration2 = Duration.ofHours(12);
        Duration duration3 = Duration.ofMinutes(30);
        Duration duration4 = Duration.ofSeconds(45);
        Duration duration5 = Duration.ofMillis(500);
        Duration duration6 = Duration.ofNanos(1000000);
        
        System.out.println("Duration 1: " + duration1);
        System.out.println("Duration 2: " + duration2);
        System.out.println("Duration 3: " + duration3);
        System.out.println("Duration 4: " + duration4);
        System.out.println("Duration 5: " + duration5);
        System.out.println("Duration 6: " + duration6);
        
        LocalTime time1 = LocalTime.of(9, 0);
        LocalTime time2 = LocalTime.of(17, 30);
        Duration duration = Duration.between(time1, time2);
        
        System.out.println("Duration between times: " + duration.toHours() + " hours, " +
                          duration.toMinutes() + " minutes, " + duration.toSeconds() + " seconds");
        
        // Adding/subtracting durations
        LocalTime newTime = time1.plus(duration);
        LocalTime oldTime = time2.minus(duration);
        
        System.out.println("Time1 plus duration: " + newTime);
        System.out.println("Time2 minus duration: " + oldTime);
        
        System.out.println();
    }
    
    // ==================== FORMATTING AND PARSING EXAMPLES ====================
    
    private static void formattingAndParsingExamples() {
        System.out.println("6. Formatting and Parsing Examples:");
        
        LocalDateTime dateTime = LocalDateTime.of(2023, 1, 15, 14, 30, 45);
        
        // Predefined formatters
        DateTimeFormatter isoDate = DateTimeFormatter.ISO_LOCAL_DATE;
        DateTimeFormatter isoTime = DateTimeFormatter.ISO_LOCAL_TIME;
        DateTimeFormatter isoDateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        
        System.out.println("ISO date: " + dateTime.format(isoDate));
        System.out.println("ISO time: " + dateTime.format(isoTime));
        System.out.println("ISO date time: " + dateTime.format(isoDateTime));
        
        // Custom formatters
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        DateTimeFormatter customFormatter2 = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");
        
        String customString = dateTime.format(customFormatter);
        String customString2 = dateTime.format(customFormatter2);
        
        System.out.println("Custom format: " + customString);
        System.out.println("Custom format 2: " + customString2);
        
        // Localized formatters
        DateTimeFormatter localizedFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        DateTimeFormatter localizedDateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        
        String localizedString = dateTime.format(localizedFormatter);
        String localizedDateString = dateTime.format(localizedDateFormatter);
        
        System.out.println("Localized format: " + localizedString);
        System.out.println("Localized date format: " + localizedDateString);
        
        // Parsing examples
        LocalDateTime parsed1 = LocalDateTime.parse("2023-01-15T14:30:45");
        LocalDateTime parsed2 = LocalDateTime.parse("15/01/2023 14:30:45", customFormatter);
        
        System.out.println("Parsed 1: " + parsed1);
        System.out.println("Parsed 2: " + parsed2);
        
        // Parsing with locale
        DateTimeFormatter localizedParser = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            .withLocale(Locale.US);
        
        try {
            LocalDateTime parsed3 = LocalDateTime.parse("Jan 15, 2023, 2:30:45 PM", localizedParser);
            System.out.println("Parsed with locale: " + parsed3);
        } catch (Exception e) {
            System.out.println("Parsing with locale failed: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    // ==================== TEMPORAL ADJUSTERS EXAMPLES ====================
    
    private static void temporalAdjustersExamples() {
        System.out.println("7. Temporal Adjusters Examples:");
        
        LocalDate date = LocalDate.of(2023, 1, 15);
        
        // Built-in adjusters
        LocalDate firstDayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate firstDayOfNextMonth = date.with(TemporalAdjusters.firstDayOfNextMonth());
        LocalDate nextMonday = date.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDate previousFriday = date.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
        LocalDate lastDayOfYear = date.with(TemporalAdjusters.lastDayOfYear());
        LocalDate firstDayOfYear = date.with(TemporalAdjusters.firstDayOfYear());
        
        System.out.println("Original date: " + date);
        System.out.println("First day of month: " + firstDayOfMonth);
        System.out.println("Last day of month: " + lastDayOfMonth);
        System.out.println("First day of next month: " + firstDayOfNextMonth);
        System.out.println("Next Monday: " + nextMonday);
        System.out.println("Previous Friday: " + previousFriday);
        System.out.println("Last day of year: " + lastDayOfYear);
        System.out.println("First day of year: " + firstDayOfYear);
        
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
        
        LocalDate friday = LocalDate.of(2023, 1, 13); // Friday
        LocalDate nextWorking = friday.with(nextWorkingDay);
        System.out.println("Friday: " + friday);
        System.out.println("Next working day: " + nextWorking);
        
        System.out.println();
    }
    
    // ==================== PRACTICAL EXAMPLES ====================
    
    private static void practicalExamples() {
        System.out.println("8. Practical Examples:");
        
        // Date range generation
        LocalDate start = LocalDate.of(2023, 1, 1);
        LocalDate end = LocalDate.of(2023, 1, 31);
        List<LocalDate> dateRange = generateDateRange(start, end);
        System.out.println("Date range size: " + dateRange.size());
        System.out.println("First date: " + dateRange.get(0));
        System.out.println("Last date: " + dateRange.get(dateRange.size() - 1));
        
        // Business day calculator
        LocalDate testDate = LocalDate.of(2023, 1, 15);
        boolean isBusinessDay = isBusinessDay(testDate);
        LocalDate nextBusiness = nextBusinessDay(testDate);
        int businessDays = businessDaysBetween(start, end);
        
        System.out.println("Is business day: " + isBusinessDay);
        System.out.println("Next business day: " + nextBusiness);
        System.out.println("Business days in range: " + businessDays);
        
        // Age calculator
        LocalDate birthDate = LocalDate.of(1990, 5, 15);
        int age = calculateAge(birthDate);
        String ageDetailed = calculateAgeDetailed(birthDate);
        
        System.out.println("Age: " + age);
        System.out.println("Age detailed: " + ageDetailed);
        
        // Time zone converter
        ZonedDateTime tokyoTime = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
        ZonedDateTime newYorkTime = convertTimeZone(tokyoTime, ZoneId.of("America/New_York"));
        
        System.out.println("Tokyo time: " + tokyoTime);
        System.out.println("New York time: " + newYorkTime);
        
        // Date validation
        boolean isValid = isValidDate(2023, 2, 29);
        boolean isLeap = isLeapYear(2024);
        
        System.out.println("Is valid date 2023-02-29: " + isValid);
        System.out.println("Is leap year 2024: " + isLeap);
        
        System.out.println();
    }
    
    // ==================== EXERCISE SOLUTIONS ====================
    
    private static void exerciseSolutions() {
        System.out.println("9. Exercise Solutions:");
        
        // Exercise 1: Date Calculator
        DateCalculator calculator = new DateCalculator();
        
        LocalDate date1 = LocalDate.of(2023, 1, 15);
        LocalDate date2 = LocalDate.of(2023, 3, 20);
        
        long daysBetween = calculator.daysBetween(date1, date2);
        LocalDate nextMonday = calculator.nextOccurrenceOfDay(date1, DayOfWeek.MONDAY);
        boolean isWeekend = calculator.isWeekend(date1);
        int quarter = calculator.getQuarter(date1);
        
        System.out.println("Days between: " + daysBetween);
        System.out.println("Next Monday: " + nextMonday);
        System.out.println("Is weekend: " + isWeekend);
        System.out.println("Quarter: " + quarter);
        
        // Exercise 2: Time Zone Converter
        TimeZoneConverter converter = new TimeZoneConverter();
        
        converter.displayCurrentTimeInMultipleZones();
        Duration timeDiff = converter.timeDifferenceBetweenCities("Asia/Tokyo", "America/New_York");
        System.out.println("Time difference: " + timeDiff.toHours() + " hours");
        
        // Exercise 3: Date Range Utilities
        DateRangeUtilities utilities = new DateRangeUtilities();
        
        List<LocalDate> businessDays = utilities.getBusinessDaysInRange(start, end);
        Duration workingHours = utilities.calculateWorkingHours(
            LocalDateTime.of(2023, 1, 15, 9, 0),
            LocalDateTime.of(2023, 1, 20, 17, 0)
        );
        
        System.out.println("Business days in range: " + businessDays.size());
        System.out.println("Working hours: " + workingHours.toHours());
        
        // Exercise 4: Event Scheduler
        EventScheduler scheduler = new EventScheduler();
        
        Event event1 = new Event("Meeting", 
            LocalDateTime.of(2023, 1, 15, 10, 0),
            LocalDateTime.of(2023, 1, 15, 11, 0),
            ZoneId.of("UTC"));
        
        Event event2 = new Event("Lunch", 
            LocalDateTime.of(2023, 1, 15, 12, 0),
            LocalDateTime.of(2023, 1, 15, 13, 0),
            ZoneId.of("UTC"));
        
        scheduler.addEvent(event1);
        scheduler.addEvent(event2);
        
        boolean hasConflict = scheduler.hasConflict(event1, event2);
        System.out.println("Has conflict: " + hasConflict);
        
        System.out.println();
    }
    
    // ==================== UTILITY METHODS ====================
    
    public static List<LocalDate> generateDateRange(LocalDate start, LocalDate end) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate current = start;
        
        while (!current.isAfter(end)) {
            dates.add(current);
            current = current.plusDays(1);
        }
        
        return dates;
    }
    
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
            .filter(DateTimeExamples::isBusinessDay)
            .count();
    }
    
    public static int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
    
    public static String calculateAgeDetailed(LocalDate birthDate) {
        Period period = Period.between(birthDate, LocalDate.now());
        return String.format("%d years, %d months, %d days", 
            period.getYears(), period.getMonths(), period.getDays());
    }
    
    public static ZonedDateTime convertTimeZone(ZonedDateTime source, ZoneId targetZone) {
        return source.withZoneSameInstant(targetZone);
    }
    
    public static String formatInTimeZone(LocalDateTime localDateTime, ZoneId zoneId, 
                                        DateTimeFormatter formatter) {
        return localDateTime.atZone(zoneId).format(formatter);
    }
    
    public static boolean isValidDate(int year, int month, int day) {
        try {
            LocalDate.of(year, month, day);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public static boolean isLeapYear(int year) {
        return LocalDate.of(year, 1, 1).isLeapYear();
    }
    
    // ==================== EXERCISE SOLUTION CLASSES ====================
    
    static class DateCalculator {
        public long daysBetween(LocalDate start, LocalDate end) {
            return ChronoUnit.DAYS.between(start, end);
        }
        
        public LocalDate nextOccurrenceOfDay(LocalDate from, DayOfWeek dayOfWeek) {
            return from.with(TemporalAdjusters.next(dayOfWeek));
        }
        
        public boolean isWeekend(LocalDate date) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
        }
        
        public int getQuarter(LocalDate date) {
            int month = date.getMonthValue();
            return (month - 1) / 3 + 1;
        }
    }
    
    static class TimeZoneConverter {
        public void displayCurrentTimeInMultipleZones() {
            ZoneId[] zones = {
                ZoneId.of("UTC"),
                ZoneId.of("Asia/Tokyo"),
                ZoneId.of("America/New_York"),
                ZoneId.of("Europe/London")
            };
            
            for (ZoneId zone : zones) {
                ZonedDateTime zonedDateTime = ZonedDateTime.now(zone);
                System.out.println(zone + ": " + zonedDateTime.format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z")));
            }
        }
        
        public Duration timeDifferenceBetweenCities(String zone1, String zone2) {
            ZonedDateTime time1 = ZonedDateTime.now(ZoneId.of(zone1));
            ZonedDateTime time2 = ZonedDateTime.now(ZoneId.of(zone2));
            
            return Duration.between(time1, time2);
        }
    }
    
    static class DateRangeUtilities {
        public List<LocalDate> getBusinessDaysInRange(LocalDate start, LocalDate end) {
            return start.datesUntil(end.plusDays(1))
                .filter(DateTimeExamples::isBusinessDay)
                .collect(Collectors.toList());
        }
        
        public Duration calculateWorkingHours(LocalDateTime start, LocalDateTime end) {
            Duration totalDuration = Duration.between(start, end);
            
            // Simple calculation - in real world, you'd need to account for weekends, holidays, etc.
            long workingDays = start.toLocalDate().datesUntil(end.toLocalDate().plusDays(1))
                .filter(DateTimeExamples::isBusinessDay)
                .count();
            
            return Duration.ofHours(workingDays * 8); // Assuming 8-hour workday
        }
    }
    
    static class EventScheduler {
        private final List<Event> events = new ArrayList<>();
        
        public void addEvent(Event event) {
            events.add(event);
        }
        
        public boolean hasConflict(Event event1, Event event2) {
            return event1.getStart().isBefore(event2.getEnd()) && 
                   event2.getStart().isBefore(event1.getEnd());
        }
        
        public List<Event> getEvents() {
            return new ArrayList<>(events);
        }
    }
    
    static class Event {
        private final String name;
        private final LocalDateTime start;
        private final LocalDateTime end;
        private final ZoneId timeZone;
        
        public Event(String name, LocalDateTime start, LocalDateTime end, ZoneId timeZone) {
            this.name = name;
            this.start = start;
            this.end = end;
            this.timeZone = timeZone;
        }
        
        public String getName() { return name; }
        public LocalDateTime getStart() { return start; }
        public LocalDateTime getEnd() { return end; }
        public ZoneId getTimeZone() { return timeZone; }
        
        @Override
        public String toString() {
            return name + " (" + start + " - " + end + ")";
        }
    }
} 