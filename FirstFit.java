import java.util.ArrayList;

public class FirstFit extends MemoryAllocationAlgorithm {
    
    public FirstFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }

    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        boolean fit = false;
        int address = -1;
        /* TODO: you need to add some code here
         * Hint: this should return the memory address where the process was
         * loaded into if the process fits. In case the process doesn't fit, it
         * should return -1. */

        int block_start_address = 0;

        int best_block_start = 0;
        int best_block_end = 0;
        int best_memslot_index = 0;
        int memslot_index=0;

        for(int i=0;i<this.availableBlockSizes.length;i++){
            int block_size = this.availableBlockSizes[i];

            int block_end_address = block_start_address + this.availableBlockSizes[i];

            if(block_size<p.getMemoryRequirements()){
                block_start_address += this.availableBlockSizes[i];
                continue;
            }

            boolean found_at_least_one = false;
            int previous_end = block_start_address;


            while(memslot_index<currentlyUsedMemorySlots.size()){

                MemorySlot memslot = currentlyUsedMemorySlots.get(memslot_index);
                if(memslot.getBlockStart() != block_start_address){
                    break;
                }
                int distance = memslot.getStart()-previous_end;
                if(distance>=p.getMemoryRequirements()){
                    fit = true;
                    address = previous_end;
                    found_at_least_one = true;
                    best_memslot_index = memslot_index;
                    break;
                }
                previous_end = memslot.getEnd();
                memslot_index++;
            }
            if(!fit) {
                int distance = block_end_address - previous_end;
                if (distance >= p.getMemoryRequirements()) {
                    fit = true;
                    address = previous_end;
                    found_at_least_one = true;
                    best_memslot_index = memslot_index;
                }
            }
            if(found_at_least_one){
                best_block_start = block_start_address;
                best_block_end = block_end_address;
                break;
            }

            block_start_address += block_size;
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
