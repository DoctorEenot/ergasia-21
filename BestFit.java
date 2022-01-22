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

        int best_distance = 0x0FFFFFFF;
        int block_start_address = 0;

        int best_block_start = 0;
        int best_block_end = 0;

        for(int i=0;i<this.availableBlockSizes.length;i++){
            int block_size = this.availableBlockSizes[i];

            int block_end_address = block_start_address + this.availableBlockSizes[i];

            if(this.availableBlockSizes[i]<p.getMemoryRequirements()){
                block_start_address += this.availableBlockSizes[i];
                continue;
            }

            boolean found_at_least_one = false;
            int previous_end = block_start_address;
            for(int memslot_index=0;
                memslot_index<currentlyUsedMemorySlots.size();
                memslot_index++){
                MemorySlot memslot = currentlyUsedMemorySlots.get(memslot_index);
                if(memslot.getBlockStart() != block_start_address){
                    break;
                }
                int distance = memslot.getStart()-previous_end;
                if(distance>=p.getMemoryRequirements()&&
                    distance<best_distance){
                    fit = true;
                    best_distance = distance;
                    address = previous_end;
                    found_at_least_one = true;
                }
                previous_end = memslot.getEnd();
            }
            int distance = block_end_address - previous_end;
            if(distance>=p.getMemoryRequirements()&&
                    distance<best_distance){
                fit = true;
                best_distance = distance;
                address = previous_end;
                found_at_least_one = true;
            }
            if(found_at_least_one){
                best_block_start = block_start_address;
                best_block_end = block_end_address;
            }

            block_start_address += this.availableBlockSizes[i];
        }

        if(fit){
            MemorySlot memslot = new MemorySlot(address,
                    address+p.getMemoryRequirements(),
                          best_block_start,
                          best_block_end);
            int left = 0;
            int right = currentlyUsedMemorySlots.size();
            while(left<right){
                int mid = (left+right)/2;
                if(currentlyUsedMemorySlots.get(mid).getStart()<address){
                    right = mid;
                }else{
                    left = mid+1;
                }
            }
            currentlyUsedMemorySlots.add(left,memslot);
            return address;
        }else{
            return -1;
        }
    }

}
