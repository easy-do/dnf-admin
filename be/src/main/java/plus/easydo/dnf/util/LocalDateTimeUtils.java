package plus.easydo.dnf.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * @author laoyu
 * @version 1.0
 * @description 时间工具类
 * @date 2023/10/14
 */

public class LocalDateTimeUtils {

    private LocalDateTimeUtils() {
    }

    /**
     * 当前时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * Date 转 LocalDateTime
     *
     * @param date date
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime convert(Date date) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime 转 Date
     *
     * @param localDateTime localDateTime
     * @return java.util.Date
     * @author laoyu
     * @date 2022/5/9
     */
    public static Date convert(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 今天开始时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime todayStartTime() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
    }

    /**
     * 今天结束时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime todayEndTime() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
    }

    /**
     * 昨天开始时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime yesterdayStartTime() {
        return LocalDateTime.of(LocalDate.now().minus(1L, ChronoUnit.DAYS), LocalTime.MIN);
    }

    /**
     * 昨天结束时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime yesterdayEndTime() {
        return LocalDateTime.of(LocalDate.now().minus(1L, ChronoUnit.DAYS), LocalTime.MAX);
    }

    /**
     * 最近7天开始时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime last7DaysStartTime() {
        return LocalDateTime.of(LocalDate.now().minus(6L, ChronoUnit.DAYS), LocalTime.MIN);
    }

    /**
     * 最近15天开始时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime lastStartTime(long day) {
        return LocalDateTime.of(LocalDate.now().minus(day-1L, ChronoUnit.DAYS), LocalTime.MIN);
    }

    /**
     * 最近7天结束时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime last7DaysEndTime() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
    }

    /**
     * 最近30天开始时间
     *
     * @return
     */
    public static LocalDateTime last30DaysStartTime() {
        return LocalDateTime.of(LocalDate.now().minus(29L, ChronoUnit.DAYS), LocalTime.MIN);
    }

