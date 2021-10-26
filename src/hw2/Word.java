package hw2;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

public class Word {
    private final int length;
    private final Line2D line;
    private final Direction direction;
    private final int wordNumber;

    private String[] domain;
    private final Constraints constraints = new Constraints();

    public Word(int length, Line2D line, Direction direction, int wordNumber) {
        this.length = length;
        this.line = line;
        this.direction = direction;
        this.wordNumber = wordNumber;
    }

    public Direction getDirection() {
        return direction;
    }

    public Point2D getStartingPoint() {
        return line.getP1();
    }

    public void print() {
        System.out.printf("(%.0f,%.0f)->(%.0f,%.0f) %s Len-%d domain %s\n",
                line.getX1(), line.getY1(), line.getX2(), line.getY2(),
                (direction == Direction.ACROSS ? "Across" : "Down"),
                length, domain == null ? "null" : "set!");
    }

    public int getLength() {
        return length;
    }

    public void setDomain(String[] domain) {
        // Domain is effectively final
        if (this.domain == null) {
            this.domain = domain;
        }
    }

    public void addConstraint(Constraint constraint) {
        constraints.add(constraint);
    }

    public Constraints getConstraints() {
        return constraints;
    }

    public int getIndexAt(Point2D coordinates) {
        if (!containsPoint(coordinates)) return -1;

        int xDiff = (int) (coordinates.getX() - line.getX1()),
                yDiff = (int) (coordinates.getY() - line.getY1());

        return xDiff + yDiff;
    }

    private boolean containsPoint(Point2D coordinate) {
        Set<Point2D> allPoints = new HashSet<>();
        int startX = (int) line.getX1(), startY = (int) line.getY1();

        if (direction == Direction.ACROSS) {
            for (int i = 0; i < length; i++) allPoints.add(new Point2D.Float(startX + i, startY));
        } else {
            for (int i = 0; i < length; i++) allPoints.add(new Point2D.Float(startX, startY + i));
        }

        return allPoints.contains(coordinate);
    }

    public String[] getDomain() {
        return domain;
    }

    public boolean isConsistent(Assignment assignment) {
        for (Constraint constraint : getConstraints()) {
            if (!constraint.constraintSatisfied(assignment)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return "X" + wordNumber + (direction == Direction.ACROSS ? "a" : "d");
    }
}
