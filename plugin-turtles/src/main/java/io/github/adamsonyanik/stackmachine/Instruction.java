package io.github.adamsonyanik.stackmachine;

public record Instruction(OP op, Integer arg) {
    public Instruction(OP op) {
        this(op, null);
    }
}
