import java.util.ArrayList;

public class NextFit extends MemoryAllocationAlgorithm {
    
    public NextFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }

    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        boolean fit = false;
        int address = -1;
        /* TODO: you need to add some code here
         * Hint: this should return the memory address where the process was
         * loaded into if the process fits. In case the process doesn't fit, it
         * should return -1. */

        MemorySlot last_slot = currentlyUsedMemorySlots.get(currentlyUsedMemorySlots.size()-1);

        return address;
    }

}
