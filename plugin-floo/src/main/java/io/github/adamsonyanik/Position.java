package io.github.adamsonyanik;

public record Position(int x, int y, int z) {

    @Override
    public String toString() {
        return x + ";" + y + ";" + z;
    }
}
