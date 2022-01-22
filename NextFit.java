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

        int last_block_start;
        int last_block_index;
        int block_start_address;
        int memslot_index;
        if(currentlyUsedMemorySlots.size()>0) {
            MemorySlot last_slot = currentlyUsedMemorySlots.get(currentlyUsedMemorySlots.size() - 1);
            last_block_start = last_slot.getBlockStart();
            last_block_index = 0;
            block_start_address = last_slot.getBlockStart();
            memslot_index = currentlyUsedMemorySlots.size()-1;
        }else{
            last_block_start = 0;
            last_block_index = 0;
            block_start_address = 0;
            memslot_index = 0;
        }

        // searching for last block index
        while(last_block_start > 0){
            last_block_start -= this.availableBlockSizes[last_block_index];
            last_block_index++;
        }

        //int block_start_address = last_slot.getBlockStart();

        //int memslot_index = currentlyUsedMemorySlots.size()-1;

        int best_block_start = 0;
        int best_block_end = 0;
        int best_memslot_index = 0;

        while(last_block_index<this.availableBlockSizes.length){
            int block_size = this.availableBlockSizes[last_block_index];
            int block_end_address = block_start_address + this.availableBlockSizes[last_block_index];

            if(block_size<p.getMemoryRequirements()){
                block_start_address += this.availableBlockSizes[last_block_index];
                last_block_index = (last_block_index+1)%this.availableBlockSizes.length;
                continue;
            }



            last_block_index = (last_block_index+1)%this.availableBlockSizes.length;
        }


        if(fit){
            MemorySlot memslot = new MemorySlot(address,
                    address+p.getMemoryRequirements(),
                    best_block_start,
                    best_block_end);

            if(currentlyUsedMemorySlots.size() != 0 &&
                    best_memslot_index<currentlyUsedMemorySlots.size()){
                if(currentlyUsedMemorySlots.get(best_memslot_index).getStart()>best_block_start){
                    currentlyUsedMemorySlots.add(best_memslot_index,memslot);
                }else{
                    currentlyUsedMemorySlots.add(best_memslot_index+1,memslot);
                }
            }else {
                currentlyUsedMemorySlots.add(best_memslot_index, memslot);
            }
            return address;
        }else{
            return -1;
        }
    }

}
