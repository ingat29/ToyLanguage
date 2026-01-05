package gui;

public class SymTblEntry {
    private final String variableName;
    private final String value;

    public SymTblEntry(String variableName, String value) {
        this.variableName = variableName;
        this.value = value;
    }

    public String getVariableName() { return variableName; }
    public String getValue() { return value; }
}
