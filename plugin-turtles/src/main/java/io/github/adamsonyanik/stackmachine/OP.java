package io.github.adamsonyanik.stackmachine;

public enum OP {
    push,
    pop,

    load,
    loadfp,
    loadsp,
    loada,
    store,
    storefp,
    storesp,
    storea,

    iadd,
    isub,
    imul,
    idiv,

    fadd,
    fsub,
    fmul,
    fdiv,

    i2f,
    f2i,

    iprint,
    fprint,
    cprint
}
