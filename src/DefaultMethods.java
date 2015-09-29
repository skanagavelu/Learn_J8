import java.util.Comparator;
import java.util.List;


/*
 * Interface evolution problem :: Interfaces has become complex, many are implemented it.
 * 
 * How can published interfaces be evolved without breaking existing implementations.
 * 
 * Adding method implementation (non abstract method) to an interface.
 * 
 * @see Iterable
 * https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html
 * 
 * The default method on java interface has an implementation provided in the interface 
 * and is inherited by classes that implement the interface.
 */

public class DefaultMethods {
	public static void main(String[] args) {
		Vehicle vehicleImpl =  new FordFigo();
		vehicleImpl.printVehicleTypeName();
		/*
		 * Important points about interface static methods:
		 * 	Interface static methods are part of interface, we can’t use it for implementation class objects.
		 * 	Interface static methods are good for providing utility methods, for example null check, collection sorting etc.
		 *	Interface static method helps us in providing security by not allowing implementation classes to override them.
		 * 	We can’t define static methods for Object class methods, we will get compiler error as “This static method cannot hide the instance method from Object”. This is because it’s not allowed in java, since Object is the base class for all the classes and we can’t have one class level static method and another instance method with same signature.
		 * 	We can use static interface methods to remove utility classes such as Collections and move all of it’s static methods to the corresponding interface, that would be easy to find and use.
		 */
//		vehicleImpl.whomIBelongsTo();
		
	}
}


interface Vehicle {
	//Non abstract method
	public default void printVehicleTypeName() { //default keyword can be used only in interface.
		System.out.println("Vehicle");
		System.out.println("Hurrah!! I am having access to this: " + this);
	}
	
//	public default void printVehicleTypeName(String input) { //YES, you can overload default methods.
//		System.out.println("I AM OVERLODDED");
//	}
	
	/*
	 * @See Collections#unmodifiableList(List) Better usage is List#unmodifiableList(List)
	 * No need of separate Utility class for that.
	 */
	public static void whomIBelongsTo() {
		System.out.println("Vehicle");
	}
}


class FordFigo implements Vehicle, Ford {
//	@Override
//	public void printVehicleTypeName() { 
//		System.out.println("Figo");
//		Vehicle.super.printVehicleTypeName();
////		whomIBelongsTo(); //Vehicle.whomIBelongsTo();
//	}
	
	public void whomIBelongsTo() {
		System.out.println("Vehicle");
	}
}


interface Car {
	public default void printVehicleTypeName() { 
		System.out.println("Car");
	}
}

interface Ford extends Car {
	public default void printVehicleManufacturerName() { 
		System.out.println("Ford");
	}
}


