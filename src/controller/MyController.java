package controller;

import exception.MyException;
import model.PrgState;
import model.values.IValue;
import model.values.RefValue;
import repository.MyIRepository;
import model.adt.MyIStack;
import model.statements.IStmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class MyController implements MyIController{
    MyIRepository repo;
    boolean displayFlag;

    public MyController(MyIRepository repo, boolean displayFlag){
        this.repo = repo;
        this.displayFlag = displayFlag;
    }

    @Override
    public PrgState oneStep(PrgState state) throws MyException {
        MyIStack<IStmt> stk=state.getExeStack();
        if(stk.isEmpty()) {
            throw new MyException("PrgState stack is empty");
        }
        IStmt crtStmt = stk.pop();
        return crtStmt.execute(state);
    }

    @Override
    public void allStep() throws MyException {
        PrgState prg = repo.getCrtPrg();
        if(displayFlag){
            repo.logPrgStateExecution();
        }
        while (!prg.getExeStack().isEmpty()){
            try {
                prg = oneStep(prg);

                prg.getHeap().SetContent(
                        safeGarbageCollector(getAddrFromSymTable(prg.getSymTable().getContent()), prg.getHeap().getContent()));

                if(displayFlag){
                    repo.logPrgStateExecution();
                }
            }
            catch (MyException e){
                throw e;
            }
        }
    }

    @Override
    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }

    @Override
    public void displayPrgState(PrgState state) {
        if(displayFlag){
            System.out.println(state.toString() + "\n");
        }
    }

    private List<Integer> getAddrFromSymTable(Map<String, IValue> symTable) {
        return symTable.values().stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    private List<Integer> getAddrFromHeap(List<Integer> symTableAddr, Map<Integer, IValue> heap) {//i need some explanations here
        boolean changed = true;
        List<Integer> newAddr = new ArrayList<>(symTableAddr);

        while (changed) {
            changed = false;
            List<Integer> currentHeapAddrs = heap.entrySet().stream()
                    .filter(e -> newAddr.contains(e.getKey())) // only look at currently reachable nodes
                    .filter(e -> e.getValue() instanceof RefValue) // check if the value in heap is a Ref
                    .map(e -> ((RefValue) e.getValue()).getAddress()) // get the address it points to
                    .filter(addr -> !newAddr.contains(addr)) // only keep new ones we haven't seen
                    .collect(Collectors.toList());

            if (!currentHeapAddrs.isEmpty()) {
                newAddr.addAll(currentHeapAddrs);
                changed = true;
            }
        }
        return newAddr;
    }

    // 3. The Main Garbage Collector Method
    private Map<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, IValue> heap) {
        List<Integer> reachableAddr = getAddrFromHeap(symTableAddr, heap);

        return heap.entrySet().stream()
                .filter(e -> reachableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}