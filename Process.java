
public class Process {
    private ProcessControlBlock pcb;
    private int arrivalTime;
    private int burstTime;
    private int memoryRequirements;
    
    public Process(int arrivalTime, int burstTime, int memoryRequirements) {
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.memoryRequirements = memoryRequirements;
        this.pcb = new ProcessControlBlock();
    }
    
    public ProcessControlBlock getPCB() {
        return this.pcb;
    }
   
    public void run() {
        /* TODO: you need to add some code here
         * Hint: this should run every time a process starts running */
        this.pcb.setState(ProcessState.RUNNING,CPU.clock);
    }
    
    public void waitInBackground() {
        /* TODO: you need to add some code here
         * Hint: this should run every time a process stops running */
        this.pcb.setState(ProcessState.READY,CPU.clock);
    }

    public double getWaitingTime() {
        /* TODO: you need to add some code here
         * and change the return value */

        return this.getTurnAroundTime()-this.burstTime;
    }
    
    public double getResponseTime() {
        /* TODO: you need to add some code here
         * and change the return value */
        return this.pcb.getStartTimes().get(0);
    }
    
    public double getTurnAroundTime() {
        /* TODO: you need to add some code here
         * and change the return value */
        return this.pcb.getStopTimes().get(this.pcb.getStopTimes().size())-this.arrivalTime;
    }

    public int getMemoryRequirements(){
        return this.memoryRequirements;
    }
}
