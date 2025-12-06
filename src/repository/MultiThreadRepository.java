package repository;

import exception.MyException;
import model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MultiThreadRepository implements MultiThreadRepositoryInterface {
    List<PrgState> prgList;
    String logFilePath;

    public MultiThreadRepository(PrgState initialState , String logFilePath) {
        this.prgList = new ArrayList<PrgState>();
        this.prgList.add(initialState);
        this.logFilePath = logFilePath;
    }

    public void addPrg(PrgState prgState) {
        prgList.add(prgState);
    }

    @Override
    public void logPrgStateExecution(PrgState state) throws MyException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
            logFile.print("ExeStack:\n");
            logFile.print(state.getExeStack().toStringFormatted());

            logFile.print("SymTable:\n");
            logFile.print(state.getSymTable().toStringFormatted());

            logFile.print("Out:\n");
            logFile.print(state.getOut().toStringFormatted());

            logFile.print("FileTable:\n");
            logFile.print(state.getFileTable().toStringFormatted());

            logFile.print("Heap:\n");
            logFile.print(state.getHeap().toStringFormatted());

        } catch (IOException e) {
            throw new MyException("Error writing to log file: " + e.getMessage());
        }
    }


    @Override
    public List<PrgState> getPrgList() throws MyException {
        return prgList;
    }

    @Override
    public void setPrgList(List<PrgState> prgList) throws MyException {
        this.prgList.clear();
        this.prgList.addAll(prgList);
    }
}
