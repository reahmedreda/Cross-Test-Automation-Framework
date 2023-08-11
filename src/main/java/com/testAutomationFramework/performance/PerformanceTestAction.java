package com.testAutomationFramework.performance;

import com.testAutomationFramework.api.ApiRequestDto;

import java.time.Duration;
import java.util.List;

public interface PerformanceTestAction {

    // Simulate normal user load
    void simulateNormalLoad(ApiRequestDto requestDto);

    // Simulate stress conditions by gradually increasing load
    void simulateStressLoad(ApiRequestDto requestDto,int maxUsers);

    // Simulate overload conditions by exceeding system capacity
    void simulateOverload(ApiRequestDto requestDto,int usersToExceedCapacity);

    // Simulate spike in traffic by rapidly increasing and decreasing load
    void simulateTrafficSpike(ApiRequestDto requestDto,int peakUsers, int spikeDuration);

    // Simulate constant load over an extended period
    void simulateEndurance(ApiRequestDto requestDto,int users, int duration);

    // Simulate ramp-up and ramp-down load pattern
    void simulateRamp(ApiRequestDto requestDto,int startUsers, int endUsers, int rampDuration);

    // Simulate mixed workload with different types of actions
    void simulateMixedWorkload(List<ApiRequestDto> requests);

    // Measure the time taken for a specific request to complete
    Duration measureRequestTime(ApiRequestDto request);

    // Introduce a delay between requests
    void introduceDelay(ApiRequestDto requestDto,Duration delay);

    // Measure the system response time under current load
    Duration measureResponseTime(ApiRequestDto request);
}

