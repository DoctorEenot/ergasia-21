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

        int counter = 0;
        int block_index = 0;
        if(currentlyUsedMemorySlots.size()!=0) {
            block_index = (currentlyUsedMemorySlots.get(currentlyUsedMemorySlots.size() - 1).getBlockStart() + 1) % this.availableBlockSizes.length;
        }

        while(!fit && counter != this.availableBlockSizes.length){
            int block_size = this.availableBlockSizes[block_index];
            if(block_size >= p.getMemoryRequirements()){
                fit = true;
                for(MemorySlot memslot:currentlyUsedMemorySlots){
                    if(memslot.getBlockStart()==block_index){
                        fit = false;
                        break;
                    }
                }
                if(fit){
                    address = block_index;
                }
            }
            block_index = (block_index+1)%this.availableBlockSizes.length;
            counter++;
        }

        if(fit){
            MemorySlot new_memslot = new MemorySlot(address,
                    address+p.getMemoryRequirements(),
                    address,
                    address+this.availableBlockSizes[address]);
            currentlyUsedMemorySlots.add(new_memslot);
        }

        return address;
    }

}
