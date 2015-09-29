import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * New package stream has been introduced.
 * http://docs.oracle.com/javase/8/docs/api/java/util/stream/package-frame.html
 * http://howtodoinjava.com/2014/04/13/java-8-tutorial-streams-by-examples/
 * http://blog.jooq.org/2014/06/13/java-8-friday-10-subtle-mistakes-when-using-the-streams-api/
 *
 */
public class StreamExplored {
public static void main(String[] args) {
	
	//Building up source of stream
	List<PurchaseOrder> listOfPOs = new ArrayList<PurchaseOrder>();
	for(int i = 1; i <= 10000; i++) {
		if(i % 2 == 0) {
			listOfPOs.add(new PurchaseOrder(PURCHASE_TYPE.ONLINE, 1, i *10));
		} else {
			listOfPOs.add(new PurchaseOrder(PURCHASE_TYPE.DIRECT, 1, i *10));
		}
	}
	
//	new Thread(){
//		public void run(){
//			try {Thread.sleep(2000);} catch (InterruptedException e) {}
//			System.out.println("Removing the entries from the list, after creating the stream!!");
//			for(int i = 0; i < 6000; i++) {
//				listOfPOs.remove(listOfPOs.size()-1);
//			}
//		}
//	}.start();
	
	long totalOnlineQuantity = listOfPOs.stream()
							.filter(po -> po.getPurchaseType() == PURCHASE_TYPE.ONLINE) // returns stream of ONLINE POs
							.mapToLong(po -> po.getQuantity()) // returns stream of Quantities (Use map() if the output is unknown)
							.sum(); /* PUT a breakpoint here, and run the above thread completely then perform SUM. 
							This will reveal that though Stream recipe is created with original list; the terminal function will work on actual/modified list only */
	
	                        // long count() : Returns the count of elements in this stream. This is a special case of a reduction and is equivalent to:
	                        // void forEach(Consumer<? super T> action) : Performs an action for each element of this stream.
	
	
	
	/*
	 *   Unless the source was explicitly designed for concurrent modification (such as a ConcurrentHashMap), 
	 *   unpredictable or erroneous behavior may result from modifying the stream source while it is being queried. 
	 *  
	 *  java.util.ConcurrentModificationException
	 */
//	totalOnlineQuantity = listOfPOs.stream()
//			.filter(po -> {
//				System.out.println("PO: " + po); // Why NULL object is possible ?
//				if(po != null && po.getPurchaseType() == PURCHASE_TYPE.ONLINE){
//					return true;
//				}
//				else {
//					if(po != null) {
//					  listOfPOs.remove(po);
//					}
//				      return false;
//				}
//			}) // returns stream of ONLINE POs
//			.mapToLong(po -> po.getQuantity()) // returns stream of Quantities
//			.sum();

	
//	totalOnlineQuantity = 0;
//	for(PurchaseOrder po : listOfPOs) {
//		if(po.getPurchaseType() == PURCHASE_TYPE.ONLINE) {
//			totalOnlineQuantity += po.getQuantity();
//		}
//	}

	
	
/*
 * How to break or return from Java8 Lambda forEach
 */
	
//	totalOnlineQuantity = listOfPOs.stream()
//			.filter(po -> {
//				if(po.getPurchaseType() == PURCHASE_TYPE.ONLINE) {
//					return true;
//				} else {
//					break; //break cannot be used outside of a loop or a switch
//				}
//				return false;
//			}).mapToLong(po -> po.getQuantity()).sum();    
			
		
	Optional obj =
			listOfPOs.stream()
			.filter(po -> po.getPurchaseType() == PURCHASE_TYPE.ONLINE).findFirst();
	
	System.out.println(obj);
	
	obj =	listOfPOs.stream()
			.filter(po -> po.getPurchaseType() == PURCHASE_TYPE.ONLINE).findAny();
	System.out.println(obj);
	
	boolean bool = listOfPOs.stream()
			.anyMatch(po -> po.getPurchaseType() == PURCHASE_TYPE.ONLINE); //boolean allMatch(Predicate<? super T> predicate) ||  boolean noneMatch(Predicate<? super T> predicate)  || Stream<T>	limit(long maxSize)
	
	
	
	
	/*
	 * How to get the list from the stream
	 * collect() method used to recieve elements from a sream and store them in a collection and metioned in parameter funcion.
	 * 
	 * http://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html
	 */
	listOfPOs = listOfPOs.stream()
				.filter(po -> po.getPurchaseType() == PURCHASE_TYPE.ONLINE).collect(Collectors.toList());
	
	
	System.out.println("Total online quanties sold: " +totalOnlineQuantity);
	
}


}

enum PURCHASE_TYPE{ONLINE, DIRECT};

class PurchaseOrder {

	private PURCHASE_TYPE pType = null;
	private long quantity = 0;
	private long totalPrice = 0; 
	
	
	public PurchaseOrder(PURCHASE_TYPE pType, long quantity, long totalPrice) {
		super();
		this.pType = pType;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}
	
	public PURCHASE_TYPE getPurchaseType() {
		return pType;
	}
	public void setPurchaseType(PURCHASE_TYPE pType) {
		this.pType = pType;
	}
	
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	
	public long getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		return "PurchaseOrder [pType=" + pType + ", quantity=" + quantity
				+ ", totalPrice=" + totalPrice + "]";
	}
	
	
}
