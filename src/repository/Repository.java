package repository;

import model.PrgState;

import java.util.List;

public class Repository implements MyIRepository {
    List<PrgState> prgList;

    public Repository(List<PrgState> prgList) {
        this.prgList = prgList;
    }

    public void addPrg(PrgState prgState) {
        prgList.add(prgState);
    }

    @Override
    public PrgState getCrtPrg() {
        return prgList.get(0);
    }
}
