import java.util.ArrayList;

public class BestFit extends MemoryAllocationAlgorithm {
    
    public BestFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }

    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        boolean fit = false;
        int address = -1;
        /* TODO: you need to add some code here
         * Hint: this should return the memory address where the process was
         * loaded into if the process fits. In case the process doesn't fit, it
         * should return -1. */
        
        int bestDifference = 0x0FFFFFFF;
        int lastAddress = 0;
        for(int blockIndex=0;blockIndex<this.availableBlockSizes.length;blockIndex++){
            int block = this.availableBlockSizes[blockIndex];
            if(this.availableBlockSizes[blockIndex]<p.getMemoryRequirements()){
                continue;
            }
            
            MemorySlot prevMemSlot;
            int lastIndex = 0;
            boolean foundMemSlot = false;

            for(lastIndex=0;lastIndex<currentlyUsedMemorySlots.size();lastIndex++){
                MemorySlot memSlot = currentlyUsedMemorySlots.get(lastIndex);
                if(memSlot.getBlockStart() == blockIndex){
                    prevMemSlot = memSlot;
                    foundMemSlot = true;
                    break;
                }
            }

            if(!foundMemSlot){
                break;   
            }

            for(int slotIndex=lastIndex+1;slotIndex<currentlyUsedMemorySlots.size();slotIndex++){
                MemorySlot memSlot = currentlyUsedMemorySlots.get(slotIndex);
                int spotSize = 0;
                if(memSlot.getBlockStart() == blockIndex){
                    spotSize = memSlot.getStart()-prevMemSlot.getEnd();

                    

                }
                

            }

            
        }
        
    


        return address;
    }

}
