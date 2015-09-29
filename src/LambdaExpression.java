import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * http://www.angelikalanger.com/Lambdas/Lambdas.pdf
 * https://www.youtube.com/watch?v=C_QbkGU_lqY  //Lambda: A Peek Under the Hood
 * http://en.wikipedia.org/wiki/Lambda_calculus
 * https://www.youtube.com/watch?v=8pDm_kH4YKY
 * https://www.youtube.com/watch?v=CvFMandIirM&list=PLQwEegbFUjHH2VuIcTx2WuWWyhZPtxZ3W&index=2
 *
 */
public class LambdaExpression {
	
	public static void main(String[] args) {
	

		/**
		 * Anonymous inner class
		 */
		Increment i = new Increment() {
			public void incrementByOne(int x) {
				x = x + 1;
			}
		};
	   
	
		/**
		 * lambda expression is a function. 
		 * It is an unnamed piece of reusable functionality. 
		 * It has a signature and a body, but no name. 
		 * Here is an example of a lambda expression in Java:
		 */
	 
		Increment inc /*Interface Type*/ = /*arguments*/(int x)  /*lambda notion*/ ->  x = x + 1 /* function body*/;
	 
		/* 
		 * Merely looking at it tells that a lambda expression is something like a method without a name.
		 * someButton.addActionListener(event -> handleBulkClick());
		 * 
		 * Return type and exceptions are inferred by the compiler from the lambda body;
		 * In our example the return type is int and the throws clause is empty. 
		 */ 

		//Empty Arguments 
		PrintSysTime prntSysTime = () -> System.out.println(new java.util.Date());  
//		PrintSysTime_Dublicate prntSysTime_dublicate = () -> System.out.println(new java.util.Date()); // Is LHS is important ?  
	 
	 
	 
		/**
		 * Existing lambda candidates :: Functional interfaces
		 * 
		 * A functional interface essentially is an interface can only have one method (non default method).
		 * 
		 * Readable, Callable, Iterable, Closeable, Flushable, Formattable, Comparable, Comparator
		 * 
		 * public interface Runnable { public abstract void run(); }
		 * public interface Comparable { public int compareTo(T o); }
		 * public interface Callable { V call() throws Exception; }
		 * public interface TransactionCallBack<T> { T doInTransaction(TransactionStatus Status)}
		 * public interface RowMapper<T> { T mapRow(ResultSet rs, int rowNum) throws SQLException} 
		 * 
		 * 
		 * java.util.function package contains 43 commonly used functional interfaces:
		 * 
		 * Consumer<T> - function that takes argument of type T and returns void
		 * Supplier<T> - function that takes no argument and returns result of type T.
		 * Predicate<T> - function that takes argument of type T and returns boolean
		 * Function<T, R> - function that takes argument of type T and returns result of type R.
		 * ...
		 * ...
		 * 
		 */
	 
		String strArray [] = {"1", "11", "111"};
		Arrays.sort(strArray, (String s1, String s2) -> s1.length() - s2.length());  // Single Line lambda; and implicit return
	 
		//OLD Way
		Arrays.sort(strArray, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return s1.length() - s2.length();
			}
		});
	 
	 
	 
		/*
		 * Arrays.sort(strArray, (String s1, String s2) -> {return s1.length() - s2.length();}); //Explicit return  
		 * 
		 * Arrays.sort(strArray, (String s1, String s2) -> // Multiple Line lambda
		 * 	{
		 * 		int lengthS1 = s1.length();
		 * 		int lengthS2 = s2.length();
		 * 		return lengthS1 - lengthS2
		 * 	}
		 * );
		 */
	
		
		
	 
	 
		/**
		 * Exception Handling 
		 */
		ThrowException throwE = () -> // No explicit 'throws Exception'. Its implicit.
		{//Throwing new exception
			throw new IOException();
			//throw new java.lang.Exception();
		 	//throw new java.lang.IndexOutOfBoundsException(); //Runtime Exception
		 };
		 
		throwE = new ThrowException() {
			public void throwsException() throws IOException {
				//Throwing new exception
			 	//throw new Exception();
			};
		};
			 	 
		try {
			throwE.throwsException();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	
		
		
		
	
		/**
		 * Variable capture
		 * 
		 * Lambdas can interact with variable defined outside of the body.
		 * Using variable outside the body of the Lambda is called Variable capture.
		 *
		 *  The rules of using variable outside the body in Lambda variable capture is same like anonymous inner class.
		 */
		List<Integer> integers = java.util.Arrays.asList(1,2,3,4,5);
		String varOutsideLamda = "Iteration: ";
		integers.forEach(x -> { //Using java.util.function.Consumer<T>
			//varOutsideLamda = "Value:  "; //Local variable varOutsideLamda defined in an enclosing scope must be final or effectively final
			System.out.println(varOutsideLamda + x);
			}
		);
		//	varOutsideLamda = "Value:  ";
	
	
		/*
		 * Variable capture in anonymous way
		 */
		integers.forEach(new java.util.function.Consumer<Integer>(){

			private int state = 100; // STATE is not possible in lambda
		
			@Override
			public void accept(Integer t) {
				System.out.println(varOutsideLamda + t);
				System.out.println(state);
			}
		
			//state = 200;
		});
	
	

		
		
		
		
	
		/**
		 * this
		 * 
		 * The behavior is different from anonymous inner class 
		 */
		LambdaExpression INSTANCE = new LambdaExpression();
		INSTANCE.checkCurrentInstancewithInstaceOfLambda(INSTANCE);
	
	
	
		/**
		 * Method reference :: If i already have written the function implementation with signature exactly same as functional interface method signature. 
		 */
	
		/*
		 * Reference to static method
		 */
		Consumer<Integer> consumer1 =(x) -> staticMethod(x);  
		consumer1.accept(1001);
	 
		Consumer<Integer> consumer2 = LambdaExpression :: staticMethod; // No argument mentioning  
		consumer2.accept(1001);
	
		/*
		 * Reference a constructor
		 * 
		 * This is useful when working with Stream
		 */
		Function<String, Integer> function1 = x -> new Integer(x); 
		System.out.println(function1.apply("1002")); //new Integer(11);
     
		Function<String, Integer> function2 = Integer :: new; 
		System.out.println(function1.apply("1002")); //new Integer(11);
     
     
		/*
		 * Reference a specific object instance method
		 */
		Consumer<Integer> consumer3 = x -> System.out.println(x); //here println method reference is assigned to consumer3  
		consumer3.accept(1003);
	 
		Consumer<Integer> consumer4 = System.out :: println; // No argument mentioning  
		consumer4.accept(1003);
	
     
		//One more example
		Function <String, String> mapper1 = x -> x.toUpperCase();
		System.out.println(mapper1.apply("abc"));
     
		Function <String, String> mapper2 = String :: toUpperCase;
		System.out.println(mapper2.apply("def"));
     
	
	} //end of main

	private void checkCurrentInstancewithInstaceOfLambda(final LambdaExpression INSTANCE) {
		PrintSysTime prntSysTime = () -> {
			if(this == INSTANCE) {
				//System.out.println(new java.util.Date());   //In anonymous inner class :: LambdaExpression.this.
			}
		};  
		prntSysTime.printSysTime();
	}
	
	private static void staticMethod(int x){
		System.out.println("The argument value is "+x);
	}
	
}//end of class



@FunctionalInterface //Optional :to avoid typing mistakes of adding more than one method. 
abstract interface Increment {
	public abstract void incrementByOne(int x);
}


@FunctionalInterface
abstract interface PrintSysTime {
	public abstract void printSysTime();
}

@FunctionalInterface
abstract interface PrintSysTime_Dublicate {
	public abstract void printSysTime();
}

@FunctionalInterface
abstract interface ThrowException {
	public abstract void throwsException() throws IOException;
}

