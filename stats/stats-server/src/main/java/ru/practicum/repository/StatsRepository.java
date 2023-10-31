package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.dto.ViewStats;
import ru.practicum.model.EndPointRequestRecord;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndPointRequestRecord, Long> {
    @Query("SELECT new ru.practicum.dto.ViewStats(record.app, record.uri, count(record.id)) " +
            "FROM EndPointRequestRecord as record " +
            "WHERE (record.uri IN :uris) " +
            "AND (record.timestamp BETWEEN :start AND :end) " +
            "GROUP BY record.uri, record.app " +
            "ORDER BY count(record.id) DESC")
    List<ViewStats> getStats(@Param("start") LocalDateTime start,
                             @Param("end") LocalDateTime end,
                             @Param("uris") List<String> uris);

    @Query("SELECT new ru.practicum.dto.ViewStats(record.app, record.uri, count(DISTINCT record.ip)) " +
            "FROM EndPointRequestRecord as record " +
            "WHERE (record.uri IN :uris) " +
            "AND (record.timestamp BETWEEN :start AND :end) " +
            "GROUP BY record.uri, record.app " +
            "ORDER BY count(record.ip) DESC")
    List<ViewStats> getUniqueStats(@Param("start") LocalDateTime start,
                                   @Param("end") LocalDateTime end,
                                   @Param("uris") List<String> uris);
}