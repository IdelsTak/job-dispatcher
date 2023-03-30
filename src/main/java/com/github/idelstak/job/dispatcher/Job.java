package com.github.idelstak.job.dispatcher;

public class Job {

    private int jobId;
    private int arrivalTime;
    private int cpuBurstTime;
    private int priorityLevel;
    private int remainingTime;
    private int turnaroundTime;

    public Job(int jobId, int arrivalTime, int cpuBurstTime, int priorityLevel) {
        this.jobId = jobId;
        this.arrivalTime = arrivalTime;
        this.cpuBurstTime = cpuBurstTime;
        this.priorityLevel = priorityLevel;
        this.remainingTime = cpuBurstTime;
        this.turnaroundTime = 0;
    }

    public int getJobId() {
        return jobId;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public int getCpuBurstTime() {
        return cpuBurstTime;
    }

    public void setCpuBurstTime(int cpuBurstTime) {
        this.cpuBurstTime = cpuBurstTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

}
