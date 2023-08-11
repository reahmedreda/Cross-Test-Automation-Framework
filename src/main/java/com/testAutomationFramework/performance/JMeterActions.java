package com.testAutomationFramework.performance;

import com.testAutomationFramework.api.ApiRequestDto;

import java.time.Duration;
import java.util.List;

public class JMeterActions implements PerformanceTestAction{
    @Override
    public void simulateNormalLoad(ApiRequestDto requestDto) {

    }

    @Override
    public void simulateStressLoad(ApiRequestDto requestDto, int maxUsers) {

    }

    @Override
    public void simulateOverload(ApiRequestDto requestDto, int usersToExceedCapacity) {

    }

    @Override
    public void simulateTrafficSpike(ApiRequestDto requestDto, int peakUsers, int spikeDuration) {

    }

    @Override
    public void simulateEndurance(ApiRequestDto requestDto, int users, int duration) {

    }

    @Override
    public void simulateRamp(ApiRequestDto requestDto, int startUsers, int endUsers, int rampDuration) {

    }

    @Override
    public void simulateMixedWorkload(List<ApiRequestDto> requests) {

    }

    @Override
    public Duration measureRequestTime(ApiRequestDto request) {
        return null;
    }

    @Override
    public void introduceDelay(ApiRequestDto requestDto, Duration delay) {

    }

    @Override
    public Duration measureResponseTime(ApiRequestDto request) {
        return null;
    }
}
