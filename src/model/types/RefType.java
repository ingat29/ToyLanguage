package model.types;

import model.values.IValue;
import model.values.RefValue;

public class RefType implements IType{
    private IType inner;

    public RefType(IType inner) {
        this.inner = inner;
    }

    IType getInner() {
        return inner;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof RefType) {
            return ((RefType)other).getInner().equals(this.inner);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Ref("+ inner.toString() +")";
    }

    @Override
    public IValue defaultValue() {
        return new RefValue(0,inner);
    }
}
