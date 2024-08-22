package COLLISION_SYSTEM;
// package BOUNCING_BALLS;

/*
 * PARTICLE a==NULL&&b!=NULL-->COLLISION WITH V WALL
 * PARTICLE a!=NULL&&b==NULL-->COLLISION WITH H WALL
 * PARTICLE a==NULL&&b==NULL-->REDRAW EVENT
 * 
 * 
 */

public class Event implements Comparable<Event> {
    private double t = 0.0;// TIME OF THE EVENT
    private Particle a, b;// COLLIDING PARTICLES
    private int count;// NUMBER OF COLLISIONS
    // private PriorityQueue<Event> pq;

    public Event(Particle a, Particle b, double t) {
        this.a = a;
        this.b = b;
        this.t = t;
        if (a != null && b != null)
            this.count = a.count() + b.count();
        else if (a != null && b == null)
            this.count = a.count();
        else if (a == null && b != null)
            this.count = b.count();
        else
            this.count = 0;

    }

    public double time() {
        return this.t;
    }

    public Particle a() {
        return this.a;
    }

    public Particle b() {
        return this.b;
    }

    @Override
    public int compareTo(Event that) {
        return Double.compare(this.t, that.t);
    }

    public boolean isValid() {
        if (a == null && b != null) {
            assert !(b.count() < this.count)
                    : "COLLISION COUNT OF PARTICLES IS LESS THAN EVENT COLLISION COUNT";
            if (b.count() > this.count)
                return false;
            return true;

        } else if (a != null && b == null) {
            assert !(a.count() < this.count)
                    : "COLLISION COUNT OF PARTICLES IS LESS THAN EVENT COLLISION COUNT";
            if (a.count() > this.count)
                return false;
            return true;

        } else if (a != null && b != null) {
            assert !(a.count() + b.count() < this.count)
                    : "COLLISION COUNT OF PARTICLES IS LESS THAN EVENT COLLISION COUNT";
            if (a.count() + b.count() > this.count)// a or b has been invalidated (prev collided and changed trajectory)
            {
                return false;
            }
            return true;
        } else if (a == null && b == null)
            return true;

        else
            return false;
    }

}
