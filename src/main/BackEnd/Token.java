
public class Token {
    String tokenType;   //  keywords Num Operators delimiters Identifier comments
    String attributeValue;
    int linePosition;
    int lineNumber;
    int id;  //id是每一个终结符都会有独一无二的id，按照LL的那个表来，没有的自己定义

    public Token(String tokenType, String attributeValue, int linePosition, int lineNumber, int id) {
        this.tokenType = tokenType;
        this.attributeValue = attributeValue;
        this.linePosition = linePosition;
        this.lineNumber = lineNumber;
        this.id = id;
    }

    public Token() {

    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }


    public int getLinePosition() {
        return linePosition;
    }

    public void setLinePosition(int linePosition) {
        this.linePosition = linePosition;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MyToken{" +
                "tokenType='" + tokenType + '\'' +
                ", attributeValue='" + attributeValue + '\'' +
                ", linePosition=" + linePosition +
                ", lineNumber=" + lineNumber +
                ", id=" + id +
                '}';
    }
}
