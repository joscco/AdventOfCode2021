package main.java.Day22;

public record Cuboid(int xMin, int xMax, int yMin, int yMax, int zMin, int zMax, boolean on) {

    public Cuboid buildIntersectionWith(Cuboid cuboid) {
        if (!this.intersectsWith(cuboid)) {
            return null;
        } else {
            // Just take the real intersection here (so don't slice everything up, that got too complicated)
            // and add it as a new cuboid with positive or negative weight.
            return new Cuboid(
                    Math.max(xMin, cuboid.xMin), Math.min(xMax, cuboid.xMax),
                    Math.max(yMin, cuboid.yMin), Math.min(yMax, cuboid.yMax),
                    Math.max(zMin, cuboid.zMin), Math.min(zMax, cuboid.zMax),
                    !on);
        }
    }

    private boolean intersectsWith(Cuboid cuboid) {
        return xMin <= cuboid.xMax && xMax >= cuboid.xMin
                && yMin <= cuboid.yMax && yMax >= cuboid.yMin
                && zMin <= cuboid.zMax && zMax >= cuboid.zMin;
    }

    public long volume() {
        // This is the real "trick" here. We use negative volumes to take all calculated intersections into account
        return (xMax - xMin + 1L) * (yMax - yMin + 1L) * (zMax - zMin + 1L) * (on ? 1 : -1);
    }

    public long limitedAxisVolume(int min, int max) {
        Cuboid limitedCuboid = new Cuboid(min, max, min, max, min, max, true);
        if (intersectsWith(limitedCuboid)) {
            return (xMax - xMin + 1L) * (yMax - yMin + 1L) * (zMax - zMin + 1L) * (on ? 1 : -1);
        }
        return 0;
    }
}
