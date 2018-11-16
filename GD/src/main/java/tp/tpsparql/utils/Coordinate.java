package tp.tpsparql.utils;

public class Coordinate<A> {

    private A coordX;
    private A coordY;

    public A getCoordX() {
        return coordX;
    }

    public A getCoordY() {
        return coordY;
    }

    public Coordinate(A coordX, A coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "coordX=" + coordX +
                ", coordY=" + coordY +
                '}';
    }
}
