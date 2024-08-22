package COLLISION_SYSTEM;
// package BOUNCING_BALLS;

import java.util.PriorityQueue;

import edu.princeton.cs.algs4.StdDraw;

public class CollisionSystem {
    private double clock;
    private PriorityQueue<Event> pq;
    private Particle[] particles;

    public CollisionSystem(Particle[] particles) {
        this.clock = 0.0;
        this.pq = new PriorityQueue<>();
        deepCopy(particles);

    }

    private void deepCopy(Particle[] particles) {
        if (particles == null)
            throw new IllegalArgumentException("NULL ARGUMENT");
        this.particles = new Particle[particles.length];
        for (int i = 0; i < particles.length; i++) {
            this.particles[i] = new Particle(particles[i]);
        }
    }

    public void predict(Particle a) {
        if (a == null)
            throw new IllegalArgumentException("NULL ARGUMENT");
        for (int i = 0; i < this.particles.length; i++) {
            pq.add(new Event(a, particles[i], this.clock + a.timeToHit(particles[i])));
        }
        pq.add(new Event(a, null, this.clock + a.timeToHitWithHorizontalWall()));
        pq.add(new Event(null, a, this.clock + a.timeToHitWithVerticalWall()));
        return;
    }

    public void redraw() {
        StdDraw.clear();
        if (this.particles == null)
            throw new IllegalArgumentException("PARTICLES ARRAY NULL");
        for (int i = 0; i < this.particles.length; i++) {
            particles[i].draw();
        }
        // StdDraw.show();
        StdDraw.pause(1);
        pq.add(new Event(null, null, this.clock + 0.1));
        return;
    }

    public void simulation() {
        for (int i = 0; i < this.particles.length; i++) {
            predict(this.particles[i]);
        }
        pq.add(new Event(null, null, 0));
        while (!this.pq.isEmpty()) {
            // this.redraw();
            Event toOccur = this.pq.remove();
            if (!toOccur.isValid())
                continue;
            Particle a = toOccur.a();
            Particle b = toOccur.b();

            // System.out.println("time" + (toOccur.time()));
            // System.out.println("P" + a + " " + b);

            // System.out.println("dt" + (toOccur.time() - this.clock));

            // if (toOccur.time() != Double.POSITIVE_INFINITY && toOccur.time() !=
            // Double.NEGATIVE_INFINITY) {
            for (int i = 0; i < this.particles.length; i++) {
                particles[i].move(toOccur.time() - this.clock);
            }
            this.clock = toOccur.time();
            // }

            if (a != null && b != null) {
                // System.out.println("BOUNCED OFF P");
                a.bounceOff(b);
                // b.bounceOff(a);
            } else if (a == null && b != null) {
                // System.out.println("BOUNCED OFF VWALL");

                b.bounceOffVWall();
            } else if (a != null && b == null) {
                // System.out.println("BOUNCED OFF HWALL");

                a.bounceOffHWall();
            } else
                redraw();
            if (a != null)
                predict(a);
            if (b != null)
                predict(b);

        }
        return;
    }

    public static void main(String[] args) {
        int N = 5;
        Particle[] particles = new Particle[N];
        // particles[0] = new Particle(.3, .5, 0.002, .005, .08, 6, StdDraw.RED);
        // particles[1] = new Particle(.1, .9, -0.004, .001, .04, 2, StdDraw.GREEN);

        for (int i = 0; i < N; i++) {
            particles[i] = new Particle();
        }
        CollisionSystem cs = new CollisionSystem(particles);
        cs.simulation();
    }
}
