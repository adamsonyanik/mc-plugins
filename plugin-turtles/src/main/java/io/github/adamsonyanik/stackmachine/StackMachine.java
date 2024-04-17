package io.github.adamsonyanik.stackmachine;

import java.util.ArrayList;
import java.util.List;

public class StackMachine {

    private Instruction[] program;
    private List<Integer> stack;

    private int pc;
    private int fp;
    private int sp;

    public StackMachine(Instruction[] program, int[] stack, int pc, int fp, int sp) {
        this.program = program;
        this.stack = new ArrayList<>();
        for (int i : stack)
            this.stack.add(i);

        this.pc = pc;
        this.fp = fp;
        this.sp = sp;
    }

    public boolean exec() {
        if (pc >= this.program.length) {
            System.out.println("halt");
            return false;
        }

        Instruction instruction = this.program[pc];
        OP op = instruction.op();
        Integer arg = instruction.arg();
        this.pc++;

        switch (op) {
            case push:
                this.stack.add(arg);
                this.sp++;
                break;
            case pop:
                this.stack.removeLast();
                this.sp--;
                break;
            case load:
                this.stack.set(this.stack.size() - 1, this.stack.get(this.stack.get(this.stack.size() - 1)));
                break;
            case loadfp:
                this.stack.set(this.stack.size() - 1, this.stack.get(this.stack.get(this.stack.size() - 1) + this.fp));
                break;
            case loadsp:
                this.stack.set(this.stack.size() - 1, this.stack.get(this.stack.get(this.stack.size() - 1) + this.sp));
                break;
            case loada:
                this.stack.set(this.stack.size() - 2, this.stack.get(this.stack.get(this.stack.size() - 1) + this.stack.get(this.stack.size() - 2)));
                this.stack.removeLast();
                this.sp--;
                break;
            case store:
                this.stack.set(this.stack.get(this.stack.size() - 2), this.stack.get(this.stack.size() - 1));
                this.stack.removeLast();
                this.stack.removeLast();
                this.sp -= 2;
                break;
            case storefp:
                this.stack.set(this.stack.get(this.stack.size() - 2) + this.fp, this.stack.get(this.stack.size() - 1));
                this.stack.removeLast();
                this.stack.removeLast();
                this.sp -= 2;
                break;
            case storesp:
                this.stack.set(this.stack.get(this.stack.size() - 2) + this.sp, this.stack.get(this.stack.size() - 1));
                this.stack.removeLast();
                this.stack.removeLast();
                this.sp -= 2;
                break;
            case storea:
                this.stack.set(this.stack.get(this.stack.size() - 2) + this.stack.get(this.stack.size() - 3), this.stack.get(this.stack.size() - 1));
                this.stack.removeLast();
                this.stack.removeLast();
                this.stack.removeLast();
                this.sp -= 3;
                break;
            case iadd:
                this.stack.set(this.stack.size() - 2, this.stack.get(this.stack.size() - 1) + this.stack.get(this.stack.size() - 2));
                this.stack.removeLast();
                this.sp--;
                break;


            case iprint:
                System.out.println(this.stack.getLast());
                this.stack.removeLast();
                this.sp--;
                break;

            default:
                throw new RuntimeException(op + " op code is not supported");
        }

        return true;
    }
}
