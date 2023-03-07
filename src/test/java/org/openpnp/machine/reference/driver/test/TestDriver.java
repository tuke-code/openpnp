package org.openpnp.machine.reference.driver.test;

import java.io.IOException;

import javax.swing.Action;
import javax.swing.Icon;

import org.openpnp.gui.support.Wizard;
import org.openpnp.machine.reference.ReferenceMachine;
import org.openpnp.model.AxesLocation;
import org.openpnp.model.Length;
import org.openpnp.model.LengthUnit;
import org.openpnp.model.Motion.MoveToCommand;
import org.openpnp.spi.Actuator;
import org.openpnp.spi.Driver;
import org.openpnp.spi.HeadMountable;
import org.openpnp.spi.Machine;
import org.openpnp.spi.MotionPlanner.CompletionType;
import org.openpnp.spi.PropertySheetHolder;
import org.openpnp.spi.base.AbstractDriver;
import org.simpleframework.xml.Attribute;

public class TestDriver extends AbstractDriver implements Driver {
    @Attribute(required = false)
    private String dummy;

    private Driver delegate = new TestDriverDelegate();

    public void setDelegate(Driver delegate) {
        this.delegate = delegate;
    }

    @Override
    public void home(Machine machine) throws Exception {
        delegate.home(machine);
    }

    @Override
    public void setGlobalOffsets(Machine machine, AxesLocation location)
            throws Exception {
        delegate.setGlobalOffsets(machine, location);
    }

    @Override
    public AxesLocation getReportedLocation(long timeout) throws Exception {
        return delegate.getReportedLocation(-1);
    }

    @Override
    public void moveTo(HeadMountable hm, MoveToCommand move)
            throws Exception {
        
        // Take only this driver's axes.
        AxesLocation newDriverLocation = move.getLocation1();
        // Take the current driver location of the given axes.
        AxesLocation oldDriverLocation = new AxesLocation(newDriverLocation.getAxes(this), 
                (axis) -> (axis.getDriverLengthCoordinate()));
        if (!oldDriverLocation.matches(newDriverLocation)) {
            delegate.moveTo(hm, move);
            // Store to axes
            newDriverLocation.setToDriverCoordinates(this);
        }
    }

    @Override
    public void actuate(Actuator actuator, boolean on) throws Exception {
        delegate.actuate(actuator, on);
    }

    @Override
    public void actuate(Actuator actuator, double value) throws Exception {
        delegate.actuate(actuator, value);
    }

    @Override
    public void setEnabled(boolean enabled) throws Exception {
        delegate.setEnabled(enabled);
    }

    public static class TestDriverDelegate implements Driver {
        @Override
        public Wizard getConfigurationWizard() {
            return null;
        }

        @Override
        public void home(Machine machine) throws Exception {

        }

        @Override
        public void setGlobalOffsets(Machine machine, AxesLocation location)
                throws Exception {
        }

        @Override
        public AxesLocation getReportedLocation(long timeout) throws Exception {
            return null;
        }

        @Override
        public void moveTo(HeadMountable hm, MoveToCommand move)
                throws Exception {

        }

        @Override
        public void actuate(Actuator actuator, boolean on) throws Exception {

        }

        @Override
        public void actuate(Actuator actuator, double value) throws Exception {

        }

        @Override
        public void setEnabled(boolean enabled) throws Exception {

        }

        @Override
        public String getPropertySheetHolderTitle() {
            return null;
        }

        @Override
        public PropertySheetHolder[] getChildPropertySheetHolders() {
            return null;
        }

        @Override
        public PropertySheet[] getPropertySheets() {
            return null;
        }

        @Override
        public Action[] getPropertySheetHolderActions() {
            return null;
        }

        @Override
        public Icon getPropertySheetHolderIcon() {
            return null;
        }

        @Override
        public void close() throws IOException {

        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public void setName(String name) {
        }

        @Override
        public String getId() {
            return null;
        }

        @Override
        public MotionControlType getMotionControlType() {
            return MotionControlType.Full3rdOrderControl;
        }

        @Override
        public LengthUnit getUnits() {
            return LengthUnit.Millimeters;
        }

        @Override
        public boolean isSupportingPreMove() {
            return false;
        }

        @Override
        public void waitForCompletion(HeadMountable hm, CompletionType completionType) throws Exception {
        }

        @Override
        public boolean isUsingLetterVariables() {
            return false;
        }

        @Override
        public Length getFeedRatePerSecond() {
            return null;
        }

        @Override
        public boolean isMotionPending() {
            return false;
        }

        @Override
        public Length getMinimumRate(int order) {
            return new Length(0.1, LengthUnit.Millimeters);
        }

        @Override
        public boolean isSyncInitialLocation() {
            return false;
        }
   }

    @Override
    public MotionControlType getMotionControlType() {
        return MotionControlType.Full3rdOrderControl;
    }

    @Override
    public LengthUnit getUnits() {
        return LengthUnit.Millimeters;
    }


    @Override
    public String getPropertySheetHolderTitle() {
        return null;
    }

    @Override
    public PropertySheetHolder[] getChildPropertySheetHolders() {
        return null;
    }

    @Override
    public PropertySheet[] getPropertySheets() {
        return null;
    }

    @Override
    public Action[] getPropertySheetHolderActions() {
        return null;
    }

    @Override
    public Icon getPropertySheetHolderIcon() {
        return null;
    }

    @Override
    public void close() throws IOException {
    }
    
    @Override
    public Wizard getConfigurationWizard() {
        return null;
    }

    @Override
    public void waitForCompletion(HeadMountable hm, CompletionType completionType) throws Exception {
    }

    @Override
    public boolean isUsingLetterVariables() {
        return false;
    }

    @Deprecated
    @Override
    public void migrateDriver(Machine machine) throws Exception {
        machine.addDriver(this);
        createAxisMappingDefaults((ReferenceMachine) machine);
    }

    @Override
    public boolean isMotionPending() {
        return false;
    }

    @Override
    public boolean isSyncInitialLocation() {
        return false;
    }
}
