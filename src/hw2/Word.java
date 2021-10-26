package hw2;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.logging.Level;

public class Word {
    private final int id;
    private final Line2D line;
    public final int length;
    public final Direction direction;

    private String[] domain;
    public final Constraints constraints = new Constraints();

    public Word(int wordNumber, Line2D line) {
        this.id = wordNumber;
        this.line = line;
        this.direction = calcDirection();
        this.length = calcLength();
    }

    private Direction calcDirection() {
        return line.getX1() == line.getX2() ? Direction.DOWN : Direction.ACROSS;
    }

    private int calcLength() {
        int length;

        if (Direction.ACROSS == direction) {
            length = (int) (line.getX2() - line.getX1());
        } else {
            length = (int) (line.getY2() - line.getY1());
        }

        return length;
    }

    public Point2D getStartingPoint() {
        return line.getP1();
    }

    public void setDomain(String[] domain) {
        if (this.domain != null) {
            Logger.log(Level.WARNING, "Domain was overwritten!");
        }

        this.domain = domain;
    }

    public void addConstraint(Constraint constraint) {
        constraints.add(constraint);
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

    // This probably should be a word problem....
    public boolean isConsistent(Assignment assignment) {
        for (Constraint constraint : constraints) {
            if (!constraint.constraintSatisfied(assignment)) {
                return false;
            }
        }

        return true;
    }

    public String[] getDomain() {
        return domain;
    }

    @Override
    public String toString() {
        return "X" + id + (direction == Direction.ACROSS ? "a" : "d");
    }
}
