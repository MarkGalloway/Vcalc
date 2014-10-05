package node.vcalc;

import errors.vcalc.InvalidAssignmentException;
import symbol.vcalc.VcalcValue;

public interface VcalcNode {
	VcalcValue<?> evaluate() throws InvalidAssignmentException;
}
