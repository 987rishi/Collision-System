package COLLISION_SYSTEM;

import edu.princeton.cs.algs4.StdDraw;
import java.awt.Color;
import java.util.Random;

import javax.print.event.PrintJobAttributeEvent;

/**
 * Balls
 */
public class Particle {
    private double x;
    private double y;
    private double vx;
    private double vy;
    private final double radius;
    private final double mass;
    private Color color;
    private int count;

    public Particle() {
        double radius = 0.009;
        double vx = 0.09;
        double vy = 0.07;
        double mass = 1;
        double maxX = 1 - radius;
        double maxY = 1 - radius;
        double minX = radius;
        double minY = radius;
        Random rand = new Random();
        this.x = minX + rand.nextDouble() * (maxX - minX);
        this.y = minY + rand.nextDouble() * (maxY - minY);
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
        this.mass = mass;
        this.color = StdDraw.BLACK;
        this.count = 0;
        // System.out.println(this);
        assert (this.x >= this.radius && this.x <= (1 - this.radius));
        assert (this.y >= this.radius && this.y <= (1 - this.radius));

    }

    public int count() {
        return this.count;
    }

    // COPY CONSTRUCTOR
    public Particle(Particle that) {
        if (that == null)
            throw new IllegalArgumentException("NULL ARGUMENT");
        this.x = that.x;
        this.y = that.y;
        this.vx = that.vx;
        this.vy = that.vy;
        this.radius = that.radius;
        this.color = that.color;
        this.mass = that.mass;
        this.count = that.count;
    }

    public Particle(double x, double y, double vx, double vy, double radius, double mass, Color color) {
        if (x - radius < 0 || x + radius > 1 || y - radius < 0 || y + radius > 1 || radius < 0 || mass <= 0)
            throw new IllegalArgumentException("ARGUMENTS VALUE OUT OF RANGE");
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
        this.mass = mass;
        this.color = color;
        this.count = 0;
    }

    public void move(double dt) {
        // if ((x + vx * dt - this.radius < 0) || (x + vx * dt + this.radius > 1.0)) {
        // vx = -vx;
        // }
        // if ((y + vy * dt - this.radius < 0) || (y + vy * dt + this.radius > 1.0)) {
        // vy = -vy;
        // }
        if (dt != Double.POSITIVE_INFINITY && dt != Double.NEGATIVE_INFINITY && dt >= 0) {
            // System
            this.x = this.x + this.vx * dt;
            this.y = this.y + this.vy * dt;

            if (this.x < 0 || this.y < 0) {
                // System.out.println("particle " + this);
                throw new IllegalStateException();
            }

        }

    }

    public double timeToHit(Particle that) {
        if (that == null)
            throw new IllegalArgumentException("NULL ARGUMENT");
        if (this == that)
            return Double.POSITIVE_INFINITY;
        double dx = that.x - this.x, dy = that.y - this.y;
        double dvx = that.vx - this.vx;
        double dvy = that.vy - this.vy;
        double dvdr = dx * dvx + dy * dvy;
        if (dvdr > 0)
            return Double.POSITIVE_INFINITY;
        if (dvdr == 0)
            return Double.POSITIVE_INFINITY;
        double dvdv = dvx * dvx + dvy * dvy;
        double drdr = dx * dx + dy * dy;
        double sigma = this.radius + that.radius;
        double d = (dvdr * dvdr) - dvdv * (drdr - sigma * sigma);
        if (d < 0)
            return Double.POSITIVE_INFINITY;
        double t = -(dvdr + Math.sqrt(d)) / dvdv;
        if (t <= 0) {
            return Double.POSITIVE_INFINITY;
        }
        return t;

    }

    public double timeToHitWithVerticalWall() {
        if (this.vx > 0) {
            double timeToHit = (1 - this.radius - this.x) / this.vx;
            return timeToHit;

        } else if (this.vx < 0) {
            return (double) (this.radius - this.x) / this.vx;
        } else
            return Double.POSITIVE_INFINITY;
    }

    public double timeToHitWithHorizontalWall() {
        if (this.vy > 0) {
            double timeToHit = (1 - this.radius - this.y) / this.vy;
            return timeToHit;

        } else if (this.vy < 0) {
            return (double) ((this.radius - this.y)) / this.vy;

        } else
            return Double.POSITIVE_INFINITY;

    }

    public void bounceOffVWall() {
        this.vx = -this.vx;
        this.count++;
    }

    public void bounceOffHWall() {
        this.vy = -this.vy;
        this.count++;
    }

    public void bounceOff(Particle that) {
        double dx = (that.x - this.x);
        double dy = (that.y - this.y);

        double dist = this.radius + that.radius;

        double dvx = (that.vx - this.vx), dvy = (that.vy - this.vy);
        double dvdr = dx * dvx + dy * dvy;
        double J = 2 * this.mass * that.mass * dvdr / ((this.mass + that.mass) * dist);
        double Jx = J * dx / dist;
        double Jy = J * dy / dist;

        this.vx = this.vx + Jx / this.mass;
        this.vy = this.vy + Jy / this.mass;
        that.vx = that.vx - Jx / that.mass;
        that.vy = that.vy - Jy / that.mass;
        this.count++;
        that.count++;

    }

    public void draw() {
        StdDraw.setPenColor(this.color);
        // System.out.println(this.x + " " + this.y);
        StdDraw.filledCircle(this.x, this.y, this.radius);
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(String.format("X:%.3f\t\t", this.x));
        output.append(String.format("Y:%.3f\t", this.y));
        output.append(String.format("VX:%.3f\t", this.vx));
        output.append(String.format("VY:%.3f", this.vy));

        return output.toString();

    }

    public static void main(String[] args) {
        Particle a = new Particle();
        System.out.println(a);
    }

}