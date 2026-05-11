package at.jku.swtesting;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;

public class RingBufferModel implements FsmModel {

    protected int capacity, size;

    public RingBufferModel (int capacity) {
        this.capacity = capacity;
    }

    @Override
    public Object getState() {
        if (size == 0) {
            return "EMPTY";
        } else if (size == capacity) {
            return "FULL";
        } else if ((size > 0) && (size < capacity)) {
            return "FILLED";
        } else return "ERROR_UNEXPECTED_MODEL_STATE";
    }

    @Override
    public void reset(boolean testing) {
        size = 0;
    }

    @Action
    public void enqueue() {
        if (size < capacity) {
            size++;
        }
    }

    @Action
    public void dequeue() {
        size--;
    }

    public boolean dequeueGuard() {
        return size > 0;
    }

    @Action
    public void dequeueFromEmptyBuffer() {
    }

    public boolean dequeueFromEmptyBufferGuard() {
        return size == 0;
    }

    @Action
    public void peek() {
    }

    public boolean peekGuard() {
        return size > 0;
    }

    @Action
    public void peekFromEmptyBuffer() {
    }

    public boolean peekFromEmptyBufferGuard() {
        return size == 0;
    }
}
