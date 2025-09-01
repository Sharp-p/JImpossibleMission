package Model;

import static Model.CodeType.NONE;
import static Model.CodeType.PSW_PIECE;

public class Code {
    /**
     * This variable olds the type of the code. If it is NONE
     * there isn't any code in the furniture piece.
     */
    private CodeType type = NONE;

    public Code() {
        CodeType[] values = CodeType.values();

        // only half of the furniture will have a code
        if (Math.random() > 0.2) {
            do {
                type = values[(int) (Math.random() * (values.length - 1))];
            } while (type == NONE);
        }
    }

    public Code(CodeType type) {
        this.type = type;
    }

    public CodeType getType() { return type; }
}
