package com.stevekung.stevekungslib.utils.enums;

public enum EntityTrackerType
{
    NORMAL(80, 3, true),
    THROWABLE_SMALL(64, 10, false),
    THROWABLE_LARGE(64, 10, true),
    ARROW(64, 20, true),
    FISHING_HOOK(64, 5, true);

    private final int trackingRange;
    private final int updateFrequency;
    private final boolean sendsVelocityUpdates;

    private EntityTrackerType(int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
    {
        this.trackingRange = trackingRange;
        this.updateFrequency = updateFrequency;
        this.sendsVelocityUpdates = sendsVelocityUpdates;
    }

    public int getTrackingRange()
    {
        return this.trackingRange;
    }

    public int getUpdateFrequency()
    {
        return this.updateFrequency;
    }

    public boolean sendsVelocityUpdates()
    {
        return this.sendsVelocityUpdates;
    }
}