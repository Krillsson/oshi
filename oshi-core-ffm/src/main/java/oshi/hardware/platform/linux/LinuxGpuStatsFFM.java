/*
 * Copyright 2026 The OSHI Project Contributors
 * SPDX-License-Identifier: MIT
 */
package oshi.hardware.platform.linux;

import oshi.annotation.concurrent.ThreadSafe;
import oshi.ffm.util.gpu.NvmlUtilFFM;
import oshi.hardware.common.platform.linux.LinuxGpuStats;

/**
 * FFM-based Linux {@link LinuxGpuStats} subclass providing NVML integration via FFM.
 */
@ThreadSafe
final class LinuxGpuStatsFFM extends LinuxGpuStats {

    LinuxGpuStatsFFM(String drmDevicePath, String driverName, String pciBusId, String cardName) {
        super(drmDevicePath, driverName, pciBusId, cardName);
    }

    /**
     * TEMPORARY (Krillsson/monitee-agent#341): tags every reading taken through the FFM backend with a fractional
     * .33, so a verification build can tell at a glance whether the FFM or the JNA provider served the value. Must be
     * removed before this branch is used for anything but that verification.
     */
    @Override
    public double getGpuUtilization() {
        double utilization = super.getGpuUtilization();
        return utilization < 0 ? utilization : Math.floor(utilization) + 0.33;
    }

    @Override
    protected boolean nvmlIsAvailable() {
        return NvmlUtilFFM.isAvailable();
    }

    @Override
    protected String nvmlFindDevice(String busId) {
        return NvmlUtilFFM.findDevice(busId);
    }

    @Override
    protected String nvmlFindDeviceByName(String name) {
        return NvmlUtilFFM.findDeviceByName(name);
    }

    @Override
    protected double nvmlGetUtilization(String deviceId) {
        return NvmlUtilFFM.getGpuUtilization(deviceId);
    }

    @Override
    protected long nvmlGetVramUsed(String deviceId) {
        return NvmlUtilFFM.getVramUsed(deviceId);
    }

    @Override
    protected double nvmlGetTemperature(String deviceId) {
        return NvmlUtilFFM.getTemperature(deviceId);
    }

    @Override
    protected double nvmlGetPowerDraw(String deviceId) {
        return NvmlUtilFFM.getPowerDraw(deviceId);
    }

    @Override
    protected long nvmlGetCoreClockMhz(String deviceId) {
        return NvmlUtilFFM.getCoreClockMhz(deviceId);
    }

    @Override
    protected long nvmlGetMemoryClockMhz(String deviceId) {
        return NvmlUtilFFM.getMemoryClockMhz(deviceId);
    }

    @Override
    protected double nvmlGetFanSpeedPercent(String deviceId) {
        return NvmlUtilFFM.getFanSpeedPercent(deviceId);
    }
}
