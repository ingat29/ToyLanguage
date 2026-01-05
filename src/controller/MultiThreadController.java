package controller;

import exception.MyException;
import model.PrgState;
import model.adt.MyIStack;
import model.statements.IStmt;
import model.values.IValue;
import model.values.RefValue;
import repository.MultiThreadRepository;
import repository.MultiThreadRepositoryInterface;
import repository.MyIRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class MultiThreadController implements MultiThreadControllerInterface{
    MultiThreadRepositoryInterface repo;
    boolean displayFlag;
    ExecutorService executor;

    public MultiThreadController(MultiThreadRepositoryInterface repo, boolean displayFlag){
        this.repo = repo;
        this.displayFlag = displayFlag;
    }

    // - Step 14: Define oneStepForAllPrg
    public void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException {
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExecution(prg);
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        });

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.oneStep();}))//HUHHHHH???
                .collect(Collectors.toList());

        List<PrgState> newPrgList = executor.invokeAll(callList).stream()//invokeAll returns a list of Futures of PrgStates
                .map(future -> {
                    try {
                        return future.get();//wait for the task to finish , and map the future to the result(null or another prgstate)
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(p -> p != null) //remove nulls (threads that didn't create a new thread)
                .collect(Collectors.toList());

        prgList.addAll(newPrgList);

        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExecution(prg);
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        });

        try {//save the programs in the repo
            repo.setPrgList(prgList);
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void allStep() throws MyException {
        executor = Executors.newFixedThreadPool(2);

        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());

        while (prgList.size() > 0) {

            prgList.get(0).getHeap().SetContent(safeGarbageCollector(getAddrFromAllSymTables(prgList), //call for garbage collecter to clean up
                                                                    prgList.get(0).getHeap().getContent()));

            try {
                oneStepForAllPrg(prgList);
            } catch (InterruptedException e) {
                throw new MyException(e.getMessage());
            }

            prgList = removeCompletedPrg(repo.getPrgList());
        }

        executor.shutdownNow();

        repo.setPrgList(prgList);
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

    @Override
    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    @Override
    public MultiThreadRepositoryInterface getRepo() {
        return repo;
    }

    private List<Integer> getAddrFromSymTable(Map<String, IValue> symTable) {
        return symTable.values().stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }


    // Helper to get addresses from ALL threads at once
    private List<Integer> getAddrFromAllSymTables(List<PrgState> prgList) {
        return prgList.stream()
                .map(p -> p.getSymTable().getContent().values())//here we have a list of lists
                .flatMap(Collection::stream)//with flat map we turn it into a single list
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue)v).getAddress())
                .collect(Collectors.toList());
    }

    private List<Integer> getAddrFromHeap(List<Integer> symTableAddr, Map<Integer, IValue> heap) {
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
