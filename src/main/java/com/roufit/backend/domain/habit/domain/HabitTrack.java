package com.roufit.backend.domain.habit.domain;

import com.roufit.backend.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "habit_level")
public class HabitTrack {

    @Id @GeneratedValue
    @Column(name = "habit_level_id")
    private Long id;

    @Column(nullable = false)
    private LocalDate recordDate;

    @Column(nullable = false)
    private Boolean isCompleteWorkout;

    @Column(nullable = false)
    private Integer consecutiveDays;

    @Column(nullable = false)
    private LocalDate consecutiveStartDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public HabitTrack(LocalDate recordDate, Boolean isCompleteWorkout, User user) {
        this.recordDate = recordDate;
        this.isCompleteWorkout = isCompleteWorkout;
        this.user = user;
    }

    public void updateConsecutiveDays(List<HabitTrack> lastThreeDays) {
        HabitTrack recentHabit = getRecentHabit(lastThreeDays);
        if(recentHabit == null) {
            this.consecutiveDays = 1;
            this.consecutiveStartDate = this.recordDate;
            return;
        }
        this.consecutiveDays = calculateConsecutiveDays(recentHabit);
        this.consecutiveStartDate = recentHabit.getConsecutiveStartDate();
    }

    private int calculateConsecutiveDays(HabitTrack recentHabit) {
        return recentHabit.getConsecutiveDays() + calculateDays(recentHabit.getRecordDate(), LocalDate.now());
    }

    private HabitTrack getRecentHabit(List<HabitTrack> habitTracks) {
        return habitTracks.stream()
                .filter(habitTrack -> !habitTrack.recordDate.equals(recordDate))
                .max(Comparator.comparing(HabitTrack::getRecordDate))
                .orElse(null);
    }

    private int calculateDays(LocalDate from, LocalDate to) {
        return Period.between(from, to).getDays();
    }


}
