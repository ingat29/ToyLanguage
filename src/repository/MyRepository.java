package repository;

import exception.MyException;
import model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class MyRepository implements MyIRepository {
    List<PrgState> prgList;
    String logFilePath;

    public MyRepository(List<PrgState> prgList , String logFilePath) {
        this.prgList = prgList;
        this.logFilePath = logFilePath;
    }

    public void addPrg(PrgState prgState) {
        prgList.add(prgState);
    }

    @Override
    public PrgState getCrtPrg() {
        return prgList.get(0);
    }

    @Override
    public void logPrgStateExecution() throws MyException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
            logFile.println("ExeStack:\n");
            logFile.println(getCrtPrg().getExeStack().toStringFormatted());

            logFile.println("SymTable:\n");
            logFile.println(getCrtPrg().getSymTable().toStringFormatted());

            logFile.println("Out:\n");
            logFile.println(getCrtPrg().getOut().toStringFormatted());

            logFile.println("FileTable:\n");
            logFile.println(getCrtPrg().getFileTable().toStringFormatted());

        } catch (IOException e) {
            throw new MyException("Error writing to log file: " + e.getMessage());
        }
    }
}
