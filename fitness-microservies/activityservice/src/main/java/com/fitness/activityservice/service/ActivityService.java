package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    public ActivityResponse trackActivity(ActivityRequest request) {
        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();

        Activity savedActivity = activityRepository.save(activity);
        System.out.println("Activity saved: " + savedActivity);

        return mapToResponse(savedActivity);

    }

    private ActivityResponse mapToResponse(Activity acitivity){

        ActivityResponse activityResponse = new ActivityResponse();

        activityResponse.setId(acitivity.getId());
        activityResponse.setUserId(acitivity.getUserId());
        activityResponse.setType(acitivity.getType());
        activityResponse.setDuration(acitivity.getDuration());
        activityResponse.setCaloriesBurned(acitivity.getCaloriesBurned());
        activityResponse.setStartTime(acitivity.getStartTime());
        activityResponse.setAdditionalMetrics(acitivity.getAdditionalMetrics());
        activityResponse.setCreatedAt(acitivity.getCreatedAt());
        activityResponse.setUpdatedAt(acitivity.getUpdatedAt());


        return activityResponse;
    }

    public List<ActivityResponse> getUserActivities(String userId) {
        List<Activity> activities=activityRepository.findByUserId(userId);
        return activities.stream()
                            .map(this:: mapToResponse)
                            .collect(Collectors.toList());
    }

    public ActivityResponse getActivityById(String activityId) {
        Activity activity=activityRepository.findById(activityId).orElseThrow(()-> new RuntimeException());
        return mapToResponse(activity);

    }
}
