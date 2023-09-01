package com.roufit.backend.domain.workout.dto.request;

import com.roufit.backend.domain.workout.domain.record.SetRecord;
import com.roufit.backend.domain.workout.domain.record.WorkoutRecord;
import com.roufit.backend.domain.workout.domain.template.SetTemplate;
import lombok.*;

@Data
public class SetRecordRequest {

    private Long setTemplateId;
    private String successSet;
    private boolean isCompleted;
    private boolean isSuccess;

    public SetRecord mapToEntity(WorkoutRecord workoutRecord, SetTemplate setTemplate) {
        SetRecord build = SetRecord.builder()
                .workoutRecord(workoutRecord)
                .setTemplate(setTemplate)
                .successSet(successSet)
                .isCompleted(isCompleted)
                .build();
        build.checkIncreasingPerformance();
        return build;
    }
}