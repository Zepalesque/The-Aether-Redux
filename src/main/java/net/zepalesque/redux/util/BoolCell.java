package net.zepalesque.redux.util;

import java.util.Objects;

// Think of this as a Rc<Cell<bool>> in rust
// More accurately some kind of Gc<Cell<bool>>
public final class BoolCell {
	public boolean deref;
	
	public BoolCell(boolean value) {
		this.deref = value;
	}
	
	@Override
	public String toString() {
		return "BoolCell[" +
			"deref=" + this.deref +
			']';
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || this.getClass() != o.getClass()) return false;
		var other = (BoolCell) o;
		return this.deref == other.deref;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.deref);
	}
}
