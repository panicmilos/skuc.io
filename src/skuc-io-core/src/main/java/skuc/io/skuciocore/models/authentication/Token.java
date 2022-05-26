package skuc.io.skuciocore.models.authentication;

public class Token {
    private String value;

    public Token() {}

    public Token(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
}