    /**
     * 最近30天结束时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime last30DaysEndTime() {
        return LocalDateTime.of(LocalDate.now().minus(29L, ChronoUnit.DAYS), LocalTime.MAX);
    }

    /**
     * 最近一年开始时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime last1YearStartTime() {
        return LocalDateTime.of(LocalDate.now().minus(1L, ChronoUnit.YEARS).plus(1L, ChronoUnit.DAYS), LocalTime.MIN);
    }

    /**
     * 最近一年结束时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime last1YearEndTime() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
    }

    /**
     * 本周开始时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime weekStartTime() {
        LocalDate now = LocalDate.now();
        return LocalDateTime.of(now.minusDays(now.getDayOfWeek().getValue() - 1L), LocalTime.MIN);
    }

    /**
     * 本周结束时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime weekEndTime() {
        LocalDate now = LocalDate.now();
        return LocalDateTime.of(now.plusDays(7L - now.getDayOfWeek().getValue()), LocalTime.MAX);
    }

    /**
     * 本月开始时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime monthStartTime() {
        return LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
    }

    /**
     * 指定月份开始时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime thisYearMonthStartTime(int month) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate date = LocalDate.of(now.getYear(), month, 14);
        return LocalDateTime.of(date.with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
    }

    /**
     * 指定年月开始时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime yearMonthStartTime(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 14);
        return LocalDateTime.of(date.with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
    }

    /**
     * 本月结束时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime monthEndTime() {
        return LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);
    }

    /**
     * 今年指定月份结束时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime thisYearMonthEndTime(int month) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate date = LocalDate.of(now.getYear(), month, 14);
        return LocalDateTime.of(date.with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);
    }

    /**
     *  指定年月份结束时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime yearMonthEndTime(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 14);
        return LocalDateTime.of(date.with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);
    }

    /**
     * 本季度开始时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime quarterStartTime() {
        LocalDate now = LocalDate.now();
        Month month = Month.of(now.getMonth().firstMonthOfQuarter().getValue());
        return LocalDateTime.of(LocalDate.of(now.getYear(), month, 1), LocalTime.MIN);
    }

    /**
     * 本季度结束时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime quarterEndTime() {
        LocalDate now = LocalDate.now();
        Month month = Month.of(now.getMonth().firstMonthOfQuarter().getValue()).plus(2L);
        return LocalDateTime.of(LocalDate.of(now.getYear(), month, month.length(now.isLeapYear())), LocalTime.MAX);
    }

    /**
     * 本半年开始时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime halfYearStartTime() {
        LocalDate now = LocalDate.now();
        Month month = (now.getMonthValue() > 6) ? Month.JULY : Month.JANUARY;
        return LocalDateTime.of(LocalDate.of(now.getYear(), month, 1), LocalTime.MIN);
    }

    /**
     * 本半年结束时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime halfYearEndTime() {
        LocalDate now = LocalDate.now();
        Month month = (now.getMonthValue() > 6) ? Month.DECEMBER : Month.JUNE;
        return LocalDateTime.of(LocalDate.of(now.getYear(), month, month.length(now.isLeapYear())), LocalTime.MAX);
    }

    /**
     * 本年开始时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime yearStartTime() {
        return LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.firstDayOfYear()), LocalTime.MIN);
    }

    /**
     * 本年结束时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime yearEndTime() {
        return LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.lastDayOfYear()), LocalTime.MAX);
    }

    /**
     * 上周开始时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime lastWeekStartTime() {
        LocalDate lastWeek = LocalDate.now().minus(1L, ChronoUnit.WEEKS);
        return LocalDateTime.of(lastWeek.minusDays(lastWeek.getDayOfWeek().getValue() - 1L), LocalTime.MIN);
    }

    /**
     * 上周结束时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime lastWeekEndTime() {
        LocalDate lastWeek = LocalDate.now().minus(1L, ChronoUnit.WEEKS);
        return LocalDateTime.of(lastWeek.plusDays(7L - lastWeek.getDayOfWeek().getValue()), LocalTime.MAX);
    }

    /**
     * 上月开始时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime lastMonthStartTime() {
        return LocalDateTime.of(LocalDate.now().minus(1L, ChronoUnit.MONTHS).with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
    }

    /**
     * 上月结束时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime lastMonthEndTime() {
        return LocalDateTime.of(LocalDate.now().minus(1L, ChronoUnit.MONTHS).with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);
    }

    /**
     * 上季度开始时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime lastQuarterStartTime() {
        LocalDate now = LocalDate.now();
        Month firstMonthOfQuarter = Month.of(now.getMonth().firstMonthOfQuarter().getValue());
        Month firstMonthOfLastQuarter = firstMonthOfQuarter.minus(3L);
        int yearOfLastQuarter = firstMonthOfQuarter.getValue() < 4 ? now.getYear() - 1 : now.getYear();
        return LocalDateTime.of(LocalDate.of(yearOfLastQuarter, firstMonthOfLastQuarter, 1), LocalTime.MIN);
    }

    /**
     * 上季度结束时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime lastQuarterEndTime() {
        LocalDate now = LocalDate.now();
        Month firstMonthOfQuarter = Month.of(now.getMonth().firstMonthOfQuarter().getValue());
        Month firstMonthOfLastQuarter = firstMonthOfQuarter.minus(1L);
        int yearOfLastQuarter = firstMonthOfQuarter.getValue() < 4 ? now.getYear() - 1 : now.getYear();
        return LocalDateTime.of(LocalDate.of(yearOfLastQuarter, firstMonthOfLastQuarter, firstMonthOfLastQuarter.maxLength()), LocalTime.MAX);
    }

    /**
     * 上半年开始时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime lastHalfYearStartTime() {
        LocalDate now = LocalDate.now();
        int lastHalfYear = (now.getMonthValue() > 6) ? now.getYear() : now.getYear() - 1;
        Month firstMonthOfLastHalfYear = (now.getMonthValue() > 6) ? Month.JANUARY : Month.JULY;
        return LocalDateTime.of(LocalDate.of(lastHalfYear, firstMonthOfLastHalfYear, 1), LocalTime.MIN);
    }

    /**
     * 上半年结束时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime lastHalfYearEndTime() {
        LocalDate now = LocalDate.now();
        int lastHalfYear = (now.getMonthValue() > 6) ? now.getYear() : now.getYear() - 1;
        Month lastMonthOfLastHalfYear = (now.getMonthValue() > 6) ? Month.JUNE : Month.DECEMBER;
        return LocalDateTime.of(LocalDate.of(lastHalfYear, lastMonthOfLastHalfYear, lastMonthOfLastHalfYear.maxLength()), LocalTime.MAX);
    }

    /**
     * 上一年开始时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime lastYearStartTime() {
        return LocalDateTime.of(LocalDate.now().minus(1L, ChronoUnit.YEARS).with(TemporalAdjusters.firstDayOfYear()), LocalTime.MIN);
    }

    /**
     * 上一年结束时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime lastYearEndTime() {
        return LocalDateTime.of(LocalDate.now().minus(1L, ChronoUnit.YEARS).with(TemporalAdjusters.lastDayOfYear()), LocalTime.MAX);
    }

    /**
     * 下周开始时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime nextWeekStartTime() {
        LocalDate nextWeek = LocalDate.now().plus(1L, ChronoUnit.WEEKS);
        return LocalDateTime.of(nextWeek.minusDays(nextWeek.getDayOfWeek().getValue() - 1L), LocalTime.MIN);
    }

    /**
     * 下周结束时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime nextWeekEndTime() {
        LocalDate nextWeek = LocalDate.now().plus(1L, ChronoUnit.WEEKS);
        return LocalDateTime.of(nextWeek.plusDays(7L - nextWeek.getDayOfWeek().getValue()), LocalTime.MAX);
    }

    /**
     * 下月开始时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime nextMonthStartTime() {
        return LocalDateTime.of(LocalDate.now().plus(1L, ChronoUnit.MONTHS).with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
    }

    /**
     * 下月结束时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime nextMonthEndTime() {
        return LocalDateTime.of(LocalDate.now().plus(1L, ChronoUnit.MONTHS).with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);
    }

    /**
     * 下季度开始时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime nextQuarterStartTime() {
        LocalDate now = LocalDate.now();
        Month firstMonthOfQuarter = Month.of(now.getMonth().firstMonthOfQuarter().getValue());
        Month firstMonthOfNextQuarter = firstMonthOfQuarter.plus(3L);
        int yearOfNextQuarter = firstMonthOfQuarter.getValue() > 9 ? now.getYear() + 1 : now.getYear();
        return LocalDateTime.of(LocalDate.of(yearOfNextQuarter, firstMonthOfNextQuarter, 1), LocalTime.MIN);
    }

    /**
     * 下季度结束时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime nextQuarterEndTime() {
        LocalDate now = LocalDate.now();
        Month firstMonthOfQuarter = Month.of(now.getMonth().firstMonthOfQuarter().getValue());
        Month firstMonthOfNextQuarter = firstMonthOfQuarter.plus(5L);
        int yearOfNextQuarter = firstMonthOfQuarter.getValue() > 9 ? now.getYear() + 1 : now.getYear();
        return LocalDateTime.of(LocalDate.of(yearOfNextQuarter, firstMonthOfNextQuarter, firstMonthOfNextQuarter.maxLength()), LocalTime.MAX);
    }

    /**
     * 上半年开始时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime nextHalfYearStartTime() {
        LocalDate now = LocalDate.now();
        int nextHalfYear = (now.getMonthValue() > 6) ? now.getYear() + 1 : now.getYear();
        Month firstMonthOfNextHalfYear = (now.getMonthValue() > 6) ? Month.JANUARY : Month.JULY;
        return LocalDateTime.of(LocalDate.of(nextHalfYear, firstMonthOfNextHalfYear, 1), LocalTime.MIN);
    }

    /**
     * 上半年结束时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime nextHalfYearEndTime() {
        LocalDate now = LocalDate.now();
        int lastHalfYear = (now.getMonthValue() > 6) ? now.getYear() + 1 : now.getYear();
        Month lastMonthOfNextHalfYear = (now.getMonthValue() > 6) ? Month.JUNE : Month.DECEMBER;
        return LocalDateTime.of(LocalDate.of(lastHalfYear, lastMonthOfNextHalfYear, lastMonthOfNextHalfYear.maxLength()), LocalTime.MAX);
    }

    /**
     * 下一年开始时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime nextYearStartTime() {
        return LocalDateTime.of(LocalDate.now().plus(1L,    ChronoUnit.YEARS).with(TemporalAdjusters.firstDayOfYear()), LocalTime.MIN);
    }

    /**
     * 下一年结束时间
     *
     * @return java.time.LocalDateTime
     * @author laoyu
     * @date 2022/5/9
     */
    public static LocalDateTime nextYearEndTime() {
        return LocalDateTime.of(LocalDate.now().plus(1L,  			      ChronoUnit.YEARS).with(TemporalAdjusters.lastDayOfYear()), LocalTime.MAX);
    }
}
