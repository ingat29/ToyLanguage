package model.values;

import model.types.IType;
import model.types.RefType;

public class RefValue implements IValue{
    int heapAddress;
    IType locationType;

    public RefValue(int heapAddress, IType locationType) {
        this.heapAddress = heapAddress;
        this.locationType = locationType;
    }

    public int getAddress() {
        return heapAddress;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    @Override
    public String toString() {
        return "RefValue{" + "heapAddress=" + heapAddress + ", locationType=" + locationType.toString() + '}';
    }
}
