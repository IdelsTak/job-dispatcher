package com.github.idelstak.job.dispatcher;

import java.util.*;

public class Dispatcher {

    private static final int QUANTUM_TIME = 5;

    public static void main(String[] args) {
        Queue<Job> jobsQueue = new LinkedList<>();
        // Add jobs to the queue
        jobsQueue.offer(new Job(1, 0, 20, 3));
        jobsQueue.offer(new Job(2, 5, 15, 2));
        jobsQueue.offer(new Job(3, 10, 30, 1));
        jobsQueue.offer(new Job(4, 15, 25, 2));
        jobsQueue.offer(new Job(5, 20, 10, 3));
        jobsQueue.offer(new Job(6, 25, 5, 1));
        jobsQueue.offer(new Job(7, 30, 35, 2));
        jobsQueue.offer(new Job(8, 35, 15, 3));
        jobsQueue.offer(new Job(9, 40, 20, 1));
        jobsQueue.offer(new Job(10, 45, 30, 2));

        List<Integer> turnaroundTimes = new ArrayList<>();
        Queue<Job> jobsCopy = new LinkedList<>(jobsQueue);

        processJobs(jobsCopy, turnaroundTimes);

        // Update the jobsQueue with the turnaround times
        for (int i = 0; i < turnaroundTimes.size(); i++) {
            jobsQueue.peek().setTurnaroundTime(turnaroundTimes.get(i));
            jobsQueue.offer(jobsQueue.poll());
        }

        printJobStats(jobsQueue);

    }

    private static void processJobs(Queue<Job> jobsQueue,
            List<Integer> turnaroundTimes) {
        int currentTime = 0;
        int totalTurnaroundTime = 0;
        int completedJobs = 0;
        
        // Implementation of the round-robin scheduling algorithm
        while (!jobsQueue.isEmpty()) {
            // Get the next job from the queue
            Job currentJob = jobsQueue.poll();
            int remainingTime = currentJob.getCpuBurstTime();

            // If the current time is less than the arrival time of the job, 
            // set the current time to the arrival time of the job
            if (currentTime < currentJob.getArrivalTime()) {
                currentTime = currentJob.getArrivalTime();
            }

            // Process the job for the quantum time or until it completes
            while (remainingTime > 0) {
                // Decrement the remaining time by the quantum time and 
                // increment the current time by the quantum time
                remainingTime -= QUANTUM_TIME;
                currentTime += QUANTUM_TIME;

                // If the job completes, calculate its turnaround time, 
                // update the total turnaround time, and increment the completed jobs count
                if (currentTime >= currentJob.getArrivalTime() + currentJob.getCpuBurstTime()) {
                    int turnaroundTime = currentTime - currentJob.getArrivalTime();
                    turnaroundTimes.add(turnaroundTime);
                    currentJob.setTurnaroundTime(turnaroundTime);
                    totalTurnaroundTime += currentJob.getTurnaroundTime();
                    completedJobs++;
                    break;
                }
            }

            // If the job has not completed, update its 
            // burst time and add it back to the queue
            if (remainingTime > 0) {
                currentJob.setCpuBurstTime(remainingTime);
                jobsQueue.offer(currentJob);
            }
        }

        // Calculate and print the average turnaround time of all completed jobs
        double averageTurnaroundTime = (double) totalTurnaroundTime / completedJobs;
        System.out.println("Average turnaround time: " + averageTurnaroundTime);
    }

    private static void printJobStats(Queue<Job> jobsQueue) {
        System.out.println("Job statistics:");
        System.out.println("Job ID\tArrival Time\tBurst Time\tPriority\tTurnaround Time");

        for (Job job : jobsQueue) {
            System.out.printf("%d\t%d\t\t%d\t\t%d\t\t%d%n", job.getJobId(), job.getArrivalTime(), job.getCpuBurstTime(), job.getPriorityLevel(), job.getTurnaroundTime());
        }
    }
}
